package v7;

public interface Deplacement {
    public static final int Y_SURFACE = 250;  // Juste sous la séparation ciel/mer
    public static final int Y_MIN = 350;      // Profondeur minimale
    public static final int Y_MAX = 550;      // Profondeur maximale

    public Position deplacer(Position position);
}
