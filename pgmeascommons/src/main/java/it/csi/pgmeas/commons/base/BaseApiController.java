/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.base;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.csi.pgmeas.commons.dto.Errore;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasRuntimeException;
import it.csi.pgmeas.commons.util.CommonConstants;
import it.csi.pgmeas.commons.util.JWTUtils;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

public class BaseApiController implements CommonConstants {

	protected UserInfoRecord getUserInfoFromToken(String jwt) throws Exception {
		JWTUtils.decodeJWT(jwt);
		return JWTUtils.getDataFromJWT(jwt, UserInfoRecord.class);
	}
	
	protected ResponseEntity<Errore> handleException(Exception e) {
		Errore errore = new Errore();
		errore.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errore.setCode("Errore generico");
		errore.setTitle(e.getMessage());
		
		return ResponseEntity.internalServerError()
				.header(HttpHeaders.CONTENT_LENGTH, getContentLength(errore))
				.body(errore);
	}
	
	protected ResponseEntity<?> handlePgmeasException(PgmeasException e) {
		return ResponseEntity.status(e.getStatus()).body(e.getErrore());
//				.header(HttpHeaders.CONTENT_LENGTH, getContentLength(e.getErrore()))
				
	}
	
	protected ResponseEntity<?> handlePgmeasRuntimeException(PgmeasRuntimeException e) {
		return ResponseEntity.status(e.getStatus()).body(e.getErrore());
	}
	private String getContentLength(Errore errore) {
		int length = errore.toString().getBytes().length;
		String contentLength = String.valueOf(length);
		return contentLength;
	}
}
