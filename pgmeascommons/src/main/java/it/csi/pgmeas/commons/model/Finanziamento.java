/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

import it.csi.pgmeas.commons.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="pgmeas_t_finanziamento")
@Data
public class Finanziamento extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7528907266003086377L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fin_id")
	private Integer finId;
	
	@Column(name = "fin_cod")
	private String finCod;
	
	@Column(name = "fin_importo")
	private BigDecimal finImporto;
	
	@Column(name = "fin_note")
	private String finNote;
	
	@Column(name = "fin_tipo_det_id")
	private Integer finTipoDetId;
	
	@Column(name = "fin_principale")
	private Boolean finPrincipale;
	
	@Column(name = "prov_id")
	private Integer provId;
	
	@Column(name = "int_id")
	private Integer intId;
	
	@Column(name = "ente_id")
	private Integer enteId;


}
