/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.pdfschedac;

import java.util.List;

public record DatiIntervento( //
		String codiceAzienda, //
		String nomeAzienda, //
		String interventoTitolo, //
		String rupNome, //
		String rupCognome, //
		String idInterventoProceduraRegionale, //
		String codiceNSIS, //
		String codiceCUP, //
		String provvedimentiAssegnazione, //
		String dataDecretoMinisteroSalute, //
		String dataAggiudicazione, //
		String importoComplessivo, //
		String statoLavori, //
		String codiceCIG, //
		List<Presidio> presidi //
) {

}
