package outils.donnees;

import outils.Affectation;

import java.util.List;

public interface Export {
    abstract void exporterSolution(List<Affectation> affactations);
}
