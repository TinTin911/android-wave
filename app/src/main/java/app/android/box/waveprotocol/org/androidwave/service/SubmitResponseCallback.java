package app.android.box.waveprotocol.org.androidwave.service;

import org.waveprotocol.box.common.comms.ProtocolSubmitResponse;

public interface SubmitResponseCallback {

    public void run(ProtocolSubmitResponse response);
}

