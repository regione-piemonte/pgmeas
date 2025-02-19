/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	@Value("${configuratore.username}")
	private String usernameConfiguratore;
	@Value("${configuratore.password}")
	private String passwordConfiguratore;
	
		
	  @Bean(name = "notificatore")
	  @Primary
	  public RestTemplate notificatoreRestTemplate(RestTemplateBuilder builder) {
	    return builder.build();
	  }

	  @Bean(name = "configuratore")
	  public RestTemplate configuratoreRestTemplate(RestTemplateBuilder builder) {
	    return builder.basicAuthentication(usernameConfiguratore, passwordConfiguratore) 
                .build();
	  }
	
	
}
