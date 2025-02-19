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
@Table(name="pgmeas_d_classif_ts")
public class ClassificazioneTs extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -8682200975426043575L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "classif_ts_id")
	private Integer classifTsId;
	
	@Column(name = "classif_ts_cod")
	private String classifTsCod;
	
	@Column(name = "classif_ts_desc")
	private String classifTsDesc;
	
	@Column(name = "classif_ts_tipo_id")
	private Integer classifTsTipoId;
	
}
