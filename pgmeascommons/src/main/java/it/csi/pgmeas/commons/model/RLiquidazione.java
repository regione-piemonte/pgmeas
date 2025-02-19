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
@Table(name="pgmeas_r_liquidazione")
@Data
public class RLiquidazione extends DecodificaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 5888425488830422940L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_liq_ric_id")
	private Integer rLiqRicId;
	
	@Column(name = "liq_ric_id")
	private Integer liqRicId;
	
	@Column(name = "liq_id")
	private Integer liqId;
	
	@Column(name = "ente_id")
	private Integer enteId;
	
	@Column(name = "liq_importo_erogato")
	private BigDecimal liqImportoErogato;
	
	@Column(name = "liq_importo_incassato")
	private BigDecimal liqImportoIncassato;
	
	@Column(name = "liq_importo_totale_speso_azienda")
	private BigDecimal liqImportoTotaleSpesoAzienda;

}
