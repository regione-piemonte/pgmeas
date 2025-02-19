/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.util.service;

import static it.csi.pgmeas.commons.validation.ValidationUtils.checkEntityIsPresentByProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.v2.InterventoParereSaveExtDto;
import it.csi.pgmeas.commons.dto.v2.InterventoToPutByRegioneModel;
import it.csi.pgmeas.commons.dto.v2.ModuloAPutByRegioneModel;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.exception.PgmeasNoDataFoundException;
import it.csi.pgmeas.commons.model.Allegato;
import it.csi.pgmeas.commons.model.AllegatoTipo;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.repository.AllegatoRepository;
import it.csi.pgmeas.commons.service.AllegatoTipoUtilityService;
import it.csi.pgmeas.commons.util.enumeration.AllegatoTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.ValidationNameEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

@Service
public class AllegatoUtilityService {
	@Value("${download.base.path}")
	private String basePath;
	@Autowired
	private AllegatoTipoUtilityService allegatoTipoUtilityService;
	@Autowired
	private AllegatoRepository allegatoRepository;

	public void salvaAllegato(AllegatoLightExtDto dtoAllegatoIn, String codiceFiscale, Timestamp now,
			Intervento intervento, Ente ente, AllegatoTipoEnum allegatoTipoEnum) throws IOException, PgmeasException {
		if(dtoAllegatoIn != null && dtoAllegatoIn.getBase64() != null && dtoAllegatoIn.getIntAllegatoData() != null 
				&& !Strings.isEmpty(dtoAllegatoIn.getIntAllegatoNumero())) {
			salvaAllegato(dtoAllegatoIn, codiceFiscale, now, intervento, ente, allegatoTipoEnum, null);
		}
	}

	public void salvaAllegato(AllegatoLightExtDto dtoAllegatoIn, String codiceFiscale, Timestamp now,
			Intervento intervento, Ente ente, AllegatoTipoEnum allegatoTipoEnum, Integer intstrId)
			throws IOException, PgmeasException {
		String filePath = "%s/%s/%s".formatted(ente.getEnteCod(), Year.now(), intervento.getIntCod());

		String ext = FilenameUtils.getExtension(dtoAllegatoIn.getFileNameUser());
		String allegatoFileNameSystem = "%s.%s".formatted(UUID.randomUUID(), ext);
		byte[] allegatoBytes = Base64.getDecoder().decode(dtoAllegatoIn.getBase64());
		// CREA LA DIRECTORY E
		Files.createDirectories(Path.of(basePath, filePath));
		Files.write(Path.of(basePath, filePath, allegatoFileNameSystem), allegatoBytes);
		AllegatoTipo allegatoTipo = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(allegatoTipoEnum);
		Allegato allegato = new Allegato();
		allegato.setAllegatoProtocolloData(new Timestamp(dtoAllegatoIn.getIntAllegatoData().getTime()));
		allegato.setAllegatoProtocolloNumero(dtoAllegatoIn.getIntAllegatoNumero());
		allegato.setAllegatoTipoId(allegatoTipo.getAllegatoTipoId());
		allegato.setEnteId(ente.getEnteId());
		allegato.setFileNameSystem(allegatoFileNameSystem);
		allegato.setFileNameUser(dtoAllegatoIn.getFileNameUser());
		allegato.setFilePath(filePath);
		allegato.setFileType(dtoAllegatoIn.getFileType());
		allegato.setIntId(intervento.getIntId());
		allegato.setUtenteCreazione(codiceFiscale);
		allegato.setUtenteModifica(codiceFiscale);
		allegato.setDataCreazione(now);
		allegato.setDataModifica(now);
		allegato.setValiditaInizio(now);
		allegato.setIntstrId(intstrId);
		allegatoRepository.save(allegato);
	}
	
	public void storicizzaAllegato(AllegatoLightExtDto dtoAllegatoIn, Timestamp now, String codiceFiscale, Ente ente)
			throws PgmeasException {
		Optional<Allegato> allegatoOldOpt = allegatoRepository.findValidByAllegatoId(dtoAllegatoIn.getIdAllegato());
		checkEntityIsPresentByProperty(allegatoOldOpt, dtoAllegatoIn.getIdAllegato().toString(),
				ValidationNameEnum.ALLEGATO_ID);
		Allegato allegatoOld = allegatoOldOpt.orElseThrow(PgmeasNoDataFoundException::new);
		allegatoOld.setDataModifica(now);
		allegatoOld.setValiditaFine(now);
		allegatoOld.setUtenteModifica(codiceFiscale);
		allegatoRepository.save(allegatoOld);
	}

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


	public void salvaAllegatiRegione(InterventoToPutByRegioneModel body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved, Ente ente) throws PgmeasException, IOException {
		if (body.getDgrApprovazioneToDelete() != null
				&& body.getDgrApprovazioneToDelete().getIdAllegato() != null) {
			storicizzaAllegato(body.getDgrApprovazioneToDelete(), now, userInfo.codiceFiscale(), ente);
		}
		if (body.getDgrConsiglioRegionaleToDelete() != null
				&& body.getDgrConsiglioRegionaleToDelete().getIdAllegato() != null) {
			storicizzaAllegato(body.getDgrConsiglioRegionaleToDelete(), now, userInfo.codiceFiscale(), ente);
		}
		if (body.getDcrApprovazioneToDelete() != null
				&& body.getDcrApprovazioneToDelete().getIdAllegato() != null) {
			storicizzaAllegato(body.getDcrApprovazioneToDelete(), now, userInfo.codiceFiscale(), ente);
		}
		if (body.getDeterminazioniDirigenzialiToDelete() != null
				&& body.getDeterminazioniDirigenzialiToDelete().size() > 0) {
			for (AllegatoLightExtDto allegato : body.getDeterminazioniDirigenzialiToDelete()) {
				if (allegato.getIdAllegato() != null) {
					storicizzaAllegato(allegato, now, userInfo.codiceFiscale(), ente);
				}
			}
		}
		
		if (body.getDgrApprovazioneNew() != null && body.getDgrApprovazioneNew().getBase64() != null
				&& body.getDgrApprovazioneNew().getIdAllegato() == null) {
			salvaAllegato(body.getDgrApprovazioneNew(), userInfo.codiceFiscale(), now, interventoSaved, ente,
					AllegatoTipoEnum.DGR_DI_APPROVAZIONE);
		}

		if (body.getDgrConsiglioRegionaleNew() != null && body.getDgrConsiglioRegionaleNew().getBase64() != null
				&& body.getDgrConsiglioRegionaleNew().getIdAllegato() == null) {
			salvaAllegato(body.getDgrConsiglioRegionaleNew(), userInfo.codiceFiscale(), now, interventoSaved, ente,
					AllegatoTipoEnum.DGR_DI_PROPOSTA);
		}

		if (body.getDcrApprovazioneNew() != null && body.getDcrApprovazioneNew().getBase64() != null
				&& body.getDcrApprovazioneNew().getIdAllegato() == null) {
			salvaAllegato(body.getDcrApprovazioneNew(), userInfo.codiceFiscale(), now, interventoSaved, ente,
					AllegatoTipoEnum.DCR_DI_APPROVAZIONE);
		}

		if (body.getDeterminazioniDirigenzialiNew() != null && body.getDeterminazioniDirigenzialiNew().size() > 0) {
			for (AllegatoLightExtDto allegato : body.getDeterminazioniDirigenzialiNew()) {
				if (allegato.getBase64() != null && allegato.getIdAllegato() == null) {
					salvaAllegato(allegato, userInfo.codiceFiscale(), now, interventoSaved, ente,
							AllegatoTipoEnum.DETERMINA_DIRIGENZIALE_REGIONALE);
				}
			}
		}
	}
	
	public void salvaAllegatiModuloARegione(ModuloAPutByRegioneModel body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved, Ente ente) throws PgmeasException, IOException {
		if (body.getDecretoMinisterialeToDelete() != null
				&& body.getDecretoMinisterialeToDelete().getIdAllegato() != null) {
			storicizzaAllegato(body.getDecretoMinisterialeToDelete(), now, userInfo.codiceFiscale(), ente);
		}
		
		if (body.getNullaOstaToDelete() != null
				&& body.getNullaOstaToDelete().getIdAllegato() != null) {
			storicizzaAllegato(body.getNullaOstaToDelete(), now, userInfo.codiceFiscale(), ente);
		}
		
		if (body.getDecretoMinisterialeNew() != null && body.getDecretoMinisterialeNew().getBase64() != null
				&& body.getDecretoMinisterialeNew().getIdAllegato() == null) {
			salvaAllegato(body.getDecretoMinisterialeNew(), userInfo.codiceFiscale(), now, interventoSaved, ente,
					AllegatoTipoEnum.DECRETO_MINISTERIALE);
		} else {
			if (body.getNullaOstaNew() != null && body.getNullaOstaNew().getBase64() != null
				&& body.getNullaOstaNew().getIdAllegato() == null) {
				salvaAllegato(body.getNullaOstaNew(), userInfo.codiceFiscale(), now, interventoSaved, ente,
					AllegatoTipoEnum.NULLA_OSTA);
			}
		}
	}

	public void salvaAllegatiPareriRegione(UserInfoRecord userInfo, Timestamp now, Intervento interventoSaved,
			Ente ente, InterventoParereSaveExtDto parere, InterventoStruttura interventoStrutturaSaved)
			throws PgmeasException, IOException {
		if (parere.parerePpp().allegatoToDelete() != null
				&& parere.parerePpp().allegatoToDelete().getIdAllegato() != null) {
			storicizzaAllegato(parere.parerePpp().allegatoToDelete(), now, userInfo.codiceFiscale(), ente);
		}
		if (parere.parereHta().allegatoToDelete() != null
				&& parere.parereHta().allegatoToDelete().getIdAllegato() != null) {
			storicizzaAllegato(parere.parereHta().allegatoToDelete(), now, userInfo.codiceFiscale(), ente);
		}
		if (parere.parerePpp().allegatoNew() != null && parere.parerePpp().allegatoNew().getBase64() != null 
				&& parere.parerePpp().allegatoNew().getIdAllegato() == null) {
			salvaAllegato(parere.parerePpp().allegatoNew(), userInfo.codiceFiscale(), now, interventoSaved, ente,
					AllegatoTipoEnum.PARERE_PPP, interventoStrutturaSaved.getIntStrId());
		}
		if (parere.parereHta().allegatoNew() != null && parere.parereHta().allegatoNew().getBase64() != null
				&& parere.parereHta().allegatoNew().getIdAllegato() == null) {
			salvaAllegato(parere.parereHta().allegatoNew(), userInfo.codiceFiscale(), now, interventoSaved, ente,
					AllegatoTipoEnum.PARERE_HTA, interventoStrutturaSaved.getIntStrId());
		}
	}
}
