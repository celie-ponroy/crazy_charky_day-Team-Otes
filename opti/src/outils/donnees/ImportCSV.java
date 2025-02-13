package outils.donnees;

import outils.Affectation;
import outils.Besoin;
import outils.Competence;
import outils.Salarie;

import java.io.*;
import java.util.*;

/**
 * Importe les données depuis un fichier CSV
 */
public class ImportCSV implements Import{
    private String path;
    private List<Besoin> besoins;
    private List<Salarie> salaries;
    private List<Affectation> affectations;
    private int score;

    /**
     * Constructeur
     * @param path
     */
    public ImportCSV(String path){
        this.path = path;
    }

    /**
     * charge les données depuis le fichier
     */
    private void chargerDonnees(){
        File file = new File(path);
        //on lis le contenus
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            this.besoins = List.of();
            this.salaries = List.of();
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        boolean besoinActuel = false;
        boolean competenceActuel = false;
        List<Besoin> besoins = new ArrayList<>();
        Map<String, Salarie> salariesMap = new HashMap<>();

        try {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // ignorer les lignes vides
                }

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
                    String nom = parts[1];
                    Competence comp = Competence.valueOf(parts[2]);
                    Besoin besoin = new Besoin(UUID.randomUUID(), nom, comp);
                    besoins.add(besoin);
                } else if (competenceActuel) {
                    String nom = parts[1];
                    Competence comp = Competence.valueOf(parts[2]);
                    int level = Integer.parseInt(parts[3]);
                    Salarie salarie = salariesMap.get(nom);
                    if (salarie == null) {
                        salarie = new Salarie(UUID.randomUUID(), nom, new HashMap<>());
                        salariesMap.put(nom, salarie);
                    }
                    salarie.competences().put(comp, level);
                }
            }
        }catch (Exception e){
            this.besoins = List.of();
            this.salaries = List.of();
        }
        this.besoins = besoins;
        this.salaries = new ArrayList<>(salariesMap.values());
    }

    /**
     * Retourne les besoins et les charge si nécessaire
     * @return
     */
    @Override
    public List<Besoin> getBesoin() {
        if(besoins == null){
            chargerDonnees();
        }
        return besoins;
    }

    /**
     * Retourne les salariés et les charge si nécessaire
     * @return
     */
    @Override
    public List<Salarie> getSalaries() {
        if (salaries == null){
            chargerDonnees();
        }
        return salaries;
    }

    /**
     * Retourne le score final et le charge si nécessaire
     * @return
     */
    public int getScore() {
        if (affectations == null){
            chargerSolution();
        }
        return score;
    }

    /**
     * Retourne les affectations et les charge si nécessaire
     * @return
     */
    @Override
    public List<Affectation> getSolution() {
        score = 0;
        if (affectations == null){
            chargerSolution();
        }
        return affectations;
    }

    /**
     * Charge la solution depuis le fichier
     */
    public void chargerSolution(){
        List<Affectation> expectedAffectations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(";");
                if (firstLine) {
                    //on recupère le score
                    score = Integer.parseInt(parts[0].replace(";", ""));
                    firstLine = false;
                    continue;
                }
                String salarieNom = parts[0];
                Competence comp = Competence.valueOf(parts[1]);
                String besoinNom = parts[2];

                Affectation affectation = new Affectation(new Besoin(UUID.randomUUID(), besoinNom, comp), new Salarie(UUID.randomUUID(), salarieNom, Map.of(comp, 0)));
                expectedAffectations.add(affectation);
            }
        } catch (Exception e) {
           affectations = List.of();
        }

        affectations =  expectedAffectations;
    }
}
