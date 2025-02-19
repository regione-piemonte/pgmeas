/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClassificazioneElementoDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = -7891461684323951016L;
	
	private Integer classifId;
	private String classifCod;
	private String classifDesc;
	private String classifDescEstesa;
	private String classifSimbolo;
	private String classifEtichetta;
}
