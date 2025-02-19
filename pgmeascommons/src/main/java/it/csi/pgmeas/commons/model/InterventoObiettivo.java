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

@Data
@Entity
@Table(name="pgmeas_d_intervento_obiettivo")
public class InterventoObiettivo extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -1146530426794858655L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "int_obiettivo_id")
	private Integer intObiettivoId;
	
	@Column(name = "int_obiettivo_cod")
	private String intObiettivoCod;
	
	@Column(name = "int_obiettivo_desc")
	private String intObiettivoDesc;
	
}
