package claude;

import nicellipse.component.NiRectangle;

import java.awt.*;

public class SatelliteView extends NiRectangle {
    private Satellite model;
    private static final long serialVersionUID = 1L;

    public SatelliteView(Satellite model) {
        this.model = model;
        this.setBackground(Color.red);
        this.setSize(new Dimension(15, 15));
    }

    public void update() {
        this.setLocation(model.getX(), model.getY());
        if (model.isSynchronizing()) {
            this.setBackground(Color.green);
        } else {
            this.setBackground(Color.red);
        }
    }

    public Satellite getModel() {
        return model;
    }
}