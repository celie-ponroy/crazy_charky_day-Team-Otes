package outils.donnees;

import outils.Affectation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportCSV implements Export {

    /**
     * Enregistre les affectations dans un fichier CSV avec le score
     * @param affectations Liste des affectations à enregistrer
     * @param score Score associé aux affectations
     * @param path Chemin où enregistrer le fichier (ex: "ressource/csv/soluce")
     */
    @Override
    public void exporterSolution(List<Affectation> affectations, int score, String path) {
        File file = new File(path);

        // Vérifier et créer les dossiers nécessaires
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("📂 Dossier créé : " + parentDir.getAbsolutePath());
            } else {
                System.err.println("❌ Erreur : Impossible de créer le dossier " + parentDir.getAbsolutePath());
            }
        }

        // Écriture dans le fichier CSV
        try (FileWriter writer = new FileWriter(file)) {
            writer.append(score + ";\n");
            for (Affectation affectation : affectations) {
                writer.append(affectation.salarie().nom());
                writer.append(";");
                writer.append(affectation.besoin().competence().name());
                writer.append(";");
                writer.append(affectation.besoin().nom());
                writer.append("\n");
            }
            System.out.println("✅ Exportation réussie : " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'exportation du fichier : " + e.getMessage());
        }
    }
}
