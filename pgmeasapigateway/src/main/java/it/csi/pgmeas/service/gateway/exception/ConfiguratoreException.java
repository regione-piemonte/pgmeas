/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.exception;

import it.csi.pgmeas.commons.exception.PgmeasException;

public class ConfiguratoreException extends PgmeasException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfiguratoreException(String message) {
		super(message);
	}

	public ConfiguratoreException(Exception e) {
		super(e);
	}	
}
