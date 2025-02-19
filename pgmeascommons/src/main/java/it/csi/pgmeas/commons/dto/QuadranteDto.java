/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class QuadranteDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = 5555632037030820492L;
	
	private Integer quadranteId;
	private String quadranteCod;
	private String quadranteDesc;

}
