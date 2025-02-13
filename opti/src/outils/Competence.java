package outils;

public enum Competence {
    BR("BR"),
    JD("JD"),
    MN("MN"),
    IF("IF"),
    AD("AD");
    final String competence;
    Competence(String competence) {
        this.competence = competence;
    }
    public static Competence getCompetence(String comp) {
        switch (comp){
            case "BR":
                return BR;
            case "JD":
                return JD;
            case "MN":
                return MN;
            case "IF":
                return IF;
            case "AD":
                return AD;
            default:
                return null;
        }
    }
}
