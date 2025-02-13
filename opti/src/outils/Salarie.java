package outils;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record Salarie(UUID id, String nom, Map<Competence, Integer> competences) {
    @Override
    public String toString() {
        return "Salarie{" +
                " nom='" + nom + '\'' +
                ", competences=" + competences +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Salarie salarie = (Salarie) o;
        return Objects.equals(id, salarie.id) && Objects.equals(nom, salarie.nom) && Objects.equals(competences, salarie.competences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, competences);
    }
}
