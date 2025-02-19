/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.filter;

import static it.csi.pgmeas.commons.util.HeadersUtils.getUser;
import static it.csi.pgmeas.commons.util.HeadersUtils.refreshUserAndJwt;
import static it.csi.pgmeas.commons.util.dev.UserDevUtils.getShibbolethTokenDevMode;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.CommonConstants;
import it.csi.pgmeas.commons.util.HeadersUtils;
import it.csi.pgmeas.commons.util.dev.UserDevUtils;
import it.csi.pgmeas.commons.util.record.ErrorRecord;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.service.ConfiguratoreService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class UserIdAdapterFilter implements Filter, CommonConstants {
	private static final Logger LOG = LoggerFactory.getLogger(UserIdAdapterFilter.class);

	@Value("${IrideIdAdapterFilter.devmode:false}")
	private boolean devmode;

	@Autowired
	private ConfiguratoreService configuratoreService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// String devmodeParam = fc.getInitParameter(DEVMODE_INIT_PARAM);
		// this.devmode = "true".equalsIgnoreCase(devmodeParam);
	}

	@PostConstruct
	public void init() {
		LOG.debug(" **** DEV-MODE " + devmode);
		HeadersUtils.setDevmode(devmode);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)) {
			LOG.warn("[UserIdAdapterFilter::doFilter] chimata non HttpServletRequest");
			filterChain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String token = getShibboletToken(httpRequest);
		LOG.debug("doFilter - token: {} for url: {} ", token, httpRequest.getRequestURL());

		if (token == null) {
			// il marcatore deve sempre essere presente altrimenti e' una
			// condizione di errore (escluse le pagine home e di servizio)
			if (mustCheckPage(httpRequest.getRequestURI())) {
				// LOG.error("[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non
				// home e non di servizio senza token di sicurezza");

				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				PrintWriter writer = httpResponse.getWriter();
				writer.append(new ErrorRecord(
						"Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza")
						.toString());

				return;

				// throw new ServletException(
				// "Tentativo di accesso a pagina non home e non di servizio senza token di
				// sicurezza");
			}
		}

		Identita identita;
		try {
			identita = new Identita(normalizeToken(token));
		} catch (MalformedIdTokenException e) {
			LOG.error("Token iride non valido: {} - {}", token, e);
			// throw new ServletException("Token di sicurezza non corretto.");
			respWithError(httpResponse);
			return;
		}

		httpRequest.setAttribute(IRIDE_ID_REQ_ATTR, identita);

		// parte custom
		try {
			UserInfoRecord user = getUser(httpRequest);
			if (user == null) {
				if (devmode) {
					user = UserDevUtils.createDevUser(httpRequest, identita);
				} else {
					user = configuratoreService.getUserInfoRecord(httpRequest);
				}
			} else {
				user = configuratoreService.aggiornaProgrammazioneUtente(httpRequest, user);
			}
			if (!user.codiceFiscale().equalsIgnoreCase(identita.getCodFiscale()) && !devmode) {
				// eccezione
				throw new CustomLoginException("Utente variato, necessario nuovo login");
			}
			// refresh token
			refreshUserAndJwt(httpRequest, httpResponse, user);

			filterChain.doFilter(httpRequest, httpResponse);
		} catch (CustomLoginException e) {
			httpRequest.getSession().invalidate();
			respWithError(httpResponse);
		}

	}

	private static void respWithError(HttpServletResponse httpResponse) throws IOException {
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.setContentType("text/json");
		PrintWriter writer = httpResponse.getWriter();
		writer.append(new ErrorRecord("Token di sicurezza non valido").toString());
	}

	private boolean mustCheckPage(String requestURI) {
		// return requestURI.startsWith("/") &&
		// !requestURI.startsWith("/lgspaweb/api/test");
		return true;
	}

	@Override
	public void destroy() {
		LOG.debug("destroy");
	}

	public String getShibboletToken(HttpServletRequest httpRequest) {
		String marker = (String) httpRequest.getHeader(AUTH_ID_MARKER);
		if (marker == null)
			marker = (String) httpRequest.getHeader(AUTH_ID_MARKER2);
		if (marker == null && devmode) {
			return getShibbolethTokenDevMode(httpRequest);
		}

		return marker;
	}

	private String normalizeToken(String token) {
		return token;
	}

}
