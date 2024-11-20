package v7;

public class Evenement {
    private final Object source;
    private final String type;
    private final Object donnees;

    public Evenement(Object source, String type, Object donnees) {
        this.source = source;
        this.type = type;
        this.donnees = donnees;
    }

    public Object getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public Object getDonnees() {
        return donnees;
    }

    // Types d'événements constants pour éviter les erreurs de frappe
    public static final String BALISE_PRETE = "BALISE_PRETE";
    public static final String DIFFUSION_POSITION_BALISE = "DIFFUSION_POSITION_BALISE";
    public static final String SATELLITE_PRET = "SATELLITE_PRET";
    public static final String DEBUT_SYNC = "DEBUT_SYNC";
    public static final String FIN_SYNC = "FIN_SYNC";
}
