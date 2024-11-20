package v7;

import java.util.*;

public class Diffuseur {
    // Map qui associe chaque type d'événement à sa liste d'écouteurs
    private final Map<String, List<EcouteurEvenement>> ecouteurs = new HashMap<>();

    // Map qui garde trace des diffusions régulières actives
    private final Map<String, Timer> diffusionsRegulières = new HashMap<>();

    /**
     * Enregistre un écouteur pour un type d'événement spécifique
     */
    public void enregistrer(String typeEvenement, EcouteurEvenement ecouteur) {
        ecouteurs.computeIfAbsent(typeEvenement, k -> new ArrayList<>()).add(ecouteur);
    }

    /**
     * Désenregistre un écouteur pour un type d'événement spécifique
     */
    public void desenregistrer(String typeEvenement, EcouteurEvenement ecouteur) {
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
    public void diffuser(Evenement evt) {
        if (ecouteurs.containsKey(evt.getType())) {
            // Copie la liste pour éviter les problèmes de concurrence
            for (EcouteurEvenement ecouteur : new ArrayList<>(ecouteurs.get(evt.getType()))) {
                ecouteur.recevoirEvenement(evt);
            }
        }
    }

    public void diffuserCible(Evenement evt, Object cible) {
        if (ecouteurs.containsKey(evt.getType())) {
            for (EcouteurEvenement ecouteur : new ArrayList<>(ecouteurs.get(evt.getType()))) {
                if (ecouteur == cible) {  // Ne diffuse qu'à la cible
                    ecouteur.recevoirEvenement(evt);
                    break;
                }
            }
        }
    }

    /**
     * Démarre une diffusion régulière d'un événement
     */
    public void demarrerDiffusionReguliere(Evenement evt, int periodeMs) {
        String identifiant = evt.getType() + "_" + evt.getSource().hashCode();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                diffuser(evt);
            }
        }, 0, periodeMs);

        // Arrête l'ancienne diffusion si elle existe
        arreterDiffusionReguliere(identifiant);
        diffusionsRegulières.put(identifiant, timer);
    }

    /**
     * Arrête une diffusion régulière
     */
    public void arreterDiffusionReguliere(String identifiant) {
        Timer timer = diffusionsRegulières.remove(identifiant);
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Arrête toutes les diffusions régulières
     */
    public void arreterToutesDiffusionsRegulieres() {
        for (Timer timer : diffusionsRegulières.values()) {
            timer.cancel();
        }
        diffusionsRegulières.clear();
    }
}