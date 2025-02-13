package algo;

import outils.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GaleShapley extends AlgoAffectation {

    /**
     * Implémente l'algorithme de Gale-Shapley pour l'affectation optimale des salariés aux besoins.
     *
     * Cet algorithme vise à affecter chaque salarié à un besoin en fonction de ses compétences,
     * tout en minimisant les conflits et en maximisant l'adéquation entre les besoins et les compétences.
     *
     * Étapes principales :
     * 1. Trier les salariés par ordre alphabétique.
     * 2. Initialiser les structures de données :
     *    - `salariesLibre` : Liste des salariés encore disponibles avec leurs besoins potentiels triés par pertinence.
     *    - `affectation` : Liste des salariés déjà affectés à un besoin.
     * 3. Construire la liste des besoins compatibles pour chaque salarié en fonction de ses compétences.
     * 4. Tant qu'il reste des salariés non affectés et des besoins disponibles :
     *    - Afficher l'état actuel des salariés disponibles.
     *    - Trier les salariés disponibles en fonction de leur meilleure compatibilité avec un besoin.
     *    - Appliquer un malus de -1 aux scores des besoins dont le client est déjà servi.
     *    - Chaque salarié propose au besoin avec lequel il a la meilleure compatibilité.
     *    - Si le besoin est libre, le salarié est directement affecté.
     *    - Sinon, il essaie un autre besoin.
     * 5. Convertir la structure de données interne en une liste d'objets `Affectation`.
     *
     * Complexité :
     * - La complexité globale est approximativement O(n log n) en raison des tris appliqués aux salariés et aux besoins.
     * - L'affectation elle-même suit une logique de matching stable, optimisant l'adéquation entre salariés et besoins.
     *
     * Remarque :
     * - Contrairement à l'algorithme original de Gale-Shapley, ici, il n'y a pas de classement des besoins du côté des salariés
     *   ni de classement des salariés du côté des besoins. Cela signifie que l'algorithme est très proche d'une approche gloutonne,
     *   où les salariés prennent la meilleure opportunité disponible à chaque itération sans rétroaction systématique.
     *
     * @param besoins   Liste des besoins à combler.
     * @param salaries  Liste des salariés disponibles.
     * @return          Liste des affectations optimisées entre salariés et besoins.
     */

    @Override
    public List<Affectation> lancerCalcul(List<Besoin> besoins, List<Salarie> salaries) {

        salaries.sort(Comparator.comparing(Salarie::nom));

        Map<Salarie, Map<Besoin, Integer>> salariesLibre = new LinkedHashMap<>();
        Map<Salarie, Map<Besoin, Integer>> affectation = new LinkedHashMap<>();

        for (Salarie salarie : salaries) {
            Map<Besoin, Integer> besoinsLibre = new LinkedHashMap<>();
            Map<Competence, Integer> competenceSalarie = salarie.competences();

            for (Besoin besoin : besoins) {
                if (competenceSalarie.containsKey(besoin.competence())) {
                    int valeur = competenceSalarie.get(besoin.competence());
                    besoinsLibre.put(besoin, valeur);
                }
            }
            if (!besoinsLibre.isEmpty()) {
                salariesLibre.put(salarie, besoinsLibre);
            }
        }

        while (!salariesLibre.isEmpty() && (affectation.size() != besoins.size())) {
            printMapVisually(salariesLibre);

            salariesLibre = trierSalariesLibre(salariesLibre);
            updateSalariesLibre(salariesLibre, affectation);

            Map<Besoin, Salarie> besoinsDemandes = new LinkedHashMap<>();
            Iterator<Map.Entry<Salarie, Map<Besoin, Integer>>> iterator = salariesLibre.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<Salarie, Map<Besoin, Integer>> entry = iterator.next();
                Salarie salarie = entry.getKey();
                Map<Besoin, Integer> liste = entry.getValue();

                if (!liste.isEmpty()) {
                    Besoin premierBesoin = liste.entrySet().iterator().next().getKey();

                    if (!besoinsDemandes.containsKey(premierBesoin)) {
                        Map<Besoin, Integer> assignment = new LinkedHashMap<>();
                        assignment.put(premierBesoin, liste.get(premierBesoin));
                        affectation.put(salarie, assignment);
                        besoinsDemandes.put(premierBesoin, salarie);
                        iterator.remove();
                    } else {
                        liste.remove(premierBesoin);
                    }
                }
            }
        }

        List<Affectation> affectations = new ArrayList<>();
        for (Map.Entry<Salarie, Map<Besoin, Integer>> entry : affectation.entrySet()) {
            Salarie salarie = entry.getKey();
            Map.Entry<Besoin, Integer> affectationEntry = entry.getValue().entrySet().iterator().next();
            Besoin besoin = affectationEntry.getKey();
            affectations.add(new Affectation(besoin, salarie));
        }

        return affectations;
    }

    private Map<Salarie, Map<Besoin, Integer>> trierSalariesLibre(
            Map<Salarie, Map<Besoin, Integer>> salariesLibre) {

        Map<Salarie, Map<Besoin, Integer>> sortedSalariesLibre = new LinkedHashMap<>();

        List<Salarie> salariesTries = new ArrayList<>(salariesLibre.keySet());
        salariesTries.sort(Comparator.comparing(Salarie::nom));

        for (Salarie salarie : salariesTries) {
            Map<Besoin, Integer> besoinsMap = salariesLibre.get(salarie);

            Map<Besoin, Integer> sortedBesoinsMap = besoinsMap.entrySet().stream()
                    .sorted((e1, e2) -> {
                        int compare = e2.getValue().compareTo(e1.getValue()); // Tri décroissant des valeurs
                        if (compare == 0) {
                            return e1.getKey().nom().compareTo(e2.getKey().nom()); // Tri alphabétique en cas d'égalité
                        }
                        return compare;
                    })
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            sortedSalariesLibre.put(salarie, sortedBesoinsMap);
        }

        return sortedSalariesLibre;
    }

    /**
     * Met à jour les scores dans salariesLibre en appliquant un malus de -1
     * aux besoins dont le client est déjà servi (au moins un besoin déjà affecté).
     * Le score ne descend pas en dessous de 1.
     *
     * @param salariesLibre La map des salariés encore libres, avec leurs besoins et scores.
     * @param affectation La map des salariés déjà affectés (avec leurs scores).
     */
    private void updateSalariesLibre(Map<Salarie, Map<Besoin, Integer>> salariesLibre,
                                     Map<Salarie, Map<Besoin, Integer>> affectation) {

        Set<String> clientsServed = affectation.values().stream()
                .flatMap(assignment -> assignment.keySet().stream())
                .map(Besoin::nom)
                .collect(Collectors.toSet());

        for (Map.Entry<Salarie, Map<Besoin, Integer>> entry : salariesLibre.entrySet()) {
            Map<Besoin, Integer> besoinsMap = entry.getValue();

            for (Map.Entry<Besoin, Integer> besoinEntry : besoinsMap.entrySet()) {
                Besoin besoin = besoinEntry.getKey();
                int score = besoinEntry.getValue();

                if (clientsServed.contains(besoin.nom())) {
                    int newScore = Math.max(score - 1, 1);
                    besoinEntry.setValue(newScore);
                }
            }
        }
    }

    private void printMapVisually(Map<Salarie, Map<Besoin, Integer>> map) {
        System.out.println("=== Affichage détaillé de la map ===");
        for (Map.Entry<Salarie, Map<Besoin, Integer>> entry : map.entrySet()) {
            Salarie salarie = entry.getKey();
            System.out.println("Salarie: " + salarie.nom());

            for (Map.Entry<Besoin, Integer> besoinEntry : entry.getValue().entrySet()) {
                Besoin besoin = besoinEntry.getKey();
                int valeur = besoinEntry.getValue();
                System.out.println("   " + besoin.nom() +" ("+besoin.competence()+ ") " +" => " + valeur);
            }
            System.out.println("------------------------------------");
        }
    }
}
