/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.pdfschedac;

public record TotaliRichiestaFinanziamento(		String importoRichiesta, 
		String importoLiquidato, 
		String importoLiquidare, 
		String importoErogato,
		String importoIncassato) {

}
