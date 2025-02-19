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
@Table(name="pgmeas_d_classif_elem")
public class ClassificazioneElemento extends DecodificaBaseEntity  implements Serializable {

	private static final long serialVersionUID = 3286354120282365158L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "classif_id")
	private Integer classifId;
	
	@Column(name = "classif_cod")
	private String classifCod;
	
	@Column(name = "classif_desc")
	private String classifDesc;
	
	@Column(name = "classif_desc_estesa")
	private String classifDescEstesa;
	
	@Column(name = "classif_simbolo")
	private String classifSimbolo;
	
	@Column(name = "classif_etichetta")
	private String classifEtichetta;
	
}
