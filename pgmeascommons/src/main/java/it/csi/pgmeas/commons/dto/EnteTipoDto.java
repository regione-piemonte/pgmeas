/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EnteTipoDto extends CommonExtDto implements Serializable{

	private static final long serialVersionUID = 8277076513906751517L;
	
	private Integer enteTipoId;
	private String enteTipoCod;
	private String enteTipoDesc;

}
