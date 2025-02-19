/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class RicercaInterventiResultDto implements Serializable {

	private static final long serialVersionUID = 8639925314641644606L;

	private Integer intId = null;
	private Integer enteId = null;
	private Integer intAnno = null;
	private String intCup = null;
	private String intCod = null;
	private String intTitolo = null;
	private Integer intStatoId = null;
	private BigDecimal intImporto = null;
	private Long total = null;
}
