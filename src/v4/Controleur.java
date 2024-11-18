package v4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controleur {
    private List<ModeleSatellite> satellites = new ArrayList<>();
    private List<ModeleBalise> balises = new ArrayList<>();

    // Map qui associe chaque balise en attente à sa position
    private Map<ModeleBalise, Position> balisesEnAttente = new HashMap<>();
    // Map pour les synchronisations en cours
    private Map<ModeleBalise, ModeleSatellite> synchronisationsEnCours = new HashMap<>();

    // Classe interne pour stocker une position
    static class Position {
        final int x, y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void ajouterSatellite(ModeleSatellite satellite) {
        satellites.add(satellite);
    }

    public void ajouterBalise(ModeleBalise balise) {
        balises.add(balise);
    }

    // Appelé par la balise quand elle est prête à synchroniser
    public void notifierBalisePrete(ModeleBalise balise, int x, int y) {
        if (!balisesEnAttente.containsKey(balise) && !synchronisationsEnCours.containsKey(balise)) {
            balisesEnAttente.put(balise, new Position(x, y));
        }
    }

    public Map<ModeleBalise, Position> getBalisesEnAttente() {
        return balisesEnAttente;
    }

    // Appelé par un satellite quand il est à portée d'une position de balise
    public void notifierSatellitePret(ModeleSatellite satellite) {
        // On cherche une balise en attente qui n'est pas déjà en synchronisation
        for (Map.Entry<ModeleBalise, Position> entry : new HashMap<>(balisesEnAttente).entrySet()) {
            ModeleBalise balise = entry.getKey();

            if (!synchronisationsEnCours.containsKey(balise)) {
                // Retire immédiatement la balise des balises en attente
                balisesEnAttente.remove(balise);
                // Ajoute la paire aux synchronisations en cours
                synchronisationsEnCours.put(balise, satellite);

                balise.demarrerSynchronisation();
                satellite.demarrerSynchronisation();

                // Démarre un timer pour cette synchronisation
                new Thread(() -> {
                    try {
                        Thread.sleep(2000); // 2 secondes de synchronisation
                        terminerSynchronisation(balise);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                break; // Sort de la boucle après avoir trouvé une balise
            }
        }
    }

    private void terminerSynchronisation(ModeleBalise balise) {
        ModeleSatellite satellite = synchronisationsEnCours.get(balise);
        if (satellite != null) {
            balise.terminerSynchronisation();
            satellite.terminerSynchronisation();
            synchronisationsEnCours.remove(balise);
        }
    }
}
