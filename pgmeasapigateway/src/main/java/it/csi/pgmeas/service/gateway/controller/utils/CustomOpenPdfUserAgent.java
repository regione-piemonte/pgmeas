/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;

public class CustomOpenPdfUserAgent extends ITextUserAgent {

	public CustomOpenPdfUserAgent(ITextOutputDevice outputDevice) {
		super(outputDevice, 300);
	}

	String getResource(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
	}

	@Override
	protected InputStream resolveAndOpenStream(String uri) {
		// LOG.info("xxxx " + uri.substring(11));
//		InputStream isd = getClass().getClassLoader().getResourceAsStream(uri.substring(11));
//		LOG.info("xxxx " + getResource(isd));
//		
		InputStream is = getClass().getClassLoader().getResourceAsStream(uri.substring(11));
		return is;
	}
}