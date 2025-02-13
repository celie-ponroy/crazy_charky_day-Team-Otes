package outils.donnees;

import outils.Affectation;
import outils.Besoin;
import outils.Competence;
import outils.Salarie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ImportCSV implements Import{
    String path;
    List<Besoin> besoins;
    List<Salarie> salaries;

    public ImportCSV(String path){
        this.path = path;
        chargerDonnees();

    }
    private void chargerDonnees(){
        File file = new File(path);
        //on lis le contenus
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("fichier non trouvé");
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
                System.out.println(line);
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

    @Override
    public List<Besoin> getBesoin() {
        return besoins;
    }

    @Override
    public List<Salarie> getSalaries() {
        return salaries;
    }

    @Override
    public List<Affectation> getSolution() {
        return List.of();
    }

}
