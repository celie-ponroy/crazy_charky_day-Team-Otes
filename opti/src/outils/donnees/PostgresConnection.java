package outils.donnees;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {

    private static final String URL = "jdbc:postgresql://localhost:2002/charly";
    private static final String USER = "user";
    private static final String PASSWORD = "toto";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie à PostgreSQL !");
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("Pilote PostgreSQL introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        // Test de connexion
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connexion fermée.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

