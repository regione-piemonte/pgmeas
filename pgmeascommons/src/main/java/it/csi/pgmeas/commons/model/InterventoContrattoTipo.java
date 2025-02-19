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
@Table(name="pgmeas_d_intervento_contratto_tipo")
public class InterventoContrattoTipo extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = -1204986985242462888L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "int_contratto_tipo_id")
	private Integer intContrattoTipoId;
	
	@Column(name = "int_contratto_tipo_cod")
	private String intContrattoTipoCod;
	
	@Column(name = "int_contratto_tipo_desc")
	private String intContrattoTipoDesc;
	
}
