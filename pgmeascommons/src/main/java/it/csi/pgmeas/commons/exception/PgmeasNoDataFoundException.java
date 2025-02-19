/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.exception;

import org.springframework.http.HttpStatus;

import it.csi.pgmeas.commons.dto.Errore;

public class PgmeasNoDataFoundException extends PgmeasException {

	private static final String MESSAGE = "Dato non trovato";
	/**
	 * 
	 */
	private static final long serialVersionUID = 840764506779068428L;

	public PgmeasNoDataFoundException() {
		super(MESSAGE);

		Errore errore = new Errore();
		errore.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errore.setCode(MESSAGE);
		errore.setTitle(MESSAGE);
		this.setErrore(errore);

		this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
