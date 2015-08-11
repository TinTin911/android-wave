package app.android.box.waveprotocol.org.androidwave.service;

import java.util.Map;

import org.waveprotocol.box.common.comms.ProtocolWaveletUpdate;
import org.waveprotocol.box.common.comms.gson.ProtocolOpenRequestGsonImpl;
import org.waveprotocol.box.common.comms.gson.ProtocolSubmitRequestGsonImpl;
import org.waveprotocol.wave.model.id.IdFilter;
import org.waveprotocol.wave.model.id.InvalidIdException;
import org.waveprotocol.wave.model.id.ModernIdSerialiser;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.id.WaveletName;
import org.waveprotocol.wave.model.util.CollectionUtils;

public final class RemoteViewServiceMultiplexer implements WaveWebSocketCallback {

    private final Map<WaveId, WaveWebSocketCallback> streams = CollectionUtils.newHashMap();

    private final Map<WaveId, String> knownChannels = CollectionUtils.newHashMap();

    private final WaveWebSocketClient socket;

    private final String userId;

    public RemoteViewServiceMultiplexer(WaveWebSocketClient socket, String userId) {
        this.socket = socket;
        this.userId = userId;

        socket.attachHandler(this);
    }

    @Override
    public void onWaveletUpdate(ProtocolWaveletUpdate message) {
        WaveletName wavelet = deserialize(message.getWaveletName());

        WaveWebSocketCallback stream = streams.get(wavelet.waveId);
        if (stream != null) {
            boolean drop;

            String knownChannelId = knownChannels.get(wavelet.waveId);
            if (knownChannelId != null) {
                drop = message.hasChannelId() && !message.getChannelId().equals(knownChannelId);
            } else {
                if (message.hasChannelId()) {
                    knownChannels.put(wavelet.waveId, message.getChannelId());
                }
                drop = false;
            }

            if (!drop) {
                stream.onWaveletUpdate(message);
            }
        }
    }

    public void open(WaveId id, IdFilter filter, WaveWebSocketCallback stream) {

        streams.put(id, stream);

        ProtocolOpenRequestGsonImpl request = new ProtocolOpenRequestGsonImpl();
        request.setWaveId(ModernIdSerialiser.INSTANCE.serialiseWaveId(id));
        request.setParticipantId(userId);
        for (String prefix : filter.getPrefixes()) {
            request.addWaveletIdPrefix(prefix);
        }

        for (WaveletId wid : filter.getIds()) {
            request.addWaveletIdPrefix(wid.getId());
        }
        socket.open(request);
    }

    public void close(WaveId id, WaveWebSocketCallback stream) {
        if (streams.get(id) == stream) {
            streams.remove(id);
            knownChannels.remove(id);
        }
    }

    public void submit(ProtocolSubmitRequestGsonImpl request, SubmitResponseCallback callback) {
        request.getDelta().setAuthor(userId);
        socket.submit(request, callback);
    }

    public static WaveletName deserialize(String name) {
        try {
            return ModernIdSerialiser.INSTANCE.deserialiseWaveletName(name);
        } catch (InvalidIdException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String serialize(WaveletName name) {
        return ModernIdSerialiser.INSTANCE.serialiseWaveletName(name);
    }
}
