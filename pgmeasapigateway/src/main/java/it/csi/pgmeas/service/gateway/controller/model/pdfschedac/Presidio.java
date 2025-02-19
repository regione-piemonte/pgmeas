/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.pdfschedac;

public record Presidio(//
		String codicePresidio, //
		String nomePresidio, //
		String dataAperturaCantierePrevista, //
		String dataAperturaCantiereEffettiva, //
		String dataCollaudoPrevista, //
		String dataCollaudoEffettiva //
) {

}
