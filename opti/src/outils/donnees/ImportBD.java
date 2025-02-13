package outils.donnees;

import outils.Affectation;
import outils.Besoin;
import outils.Competence;
import outils.Salarie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ImportBD implements Import{
    private List<Besoin> besoins;
    private List<Salarie> salaries;

    public ImportBD(){

    }

    @Override
    public List<Besoin> getBesoin() {
        return besoins;
    }

    @Override
    public List<Salarie> getSalaries() {
        return salaries;
    }

    @Override
    public List<Affectation> getSolution() {
        return List.of();
    }

}
