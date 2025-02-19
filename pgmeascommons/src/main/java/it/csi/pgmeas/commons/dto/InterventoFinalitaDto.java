/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class InterventoFinalitaDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = -1069898037114002354L;
	
	private Integer intFinalitaId;
	private String intfinalitaCod;
	private String intfinalitaDesc;	

}
