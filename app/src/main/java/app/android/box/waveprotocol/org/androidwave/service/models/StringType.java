package app.android.box.waveprotocol.org.androidwave.service.models;

import org.waveprotocol.wave.model.adt.ObservableBasicValue;
import org.waveprotocol.wave.model.util.CopyOnWriteSet;
import org.waveprotocol.wave.model.util.Preconditions;
import org.waveprotocol.wave.model.wave.SourcesEvents;

public class StringType extends Type implements SourcesEvents<StringType.Listener> {


    public final static String PREFIX = "str";
    public final static String VALUE_ATTR = "v";
    private final CopyOnWriteSet<Listener> listeners = CopyOnWriteSet.create();
    private ObservableBasicValue<String> observableValue;
    private ObservableBasicValue.Listener<String> observableValueListener;
    private Model model;
    private int indexStringPos;
    private String initValue;
    private boolean isAttached;

    protected StringType(Model model) {

        this.model = model;
        this.initValue = null;
        this.observableValueListener = new ObservableBasicValue.Listener<String>() {

            @Override
            public void onValueChanged(String oldValue, String newValue) {
                for (Listener l : listeners)
                    l.onValueChanged(oldValue, newValue);
            }
        };
    }

    public StringType(Model model, String value) {

        this.model = model;
        this.initValue = value != null ? value : ""; // null string is not valid

        this.observableValueListener = new ObservableBasicValue.Listener<String>() {

            @Override
            public void onValueChanged(String oldValue, String newValue) {
                for (Listener l : listeners)
                    l.onValueChanged(oldValue, newValue);
            }
        };
    }

    protected static Type createAndAttach(Model model, String id) {

        Preconditions.checkArgument(id.startsWith(PREFIX),
                "StringType.createAndAttach() not a string id");
        StringType string = new StringType(model);
        string.attach(id);
        return string;

    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

    @Override
    protected void attach(String stringId) {

        if (stringId == null) {

            indexStringPos = model.getStringIndex().size();
            observableValue = model.getStringIndex().add(indexStringPos, initValue);

        } else {

            indexStringPos = Integer.valueOf(stringId.split("\\+")[1]);
            observableValue = model.getStringIndex().get(indexStringPos);
            initValue = observableValue.get();

        }

        observableValue.addListener(observableValueListener);

        this.isAttached = true;

    }

    protected void deAttach() {
        Preconditions.checkArgument(isAttached, "Unable to deAttach an unattached MapType");
    }

    @Override
    protected boolean isAttached() {
        return isAttached;
    }

    @Override
    protected String serializeToModel() {
        Preconditions.checkArgument(isAttached, "Unable to serialize an unattached StringType");
        return PREFIX + "+" + Integer.toString(indexStringPos);
    }

    @Override
    protected ListElementInitializer getListElementInitializer() {
        return new ListElementInitializer() {

            @Override
            public String getType() {
                return PREFIX;
            }

            @Override
            public String getBackendId() {
                Preconditions.checkArgument(isAttached, "Unable to initialize an unattached StringType");
                return serializeToModel();
            }

        };
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public String getValue() {
        if (!isAttached())
            return initValue;
        else
            return observableValue.get();
    }

    public void setValue(String value) {
        if (isAttached()) observableValue.set(value);
    }

    @Override
    public String getDocumentId() {
        return null;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public String getType() {
        return "StringType";
    }

    public interface Listener {
        void onValueChanged(String oldValue, String newValue);
    }
}
