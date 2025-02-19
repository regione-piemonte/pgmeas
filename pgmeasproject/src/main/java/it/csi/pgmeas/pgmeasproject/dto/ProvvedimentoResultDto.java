/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProvvedimentoResultDto implements Serializable {
	private static final long serialVersionUID = 4680789780832218450L;

	private Integer provId;
	private Integer provIdPadre;
	private String provTitolo;
	private Timestamp provData;
	private BigDecimal provImporto;
	private String provEnteProvenienza;
	private String provOggetto;
	private BigDecimal provNumero;
	private BigDecimal provNumero2;
	private String provNote;
	private Integer provLivId;
	private Integer finTipoDetId;
	private Integer provTipoId;
	private Integer provIdSostituito;
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer enteId;
}
