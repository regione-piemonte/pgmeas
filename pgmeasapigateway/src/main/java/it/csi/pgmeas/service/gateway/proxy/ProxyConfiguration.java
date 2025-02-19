/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties(RoutesConfiguration.class)
public class ProxyConfiguration {
	private static final Logger LOG = LoggerFactory.getLogger(ProxyConfiguration.class);
	public static final String PROXY_PATH = "proxy";

	@Autowired
	private RoutesConfiguration routesConfiguration;

	@PostConstruct
	private void init() {
		LOG.info(" ## ProxyConfiguration - init");
	}

	@Bean
	ProxyUrlModel proxyUrlModel() {
		ProxyUrlModel proxyUrlModel = new ProxyUrlModel();
		if (routesConfiguration.routes() != null) {
			routesConfiguration.routes().forEach((key, route) -> proxyUrlModel.registerMapping(route.regExp(),
					route.targetHost(), route.targetUri()));
		}
		
		return proxyUrlModel;
	}
}
