package v5;

public class ModeleBalise implements EcouteurEvenement {
    private int x, y;
    private int memoire;
    private static final int MEMOIRE_MAX = 100;
    private boolean enCollecte = true;
    private boolean enSurface = false;
    private final TypeDeplacement typeDeplacement;
    private boolean enSynchronisation = false;
    private final Annonceur annonceur;
    private boolean aNotifiePosition = false;


    // Variables pour la remontée
    private boolean enRemontee = false;
    private boolean enDescente = false;
    private int yOriginal;
    private int yCible;
    private static final int VITESSE_VERTICALE = 2;
    private static final int Y_SURFACE = 320;
    private static final int Y_MIN = 300;
    private static final int Y_MAX = 500;

    public enum TypeDeplacement {
        HORIZONTAL, VERTICAL, SINUSOIDAL, STATIONNAIRE
    }

    public ModeleBalise(int x, int y, TypeDeplacement typeDeplacement, Controleur controleur) {
        this.x = x;
        this.y = y;
        this.yOriginal = y;
        this.typeDeplacement = typeDeplacement;
        this.memoire = (int)(Math.random() * ((double) MEMOIRE_MAX / 2));
        this.annonceur = controleur.getAnnonceur();
        this.annonceur.enregistrer("DEBUT_SYNC_BALISE", this);
        this.annonceur.enregistrer("FIN_SYNC", this);
    }

    @Override
    public void recevoirEvenement(Evenement evt) {
        if (evt.getType().equals("DEBUT_SYNC_BALISE") && evt.getSource() == this) {
            demarrerSynchronisation();
        } else if (evt.getType().equals("FIN_SYNC")) {
            terminerSynchronisation();
        }
    }

    private void collecter() {
        if (enCollecte && !enSurface) {
            memoire++;
            if (memoire > MEMOIRE_MAX) {
                memoire = MEMOIRE_MAX;
            }
        }
    }

    private void demarrerRemontee() {
        if (!enRemontee && !enSurface) {
            enRemontee = true;
            yCible = Y_SURFACE;
        }
    }

    private void demarrerDescente() {
        if (enSurface) {
            enSurface = false;
            enDescente = true;
            memoire = 0;
            yCible = yOriginal;
        }
    }

    private void demarrerSynchronisation() {
        if (!enSynchronisation && enSurface) {
            enSynchronisation = true;
        }
    }

    private void terminerSynchronisation() {
        enSynchronisation = false;
        aNotifiePosition = false;
        demarrerDescente();
    }

    public void mettreAJour() {
        if (!enSynchronisation) {
            if (!enRemontee && !enDescente && enCollecte) {
                collecter();
                deplacer();
            }

            if (memoire >= MEMOIRE_MAX && !enSurface && !enRemontee) {
                demarrerRemontee();
            }

            // Si à la surface, notifie sa position
            if (enSurface && !enSynchronisation && !aNotifiePosition) {
                annonceur.annoncer(new Evenement(this, "BALISE_PRETE",
                        new Controleur.Position(x, Y_SURFACE)));
                aNotifiePosition = true;
            }
        }

        // Gestion des animations de remontée/descente
        if (enRemontee) {
            if (Math.abs(y - yCible) <= VITESSE_VERTICALE) {
                y = yCible;
                enRemontee = false;
                enSurface = true;
            } else {
                y -= VITESSE_VERTICALE;
            }
        }

        if (enDescente) {
            if (Math.abs(y - yCible) <= VITESSE_VERTICALE) {
                y = yCible;
                enDescente = false;
                enCollecte = true;
            } else {
                y += VITESSE_VERTICALE;
            }
        }
    }

    private void deplacer() {
        if (!enSurface && !enRemontee && !enDescente) {
            switch (typeDeplacement) {
                case HORIZONTAL:
                    x = (x + 2) % 800;
                    break;
                case VERTICAL:
                    y = Math.max(Y_MIN, Math.min(Y_MAX, y + 2));
                    if (y >= Y_MAX) y = Y_MIN;
                    break;
                case SINUSOIDAL:
                    x = (x + 2) % 800;
                    y = (Y_MIN + Y_MAX) / 2 + (int)(50 * Math.sin(x * 0.05));
                    break;
                case STATIONNAIRE:
                    break;
            }
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean estEnSynchronisation() { return enSynchronisation; }
    public boolean estEnSurface() { return enSurface; }
}
