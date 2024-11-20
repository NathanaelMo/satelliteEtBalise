package v5;

import java.util.HashMap;
import java.util.Map;

public class Controleur {
    private final Annonceur annonceur = new Annonceur();
    private final Map<ModeleBalise, Position> balisesEnAttente = new HashMap<>();
    private final Map<ModeleBalise, ModeleSatellite> synchronisationsEnCours = new HashMap<>();

    public static class Position {
        final int x, y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Controleur() {
        // Écoute les positions des balises
        annonceur.enregistrer("POSITION_BALISE", evt -> {
            ModeleBalise balise = (ModeleBalise) evt.getSource();
            Position pos = (Position) evt.getData();
            if (!balisesEnAttente.containsKey(balise) && !synchronisationsEnCours.containsKey(balise)) {
                balisesEnAttente.put(balise, pos);
            }
        });

        // Écoute les positions des satellites
        annonceur.enregistrer("POSITION_SATELLITE", evt -> {
            ModeleSatellite satellite = (ModeleSatellite) evt.getSource();
            Position positionSatellite = (Position) evt.getData();
            verifierSynchronisationPossible(satellite, positionSatellite);
        });
    }

    private void verifierSynchronisationPossible(ModeleSatellite satellite, Position positionSatellite) {
        for (Map.Entry<ModeleBalise, Position> entry : new HashMap<>(balisesEnAttente).entrySet()) {
            ModeleBalise balise = entry.getKey();
            Position positionBalise = entry.getValue();

            if (!synchronisationsEnCours.containsKey(balise) &&
                    Math.abs(positionSatellite.x - positionBalise.x) <= 5) {

                balisesEnAttente.remove(balise);
                synchronisationsEnCours.put(balise, satellite);

                // Annonce le début de la synchronisation aux deux parties
                annonceur.annoncer(new Evenement(satellite, "DEBUT_SYNC_SATELLITE", null));
                annonceur.annoncer(new Evenement(balise, "DEBUT_SYNC_BALISE", null));

                // Timer pour la synchronisation
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        terminerSynchronisation(balise, satellite);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                break;
            }
        }
    }

    private void terminerSynchronisation(ModeleBalise balise, ModeleSatellite satellite) {
        if (synchronisationsEnCours.containsKey(balise)) {
            annonceur.annoncer(new Evenement(this, "FIN_SYNC", null));
            synchronisationsEnCours.remove(balise);
        }
    }

    public Annonceur getAnnonceur() {
        return annonceur;
    }
}