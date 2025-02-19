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
@Table(name="pgmeas_d_intervento_finalita")
public class InterventoFinalita extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = 2860275246547447486L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "int_finalita_id")
	private Integer intFinalitaId;
	
	@Column(name = "int_finalita_cod")
	private String intfinalitaCod;
	
	@Column(name = "int_finalita_desc")
	private String intfinalitaDesc;
	
}
