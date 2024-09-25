package nicellipse.v1;

import nicellipse.component.NiBorderedComponent;

import javax.swing.*;
import java.awt.*;

public class Satellite extends JPanel implements NiBorderedComponent {

    private int vitesse;

    private int x;

    private int y;

    public Satellite(){
        this.defaultSetup();
        this.setDimension(new Dimension(20,20));
        this.setLayout(null);
        this.setBackground(Color.red);
        this.vitesse = 1;
        this.x = 0;
        this.y = 75;
    }

    public Point move(){
        this.setX(this.getX() + this.getVitesse());
        return new Point(this.getX(), this.getY());
    }

    public Point reset(){
        this.setX(0);
        return new Point(this.getX(), this.getY());
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
