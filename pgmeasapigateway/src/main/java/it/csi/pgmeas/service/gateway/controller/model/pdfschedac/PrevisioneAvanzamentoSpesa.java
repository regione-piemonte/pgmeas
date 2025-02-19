/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.pdfschedac;

import java.util.List;
import java.util.Map;

public record PrevisioneAvanzamentoSpesa(List<String> anni, List<VociPrevisioneAvanzamentoSpesa> voci, //
		Map<String,String> totaleAnni, //
		String totale) {

}
