package v5;

import java.util.*;

public class Annonceur {
    private final Map<String, List<EcouteurEvenement>> ecouteurs = new HashMap<>();

    public void enregistrer(String typeEvenement, EcouteurEvenement ecouteur) {
        ecouteurs.computeIfAbsent(typeEvenement, k -> new ArrayList<>()).add(ecouteur);
    }

    public void desenregistrer(String typeEvenement, EcouteurEvenement ecouteur) {
        if (ecouteurs.containsKey(typeEvenement)) {
            ecouteurs.get(typeEvenement).remove(ecouteur);
            if (ecouteurs.get(typeEvenement).isEmpty()) {
                ecouteurs.remove(typeEvenement);
            }
        }
    }

    public void annoncer(Evenement evt) {
        if (ecouteurs.containsKey(evt.getType())) {
            for (EcouteurEvenement ecouteur : new ArrayList<>(ecouteurs.get(evt.getType()))) {
                ecouteur.recevoirEvenement(evt);
            }
        }
    }
}