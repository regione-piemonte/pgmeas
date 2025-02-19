/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class InterventoCategoriaDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = -1434991655124889739L;
	
	private Integer intCategoriaId;
	private String intCategoriaCod;
	private String intCategoriaDesc;
	
	
}
