/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProvvedimentoTipoDto extends CommonExtDto implements Serializable {
	
	private static final long serialVersionUID = 5631605021881003338L;
	
	private Integer provTipoId;
	private String provTipoCod;
	private String provTipoDesc;
	private Integer organoId;


}
