/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.dev.UserDevUtils;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * mentre il filtro del gateway funzionerà con shibbolet, jwt e parametro di
 * lcce, quelle delle api solo col jwt.
 */
public class APISecurityFilterUtils implements CommonConstants {
	private static final Logger LOG = LoggerFactory.getLogger(APISecurityFilterUtils.class);

	private static boolean devmode = false;

	public static void setDevmode(boolean devmode) {
		APISecurityFilterUtils.devmode = devmode;
	}

	public static UserInfoRecord getUser(HttpServletRequest httpRequest) throws CustomLoginException {
		return (UserInfoRecord) httpRequest.getAttribute(UTENTE_REQ_ATTR);
	}
	
	public static void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn)
			throws IOException, ServletException {

		if (!(req instanceof HttpServletRequest)) {
			fchn.doFilter(req, resp);
			return;
		}

		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hresp = (HttpServletResponse) resp;

		try {
			UserInfoRecord utente = HeadersUtils.getUser(hreq, false);

			if (utente == null && devmode) {
				utente = UserDevUtils.createDevUser(hreq, UserDevUtils.gatFakeIdentita());
				LOG.warn("[APISecurityFilterUtils::doFilter] creata identià finta");
			}

			if (utente != null) {
				LOG.info("[APISecurityFilterUtils::doFilter] riconusciuto utente");
				LOG.debug("[APISecurityFilterUtils::doFilter] utente " + utente.toString());
				hreq.setAttribute(UTENTE_REQ_ATTR, utente);
				fchn.doFilter(hreq, hresp);
			} else {
				responseWError(hresp);
			}
		} catch (Exception e) {
			LOG.warn("[APISecurityFilterUtils::doFilter] Errore nella autenticazione");
			responseWError(hresp);
		}

	}

	private static void responseWError(HttpServletResponse hresp) throws IOException {
		hresp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter pw = hresp.getWriter();
		pw.append("{\"error\": \"Tentativo di accesso a un servizio senza token di sicurezza\"}");
	}

}
