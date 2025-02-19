/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class AllegatoLightExtDto extends AllegatoLightDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7858107683136154077L;
	private Integer idAllegato=null;
	private String intAllegatoNumero= null;
	private Timestamp intAllegatoData=null;

}
