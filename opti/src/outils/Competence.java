package outils;

public enum Competence {
    BR("Bricolage"),
    JD("Jardinage"),
    MN("Nettoyage"),
    IF("Informatique"),
    AD("Cuisine"),
    PL("Plomberie");

    final String label;

    Competence(String label) {
        this.label = label;
    }

    public static Competence getCompetence(String comp) {
        for (Competence c : Competence.values()) {
            if (c.label.equalsIgnoreCase(comp)) {
                return c;
            }
        }
        System.err.println("⚠️ Competence inconnue : " + comp);
        return null;
    }
}

