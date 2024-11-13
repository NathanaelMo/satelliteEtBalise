package oberser_observable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observer;
import java.util.Observable;

class Satellite implements Observer {
    private Point position;
    private DeplacementStrategy strategy;

    public Satellite(int x, int y, DeplacementStrategy strategy) {
        this.position = new Point(x, y);
        this.strategy = strategy;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Balise) {
            Balise balise = (Balise) o;
            // Change la couleur si la balise est proche
            if (position.distance(balise.getPosition()) < 100) {
                balise.setCouleur(Color.RED); // Changement de couleur
            } else {
                balise.setCouleur(Color.GREEN);
            }
        }
    }

    public void deplacer() {
        strategy.deplacer(this);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    public void dessiner(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(position.x - 5, position.y - 5, 10, 10); // Dessine le satellite
    }
}