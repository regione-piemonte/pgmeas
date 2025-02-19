/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.csi.pgmeas.commons.model.base.DecodificaBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="pgmeas_t_intstr_dic_appaltabilita_tree")
@Data
public class IntStrDicAppaltabilitaTree extends DecodificaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 728587039360339116L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "intstr_da_tree_id")
	private Integer intstrDaTreeId;
	
	@Column(name = "intstr_da_ts_id")
	private Integer intstrDaTsId;
	
	@Column(name = "classif_tree_id")
	private Integer classifTreeId;
	
	@Column(name = "intstr_da_tree_selezionata")
	private Boolean intstrDaTreeSelezionata;
	
	@Column(name = "intstr_da_tree_validazione_data")
	private Timestamp intstrDaTreeValidazioneData;
	
	@Column(name = "int_tipo_det_id")
	private Integer intTipoDetId;
	
	@Column(name = "ente_id")
	private Integer enteId;
	
	@Column(name = "atto_numero")
	private String attoNumero;
}
