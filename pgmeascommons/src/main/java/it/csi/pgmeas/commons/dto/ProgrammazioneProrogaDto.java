/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;

public class ProgrammazioneProrogaDto extends ProgrammazioneBaseDto implements Serializable {

	private static final long serialVersionUID = 5287282710550985313L;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", lenient = OptBoolean.FALSE)
	Date faseProrogaInizio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", lenient = OptBoolean.FALSE)
	Date faseProrogaFine;

	public Date getFaseProrogaInizio() {
		return faseProrogaInizio;
	}

	public void setFaseProrogaInizio(Date faseProrogaInizio) {
		this.faseProrogaInizio = faseProrogaInizio;
	}

	public Date getFaseProrogaFine() {
		return faseProrogaFine;
	}

	public void setFaseProrogaFine(Date faseProrogaFine) {
		this.faseProrogaFine = faseProrogaFine;
	}

}
