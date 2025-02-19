/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.audit.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="csi_log_audit")
public class LogAudit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7752694342398770812L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "audit_id")
	private Integer auditId;
	
	@Column(name = "data_ora")
	private Timestamp dataOra;
	
	@Column(name = "id_app")
	private String idApp;
	
	@Column(name = "ip_address")
	private String ipAddress;
	
	@Column(name = "utente")
	private String utente;
	
	@Column(name = "operazione")
	private String operazione;
	
	@Column(name = "ogg_oper")
	private String oggOper;
	
	@Column(name = "key_oper")
	private String keyOper;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "request_payload")
	private String requestPayload;
	
	@Column(name = "response_payload")
	private String responsePayload;
	
	@Column(name = "esito_chiamata")
	private Integer esitoChiamata;
}
