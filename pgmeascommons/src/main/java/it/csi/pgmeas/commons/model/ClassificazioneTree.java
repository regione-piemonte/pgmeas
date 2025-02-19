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
@Table(name="pgmeas_d_classif_tree")
public class ClassificazioneTree extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = 7464367713229888382L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "classif_tree_id")
	private Integer classifTreeId;
	
	@Column(name = "classif_ts_id")
	private Integer classifTsId;
	
	@Column(name = "classif_id")
	private Integer classifId;
	
	@Column(name = "classif_id_padre")
	private Integer classifIdPadre;
	
	@Column(name = "classif_tree_livello")
	private Integer classifTreeLivello;
	
	@Column(name = "classif_tree_ordine")
	private Integer classifTreeOrdine;
	
	@Column(name = "classif_tree_importo_decimali")
	private Integer classifTreeImportoDecimali;
	
	@Column(name = "classif_tree_con_importo")
	private Boolean classifTreeConImporto;
	
	@Column(name = "classif_tree_editabile")
	private Boolean classifTreeEditabile;
	
	@Column(name = "classif_tree_formula")
	private String classifTreeFormula;
}
