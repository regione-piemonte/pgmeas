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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="pgmeas_r_intervento_finalita")
public class RInterventoFinalita extends DecodificaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = -1005365170524640031L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_int_finalita_id")
	private Integer rIntFinalitaId;
	
	@Column(name = "int_id")
	private Integer intId;
	
	@Column(name = "int_finalita_id")
	private Integer intFinalitaId;
	
	@Column(name = "ente_id")
	private Integer enteId;
	
	

}
