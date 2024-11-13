package oberser_observable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulation extends JPanel {
    private List<Balise> balises;
    private List<Satellite> satellites;

    public Simulation() {
        balises = new ArrayList<>();
        satellites = new ArrayList<>();

        // Créer des balises avec des stratégies de déplacement
        Balise balise1 = new Balise(100, 100, new DeplacementHorizontal(2));
        balises.add(balise1);

        // Créer des satellites avec des stratégies de déplacement
        Satellite satellite1 = new Satellite(50, 50, new DeplacementHorizontal(5));
        Satellite satellite2 = new Satellite(150, 50, new DeplacementHorizontal(2));
        satellites.add(satellite1);
        satellites.add(satellite2);

        // Ajouter les satellites comme observateurs des balises
        for (Balise balise : balises) {
            balise.addObserver(satellite1);
            balise.addObserver(satellite2);
        }

        Timer timer = new Timer(100, e -> {
            //for (Balise balise : balises) {
               // balise.deplacer();
            //}
            for (Satellite satellite : satellites) {
                satellite.deplacer();
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Balise balise : balises) {
            balise.dessiner(g);
        }
        for (Satellite satellite : satellites) {
            satellite.dessiner(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulation de Satellites et Balises");
        Simulation simulation = new Simulation();
        frame.add(simulation);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}