package nicellipse.v1;

import nicellipse.component.NiBorderedComponent;
import nicellipse.component.NiRectangle;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Balise extends JPanel implements NiBorderedComponent {
    private int vitesse;
    private int x;
    private int y;
    private boolean stop=false;
    private int surface;
    /**
     * Parcours sinusoïde ou horizontal ou vertical
     */
    private ParcoursBalise parcoursChoisi ;

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
        this.defaultSetup();
        this.setDimension(new Dimension(20,20));
        this.setLayout(null);
        this.setBackground(Color.blue);
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

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Point monter(){
        this.y = this.y + vitesse;
        return new Point(x,y);
    }

    public Point descendre(){
        this.y = this.y + vitesse;
        return new Point(x,y);
    }

    public Point glisser(){
        this.x = this.x + vitesse;
        return new Point(x,y);
    }

    public void parcours(){
        Random rand = new Random();
        int res=rand.nextInt(3);
        if (res==0){
            this.parcoursChoisi=ParcoursBalise.SINUS;
        } else if (res==1) {
            this.parcoursChoisi=ParcoursBalise.VERTICAL;
        }else {
            this.parcoursChoisi=ParcoursBalise.HORIZONTAL;
        }
        //return this.parcoursChoisi;
    }
}
