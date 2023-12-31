/**
 * Package dédié à la configuration du microservice front.
 */
package com.mfront.microservicefront.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe de configuration pour les propriétés personnalisées du microservice front.
  * Cette classe est utilisée pour lier les propriétés spécifiées dans les fichiers de configuration
 * (par exemple, application.yml ou application.properties) à cette classe Java. Elle est annotée avec
 * {@code @Configuration} pour indiquer à Spring que c'est une classe de configuration, et avec
 * {@code @ConfigurationProperties} pour spécifier le préfixe des propriétés à lier.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "com.mfront.microservicefront")
public class CustomProperties {
    private static final Logger logger = LoggerFactory.getLogger(CustomProperties.class);

    /**
     * Chemin du gateway.
     * Cette propriété est utilisée pour spécifier le chemin du gateway
     * que le microservice front pourrait utiliser.
     */
    private String gatewayPath;

    private String gatewayPathRedirect;

    /**
     * Récupère le chemin du gateway.
     *
     * @return le chemin du gateway.
     */
    public String getGatewayPath() {
        return gatewayPath;
    }

    /**
     * Définit le chemin du gateway.
     *
     * @param gatewayPath le nouveau chemin du gateway à définir.
     */
    public void setGatewayPath(String gatewayPath) {
        logger.info("Mise à jour du chemin du gateway : {}", gatewayPath);
        this.gatewayPath = gatewayPath;
    }

    /**
     * Récupère le chemin du gatewayrReirect.
     *
     * @return le chemin du gatewayRedirect.
     */
    public String getGatewayPathRedirect() {
        return gatewayPathRedirect;
    }

    /**
     * Définit le chemin du gateway.
     *
     * @param gatewayPathRedirect le nouveau chemin du gateway à définir.
     */
    public void setGatewayPathRedirect(String gatewayPathRedirect) {
        this.gatewayPathRedirect = gatewayPathRedirect;
    }
}
