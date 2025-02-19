/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "it.csi.pgmeas" })
@EnableScheduling
@EntityScan("it.csi.pgmeas")
@EnableJpaRepositories(basePackages = "it.csi.pgmeas")
public class PgmeasNotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(PgmeasNotifierApplication.class, args);
	}

}