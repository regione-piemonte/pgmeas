/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.pdfschedac;

import java.util.Map;

public record VociPrevisioneAvanzamentoSpesa(String fonteFinanziamento, Map<String, String> esercizioFinanziario,
		 String totale) {
	
	public VociPrevisioneAvanzamentoSpesa withTotale(String totale) {
		return new VociPrevisioneAvanzamentoSpesa(this.fonteFinanziamento(), this.esercizioFinanziario(), totale);
	}

}
