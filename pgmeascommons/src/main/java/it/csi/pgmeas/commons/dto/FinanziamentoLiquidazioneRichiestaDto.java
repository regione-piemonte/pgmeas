/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class FinanziamentoLiquidazioneRichiestaDto implements Serializable {
	private static final long serialVersionUID = -7771264480075990473L;

	private Integer liqRicId;
	private Integer liqRicNumero;
	private String liqRicProtocollo;
	private Timestamp liqRicProtocolloData;
	private BigDecimal liqRicImporto;
	private Integer finId;
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer enteId;
	private List<LiquidazioneDto> listaLiquidazione;
}
