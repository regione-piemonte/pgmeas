/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class InterventoContrattoTipoDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = 269359669529614166L;
	
	private Integer intContrattoTipoId;
	private String intContrattoTipoCod;
	private String intContrattoTipoDesc;

}
