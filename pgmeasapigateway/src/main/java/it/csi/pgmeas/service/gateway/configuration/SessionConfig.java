/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@Configuration
@EnableJdbcHttpSession
public class SessionConfig {

	@Value("${session.datasource.embedded:false}")
	private boolean embeddedDBSession;

	DataSource createEmbeddedDB() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
		// ;DB_CLOSE_ON_EXIT=TRUE;
//				jdbc:h2:mem:testdb
				.setName("testdb;MODE=PostgreSQL;"
//						+ "INIT=create schema if not exists "
//						+ "schema_a\\;create schema if not exists schema_b;" 
						+ "DB_CLOSE_DELAY=-1;")
				.addScript("sql/SPRING_SESSION.sql") //
				.build();
	}

	@Bean
	@ConfigurationProperties(prefix = "session.datasource")
	@SpringSessionDataSource
	DataSource dataSource() {
		// DataSource dataSource = createEmbeddedDB();
		DataSource dataSource = (embeddedDBSession ? createEmbeddedDB() : DataSourceBuilder.create().build());
		return dataSource;
	}
}
