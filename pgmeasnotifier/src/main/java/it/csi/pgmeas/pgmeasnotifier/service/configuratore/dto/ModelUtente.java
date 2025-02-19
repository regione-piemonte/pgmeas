/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service.configuratore.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelUtente implements Serializable{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 3152179118136325951L;
		private String nome;
	    private String cognome;
	    @JsonProperty("codice_fiscale")
	    private String codiceFiscale;
	    private List<Abilitazione> abilitazioni;
}
