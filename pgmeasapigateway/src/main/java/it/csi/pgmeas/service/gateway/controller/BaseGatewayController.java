/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import it.csi.pgmeas.commons.dto.Errore;
import it.csi.pgmeas.commons.util.CommonConstants;
import it.csi.pgmeas.service.gateway.exception.ApiGatewayCustomException;

public class BaseGatewayController implements CommonConstants {
	protected ResponseEntity<?> handleError(){
		Errore errore = new Errore();
		errore.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errore.setCode("Errore generico");
		errore.setTitle("Errore generico dell'applicazione");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errore);
	}
	
	protected ResponseEntity<?> handleExceptionRE(Exception e){
		Errore errore = new Errore();
		errore.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errore.setCode("Errore generico");
		errore.setTitle(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errore);
	}
	
	protected ResponseEntity<?> handleApiGatewayCustomException(ApiGatewayCustomException e){
		Errore errore = new Errore();
		errore.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errore.setCode("Errore generico");
		errore.setTitle(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errore);
	}
	
	protected ResponseEntity<?> handleHttpStatusCodeException(HttpStatusCodeException e) {
		return ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders())
				.body(e.getResponseBodyAsString());
	}
	
	protected ResponseEntity<?>handleHttpClientErrorException(HttpClientErrorException e){
		return ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders())
				.body(e.getResponseBodyAsString());
	}
}
