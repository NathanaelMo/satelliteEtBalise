package v3;

import nicellipse.component.NiRectangle;

import java.awt.*;

class MarineSea extends NiRectangle {
    private static final long serialVersionUID = 1L;

    public MarineSea(int width, int height) {
        setSize(new Dimension(width, height));
        setBackground(new Color(0, 119, 190)); // Bleu mer simple
    }
}