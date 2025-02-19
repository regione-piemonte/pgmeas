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
public class FinanziamentoResultDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2083843968493470906L;
	
	
	private Integer finId;	
	private String finCod;
	private BigDecimal finImporto;
	private String finNote;
	private Integer finTipoDetId;	
	private Boolean finPrincipale;	
	private Integer provId;
	private Integer intId;	
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;	
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer enteId;

}
