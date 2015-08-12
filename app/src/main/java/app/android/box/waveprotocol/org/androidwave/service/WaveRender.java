package app.android.box.waveprotocol.org.androidwave.service;

import org.waveprotocol.wave.concurrencycontrol.common.UnsavedDataListener;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import java.util.Set;
import java.util.Timer;

public class WaveRender {

    private final WaveRef waveRef;
    private final boolean isNewWave;
    private final Set<ParticipantId> otherParticipants;
    private ParticipantId signedInuser;
    private IdGenerator idGenerator;

    private final RemoteViewServiceMultiplexer channel;
    private final UnsavedDataListener unsavedDataListener;

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
}
