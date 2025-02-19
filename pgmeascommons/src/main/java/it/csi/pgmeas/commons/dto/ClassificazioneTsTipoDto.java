/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClassificazioneTsTipoDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = -276155325412525593L;
	
	private Integer classifTsTipoId;
	private String classifTsTipoCod;
	private String classifTsTipoDesc;

}
