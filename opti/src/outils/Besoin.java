package outils;

import java.util.Objects;

public record Besoin(int id, String nom, Competence competence) {

    public boolean estAcceptable(Salarie salarie) {
        return salarie.competences().containsKey(competence);
    }

    @Override
    public String toString() {
        return "Besoin{" +
                " nom='" + nom + '\'' +
                ", competence=" + competence +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Besoin besoin = (Besoin) o;
        return (Objects.equals(id, besoin.id) || Objects.equals(nom, besoin.nom) ) && competence == besoin.competence;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, competence);
    }
}
