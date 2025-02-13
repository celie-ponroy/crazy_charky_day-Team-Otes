package algo;

import outils.Affectation;
import outils.Besoin;
import outils.Salarie;

import java.util.HashMap;
import java.util.List;

public class CalculeScoreAffectation {
    public static int scoreAffectation(List<Affectation> affectations, List<Besoin> besoins, List<Salarie> salaries) {
        int score = 0;
        int malus = 0;
        int nbCliNonPresent = 0;
        HashMap<String, Integer> idCliPresent = new HashMap<>();

        score -= 10 * (salaries.size() - affectations.size()); //regle 7

        for (Affectation a : affectations) {
            Besoin b = a.besoin();
            if (!idCliPresent.containsKey(b.nom())) {
                idCliPresent.put(b.nom(), 0);
            } else {
                idCliPresent.replace(b.nom(), idCliPresent.get(b.nom()) + 1);
            }
            malus = idCliPresent.get(b.nom());

            score += a.salarie().competences().get(b.competence()) - malus; // regle 4 et 5
        }
        for (Besoin b : besoins) {
            if (!idCliPresent.containsKey(b.nom())) {
                nbCliNonPresent++;
            }
        }
        score -= 10 * nbCliNonPresent; //regle 6
        return score;
    }
}
