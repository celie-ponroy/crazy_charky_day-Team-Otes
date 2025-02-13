package outils.donnees;

import outils.Affectation;
import outils.Besoin;
import outils.Competence;
import outils.Salarie;

import java.io.*;
import java.util.*;

/**
 * Importe les données depuis un fichier CSV.
 */
public class ImportCSV implements Import {
    private final String path;
    private List<Besoin> besoins;
    private List<Salarie> salaries;
    private List<Affectation> affectations;
    private int score;

    /**
     * Constructeur
     * @param path Chemin du fichier CSV (ex: "ressource/csv/00_exemple/Probleme_simple.csv")
     */
    public ImportCSV(String path) {
        this.path = path;
    }

    /**
     * Charge les données depuis le fichier CSV.
     */
    private void chargerDonnees() {
        File file = new File(path);

        if (!file.exists()) {
            System.err.println("❌ Erreur : Fichier introuvable -> " + file.getAbsolutePath());
            return;
        }

        System.out.println("📄 Fichier trouvé : " + file.getAbsolutePath());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean besoinActuel = false;
            boolean competenceActuel = false;
            List<Besoin> besoins = new ArrayList<>();
            Map<String, Salarie> salariesMap = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.contains("besoins")) {
                    besoinActuel = true;
                    competenceActuel = false;
                    continue;
                }
                if (line.contains("competences")) {
                    besoinActuel = false;
                    competenceActuel = true;
                    continue;
                }

                String[] parts = line.split(";");
                if (besoinActuel) {
                    int id = Integer.parseInt(parts[0]);
                    String nom = parts[1];
                    Competence comp = Competence.valueOf(parts[2]);
                    besoins.add(new Besoin(id, nom, comp));
                } else if (competenceActuel) {
                    String nom = parts[1];
                    Competence comp = Competence.valueOf(parts[2]);
                    int level = Integer.parseInt(parts[3]);
                    salariesMap.computeIfAbsent(nom, k -> new Salarie(UUID.randomUUID(), nom, new HashMap<>()))
                            .competences().put(comp, level);
                }
            }

            this.besoins = besoins;
            this.salaries = new ArrayList<>(salariesMap.values());
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            this.besoins = List.of();
            this.salaries = List.of();
        }
    }

    @Override
    public List<Besoin> getBesoin() {
        if (besoins == null) chargerDonnees();
        return besoins;
    }

    @Override
    public List<Salarie> getSalaries() {
        if (salaries == null) chargerDonnees();
        return salaries;
    }

    public int getScore() {
        if (affectations == null) chargerSolution();
        return score;
    }

    @Override
    public List<Affectation> getSolution() {
        if (affectations == null) chargerSolution();
        return affectations;
    }

    public void chargerSolution() {
        List<Affectation> expectedAffectations = new ArrayList<>();
        File file = new File(path);

        if (!file.exists()) {
            System.err.println("❌ Erreur : Fichier solution introuvable -> " + file.getAbsolutePath());
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";");

                if (firstLine) {
                    score = Integer.parseInt(parts[0].replace(";", ""));
                    firstLine = false;
                    continue;
                }

                String salarieNom = parts[0];
                Competence comp = Competence.valueOf(parts[1]);
                String besoinNom = parts[2];

                expectedAffectations.add(new Affectation(
                        new Besoin(0, besoinNom, comp),
                        new Salarie(UUID.randomUUID(), salarieNom, Map.of(comp, 0))
                ));
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement de la solution : " + e.getMessage());
            affectations = List.of();
        }

        affectations = expectedAffectations;
    }
}
