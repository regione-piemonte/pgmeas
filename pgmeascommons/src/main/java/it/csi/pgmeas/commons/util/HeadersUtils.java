/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util;

import static it.csi.pgmeas.commons.util.dev.UserDevUtils.gatFakeMarkerIdentita;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HeadersUtils implements CommonConstants {
	// import static it.csi.pgmeas.commons.util.HeadersUtils.*;
	private static final Logger LOG = LoggerFactory.getLogger(HeadersUtils.class);

	private static boolean devmode = false;

	public static void setDevmode(boolean devmode) {
		HeadersUtils.devmode = devmode;
	}

	public static HttpHeaders getHttpHeaders(HttpServletRequest request) throws CustomLoginException {
		UserInfoRecord user = getUser(request);
		return getHttpHeaders(request, user);
	}

	/**
	 * usato dal proxy per perparare le header alle chiamate dei servizi di be
	 * (project e repository).
	 * 
	 * @param request
	 * @return
	 * @throws CustomLoginException
	 */
	public static HttpHeaders getHttpHeaders(HttpServletRequest request, UserInfoRecord user)
			throws CustomLoginException {
		HttpHeaders headers = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			if (!headerName.equals("Accept-Encoding")) {
				// for issues with gzip compression sent from the target
				// resource
				headers.set(headerName, request.getHeader(headerName));
			}
		}

		if (devmode) {
			LOG.info("[HEADER-UTIL] *** dev mod!");
			headers.set(AUTH_ID_MARKER, gatFakeMarkerIdentita());
		}

		// ripropongo il jwt settato in sessione

		try {
			String jwtuser = null;
			if (user != null) {
				// viene passato come timeout API_JWT_TOKEN_VALIDITY, per il jwt per altri
				// servizi viene generato un token con una validità più breve
				jwtuser = JWTUtils.doGenerateToken(JWS_USER_SUBJECT, user, API_JWT_TOKEN_VALIDITY);
			}
			if (jwtuser != null) {
				headers.set(JWT_USER_HEADER, jwtuser);
			}
		} catch (Exception e) {
			LOG.warn("[HeadersUtils::getHttpHeaders] ", e);
			throw new CustomLoginException(e);
		}
		return headers;

	}

	public static UserInfoRecord getUser(HttpServletRequest httpRequest) throws CustomLoginException {
		return getUser(httpRequest, true);
	}

	public static UserInfoRecord getUser(HttpServletRequest httpRequest, boolean isSessionEnabled)
			throws CustomLoginException {
		UserInfoRecord userInfo = null;
		try {
			String jwt = httpRequest.getHeader(JWT_USER_HEADER);
			if (jwt != null) {
				userInfo = JWTUtils.getDataFromJWT(jwt, UserInfoRecord.class);
			} else if (isSessionEnabled) {
				LOG.warn(
						"[HeadersUtils::getUser] [!!!!] c'è stato un passaggio dalla sessione per recuperare le info dell'utente");
				userInfo = (UserInfoRecord) httpRequest.getSession().getAttribute(USER_SESSION_ATTR);

			}
		} catch (Exception e) {
			LOG.warn("[HeadersUtils::getUser] ", e);
			throw new CustomLoginException(e);
		}

		return userInfo;
	}

//	public static UserInfoRecord getUser(HttpHeaders httpHeaders) throws CustomLoginException {
//		try {
//			String jwt = httpHeaders.getHeaderString(JWT_USER_HEADER);
//			return JWTUtils.getDataFromJWT(jwt, UserInfoRecord.class);
//		} catch (Exception e) {
//			LOG.warn("[HeadersUtils::getUser] ", e);
//			throw new CustomLoginException(e);
//		}
//	}

	/**
	 * Serve a peparare la risposta al front-end. in maniera temporanea viene
	 * gestito anche l'utente in sessione.
	 * 
	 * @param httpRequest
	 * @param httpResponse
	 * @param user
	 */
	public static void refreshUserAndJwt(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			UserInfoRecord user) {
		httpRequest.getSession().setAttribute(USER_SESSION_ATTR, user);
		try {
			// viene passato come timeout BFF_JWT_TOKEN_VALIDITY, per il jwt del front end
			// viene generato un token con una validità più lunga
			String token = JWTUtils.doGenerateToken(JWS_USER_SUBJECT, user, BFF_JWT_TOKEN_VALIDITY);
			httpResponse.setHeader(JWT_USER_HEADER, token);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			LOG.error("Errore nella generazione del token - {}", e);
		}
	}

}
