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

public class EnteProrogaDto implements Serializable {

	private static final long serialVersionUID = 1288299661329985085L;
	
	Integer enteId;
	String enteCodEsteso;
	String enteDesc;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", lenient = OptBoolean.FALSE) 
	Date faseProrogaInizio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", lenient = OptBoolean.FALSE) 
	Date faseProrogaFine;

	public EnteProrogaDto() {
		super();
	}

	public EnteProrogaDto(Integer enteId, String enteCodEsteso, String enteDesc, Date faseProrogaInizio,
			Date faseProrogaFine) {
		super();
		this.enteId = enteId;
		this.enteCodEsteso = enteCodEsteso;
		this.enteDesc = enteDesc;
		this.faseProrogaInizio = faseProrogaInizio;
		this.faseProrogaFine = faseProrogaFine;
	}

	public Integer getEnteId() {
		return enteId;
	}

	public void setEnteId(Integer enteId) {
		this.enteId = enteId;
	}

	public String getEnteCodEsteso() {
		return enteCodEsteso;
	}

	public void setEnteCodEsteso(String enteCodEsteso) {
		this.enteCodEsteso = enteCodEsteso;
	}

	public String getEnteDesc() {
		return enteDesc;
	}

	public void setEnteDesc(String enteDesc) {
		this.enteDesc = enteDesc;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(enteCodEsteso, enteDesc, enteId, faseProrogaFine, faseProrogaInizio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnteProrogaDto other = (EnteProrogaDto) obj;
		return Objects.equals(enteCodEsteso, other.enteCodEsteso) && Objects.equals(enteDesc, other.enteDesc)
				&& Objects.equals(enteId, other.enteId) && Objects.equals(faseProrogaFine, other.faseProrogaFine)
				&& Objects.equals(faseProrogaInizio, other.faseProrogaInizio);
	}

	@Override
	public String toString() {
		return String.format(
				"EnteProrogaDto [enteId=%s, enteCodEsteso=%s, enteDesc=%s, faseProrogaInizio=%s, faseProrogaFine=%s]",
				enteId, enteCodEsteso, enteDesc, faseProrogaInizio, faseProrogaFine);
	}

}
