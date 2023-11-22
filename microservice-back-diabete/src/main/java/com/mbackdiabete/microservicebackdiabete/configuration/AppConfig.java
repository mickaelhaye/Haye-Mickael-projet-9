package com.mbackdiabete.microservicebackdiabete.configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Classe AppConfig pour configurer les beans nécessaires au fonctionnement du microservice.
 * Cette classe utilise Spring Framework pour définir et configurer les beans.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Configuration
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    /**
     * Crée et configure un bean RestTemplate pour les appels HTTP.
     * Le RestTemplate est un client HTTP qui facilite la communication avec les services web.
     *
     * @return RestTemplate - l'instance de RestTemplate configurée pour l'utilisation dans le microservice.
     */
    @Bean
    public RestTemplate restTemplate() {
        logger.info("Création du bean RestTemplate pour les appels HTTP.");
        return new RestTemplate();
    }
}
