package v7;

import java.util.*;

public class Announcer {
    // Map qui associe chaque type d'événement à sa liste d'écouteurs
    private final Map<TypeEvenement, List<EcouteurEvenement>> ecouteurs = new HashMap<>();

    /**
     * Enregistre un écouteur pour un type d'événement spécifique
     */
    public void register(TypeEvenement typeEvenement, EcouteurEvenement ecouteur) {
        ecouteurs.computeIfAbsent(typeEvenement, k -> new ArrayList<>()).add(ecouteur);
    }

    /**
     * Désenregistre un écouteur pour un type d'événement spécifique
     */
    public void unregister(TypeEvenement typeEvenement, EcouteurEvenement ecouteur) {
        if (ecouteurs.containsKey(typeEvenement)) {
            ecouteurs.get(typeEvenement).remove(ecouteur);
            if (ecouteurs.get(typeEvenement).isEmpty()) {
                ecouteurs.remove(typeEvenement);
            }
        }
    }

    /**
     * Diffuse un événement ponctuel à tous les écouteurs concernés
     */
    public void announce(Evenement evt) {
        if (ecouteurs.containsKey(evt.getType())) {
            // Copie la liste pour éviter les problèmes de concurrence
            for (EcouteurEvenement ecouteur : new ArrayList<>(ecouteurs.get(evt.getType()))) {
                ecouteur.recevoirEvenement(evt);
            }
        }
    }

    public void announcerTargeted(Evenement evt, Object cible) {
        if (ecouteurs.containsKey(evt.getType())) {
            for (EcouteurEvenement ecouteur : new ArrayList<>(ecouteurs.get(evt.getType()))) {
                if (ecouteur == cible) {  // Ne diffuse qu'à la cible
                    ecouteur.recevoirEvenement(evt);
                    break;
                }
            }
        }
    }
}