/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProvvedimentoLivelloDto extends CommonExtDto implements Serializable {
	
	private static final long serialVersionUID = 6072399130930815295L;
	
	private Integer provLivId;
	private String provLivCod;
	private String provLivDesc;

}
