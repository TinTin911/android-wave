package app.android.box.waveprotocol.org.androidwave.service.models;

import org.waveprotocol.wave.model.document.Doc.E;
import org.waveprotocol.wave.model.document.util.DocumentEventRouter;
import org.waveprotocol.wave.model.util.Preconditions;

import java.util.Map;

public class ListElementFactory implements
        org.waveprotocol.wave.model.adt.docbased.Factory<E, Type, ListElementInitializer> {


    private Model model;

    protected ListElementFactory(Model model) {
        this.model = model;
    }

    @Override
    public Type adapt(DocumentEventRouter<? super E, E, ?> router, E element) {

        Map<String, String> attributes = router.getDocument().getAttributes(element);
        Preconditions.checkArgument(attributes != null,
                "Adapting a list element to Type but attributes not found");

        String type = attributes.get("t");
        Preconditions.checkArgument(type != null,
                "Adapting a list element to Type but attribute for type not found");

        String value = attributes.get("r");
        Preconditions.checkArgument(value != null,
                "Adapting a list element to Type but attribute for reference not found");

        return Type.createInstance(type, value, model);

    }

    @Override
    public org.waveprotocol.wave.model.adt.docbased.Initializer createInitializer(
            final ListElementInitializer initialState) {

        return new org.waveprotocol.wave.model.adt.docbased.Initializer() {

            @Override
            public void initialize(Map<String, String> target) {
                target.put("t", initialState.getType());
                if (initialState.getBackendId() != null) {
                    target.put("r", initialState.getBackendId());
                }
            }

        };
    }

}