/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PianoTriennaleDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = -6508242835165470487L;
	
	private Integer pianoId;
	private String pianoCod;
	private String pianoDesc;
	private Integer annoDa;
	private Integer annoA;
	
}
