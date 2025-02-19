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
@Table(name="pgmeas_r_intervento_stato")
@Data
public class RInterventoStato extends DecodificaBaseEntity implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_int_stato_id")
	private Integer rIntStatoId;
	
	@Column(name = "int_id")
	private Integer intId;
	
	@Column(name = "int_stato_id")
	private Integer intStatoId;
	
	@Column(name = "ente_id")
	private Integer enteId;
	
	@Column(name = "note")
	private String note;

}
