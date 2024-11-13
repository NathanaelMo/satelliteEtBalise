package claude;

public class Satellite implements MarineListener {
    private int x, y;
    private final int speed;
    private final boolean isGeosynchronous;
    private boolean isSynchronizing = false;
    private final MovementPattern pattern;
    private final int originalY;

    private static final int MIN_Y = 50;
    private static final int MAX_Y = 150;

    public enum MovementPattern {
        HORIZONTAL, SINUSOIDAL, STATIONARY
    }

    public Satellite(int x, int y, int speed, boolean isGeosynchronous, MovementPattern pattern) {
        this.x = x;
        this.y = y;
        this.originalY = y;
        this.speed = speed;
        this.isGeosynchronous = isGeosynchronous;
        this.pattern = pattern;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSynchronizing() {
        return isSynchronizing;
    }

    public void startSynchronization() {
        isSynchronizing = true;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                isSynchronizing = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void update() {
        if (!isSynchronizing && !isGeosynchronous) {
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
    public void onSynchronizationRequest(SynchronizationRequestEvent evt) {
        Beacon beacon = (Beacon)evt.getSource();
        beacon.startSync(this);
    }

    @Override
    public void onDataTransferComplete(DataTransferEvent evt) {
        isSynchronizing = false;
    }
}