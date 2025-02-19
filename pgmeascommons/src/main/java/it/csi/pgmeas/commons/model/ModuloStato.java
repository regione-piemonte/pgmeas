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
@Table(name="pgmeas_d_modulo_stato")
@Data
public class ModuloStato extends DecodificaBaseEntity implements Serializable{

	private static final long serialVersionUID = 2633048150768776002L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "modulo_stato_id")
	private Integer moduloStatoId;
	
	@Column(name = "modulo_stato_cod", nullable = false)
	private String moduloStatoCod;
	
	@Column(name = "modulo_stato_desc")
	private String moduloStatoDesc;
	
}
