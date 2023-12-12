# Application pour aider à détecter le diabète type2

Description:
Beaucoup de patients souffrent de mauvais choix nutritionnels. Ils
choisissent souvent des produits bon marché, riches en sucre et en calories, au lieu d’opter
pour un régime alimentaire sain avec des légumes. À cause de ces choix, ils ont tendance à
être plus exposés à certaines maladies, dont le diabète de type 2.
L'application doit aider less médecins à identifier les patients les plus à risque. Le médecin doit pouvoir renseigner les informations de base sur un patient (nom, age, etc.).
Il doit aussi pouvoir renseigner des notes sur ses patients. Avec ce données l'application doit fournir un rapport indiquant la probabilité qu'un patient développe un diabète.

# Architecture:
	-Microservice Back Diabete : Gère les fonctionnalités pour déterminer les risques de diabète.
 	-Microservice Back Note : Gère les notes concernant les patients.
 	-Microservice Back Patient : Gère les informations des patients.
  	-Microservice Front : Interface utilisateur du projet.
    -Microservice Gateway : Gère les requêtes et redirige vers les microservices appropriés.

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

# Documentation des services web RESTful, Swagger:
	-Microservice Back Diabete: http://localhost:9004/swagger-ui.html
 	-Microservice Back Note: http://localhost:9003/swagger-ui.html
  	-Microservice Back Patient : http://localhost:9001/swagger-ui.html
	
