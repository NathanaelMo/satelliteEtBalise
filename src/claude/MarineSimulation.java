package claude;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import java.awt.*;
import java.util.ArrayList;

public class MarineSimulation {
    public static void main(String[] args) {
        NiSpace space = new NiSpace("Marine Simulation", new Dimension(800, 600));
        NiRectangle container = new NiRectangle();
        container.setLayout(null);
        container.setSize(new Dimension(800, 600));
        space.add(container);
        ArrayList<Satellite> list = new ArrayList<>();

        MarineSky sky = new MarineSky(800, 320);
        sky.setLocation(0, 0);
        container.add(sky, 0);

        MarineSea sea = new MarineSea(800, 300);
        sea.setLocation(0, 320);
        container.add(sea, 0);

        // Création des satellites
        Satellite satellite1 = new Satellite(0, 50, 3, false);
        SatelliteView satelliteView1 = new SatelliteView(satellite1);
        container.add(satelliteView1, 1);
        list.add(satellite1);


        Satellite satellite2 = new Satellite(100, 50, 3, false);
        SatelliteView satelliteView2 = new SatelliteView(satellite2);
        container.add(satelliteView2,1);
        list.add(satellite2);

        Satellite satellite3 = new Satellite(250, 50, 3, false);
        SatelliteView satelliteView3 = new SatelliteView(satellite3);
        container.add(satelliteView3,1);
        list.add(satellite3);

        // Création des balises
        Beacon beacon1 = new Beacon(0, 350, Beacon.MovementPattern.HORIZONTAL);
        Beacon beacon2 = new Beacon(200, 400, Beacon.MovementPattern.SINUSOIDAL);
        BeaconView beaconView1 = new BeaconView(beacon1, list);
        BeaconView beaconView2 = new BeaconView(beacon2, list);

        container.add(beaconView1, 0);
        container.add(beaconView2, 0);

        space.openInWindow();

        while (true) {
            beacon1.update();
            beacon2.update();
            satellite1.update();
            satellite2.update();
            satellite3.update();

            beaconView1.update();
            beaconView2.update();
            satelliteView1.update();
            satelliteView2.update();
           satelliteView3.update();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            container.repaint();
        }
    }
}