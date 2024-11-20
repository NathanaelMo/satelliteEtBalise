// ModeleSatellite.java
package v5;

import java.util.Map;

public class ModeleSatellite implements EcouteurEvenement {
    private int x, y;
    private final int vitesse;
    private boolean enSynchronisation;
    private final TypeDeplacement typeDeplacement;
    private final Controleur controleur;
    private Controleur.Position positionCible = null;
    private int precedentX; // Pour détecter quand on passe au-dessus de la balise
    private static final int MARGE_DETECTION = 5;  // Zone de détection étroite
    private final Annonceur annonceur;

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
        this.annonceur = controleur.getAnnonceur();
        this.annonceur.enregistrer("BALISE_DISPONIBLE", this);
        this.annonceur.enregistrer("DEBUT_SYNC_SATELLITE", this);
        this.annonceur.enregistrer("FIN_SYNC", this);
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
            verifierPosition();
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

    @Override
    public void recevoirEvenement(Evenement evt) {
        if (evt.getType().equals("BALISE_DISPONIBLE") && !enSynchronisation && positionCible == null) {
            // Reçoit uniquement la position de la balise disponible
            positionCible = (Controleur.Position) evt.getData();
        } else if (evt.getType().equals("DEBUT_SYNC_SATELLITE") && evt.getSource() == this) {
            demarrerSynchronisation();
        } else if (evt.getType().equals("FIN_SYNC")) {
            terminerSynchronisation();
        }
    }


    private void verifierPosition() {
        if (!enSynchronisation) {
            // Notifie sa position
            annonceur.annoncer(new Evenement(this, "POSITION_SATELLITE",
                    new Controleur.Position(x, y)));
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean estEnSynchronisation() { return enSynchronisation; }
}