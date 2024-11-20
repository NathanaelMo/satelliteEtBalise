package v7;

public class ModeleSatellite implements EcouteurEvenement {
    private int x, y;
    private final int vitesse;
    private boolean enSynchronisation = false;
    private final Diffuseur diffuseur;

    public ModeleSatellite(int x, int y, int vitesse, Controleur controleur) {
        this.x = x;
        this.y = y;
        this.vitesse = vitesse;
        this.diffuseur = controleur.getDiffuseur();

        // S'enregistre uniquement pour les événements de synchronisation
        diffuseur.enregistrer(Evenement.DEBUT_SYNC, this);
        diffuseur.enregistrer(Evenement.FIN_SYNC, this);
    }

    @Override
    public void recevoirEvenement(Evenement evt) {
        switch (evt.getType()) {
            case Evenement.DEBUT_SYNC:
                if (evt.getDonnees() == this) {
                    enSynchronisation = true;
                }
                break;
            case Evenement.FIN_SYNC:
                if (this == evt.getDonnees()) {
                    enSynchronisation = false;
                }
                break;
        }
    }

    public void mettreAJour() {
        // Le satellite continue toujours son mouvement
        deplacer();
        // Envoie sa position au contrôleur
        diffuseur.diffuser(new Evenement(this, "POSITION_SATELLITE",
                new Controleur.Position(x, y)));
    }

    private void deplacer() {
        x = (x + vitesse) % 800;  // Mouvement horizontal uniquement
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean estEnSynchronisation() { return enSynchronisation; }
}