package claude;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

import java.awt.*;

public class MarineSimulation {
    public static void main(String[] args) {
        NiSpace space = new NiSpace("Marine Simulation", new Dimension(800, 600));
        NiRectangle container = new NiRectangle();
        container.setBackground(Color.lightGray);
        container.setSize(new Dimension(800, 600));
        space.add(container);

        // Création des satellites d'abord
        Satellite satellite1 = new Satellite(0, 50, 3, false);
        SatelliteView satelliteView1 = new SatelliteView(satellite1);
        container.add(satelliteView1);

        // Puis création des balises avec référence aux satellites
        Beacon beacon1 = new Beacon(0, 350, Beacon.MovementPattern.HORIZONTAL);
        Beacon beacon2 = new Beacon(200, 400, Beacon.MovementPattern.SINUSOIDAL);
        BeaconView beaconView1 = new BeaconView(beacon1, satellite1);
        BeaconView beaconView2 = new BeaconView(beacon2, satellite1);

        container.add(beaconView1);
        container.add(beaconView2);

        space.openInWindow();

        while (true) {
            // Update models and views
            beacon1.update();
            beacon2.update();
            satellite1.update();

            beaconView1.update();
            beaconView2.update();
            satelliteView1.update();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            container.repaint();
        }
    }
}