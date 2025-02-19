/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.exception;

import it.csi.pgmeas.commons.exception.PgmeasException;

public class ApiGatewayCustomException extends PgmeasException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiGatewayCustomException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApiGatewayCustomException(Exception cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ApiGatewayCustomException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
