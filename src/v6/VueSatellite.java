package v6;

import nicellipse.component.NiRectangle;
import java.awt.Color;
import java.awt.Dimension;

public class VueSatellite extends NiRectangle implements Vue {
    private static final long serialVersionUID = 1L;
    private final ModeleSatellite modele;

    public VueSatellite(ModeleSatellite modele) {
        this.modele = modele;
        this.setBackground(Color.red);
        this.setSize(new Dimension(15, 15));
    }

    @Override
    public void mettreAJour() {
        this.setLocation(modele.getX(), modele.getY());

        // Change la couleur selon l'état
        if (modele.estEnSynchronisation()) {
            this.setBackground(Color.green);
        } else {
            this.setBackground(Color.red);
        }
    }
}