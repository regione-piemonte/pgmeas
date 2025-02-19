/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.csi.pgmeas.commons.model.base.DecodificaBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="pgmeas_t_allegato")
@Data
public class Allegato extends DecodificaBaseEntity implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5987905525033424617L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "allegato_id")
	private Integer allegatoId;
	
	@Column(name = "allegato_oggetto")
	private String allegatoOggetto;
	
	@Column(name = "allegato_protocollo_numero")
	private String allegatoProtocolloNumero;
	
	@Column(name = "allegato_protocollo_data")
	private Timestamp allegatoProtocolloData;
	
	@Column(name = "file_name_user")
	private String fileNameUser;
	
	@Column(name = "file_name_system")
	private String fileNameSystem;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "allegato_tipo_id")
	private Integer allegatoTipoId;
	
	@Column(name = "int_id")
	private Integer intId;
	
//	@Column(name = "data_creazione", nullable=false)
//	private Timestamp dataCreazione;
//	
//	@Column(name = "data_modifica", nullable=false)
//	private Timestamp dataModifica;
//	
//	@Column(name = "data_cancellazione")
//	private Timestamp dataCancellazione;
//	
//	@Column(name = "utente_creazione", nullable=false)
//	private String utenteCreazione;
//	
//	@Column(name = "utente_modifica", nullable=false)
//	private String utenteModifica;
//	
//	@Column(name = "utente_cancellazione")
//	private String utenteCancellazione;
	
	@Column(name = "ente_id")
	private Integer enteId;
	
//	@Column(name = "validita_inizio", nullable=false)
//	private Timestamp validitaInizio;
//	
//	@Column(name = "validita_fine")
//	private Timestamp validitaFine;
	
	@Column(name = "intstr_id")
	private Integer intstrId;
}
