/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.exception;

public class BatchBuilderException extends Exception { 
	
	private static final long serialVersionUID = -2841485244525496445L;

	public BatchBuilderException(Exception e) {
		super(e);
	}
	
	public BatchBuilderException(String message) {
		super(message);
	}
	
	public BatchBuilderException(String message,Exception e) {
		super(message,e);
	}
	
	public BatchBuilderException() {
		super();
	}

}
