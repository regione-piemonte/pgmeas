/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Collocazione {
	@JsonProperty("codice_collocazione")
	private String codiceCollocazione;
	@JsonProperty("descrizione_collocazione")
    private String descrizioneCollocazione;
	@JsonProperty("codice_azienda")
	private String codiceAzienda;
	@JsonProperty("descrizione_azienda")
    private String descrizioneAzienda;
}
