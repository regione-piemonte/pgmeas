/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;

import it.csi.pgmeas.commons.model.base.DecodificaBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="pgmeas_r_intervento_modulo")
@Data
public class RInterventoModulo extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = 9099911578318375014L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_int_modulo_id")
	private Integer rIntModuloId;
	
	@Column(name = "int_id", nullable = false)
	private Integer intId;
	
	@Column(name = "modulo_id", nullable = false)
	private Integer moduloId;
	
	@Column(name = "modulo_stato_id", nullable = false)
	private Integer moduloStatoId;
	
	@Column(name = "file_id")
	private Integer fileId;
	
	@Column(name = "ente_id")
	private Integer enteId;

	@Column(name ="utente_nomecognome")
	private String utenteNomeCognome;
	
	@Column(name ="note")
	private String note;
}
