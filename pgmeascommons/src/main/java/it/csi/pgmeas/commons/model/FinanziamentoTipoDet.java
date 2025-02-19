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
@Table(name="pgmeas_d_finanziamento_tipo_det")
public class FinanziamentoTipoDet extends DecodificaBaseEntity implements Serializable {
	
	private static final long serialVersionUID = -9209079147614443516L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fin_tipo_det_id")
	private Integer finTipoDetId;
	
	@Column(name = "fin_tipo_det_cod")
	private String finTipoDetCod;
	
	@Column(name = "fin_tipo_det_desc")
	private String finTipoDetDesc;
	
	@Column(name = "fin_tipo_id")
	private Integer finTipoId;
	
	//TODO delete
	@Column(name = "fin_tipo_det_percentuale_stato")
	private Integer finTipoDetPercentualeStato; 

	@Column(name = "fin_tipo_det_percentuale_regione")
	private Integer finTipoDetPercentualeRegione;
	
	@Column(name = "fin_tipo_det_percentuale_altro")
	private Integer finTipoDetPercentualeAltro;
	
}
