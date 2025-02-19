/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.pdfschedac;

import java.math.BigDecimal;

public record VociPianoFinanziario(Boolean finPrincipale, //
		String fonteFinanziamento, //
		String estremiAttoAssegnazione, //
		String importoFinanziamento,//
		BigDecimal origImportoFinanziamento //
) {

}
