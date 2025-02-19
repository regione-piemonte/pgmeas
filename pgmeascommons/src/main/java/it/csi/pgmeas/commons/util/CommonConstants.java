/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util;

public interface CommonConstants {

	public static final String IRIDE_ID_REQ_ATTR = "iride2_id";
	public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";
	public static final String AUTH_ID_MARKER2 = "shib-iride-identitadigitale";
	public static final String UTENTE_REQ_ATTR = "utente";

	public static final String JWT_USER_HEADER = "jwt-user";
	public static final String USER_SESSION_ATTR = "user";
	public static final String JWS_USER_SUBJECT = "pgmeas-user";
	public static final String LCCE_TOKEN = "lcceToken";
	public static final String UTF_8 = "UTF-8";

	public static final long BFF_JWT_TOKEN_VALIDITY = 120L * 60L * 1000L; // secondi
	public static final int API_JWT_TOKEN_VALIDITY = 60 * 1000; // secondi

}
