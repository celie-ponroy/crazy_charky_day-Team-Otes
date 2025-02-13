package algo;

import outils.Affectation;
import outils.Besoin;
import outils.Salarie;
import outils.donnees.ImportCSV;

import java.util.ArrayList;
import java.util.List;

public class AlgoRecuitSimule implements AlgoAffectation{
    private List<Affectation> perfectMatches = new ArrayList<>();
     int score = 0;

    @Override
    public List<Affectation> lancerCalcul(List<Besoin> besoins, List<Salarie> salaries) {
        List<Affectation> affectations = new ArrayList<>();
        //on crée une affectation par défault
        affectations = affecterAleatoire(besoins,salaries);
        recuitSimule(affectations,100,100,0,salaries,besoins);
        score = CalculeScoreAffectation.scoreAffectation(affectations,besoins,salaries);
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
    private List<Affectation> recuitSimule(List<Affectation> affectations, double temp, int kmax, int emax,List<Salarie> salaries,List<Besoin> besoins){
        List<Affectation> s = affectations;
        List<Affectation> g = affectations;
        System.out.println("e");
        int e = -CalculeScoreAffectation.scoreAffectation(affectations,besoins,salaries);
        int m = e;
        int k = 0;
        while(k < kmax && e > emax){
            List<Salarie> salarieNonAffectes = salaries.stream().filter(salar -> !salaries.contains(salar)).toList();
            List<Affectation> sn = voisinAleatoire(s,salarieNonAffectes);
            System.out.println("en");
            int en = CalculeScoreAffectation.scoreAffectation(sn,besoins,salaries);
            temp = temp *0.99;
            if(en < e || Math.random() < P(en - e, temp)){
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
            System.out.println("perfect match");
            return affectations;
        }
        int index = (int) (Math.random() * copieAffectations.size());
        Besoin besoinSelectionne = copieAffectations.get(index).besoin();
        //trouver les salaries competents qui ne sont pas dans les affectations
        List<Salarie> salarieNonAffectesCompetent = salarieNonAffectes.stream().filter(s -> besoinSelectionne.estAcceptable(s)).toList();

        if (!salarieNonAffectesCompetent.isEmpty()){
            System.out.println("meilleur cas");
            //au hasard dans la nouvelle liste
            int indexDispo = (int) (Math.random() * copieAffectations.size());
            copieAffectations.set(index,new Affectation(besoinSelectionne,salarieNonAffectesCompetent.get(indexDispo)));
            return copieAffectations;
        }else{
            System.out.println("cas liste affectés");
            //on regarde dans la liste des affectations
            List<Salarie> salarierAffectes = new ArrayList<>(copieAffectations.stream().map(Affectation::salarie).toList());
            Salarie salarie = copieAffectations.get(index).salarie();
            salarierAffectes.remove(salarie);
            salarierAffectes = salarierAffectes.stream().filter(s -> besoinSelectionne.estAcceptable(s)).toList();

            if (!salarierAffectes.isEmpty()){
                System.out.println("cas liste affectés1");
                //au hasard dans la nouvelle liste
                int indexDispo = (int) (Math.random() * salarieNonAffectesCompetent.size());
                Besoin ancienBesoin = copieAffectations.get(indexDispo).besoin();
                copieAffectations.set(index, new Affectation(besoinSelectionne, salarierAffectes.get(indexDispo)));
                List<Salarie> salarieNonAffectesCompetent2  = salarieNonAffectes.stream().filter(s -> ancienBesoin.estAcceptable(s)).toList();
                if (!salarieNonAffectesCompetent2.isEmpty()) {
                    System.out.println("cas liste affectés2");
                    //au hasard dans la nouvelle liste
                    int indexDispo2 = (int) (Math.random() * copieAffectations.size());
                    copieAffectations.set(index, new Affectation(besoinSelectionne, salarieNonAffectesCompetent.get(indexDispo2)));
                    return copieAffectations;
                }//sinon on modifira trop
                return copieAffectations;
            }else {
                System.out.println("perfect match2");
                perfectMatches.add(copieAffectations.get(index));
                return voisinAleatoire(affectations,salarieNonAffectesCompetent);
            }
        }
    }
    private double P(int deltaE, double temp){
        return Math.exp(-deltaE/temp);
    }

}

