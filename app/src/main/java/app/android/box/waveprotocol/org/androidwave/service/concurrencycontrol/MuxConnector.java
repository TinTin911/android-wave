package app.android.box.waveprotocol.org.androidwave.service.concurrencycontrol;

public interface MuxConnector {

    public interface Command {

        public void execute();

    }

    void connect(Command onOpened);

    void close();
}