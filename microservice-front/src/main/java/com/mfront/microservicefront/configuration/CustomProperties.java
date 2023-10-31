/**
 * Package dédié à la configuration du microservice front.
 */
package com.mfront.microservicefront.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuration pour les propriétés personnalisées du microservice front.
 * <p>
 * Cette classe est utilisée pour lier les propriétés spécifiées dans les fichiers de configuration
 * (par exemple, application.yml ou application.properties) à cette classe Java. Elle est annotée avec
 * {@code @Configuration} pour indiquer à Spring que c'est une classe de configuration, et avec
 * {@code @ConfigurationProperties} pour spécifier le préfixe des propriétés à lier.
 * </p>
 *
 * @author mickael hayé
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "com.mfront.microservicefront")
public class CustomProperties {
    /**
     * Chemin du gateway.
     * <p>
     * Cette propriété est utilisée pour spécifier le chemin du gateway
     * que le microservice front pourrait utiliser.
     * </p>
     */
    private String gatewayPath;

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
        this.gatewayPath = gatewayPath;
    }
}
