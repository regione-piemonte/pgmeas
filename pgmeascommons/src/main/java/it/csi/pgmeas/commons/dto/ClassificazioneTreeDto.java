/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClassificazioneTreeDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = 714278663088437073L;
	
	private Integer classifTreeId;
	private Integer classifTsId;
	private Integer classifId;
	private Integer classifIdPadre;
	private Integer classifTreeLivello;
	private Integer classifTreeOrdine;

}
