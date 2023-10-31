/**
 * Package principal du microservice back-end pour la gestion des patients.
 */
package com.mbackpatient.microservicebackpatient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de démarrage de l'application Microservice Back-End Patient.
 * <p>
 * Cette classe est le point d'entrée pour l'exécution du microservice dédié
 * à la gestion des patients. Elle utilise le mécanisme de démarrage de Spring Boot
 * pour initialiser et exécuter l'application.
 * </p>
 *
 * @author mickael hayé
 * @version 1.0
 */
@SpringBootApplication
public class MicroserviceBackPatientApplication {

    /**
     * La méthode principale qui déclenche l'exécution de l'application.
     *
     * @param args Les arguments de la ligne de commande. Non utilisés dans cette application.
     */
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceBackPatientApplication.class, args);
    }
}
