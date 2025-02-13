package algo;


import outils.Affectation;
import outils.Besoin;
import outils.Salarie;
import outils.donnees.ExportCSV;
import outils.donnees.ImportCSV;

import java.util.List;
import java.util.Scanner;

public class LancerAlgo {

    public static void run(AlgoAffectation algo, List<Besoin> besoins, List<Salarie> salaries) {
        algo.lancerCalcul(besoins, salaries);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Choix de l'algorithme
        System.out.println("Choisissez un algorithme :");
        System.out.println("1 - AlgoRecuitSimule");
        System.out.println("2 - GaleShapley");
        System.out.println("3 - AlgoAffectation (autre implémentation)");
        System.out.print("Entrez le numéro correspondant : ");
        int choixAlgo = scanner.nextInt();
        scanner.nextLine();

        AlgoAffectation algo;
        switch (choixAlgo) {
            case 1:
                algo = new AlgoRecuitSimule();
                break;
            case 2:
                algo = new GaleShapley();
                break;
            case 3:
                //algo = new AlgoGlouton();
                break;
            default:
                System.out.println("Choix invalide, utilisation de l'algorithme par défaut (AlgoRecuitSimule).");
                algo = new AlgoRecuitSimule();
        }

        // Choix du fichier de données
        System.out.println("\nChoisissez un fichier de test :");
        System.out.println("1 - Exemple");
        System.out.println("2 - Simple");
        System.out.println("3 - Complexe");
        System.out.print("Entrez le numéro correspondant : ");
        int choixFichier = scanner.nextInt();
        scanner.nextLine();

        String fichierCSV;
        switch (choixFichier) {
            case 1:
                fichierCSV = "opti/csv/00_pb_exemple/Probleme_simple.csv";
                break;
            case 2:
                fichierCSV = "opti/csv/01_pb_simples/Probleme_1_nbSalaries_3_nbClients_3_nbTaches_2.csv";
                break;
            case 3:
                fichierCSV = "opti/csv/02_pb_complexes/Probleme_1_nbSalaries_10_nbClients_10_nbTaches_3.csv";
                break;
            default:
                System.out.println("Choix invalide, utilisation du fichier par défaut (difficile).");
                fichierCSV = "opti/csv/02_pb_complexes/Probleme_1_nbSalaries_10_nbClients_10_nbTaches_3.csv";
        }

        // Importation des données
        ImportCSV importCSV = new ImportCSV(fichierCSV);

        List<Besoin> listeBesoin = importCSV.getBesoin();
        List <Salarie> listeSalaries = importCSV.getSalaries();

        // Exécution de l'algorithme
        ExportCSV ex = new ExportCSV();
        System.out.println("\nExécution de l'algorithme...");
        List<Affectation> resultats = algo.lancerCalcul(listeBesoin, listeSalaries);

        // Affichage et exportation des résultats
        System.out.println("Liste des affectations : ");

        System.out.println(resultats);
        ex.exporterSolution(resultats, algo.score, "opti/csv/soluce.csv");
        System.out.println("Score : " + algo.score);

        scanner.close();

    }


}
