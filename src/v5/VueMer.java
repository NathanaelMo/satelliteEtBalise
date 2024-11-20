package v5;

import nicellipse.component.NiRectangle;

import java.awt.*;

public class VueMer extends NiRectangle {
    private static final long serialVersionUID = 1L;

    public VueMer(int width, int height) {
        setSize(new Dimension(width, height));
        setBackground(new Color(0, 119, 190)); // Bleu mer
    }
}