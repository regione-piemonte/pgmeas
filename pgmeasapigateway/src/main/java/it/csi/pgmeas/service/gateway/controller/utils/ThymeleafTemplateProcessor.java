/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.utils;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

//https://github.com/tuhrig/Flying_Saucer_PDF_Generation/tree/master

public class ThymeleafTemplateProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(ThymeleafTemplateProcessor.class);

	private static final String UTF_8 = "UTF-8";
	private static final String IDENTITY_H = "Identity-H";
	private static final boolean EMBEDDED = true;

	private TemplateEngine templateEngine;
	private ITextRenderer renderer;
	private OutputStream outputStream;

	private String templateName;

	private Context context = new Context();

	public ThymeleafTemplateProcessor() {
		super();
		init();

	}

	public ThymeleafTemplateProcessor(OutputStream outputStream) {
		super();
		init();
		this.outputStream = outputStream;

	}

	public ThymeleafTemplateProcessor(String templateName) {
		super();
		init();
		this.templateName = templateName;
	}

	private void init() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);

		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");

		templateResolver.setCharacterEncoding(UTF_8);

		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		// render
		renderer = new ITextRenderer();
		try {
			renderer.getFontResolver().addFont("Code39.ttf", IDENTITY_H, EMBEDDED);
			renderer.getFontResolver().addFont("OpenSans.ttf", IDENTITY_H, EMBEDDED);
			renderer.getFontResolver().addFont("OpenSansRegular.ttf", IDENTITY_H, EMBEDDED);

		} catch (DocumentException | IOException e) {
			LOG.warn("[error]", e);
		}

		renderer.getSharedContext().setUserAgentCallback(new CustomOpenPdfUserAgent(renderer.getOutputDevice()));

	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public void setVariable(String key, Object value) {
		context.setVariable(key, value);
	}

	public void generatePdf() throws DocumentException {

		if (outputStream != null) {
			String html = templateEngine.process(templateName, context);

			LOG.debug("[ThymeleafTemplateProcessor::generatePdf] html :: {}", html);

			renderer.setDocumentFromString(html);
			renderer.layout();
			renderer.createPDF(outputStream);
		}
	}

}
