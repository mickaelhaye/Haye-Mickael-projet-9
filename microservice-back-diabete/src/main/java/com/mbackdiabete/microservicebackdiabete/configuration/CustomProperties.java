package com.mbackdiabete.microservicebackdiabete.configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Classe CustomProperties pour gérer les propriétés personnalisées du microservice.
 * Elle est annotée avec {@link Configuration} pour indiquer qu'elle contient des définitions de bean,
 * et {@link ConfigurationProperties} pour lier et valider les propriétés de configuration externes.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "com.mbackdiabete.microservicebackdiabete")
public class CustomProperties {
    private static final Logger logger = LoggerFactory.getLogger(CustomProperties.class);
    private String gatewayPath;
    private String termesDiabeteFilePath;

    /**
     * Obtient le chemin du fichier de termes liés au diabète.
     *
     * @return String - Le chemin du fichier de termes du diabète.
     */
    public String getTermesDiabeteFilePath() {
        return termesDiabeteFilePath;
    }

    /**
     * Définit le chemin du fichier de termes liés au diabète.
     *
     * @param termesDiabeteFilePath Le chemin du fichier de termes du diabète à définir.
     */
    public void setTermesDiabeteFilePath(String termesDiabeteFilePath) {
        logger.info("Mise à jour du chemin du fichier termesDiabete : {}", termesDiabeteFilePath);
        this.termesDiabeteFilePath = termesDiabeteFilePath;
    }

    /**
     * Obtient le chemin du gateway.
     *
     * @return String - Le chemin actuel du gateway.
     */
    public String getGatewayPath() {
        return gatewayPath;
    }

    /**
     * Définit le chemin du gateway.
     * Enregistre également cette action dans les logs.
     *
     * @param gatewayPath Le nouveau chemin du gateway à définir.
     */
    public void setGatewayPath(String gatewayPath) {
        logger.info("Mise à jour du chemin du gateway : {}", gatewayPath);
        this.gatewayPath = gatewayPath;
    }
}
