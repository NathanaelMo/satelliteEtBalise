package claude;

import java.awt.Color;
import java.awt.Dimension;
import nicellipse.component.NiRectangle;
import java.util.ArrayList;

public class BeaconView extends NiRectangle implements MarineListener {
    private Beacon model;
    private static final long serialVersionUID = 1L;

    public BeaconView(Beacon model, ArrayList<Satellite> satellites) {
        this.model = model;
        this.setBackground(Color.blue);
        this.setSize(new Dimension(10, 10));
        model.register(this);
        for (Satellite satellite : satellites) {
            model.register(satellite);
        }
    }

    public void update() {
        this.setLocation(model.getX(), model.getY());
        if (model.isSurfaced()) {
            this.setBackground(Color.yellow);
        } else {
            this.setBackground(Color.blue);
        }
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
        this.setBackground(Color.yellow);
    }

    @Override
    public void onDataTransferComplete(DataTransferEvent evt) {
        this.setBackground(Color.blue);
    }
}