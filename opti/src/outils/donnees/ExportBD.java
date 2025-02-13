package outils.donnees;

import outils.Affectation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ExportBD implements Export {

    @Override
    public void exporterSolution(List<Affectation> affectations, int score, String path) {
        String checkSql = "SELECT COUNT(*) FROM service WHERE id_besoin = ?";
        String insertSql = "INSERT INTO service (client_id, salarie_id, id_besoin) VALUES (?, ?, ?)";
        String updateSql = "UPDATE service SET client_id = ?, salarie_id = ? WHERE id_besoin = ?";

        try (Connection conn = PostgresConnection.getConnection()) {
            for (Affectation affectation : affectations) {
                int besoinId = affectation.besoin().id(); // ✅ `id_besoin` est un `int`
                UUID salarieId = affectation.salarie().id(); // ✅ `salarie_id` est un `UUID`
                String clientIdStr = getClientIdForBesoin(conn, besoinId); // 🔹 Récupération du `client_id`

                if (clientIdStr == null) {
                    System.err.println("❌ Erreur : Client ID introuvable pour besoin " + besoinId);
                    continue; // Passe au suivant
                }

                UUID clientId = UUID.fromString(clientIdStr); // ✅ Conversion en `UUID`

                // 🔹 Vérifier si une affectation existe déjà pour ce besoin
                boolean affectationExiste = false;
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, besoinId);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        affectationExiste = true;
                    }
                }

                // 🔹 Si une affectation existe déjà, la mettre à jour (UPDATE)
                if (affectationExiste) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setObject(1, clientId);
                        updateStmt.setObject(2, salarieId);
                        updateStmt.setInt(3, besoinId);
                        updateStmt.executeUpdate();
                        System.out.println("🔄 Mise à jour : Besoin " + besoinId + " → Salarié " + salarieId);
                    } catch (SQLException e) {
                        System.err.println("❌ Erreur lors de la mise à jour de l'affectation : " + e.getMessage());
                    }
                } else {
                    // 🔹 Sinon, insérer la nouvelle affectation (INSERT)
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setObject(1, clientId);
                        insertStmt.setObject(2, salarieId);
                        insertStmt.setInt(3, besoinId);
                        insertStmt.executeUpdate();
                        System.out.println("✅ Affectation ajoutée : Besoin " + besoinId + " → Salarié " + salarieId);
                    } catch (SQLException e) {
                        System.err.println("❌ Erreur lors de l'insertion de l'affectation : " + e.getMessage());
                    }
                }
            }
            System.out.println("✅ Toutes les affectations ont été insérées ou mises à jour !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    /**
     * 🔹 Récupère le `client_id` associé à un `besoin.id` donné.
     */
    private String getClientIdForBesoin(Connection conn, int besoinId) {
        String sql = "SELECT client_id FROM besoin WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, besoinId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("client_id"); // ✅ Retourne le `UUID` sous forme de `String`
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération du client_id : " + e.getMessage());
        }
        return null;
    }
}
