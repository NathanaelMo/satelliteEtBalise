package v4;

public class ModeleBalise {
    private int x, y;
    private int memoire;
    private static final int MEMOIRE_MAX = 100;
    private boolean enCollecte = true;
    private boolean enSurface = false;
    private final TypeDeplacement typeDeplacement;
    private boolean enSynchronisation = false;
    private Controleur controleur;

    // Variables pour la remontée
    private boolean enRemontee = false;
    private boolean enDescente = false;
    private boolean aNotifiePosition = false;
    private int yOriginal;
    private int yCible;
    private static final int VITESSE_VERTICALE = 2;
    private static final int Y_SURFACE = 320; // Position à la surface
    private static final int Y_MIN = 300;     // Limite haute
    private static final int Y_MAX = 500;     // Limite basse

    public enum TypeDeplacement {
        HORIZONTAL, VERTICAL, SINUSOIDAL, STATIONNAIRE
    }

    public ModeleBalise(int x, int y, TypeDeplacement typeDeplacement, Controleur controleur) {
        this.x = x;
        this.y = y;
        this.yOriginal = y;
        this.typeDeplacement = typeDeplacement;
        this.memoire = (int)(Math.random() * ((double) MEMOIRE_MAX / 2));
        this.controleur = controleur;
        controleur.ajouterBalise(this);
    }

    public void mettreAJour() {
        // Gestion de la collecte normale
        if (!enSynchronisation) {
            if (!enRemontee && !enDescente && enCollecte) {
                collecter();
                deplacer();
            }

            if (memoire >= MEMOIRE_MAX && !enSurface && !enRemontee) {
                demarrerRemontee();
            }

            // Si à la surface, notifie le contrôleur avec sa position
            if (enSurface && !enSynchronisation && !aNotifiePosition) {
                aNotifiePosition = true;
                controleur.notifierBalisePrete(this, x, Y_SURFACE);  // Utilise Y_SURFACE au lieu de y
            }
        }

        // Animation de remontée
        if (enRemontee) {
            if (Math.abs(y - yCible) <= VITESSE_VERTICALE) {
                y = yCible;
                enRemontee = false;
                enSurface = true;
            } else {
                y -= VITESSE_VERTICALE;
            }
        }

        // Animation de descente
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
            yOriginal = y;
            yCible = Y_SURFACE;
        }
    }

    public void demarrerDescente() {
        if (enSurface) {
            enSurface = false;
            enDescente = true;
            memoire = 0;
            yCible = yOriginal;
        }
    }

    public void demarrerSynchronisation() {
        if (!enSynchronisation && enSurface) {
            enSynchronisation = true;
        }
    }

    public void terminerSynchronisation() {
        enSynchronisation = false;
        aNotifiePosition = false;
        demarrerDescente();
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean estEnSurface() { return enSurface; }
    public boolean estEnRemontee() { return enRemontee; }
    public boolean estEnDescente() { return enDescente; }
    public int getMemoire() { return memoire; }
}
