package algo;

import outils.Affectation;
import outils.Besoin;
import outils.Competence;
import outils.Salarie;

import java.util.*;
import java.util.stream.Collectors;

import static algo.CalculeScoreAffectation.scoreAffectation;

/**
 * Classe qui permet de lancer l'algorithme glouton
 */
public class AlgoGlouton extends AlgoAffectation {

    public List<Affectation> lancerCalcul(List<Besoin> besoins, List<Salarie> salaries) {
        List<Affectation> affectations = new ArrayList<>();
        List<Besoin> besoinsNonAffectes = new ArrayList<>(besoins);
        List<Salarie> salariesNonAffectes = new ArrayList<>(salaries);

        while (!besoinsNonAffectes.isEmpty() && !salariesNonAffectes.isEmpty()) {
            // On cherche le salarié avec la plus haute compétence parmi tous les salariés non affectés
            Salarie meilleurSalarie = null;
            Besoin meilleurBesoin = null;
            int meilleurScore = -1;

            // Pour chaque salarié non affecté
            for (Salarie salarie : salariesNonAffectes) {
                // On cherche sa plus haute compétence qui correspond à un besoin non affecté
                for (Map.Entry<Competence, Integer> entry : salarie.competences().entrySet()) {
                    Competence comp = entry.getKey();
                    int score = entry.getValue();

                    // On cherche si cette compétence correspond à un besoin non affecté
                    for (Besoin besoin : besoinsNonAffectes) {
                        if (besoin.competence().equals(comp) && score > meilleurScore) {
                            meilleurScore = score;
                            meilleurSalarie = salarie;
                            meilleurBesoin = besoin;
                        }
                    }
                }
            }

            if (meilleurSalarie != null && meilleurBesoin != null) {
                // On crée l'affectation avec le meilleur score trouvé
                Affectation affectation = new Affectation(meilleurBesoin, meilleurSalarie);
                affectations.add(affectation);

                // On met à jour nos listes
                besoinsNonAffectes.remove(meilleurBesoin);
                salariesNonAffectes.remove(meilleurSalarie);
            } else {
                break;
            }
        }

        score = scoreAffectation(affectations, besoins, salaries);
        return affectations;
    }
}
