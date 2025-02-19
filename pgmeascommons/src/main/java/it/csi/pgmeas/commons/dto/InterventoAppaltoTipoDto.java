/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class InterventoAppaltoTipoDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = 8320239996439699905L;
	
	private Integer intAppaltoTipoId;
	private String intAppaltoTipoCod;
	private String intAppaltoTipoDesc;

}
