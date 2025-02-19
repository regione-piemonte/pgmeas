/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.be.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.pgmeasregistry.be.PingApi;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class PingApiServiceImpl implements PingApi {

	@Override
	public ResponseEntity<?> ping(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return ResponseEntity.ok().header("someheader", "" + System.currentTimeMillis()).body("PING OK!");
	}

}
