/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgrammazioneDto extends ProgrammazioneBaseDto implements Serializable {

	private static final long serialVersionUID = -6320801554935988028L;
	private List<EnteProrogaDto> enti;

	public ProgrammazioneDto() {
		super();
	}

	public ProgrammazioneDto(Integer anno, Date faseInizio, Date faseFine) {
		super(anno, faseInizio, faseFine);
	}

	public List<EnteProrogaDto> getEnti() {
		if (enti == null)
			enti = new ArrayList<EnteProrogaDto>();

		return enti;
	}

}
