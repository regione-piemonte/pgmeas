/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.csi.pgmeas.commons.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pgmeas_t_notifica")
public class Notifica extends BaseEntity implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notifica_id")
	private Long notificaId;

	@Column(name = "notifica_data_inizio")
	private Timestamp notificaDataInizio;

	@Column(name = "notifica_data_fine")
	private Timestamp notificaDataFine;

	@Column(name = "notifica_mex_breve")
	private String notificaMexBreve;

	@Column(name = "notifica_mex_esteso")
	private String notificaMexEsteso;

	@Column(name = "notifica_email_oggetto")
	private String notificaEmailOggetto;

	@Column(name = "notifica_email_corpo")
	private String notificaEmailCorpo;

	@Column(name = "notifica_destinatatio_cf")
	private String notificaDestinatatioCf;

	@Column(name = "notifica_destinatatio_applicazione")
	private String notificaDestinatatioApplicazione;

	@Column(name = "notifica_destinatatio_ruolo")
	private String notificaDestinatatioRuolo;

	@Column(name = "notifica_retry_contatore")
	private Integer notificaRetryContatore;
	
	@Column(name = "notifica_endpoint")
	private String notificaEndpoint;

	@Column(name = "notifica_stato_id")
	private Integer notificaStatoId;

	@Column(name = "ente_id")
	private Integer enteId;
}
