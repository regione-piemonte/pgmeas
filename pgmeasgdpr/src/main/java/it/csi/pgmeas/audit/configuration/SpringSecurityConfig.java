/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.audit.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SpringSecurityConfig {
	
	@Value("${AbstractHttpConfigurer.disable:false}")
	private boolean csrfDisable;
	
	// Questo bean attiva la protezione da attacchi CSRF/XSRF: https://it.wikipedia.org/wiki/Cross-site_request_forgery
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		if (csrfDisable) {
			http.csrf(AbstractHttpConfigurer::disable);
		} else {
			http.csrf(csrf -> csrf.csrfTokenRepository(

					CookieCsrfTokenRepository.withHttpOnlyFalse()
				// HttpOnly, di default è true, e NON permette l'accesso via Javascript al cookie XSRF-TOKEN.
				// Il client Angular necessita per il funzionamento delle chiamate PUT/POST/DELETE di accedere via javascript 
				// (più precisamente attraverso un interceptor definito nel modulo HttpClientXsrfModule) a
				// questo Cookie, quindi è necessario impostare HttpOnly=false per permettere il corretto funzionamento di un client
				// Angular.
				
				// https://angular.io/guide/http#security-xsrf-protection
				
				
				));
		}
		return http.build();
	}
	
	
}
