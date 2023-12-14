# Application pour aider à détecter le diabète type2

Description:
Beaucoup de patients souffrent de mauvais choix nutritionnels. Ils
choisissent souvent des produits bon marché, riches en sucre et en calories, au lieu d’opter
pour un régime alimentaire sain avec des légumes. À cause de ces choix, ils ont tendance à
être plus exposés à certaines maladies, dont le diabète de type 2.
L'application doit aider les médecins à identifier les patients les plus à risque. Le médecin doit pouvoir renseigner les informations de base sur un patient (nom, age, etc.).
Il doit aussi pouvoir renseigner des notes sur ses patients. Avec ce données l'application doit fournir un rapport indiquant la probabilité qu'un patient développe un diabète.

# Architecture:
	-Microservice Back Diabete : Gère les fonctionnalités pour déterminer les risques de diabète.
 	-Microservice Back Note : Gère les notes concernant les patients.
 	-Microservice Back Patient : Gère les informations des patients.
  	-Microservice Front : Interface utilisateur du projet.
    -Microservice Gateway : Gère les requêtes et redirige vers les microservices appropriés.

 	Echanges:
     	-Microservice Front <-> Microservice Gateway <-> Microservice Back Note 
      	-Microservice Front <-> Microservice Gateway <-> Microservice Back Patient
       	-Microservice Front <-> Microservice Gateway <-> Microservice Back Diabete <-> Microservice Gateway <-> Microservice Back Note
		-Microservice Front <-> Microservice Gateway <-> Microservice Back Diabete <-> Microservice Gateway <-> Microservice Back Patient

# Solutions technique:
	-L’application est développée en Java(v17) avec le framework Spring(v3.1.5) et le pluggin Maven(v4.0.0).
	-Le code est versionné sur un repository Git.
	-Le code est couvert par des tests unitaires avec Junit.
	-La couverture de code est mesuré avec la librairie Jacoco.
	-Les logs sont gérés par Log4J.
 	-La documentation de ce programme est fournie via Javadoc.
 
# Dépendances par microservice:
	-Microservice Back Diabete:
 		-Spring Boot Starter Security
   		-Spring Boot Starter Web
	 	-Spring Boot Devtools
   		-Lombok
	 	-Springdoc OpenApiStarter Webmvc UI
   
   	-Microservice Back Note:
   		-Spring Boot Starter Data MongoDB
		-Spring Boot Starter Security
 		-Spring Boot Starter Web
  		-Spring Boot Devtools
   		-Lombok
		-Springdoc OpenAPI Starter Webmvc UI

  	-Microservice Back Patient:
  		-Spring Boot Starter Data JPA
		-MySQL Connector/J
  		-Spring Boot Starter Security
 		-Spring Boot Starter Web
  		-Spring Boot Devtools
   		-Lombok
		-Springdoc OpenAPI Starter Webmvc UI

   	-Microservice Front:
		-Spring Boot Starter Thymeleaf
  		-Spring Boot Starter Security
 		-Spring Boot Starter Web
  		-Spring Boot Devtools
   		-Lombok

  	-Microservice Gateway:
   		-Spring Cloud Starter Gateway
   		-Spring Boot Starter Security
 		-Spring Boot Devtools
   		-Lombok

# Installation:
	L'accès aux bases de données est protégée par des variables d'environnement:
   		-SP_USERNAME
	 	-SP_PASSWORD
   
	Backup des bases de données: 
  		-Microservice Back Note : src/main/resources/MongoDB
		-Microserice Back Patient : src/main/resources/Sql

# Utilisation:
  	Url de démarrage: http://localhost:9000/patientFront/list
  		-User: user1
  		-Password: 0241585915 (à enlever, juste pour faciliter l'utilisation à l'examinateur)

# Documentation des services web RESTful, Swagger:
	-Microservice Back Diabete: http://localhost:9004/swagger-ui.html
 	-Microservice Back Note: http://localhost:9003/swagger-ui.html
  	-Microservice Back Patient : http://localhost:9001/swagger-ui.html

# Docker:
	Chaque microservice possède un fichier Dockerfile.
 	L'ensemble du projer possède un fichier docker-compose.yml.
  	Le fichier docker-compose.yml prend en compte la conteneurisation des bases de données.
   	Pour construire et démarrer les services:
    	-Installer Docker Desktop.
      	-Démarrer Doicker Desktop.
      	-Ouvrir un terminal à la racine du projet.
		-Exécuter 'docker-compose up --build' pour builder et démarrer les services.
  		-Exécuter 'docker-compose down' pour stopper les services.
    	-Exécuter 'docker-compose up' pour redémarrer les services.

# Green Code:
	Concernant le numérique, La fabrication des terminaux est la principale source d’impacts écologiques.
 	Pour agir sur cette source, le dévelloppeur  doit avoir une démarche écoconception.
  	Cett démarche vise notamment à diminuer l’obsolescence des terminaux. 
   	Il faut la prendre en compte dès la conception.
    Il faut des sites plus légers pour prolonger la durée de vie des terminaux.
    Encourager les utilisateurs à garder leur matériel (via des applications légères et efficaces).
 
	-3 leviers permettent d'alléger un site web:
		-Frugalité fonctionnelle: supprimer les fonctionnalités peu utilisées, performances des algorithmes.
		-Optimisation du contenant : Optimiser les codes sources serveur et client : inspecter le code pour identifier les éléments de code inutiles
		-Optimisation du contenu: définition des images, contenu statique.

  	-Coté front: 
		-Concevoir des applications légères et efficaces.
		-Eliminer les fonctionnalités non essentielles.
		-Support des anciens terminaux (mobiles, avec un réseau peu performant)
		-Limiter le poids de la page, le nombre de requètes serveur.
		-70 % des fonctionnalités demandées par les utilisateurs ne sont pas essentielles , 45 % ne sont jamais utilisées.

	-Coté back:
		-Données : durée de stockage des données, expiration des données.
		-Test de performances.

	-Pour notre application les points qui pourraient être intéressant d'analyser:
		-Dans le microservice Back Diabète, l'optimisation de l'algorithme de recherche des termes diabètes (test de performance).
		-Dans le microservice Front, il est possible de diminuer le nombre de requètes. Il y a une requête pour récupérer la liste des patients, et une requête pour récupérer un patient 			précis pour le mettre à jour, alors que l'on a déjà la liste avec tous les patients disponibles. Pour afficher la liste est ce qu’il est utile de récupérer l’intégralité des objets. 			Idem pour les notes.
 		-Dans le microservice Front, est ce que les touches Supprimer un patient et une note, sont bien utiles.
		-Dans le microservice Front, les touches visu fiche et note sont redondantes.
  		-Concernant la durée de vie des notes dans la base de données MongoDb, peut être qu'il serait pertinent de rajouter une donnée pour la durée de validité de la note.
