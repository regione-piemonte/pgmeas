/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.exception;

import org.springframework.http.HttpStatusCode;

import it.csi.pgmeas.commons.dto.Errore;

public class PgmeasException extends Exception {
	private static final long serialVersionUID = -4292457204639395316L;

	private HttpStatusCode status;
	private Errore errore;
	
	public PgmeasException(Exception e, HttpStatusCode status, Errore errore) {
		super(e);
		this.status = status;
		this.errore = errore;
	}
	
	public PgmeasException(String message, HttpStatusCode status, Errore errore) {
		super(message);
		this.status = status;
		this.errore = errore;
	}
	
	public PgmeasException(String message) {
		super(message);
	}

	public PgmeasException(Exception e) {
		super(e);
	}

	public PgmeasException() {
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