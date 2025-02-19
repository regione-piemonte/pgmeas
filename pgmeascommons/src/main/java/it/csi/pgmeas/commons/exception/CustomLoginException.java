/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.exception;

public class CustomLoginException extends PgmeasException {

	private static final long serialVersionUID = -3209110718614480780L;

	public CustomLoginException(String message) {
		super(message);
	}

	public CustomLoginException(Exception e) {
		super(e);
	}

}