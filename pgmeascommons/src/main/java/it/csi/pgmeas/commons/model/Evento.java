/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.sql.Timestamp;

import it.csi.pgmeas.commons.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="pgmeas_t_evento")
public class Evento extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5782492679400270161L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evento_id")
	private Integer eventoId;
	
	@Column(name = "ente_id")
	private Integer enteId;
	
	@Column(name = "evento_data")
	private Timestamp eventoData;
	
	@Column(name = "evento_tipo_id")
	private Integer eventoTipoId;
	
	@Column(name = "eventoTabellaId")
	private Integer eventoTabellaId;
	
	@Column(name = "notifica_id")
	private Long notificaId;
}
