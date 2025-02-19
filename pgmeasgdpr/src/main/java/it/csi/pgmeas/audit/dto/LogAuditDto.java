/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.audit.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class LogAuditDto implements Serializable{
	
	private Integer auditId;	
	private Timestamp dataOra;		
	private String idApp;		
	private String ipAddress;		
	private String utente;		
	private String operazione;		
	private String oggOper;	
	private String keyOper;		
	private String uuid;		
	private String requestPayload;	
	private String responsePayload;	
	private Integer esitoChiamata;

}
