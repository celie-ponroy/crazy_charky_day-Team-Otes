package algo;

import outils.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GaleShapley implements AlgoAffectation {

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

            // Tri des salariés libres (par leur meilleur score)
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
                        // Affectation directe
                        Map<Besoin, Integer> assignment = new LinkedHashMap<>();
                        assignment.put(premierBesoin, liste.get(premierBesoin));
                        affectation.put(salarie, assignment);
                        besoinsDemandes.put(premierBesoin, salarie);
                        iterator.remove(); // Retirer ce salarié car il est affecté
                    } else {
                        liste.remove(premierBesoin);
                    }
                }
            }
        }

        // Conversion de `affectation` en `List<Affectation>`
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

        // 🔹 Trier par ordre alphabétique des salariés
        List<Salarie> salariesTries = new ArrayList<>(salariesLibre.keySet());
        salariesTries.sort(Comparator.comparing(Salarie::nom));

        for (Salarie salarie : salariesTries) {
            Map<Besoin, Integer> besoinsMap = salariesLibre.get(salarie);

            // 🔹 Tri stable en cas d'égalité
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

        // Récupérer l'ensemble des clients déjà servis.
        // Ici, on suppose que la méthode besoin.nom() retourne l'identifiant du client.
        Set<String> clientsServed = affectation.values().stream()
                .flatMap(assignment -> assignment.keySet().stream())
                .map(Besoin::nom)
                .collect(Collectors.toSet());

        // Pour chaque salarié libre...
        for (Map.Entry<Salarie, Map<Besoin, Integer>> entry : salariesLibre.entrySet()) {
            Map<Besoin, Integer> besoinsMap = entry.getValue();

            // Pour chaque besoin potentiel du salarié...
            for (Map.Entry<Besoin, Integer> besoinEntry : besoinsMap.entrySet()) {
                Besoin besoin = besoinEntry.getKey();
                int score = besoinEntry.getValue();

                // Si le client (ici identifié par besoin.nom()) est déjà servi,
                // on applique le malus de -1 (sans descendre en dessous de 1).
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
