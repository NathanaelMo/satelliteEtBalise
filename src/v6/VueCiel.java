package v6;

import nicellipse.component.NiRectangle;
import java.awt.*;

public class VueCiel extends NiRectangle {
    private static final long serialVersionUID = 1L;

    public VueCiel(int largeur, int hauteur) {
        setSize(new Dimension(largeur, hauteur));
        setLocation(0, 0);
        setBackground(new Color(135, 206, 235)); // Bleu ciel
    }
}