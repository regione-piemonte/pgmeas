/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class StrutturaDto extends CommonExtDto implements Serializable {
	
	private static final long serialVersionUID = 5412943860378018941L;

	private Integer strId;
	private String strCod;
	private String strDenominazione;
	private String strHsp11Cod;
	private String strFimCod;
	private String strBisCod;
	private String strIndirizzo;
	private Float strCoordinataX;
	private Float strCoordinataY;
	private Integer strIdPadre;
	private Integer enteId;
	private EnteDto ente;
	private String strComune;
	private String strComuneIstatCod;
	private Boolean strPgmeas;
	private Boolean strNonCensita;
	private Boolean strNuova;
	private String note;
	private String strDatiCatastali;
}
