/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RInterventoModuloDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7887983522443447345L;
	private Integer rIntModuloId;
	private Integer interventoId;
	private String statoModuloCod;
	private String moduloTipoId;
	private Integer moduloFileId;
}
