/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;

import it.csi.pgmeas.commons.dto.CommonExtDto;
import lombok.Data;

@Data
public class StrutturaNewDto extends CommonExtDto implements Serializable {
	
	private static final long serialVersionUID = 5412943860378018941L;

	private Boolean strNonCensita;
	private Boolean strNuova;
	private String strDenominazione;
	private String strComune;
	private String strIndirizzo;
	private String strDatiCatastali;
	private String note;
	private Integer enteId;
}
