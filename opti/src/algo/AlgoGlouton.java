package algo;

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
public class AlgoGlouton implements AlgoAffectation {


    //constructeur
    public AlgoGlouton(){

    }

    /**
     * Méthode qui permet de calculer l'affectation des besoins aux salariés avec l'algorithme glouton en fonction des règles imposées:
     * - un salarié ne peut pas être affecté à plus d'un besoin
     * - un besoin ne peut pas être affecté à plus d'un salarié
     * un salrié peut seulement etre affecté à un besoin si il a la compétence nécessaire
     * @param besoins la liste des besoins à affecter
     * @param salaries la liste des salariés à qui on peut affecter les besoins
     * @return la liste des affectations
     */
    public List<Affectation> lancerCalcul (List<Besoin> besoins, List<Salarie> salaries) {

        //on crée une liste d'affectation finale
        List<Affectation> affectations = new ArrayList<Affectation>();

        //on crée une liste des besoins non affectés
        List<Besoin> besoinsNonAffectes = new ArrayList<Besoin>(besoins);

        //on trie les interets des salariés (Map) par type de besoin et par ordre decroissant
        salaries.forEach(salarie -> salarie.competences().entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(e -> System.out.println(e.getKey() + " : " + e.getValue())));

        //on parcourt les besoins non affectés
        while (!besoinsNonAffectes.isEmpty()) {

            //on récupère le premier besoin non affecté
            Besoin besoin = besoinsNonAffectes.get(0);

            //on récupère les compétences nécessaires pour ce besoin
            Competence competenceBesoin = besoin.competence();

            //on récupère les salariés qui ont cette compétence
            List<Salarie> salariesCompetents = salaries.stream().filter(salarie -> salarie.competences().containsKey(competenceBesoin)).collect(Collectors.toList());

            //on trie les salariés compétents par ordre croissant de compétence
            salariesCompetents.sort(Comparator.comparing(salarie -> salarie.competences().get(competenceBesoin)));

            //on récupère le premier salarié compétent
            Salarie salarie = salariesCompetents.get(0);

            //on crée une affectation
            Affectation affectation = new Affectation(besoin, salarie);

            //on ajoute l'affectation à la liste des affectations
            affectations.add(affectation);

            //on retire le besoin de la liste des besoins non affectés
            besoinsNonAffectes.remove(besoin);

            //on retire le salarié de la liste des salariés
            salaries.remove(salarie);

        }
        return affectations;
    }

}
