package v4;

public class Satellite implements MarineListener {
    private int x, y;
    private final int speed;
    private final boolean estSynchro;
    private boolean synchronisation = false;
    private final MovementPattern pattern;
    private final int originalY;

    private static final int MIN_Y = 50;
    private static final int MAX_Y = 150;

    public enum MovementPattern {
        HORIZONTAL, SINUSOIDAL, STATIONARY
    }

    public Satellite(int x, int y, int speed, boolean estSynchro, MovementPattern pattern) {
        this.x = x;
        this.y = y;
        this.originalY = y;
        this.speed = speed;
        this.estSynchro = estSynchro;
        this.pattern = pattern;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean synchronisation() {
        return synchronisation;
    }

    public void startSynchro() {
        synchronisation = true;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                synchronisation = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void update() {
        if (!synchronisation && !estSynchro) {
            move();
        }
    }

    private void move() {
        switch (pattern) {
            case HORIZONTAL:
                x = (x + speed) % 800;
                break;
            case SINUSOIDAL:
                x = (x + speed) % 800;
                y = (MIN_Y + MAX_Y) / 2 + (int)(30 * Math.sin(x * 0.05));
                break;
            case STATIONARY:
                break;
        }
    }

    @Override
    public void onSynchroRequest(SynchronizationRequestEvent evt) {
        Balise balise = (Balise)evt.getSource();
        balise.startSync(this);
    }

    @Override
    public void echangeFini(DataTransferEvent evt) {
        synchronisation = false;
    }
}