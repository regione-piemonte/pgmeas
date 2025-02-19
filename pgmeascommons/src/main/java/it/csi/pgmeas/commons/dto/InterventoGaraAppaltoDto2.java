/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class InterventoGaraAppaltoDto2 implements Serializable {
	private static final long serialVersionUID = -1608555397430360728L;

	private Integer garaAppaltoId;
	private String garaAppaltoCigCod;
	private Timestamp garaAppaltoData;
	private Integer intstrId;
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer enteId;
}
