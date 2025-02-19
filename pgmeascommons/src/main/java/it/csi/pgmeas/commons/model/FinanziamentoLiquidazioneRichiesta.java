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
@Table(name="pgmeas_t_finanziamento_liquidazione_richiesta")
@Data
public class FinanziamentoLiquidazioneRichiesta extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 8300899151259338307L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "liq_ric_id")
	private Integer liqRicId;
	
	@Column(name = "liq_ric_numero")
	private Integer liqRicNumero;
	
	@Column(name = "liq_ric_protocollo")
	private String liqRicProtocollo;
	
	@Column(name = "liq_ric_portocollo_data")
	private Timestamp liqRicProtocolloData;
	
	@Column(name = "liq_ric_importo")
	private BigDecimal liqRicImporto;
	
	@Column(name = "fin_id")
	private Integer finId;
	
	
	@Column(name = "ente_id")
	private Integer enteId;


}
