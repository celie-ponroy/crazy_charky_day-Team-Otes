package algo;

import outils.Affectation;
import outils.Besoin;
import outils.Salarie;

import java.util.List;

public abstract class AlgoAffectation {

    //Atributs

    protected int score ;


    //constructeur
    public AlgoAffectation() {
        this.score = 0;
    }

    public abstract List<Affectation> lancerCalcul(List<Besoin> besoins, List<Salarie> salaries);
    public abstract void verifFin(List<Besoin> besoins, List<Salarie> salaries);

}
