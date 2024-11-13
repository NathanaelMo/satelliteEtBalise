package v4;

import nicellipse.component.NiRectangle;

import java.awt.*;
import java.util.ArrayList;

public class BaliseView extends NiRectangle implements MarineListener {
    private Balise model;
    private static final long serialVersionUID = 1L;

    public BaliseView(Balise model, ArrayList<Satellite> satellites) {
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
        if (model.isSurface()) {
            this.setBackground(Color.yellow);
        } else {
            this.setBackground(Color.blue);
        }
        if (this.getParent() != null && this.getParent().getComponents().length > 0) {
            for (int i = 0; i < this.getParent().getComponents().length; i++) {
                if (this.getParent().getComponents()[i] instanceof SatelliteView satelliteView) {
                    model.checkSatellite(satelliteView.getModel());
                }
            }
        }
    }

    @Override
    public void onSynchroRequest(SynchronizationRequestEvent evt) {
        this.setBackground(Color.yellow);
    }

    @Override
    public void echangeFini(DataTransferEvent evt) {
        this.setBackground(Color.blue);
    }

    public Balise getModel() {
        return model;
    }
}