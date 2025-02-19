/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.exception;

import org.springframework.http.HttpStatusCode;

import it.csi.pgmeas.commons.dto.Errore;

public class PgmeasRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6855118843867377497L;
	private HttpStatusCode status;
	private Errore errore;
	
	public PgmeasRuntimeException(RuntimeException e, HttpStatusCode status, Errore errore) {
		super(e);
		this.status = status;
		this.errore = errore;
	}
	
	public PgmeasRuntimeException(String message, HttpStatusCode status, Errore errore) {
		super(message);
		this.status = status;
		this.errore = errore;
	}
	
	public PgmeasRuntimeException(String message) {
		super(message);
	}

	public PgmeasRuntimeException(RuntimeException e) {
		super(e);
	}

	public PgmeasRuntimeException() {
		super();
	}

	public HttpStatusCode getStatus() {
		return status;
	}

	public void setStatus(HttpStatusCode status) {
		this.status = status;
	}

	public Errore getErrore() {
		return errore;
	}

	public void setErrore(Errore errore) {
		this.errore = errore;
	}
}