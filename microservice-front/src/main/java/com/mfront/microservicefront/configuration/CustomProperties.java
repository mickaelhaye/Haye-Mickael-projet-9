package com.mfront.microservicefront.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Cette classe permet de récupérer des informations du fichier
 * application.properties.
 * 
 * @author Mickael Hayé
 */

@Configuration
@ConfigurationProperties(prefix = "com.mfront.microservicefront")
public class CustomProperties {
	private String gatewayPath;
	public String getGatewayPath() {
		return gatewayPath;
	}

	public void setGatewayPath(String gatewayPath) {
		this.gatewayPath = gatewayPath;
	}





}
