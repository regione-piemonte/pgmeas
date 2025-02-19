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
@Table(name="pgmeas_d_piano_triennale")
public class PianoTriennale extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = 1539754337094681875L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "piano_id")
	private Integer pianoId;
	
	@Column(name = "piano_cod")
	private String pianoCod;
	
	@Column(name = "piano_desc")
	private String pianoDesc;
	
	@Column(name = "anno_da")
	private Integer annoDa;
	
	@Column(name = "anno_a")
	private Integer annoA;
	
}
