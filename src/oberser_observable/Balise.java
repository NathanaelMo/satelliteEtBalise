package oberser_observable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observable;

class Balise extends Observable {
    private Point position;
    private DeplacementStrategy strategy;
    private Color couleur;

    public Balise(int x, int y, DeplacementStrategy strategy) {
        this.position = new Point(x, y);
        this.strategy = strategy;
        this.couleur = Color.GREEN; // Couleur par défaut
    }

    public void setStrategy(DeplacementStrategy strategy) {
        this.strategy = strategy;
    }

    public void deplacer() {
        strategy.deplacer(this);
        setChanged();
        notifyObservers();
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public void dessiner(Graphics g) {
        g.setColor(couleur);
        g.fillOval(position.x - 5, position.y - 5, 10, 10); // Dessine la balise
    }
}