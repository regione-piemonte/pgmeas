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
@Table(name="pgmeas_c_parametro_tipo")
public class ParametroTipo extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -6244866305446857001L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parametro_tipo_id")
	private Integer parametroTipoId;
	
	@Column(name = "parametro_tipo_cod")
	private String parametroTipoCod;
	
	@Column(name = "parametro_tipo_desc")
	private String parametroTipoDesc;
	
	
}
