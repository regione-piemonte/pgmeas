/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.exception;

public class NotificatoreException extends Exception { /**
	 * 
	 */
	private static final long serialVersionUID = -5931527514305291165L;

	String errorPayload;
	
	public NotificatoreException(Exception e) {
		super(e);
	}
	
	public NotificatoreException(Exception e,String errorPayload) {
		super(e);
		this.errorPayload=errorPayload;
	}
	public NotificatoreException(String message) {
		super(message);
	}
	
	public NotificatoreException(String message,String errorPayload) {
		super(message);
		this.errorPayload=errorPayload;
	}
	
	public NotificatoreException(String message,Exception e,String errorPayload) {
		super(message,e);
		this.errorPayload=errorPayload;
	}
	
	public NotificatoreException() {
		super();
	}


	public String getErrorPayload() {
		return errorPayload;
	}


	public void setErrorPayload(String errorPayload) {
		this.errorPayload = errorPayload;
	}
	
}
