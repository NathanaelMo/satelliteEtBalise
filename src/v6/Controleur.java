package v6;

import java.util.*;

public class Controleur implements EcouteurEvenement {
    private final Diffuseur diffuseur = new Diffuseur();
    private final Map<ModeleBalise, Position> balisesEnAttente = new HashMap<>();
    private final Map<ModeleSatellite, Position> positionsSatellites = new HashMap<>();

    // Pour suivre les synchronisations en cours
    private final Map<ModeleBalise, ModeleSatellite> synchronisationsBalises = new HashMap<>();
    private final Map<ModeleSatellite, ModeleBalise> synchronisationsSatellites = new HashMap<>();

    private static final int MARGE_DETECTION = 5;

    public static class Position {
        final int x, y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Controleur() {
        // Écoute les positions des balises et satellites
        diffuseur.enregistrer("POSITION_SATELLITE", this);
        diffuseur.enregistrer("BALISE_PRETE", this);
    }

    @Override
    public void recevoirEvenement(Evenement evt) {
        switch (evt.getType()) {
            case "POSITION_SATELLITE":
                mettreAJourPositionSatellite((ModeleSatellite)evt.getSource(),
                        (Position)evt.getDonnees());
                break;
            case "BALISE_PRETE":
                gererBalisePrete((ModeleBalise)evt.getSource(),
                        (Position)evt.getDonnees());
                break;
        }
    }

    private void mettreAJourPositionSatellite(ModeleSatellite satellite, Position position) {
        positionsSatellites.put(satellite, position);
        verifierSynchronisationsPossibles(satellite);
    }

    private void gererBalisePrete(ModeleBalise balise, Position position) {
        if (!synchronisationsBalises.containsKey(balise)) {
            balisesEnAttente.put(balise, position);
        }
    }

    private void verifierSynchronisationsPossibles(ModeleSatellite satellite) {
        // Si le satellite est déjà en synchronisation, on ignore
        if (synchronisationsSatellites.containsKey(satellite)) {
            return;
        }

        Position posSatellite = positionsSatellites.get(satellite);
        if (posSatellite == null) return;

        for (Map.Entry<ModeleBalise, Position> entry : new HashMap<>(balisesEnAttente).entrySet()) {
            ModeleBalise balise = entry.getKey();
            Position posBalise = entry.getValue();

            // Vérifie que ni la balise ni le satellite ne sont déjà en synchronisation
            if (!synchronisationsBalises.containsKey(balise) &&
                    !synchronisationsSatellites.containsKey(satellite)) {

                // Vérifie l'alignement
                if (Math.abs(posSatellite.x - posBalise.x) <= MARGE_DETECTION) {
                    demarrerSynchronisation(balise, satellite);
                    break;
                }
            }
        }
    }

    private void demarrerSynchronisation(ModeleBalise balise, ModeleSatellite satellite) {
        // Retire la balise des balises en attente
        balisesEnAttente.remove(balise);

        // Enregistre la synchronisation
        synchronisationsBalises.put(balise, satellite);
        synchronisationsSatellites.put(satellite, balise);

        // Notifie les deux parties
        diffuseur.diffuserCible(new Evenement(this, Evenement.DEBUT_SYNC, satellite), satellite);
        diffuseur.diffuserCible(new Evenement(this, Evenement.DEBUT_SYNC, balise), balise);

        // Programme la fin de la synchronisation
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                terminerSynchronisation(balise, satellite);
            }
        }, 2000);
    }

    private void terminerSynchronisation(ModeleBalise balise, ModeleSatellite satellite) {
        // Retire les enregistrements de synchronisation
        synchronisationsBalises.remove(balise);
        synchronisationsSatellites.remove(satellite);

        // Notifie les deux parties
        diffuseur.diffuserCible(new Evenement(this, Evenement.FIN_SYNC, satellite), satellite);
        diffuseur.diffuserCible(new Evenement(this, Evenement.FIN_SYNC, balise), balise);
    }

    public Diffuseur getDiffuseur() {
        return diffuseur;
    }
}