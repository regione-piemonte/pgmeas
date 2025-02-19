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
@Table(name="pgmeas_d_intervento_categoria")
public class InterventoCategoria  extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = 5193001282067252267L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "int_categoria_id")
	private Integer intCategoriaId;
	
	@Column(name = "int_categoria_cod")
	private String intCategoriaCod;
	
	@Column(name = "int_categoria_desc")
	private String intCategoriaDesc;
	
}
