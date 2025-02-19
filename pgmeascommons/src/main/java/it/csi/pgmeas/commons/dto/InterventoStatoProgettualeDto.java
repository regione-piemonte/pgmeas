/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class InterventoStatoProgettualeDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = -2780505168111552426L;
	
	private Integer intStatoProgId;
	private String intStatoProgCod;
	private String intStatoProgDesc;

}
