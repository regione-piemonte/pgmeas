/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class InterventoObiettivoDto extends CommonExtDto implements Serializable {
	
	private static final long serialVersionUID = 6216337866461136948L;
	
	private Integer intObiettivoId;
	private String intObiettivoCod;
	private String intObiettivoDesc;

}
