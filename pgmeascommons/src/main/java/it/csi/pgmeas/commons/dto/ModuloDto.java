/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ModuloDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = -5295459432784389263L;
	
	private Integer moduloId;
	private String moduloCod;
	private String moduloDesc;

}
