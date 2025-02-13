# Dépôt du projet de l'équipe xxx #

<!-- Supprimez les exemples dont vous n'avez pas besoin, gardez la -->
<!-- structure générale du document et répondez aux questions posées -->

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
<!-- Indiquez toutes les données utiles au test (identifiants, mots de -->
<!-- passes, données déjà saisies etc...), décrivez les éventuelles -->
<!-- fonctionnalités additionnelles -->

Il y avait du code que voilà :

```
git push -u origin master
```

Image de l'accueil de l'application web :

![Texte alternatif](accueil.png "Accueil de l'application")

##  Partie Optimisation ##

Pour cette partie, nous avons décider d'implémenter 3 algorithmes d'optimisation différents.


- les explications...
- ...de ce qu'on a fait

##  Déploiement ##

Pour cette partie, nous avons suivi l'approche :

- les explications...
- ...de ce qu'on a fait

