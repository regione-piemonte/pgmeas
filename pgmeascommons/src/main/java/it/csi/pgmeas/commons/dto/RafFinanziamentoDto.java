/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RafFinanziamentoDto {
	private BigDecimal finImporto;
	private String finNote;
	private Integer finTipoDetId;
	private String provTitolo;
}
