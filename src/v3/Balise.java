package v3;

public class Balise {
    private int x, y;
    private int memoire;
    private static final int MAX_MEMOIRE = 100;
    private boolean collecte = true;
    private boolean surface = false;
    final private MovementPattern pattern;
    final private Announcer announcer;
    private int originalY;
    private static final int MIN_Y = 300;
    private static final int MAX_Y = 500;
    private static final int SURFACE_SPEED = 2; // Vitesse de remontée/descente
    private boolean goToSurface = false;
    private boolean goToFond = false;
    private int targetY;
    private boolean synchronisation = false;
    private Satellite satelliteCourant = null;

    // Enum déplacé à l'intérieur de la classe
    public enum MovementPattern {
        HORIZONTAL, VERTICAL, SINUSOIDAL, STATIONARY
    }

    public Balise(int x, int y, MovementPattern pattern) {
        this.x = x;
        this.y = y;
        this.originalY = y;
        this.pattern = pattern;
        this.announcer = new Announcer();
        this.memoire = (int)(Math.random() * ((double) MAX_MEMOIRE / 2));
    }

    public void checkSatellite(Satellite satellite) {
        int detectionRange = 5;
        if (surface && !synchronisation) {  // Vérifie si la balise n'est pas déjà en synchronisation
            int diffX = Math.abs(satellite.getX() - this.x);
            if (diffX <= detectionRange) {
                // On passe maintenant le satellite ciblé dans l'événement
                announcer.announce(new SynchronizationRequestEvent(this, satellite));
            }
        }
    }


    public void register(Object o) {
        announcer.register(o, SynchronizationRequestEvent.class);
        announcer.register(o, DataTransferEvent.class);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSurface() {
        return surface;
    }

    public void update() {
        // Gestion de la collecte normale
        if (!goToSurface && !goToFond && collecte) {
            collecte();
            move();
        }

        // Déclenche la remontée si la mémoire est pleine
        if (memoire >= MAX_MEMOIRE && !surface && !goToSurface) {
            surfaceForSync();
        }

        // Animation de remontée
        if (goToSurface) {
            if (Math.abs(y - targetY) <= SURFACE_SPEED) {
                y = targetY;
                goToSurface = false;
                surface = true;
            } else {
                y -= SURFACE_SPEED; // Remonte progressivement
            }
        }

        // Animation de descente
        if (goToFond) {
            if (Math.abs(y - targetY) <= SURFACE_SPEED) {
                y = targetY;
                goToFond = false;
                collecte = true;
            } else {
                y += SURFACE_SPEED; // Descend progressivement
            }
        }
    }

    private void collecte() {
        if (!surface) {
            memoire++;
            if (memoire > MAX_MEMOIRE) {
                memoire = MAX_MEMOIRE;
            }
        }
    }

    private void surfaceForSync() {
        if (!goToSurface && !surface) {
            goToSurface = true;
            originalY = y;
            targetY = MIN_Y + 20; // Position de surface
        }
    }

    public void startSync(Satellite satellite) {
        if (!satellite.synchronisation() && !synchronisation) {  // Vérifie si ni le satellite ni la balise ne sont en synchronisation
            synchronisation = true;
            satelliteCourant = satellite;
            satellite.startSynchro();
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    completeSync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void completeSync() {
        memoire = 0;
        surface = false;
        goToFond = true;
        collecte = false;
        synchronisation = false;
        satelliteCourant = null;
        targetY = originalY;
        announcer.announce(new DataTransferEvent(this));
    }
    private void move() {
        if (surface) return;

        switch (pattern) {
            case HORIZONTAL:
                x = (x + 2) % 800;
                break;
            case VERTICAL:
                y += 2;
                if (y > MAX_Y) y = MIN_Y;
                break;
            case SINUSOIDAL:
                x = (x + 2) % 800;
                y = (MIN_Y + MAX_Y) / 2 + (int)(50 * Math.sin(x * 0.05));
                break;
            case STATIONARY:
                break;
        }
    }

    public boolean isGoToSurface() {
        return goToSurface;
    }

    public boolean isGoToFond() {
        return goToFond;
    }
}