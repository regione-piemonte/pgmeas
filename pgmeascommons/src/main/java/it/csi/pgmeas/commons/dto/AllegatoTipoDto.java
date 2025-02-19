/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AllegatoTipoDto extends CommonExtDto implements Serializable {

	private static final long serialVersionUID = 6444181436407998847L;
	
	private Integer allegatoTipoId;
	private String allegatoTipoCod;
	private String allegatoTipoDesc;
	
}
