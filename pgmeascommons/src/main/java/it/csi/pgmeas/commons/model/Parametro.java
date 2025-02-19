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
@Table(name="pgmeas_c_parametro")
public class Parametro extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -6670936011587388137L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parametro_id")
	private Integer parametroId;
	
	@Column(name = "parametro_cod")
	private String parametroCod;
	
	@Column(name = "parametro_desc")
	private String parametroDesc;
	
	@Column(name = "parametro_valore")
	private String parametroValore;
	
	@Column(name = "parametro_tipo_id")
	private Integer parametroTipoId;
	
	
}
