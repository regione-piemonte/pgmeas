/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.exception;

public class ConfiguratoreException extends Exception { /**
	 * 
	 */
	private static final long serialVersionUID = -5931527514305291165L;

	String errorPayload;
	
	public ConfiguratoreException(Exception e) {
		super(e);
	}
	
	public ConfiguratoreException(Exception e,String errorPayload) {
		super(e);
		this.errorPayload=errorPayload;
	}
	public ConfiguratoreException(String message) {
		super(message);
	}
	
	public ConfiguratoreException(String message,Exception e,String errorPayload) {
		super(message,e);
		this.errorPayload=errorPayload;
	}
	
	public ConfiguratoreException() {
		super();
	}


	public String getErrorPayload() {
		return errorPayload;
	}


	public void setErrorPayload(String errorPayload) {
		this.errorPayload = errorPayload;
	}
	
}
