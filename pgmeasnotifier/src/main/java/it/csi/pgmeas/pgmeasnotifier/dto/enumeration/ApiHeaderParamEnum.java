/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.dto.enumeration;


public enum ApiHeaderParamEnum {

	SHIB_IRIDE_IDENTITADIGITALE("Shib-Iride-IdentitaDigitale"),
	X_AUTHENTICATION("x-authentication"),
	AUTHORIZATION("Authorization"),
	CONTENT_TYPE("Content-Type"),
	X_FORWARDED_FOR("X-Forwarded-For"),
	X_CODICE_SERVIZIO("X-Codice-Servizio"),
	SHIB_IDENTITA_CODICEFISCALE("Shib-Identita-CodiceFiscale"),
	X_REQUEST_ID("X-Request-Id");
	
	
	private final String code;

	private ApiHeaderParamEnum(String inCode) {
		this.code = inCode;
	}

	public String getCode() {
		return code;
	}

	
}
