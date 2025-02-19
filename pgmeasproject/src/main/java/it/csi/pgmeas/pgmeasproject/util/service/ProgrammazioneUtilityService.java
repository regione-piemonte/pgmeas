/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.util.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import it.csi.pgmeas.commons.dto.EnteProrogaDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneDto;
import it.csi.pgmeas.commons.model.EnteFase;

public class ProgrammazioneUtilityService {

	/**
	 * Costruisce un oggetto di programmazione a partire da un elenco di EntiFase.
	 * 
	 * @param anno anno della programmazione
	 * @param list elenco di EntiFase
	 * @return oggetto ProgrammazioneDto
	 */
	public static ProgrammazioneDto buildProgrammazione(int anno, List<EnteFase> list) {
		ProgrammazioneDto elem = list.stream().map(ef -> new ProgrammazioneDto(anno, 
				date(ef.getFaseInizio()), 
				date(ef.getFaseFine())))
				.distinct() //
				.findFirst() // Restituisce il primo elemento come Optional
				.orElse(null); // Se non ci sono elementi, restituisce null

		if (elem != null) {
			List<EnteProrogaDto> elencoEnti = list.stream()
					.map(ef -> new EnteProrogaDto(ef.getEnteId(), ef.getEnte().getEnteCodEsteso(),
							ef.getEnte().getEnteDesc(), date(ef.getFaseProrogaInizio()), date(ef.getFaseProrogaFine())))
					.distinct() // Filtra i duplicati
					.collect(Collectors.toList());

			elem.getEnti().addAll(elencoEnti);
		}

		return elem;
	}
	
	/**
	 * Converte un LocalDateTime in un oggetto Date.
	 * 
	 * @param ldt LocalDateTime da convertire
	 * @return oggetto Date
	 */
	public static Date date(LocalDateTime ldt) {
		if (ldt != null) {
			return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		} else {
			return null;
		}
	}

	/**
	 * Converte un oggetto Date in un LocalDateTime.
	 * 
	 * @param date oggetto Date da convertire
	 * @return LocalDateTime
	 */
	public static LocalDateTime localDateTime(Date date) {
		if (date != null) {
			return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		} else {
			return null;
		}
	}
}
