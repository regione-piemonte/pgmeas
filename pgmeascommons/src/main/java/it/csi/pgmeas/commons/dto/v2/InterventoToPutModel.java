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
public class InterventoToPutModel extends InterventoBase implements Serializable {
	
	private static final long serialVersionUID = -3069681570839783834L;
	
	private AllegatoLightExtDto intAllegatoDeliberaNew = null;
	private AllegatoLightExtDto intAllegatoDeliberaToDelete = null;
}
