package claude;

public class Satellite implements MarineListener {
    private int x, y;
    private final int speed;
    private final boolean isGeosynchronous;
    private boolean isSynchronizing = false;

    // Constantes pour les limites de mouvement des satellites
    private static final int MIN_Y = 50;
    private static final int MAX_Y = 150;

    public Satellite(int x, int y, int speed, boolean isGeosynchronous) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.isGeosynchronous = isGeosynchronous;
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
            x = (x + speed) % 800;
            // Mouvement légèrement ondulé pour les satellites
            y = MIN_Y + (int)(20 * Math.sin(x * 0.02));
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