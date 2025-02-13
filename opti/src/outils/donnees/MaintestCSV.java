package outils.donnees;

public class MaintestCSV {
    public static void main(String[] args) {
        ImportCSV importCSV = new ImportCSV("/home/celie/Documents/s5/Crazy_Charly_Day/crazy_charky_day-Team-Otes/opti/ressource/csv/00_exemple/metier_1.csv");
        System.out.println(importCSV.getBesoin());
        System.out.println(importCSV.getSalaries());
        ImportCSV importCSV1 = new ImportCSV("/home/celie/Documents/s5/Crazy_Charly_Day/crazy_charky_day-Team-Otes/opti/ressource/csv/00_exemple/metier_1_solution_1.csv");
        System.out.println(importCSV1.getSolution());
    }
}
