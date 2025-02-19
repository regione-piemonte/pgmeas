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

@Entity
@Table(name="pgmeas_t_intstr_quadroecon_ts")
@Data
public class IntStrQuadroEconTs extends DecodificaBaseEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "intstr_qe_ts_id")
	private Integer intstrQeTsId;
	
	@Column(name = "intstr_id")
	private Integer intstrId;
	
	@Column(name = "classif_ts_id")
	private Integer classifTsId;
	
	@Column(name = "ente_id")
	private Integer enteId;


}
