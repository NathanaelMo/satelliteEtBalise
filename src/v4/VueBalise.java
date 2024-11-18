package v4;

import nicellipse.component.NiRectangle;
import java.awt.Color;
import java.awt.Dimension;

public class VueBalise extends NiRectangle implements Vue {
    private static final long serialVersionUID = 1L;
    private final ModeleBalise modele;

    public VueBalise(ModeleBalise modele) {
        this.modele = modele;
        this.setBackground(Color.blue);
        this.setSize(new Dimension(10, 10));
    }

    @Override
    public void mettreAJour() {
        this.setLocation(modele.getX(), modele.getY());
        if (modele.estEnSurface()) {
            this.setBackground(Color.yellow);
        } else {
            this.setBackground(Color.blue);
        }
    }
}