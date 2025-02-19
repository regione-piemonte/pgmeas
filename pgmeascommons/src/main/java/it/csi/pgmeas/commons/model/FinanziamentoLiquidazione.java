/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import it.csi.pgmeas.commons.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="pgmeas_t_finanziamento_liquidazione")
@Data
public class FinanziamentoLiquidazione extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 5550557535281706906L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "liq_id")
	private Integer liqId;
	
	@Column(name = "liq_numero")
	private Integer liqNumero;
	
	@Column(name = "liq_data_da")
	private Timestamp liqDataDa;
	
	@Column(name = "liq_data_a")
	private Timestamp liqDataA;
	
	@Column(name = "liq_importo")
	private BigDecimal liqImporto;
	
	@Column(name = "fin_id")
	private Integer finId;
	
	@Column(name = "ente_id")
	private Integer enteId;


}
