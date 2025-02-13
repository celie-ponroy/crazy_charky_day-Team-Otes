package algo;

import outils.Besoin;
import outils.Salarie;
import outils.donnees.ImportCSV;

import java.util.List;

public class LancerAlgo {

    public void run() {
        // TODO
    }

    public static void main(String[] args) {
        AlgoGlouton algoGlouton = new AlgoGlouton();

        //on prend un fichier CSV pour le transfome en liste de besoins et de salaries
        ImportCSV importCSV = new ImportCSV("/home/maelle/Documents/crazy_charky_day-Team-Otes/opti/ressource/csv/00_exemple/metier_1.csv");
        List<Besoin> listeBesoin = importCSV.getBesoin();
        List <Salarie> listeSalaries = importCSV.getSalaries();


        System.out.println("Liste des affectations : ");
        System.out.println(algoGlouton.lancerCalcul(listeBesoin, listeSalaries));
        System.out.println("Score : " + algoGlouton.score);
    }

}
