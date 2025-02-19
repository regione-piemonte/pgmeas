/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SpringSecurityConfig {

	@Value("${IrideIdAdapterFilter.devmode:false}")
	private boolean devmode;

	// Questo bean attiva la protezione da attacchi CSRF/XSRF:
	// https://it.wikipedia.org/wiki/Cross-site_request_forgery
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
		http.csrf(AbstractHttpConfigurer::disable);
		//http.cors(Customizer.withDefaults()) ;
		
		http.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration configuration = new CorsConfiguration();

			if (devmode) {
				configuration.setAllowedOrigins(Arrays.asList(//
						"http://localhost:4200", //
						"https://tst-pgmeas.ruparpiemonte.it", //
						"https://coll-pgmeas.ruparpiemonte.it", //
						"https://xp-pgmeas.ruparpiemonte.it", //
						"https://pgmeas.ruparpiemonte.it"));
			} else {
				// configuration.setAllowedOrigins(Arrays.asList("https://*.ruparpiemonte.it"));
				configuration.setAllowedOrigins(Arrays.asList( //
						"https://tst-pgmeas.ruparpiemonte.it", //
						"https://coll-pgmeas.ruparpiemonte.it", //
						"https://xp-pgmeas.ruparpiemonte.it", //
						"https://pgmeas.ruparpiemonte.it"));

			}
			configuration.setAllowedMethods(Arrays.asList("*"));
			configuration.setAllowedHeaders(Arrays.asList("*"));
			configuration.setExposedHeaders(Arrays.asList("*"));
			return configuration;
		}));
		

		return http.build();
	}

}
