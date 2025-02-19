/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
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
@Table(name="pgmeas_t_intervento_gara_appalto")
@Data
public class InterventoGaraAppalto extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4300782231262071801L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gara_appalto_id")
	private Integer garaAppaltoId;
	
	@Column(name = "gara_appalto_cig_cod")
	private String garaAppaltoCigCod;
	
	@Column(name = "gara_appalto_data")
	private Timestamp garaAppaltoData;
	
	@Column(name = "intstr_id")
	private Integer intstrId;
	
	@Column(name = "ente_id")
	private Integer enteId;
	
	

}
