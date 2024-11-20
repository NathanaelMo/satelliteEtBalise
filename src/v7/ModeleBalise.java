package v7;

import java.util.Timer;
import java.util.TimerTask;

public class ModeleBalise implements EcouteurEvenement {
    private Position position;
    private Deplacement deplacement;
    private boolean enSynchronisation = false;
    private final Announcer announcer;
    private boolean aNotifiePosition = false;
    private Timer timerRemontee;
    private final int intervalleRemontee; // en millisecondes


    // Variables pour la remontée
    private boolean enRemontee = false;
    private boolean enDescente = false;
    private int yOriginal;
    private int yCible;
    private static final int VITESSE_VERTICALE = 2;
    private static final int Y_SURFACE = 250;  // Juste sous la séparation ciel/mer
    private static final int Y_MIN = 350;      // Profondeur minimale
    private static final int Y_MAX = 550;      // Profondeur maximale

    public enum TypeDeplacement {
        HORIZONTAL, SINUSOIDAL, STATIQUE
    }

    public ModeleBalise(int x, int y, Deplacement deplacement,
                        int intervalleRemonteeSecondes, Controleur controleur) {
        this.position = new Position(x, y);
        this.yOriginal = y;
        this.deplacement=deplacement;
        this.intervalleRemontee = intervalleRemonteeSecondes * 1000; // conversion en millisecondes
        this.announcer = controleur.getAnnouncer();

        // S'enregistre pour recevoir les événements de synchronisation
        announcer.register(TypeEvenement.DEBUT_SYNC, this);
        announcer.register(TypeEvenement.FIN_SYNC, this);

        // Démarre le timer pour les remontées périodiques
        demarrerTimerRemontee();
    }

    @Override
    public void recevoirEvenement(Evenement evt) {
        if (evt.getType().equals(TypeEvenement.DEBUT_SYNC) && evt.getDonnees() == this) {
            demarrerSynchronisation();
        } else if (evt.getType().equals(TypeEvenement.FIN_SYNC) && evt.getDonnees() == this) {
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
            this.position=this.deplacement.deplacer(this.position);

            // Si à la surface, notifie sa position une seule fois
            if (estEnSurface() && !enSynchronisation && !aNotifiePosition) {
                aNotifiePosition = true;
                // La balise signale qu'elle est prête et envoie sa position
                announcer.announce(new Evenement(this, TypeEvenement.BALISE_PRETE,
                        new Controleur.Position(this.position.getX(), Y_SURFACE)));
            }
        }

        // Gestion des animations de remontée/descente
        if (enRemontee) {
            if (Math.abs(this.position.getY() - yCible) <= VITESSE_VERTICALE) {
                this.position.setY(yCible);
                enRemontee = false;
                enSurface = true;
            } else {
                this.position.setY(this.position.getY()-VITESSE_VERTICALE);
            }
        }

        if (enDescente) {
            if (Math.abs(this.position.getY() - yCible) <= VITESSE_VERTICALE) {
                this.position.setY(yCible);
                enDescente = false;
            } else {
                this.position.setY(this.position.getY()+VITESSE_VERTICALE);
            }
        }
    }


    // Getters
    public int getX() { return this.position.getX(); }
    public int getY() { return this.position.getY(); }
    public boolean estEnSynchronisation() { return enSynchronisation; }
    public boolean estEnSurface() { return enSurface; }

    // Variables d'état
    private boolean enSurface = false;
}