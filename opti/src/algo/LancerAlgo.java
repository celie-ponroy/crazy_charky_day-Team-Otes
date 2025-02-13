package algo;

import outils.Besoin;
import outils.Salarie;
import outils.donnees.ExportCSV;
import outils.donnees.ImportCSV;

import java.util.List;

public class LancerAlgo {

    public void run() {
        // TODO
    }

    public static void main(String[] args) {
        AlgoRecuitSimule algoGlouton = new AlgoRecuitSimule();

        //on prend un fichier CSV pour le transfome en liste de besoins et de salaries
        ImportCSV importCSV = new ImportCSV("/home/celie/Documents/s5/Crazy_Charly_Day/crazy_charky_day-Team-Otes/opti/ressource/csv/02_pb_complexes/Probleme_1_nbSalaries_10_nbClients_10_nbTaches_3.csv");
        List<Besoin> listeBesoin = importCSV.getBesoin();
        List<Salarie> listeSalaries = importCSV.getSalaries();

        ExportCSV ex = new ExportCSV();

        System.out.println("Liste des affectations : ");
        System.out.println(algoGlouton.lancerCalcul(listeBesoin, listeSalaries));
        ex.exporterSolution(algoGlouton.lancerCalcul(listeBesoin, listeSalaries), algoGlouton.score, "/home/celie/Documents/s5/Crazy_Charly_Day/crazy_charky_day-Team-Otes/opti/ressource/csv/soluce.csv");
        System.out.println("Score : " + algoGlouton.score);
    }
}
