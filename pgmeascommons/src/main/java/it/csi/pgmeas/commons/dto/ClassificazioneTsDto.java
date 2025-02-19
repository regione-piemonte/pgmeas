/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClassificazioneTsDto extends CommonExtDto implements Serializable {
	
	private static final long serialVersionUID = -1051015070190994946L;
	
	private Integer classifTsId;
	private String classifTsCod;
	private String classifTsDesc;
	private Integer classifTsTipoId;

}
