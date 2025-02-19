/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class FinanziamentoLiquidazioneDto implements Serializable {
	private static final long serialVersionUID = 7640135179937099065L;

	private Integer liqId;
	private Integer liqNumero;
	private Timestamp liqDataDa;
	private Timestamp liqDataA;
	private BigDecimal liqImporto;
	private Integer finId;
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer enteId;
}
