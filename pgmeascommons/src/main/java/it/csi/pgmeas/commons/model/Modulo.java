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
@Table(name="pgmeas_d_modulo")
@Data
public class Modulo extends DecodificaBaseEntity implements Serializable{

	private static final long serialVersionUID = 1103564911858255101L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "modulo_id")
	private Integer moduloId;
	
	@Column(name = "modulo_cod", nullable = false)
	private String moduloCod;
	
	@Column(name = "modulo_desc")
	private String moduloDesc;
	
	
}
