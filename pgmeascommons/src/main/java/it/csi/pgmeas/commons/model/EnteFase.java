/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Fase;
import it.csi.pgmeas.commons.model.base.DecodificaBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "pgmeas_r_ente_fase")
public class EnteFase extends DecodificaBaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2617634798773375997L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_ente_fase_id", nullable = false)
	private Integer rEnteFaseId;

	@Column(name = "fase_id", nullable = false)
	private Integer faseId;

	@Column(name = "fase_inizio", nullable = false)
	private LocalDateTime faseInizio;

	@Column(name = "fase_fine", nullable = false)
	private LocalDateTime faseFine;

	@Column(name = "fase_proroga_inizio")
	private LocalDateTime faseProrogaInizio;

	@Column(name = "fase_proroga_fine")
	private LocalDateTime faseProrogaFine;


	@Column(name = "ente_id", nullable = false)
	private Integer enteId;

	// Relationship with PgmeasDEnte (foreign key)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ente_id", insertable = false, updatable = false)
	private Ente ente;

	// Relationship with PgmeasDFase (foreign key)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fase_id", insertable = false, updatable = false)
	private Fase fase;

}