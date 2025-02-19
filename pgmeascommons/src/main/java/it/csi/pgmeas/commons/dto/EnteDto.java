/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class EnteDto extends CommonExtDto implements Serializable{

	private static final long serialVersionUID = 8277076513906751517L;
	
	private Integer enteId;
	private String enteCodEsteso;
	private String enteRegioneCod;
	private String enteRegioneDesc;
	private String enteCod;
	private String enteDesc;
	private String enteIndirizzo;
	private String enteCap;
	private String enteComune;
	private String enteProvinciaSigla;
	private String enteTelefono;
	private String enteFax;
	private String enteEmail;
	private String enteSitoWeb;
	private String entePartitaIva;
	private String enteTipoId;

}
