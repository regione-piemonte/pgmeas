/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FaseDto extends CommonExtDto implements Serializable{

	private static final long serialVersionUID = -1956136935086819823L;
	
	private Integer faseId;
	private String faseCod;
	private String faseDesc;
}
