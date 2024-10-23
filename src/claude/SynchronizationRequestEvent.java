package claude;


public class SynchronizationRequestEvent extends AbstractMarineEvent {
    private static final long serialVersionUID = 1L;

    public SynchronizationRequestEvent(Object source) {
        super(source);
    }

    @Override
    public void sentTo(Object target) {
        ((MarineListener)target).onSynchronizationRequest(this);
    }
}