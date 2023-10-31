/**
 * Package dédié à la configuration du microservice front.
 */
package com.mfront.microservicefront.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Classe de configuration pour les beans utilisés dans le microservice front.
 * <p>
 * Cette classe est utilisée pour définir et configurer les beans nécessaires
 * pour le bon fonctionnement de l'application. Elle est annotée avec
 * {@code @Configuration} pour indiquer à Spring que c'est une classe de configuration.
 * </p>
 *
 * @author mickael hayé
 * @version 1.0
 */
@Configuration
public class AppConfig {

    /**
     * Crée et retourne un bean {@code RestTemplate}.
     * <p>
     * Le bean {@code RestTemplate} est utilisé pour effectuer des appels HTTP
     * dans une application Spring. Cette méthode crée une nouvelle instance de
     * {@code RestTemplate} et la retourne pour être gérée par le conteneur Spring.
     * </p>
     *
     * @return une nouvelle instance de {@code RestTemplate}.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
