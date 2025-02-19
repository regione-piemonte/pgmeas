/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FinanziamentoTipoDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = 4168495519598529472L;
	
	private Integer finTipoId;
	private String finTipoCod;
	private String finTipoDesc;

}
