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
public class AllegatoDto implements Serializable {
	private static final long serialVersionUID = 1116370216957518014L;

	private Integer allegatoId;
	private String allegatoOggetto;
	private String allegatoProtocolloNumero;
	private Timestamp allegatoProtocolloData;
	private String fileNameUser;
	private String fileNameSystem;
	private String fileType;
	private String filePath;
	private Integer allegatoTipoId;
	private Integer intId;
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer enteId;
}
