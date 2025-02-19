/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.allegato;

import static it.csi.pgmeas.commons.validation.ValidationUtils.objectNullValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.stringNullOrEmptyValidator;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.v2.InterventoParereDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Allegato;
import it.csi.pgmeas.commons.model.AllegatoTipo;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.repository.AllegatoRepository;
import it.csi.pgmeas.commons.service.AllegatoTipoUtilityService;
import it.csi.pgmeas.commons.util.enumeration.AllegatoTipoEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

@Service
public class AllegatoUtility {
	@Autowired
	private AllegatoTipoUtilityService allegatoTipoUtilityService;
	@Autowired
	private AllegatoRepository allegatoRepository;

	public List<AllegatoLightExtDto> buildInterventoAllegatiDto(Integer interventoId, Integer enteId,
			AllegatoTipoEnum tipoAllegato) throws PgmeasException {
		List<AllegatoLightExtDto> allegatiDto = new ArrayList<AllegatoLightExtDto>();
		AllegatoTipo allegatoTipo = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(tipoAllegato);

		List<Allegato> allegati = allegatoRepository.findAllValidByIntIdAndEnteIdAndAllegatoTipoId(interventoId, 
				enteId, allegatoTipo.getAllegatoTipoId());

		if (allegati.isEmpty()) {
			return null;
		}

		allegati.stream().forEach(allegato -> {
			AllegatoLightExtDto allegatoDto = new AllegatoLightExtDto();
			allegatoDto.setIdAllegato(allegato.getAllegatoId());
			allegatoDto.setIntAllegatoData(allegato.getAllegatoProtocolloData());
			allegatoDto.setIntAllegatoNumero(allegato.getAllegatoProtocolloNumero());
			allegatoDto.setFileNameUser(allegato.getFileNameUser());
			allegatoDto.setFileType(allegato.getFileType());

			allegatiDto.add(allegatoDto);
		});

		return allegatiDto;
	}
	
	public List<AllegatoLightExtDto> buildInterventoStrutturaAllegatiDto(Integer interventoId, Integer enteId,
			Integer intStrId, AllegatoTipoEnum tipoAllegato) throws PgmeasException {
		List<AllegatoLightExtDto> allegatiDto = new ArrayList<AllegatoLightExtDto>();
		AllegatoTipo allegatoTipo = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(tipoAllegato);

		List<Allegato> allegati = allegatoRepository.findAllValidByIntIdAndEnteIdAndIntStrIdAndAllegatoTipoId(interventoId, 
				enteId, intStrId, allegatoTipo.getAllegatoTipoId());

		if (allegati.isEmpty()) {
			return null;
		}

		allegati.stream().forEach(allegato -> {
			AllegatoLightExtDto allegatoDto = new AllegatoLightExtDto();
			allegatoDto.setIdAllegato(allegato.getAllegatoId());
			allegatoDto.setIntAllegatoData(allegato.getAllegatoProtocolloData());
			allegatoDto.setIntAllegatoNumero(allegato.getAllegatoProtocolloNumero());
			allegatoDto.setFileNameUser(allegato.getFileNameUser());
			allegatoDto.setFileType(allegato.getFileType());
			
			allegatiDto.add(allegatoDto);
		});

		return allegatiDto;
	}

	public InterventoParereDto buildInterventoParereDto(InterventoStruttura interventoStruttura, Integer enteId,
			AllegatoTipoEnum allegatoTipoEnum) throws PgmeasException {
		Integer interventoId = interventoStruttura.getIntId();
		List<AllegatoLightExtDto> allegatiDto = buildInterventoStrutturaAllegatiDto(interventoId, enteId, 
				interventoStruttura.getIntStrId(), allegatoTipoEnum);

		Boolean parere = allegatoTipoEnum.equals(AllegatoTipoEnum.PARERE_PPP) ? interventoStruttura.getIntstrParerePpp()
				: interventoStruttura.getIntstrParereHta();

		InterventoParereDto parereDto = new InterventoParereDto(parere, allegatiDto);
		return parereDto;
	}

	
	public static void checkAllegatoObbligatorio(AllegatoTipoEnum allegatoTipoEnum,AllegatoLightExtDto allegato,
			List<ErroreDettaglio> listaErroriRilevati) {
		String nomeAllegato=allegatoTipoEnum.name();
		objectNullValidator(nomeAllegato + "intAllegato", allegato, listaErroriRilevati);
		if (allegato != null) {
			stringNullOrEmptyValidator(nomeAllegato + "intAllegatoNumero", allegato.getIntAllegatoNumero(), listaErroriRilevati);
			objectNullValidator(nomeAllegato + "intAllegatoData", allegato.getIntAllegatoData(), listaErroriRilevati);
			stringNullOrEmptyValidator(nomeAllegato + "fileType", allegato.getFileType(), listaErroriRilevati);
			stringNullOrEmptyValidator(nomeAllegato + "fileNameUser", allegato.getFileNameUser(), listaErroriRilevati);
			stringNullOrEmptyValidator(nomeAllegato + "base64", allegato.getBase64(), listaErroriRilevati);
		}
	}
	
	public static void checkAllegatoObbligatorio(AllegatoTipoEnum allegatoTipoEnum, Allegato allegato,
			List<ErroreDettaglio> listaErroriRilevati) {
		String nomeAllegato = allegatoTipoEnum.name();
		objectNullValidator(nomeAllegato + "intAllegato", allegato, listaErroriRilevati);
		if (allegato != null) {
			stringNullOrEmptyValidator(nomeAllegato + "intAllegatoNumero", allegato.getAllegatoProtocolloNumero(), listaErroriRilevati);
			objectNullValidator(nomeAllegato + "intAllegatoData", allegato.getAllegatoProtocolloData(), listaErroriRilevati);
			stringNullOrEmptyValidator(nomeAllegato + "fileType", allegato.getFileType(), listaErroriRilevati);
			stringNullOrEmptyValidator(nomeAllegato + "fileNameUser", allegato.getFileNameUser(), listaErroriRilevati);
		}
	}
	
	public static void checkAllegatoObbligatorio(AllegatoTipoEnum allegatoTipoEnum, Optional<Allegato> allegatoOpt,
			List<ErroreDettaglio> listaErroriRilevati) {
		String nomeAllegato = allegatoTipoEnum.name();
		objectNullValidator(nomeAllegato + "intAllegato", allegatoOpt, listaErroriRilevati);
		if (allegatoOpt.isPresent()) {
			Allegato allegato = allegatoOpt.get();
			stringNullOrEmptyValidator(nomeAllegato + "intAllegatoNumero", allegato.getAllegatoProtocolloNumero(), listaErroriRilevati);
			objectNullValidator(nomeAllegato + "intAllegatoData", allegato.getAllegatoProtocolloData(), listaErroriRilevati);
			stringNullOrEmptyValidator(nomeAllegato + "fileType", allegato.getFileType(), listaErroriRilevati);
			stringNullOrEmptyValidator(nomeAllegato + "fileNameUser", allegato.getFileNameUser(), listaErroriRilevati);
		} else {
			listaErroriRilevati.add(new ErroreDettaglio("",""));
		}
	}
	
	public static void checkAllegatoLightObbligatorio(AllegatoTipoEnum allegatoTipoEnum, Optional<Allegato> allegatoOpt,
			List<ErroreDettaglio> listaErroriRilevati) {
		String nomeAllegato = allegatoTipoEnum.name();
		objectNullValidator(nomeAllegato + "intAllegato", allegatoOpt, listaErroriRilevati);
		if (allegatoOpt.isPresent()) {
			Allegato allegato = allegatoOpt.get();
			stringNullOrEmptyValidator(nomeAllegato + "fileType", allegato.getFileType(), listaErroriRilevati);
			stringNullOrEmptyValidator(nomeAllegato + "fileNameUser", allegato.getFileNameUser(), listaErroriRilevati);
		} else {
			listaErroriRilevati.add(new ErroreDettaglio("",""));
		}
	}
	
	public List<Allegato> getAllegatiByIntervento(Integer interventoId, Integer enteId) {
		List<Allegato> allegati = allegatoRepository.findAllValidByIntIdAndEnteId(interventoId, enteId);
		return allegati;
	}
	
	public void storicizzaESalvaPareri(UserInfoRecord userInfo, Timestamp now, Integer intId,
			Integer enteId, InterventoStruttura interventoStrutturaOld, InterventoStruttura interventoStruttura)
			throws PgmeasException, IOException {
		
		if (interventoStruttura.getIntstrParerePpp() != null && interventoStruttura.getIntstrParerePpp()) {
			Allegato allegatoOld = storicizzaParereByIntId(intId, interventoStrutturaOld.getIntStrId(), AllegatoTipoEnum.PARERE_PPP, now, userInfo.codiceFiscale(), enteId);
			salvaParere(allegatoOld, userInfo.codiceFiscale(), now, interventoStruttura.getIntStrId());
		}
		
		if (interventoStruttura.getIntstrParereHta() != null && interventoStruttura.getIntstrParereHta()) {
			Allegato allegatoOld = storicizzaParereByIntId(intId, interventoStrutturaOld.getIntStrId(), AllegatoTipoEnum.PARERE_HTA, now, userInfo.codiceFiscale(), enteId);
			salvaParere(allegatoOld, userInfo.codiceFiscale(), now, interventoStruttura.getIntStrId());
		}
	}
	
	public Allegato storicizzaParereByIntId(Integer intId, Integer intStrId, AllegatoTipoEnum allegatoTipoEnum, Timestamp now, String codiceFiscale, Integer enteId)
			throws PgmeasException {
		AllegatoTipo allegatoTipo = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(allegatoTipoEnum);
		List<Allegato> allegatiOld = allegatoRepository.findAllValidByIntIdAndEnteIdAndIntStrIdAndAllegatoTipoId(intId, enteId, intStrId, allegatoTipo.getAllegatoTipoId());

		if (allegatiOld.isEmpty()) {
			return null;
		}
		
		Allegato allegatoOld = allegatiOld.get(0);
		allegatoOld.setDataModifica(now);
		allegatoOld.setValiditaFine(now);
		allegatoOld.setUtenteModifica(codiceFiscale);
		allegatoRepository.save(allegatoOld);
		
		return allegatoOld;
	}
	
	public void salvaParere(Allegato allegatoOld, String codiceFiscale, Timestamp now,
			Integer intstrId) throws IOException, PgmeasException {
		if(allegatoOld == null)
			return;
		
		Allegato allegato = new Allegato();
		allegato.setAllegatoProtocolloData(allegatoOld.getAllegatoProtocolloData());
		allegato.setAllegatoProtocolloNumero(allegatoOld.getAllegatoProtocolloNumero());
		allegato.setAllegatoTipoId(allegatoOld.getAllegatoTipoId());
		allegato.setEnteId(allegatoOld.getEnteId());
		allegato.setFileNameSystem(allegatoOld.getFileNameSystem());
		allegato.setFileNameUser(allegatoOld.getFileNameUser());
		allegato.setFilePath(allegatoOld.getFilePath());
		allegato.setFileType(allegatoOld.getFileType());
		allegato.setIntId(allegatoOld.getIntId());
		allegato.setUtenteCreazione(allegatoOld.getUtenteCreazione());
		allegato.setUtenteModifica(codiceFiscale);
		allegato.setDataCreazione(allegatoOld.getDataCreazione());
		allegato.setDataModifica(now);
		allegato.setValiditaInizio(now);
		allegato.setIntstrId(intstrId);
		allegatoRepository.save(allegato);
	}

}
