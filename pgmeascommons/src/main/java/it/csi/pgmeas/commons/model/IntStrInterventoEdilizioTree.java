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
@Table(name="pgmeas_t_intstr_interventoedilizio_tree")
@Data
public class IntStrInterventoEdilizioTree extends DecodificaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 7942275170302619416L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "intstr_ie_tree_id")
	private Integer intstrIeTreeId;
	
	@Column(name = "intstr_ie_ts_id")
	private Integer intstrIeTsId;
	
	@Column(name = "classif_tree_id")
	private Integer classifTreeId;
	
	@Column(name = "intstr_ie_tree_visibile")
	private Boolean intstrIeTreeVisibile;
	
	@Column(name = "intstr_ie_tree_importo")
	private BigDecimal intstrIeTreeImporto;
	
	@Column(name = "ente_id")
	private Integer enteId;
	

}
