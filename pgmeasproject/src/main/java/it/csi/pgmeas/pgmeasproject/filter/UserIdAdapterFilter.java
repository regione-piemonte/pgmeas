/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.csi.pgmeas.commons.util.APISecurityFilterUtils;
import it.csi.pgmeas.commons.util.HeadersUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
@Order(1)
public class UserIdAdapterFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(UserIdAdapterFilter.class);

	@Value("${IrideIdAdapterFilter.devmode:false}")
	private boolean devmode;

	@PostConstruct
	public void init() {
		LOG.info(" **** DEV-MODE {} ", devmode);
		HeadersUtils.setDevmode(devmode);
		APISecurityFilterUtils.setDevmode(devmode);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn)
			throws IOException, ServletException {
		APISecurityFilterUtils.doFilter(req, resp, fchn);
	}

	@Override
	public void destroy() {
		LOG.debug("destroy");
	}

}
