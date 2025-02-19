/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClassificazioneTreeByClassTsTipoDto implements Serializable {

	private static final long serialVersionUID = 714278663088437073L;
	
	private Integer classifTreeId;
	private Integer classifTsId;
	private String classifElemCod;
	private Integer livello;
	private String descrizione;
    private Boolean selezionabile;
	private Integer classifTreeImportoDecimali;
	private Boolean classifTreeConImporto;
	private Boolean classifTreeEditabile;
	private String classifSimbolo;
	private String classifEtichetta;
}
