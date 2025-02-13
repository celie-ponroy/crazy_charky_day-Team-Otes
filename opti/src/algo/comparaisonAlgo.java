package algo;

import outils.Affectation;
import outils.Besoin;
import outils.Salarie;
import outils.donnees.ExportCSV;
import outils.donnees.ImportCSV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class comparaisonAlgo {
    public static void main(String[] args) {


        AlgoAffectation algoRecuit = new AlgoRecuitSimule();

        AlgoAffectation algoGale = new GaleShapley();

        AlgoAffectation algoGlou = new AlgoGlouton();
        List<AlgoAffectation> algos = new ArrayList<>();
        algos.add(algoGlou);
        algos.add(algoRecuit);


        String fichierCSV00 = "opti/ressource/csv/00_exemple/metier_1.csv";
        String fichierCSV01 = "opti/ressource/csv/01_pb_simples/Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2.csv";
        String fichierCSV02 = "opti/ressource/csv/02_pb_complexes/Probleme_1_nbSalaries_10_nbClients_10_nbTaches_3.csv";


        String solGlouFichierCSV00 = "opti/ressource/csv/solutions/solutions_Glou_1";
        String solRecFichierCSV00 = "opti/ressource/csv/solutions/solutions_Rec_1";
        String solGalFichierCSV00 = "opti/ressource/csv/solutions/solutions_Gal_1";
        String solGlouFichierCSV01 = "opti/ressource/csv/solutions/solutions_Glou_1_nbSalaries_3_nbClients_3_nbTaches_2";
        String solRecFichierCSV01 = "opti/ressource/csv/solutions/solutions_Rec_1_nbSalaries_3_nbClients_3_nbTaches_2";
        String solGalFichierCSV01 = "opti/ressource/csv/solutions/solutions_Gal_1_nbSalaries_3_nbClients_3_nbTaches_2";
        String solGlouFichierCSV02 = "opti/ressource/csv/solutions/solutions_Glou_1_nbSalaries_10_nbClients_10_nbTaches_3";
        String solGalFichierCSV02 = "opti/ressource/csv/solutions/solutions_Gal_1_nbSalaries_10_nbClients_10_nbTaches_3";
        String solRecFfichierCSV02 = "opti/ressource/csv/solutions/solutions_Rec_1_nbSalaries_10_nbClients_10_nbTaches_3";
        List<String> enregistrer = new ArrayList<>();
        enregistrer.add(solGlouFichierCSV00);
        enregistrer.add(solRecFichierCSV00);
        enregistrer.add(solGalFichierCSV00);
        enregistrer.add(solGlouFichierCSV01);
        enregistrer.add(solRecFichierCSV01);
        enregistrer.add(solGalFichierCSV01);
        enregistrer.add(solGlouFichierCSV02);
        enregistrer.add(solGalFichierCSV02);
        enregistrer.add(solRecFfichierCSV02);


        List<String> fichies = new ArrayList<>();
        fichies.add(fichierCSV00);
        fichies.add(fichierCSV01);
        fichies.add(fichierCSV02);

        HashMap<AlgoAffectation, List<Integer>> mapRes = new HashMap();
        List<Integer> resGlou = new ArrayList<>();
        List<Integer> resRecuit = new ArrayList<>();
        List<Integer> resGale = new ArrayList<>();

        mapRes.put(algoGlou, resGlou);
        mapRes.put(algoRecuit, resRecuit);
        mapRes.put(algoGale, resGale);

        ExportCSV exportCSV = new ExportCSV();

        for (String fichier : fichies) {
            System.out.println(fichier);
            ImportCSV importCSV = new ImportCSV(fichier);
            // Importation des données
            importCSV = new ImportCSV(fichier);

            List<Besoin> listeBesoin = importCSV.getBesoin();
            List<Salarie> listeSalaries = importCSV.getSalaries();
            for (AlgoAffectation algo : algos) {
                // Exécution de l'algorithme
                exportCSV.exporterSolution(algo.lancerCalcul(listeBesoin, listeSalaries), algo.score, enregistrer.getFirst());
                enregistrer.removeFirst();
                System.out.println("\nExécution de l'algorithme...");
                System.out.println("Score : " + algo.score);
                mapRes.get(algo).add(algo.score);
            }
            List<Affectation> af = algoGale.lancerCalcul(listeBesoin, listeSalaries);
            Integer score = CalculeScoreAffectation.scoreAffectation(af, listeBesoin, listeSalaries);
            mapRes.get(algoGale).add(score);
            exportCSV.exporterSolution(af, score, enregistrer.getFirst());
            enregistrer.removeFirst();
        }
        for (AlgoAffectation algo : mapRes.keySet()) {
            System.out.println("Algo : " + algo);
            List<Integer> res = mapRes.get(algo);
            int i = 1;
            for (Integer resultat : res) {
                System.out.println("resultat n°" + i + " : " + resultat);
                i++;
            }
        }
    }
}
