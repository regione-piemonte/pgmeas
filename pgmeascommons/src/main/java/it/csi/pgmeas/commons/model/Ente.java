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
@Table(name="pgmeas_d_ente")
public class Ente extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = 1803280847116455572L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ente_id")
	private Integer enteId;
	
	@Column(name = "ente_cod_esteso")
	private String enteCodEsteso;
	
	@Column(name = "ente_regione_cod")
	private String enteRegioneCod;
	
	@Column(name = "ente_regione_desc")
	private String enteRegioneDesc;
	
	@Column(name = "ente_cod")
	private String enteCod;
	
	@Column(name = "ente_desc")
	private String enteDesc;
	
	@Column(name = "ente_indirizzo")
	private String enteIndirizzo;
	
	@Column(name = "ente_cap")
	private String enteCap;
	
	@Column(name = "ente_comune")
	private String enteComune;
	
	@Column(name = "ente_provincia_sigla")
	private String enteProvinciaSigla;
	
	@Column(name = "ente_telefono")
	private String enteTelefono;
	
	@Column(name = "ente_fax")
	private String enteFax;
	
	@Column(name = "ente_email")
	private String enteEmail;
	
	@Column(name = "ente_sito_web")
	private String enteSitoWeb;
	
	@Column(name = "ente_partita_iva")
	private String entePartitaIva;
	
	@Column(name = "ente_tipo_id")
	private Integer enteTipoId;
	
}
