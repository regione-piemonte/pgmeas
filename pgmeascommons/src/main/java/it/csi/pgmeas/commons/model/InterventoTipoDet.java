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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="pgmeas_d_intervento_tipo_det")
public class InterventoTipoDet extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -4960766312661394140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "int_tipo_det_id")
	private Integer intTipoDetId;
	
	@Column(name = "int_tipo_det_cod")
	private String intTipoDetCod;
	
	@Column(name = "int_tipo_det_desc")
	private String intTipoDetDesc;
	
	@Column(name = "int_tipo_id", insertable=false, updatable=false)
	private Integer intTipoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "int_tipo_id", referencedColumnName = "int_tipo_id", nullable = false)
    private InterventoTipo interventoTipo;
	
}
