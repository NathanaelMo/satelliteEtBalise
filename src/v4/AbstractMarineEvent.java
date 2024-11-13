package v4;

import java.util.EventObject;

public abstract class AbstractMarineEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    public AbstractMarineEvent(Object source) {
        super(source);
    }

    public abstract void sentTo(Object target);
}