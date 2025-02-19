/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.proxy;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import it.csi.pgmeas.service.gateway.proxy.record.RouteConfigRecord;

@ConfigurationProperties
public record RoutesConfiguration(Map<String, RouteConfigRecord> routes) {

}
