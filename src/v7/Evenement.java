package v7;

public class Evenement {
    private final Object source;
    private final TypeEvenement type;
    private final Object donnees;

    public Evenement(Object source, TypeEvenement type, Object donnees) {
        this.source = source;
        this.type = type;
        this.donnees = donnees;
    }

    public Object getSource() {
        return source;
    }

    public TypeEvenement getType() {
        return type;
    }

    public Object getDonnees() {
        return donnees;
    }
}
