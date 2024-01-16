# Gestion des Fermes

Ce projet est une application web développée avec JHipster, React, Spring Boot et MySQL. Il offre une plateforme complète pour la gestion des fermes, permettant aux utilisateurs de s'inscrire, de visualiser leur profil, de gérer les fermes, les parcelles, les plantes, de planter des plantes, de consulter l'historique des plantations, et plus encore. Les administrateurs ont également la possibilité de gérer les utilisateurs, les types de plantes et d'accéder aux statistiques.

## Modèle de Données

### Ferme

- **fermeLibelle:** Libellé de la ferme.
- **photo:** Image de la ferme (au format Blob).

### Parcelle

- **parcelleLibelle:** Libellé de la parcelle.
- **photo:** Image de la parcelle (au format Blob).

### Plante

- **planteLibelle:** Libellé de la plante.
- **racine:** Type de racine de la plante.
- **photo:** Image de la plante (au format Blob).

### TypePlante

- **nom:** Nom du type de plante.
- **humiditeMax:** Humidité maximale requise.
- **humiditeMin:** Humidité minimale requise.
- **temperature:** Température optimale requise.

### Plantage

- **date:** Date du plantage.
- **nombre:** Nombre de plantes plantées.




**Diagramme de cas d’utilisation**
![image](https://github.com/Ghaziyassine/Projet-Gestion_des_fermes/assets/114885285/8278821c-5cb4-4932-8adc-35767ec71d76)

**Diagramme de classe**
![image](https://github.com/Ghaziyassine/Projet-Gestion_des_fermes/assets/114885285/a0c190db-7ede-45e0-8988-fc23e1e5e70f)






## Fonctionnalités

- **Inscription et Profil**
  - Permet aux utilisateurs de s'inscrire et de gérer leur profil.

- **Gestion des Fermes**
  - Ajouter, modifier et supprimer des fermes.

- **Gestion des Parcelles**
  - Ajouter, modifier et supprimer des parcelles.

- **Gestion des Plantes**
  - Ajouter, modifier et supprimer des plantes.
  - Planter des plantes dans les parcelles.

- **Gestion des Types de Plantes**
  - Ajouter, modifier et supprimer des types de plantes.

- **Historique de Plantage**
  - Consulter l'historique des plantations.

- **Administration**
  - Gestion des utilisateurs.
  - Accès aux statistiques.

## Prérequis
- Maven
- Node.js
- Java
- MySQL


## Utilisation

1. Lancez le serveur back-end: `./mvnw`
2. Lancez l'application front-end: `npm start`
3. Accédez à l'application dans votre navigateur: `http://localhost:9000`

## Installation

### Utilisation de Docker

1. Assurez-vous d'avoir Docker installé sur votre machine.

2. Clonez ce dépôt: 

    ```bash
    git clone https://github.com/Ghaziyassine/Projet-Gestion_des_fermes.git
    ```

4. Accédez au répertoire du projet: 

     ```bash
    cd Projet-Gestion_des_fermes
    ```

5. Utilisez Docker Compose pour construire et démarrer les conteneurs:

    ```bash
    docker-compose up
    ```

    Ceci lancera les conteneurs nécessaires, y compris la base de données MySQL, le serveur back-end Spring Boot, et l'application front-end React.

6. Lorsque les conteneurs sont en cours d'exécution, accédez à l'application dans votre navigateur: `http://localhost:8080`

### Installation Manuelle

Si vous préférez ne pas utiliser Docker, vous pouvez également installer manuellement les dépendances front-end et back-end comme indiqué précédemment.

1. Clonez ce dépôt: `git clone  https://github.com/Ghaziyassine/Projet-Gestion_des_fermes.git`

2. Accédez au répertoire du projet: `cd  Projet-Gestion_des_fermes`

3. Installez les dépendances front-end: `npm install`

4. Installez les dépendances back-end: `./mvnw`

5. Configurez la base de données dans `app.yml`  avec vos informations de connexion MySQL.

6. Lancez le serveur back-end: `./mvnw`

7. Lancez l'application front-end: `npm start`

8. Accédez à l'application dans votre navigateur: `http://localhost:9000` ou  `http://localhost:8080` 



## Comptes par défaut

### Administrateur
- **Nom d'utilisateur:** admin@admin
- **Mot de passe:** admin@admin

### Utilisateur standard
- **Nom d'utilisateur:** user
- **Mot de passe:** user
  
![image](https://github.com/Ghaziyassine/Projet-Gestion_des_fermes/assets/114885285/0f745263-7124-474d-8e94-c0df7e5ff7bb)


## Demonstration

### Gestion par compte user



https://github.com/Ghaziyassine/Projet-Gestion_des_fermes/assets/114885285/9f63592d-d4f8-4cbe-ac20-13f49d925c4c


### Gestion par compte admin


https://github.com/Ghaziyassine/Projet-Gestion_des_fermes/assets/114885285/2cb3bdb6-a1db-4ecc-a2f3-f6ca4e682fb0

### Filtrage



https://github.com/Ghaziyassine/Projet-Gestion_des_fermes/assets/114885285/99c43ef9-3c1c-40f9-a48d-2c21df433275



