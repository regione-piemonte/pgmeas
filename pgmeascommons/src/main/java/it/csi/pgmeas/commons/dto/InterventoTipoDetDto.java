/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class InterventoTipoDetDto extends CommonExtDto implements Serializable {
	
	private static final long serialVersionUID = 1563577864655885795L;
	
	private Integer intTipoDetId;
	private String intTipoDetCod;
	private String intTipoDetDesc;
	private Integer intTipoId;
	private InterventoTipoDto interventoTipo;

}
