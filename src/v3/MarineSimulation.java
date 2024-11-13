package v3;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MarineSimulation {
    // Constants for boundaries
    private static final int SKY_MIN_Y = 50;
    private static final int SKY_MAX_Y = 150;
    private static final int SEA_MIN_Y = 350;
    private static final int SEA_MAX_Y = 500;
    private static final int MIN_VERTICAL_DISTANCE = 1;
    private static final int TAILLE_FENETRE = 800;
    private static final int MAX_SATELLITES_STATIONNAIRES = 3; // Maximum de satellites stationnaires

    public static void main(String[] args) {
        Random random = new Random();

        NiSpace space = new NiSpace("Marine Simulation", new Dimension(TAILLE_FENETRE, 600));
        NiRectangle container = new NiRectangle();
        container.setLayout(null);
        container.setSize(new Dimension(TAILLE_FENETRE, 600));
        space.add(container);

        // Liste pour stocker les satellites
        ArrayList<Satellite> satelliteList = new ArrayList<>();
        ArrayList<BaliseView> baliseViews = new ArrayList<>();
        ArrayList<SatelliteView> satelliteViews = new ArrayList<>();
        int stationaryCount = 0; // Compteur pour les satellites stationnaires

        // Ajout du ciel et de la mer
        MarineSky sky = new MarineSky(TAILLE_FENETRE, 320);
        sky.setLocation(0, 0);
        container.add(sky, 0);

        MarineSea sea = new MarineSea(TAILLE_FENETRE, 300);
        sea.setLocation(0, 320);
        container.add(sea, 0);

        // Création des satellites
        int numSatellites = 10;
        ArrayList<Integer> hauteurUtilise = new ArrayList<>();

        // Calcul de l'espacement moyen pour les satellites
        int espaceDispoSat = SKY_MAX_Y - SKY_MIN_Y;
        int espaceMoyenSat = espaceDispoSat / numSatellites;

        for (int i = 0; i < numSatellites; i++) {
            int baseY = SKY_MIN_Y + (i * espaceMoyenSat);
            int yPos = baseY + random.nextInt(espaceMoyenSat);
            hauteurUtilise.add(yPos);

            int xPos = (TAILLE_FENETRE / numSatellites) * i + random.nextInt(TAILLE_FENETRE /numSatellites);
            int speed = random.nextInt(3) + 2;
            boolean synchro = random.nextBoolean();

            // Obtention du pattern en tenant compte du maximum de stationnaires
            Satellite.MovementPattern pattern = getRandomSatellitePattern(random, stationaryCount);
            if (pattern == Satellite.MovementPattern.STATIONARY) {
                stationaryCount++;
            }

            Satellite satellite = new Satellite(xPos, yPos, speed, synchro, pattern);
            SatelliteView satelliteView = new SatelliteView(satellite);

            satelliteList.add(satellite);
            satelliteViews.add(satelliteView);
            container.add(satelliteView, 1);
        }

        // Création des balises
        int numBalise = 10;
        hauteurUtilise.clear();

        int espaceDispoBalise = SEA_MAX_Y - SEA_MIN_Y;
        int espaceMoyenBalise = espaceDispoBalise / numBalise;

        for (int i = 0; i < numBalise; i++) {
            int baseY = SEA_MIN_Y + (i * espaceMoyenBalise);
            int yPos = baseY + random.nextInt(espaceMoyenBalise);
            hauteurUtilise.add(yPos);

            int xPos = (TAILLE_FENETRE / numBalise) * i + random.nextInt(TAILLE_FENETRE /numBalise);
            Balise.MovementPattern pattern = getRandomBalisePattern(random);

            Balise balise = new Balise(xPos, yPos, pattern);
            BaliseView baliseView = new BaliseView(balise, satelliteList);

            baliseViews.add(baliseView);
            container.add(baliseView, 0);
        }

        space.openInWindow();

        // Boucle principale
        while (true) {
            for (BaliseView baliseView : baliseViews) {
                baliseView.getModel().update();
                baliseView.update();
            }

            for (SatelliteView satelliteView : satelliteViews) {
                satelliteView.getModel().update();
                satelliteView.update();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            container.repaint();
        }
    }

    private static Balise.MovementPattern getRandomBalisePattern(Random random) {
        Balise.MovementPattern[] patterns = {
                Balise.MovementPattern.HORIZONTAL,
                Balise.MovementPattern.STATIONARY,
                Balise.MovementPattern.SINUSOIDAL
        };
        return patterns[random.nextInt(patterns.length)];
    }

    private static Satellite.MovementPattern getRandomSatellitePattern(Random random, int nbSatStationnaire) {
        // Si on a atteint le maximum de satellites stationnaires, on ne peut plus en créer
        if (nbSatStationnaire >= MAX_SATELLITES_STATIONNAIRES) {
            Satellite.MovementPattern[] patterns = {
                    Satellite.MovementPattern.HORIZONTAL,
                    Satellite.MovementPattern.SINUSOIDAL
            };
            return patterns[random.nextInt(patterns.length)];
        } else {
            // Sinon on peut avoir tous les patterns
            Satellite.MovementPattern[] patterns = {
                    Satellite.MovementPattern.HORIZONTAL,
                    Satellite.MovementPattern.STATIONARY,
                    Satellite.MovementPattern.SINUSOIDAL
            };
            return patterns[random.nextInt(patterns.length)];
        }
    }
}