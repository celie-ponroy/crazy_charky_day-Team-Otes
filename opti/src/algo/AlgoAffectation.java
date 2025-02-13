package algo;

import outils.Affectation;
import outils.Besoin;
import outils.Salarie;

import java.util.List;

public interface AlgoAffectation {

    List<Affectation> lancerCalcul(List<Besoin> besoins, List<Salarie> salaries);

}
