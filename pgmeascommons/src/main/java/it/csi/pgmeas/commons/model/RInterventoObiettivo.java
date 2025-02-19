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
@Table(name="pgmeas_r_intervento_obiettivo")
@Data
public class RInterventoObiettivo extends DecodificaBaseEntity implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_int_obiettivo_id")
	private Integer rIntObiettivoId;
	
	@Column(name = "int_id")
	private Integer intId;
	
	@Column(name = "int_obiettivo_id")
	private Integer intObiettivoId;
	
	@Column(name = "ente_id")
	private Integer enteId;


}
