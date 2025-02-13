package outils;

public record Affectation(Besoin besoin, Salarie salarie) {
    @Override
    public String toString() {
        return "Affectation{" +
                "besoin=" + besoin +
                ", salarie=" + salarie +
                '}';
    }
}
