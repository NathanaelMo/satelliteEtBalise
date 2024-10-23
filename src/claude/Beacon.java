package claude;

public class Beacon {
    private int x, y;
    private int memory;
    private static final int MAX_MEMORY = 100;
    private boolean isCollecting = true;
    private boolean isSurfaced = false;
    final private MovementPattern pattern;
    final private Announcer announcer;
    private int originalY;
    private static final int MIN_Y = 300;
    private static final int MAX_Y = 500;
    private static final int SURFACE_SPEED = 2; // Vitesse de remontée/descente
    private boolean isMovingToSurface = false;
    private boolean isMovingToDepth = false;
    private int targetY;
    private static final int SYNC_RANGE_X = 800; // Zone de détection horizontale

    // Enum déplacé à l'intérieur de la classe
    public enum MovementPattern {
        HORIZONTAL, VERTICAL, SINUSOIDAL, STATIONARY
    }

    public Beacon(int x, int y, MovementPattern pattern) {
        this.x = x;
        this.y = y;
        this.originalY = y;
        this.pattern = pattern;
        this.announcer = new Announcer();
        this.memory = (int)(Math.random() * ((double) MAX_MEMORY / 2));
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

    public boolean isSurfaced() {
        return isSurfaced;
    }

    public void update() {
        // Gestion de la collecte normale
        if (!isMovingToSurface && !isMovingToDepth && isCollecting) {
            collectData();
            move();
        }

        // Déclenche la remontée si la mémoire est pleine
        if (memory >= MAX_MEMORY && !isSurfaced && !isMovingToSurface) {
            surfaceForSync();
        }

        // Animation de remontée
        if (isMovingToSurface) {
            if (Math.abs(y - targetY) <= SURFACE_SPEED) {
                y = targetY;
                isMovingToSurface = false;
                isSurfaced = true;
            } else {
                y -= SURFACE_SPEED; // Remonte progressivement
            }
        }

        // Animation de descente
        if (isMovingToDepth) {
            if (Math.abs(y - targetY) <= SURFACE_SPEED) {
                y = targetY;
                isMovingToDepth = false;
                isCollecting = true;
            } else {
                y += SURFACE_SPEED; // Descend progressivement
            }
        }
    }

    private void collectData() {
        if (!isSurfaced) {
            memory++;
            if (memory > MAX_MEMORY) {
                memory = MAX_MEMORY;
            }
        }
    }

    private void surfaceForSync() {
        if (!isMovingToSurface && !isSurfaced) {
            isMovingToSurface = true;
            originalY = y;
            targetY = MIN_Y + 20; // Position de surface
        }
    }

    public void checkForSatellite(Satellite satellite) {
        // On vérifie uniquement si le satellite est aligné verticalement (même X).
        if (isSurfaced && (Math.abs(satellite.getX()) == this.x)) {
            int diffX = Math.abs(satellite.getX() - this.x);
            System.out.println("Difference X: " + diffX +
                    " (Satellite X: " + satellite.getX() +
                    ", Balise X: " + this.x + ")");
            announcer.announce(new SynchronizationRequestEvent(this));
        }
    }


    public void startSync(Satellite satellite) {
        if (!satellite.isSynchronizing()) {
            satellite.startSynchronization();
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
        memory = 0;
        isSurfaced = false;
        isMovingToDepth = true;
        isCollecting = false;
        targetY = originalY;
        announcer.announce(new DataTransferEvent(this));
    }

    private void move() {
        if (isSurfaced) return;

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

    public boolean isMovingToSurface() {
        return isMovingToSurface;
    }

    public boolean isMovingToDepth() {
        return isMovingToDepth;
    }
}