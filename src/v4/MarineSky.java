package v4;

import nicellipse.component.NiRectangle;

import java.awt.*;

class MarineSky extends NiRectangle {
    private static final long serialVersionUID = 1L;

    public MarineSky(int width, int height) {
        setSize(new Dimension(width, height));
        setLocation(0, 0);
        setBackground(new Color(135, 206, 235)); // Bleu ciel simple
    }
}
