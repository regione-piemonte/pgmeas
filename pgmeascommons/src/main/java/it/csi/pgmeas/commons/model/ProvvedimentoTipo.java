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
@Table(name="pgmeas_d_provvedimento_tipo")
public class ProvvedimentoTipo extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -2423517481726764600L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prov_tipo_id")
	private Integer provTipoId;
	
	@Column(name = "prov_tipo_cod")
	private String provTipoCod;
	
	@Column(name = "prov_tipo_desc")
	private String provTipoDesc;
	
	@Column(name = "organo_id")
	private Integer organoId;
	
	
}
