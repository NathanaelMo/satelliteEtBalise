package v4;

import nicellipse.component.NiRectangle;
import java.awt.*;

public class VueCiel extends NiRectangle {
    private static final long serialVersionUID = 1L;

    public VueCiel(int width, int height) {
        setSize(new Dimension(width, height));
        setLocation(0, 0);
        setBackground(new Color(135, 206, 235)); // Bleu ciel
    }
}