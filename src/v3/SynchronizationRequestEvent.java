// Modifier SynchronizationRequestEvent pour inclure le satellite ciblé
package v3;

public class SynchronizationRequestEvent extends AbstractMarineEvent {
    private static final long serialVersionUID = 1L;
    private final Satellite targetSatellite; // Ajout du satellite ciblé

    public SynchronizationRequestEvent(Object source, Satellite targetSatellite) {
        super(source);
        this.targetSatellite = targetSatellite;
    }

    public Satellite getTargetSatellite() {
        return targetSatellite;
    }

    @Override
    public void sentTo(Object target) {
        // Ne notifie que si le target est le satellite ciblé ou n'est pas un satellite
        if (!(target instanceof Satellite) || target == targetSatellite) {
            ((MarineListener)target).onSynchroRequest(this);
        }
    }
}