# Dépôt du projet de l'équipe Team Otés #

## Liste des membres ##

- AMAGLIO / Matias / IL2
- BITSINDOU / Maëlle / IL2
- HIRTZ / Grégoire / IL2
- PONROY / Célie / IL2
- GEORGES / Victor / DWM1
- PRYKHODKO / Yehor / DWM1
- MARTINEZ ORTUÑO / Galo / DWM1
- BERRET / Axel / DACS

## URL ##

- Dépôt GIT: https://github.com/celie-ponroy/crazy_charky_day-Team-Otes/tree/main
- Site de l'application finale: http://perfectmatch.ghmail.fr

##  Partie application Web ##

### Liste des numéros de fonctionnalités implantées ###
1. Création d’un besoin
2. Affichage de la liste des besoins d’un client
3. Affichage de la liste de tous les besoins
4. Création d’un salarié
5. Affichage de la liste des salariés
6. Gestion de la liste des compétences
7.  CRUD d’une nouvelle compétence lors de la saisie d’un salarié
8. Authentification
9. Lancement des affectations et affichage des résultats

### Commentaires additionnels ###

### Routes pour les Besoins
- `POST /besoins/` → Création d'un besoin *(Requiert l'authentification)*
- `GET /besoins/` → Récupération de la liste des besoins *(Requiert l'authentification)*
- `GET /clients/besoins/` → Récupération des besoins des utilisateurs *(Requiert l'authentification)*

### Routes pour les Salariés
- `POST /users/salaries/` → Ajout d'un salarié *(Requiert l'authentification)*
- `GET /users/salaries/` → Récupération des salariés *(Requiert l'authentification)*

### Routes pour l'Authentification
- `POST /register` → Inscription d'un utilisateur
- `POST /login` → Connexion d'un utilisateur

###  Routes pour les Compétences
- `GET /competences/` → Récupération des compétences *(Requiert l'authentification)*
- `POST /competences/` → Création d'une compétence *(Requiert l'authentification et le role admin)*
- `PATCH /competences/{id}/` → Mise à jour d'une compétence *(Requiert l'authentification et le role admin)*
- `DELETE /competences/{id}/` → Suppression d'une compétence *(Requiert l'authentification et le role admin)*

### Routes pour les Services
- `GET /users/{id}/services` → Récupération des services d'un utilisateur *(Requiert l'authentification)*

Image de l'accueil de l'application web :

![Texte alternatif](/image/accueil.png "Accueil de l'application")

##  Partie Optimisation ##

Pour cette partie, nous avons décider d'implémenter 3 algorithmes d'optimisation différents.

### Recuit simulé {#recuit-simulé} ###

L'algorithme **recuit simulé** permet de simuler le processus de refroidissement et de réchauffage des métaux en métallurgie.

L’algorithme repose sur deux paramètres principaux :

1. **La température**, qui évolue progressivement au fil des itérations.Et qui permet de se rapprocher plus ou moins vite vers l’optimum désiré.
2. **Un deuxième paramètre** est l’énergie, l’énergie est représenté par le score (nous avons mis énergie \= \- score pour le fonctionnement de l’algo)

Le processus commence avec un **état initial aléatoire**. À chaque itération :

* Un voisin de l’état actuel est choisi (en modifiant une affectation de la liste aléatoire)
* L’algorithme décide alors s’il conserve la solution actuelle ou s’il adopte la nouvelle, en fonction de la température et de l’énergie :
    * Si la nouvelle solution est meilleure, elle est automatiquement acceptée. Si elle est moins bonne, elle peut être acceptée avec une certaine probabilité, qui diminue à mesure que la température baisse.

### Algorithme Glouton ###
La classe AlgoGlouton met en œuvre un algorithme glouton pour attribuer des salariés à des besoins en fonction de leurs 
compétences, en cherchant à maximiser l'adéquation à chaque étape. L'algorithme commence par copier les listes des 
besoins et des salariés non affectés, puis entre dans une boucle où il sélectionne, à chaque itération, le salarié ayant
la meilleure compétence disponible correspondant à un besoin encore disponible. 
Une affectation est alors créée et les listes des besoins et salariés restants sont mises à jour. Ce processus continue 
jusqu'à ce qu'il ne soit plus possible de réaliser une affectation pertinente. À la fin, la méthode scoreAffectation() 
est appelée pour évaluer le score globale de l'affectation en fonction des règles à respecter. 
L'approche gloutonne garantit une répartition rapide et généralement efficace, mais elle présente certaines limites : 
elle ne prend en compte que des décisions localement optimales et ne revient pas sur ses choix précédents, ce qui peut 
empêcher d'obtenir une solution globalement optimale.

### Algorithme Gale et Shapley ###

L'algorithme de Gale-Shapley, également connu sous le nom d'algorithme des mariages stables, est une méthode permettant d'associer de manière optimale deux ensembles d'éléments en respectant leurs préférences respectives. Initialement conçu pour résoudre le problème du mariage stable, il est largement utilisé dans des domaines tels que l’affectation d'étudiants à des universités, la répartition de ressources ou encore l’appariement d’offres et de demandes sur le marché du travail.
Dans la classe GaleShapley, l’algorithme est adapté à l’affectation des salariés à des besoins en fonction de leurs compétences. L’approche suivie commence par trier les salariés par ordre alphabétique avant d’identifier, pour chacun d’eux, les besoins auxquels ils peuvent répondre. Ces besoins sont ensuite classés en fonction de leur pertinence, c’est-à-dire en tenant compte du niveau de compétence du salarié correspondant à chaque besoin.
L’algorithme procède ensuite de manière itérative tant qu’il reste des salariés non affectés et des besoins disponibles. Chaque salarié propose son aide au besoin le mieux adapté selon les critères de compatibilité. Si le besoin est libre, le salarié est directement affecté. Dans le cas contraire, le salarié doit proposer à un autre besoin. Une particularité de cette implémentation réside dans l’ajout d’un malus de -1 aux scores si le client possède deja un besoin occupé par un salarié, afin de diversifier les affectations et d’éviter une saturation autour des mêmes besoins.
La complexité de cet algorithme est principalement dominée par les étapes de tri, ce qui le place dans une classe de complexité O(n log n). 

##  Partie Déploiement ##

### Choix des technologies {#choix-des-technologies} ###

* Docker

Pour la partie déploiement, on s’est orienté vers une infrastructure en Docker. Cela se justifie par le fait que cette infrastructure sera facilement exportable sur d'autres supports.

Cette infrastructure était composé de conteneurs :

- frontend : possède une image alpine stable Nginx pour servir le contenu
- charly.db : base de données PostgreSQL
- php : contient le backend de l’application
- adminer : pour accéder à la base de données avec une interface

* VPS et CTX

En complément de cela, on disposait d’un VPS (Proxmox) ce qui nous a facilité le déploiement. On a donc mis en place CTX sur laquelle on avait la possibilité d’exposer un sous-domaine. Dans le cas de notre application à savoir “Perfect Match”, on a donc obtenu le domaine suivant :

[http://perfectmatch.ghmail.fr](http://perfectmatch.ghmail.fr)

* Passage de la partie Optimisation (Java) en Jar

Cela va nous permettre d’avoir un seul fichier .jar qui lancera l’API. 

### Perspectives d’amélioration ###

Si l’on souhaite améliorer le déploiement, il est possible de faire un reverse proxy qui permettrait d’avoir des meilleures performances en mettant en cache les réponses serveurs mais également en qualité de sécurité grâce au masquage des serveurs backend, dans le cas où l’on souhaite étendre l’infrastructure.


