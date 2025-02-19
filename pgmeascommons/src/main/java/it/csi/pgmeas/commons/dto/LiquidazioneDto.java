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
public class LiquidazioneDto implements Serializable {
	private static final long serialVersionUID = -8631151769187089483L;

	private Integer rLiqRicId;
	private Integer liqRicId;
	private Integer liqId;
	private Timestamp validitaInizio;
	private Timestamp validitaFine;
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer enteId;
	private BigDecimal liqImportoErogato;
	private BigDecimal liqImportoIncassato;
	private BigDecimal liqImportoTotaleSpesoAzienda;
}
