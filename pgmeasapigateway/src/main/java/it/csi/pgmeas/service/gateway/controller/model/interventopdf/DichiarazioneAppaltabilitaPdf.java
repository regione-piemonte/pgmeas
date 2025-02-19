/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.interventopdf;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DichiarazioneAppaltabilitaPdf {
	private Integer id; // corrisponde alla chiave della mappa
	private String descrizione;
	private Boolean selezionato;
	private Date validazioneData;
	private Boolean editabile; // booleano che indica se è modificabile
	private Boolean conImporto; //booleano che indica se l'elemento ha un importo associato
	private Integer livello; // livello intero
}
