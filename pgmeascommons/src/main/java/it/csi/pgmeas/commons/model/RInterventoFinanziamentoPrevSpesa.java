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
@Table(name="pgmeas_r_intervento_finanziamento_prev_spesa")
@Data
public class RInterventoFinanziamentoPrevSpesa extends DecodificaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = -9136198133602214289L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "int_fin_prev_spesa_id")
	private Integer intFinPrevSpesaId;
	
	@Column(name = "int_id")
	private Integer intId;
	
	@Column(name = "fin_id")
	private Integer finId;
	
	@Column(name = "int_fin_prev_spesa_anno")
	private Integer intFinPrevSpesaAnno;
	
	@Column(name = "int_fin_prev_spesa_importo")
	private BigDecimal intFinPrevSpesaImporto;
	
	@Column(name = "ente_id")
	private Integer enteId;


}
