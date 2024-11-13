package claude;

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
    private static final int WINDOW_WIDTH = 800;
    private static final int MAX_STATIONARY_SATELLITES = 3; // Maximum de satellites stationnaires

    public static void main(String[] args) {
        Random random = new Random();

        NiSpace space = new NiSpace("Marine Simulation", new Dimension(WINDOW_WIDTH, 600));
        NiRectangle container = new NiRectangle();
        container.setLayout(null);
        container.setSize(new Dimension(WINDOW_WIDTH, 600));
        space.add(container);

        // Liste pour stocker les satellites
        ArrayList<Satellite> satelliteList = new ArrayList<>();
        ArrayList<BeaconView> beaconViews = new ArrayList<>();
        ArrayList<SatelliteView> satelliteViews = new ArrayList<>();
        int stationaryCount = 0; // Compteur pour les satellites stationnaires

        // Ajout du ciel et de la mer
        MarineSky sky = new MarineSky(WINDOW_WIDTH, 320);
        sky.setLocation(0, 0);
        container.add(sky, 0);

        MarineSea sea = new MarineSea(WINDOW_WIDTH, 300);
        sea.setLocation(0, 320);
        container.add(sea, 0);

        // Création des satellites
        int numSatellites = 10;
        ArrayList<Integer> usedYPositions = new ArrayList<>();

        // Calcul de l'espacement moyen pour les satellites
        int availableSpaceS = SKY_MAX_Y - SKY_MIN_Y;
        int avgSpacingS = availableSpaceS / numSatellites;

        for (int i = 0; i < numSatellites; i++) {
            int baseY = SKY_MIN_Y + (i * avgSpacingS);
            int yPos = baseY + random.nextInt(avgSpacingS);
            usedYPositions.add(yPos);

            int xPos = (WINDOW_WIDTH / numSatellites) * i + random.nextInt(WINDOW_WIDTH/numSatellites);
            int speed = random.nextInt(3) + 2;
            boolean isGeosynchronous = random.nextBoolean();

            // Obtention du pattern en tenant compte du maximum de stationnaires
            Satellite.MovementPattern pattern = getRandomSatellitePattern(random, stationaryCount);
            if (pattern == Satellite.MovementPattern.STATIONARY) {
                stationaryCount++;
            }

            Satellite satellite = new Satellite(xPos, yPos, speed, isGeosynchronous, pattern);
            SatelliteView satelliteView = new SatelliteView(satellite);

            satelliteList.add(satellite);
            satelliteViews.add(satelliteView);
            container.add(satelliteView, 1);
        }

        // Création des balises
        int numBeacons = 10;
        usedYPositions.clear();

        int availableSpaceB = SEA_MAX_Y - SEA_MIN_Y;
        int avgSpacingB = availableSpaceB / numBeacons;

        for (int i = 0; i < numBeacons; i++) {
            int baseY = SEA_MIN_Y + (i * avgSpacingB);
            int yPos = baseY + random.nextInt(avgSpacingB);
            usedYPositions.add(yPos);

            int xPos = (WINDOW_WIDTH / numBeacons) * i + random.nextInt(WINDOW_WIDTH/numBeacons);
            Beacon.MovementPattern pattern = getRandomBeaconPattern(random);

            Beacon beacon = new Beacon(xPos, yPos, pattern);
            BeaconView beaconView = new BeaconView(beacon, satelliteList);

            beaconViews.add(beaconView);
            container.add(beaconView, 0);
        }

        space.openInWindow();

        // Boucle principale
        while (true) {
            for (BeaconView beaconView : beaconViews) {
                beaconView.getModel().update();
                beaconView.update();
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

    private static Beacon.MovementPattern getRandomBeaconPattern(Random random) {
        Beacon.MovementPattern[] patterns = {
                Beacon.MovementPattern.HORIZONTAL,
                Beacon.MovementPattern.STATIONARY,
                Beacon.MovementPattern.SINUSOIDAL
        };
        return patterns[random.nextInt(patterns.length)];
    }

    private static Satellite.MovementPattern getRandomSatellitePattern(Random random, int currentStationaryCount) {
        // Si on a atteint le maximum de satellites stationnaires, on ne peut plus en créer
        if (currentStationaryCount >= MAX_STATIONARY_SATELLITES) {
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