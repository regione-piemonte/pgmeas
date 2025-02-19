/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class InterventoTipoDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = 4156997675163726634L;
	
	private Integer intTipoId;
	private String intTipoCod;
	private String intTipoDesc;

}
