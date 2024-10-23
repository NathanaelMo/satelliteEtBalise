package claude;

import java.awt.Color;
import java.awt.Dimension;
import nicellipse.component.NiRectangle;

public class BeaconView extends NiRectangle implements MarineListener {
    private Beacon model;
    private static final long serialVersionUID = 1L;

    public BeaconView(Beacon model, Satellite satellite) {
        this.model = model;
        this.setBackground(Color.blue);
        this.setSize(new Dimension(10, 10));
        // On enregistre la vue pour recevoir les événements de la balise
        model.register(this);
        // On enregistre le satellite pour recevoir les événements de la balise
        model.register(satellite);
    }

    public void update() {
        this.setLocation(model.getX(), model.getY());
        if (model.isSurfaced()) {
            this.setBackground(Color.yellow);
        } else {
            this.setBackground(Color.blue);
        }
        // Vérifie si un satellite est au-dessus
        if (this.getParent() != null && this.getParent().getComponents().length > 0) {
            for (int i = 0; i < this.getParent().getComponents().length; i++) {
                if (this.getParent().getComponents()[i] instanceof SatelliteView) {
                    SatelliteView satelliteView = (SatelliteView) this.getParent().getComponents()[i];
                    model.checkForSatellite(satelliteView.getModel());
                }
            }
        }
    }

    @Override
    public void onSynchronizationRequest(SynchronizationRequestEvent evt) {
        // Change la couleur en jaune quand une demande de synchronisation est reçue
        this.setBackground(Color.yellow);
    }

    @Override
    public void onDataTransferComplete(DataTransferEvent evt) {
        // Retourne à la couleur bleue une fois la synchronisation terminée
        this.setBackground(Color.blue);
    }
}