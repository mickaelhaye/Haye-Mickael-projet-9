/**
 * Package dédié à la configuration du microservice front.
 */
package com.mfront.microservicefront.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Classe de configuration pour le microservice front.
 * Cette classe est marquée avec l'annotation {@link Configuration} pour indiquer qu'elle contient des définitions de bean pour l'application Spring.
 * Elle fournit la configuration nécessaire pour initialiser et utiliser divers composants et services dans l'application.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Configuration
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    /**
     * Crée et configure un bean RestTemplate pour les appels HTTP.
     * Le RestTemplate est un client HTTP centralisé utilisé pour effectuer des requêtes et consommer des services RESTful.
     * Cette méthode met en place un RestTemplate standard qui peut être injecté et réutilisé dans toute l'application.
     *
     * @return une nouvelle instance de {@code RestTemplate}.
     */
    @Bean
    public RestTemplate restTemplate() {
        logger.info("Création du bean RestTemplate pour les appels HTTP.");
        return new RestTemplate();
    }
}
