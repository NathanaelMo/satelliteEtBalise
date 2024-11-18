// ModeleSatellite.java
package v4;

import java.util.Map;

public class ModeleSatellite {
    private int x, y;
    private final int vitesse;
    private boolean enSynchronisation;
    private final TypeDeplacement typeDeplacement;
    private final Controleur controleur;
    private int precedentX; // Pour détecter quand on passe au-dessus de la balise
    private static final int MARGE_DETECTION = 5;  // Zone de détection étroite


    public enum TypeDeplacement {
        HORIZONTAL, SINUSOIDAL, STATIONNAIRE
    }

    public ModeleSatellite(int x, int y, int vitesse, TypeDeplacement typeDeplacement, Controleur controleur) {
        this.x = x;
        this.precedentX = x;
        this.y = y;
        this.vitesse = vitesse;
        this.typeDeplacement = typeDeplacement;
        this.controleur = controleur;
        this.enSynchronisation = false;
        controleur.ajouterSatellite(this);
    }

    private void verifierSynchronisationAvecBalises() {
        if (enSynchronisation) return;

        Map<ModeleBalise, Controleur.Position> balisesEnAttente = controleur.getBalisesEnAttente();

        for (Map.Entry<ModeleBalise, Controleur.Position> entry : balisesEnAttente.entrySet()) {
            Controleur.Position pos = entry.getValue();
            // Vérifie si le satellite est exactement au-dessus (ou presque) de la balise
            int diffX = Math.abs(x - pos.x);
            if (diffX <= MARGE_DETECTION) {
                controleur.notifierSatellitePret(this);
                break;  // On ne synchronise qu'avec une balise à la fois
            }
        }
    }

    public void demarrerSynchronisation() {
        enSynchronisation = true;
    }

    public void terminerSynchronisation() {
        enSynchronisation = false;
    }

    public void mettreAJour() {
        if (!enSynchronisation) {
            deplacer();
            verifierSynchronisationAvecBalises();
        }
    }

    private void deplacer() {
        switch (typeDeplacement) {
            case HORIZONTAL:
                x = (x + vitesse) % 800;
                break;
            case SINUSOIDAL:
                x = (x + vitesse) % 800;
                y = 100 + (int)(30 * Math.sin(x * 0.05));
                break;
            case STATIONNAIRE:
                break;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean estEnSynchronisation() { return enSynchronisation; }
}