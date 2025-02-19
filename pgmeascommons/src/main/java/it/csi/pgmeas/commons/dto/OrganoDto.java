/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrganoDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = -3525364083833158624L;
	
	private Integer organoId;
	private String organoCod;
	private String organoDesc;

}
