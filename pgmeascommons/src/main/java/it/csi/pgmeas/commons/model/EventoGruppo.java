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
@Table(name="pgmeas_d_evento_gruppo")
public class EventoGruppo extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -3127782369498806881L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evento_gruppo_id")
	private Integer eventoGruppoId;
	
	@Column(name = "evento_gruppo_cod")
	private String eventoGruppoCod;
	
	@Column(name = "evento_gruppo_desc")
	private String eventoGruppoDesc;
}
