package outils;

public record Besoin(int id, String nom, Competence competence) {

    public boolean estAcceptable(Salarie salarie) {
        return salarie.competences().containsKey(competence);
    }
}
