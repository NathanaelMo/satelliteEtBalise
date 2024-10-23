package claude;

import java.util.EventObject;

public class DataTransferEvent extends AbstractMarineEvent {
    private static final long serialVersionUID = 1L;

    public DataTransferEvent(Object source) {
        super(source);
    }

    @Override
    public void sentTo(Object target) {
        ((MarineListener)target).onDataTransferComplete(this);
    }
}