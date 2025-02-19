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
@Table(name="pgmeas_d_quadrante")
public class Quadrante extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = 5289158487050672653L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quadrante_id")
	private Integer quadranteId;
	
	@Column(name = "quadrante_cod")
	private String quadranteCod;
	
	@Column(name = "quadrante_desc")
	private String quadranteDesc;
	
}
