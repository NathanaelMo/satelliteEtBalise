package v6;

import java.util.Timer;
import java.util.TimerTask;

public class ModeleBalise implements EcouteurEvenement {
    private int x, y;
    private final TypeDeplacement typeDeplacement;
    private boolean enSynchronisation = false;
    private final Diffuseur diffuseur;
    private boolean aNotifiePosition = false;
    private Timer timerRemontee;
    private final int intervalleRemontee; // en millisecondes


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
        HORIZONTAL
    }

    public ModeleBalise(int x, int y, TypeDeplacement typeDeplacement,
                        int intervalleRemonteeSecondes, Controleur controleur) {
        this.x = x;
        this.y = y;
        this.yOriginal = y;
        this.typeDeplacement = typeDeplacement;
        this.intervalleRemontee = intervalleRemonteeSecondes * 1000; // conversion en millisecondes
        this.diffuseur = controleur.getDiffuseur();

        // S'enregistre pour recevoir les événements de synchronisation
        diffuseur.enregistrer(Evenement.DEBUT_SYNC, this);
        diffuseur.enregistrer(Evenement.FIN_SYNC, this);

        // Démarre le timer pour les remontées périodiques
        demarrerTimerRemontee();
    }

    @Override
    public void recevoirEvenement(Evenement evt) {
        if (evt.getType().equals(Evenement.DEBUT_SYNC) && evt.getDonnees() == this) {
            demarrerSynchronisation();
        } else if (evt.getType().equals(Evenement.FIN_SYNC) && evt.getDonnees() == this) {
            terminerSynchronisation();
        }
    }

    private void demarrerTimerRemontee() {
        timerRemontee = new Timer(true);
        timerRemontee.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!enSynchronisation && !enRemontee && !enDescente && !estEnSurface()) {
                    demarrerRemontee();
                }
            }
        }, intervalleRemontee, intervalleRemontee);
    }

    private void demarrerRemontee() {
        if (!enRemontee && !enSurface) {
            enRemontee = true;
            yCible = Y_SURFACE;
        }
    }

    private void demarrerDescente() {
        if (estEnSurface()) {
            enSurface = false;
            enDescente = true;
            yCible = yOriginal;
            aNotifiePosition = false;
        }
    }

    private void demarrerSynchronisation() {
        if (!enSynchronisation && estEnSurface()) {
            enSynchronisation = true;
        }
    }

    private void terminerSynchronisation() {
        if (enSynchronisation) {
            enSynchronisation = false;
            demarrerDescente();
        }
    }

    public void mettreAJour() {
        if (!enSynchronisation) {
            deplacer();

            // Si à la surface, notifie sa position une seule fois
            if (estEnSurface() && !enSynchronisation && !aNotifiePosition) {
                aNotifiePosition = true;
                diffuseur.diffuser(new Evenement(this, "BALISE_PRETE",
                        new Controleur.Position(x, Y_SURFACE)));
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
            } else {
                y += VITESSE_VERTICALE;
            }
        }
    }

    private void deplacer() {
        if (!estEnSurface() && !enRemontee && !enDescente) {
            x = (x + 2) % 800; // Mouvement horizontal uniquement
        }
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean estEnSynchronisation() { return enSynchronisation; }
    public boolean estEnSurface() { return enSurface; }

    // Variables d'état
    private boolean enSurface = false;
}