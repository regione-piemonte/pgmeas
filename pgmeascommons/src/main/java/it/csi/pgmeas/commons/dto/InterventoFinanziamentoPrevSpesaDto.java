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
public class InterventoFinanziamentoPrevSpesaDto implements Serializable {
	private static final long serialVersionUID = -2787169755233665396L;

	private Integer intFinPrevSpesaId;
	private Integer intId;
	private Integer finId;
	private Integer intFinPrevSpesaAnno;
	private BigDecimal intFinPrevSpesaImporto;
	private Timestamp validitaInizio;
	private Timestamp validitaFine;
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer enteId;
}
