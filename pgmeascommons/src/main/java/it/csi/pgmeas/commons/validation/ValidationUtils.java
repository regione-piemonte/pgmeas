/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.validation;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

import it.csi.pgmeas.commons.dto.EnteProrogaDto;
import it.csi.pgmeas.commons.dto.Errore;
import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.ProgrammazioneBaseDto;
import it.csi.pgmeas.commons.dto.ProgrammazioneProrogaDto;
import it.csi.pgmeas.commons.dto.RespingimentoDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasRuntimeException;
import it.csi.pgmeas.commons.model.InterventoStato;
import it.csi.pgmeas.commons.util.enumeration.ErroreEnum;
import it.csi.pgmeas.commons.util.enumeration.InterventoRequiredErrorEnum;
import it.csi.pgmeas.commons.util.enumeration.InterventoStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;

public class ValidationUtils {
	//it.csi.pgmeas.commons.validation.ValidationUtils.sanitizeInput(String)
    public static String sanitizeInput(String input) {
        if (input == null) return null;
        return input.replaceAll("[<>\"'&]", ""); // Rimuove caratteri pericolosi
    }
    
	public static void objectNullValidator(String objectName, Object objectToCheck,
			List<ErroreDettaglio> listaErroriRilevati) {
		if (objectToCheck == null) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(objectName);
			err.setValore(objectName + " non è stato compilato. Inserire un valore.");
			listaErroriRilevati.add(err);
		}
	}

	public static void listNullOrEmptyValidator(InterventoRequiredErrorEnum interventoErroriEnum, List<?> listToCheck,
			List<ErroreDettaglio> listaErroriRilevati) {
		if (listToCheck == null || listToCheck.size() == 0) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(interventoErroriEnum.name());
			err.setValore(interventoErroriEnum.getCodeForNull());
			listaErroriRilevati.add(err);
		}
	}

	public static void mapNullOrEmptyValidator(String mapName, Map<?, ?> m, List<ErroreDettaglio> listaErroriRilevati) {
		if (m == null || m.isEmpty()) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(mapName);
			err.setValore("Il campo " + mapName + " non contiene valori. Verificare e aggiungere informazioni.");
			listaErroriRilevati.add(err);
		}
	}
	
	public static void booleanNullOrFalseValidator(String fieldName, Boolean valueToCheck,
			List<ErroreDettaglio> listaErroriRilevati) {
		if (valueToCheck == null || !valueToCheck) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(fieldName);
			err.setValore("Il campo " + fieldName + " deve essere valorizzato");
			listaErroriRilevati.add(err);
		}
	}

	public static void stringNullOrEmptyValidator(String fieldName, String valueToCheck,
			List<ErroreDettaglio> listaErroriRilevati) {
		if (StringUtils.isBlank(valueToCheck)) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(fieldName);
			err.setValore("Il campo " + fieldName + " è vuoto. Inserire un valore valido.");
			listaErroriRilevati.add(err);
		}
	}

	public static void integerNullOrNotValidValidator(String fieldName, Integer valueToCheck,
			List<ErroreDettaglio> listaErroriRilevati) {
		if (valueToCheck == null || valueToCheck < 1) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(fieldName);
			err.setValore("Il campo " + fieldName + " deve essere un numero positivo maggiore di zero.");
			listaErroriRilevati.add(err);
		}
	}
	
	public static void integerNullOrZeroValidator(String fieldName, Integer valueToCheck,
			List<ErroreDettaglio> listaErroriRilevati) {
		if (valueToCheck == null || valueToCheck < 0) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(fieldName);
			err.setValore("Il campo " + fieldName + " deve essere un numero positivo maggiore di zero.");
			listaErroriRilevati.add(err);
		}
	}

	public static void bigDecimalNullOrNotValidValidator(String fieldName, BigDecimal valueToCheck,
			List<ErroreDettaglio> listaErroriRilevati) {
		if (valueToCheck == null) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave(fieldName);
			err.setValore("Il campo " + fieldName + " non è stato compilato. Inserire un valore numerico.");
			listaErroriRilevati.add(err);
		}
	}

	public static void checkAnnoDuePrecedenti(String fieldName, Integer anno) throws PgmeasException {
		if (anno == null) {
			return;
		}

		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		int currentYear = Year.now().getValue();
		if (anno < currentYear -2 || anno > currentYear -1) {
			listaErroriRilevati.add(new ErroreDettaglio(fieldName, "L'anno " + anno
					+ " non è valido. Deve essere compreso tra " + (currentYear - 2) + " e " + (currentYear - 1) + "."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Anno non ammesso", listaErroriRilevati,
					"Anno non ammesso: " + anno);
		}
	}
	
	public static void checkAnnoDueSuccessivi(String fieldName, Integer anno, List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {
		if (anno == null) {
			return;
		}

		int currentYear = Year.now().getValue();
		if (anno < currentYear || anno > currentYear + 2) {
			listaErroriRilevati.add(new ErroreDettaglio(ErroreEnum.MSG_057.getCode(), "Attenzione! L'anno di priorità inserito non è all'interno del triennio corrente"));
		}
	}
	
	public static void checkSottopriorita(String fieldName, String sottopriorita, List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {
		if (sottopriorita == null) {
			return;
		}

		String regex = "^[a-zA-Z]$";
        boolean isMatch = Pattern.matches(regex, sottopriorita);
		if (sottopriorita.length() > 1 && !isMatch) {
			listaErroriRilevati.add(new ErroreDettaglio(ErroreEnum.MSG_059.getCode(), "Attenzione! La sotto-priorità deve essere una lettera compresa tra A e Z"));
		}
	}

	public static void checkAnnoIntervento(String fieldName, Integer anno) throws PgmeasException {
		if (anno == null) {
			return;
		}

		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		int currentYear = Year.now().getValue();
		if (anno != currentYear) {
			listaErroriRilevati.add(new ErroreDettaglio(fieldName,
					"L'anno " +  anno + " inserito non corrisponde all'anno corrente (" + currentYear + ")."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Anno non ammesso", listaErroriRilevati,
					"Anno non ammesso: " + anno);
		}
	}

	public static void checkQueryParamGetIntervento(Integer anno, String codice, String titolo, String cup)
			throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		if (anno == null && Strings.isEmpty(codice) && Strings.isEmpty(titolo) && Strings.isEmpty(cup)) {
			listaErroriRilevati.add(new ErroreDettaglio(ErroreEnum.MSG_071.getCode(), 
					"Attenzione! Compila almeno uno dei campi tra anno inserimento, codice PGMEAS intervento, codice CUP, titolo intervento, prima di procedere"));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "MSG-071-Attenzione! Compila almeno uno dei campi tra anno inserimento, codice PGMEAS intervento, codice CUP, titolo intervento, prima di procedere",
					listaErroriRilevati, "MSG-071-Attenzione! Compila almeno uno dei campi tra anno inserimento, codice PGMEAS intervento, codice CUP, titolo intervento, prima di procedere");
		}
	}

	public static void checkEntityIsPresentByProperty(Optional<?> opt, String property,
			ValidationNameEnum validationName) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		if (opt.isEmpty()) {
			listaErroriRilevati.add(new ErroreDettaglio(validationName.getAttributo(),
					"Non sono stati impostati il valori per \'" + property + "\'."));
			generatePgmeasException(HttpStatus.NOT_FOUND, validationName.getEntita() + " non trovato",
					listaErroriRilevati,
					validationName.getEntita() + " by " + validationName.getAttributo() + " non trovato");
		}
	}

	public static void checkObjectIsPresentByProperty(Object obj, String property, ValidationNameEnum validationName)
			throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		if (obj == null) {
			listaErroriRilevati.add(new ErroreDettaglio(validationName.getAttributo(),
					"Non sono stati impostati il valori per \'" + property + "\'."));
			generatePgmeasException(HttpStatus.NOT_FOUND, validationName.getEntita() + " non trovato",
					listaErroriRilevati,
					validationName.getEntita() + " di " + validationName.getAttributo() + " non trovato");
		}
	}

	public static void generatePgmeasException(HttpStatus status, String code,
			List<ErroreDettaglio> listaErroriRilevati, String title) throws PgmeasException {
		Errore errore = new Errore();
		errore.setStatus(status.value());
		errore.setCode(code);
		errore.setDetail(listaErroriRilevati);
		errore.setTitle(title);

		throw new PgmeasException(title, status, errore);
	}

	public static void generatePgmeasRuntimeException(HttpStatus status, String code,
			List<ErroreDettaglio> listaErroriRilevati, String title) throws PgmeasRuntimeException {
		Errore errore = new Errore();
		errore.setStatus(status.value());
		errore.setCode(code);
		errore.setDetail(listaErroriRilevati);
		errore.setTitle(title);

		throw new PgmeasRuntimeException(title, status, errore);
	}
	private static void validateRequiredEnteProrogaDto(EnteProrogaDto body, String anno) throws PgmeasException {
		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
		objectNullValidator("data fine proroga", body.getFaseProrogaFine(), errorList);
		stringNullOrEmptyValidator("anno", anno, errorList);
		if (errorList.size() > 0) {
			generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD", errorList,
					"Payload non compliant");
		}
	}

	public static void validateProgrammazioneProrogaDto(ProgrammazioneProrogaDto body) throws PgmeasException {
		validateRequiredProgrammazioneProrogaDto(body);
		validateProrogaProgrammazioneIsConsistency(body);
	}

	private static void validateProrogaProgrammazioneIsConsistency(ProgrammazioneProrogaDto body)
			throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();

		boolean isValid = isDateInYear(body.getFaseInizio(), body.getAnno())
				&& isDateInYear(body.getFaseFine(), body.getAnno())
				&& areConsistentDates(body.getFaseInizio(), body.getFaseFine())
				&& areConsistentDates(body.getFaseInizio(), body.getFaseProrogaFine())
				&& areConsistentDates(body.getFaseFine(), body.getFaseProrogaFine());

		if (!isValid) {
			listaErroriRilevati.add(new ErroreDettaglio("date proroga non valide", ""));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Date non corrette", listaErroriRilevati,
					"Date non corrette ");
		}
	}

	private static void validateRequiredProgrammazioneProrogaDto(ProgrammazioneProrogaDto body) throws PgmeasException {
		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
		objectNullValidator("Data fine proroga", body.getFaseProrogaFine(), errorList);
		objectNullValidator("anno", body.getAnno(), errorList);
		objectNullValidator("Data inizio programmazione", body.getFaseInizio(), errorList);
		objectNullValidator("Data fine programmazione", body.getFaseFine(), errorList);

		if (errorList.size() > 0) {
			generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD", errorList,
					"Payload non compliant");
		}
	}

	public static void validateRespingimentoDto(RespingimentoDto respingimentoDto) throws PgmeasException {
		List<ErroreDettaglio> errorList = new ArrayList<>();
		objectNullValidator("Motivo del respingimento", respingimentoDto, errorList);
		if (respingimentoDto != null) {
			stringNullOrEmptyValidator("note del respingimento", respingimentoDto.getNote(), errorList);
		}
		if (!errorList.isEmpty()) {
			generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD", errorList,
					"Dati di respingimento mancanti o incompleti.");
		}
	}

	// ---
	private static boolean validateBase(ProgrammazioneBaseDto programmazioneBaseDto, int anno) {
		// Controllo se l'anno delle date coincide con l'anno fornito
		return isDateInYear(programmazioneBaseDto.getFaseInizio(), anno)
				&& isDateInYear(programmazioneBaseDto.getFaseFine(), anno)
				&& areConsistentDates(programmazioneBaseDto.getFaseInizio(), programmazioneBaseDto.getFaseFine());
	}

	private static boolean validate(EnteProrogaDto ente, int anno) {
		// Controllo se l'anno delle date coincide con l'anno fornito
		boolean isValid = isDateInYear(ente.getFaseProrogaInizio(), anno)
				&& isDateInYear(ente.getFaseProrogaFine(), anno)
				&& areConsistentDates(ente.getFaseProrogaInizio(), ente.getFaseProrogaFine());

		return isValid;
	}

	private static boolean isDateInYear(Date date, int anno) {
		if (date == null) {
			return true; // Tratta le date null come valide
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) == anno;
	}

	private static boolean areConsistentDates(Date start, Date end) {
		if(start == null) {
			return true;
		}
		return start.before(end);
	}

	public static void validateEnteProrogaDto(EnteProrogaDto body, String anno) throws PgmeasException {
		validateRequiredEnteProrogaDto(body, anno);
		validateAnnoIsConsistent(anno);
		validateDateAreConsistent(body, Integer.parseInt(anno));
	}


	public static void validateProrogaProgrammazioneFasi(Date faseInizio, Date faseFine, EnteProrogaDto body)
			throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		boolean isValid = areConsistentDates(faseInizio, body.getFaseProrogaFine())
				&& areConsistentDates(faseFine, body.getFaseProrogaFine());
		if (!isValid) {
			listaErroriRilevati.add(new ErroreDettaglio("date della proroga",
					"Le date della proroga non sono valide: verificare che le date siano coerenti con l'intervallo temporale previsto."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Date non corrette", listaErroriRilevati,
					"Errore nelle date di proroga.");
		}
	}

	private static void validateAnnoIsConsistent(String anno) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		try {
			Integer.parseInt(anno);
		} catch (Exception e) {
			listaErroriRilevati.add(new ErroreDettaglio("anno",
					"Il formato dell'anno fornito non è corretto. Deve essere un valore numerico."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "FormatoAnno", listaErroriRilevati,
					"Anno non corretto.");
		}
	}

	public static void checkStatoInterventoConsistente(Integer statoOld, InterventoStato statoConsistente)
			throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		if (!statoConsistente.getIntStatoId().equals(statoOld)) {
			listaErroriRilevati.add(new ErroreDettaglio("stato dell'intervento", "Lo stato attuale dell'intervento ("
					+ sanitizeInput(statoConsistente.getIntStatoDesc()) + ") non corrisponde allo stato richiesto per la modifica."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Cambio Stato non Ammesso", listaErroriRilevati,
					"Stato intervento non coerente.");
		}
	}

	public static void checkStatoIntervento(InterventoStato interventoStato, InterventoStatoEnum stato)
			throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		if (!stato.getCode().equalsIgnoreCase(interventoStato.getIntStatoCod())) {
			listaErroriRilevati.add(new ErroreDettaglio("stato dell'intervento", "Lo stato corrente ("
					+ sanitizeInput( interventoStato.getIntStatoDesc()) + ") non consente l'operazione richiesta."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Stato non Ammesso", listaErroriRilevati,
					"Stato intervento non coerente.");
		}
	}
	
	public static void checkStatoIntervento(InterventoStato interventoStato, List<InterventoStatoEnum> stati)
			throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		if (stati.stream().filter(s -> s.getCode().equalsIgnoreCase(interventoStato.getIntStatoCod())).findAny().isEmpty()) {
			listaErroriRilevati.add(new ErroreDettaglio("stato dell'intervento", "Lo stato corrente ("
					+ sanitizeInput(interventoStato.getIntStatoDesc()) + ") non consente l'operazione richiesta."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Stato non Ammesso", listaErroriRilevati,
					"Stato intervento non coerente.");
		}
	}


	public static void checkCfRup(String cfRupIntervento, String cfLogged) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		if (!cfRupIntervento.equalsIgnoreCase(cfLogged)) {
			listaErroriRilevati.add(new ErroreDettaglio("codice fiscale",
					"L'utente con codice fiscale " + cfLogged + " non è autorizzato per questa operazione."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Utente Rup non Ammesso", listaErroriRilevati,
					"MSG-072: Attenzione! il codice fiscale del RUP deve essere identico a quello dell'utente collegato altrimenti non è possibile inviare l'intervento a Regione Piemonte");
		}
	}

	public static void validaInserisciProgrammazione(ProgrammazioneBaseDto programmazione) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		objectNullValidator("Data Inizio Programmazione", programmazione.getFaseInizio(), listaErroriRilevati);
		objectNullValidator("Data Fine Programmazione", programmazione.getFaseFine(), listaErroriRilevati);
		objectNullValidator("Anno", programmazione.getAnno(), listaErroriRilevati);

		if (!listaErroriRilevati.isEmpty()) {
			generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD", listaErroriRilevati,
					"Dati di programmazione mancanti o incompleti.");
		}

		if (!validateBase(programmazione, programmazione.getAnno())) {
			listaErroriRilevati.add(new ErroreDettaglio("Date della Programmazione",
					"Le date di inizio e fine programmazione non sono coerenti o non appartengono all'anno "
							+ programmazione.getAnno() + "."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Date non corrette", listaErroriRilevati,
					"Date di programmazione non valide.");
		}
	}

	private static void validateDateAreConsistent(EnteProrogaDto body, int anno) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<>();
		if (!validate(body, anno)) {
			listaErroriRilevati.add(new ErroreDettaglio("Date della proroga",
					"Le date fornite non sono coerenti o non appartengono all'anno " + anno + "."));
			generatePgmeasException(HttpStatus.BAD_REQUEST, "Date non corrette", listaErroriRilevati,
					"Errore nella validazione delle date.");
		}
	}



}
