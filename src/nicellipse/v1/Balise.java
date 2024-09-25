package nicellipse.v1;

import nicellipse.component.NiBorderedComponent;
import nicellipse.component.NiRectangle;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Balise extends JPanel implements NiBorderedComponent {
    private int vitesse;
    private int x;
    private int y;

    public Balise(){
        this.defaultSetup();
        this.setDimension(new Dimension(20,20));
        this.setLayout(null);
        this.setBackground(Color.blue);
        this.vitesse=1;
        this.x=25;
        this.y=0;
    }
    public Balise(int vitesse,int x, int y){
        this.vitesse=vitesse;
        this.x=x;
        this.y=y;
    }

    public Point move(){
        this.y = this.y + vitesse;
        return new Point(x,y);
    }

    public Point reset(){
        this.y=0;
        return new Point(x,y);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
