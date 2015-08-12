package app.android.box.waveprotocol.org.androidwave.service;

import org.waveprotocol.wave.concurrencycontrol.common.UnsavedDataListener;
import org.waveprotocol.wave.model.conversation.ObservableConversationView;
import org.waveprotocol.wave.model.conversation.WaveBasedConversationView;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.wave.data.WaveViewData;
import org.waveprotocol.wave.model.wave.data.impl.WaveViewDataImpl;
import org.waveprotocol.wave.model.wave.opbased.ObservableWaveView;
import org.waveprotocol.wave.model.wave.opbased.OpBasedWavelet;
import org.waveprotocol.wave.model.wave.opbased.WaveViewImpl;
import org.waveprotocol.wave.model.waveref.WaveRef;

import java.util.Set;
import java.util.Timer;

import app.android.box.waveprotocol.org.androidwave.service.concurrencycontrol.MuxConnector;
import app.android.box.waveprotocol.org.androidwave.service.concurrencycontrol.MuxConnector.Command;

public class WaveRender {

    private final WaveRef waveRef;
    private final boolean isNewWave;
    private final Set<ParticipantId> otherParticipants;
    private ParticipantId signedInuser;
    private IdGenerator idGenerator;

    private final RemoteViewServiceMultiplexer channel;
    private final UnsavedDataListener unsavedDataListener;

    private WaveViewData waveData;
    private MuxConnector connector;

    private ObservableConversationView conversations;
    private WaveViewImpl<OpBasedWavelet> wave;

    private boolean isClosed = true;
    private Timer timer;


    public WaveRender(boolean isNewWave, WaveRef waveRef, RemoteViewServiceMultiplexer waveChannel,
                         ParticipantId waveParticipant,
                         Set<ParticipantId> waveOtherParticipants, IdGenerator waveIdGenerator,
                         UnsavedDataListener unsavedDataListener, Timer timer) {
        this.signedInuser = waveParticipant;
        this.waveRef = waveRef;
        this.isNewWave = isNewWave;
        this.idGenerator = waveIdGenerator;
        this.channel = waveChannel;
        this.otherParticipants = waveOtherParticipants;
        this.unsavedDataListener = unsavedDataListener;
        this.timer = timer;
    }

    public void init(Command command) {

        waveData = WaveViewDataImpl.create(waveRef.getWaveId());

        if (isNewWave) {

            getConversations().createRoot().addParticipantIds(otherParticipants);
            getConnector().connect(command);
        } else {
            getConnector().connect(command);
        }

        isClosed = false;
    }

    private ObservableConversationView getConversations() {
        return conversations == null ? conversations = createConversations() : conversations;
    }

    private MuxConnector getConnector() {
        return connector == null ? connector = createConnector() : connector;
    }

    private ObservableConversationView createConversations() {
        return WaveBasedConversationView.create(getWave(), getIdGenerator());
    }

    private WaveViewImpl<OpBasedWavelet> getWave() {
        return wave == null ? wave = createWave() : wave;
    }

    private IdGenerator getIdGenerator() {
        return idGenerator;
    }

    private MuxConnector createConnector() {
        return null;
    }

    private WaveViewImpl<OpBasedWavelet> createWave() {
        return null;
    }

}
