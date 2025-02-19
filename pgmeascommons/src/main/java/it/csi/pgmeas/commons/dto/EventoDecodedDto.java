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
public class EventoDecodedDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer eventoId;
	private Integer enteId;
	private Timestamp eventoData;
	private Integer eventoTipoId;
	private String eventoTipoCod;
	private Integer eventoTabellaId;
	private Long notificaId;
}
