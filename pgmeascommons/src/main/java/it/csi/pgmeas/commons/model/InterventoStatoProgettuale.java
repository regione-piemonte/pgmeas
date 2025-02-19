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
@Table(name="pgmeas_d_intervento_stato_progettuale")
public class InterventoStatoProgettuale extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -6453105651736296630L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "int_stato_prog_id")
	private Integer intStatoProgId;
	
	@Column(name = "int_stato_prog_cod")
	private String intStatoProgCod;
	
	@Column(name = "int_stato_prog_desc")
	private String intStatoProgDesc;
	
	
}
