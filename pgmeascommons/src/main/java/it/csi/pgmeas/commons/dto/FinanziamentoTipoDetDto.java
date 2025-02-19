/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FinanziamentoTipoDetDto extends CommonExtDto implements Serializable {
	
	private static final long serialVersionUID = 4898193992498819027L;

	private Integer finTipoDetId;
	private String finTipoDetCod;
	private String finTipoDetDesc;
	private Integer finTipoId;
	private Integer finTipoDetPercentualeStato;
	private Integer finTipoDetPercentualeRegione;
	private Integer finTipoDetPercentualeAltro;

}
