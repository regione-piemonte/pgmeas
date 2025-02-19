/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import lombok.Data;

@Data
public class ModuloAPutByRegioneModel implements Serializable {
	
	private static final long serialVersionUID = -3069681570839783834L;
	private Integer intId = null;
	private AllegatoLightExtDto nullaOstaNew = null;
	private AllegatoLightExtDto nullaOstaToDelete = null;
	private AllegatoLightExtDto decretoMinisterialeNew = null;
	private AllegatoLightExtDto decretoMinisterialeToDelete = null;
}
