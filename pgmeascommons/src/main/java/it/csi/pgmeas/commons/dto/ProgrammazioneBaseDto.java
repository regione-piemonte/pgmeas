/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;

public class ProgrammazioneBaseDto implements Serializable {
	private static final long serialVersionUID = -5835106764126327447L;
	private Integer anno;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", lenient = OptBoolean.FALSE)
	private Date faseInizio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", lenient = OptBoolean.FALSE)
	private Date faseFine;

	public ProgrammazioneBaseDto() {
		super();
	}

	public ProgrammazioneBaseDto(Integer anno, Date faseInizio, Date faseFine) {
		super();
		this.anno = anno;
		this.faseInizio = faseInizio;
		this.faseFine = faseFine;
	}

	public Date getFaseInizio() {
		return faseInizio;
	}

	public void setFaseInizio(Date faseInizio) {
		this.faseInizio = faseInizio;
	}

	public Date getFaseFine() {
		return faseFine;
	}

	public void setFaseFine(Date faseFine) {
		this.faseFine = faseFine;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anno, faseFine, faseInizio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProgrammazioneBaseDto other = (ProgrammazioneBaseDto) obj;
		return Objects.equals(anno, other.anno) && Objects.equals(faseFine, other.faseFine)
				&& Objects.equals(faseInizio, other.faseInizio);
	}

	@Override
	public String toString() {
		return String.format("ProgrammazioneDto [anno=%s, faseInizio=%s, faseFine=%s]", anno, faseInizio, faseFine);
	}
}
