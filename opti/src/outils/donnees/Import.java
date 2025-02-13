package outils.donnees;

import outils.Affectation;
import outils.Besoin;
import outils.Salarie;

import java.util.List;

public interface Import {

    List<Besoin> getBesoin();
    List<Salarie> getSalaries();
    List<Affectation> getSolution();


}
