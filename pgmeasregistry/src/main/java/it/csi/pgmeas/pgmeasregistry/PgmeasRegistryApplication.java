/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(scanBasePackages = { "it.csi.pgmeas" })
@EnableRetry
@EntityScan("it.csi.pgmeas.commons.model")
@EnableJpaRepositories(basePackages = "it.csi.pgmeas")
@EnableAutoConfiguration(exclude = { UserDetailsServiceAutoConfiguration.class })
public class PgmeasRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PgmeasRegistryApplication.class, args);
	}

}
