package algo;

import outils.Affectation;
import outils.Besoin;
import outils.Salarie;
import outils.donnees.ImportCSV;

import java.util.ArrayList;
import java.util.List;

public class AlgoRecuitSimule implements AlgoAffectation{
    private List<Affectation> perfectMatches = new ArrayList<>();

    @Override
    public List<Affectation> lancerCalcul(List<Besoin> besoins, List<Salarie> salaries) {
        List<Affectation> affectations = new ArrayList<>();
        //on crée une affectation par défault
        affectations = affecterAleatoire(besoins,salaries);
        return affectations;
    }
    private List<Affectation> affecterDefaut(List<Besoin> besoins, List<Salarie> salaries) {
        List<Affectation> affectations = new ArrayList<>();

        List<Salarie> salarieAffecte = salaries;
        for (Besoin besoin : besoins) {
            for (Salarie salarie : salarieAffecte) {
                if (besoin.estAcceptable(salarie)) {
                    affectations.add(new Affectation(besoin,salarie));
                    salarieAffecte.remove(salarie);
                    break;
                }
            }
        }
        return affectations;
    }
    private List<Affectation> affecterAleatoire(List<Besoin> besoins, List<Salarie> salaries) {
        List<Affectation> affectations = new ArrayList<>();
        List<Salarie> salarieAffecte = new ArrayList<>(salaries);
        for (Besoin besoin : besoins) {
            //ajouter verif si salarie peut faire le besoin
            List<Salarie> stmp = salariesCompetents(besoin,salarieAffecte);
            if(stmp.isEmpty()){
                continue;
            }
            Salarie salarie = stmp.get((int) (Math.random() * stmp.size()));
            affectations.add(new Affectation(besoin,salarie));
            salarieAffecte.remove(salarie);
        }
        return affectations;
    }

    private List<Salarie> salariesCompetents(Besoin b, List<Salarie> salaries){
        return salaries.stream().filter(s -> b.estAcceptable(s)).toList();
    }

    public static void main(String[] args) {
        ImportCSV importCSV = new ImportCSV("/home/celie/Documents/s5/Crazy_Charly_Day/crazy_charky_day-Team-Otes/opti/ressource/csv/00_exemple/metier_1.csv");
        var besoin = importCSV.getBesoin();
        var sala = importCSV.getSalaries();

        AlgoRecuitSimule algoRecuitSimule = new AlgoRecuitSimule();
        System.out.println(algoRecuitSimule.lancerCalcul(besoin,sala));
    }
    //au bout de n itération je baisse la température
    //t = nombre de changement de
    /**
     * s := s0
     * g := s0
     * e := E(s)
     * m := E(g)
     * k := 0
     * tant que k < kmax et e > emax
     *   s' := choisir aléatoirement un voisin de s uniformément
     *   e' := E(sn)
     *   si e' < e ou aléatoire() < P(e' - e, temp(k/kmax)) alors
     *     s := sn; e := e'
     *   si e < m alors
     *       g := s; m := e;
     *   k := k + 1
     * retourne g
     */
    private List<Affectation> recuitSimule(List<Affectation> affectations, int temp, int kmax, int emax){
        List<Affectation> s = affectations;
        List<Affectation> g = affectations;
        int e = calculerScore(affectations);
        int m = e;
        int k = 0;
        while(k < kmax && e > emax){
            List<Affectation> sn = voisinAleatoire(s);
            int en = calculerScore(sn);
            if(en < e || Math.random() < P(en - e, temp(k/kmax))){
                s = sn;
                e = en;
            }
            if(e < m){
                g = s;
                m = e;
            }
            k++;
        }
        return g;

    }
    private List<Affectation> voisinAleatoire(List<Affectation> affectations, List<Salarie> salarieNonAffectes){
        //on choisi une case aléatoire et on la change
        List<Affectation> copieAffectations = new ArrayList<>(affectations);
        copieAffectations.removeAll(perfectMatches);
        if(copieAffectations.isEmpty()){
            return affectations;
        }
        Besoin besoinSelectionne = copieAffectations.get(index).besoin();
        int index = (int) (Math.random() * copieAffectations.size());
        //trouver les salaries competents qui ne sont pas dans les affectations
        List<Salarie> salarieNonAffectesCompetent = salarieNonAffectes.stream().filter(s -> besoinSelectionne.estAcceptable(s)).toList();

        if (!salarieNonAffectesCompetent.isEmpty()){
            //au hasard dans la nouvelle liste
            int indexDispo = (int) (Math.random() * copieAffectations.size());
            copieAffectations.set(index,new Affectation(besoinSelectionne,salarieNonAffectesCompetent.get(indexDispo)));
            return copieAffectations;
        }else{
            //on regarde dans la liste des affectations
            List<Salarie> salarierAffectes = copieAffectations.stream().map(Affectation::salarie).toList();
            salarierAffectes.remove(copieAffectations.get(index).salarie());
            salarierAffectes.stream().filter(s -> besoinSelectionne.estAcceptable(s)).toList();

            if (!salarierAffectes.isEmpty()){
                //au hasard dans la nouvelle liste
                int indexDispo = (int) (Math.random() * copieAffectations.size());
                Besoin ancienBesoin = copieAffectations.get(indexDispo).besoin();
                copieAffectations.set(index,new Affectation(besoinSelectionne,salarierAffectes.get(indexDispo)));
                List<Salarie> salarieNonAffectesCompetent2  = salarieNonAffectes.stream().filter(s -> ancienBesoin.estAcceptable(s)).toList();
                if (!salarieNonAffectesCompetent.isEmpty()) {
                    //au hasard dans la nouvelle liste
                    int indexDispo2 = (int) (Math.random() * copieAffectations.size());
                    copieAffectations.set(index, new Affectation(besoinSelectionne, salarieNonAffectesCompetent.get(indexDispo2)));
                    return copieAffectations;
                }//sinon on modifira trop
                return copieAffectations;
            }else {
                perfectMatches.add(copieAffectations.get(index));
                return voisinAleatoire(affectations,salarieNonAffectesCompetent);
            }
        }
    }

}

