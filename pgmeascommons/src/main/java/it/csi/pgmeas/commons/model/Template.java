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
@Table(name = "pgmeas_d_template")
public class Template extends DecodificaBaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7342241010416104868L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "template_id")
	private Integer templateId;

	@Column(name = "template_cod")
	private String templateCod;

	@Column(name = "template_desc")
	private String templateDesc;

	@Column(name = "template_oggetto")
	private String templateOggetto;
	
	@Column(name = "template_corpo")
	private String templateCorpo;
	
	@Column(name = "template_tipo_id")
	private Integer templateTipoId;
	
	@Column(name = "evento_tipo_id")
	private Integer eventoTipoId;
}
