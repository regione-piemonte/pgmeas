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
@Table(name="pgmeas_t_intstr_quadroecon_tree")
@Data
public class IntStrQuadroEconTree extends DecodificaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = -3709407093984188018L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "intstr_qe_tree_id")
	private Integer intstrQeTreeId;
	
	@Column(name = "intstr_qe_ts_id")
	private Integer intstrQeTsId;
	
	@Column(name = "classif_tree_id")
	private Integer classifTreeId;
	
	@Column(name = "intstr_qe_tree_visibile")
	private Boolean intstrQeTreeVisibile;
	
	@Column(name = "intstr_qe_tree_importo")
	private BigDecimal intstrQeTreeImporto;
	
	@Column(name = "ente_id")
	private Integer enteId;
	
	
	
}
