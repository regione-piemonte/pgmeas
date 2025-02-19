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
@Table(name="pgmeas_r_intervento_tipo")
@Data
public class RInterventoTipo extends DecodificaBaseEntity implements Serializable {
	
	private static final long serialVersionUID = -4055455504000522519L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_int_tipo_id")
	private Integer rIntTipoId;
	
	@Column(name = "int_id")
	private Integer intId;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "int_tipo_id")
	private Integer intTipoId;
	
	@Column(name = "int_tipo_det_id")
	private Integer intTipoDetId;
	
	@Column(name = "ente_id")
	private Integer enteId;


}
