/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

import it.csi.pgmeas.commons.model.base.DecodificaBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="pgmeas_t_intervento_previsione_spesa")
@Data
public class InterventoPrevisioneSpesa extends DecodificaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1940618726978921341L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "int_prev_spesa_id")
	private Integer intPrevSpesaId;
	
	@Column(name = "int_prev_spesa_anno")
	private Integer intPrevSpesaAnno;
	
	@Column(name = "int_prev_spesa_importo")
	private BigDecimal intPrevSpesaImporto;
	
	@Column(name = "int_id")
	private Integer intId;
	
	@Column(name = "piano_id")
	private Integer pianoId;
	
	@Column(name = "ente_id")
	private Integer enteId;


}
