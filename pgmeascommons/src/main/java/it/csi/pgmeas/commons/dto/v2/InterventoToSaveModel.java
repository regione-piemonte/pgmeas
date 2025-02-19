/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InterventoToSaveModel extends InterventoBase implements Serializable {
	
	private static final long serialVersionUID = 8639925314641644606L;
	
	private AllegatoLightExtDto intAllegatoDelibera = null;

}
