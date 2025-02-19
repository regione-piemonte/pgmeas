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
@Table(name="pgmeas_d_evento_tipo")
public class EventoTipo extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -1807889861077048487L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evento_tipo_id")
	private Integer eventoTipoId;
	
	@Column(name = "evento_tipo_cod")
	private String eventoTipoCod;
	
	@Column(name = "evento_tipo_desc")
	private String eventoTipoDesc;
	
	@Column(name = "evento_max_numero_retry_notifica")
	private Integer eventoMaxNumeroRetryNotifica;
	
	@Column(name = "evento_max_gg_retry_notifica")
	private Integer eventoMaxGgRetryNotifica;
	
	@Column(name = "evento_gruppo_id")
	private Integer eventoGruppoId;
	
	
	
}
