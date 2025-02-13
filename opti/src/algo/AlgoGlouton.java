package algo;

import org.w3c.dom.ls.LSOutput;
import outils.Affectation;
import outils.Besoin;
import outils.Competence;
import outils.Salarie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe qui permet de lancer l'algorithme glouton
 */
public class AlgoGlouton extends AlgoAffectation {


    //constructeur
    public AlgoGlouton(){
        super();
    }

    /**
     * Méthode qui permet de calculer l'affectation des besoins aux salariés avec l'algorithme glouton en fonction des règles imposées:
     * - un salarié ne peut pas être affecté à plus d'un besoin
     * - un besoin ne peut pas être affecté à plus d'un salarié
     * un salrié peut seulement etre affecté à un besoin si il a la compétence nécessaire
     * @param besoins la liste des besoins à affecter
     * @param salaries la liste des salariés à qui on peut affecter les besoins
     * @return la lsite des affectations (Besoin, Salarié)
     */
    public List<Affectation> lancerCalcul (List<Besoin> besoins, List<Salarie> salaries) {

        //on crée une liste d'affectation finale
        List<Affectation> affectations = new ArrayList<Affectation>();

        //on crée une liste des besoins non affectés
        List<Besoin> besoinsNonAffectes = new ArrayList<Besoin>(besoins);

        //on crée une liste des salaries non affectés
        List<Salarie> salariesNonAffectes = new ArrayList<>(salaries);

        //on trie les interets des salariés (Map) par type de besoin et par ordre decroissant
        for (Salarie salarie : salaries) {
            Map<Competence, Integer> competences = salarie.competences();
            competences.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        }

        //on parcourt les besoins non affectés
        while (!besoinsNonAffectes.isEmpty() && !salariesNonAffectes.isEmpty()) {

            //on récupère le premier besoin non affecté
            Besoin besoin = besoinsNonAffectes.get(0);
            System.out.println(besoin);

            //on récupère les compétences nécessaires pour ce besoin
            Competence competenceBesoin = besoin.competence();

            //on récupère les salariés qui ont cette compétence
            List<Salarie> salariesCompetents = salariesNonAffectes.stream().filter(salarie -> salarie.competences().containsKey(competenceBesoin)).collect(Collectors.toList());

            //on trie les salariés compétents par ordre décroissant de leur interert pour la compétence du besoin
            for ( Salarie salarie : salariesCompetents) {
                Map<Competence, Integer> competences = salarie.competences();
                competences.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            }

            //on récupère le premier salarié compétent
            Salarie salarie = salariesCompetents.get(0);

            //on crée une affectation
            Affectation affectation = new Affectation(besoin, salarie);
            System.out.println("Besoin : " + besoin + " Salarié : " + salarie);

            //on ajoute l'affectation à la liste des affectations
            affectations.add(affectation);
            score += salarie.competences().get(competenceBesoin);

            //on retire le besoin de la liste des besoins non affectés
            besoinsNonAffectes.remove(besoin);

            //on retire le salarié qui a été affecté à ce besoin
            salariesNonAffectes.remove(salarie);

            //on retire le salarie de la liste des salaries
            salaries.remove(salarie);

            //si le client d'un besoin est deja dans affectation, on retire -1 au score
            if (affectations.stream().anyMatch((a) -> a.besoin().nom().equals(besoin.nom())&&!a.besoin().equals(besoin))) {
                score -= 1;
                System.out.println("On retire 1 au score, le client " + besoin.nom() + " est déjà affecté.");
            }
        }
        verifFin(besoinsNonAffectes, salariesNonAffectes);
        return affectations;
    }

    @Override
    public void verifFin(List<Besoin> besoins, List<Salarie> salaries) {
        //si il reste des besoins non affectés
        if (!besoins.isEmpty()) {
           score -= 10;
            System.out.println("On retire 10 au score");
        }
        //si il reste des salariés non affectés
        if (!salaries.isEmpty()) {
            score -= 10;
            System.out.println("On retire 10 au score");
        }
    }


}
