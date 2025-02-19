/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.configurations;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import jakarta.servlet.Filter;

@Configuration
public class GlobalConf {
	
	/**
	 * Configura un filtro per applicare lo Shallow ETag Header sulle richieste HTTP.
	 * 
	 * Questo filtro viene utilizzato per:
	 * - Generare un'intestazione HTTP `ETag` basata sul contenuto della risposta,
	 *   che può essere utile per implementare meccanismi di caching lato client.
	 * - Risolvere il problema di `Transfer-Encoding: chunked` bufferizzando il contenuto 
	 *   della risposta prima di inviarlo, garantendo che venga calcolata una lunghezza 
	 *   della risposta (`Content-Length`).
	 * 
	 * Specifiche della configurazione:
	 * - Applicato solo agli endpoint con URL che iniziano con `/api/*`.
	 * 
	 * Questo approccio è utile in contesti dove un'applicazione Spring fa da intermediario
	 * per altre applicazioni, aggregando e restituendo risultati, poiché riduce il rischio 
	 * di errori legati al chunking nelle risposte.
	 * 
	 * @return una configurazione del filtro con ShallowEtagHeaderFilter applicato.
	 */
	 @Bean
	    public FilterRegistrationBean<?> registerFilters()
	    {
	        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
	        registration.setFilter(new ShallowEtagHeaderFilter());
	        registration.addUrlPatterns("/*");
	        return registration;
	    }
}
