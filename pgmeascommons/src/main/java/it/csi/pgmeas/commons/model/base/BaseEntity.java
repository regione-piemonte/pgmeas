/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model.base;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
@MappedSuperclass
@Data
public abstract class BaseEntity {
	@Column(name = "data_creazione")
	private Timestamp dataCreazione;
	
	@Column(name = "data_modifica")
	private Timestamp dataModifica;
	
	@Column(name = "data_cancellazione")
	private Timestamp dataCancellazione;
	
	@Column(name = "utente_creazione")
	private String utenteCreazione;
	
	@Column(name = "utente_modifica")
	private String utenteModifica;
	
	@Column(name = "utente_cancellazione")
	private String utenteCancellazione;
}
