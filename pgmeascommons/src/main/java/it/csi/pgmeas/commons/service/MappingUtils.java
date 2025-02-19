/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeanUtils;

import it.csi.pgmeas.commons.model.EnteFase;
import it.csi.pgmeas.commons.util.record.InfoProgrammazione;

public class MappingUtils {

	/**
	 * Copia le proprietà da un oggetto sorgente a un oggetto di destinazione.
	 * <p>
	 * Utilizza {@link BeanUtils#copyProperties(Object, Object)} per copiare le
	 * proprietà comuni tra gli oggetti. Le proprietà dell'oggetto sorgente saranno
	 * trasferite all'oggetto di destinazione.
	 * </p>
	 * 
	 * @param <F>  il tipo dell'oggetto sorgente.
	 * @param <T>  il tipo dell'oggetto di destinazione.
	 * @param from l'oggetto sorgente dal quale copiare le proprietà. Non può essere
	 *             {@code null}.
	 * @param to   l'oggetto di destinazione nel quale copiare le proprietà. Non può
	 *             essere {@code null}.
	 * @return l'oggetto di destinazione con le proprietà copiate dall'oggetto
	 *         sorgente.
	 * 
	 * @throws IllegalArgumentException se {@code from} o {@code to} sono
	 *                                  {@code null}.
	 */
	public static <F, T> T copy(F from, T to) {
		BeanUtils.copyProperties(from, to);

		return to;
	}

	public static InfoProgrammazione getFrom(EnteFase enteFase) {
		if (enteFase == null) {
			return null;
		}

		// Controllo se la programmazione è aperta
		boolean programmazioneAperta = false;
		LocalDate now = LocalDate.now();
		if (enteFase.getFaseInizio() != null && enteFase.getFaseFine() != null && enteFase.getFaseProrogaFine() == null) {
			programmazioneAperta = (now.isAfter(enteFase.getFaseInizio().toLocalDate()) || now.isEqual(enteFase.getFaseInizio().toLocalDate()))
					&& (now.isBefore(enteFase.getFaseFine().toLocalDate()) || now.isEqual(enteFase.getFaseFine().toLocalDate()));
		} else if (enteFase.getFaseInizio() != null && enteFase.getFaseFine() != null && enteFase.getFaseProrogaFine() != null) {
			programmazioneAperta = (now.isAfter(enteFase.getFaseInizio().toLocalDate()) || now.isEqual(enteFase.getFaseInizio().toLocalDate()))
					&& (now.isBefore(enteFase.getFaseProrogaFine().toLocalDate()) || now.isEqual(enteFase.getFaseProrogaFine().toLocalDate()));
		}
		
		// Calcolo del triennio basato sull'anno di faseFine
		String triennio = null;
		if (enteFase.getFaseFine() != null) {
			int annoFine = enteFase.getFaseFine().getYear();
			triennio = annoFine + "-" + (annoFine + 2);
		}

		// Formattazione delle date (da LocalDateTime a String)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataInizioProgrammazione = enteFase.getFaseInizio() != null ? enteFase.getFaseInizio().format(formatter)
				: null;

		String dataFineProgrammazione = enteFase.getFaseFine() != null ? enteFase.getFaseFine().format(formatter)
				: null;
		
		String dataFineProroga = enteFase.getFaseProrogaFine() != null ? enteFase.getFaseProrogaFine().format(formatter)
				: null;

		String annoInserimentIntervento = enteFase.getFaseInizio() != null ? String.valueOf(now.getYear()) : null;

		return new InfoProgrammazione(programmazioneAperta, triennio, annoInserimentIntervento,
				dataInizioProgrammazione, dataFineProgrammazione, dataFineProroga);

	}

}
