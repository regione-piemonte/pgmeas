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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="pgmeas_t_struttura")
public class Struttura extends DecodificaBaseEntity implements Serializable {

	private static final long serialVersionUID = 8153135380513689237L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "str_id")
	private Integer strId;
	
	@Column(name = "str_cod")
	private String strCod;
	
	@Column(name = "str_denominazione")
	private String strDenominazione;
	
	@Column(name = "str_hsp11_cod")
	private String strHsp11Cod;
	
	@Column(name = "str_fim_cod")
	private String strFimCod;
	
	@Column(name = "str_bis_cod")
	private String strBisCod;
	
	@Column(name = "str_indirizzo")
	private String strIndirizzo;
	
	@Column(name = "str_coordinata_x", columnDefinition = "NUMERIC", length = 20)
	private Float strCoordinataX;
	
	@Column(name = "str_coordinata_y", columnDefinition = "NUMERIC", length = 20)
	private Float strCoordinataY;
	
	@Column(name = "str_id_padre")
	private Integer strIdPadre;
	
	@Column(name = "ente_id", insertable=false, updatable=false)
	private Integer enteId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ente_id", referencedColumnName = "ente_id", nullable = false)
    private Ente ente;
	
	@Column(name = "str_comune")
	private String strComune;
	
	@Column(name = "str_comune_istat_cod")
	private String strComuneIstatCod;
	
	@Column(name = "str_pgmeas")
	private Boolean strPgmeas;
	
	@Column(name = "str_non_censita")
	private Boolean strNonCensita;
	
	@Column(name = "str_nuova")
	private Boolean strNuova;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "str_dati_catastali")
	private String strDatiCatastali;
}
