/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.model;

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
@Table(name="pgmeas_l_notifica")
public class LogNotifica implements Serializable {

	private static final long serialVersionUID = -2142386426821179366L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nlog_id")
	private Long nLogId;
	
	@Column(name = "notifica_id")
	private Long notificaId;
	
	@Column(name = "notifica_stato_id")
	private Integer notificaStatoId;
	//TODO capire
	@Column(name = "notifica_stato_id_old")
	private Integer notificaStatoIdOld;
	
	@Column(name = "notifica_retry_contatore")
	private Integer notificaRetryContatore;
	
	@Column(name = "nlog_data_operazione")
	private Timestamp nLogDataOperazione;
	
	@Column(name = "nlog_request")
	private String nLogRequest;
	
	@Column(name = "nlog_response")
	private String nLogResponse;
	
	@Column(name = "nlog_esito")
	private String nLogEsito;
	
	@Column(name = "nlog_errore")
	private String nLogErrore;
	
	@Column(name = "nlog_data_inizio")
	private Timestamp nlogDataInizio;
	
	@Column(name = "nlog_data_fine")
	private Timestamp nlogDataFine;
	
	@Column(name = "ente_id")
	private Integer enteId;
	
}
