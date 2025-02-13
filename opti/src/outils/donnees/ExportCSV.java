package outils.donnees;

import outils.Affectation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportCSV implements Export{

    /**
     * Enregistre les affectations dans un fichier CSV avec le score
     * @param affactations à enregistrer
     * @param score
     * @param path
     */
    @Override
    public void exporterSolution(List<Affectation> affactations,int score,String path) {
        try {
            FileWriter writer = new FileWriter(path+".csv");
            writer.append(score+";\n");
            for (Affectation affectation : affactations) {
                writer.append(affectation.salarie().nom().toString());
                writer.append(";");
                writer.append(affectation.besoin().nom().toString());
                writer.append("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


