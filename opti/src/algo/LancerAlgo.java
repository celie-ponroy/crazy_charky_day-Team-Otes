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
        AlgoGlouton algoGlouton = new AlgoGlouton();

        //on prend un fichier CSV pour le transfome en liste de besoins et de salaries
        ImportCSV importCSV = new ImportCSV("/home/maelle/Documents/crazy_charky_day-Team-Otes/opti/ressource/csv/01_pb_simples/Probleme_2_nbSalaries_3_nbClients_3_nbTaches_5.csv");
        List<Besoin> listeBesoin = importCSV.getBesoin();
        List <Salarie> listeSalaries = importCSV.getSalaries();

        ExportCSV ex = new ExportCSV();

        System.out.println("Liste des affectations : ");
        System.out.println(algoGlouton.lancerCalcul(listeBesoin, listeSalaries));
        ex.exporterSolution(algoGlouton.lancerCalcul(listeBesoin, listeSalaries),algoGlouton.score,"/home/maelle/Documents/crazy_charky_day-Team-Otes/opti/ressource/csv/01_pb_simples/Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2.csv");
        System.out.println("Score : " + algoGlouton.score);
    }

}
