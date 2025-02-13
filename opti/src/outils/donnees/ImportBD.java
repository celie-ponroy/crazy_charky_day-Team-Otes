package outils.donnees;

import outils.Affectation;
import outils.Besoin;
import outils.Competence;
import outils.Salarie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ImportBD implements Import {
    private List<Besoin> besoins;
    private List<Salarie> salaries;

    public ImportBD() {
        this.besoins = new ArrayList<>();
        this.salaries = new ArrayList<>();
        chargerBesoins();
        chargerSalaries();
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
        return List.of(); // Pour l’instant, aucune affectation
    }

    /**
     * Charge les besoins depuis la base de données.
     */
    private void chargerBesoins() {
        String sql = "SELECT b.id, b.libelle, c.libelle as competence FROM besoin b " +
                "JOIN competence c ON b.competence_id = c.id";

        try (Connection conn = PostgresConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("libelle");
                String competenceStr = rs.getString("competence");
                Competence competence = Competence.getCompetence(competenceStr);
                Besoin besoin = new Besoin(id, nom, competence);
                besoins.add(besoin);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des besoins : " + e.getMessage());
        }
    }

    /**
     * Charge les salariés et leurs compétences depuis la base de données.
     */
    private void chargerSalaries() {
        String sql = "SELECT u.id, u.nom, c.libelle, uc.interet " +
                "FROM users u " +
                "JOIN user_competence uc ON u.id = uc.user_id " +
                "JOIN competence c ON uc.competence_id = c.id " +
                "WHERE u.role = 1";

        Map<UUID, Salarie> salarieMap = new HashMap<>();

        try (Connection conn = PostgresConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String nom = rs.getString("nom");
                String competenceStr = rs.getString("libelle");
                int interet = rs.getInt("interet");

                Competence competence = Competence.getCompetence(competenceStr);

                salarieMap.putIfAbsent(id, new Salarie(id, nom, new HashMap<>()));
                salarieMap.get(id).competences().put(competence, interet);
            }

            salaries = new ArrayList<>(salarieMap.values());

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des salariés : " + e.getMessage());
        }
    }

    public List<Besoin> getBesoinsByDate(String dateStr) {
        List<Besoin> result = new ArrayList<>();
        String sql = "SELECT b.id, b.libelle, c.libelle as competence FROM besoin b " +
                "JOIN competence c ON b.competence_id = c.id " +
                "WHERE DATE(b.date) = ?";

        try (Connection conn = PostgresConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(dateStr));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id"); // Génération d'un UUID temporaire
                String nom = rs.getString("libelle");
                String competenceStr = rs.getString("competence");
                Competence competence = Competence.getCompetence(competenceStr);

                result.add(new Besoin(id, nom, competence));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des besoins : " + e.getMessage());
        }
        return result;
    }
}
