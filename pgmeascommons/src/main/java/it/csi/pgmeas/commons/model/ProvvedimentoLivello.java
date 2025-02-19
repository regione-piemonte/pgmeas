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
@Table(name="pgmeas_d_provvedimento_livello")
public class ProvvedimentoLivello extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -6449862082256625219L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prov_liv_id")
	private Integer provLivId;
	
	@Column(name = "prov_liv_cod")
	private String provLivCod;
	
	@Column(name = "prov_liv_desc")
	private String provLivDesc;
	
	
}
