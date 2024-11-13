package v4;

import java.util.*;

public class Announcer {
	private Map<Class<? extends AbstractMarineEvent>, List<Object>> registrationIndex;

	public Announcer() {
		registrationIndex = new HashMap<>();
	}

	public void register(Object o, Class<? extends AbstractMarineEvent> eventClass) {
		List<Object> l = registrationIndex.get(eventClass);
		if (l == null) {
			l = new ArrayList<Object>();
			registrationIndex.put(eventClass, l);
		}
		l.add(o);
	}

	public void unregister(Object o, Class<? extends AbstractMarineEvent> eventClass) {
		List<Object> l = registrationIndex.get(eventClass);
		if (l != null) {
			Iterator<Object> itor = l.iterator();
			while (itor.hasNext()) {
				Object current = itor.next();
				if (o == current) itor.remove();
			}
			if (l.isEmpty()) {
				registrationIndex.remove(eventClass);
			}
		}
	}

	public void announce(AbstractMarineEvent anEvent) {
		Class<?> eventClass = anEvent.getClass();
		List<Object> l = registrationIndex.get(eventClass);
		if (l == null) return;
		Iterator<Object> itor = l.iterator();
		while (itor.hasNext()) {
			Object current = itor.next();
			anEvent.sentTo(current);
		}
	}
}