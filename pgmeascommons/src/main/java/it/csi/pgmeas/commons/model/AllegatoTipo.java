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
@Table(name="pgmeas_d_allegato_tipo")
public class AllegatoTipo extends DecodificaBaseEntity implements Serializable {
	
	private static final long serialVersionUID = -4275219142534283325L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "allegato_tipo_id")
	private Integer allegatoTipoId;
	
	@Column(name = "allegato_tipo_cod")
	private String allegatoTipoCod;
	
	@Column(name = "allegato_tipo_desc")
	private String allegatoTipoDesc;
	
	
}
