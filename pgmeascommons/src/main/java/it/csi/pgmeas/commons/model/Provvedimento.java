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
@Table(name="pgmeas_t_provvedimento")
@Data
public class Provvedimento extends BaseEntity implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prov_id")
	private Integer provId;
	
	@Column(name = "prov_id_padre")
	private Integer provIdPadre;
	
	@Column(name = "prov_titolo")
	private String provTitolo;
	
	@Column(name = "prov_data")
	private Timestamp provData;
	
	@Column(name = "prov_importo")
	private BigDecimal provImporto;
	
	@Column(name = "prov_ente_provenienza")
	private String provEnteProvenienza;
	
	@Column(name = "prov_oggetto")
	private String provOggetto;
	
	@Column(name = "prov_numero")
	private BigDecimal provNumero;
	
	@Column(name = "prov_numero2")
	private BigDecimal provNumero2;
	
	@Column(name = "prov_note", columnDefinition="bpchar")
	private String provNote;
	
	@Column(name = "prov_liv_id")
	private Integer provLivId;
	
	@Column(name = "fin_tipo_det_id")
	private Integer finTipoDetId;
	
	@Column(name = "prov_tipo_id")
	private Integer provTipoId;
	
	@Column(name = "prov_id_sostituito")
	private Integer provIdSostituito;
	
	@Column(name = "ente_id")
	private Integer enteId;


}
