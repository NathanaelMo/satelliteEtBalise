package v7;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationMarine {
    private static final int LARGEUR_FENETRE = 900;
    private static final int HAUTEUR_FENETRE = 700;
    private static final int Y_MIN_CIEL = 10;   // Satellites
    private static final int Y_MAX_CIEL = 75;
    private static final int Y_MIN_MER = 400;   // Balises
    private static final int Y_MAX_MER = 650;

    public static void main(String[] args) throws IOException {
        Random random = new Random();

        // Pour stocker les modèles à mettre à jour
        List<ModeleBalise> balises = new ArrayList<>();
        List<ModeleSatellite> satellites = new ArrayList<>();

        // Création de l'espace de simulation
        NiSpace espace = new NiSpace("Simulation Marine v6", new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        NiRectangle conteneur = new NiRectangle();
        conteneur.setLayout(null);
        conteneur.setSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        espace.add(conteneur);

        // Création du contrôleur
        Controleur controleur = new Controleur();

        // Ajout du ciel et de la mer
        VueCiel ciel = new VueCiel(LARGEUR_FENETRE, HAUTEUR_FENETRE/2);
        conteneur.add(ciel, -1);

        VueMer mer = new VueMer(LARGEUR_FENETRE, HAUTEUR_FENETRE/2);
        mer.setLocation(0, HAUTEUR_FENETRE/2);
        conteneur.add(mer, -1);

        // Création des satellites avec différentes vitesses
        int nbSatellites = 4;
        for (int i = 0; i < nbSatellites; i++) {
            int x = (LARGEUR_FENETRE / nbSatellites) * i;
            int y = Y_MIN_CIEL + random.nextInt(Y_MAX_CIEL - Y_MIN_CIEL);
            int vitesse = random.nextInt(3) + 2; // Vitesse entre 2 et 4

            ModeleSatellite modeleSatellite = new ModeleSatellite(x, y, vitesse, controleur);
            satellites.add(modeleSatellite);

            VueSatellite vueSatellite = new VueSatellite(modeleSatellite);
            conteneur.add(vueSatellite, 0);
        }

        // Création des balises
        int nbBalises = 4;
        for (int i = 0; i < nbBalises; i++) {
            int x = (LARGEUR_FENETRE / nbBalises) * i + random.nextInt(LARGEUR_FENETRE / nbBalises);
            int y = Y_MIN_MER + random.nextInt(Y_MAX_MER - Y_MIN_MER);

            // Intervalle de remontée aléatoire entre 5 et 15 secondes
            int intervalleRemontee = 5 + random.nextInt(11);  // 5 à 15 secondes

            // Sélection aléatoire du type de déplacement
            ModeleBalise.TypeDeplacement[] types = ModeleBalise.TypeDeplacement.values();
            int deplacementAleatoire = (int) (Math.random() * 3) + 1;
            Deplacement deplacement=null;
            if (deplacementAleatoire==1){
                deplacement = new Sinusoidale();
            }else if (deplacementAleatoire==2){
                deplacement = new Horizontal();
            }else {
                deplacement = new Stationnaire();
            }

            ModeleBalise modeleBalise = new ModeleBalise(x, y, deplacement, intervalleRemontee, controleur);
            balises.add(modeleBalise);

            VueBalise vueBalise = new VueBalise(modeleBalise);
            conteneur.add(vueBalise, 1);
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