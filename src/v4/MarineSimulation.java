// MarineSimulation.java
package v4;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarineSimulation {
    private static final int LARGEUR_FENETRE = 800;
    private static final int HAUTEUR_FENETRE = 600;
    private static final int Y_MIN_CIEL = 50;
    private static final int Y_MAX_CIEL = 250;
    private static final int Y_MIN_MER = 350;
    private static final int Y_MAX_MER = 550;

    public static void main(String[] args) {
        Random random = new Random();

        // Pour stocker les modèles à mettre à jour
        List<ModeleBalise> balises = new ArrayList<>();
        List<ModeleSatellite> satellites = new ArrayList<>();

        // Création de l'espace de simulation
        NiSpace espace = new NiSpace("Simulation Marine v4", new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        NiRectangle conteneur = new NiRectangle();
        conteneur.setLayout(null);
        conteneur.setSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        espace.add(conteneur);

        // Création du contrôleur
        Controleur controleur = new Controleur();

        // Création des satellites
        int nbSatellites = 4;
        for (int i = 0; i < nbSatellites; i++) {
            int x = (LARGEUR_FENETRE / nbSatellites) * i;
            int y = Y_MIN_CIEL + random.nextInt(Y_MAX_CIEL - Y_MIN_CIEL);

            int vitesse = random.nextInt(3) + 2;

            ModeleSatellite.TypeDeplacement typeDeplacement = ModeleSatellite.TypeDeplacement.values()[
                    random.nextInt(ModeleSatellite.TypeDeplacement.values().length)
                    ];

            ModeleSatellite modeleSatellite = new ModeleSatellite(x, y, vitesse, typeDeplacement, controleur);
            satellites.add(modeleSatellite);  // Ajoute le modèle à la liste

            VueSatellite vueSatellite = new VueSatellite(modeleSatellite);
            conteneur.add(vueSatellite);
        }

        // Création des balises
        int nbBalises = 4;
        for (int i = 0; i < nbBalises; i++) {
            int x = (LARGEUR_FENETRE / nbBalises) * i + random.nextInt(LARGEUR_FENETRE/nbBalises);
            int y = Y_MIN_MER + random.nextInt(Y_MAX_MER - Y_MIN_MER);

            ModeleBalise.TypeDeplacement typeDeplacement = ModeleBalise.TypeDeplacement.values()[
                    random.nextInt(ModeleBalise.TypeDeplacement.values().length)
                    ];

            ModeleBalise modeleBalise = new ModeleBalise(x, y, typeDeplacement, controleur);
            balises.add(modeleBalise);  // Ajoute le modèle à la liste

            VueBalise vueBalise = new VueBalise(modeleBalise);
            conteneur.add(vueBalise);
        }

        // Ouvre la fenêtre
        espace.openInWindow();

        // Boucle principale
        while (true) {
            // Mise à jour des modèles
            for (ModeleSatellite satellite : satellites) {
                satellite.mettreAJour();
            }
            for (ModeleBalise balise : balises) {
                balise.mettreAJour();
            }

            // Mise à jour des vues
            for (Component comp : conteneur.getComponents()) {
                if (comp instanceof Vue) {
                    ((Vue) comp).mettreAJour();
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            conteneur.repaint();
        }
    }
}