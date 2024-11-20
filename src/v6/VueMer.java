package v6;

import nicellipse.component.NiRectangle;
import java.awt.*;

public class VueMer extends NiRectangle {
    private static final long serialVersionUID = 1L;

    public VueMer(int largeur, int hauteur) {
        setSize(new Dimension(largeur, hauteur));
        setBackground(new Color(0, 119, 190)); // Bleu mer
    }
}