/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model.base;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
@MappedSuperclass
@Data
public class DecodificaBaseEntity extends BaseEntity{
	@Column(name = "validita_inizio")
	private Timestamp validitaInizio;
	
	@Column(name = "validita_fine")
	private Timestamp validitaFine;
	
}
