/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import static it.csi.pgmeas.commons.util.allegato.AllegatoUtility.checkAllegatoLightObbligatorio;
import static it.csi.pgmeas.commons.util.allegato.AllegatoUtility.checkAllegatoObbligatorio;
import static it.csi.pgmeas.commons.validation.ValidationUtils.integerNullOrNotValidValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.mapNullOrEmptyValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.objectNullValidator;
import static it.csi.pgmeas.commons.validation.ValidationUtils.stringNullOrEmptyValidator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.ErroreDettaglio;
import it.csi.pgmeas.commons.dto.RInterventoModuloDto;
import it.csi.pgmeas.commons.dto.RafInterventoStrutturaDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoDto;
import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoPutDto;
import it.csi.pgmeas.commons.dto.v2.ModuloAPutByRegioneModel;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Allegato;
import it.csi.pgmeas.commons.model.AllegatoTipo;
import it.csi.pgmeas.commons.model.Ente;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoPrevisioneSpesa;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.model.Modulo;
import it.csi.pgmeas.commons.model.ModuloFile;
import it.csi.pgmeas.commons.model.ModuloStato;
import it.csi.pgmeas.commons.model.RInterventoModulo;
import it.csi.pgmeas.commons.model.Struttura;
import it.csi.pgmeas.commons.repository.InterventoRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaRepository;
import it.csi.pgmeas.commons.repository.ModuloFileRepository;
import it.csi.pgmeas.commons.repository.ModuloRepository;
import it.csi.pgmeas.commons.repository.ModuloStatoRepository;
import it.csi.pgmeas.commons.repository.RInterventoModuloRepository;
import it.csi.pgmeas.commons.util.allegato.AllegatoUtility;
import it.csi.pgmeas.commons.util.enumeration.AllegatoTipoEnum;
import it.csi.pgmeas.commons.util.enumeration.ErroreEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.commons.validation.ValidationUtils;

@Service
public class ModuloUtilityService {
	@Autowired
	private ModuloStatoRepository moduloStatoRepository;
	@Autowired
	private ModuloRepository moduloRepository;
	@Autowired
	private ModuloFileRepository moduloFileRepository;
	@Autowired
	private RInterventoModuloRepository rInterventoModuloRepository;
	@Autowired
	private CronoProgrammaUtilityService cronoProgrammaUtilityService;
	@Autowired
	private PrevisioniDiSpesaUtilityService previsioniDiSpesaUtilityService;
	@Autowired
	private InterventoRepository interventoRepository;
	@Autowired
	private InterventoStrutturaRepository interventoStrutturaRepository;
	@Autowired
	private AllegatoUtility allegatoUtility;
	@Autowired
	private AllegatoTipoUtilityService allegatoTipoUtilityService;
	@Autowired
	private StrutturaUtilityService strutturaUtilityService;

	protected static Integer get(ModuloStato moduloStato) {
		return (moduloStato != null ? moduloStato.getModuloStatoId() : null);
	}

	protected static Integer get(Modulo moduloTipo) {
		return (moduloTipo != null ? moduloTipo.getModuloId() : null);
	}
	
	protected static Integer get(ModuloFile moduloFile) {
		return (moduloFile != null ? moduloFile.getFileId() : null);
	}
	
	protected static String getUtenteNomeCognome(UserInfoRecord userInfo) {
		return userInfo.nome() + " " + userInfo.cognome();
	}
	
	protected ModuloFile buildModuloFile(AllegatoLightExtDto dtoAllegatoIn, UserInfoRecord userInfo, Timestamp now,
			Ente ente, String filePath, String allegatoFileNameSystem) {
		ModuloFile moduloFile = new ModuloFile();
		moduloFile.setFileNameSystem(allegatoFileNameSystem);
		moduloFile.setFileNameUser(dtoAllegatoIn.getFileNameUser());
		moduloFile.setFilePath(filePath);
		moduloFile.setFileType(dtoAllegatoIn.getFileType());
		moduloFile.setUtenteCreazione(userInfo.codiceFiscale());
		moduloFile.setUtenteModifica(userInfo.codiceFiscale());
		moduloFile.setDataCreazione(now);
		moduloFile.setDataModifica(now);
		moduloFile.setValiditaInizio(now);
		moduloFile.setEnteId(ente.getEnteId());
		return moduloFile;
	}
	
	private RInterventoModulo buildRInterventoModulo(UserInfoRecord userInfo, Timestamp now, Integer interventoId, Integer enteId,
			Integer moduloTipoId, Integer moduloStatoId, Integer moduloFileId, String utenteCreazione, 
			Timestamp dataCreazione, String utenteNomeCognome, String note) {
		RInterventoModulo rInterventoModulo = new RInterventoModulo();
		rInterventoModulo.setEnteId(enteId);
		rInterventoModulo.setFileId(moduloFileId);
		rInterventoModulo.setIntId(interventoId);
		rInterventoModulo.setModuloId(moduloTipoId);
		rInterventoModulo.setModuloStatoId(moduloStatoId);
		rInterventoModulo.setValiditaInizio(now);
		rInterventoModulo.setUtenteCreazione(utenteCreazione);
		rInterventoModulo.setUtenteModifica(userInfo.codiceFiscale());
		rInterventoModulo.setDataCreazione(dataCreazione);
		rInterventoModulo.setDataModifica(now);
		rInterventoModulo.setUtenteNomeCognome(utenteNomeCognome);
		rInterventoModulo.setNote(note);
		
		return rInterventoModulo;
	}
	
	protected RInterventoModulo inserisciRInterventoModulo(Integer interventoId, Integer enteId, UserInfoRecord userInfo,
			Timestamp now, Integer moduloTipoId, Integer moduloStatoId, Integer moduloFileId, String utenteCreazione, 
			Timestamp dataCreazione, String utenteNomeCognome, String note) {
		RInterventoModulo rInterventoModulo = buildRInterventoModulo(userInfo, now, interventoId, enteId, moduloTipoId, moduloStatoId, 
				moduloFileId, utenteCreazione, dataCreazione, utenteNomeCognome, note);
		rInterventoModulo = rInterventoModuloRepository.saveAndFlush(rInterventoModulo);
		return rInterventoModulo;
	}
	
	public void inserisciModuloFittizioByRInterventoModuloOld(Intervento intervento, Ente ente, ModuloTipoEnum moduloTipoEnum,
			ModuloStatoEnum moduloStatoEnum, UserInfoRecord userInfo, Timestamp now,RInterventoModulo rInterventoModuloOld) throws PgmeasException {
		
		Modulo moduloTipo = getModuloByModuloCod(moduloTipoEnum);
		ModuloStato moduloStato = getModuloStatoByModuloStatoCod(moduloStatoEnum);
		inserisciRInterventoModulo(intervento.getIntId(), ente.getEnteId(), userInfo, now, get(moduloTipo), get(moduloStato), 
				null, rInterventoModuloOld.getUtenteCreazione(), rInterventoModuloOld.getDataCreazione(), 
				rInterventoModuloOld.getUtenteNomeCognome(), null);
	}
	
	public RInterventoModuloDto inserisciModuloFittizioByIntervento(Intervento intervento, Ente ente, ModuloTipoEnum moduloTipoEnum,
			ModuloStatoEnum moduloStatoEnum, UserInfoRecord userInfo, Timestamp now) throws PgmeasException {
		RInterventoModuloDto resultDto = new RInterventoModuloDto();
		
		Modulo moduloTipo = getModuloByModuloCod(moduloTipoEnum);
		ModuloStato moduloStato = getModuloStatoByModuloStatoCod(moduloStatoEnum);
		RInterventoModulo rInterventoModulo = inserisciRInterventoModulo(intervento.getIntId(), ente.getEnteId(), userInfo, now, get(moduloTipo),
				get(moduloStato), null, userInfo.codiceFiscale(), now, getUtenteNomeCognome(userInfo), null);	
		
		//CORREZIONE SONARQUBE
		if(moduloTipo != null) {
			if(moduloTipo.getModuloCod() != null) {
				resultDto.setModuloTipoId(moduloTipo.getModuloCod());
			}else {
				resultDto.setModuloTipoId(null);
			}
		}		
		//CORREZIONE SONARQUBE
		if(moduloStato != null) {
			if(moduloStato.getModuloStatoCod() != null) {
				resultDto.setStatoModuloCod(moduloStato.getModuloStatoCod());
			}else {
				resultDto.setStatoModuloCod(null);
			}
		}		
		resultDto.setRIntModuloId(rInterventoModulo.getRIntModuloId());
		return resultDto;
	}
	
	public RInterventoModulo cambioStatoModuloByRInterventoModulo(RInterventoModulo rInterventoModulo,
			ModuloStatoEnum moduloStatoNewEnum, Timestamp now, UserInfoRecord userInfo, Ente ente)
			throws PgmeasException {
		return cambioStatoModuloByRInterventoModulo(rInterventoModulo, moduloStatoNewEnum, now, userInfo, ente, null);
	}

	public RInterventoModulo cambioStatoModuloByRInterventoModulo(RInterventoModulo rInterventoModulo,
			ModuloStatoEnum moduloStatoNewEnum, Timestamp now, UserInfoRecord userInfo, Ente ente, String note)
			throws PgmeasException {

		ModuloStato moduloStatoNew = getModuloStatoByModuloStatoCod(moduloStatoNewEnum);
		RInterventoModulo moduloOld = storicizzaModuloByRInterventoModulo(rInterventoModulo, now, userInfo.codiceFiscale());
		return inserisciRInterventoModulo(moduloOld.getIntId(), moduloOld.getEnteId(), userInfo, now, moduloOld.getModuloId(), 
				get(moduloStatoNew), moduloOld.getFileId(), userInfo.codiceFiscale(), now, moduloOld.getUtenteNomeCognome(), note);
	}
	
	public RInterventoModulo cambioStatoModuloByInterventoId(Integer interventoId, ModuloTipoEnum moduloTipoEnum,
			ModuloStatoEnum moduloStatoNewEnum, Timestamp now, UserInfoRecord userInfo, Ente ente)
			throws PgmeasException {
		return cambioStatoModuloByInterventoId(interventoId, moduloTipoEnum, moduloStatoNewEnum, now, userInfo, ente, null);
	}
	
	public RInterventoModulo cambioStatoModuloByInterventoId(Integer interventoId, ModuloTipoEnum moduloTipoEnum,
			ModuloStatoEnum moduloStatoNewEnum, Timestamp now, UserInfoRecord userInfo, Ente ente, String note)
			throws PgmeasException {

		ModuloStato moduloStatoNew = getModuloStatoByModuloStatoCod(moduloStatoNewEnum);
		RInterventoModulo moduloOld = storicizzaModuloByInterventoIdAndEnteId(interventoId, moduloTipoEnum, now, userInfo.codiceFiscale(), ente.getEnteId());
		return inserisciRInterventoModulo(moduloOld.getIntId(), moduloOld.getEnteId(), userInfo, now, moduloOld.getModuloId(), 
				get(moduloStatoNew), moduloOld.getFileId(), userInfo.codiceFiscale(), now, getUtenteNomeCognome(userInfo), note);
	}

	public RInterventoModulo storicizzaModuloByRInterventoModulo(RInterventoModulo moduloOld, Timestamp now,
			String codiceFiscale) throws PgmeasException {
		moduloOld.setValiditaFine(now);
		moduloOld.setDataModifica(now);
		moduloOld.setUtenteModifica(codiceFiscale);
		rInterventoModuloRepository.save(moduloOld);
		return moduloOld;
	}
	
    private RInterventoModulo getRInterventoModuloByInterventoIdAndModuloTipoId(Integer interventoId,
            Integer moduloTipoId, Integer enteId) throws PgmeasException {    	
        List<RInterventoModulo> rInterventoModuloList = rInterventoModuloRepository
                .findAllValidByIntIdAndModuloIdAndEnteId(interventoId, moduloTipoId, enteId);
        if (rInterventoModuloList != null && rInterventoModuloList.size() > 0) {
            return rInterventoModuloList.get(0);
        } else {
            ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "RInterventoModulo non trovato",
                    new ArrayList<ErroreDettaglio>(),
                    "RInterventoModulo non trovato per Intervento:" + interventoId + ", e Modulo:" + moduloTipoId);
        }
        return null;
    }
	
	public RInterventoModulo storicizzaModuloByInterventoIdAndEnteId(Integer interventoId, ModuloTipoEnum moduloTipoEnum, Timestamp now,
			String codiceFiscale, Integer enteId) throws PgmeasException {
		Modulo moduloTipo = getModuloByModuloCod(moduloTipoEnum);
		//CORREZIONE SONARQUBE
		Integer moduloId = null;
		if(moduloTipo != null) {
			if(moduloTipo.getModuloId() != null) {
				moduloId = moduloTipo.getModuloId();
			}
		}
        RInterventoModulo moduloOld = getRInterventoModuloByInterventoIdAndModuloTipoId(interventoId, moduloId, enteId);
        //CORREZIONE SONARQUBE
        if(moduloOld!=null) {
        	moduloOld.setValiditaFine(now);
    		moduloOld.setDataModifica(now);
    		moduloOld.setUtenteModifica(codiceFiscale);
        }		
		rInterventoModuloRepository.save(moduloOld);
		return moduloOld;
	}

	public void checkStatusModuloByRInterventoModuloAndEnteId(RInterventoModulo rInterventoModulo, ModuloStatoEnum statoEnum, Integer enteId)
			throws PgmeasException {
		Integer moduloStatoId = rInterventoModulo.getModuloStatoId();
		ModuloStato stato = getModuloStatoByModuloStatoId(moduloStatoId);
		//CORREZIONE SONARQUBE
		if(stato != null) {
			if(stato.getModuloStatoCod() != null) {
				if(!stato.getModuloStatoCod().equals(statoEnum.getCode())) {
					ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST",
							new ArrayList<ErroreDettaglio>(), "Modulo Ammissione Finanziamento presente per l'intervento "
									+ rInterventoModulo.getIntId() + " in stato diverso da " + statoEnum.getCode());
				}
			}
		}
	}
	
	public void checkStatusModuloByRInterventoModuloAndEnteId(RInterventoModulo rInterventoModulo, List<ModuloStatoEnum> statiEnum, Integer enteId)
			throws PgmeasException {
		Integer moduloStatoId = rInterventoModulo.getModuloStatoId();
		ModuloStato stato = getModuloStatoByModuloStatoId(moduloStatoId);
		//CORREZIONE SONARQUBE
		if(stato != null) {
			if(stato.getModuloStatoCod() != null) {
				if(!statiEnum.stream().anyMatch(statoEnum -> statoEnum.getCode().equals(stato.getModuloStatoCod()))) {
					ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST",
							new ArrayList<ErroreDettaglio>(), "Modulo " + rInterventoModulo.getRIntModuloId() + " per l'intervento "
									+ rInterventoModulo.getIntId() + " in stato diverso da " + statiEnum);
				}
			}
		}
	}
	
	public void checkFileByRInterventoModulo(RInterventoModulo rInterventoModulo)
			throws PgmeasException {
		if(rInterventoModulo.getFileId() == null) {
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST",
					new ArrayList<ErroreDettaglio>(), "File per modulo " + rInterventoModulo.getRIntModuloId() + " per l'intervento "
							+ rInterventoModulo.getIntId() + " non trovato");
		}
	}
	
	public List<RInterventoModulo> getModuliByTipoAndInterventoIdAndEnteId(Integer interventoId, ModuloTipoEnum moduloTipoEnum, Integer enteId)
			throws PgmeasException {
		List<RInterventoModulo> rInterventoModulo= null;
		Modulo moduloTipo = getModuloByModuloCod(moduloTipoEnum);
		//CORREZIONE SONARQUBE
		if(moduloTipo != null) {
			if(moduloTipo.getModuloId() != null) {
				rInterventoModulo =  rInterventoModuloRepository.findAllValidByIntIdAndModuloIdAndEnteId(interventoId, moduloTipo.getModuloId(), enteId);
			}
		}
		return rInterventoModulo;		
	}
	
	public boolean moduloInseritoInterventoIdAndModuloTipoEnumAndRInterventoIdisPresentAndEnteId(Integer interventoId,
			ModuloTipoEnum moduloTipoEnum, Integer rIntModuloId, Integer enteId) throws PgmeasException {
		ModuloStato statoInserito = getModuloStatoByModuloStatoCod(ModuloStatoEnum.INSERITO);
		boolean isPresent = false;
		Modulo moduloTipo = getModuloByModuloCod(moduloTipoEnum);
		//CORREZIONE SONARQUBE
		if(moduloTipo != null) {
			if(moduloTipo.getModuloId() != null) {				
				//CORREZIONE SONARQUBE
				if(statoInserito != null) {
					if(statoInserito.getModuloStatoId() != null) {
						List<RInterventoModulo> interventoModuloList = rInterventoModuloRepository
								.findAllValidByIntIdAndModuloIdAndModuloStatoIdAndEnteId(interventoId, moduloTipo.getModuloId(),
										statoInserito.getModuloStatoId(), enteId);
						if (!interventoModuloList.isEmpty()) {
							RInterventoModulo rIntModulo = interventoModuloList.get(0);
							isPresent = rIntModulo.getRIntModuloId().equals(rIntModuloId);
						}
					}
				}
				
			}
		}
		return isPresent;
	}

	public RInterventoModulo getModuloAByInterventoIdAndEnteId(Integer interventoId, Integer enteId)
			throws PgmeasException {
		Modulo moduloTipo = getModuloByModuloCod(ModuloTipoEnum.MODULO_A);
		//CORREZIONE SONARQUBE
		Integer moduloId = null;
		if(moduloTipo != null) {
			if(moduloTipo.getModuloId() != null) {
				moduloId = moduloTipo.getModuloId();
			}
		}
		
		List<RInterventoModulo> moduloAList = rInterventoModuloRepository.findAllValidByIntIdAndModuloIdAndEnteId(interventoId, moduloId, enteId);
		if(moduloAList.size() == 0) {
			moduloTipo = getModuloByModuloCod(ModuloTipoEnum.MODULO_A_A);
			if(moduloTipo != null) {
				if(moduloTipo.getModuloId() != null) {
					moduloId = moduloTipo.getModuloId();
				}
			}
			List<RInterventoModulo> moduloAAList = rInterventoModuloRepository.findAllValidByIntIdAndModuloIdAndEnteId(interventoId, moduloId, enteId);
			if(moduloAAList.size() == 0) {
				return null;
			}
			
			return moduloAAList.get(0);
		}		
		return moduloAList.get(0);
	}
	
	public RInterventoModulo getModuloIntByInterventoIdAndEnteId(Integer interventoId, Integer enteId)
			throws PgmeasException {
		Modulo moduloTipo = getModuloByModuloCod(ModuloTipoEnum.DOCUMENTO_DATI_INTERVENTO);
		//CORREZIONE SONARQUBE
		Integer moduloId = null;
		if(moduloTipo != null) {
			if(moduloTipo.getModuloId() != null) {
				moduloId = moduloTipo.getModuloId();
			}
		}
		
		List<RInterventoModulo> moduloIntList = rInterventoModuloRepository.findAllValidByIntIdAndModuloIdAndEnteId(interventoId, moduloId, enteId);
		if(moduloIntList.size() == 0) {
			return null;
		}
			
		return moduloIntList.get(0);
	}
	
	protected Modulo getModuloByModuloCod(ModuloTipoEnum moduloTipoEnum) throws PgmeasException {
		List<Modulo> moduloTipoList = moduloRepository.findAllByModuloCod(moduloTipoEnum.getCode());
		if (moduloTipoList != null && moduloTipoList.size() > 0) {
			return moduloTipoList.get(0);
		} else {
			ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "codice Modulo  non trovato",
					new ArrayList<ErroreDettaglio>(), "codice Modulo  non trovato :" + moduloTipoEnum.getCode());
		}
		return null;
	}

	protected ModuloStato getModuloStatoByModuloStatoCod(ModuloStatoEnum moduloStatoEnum) throws PgmeasException {
		List<ModuloStato> moduloStatoList = moduloStatoRepository.findAllByModuloStatoCod(moduloStatoEnum.getCode());
		if (moduloStatoList != null && moduloStatoList.size() > 0) {
			return moduloStatoList.get(0);
		} else {
			ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "codice ModuloStato non trovato",
					new ArrayList<ErroreDettaglio>(), "codice ModuloStato non trovato :" + moduloStatoEnum.getCode());
		}
		return null;
	}
	
	private ModuloStato getModuloStatoByModuloStatoId(Integer id) throws PgmeasException {
		ModuloStato moduloStato = moduloStatoRepository.findByModuloStatoId(id);
		if (moduloStato != null) {
			return moduloStato;
		} else {
			ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "ModuloStato non trovato",
					new ArrayList<ErroreDettaglio>(), "ModuloStato non trovato :" + id);
		}
		return null;
	}

	public RInterventoModulo getRInterventoModuloByIdAndEnteId(Integer rIntModuloId, Integer enteId) throws PgmeasException {
		Optional<RInterventoModulo> rInterventoModulo = rInterventoModuloRepository
				.findValidByRIntModuloIdAndEnteId(rIntModuloId, enteId);
		if (rInterventoModulo.isPresent()) {
			return rInterventoModulo.get();
		} else {
			ValidationUtils.generatePgmeasException(HttpStatus.NOT_FOUND, "RInterventoModulo non trovato",
					new ArrayList<ErroreDettaglio>(),
					"RInterventoModulo non trovato per id:" + rInterventoModulo);
		}
		return null;
	}

	public ModuloTipoEnum getModuloTipoEnumByRInterventoModulo(RInterventoModulo rInterventoModulo) throws PgmeasException {
		Modulo modulo = moduloRepository.findByModuloId(rInterventoModulo.getModuloId());
		return ModuloTipoEnum.fromCode(modulo.getModuloCod());
	}
	
	public static void validateRequiredPutModuloByRegione(ModuloAPutByRegioneModel body) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();

		objectNullValidator("moduloRegione", body, listaErroriRilevati);
//		validateAllegatiPutRegione(body, listaErroriRilevati);
		
		if (listaErroriRilevati.size() > 0) {
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD",
					listaErroriRilevati, "Payload non compliant");
		}
	}

//	private static void validateAllegatiPutRegione(ModuloAPutByRegioneModel body, List<ErroreDettaglio> listaErroriRilevati) {
//		List<ErroreDettaglio> listaErroriRilevatiAllegati = new ArrayList<ErroreDettaglio>();
//		
//		if (body.getDecretoMinisterialeToDelete() != null && body.getDecretoMinisterialeToDelete().getIdAllegato() != null) {
//			checkAllegatoObbligatorio(AllegatoTipoEnum.DECRETO_MINISTERIALE, body.getDecretoMinisterialeNew(), listaErroriRilevatiAllegati);
//			if (listaErroriRilevatiAllegati.size() > 0) {
//				checkAllegatoObbligatorio(AllegatoTipoEnum.NULLA_OSTA, body.getNullaOstaNew(), listaErroriRilevatiAllegati);
//				if (listaErroriRilevatiAllegati.size() > 0) {
//					listaErroriRilevati.addAll(listaErroriRilevatiAllegati);
//				}
//			}
//		} else if (body.getNullaOstaToDelete() != null && body.getNullaOstaToDelete().getIdAllegato() != null) {
//			checkAllegatoObbligatorio(AllegatoTipoEnum.NULLA_OSTA, body.getNullaOstaNew(), listaErroriRilevatiAllegati);
//			if (listaErroriRilevatiAllegati.size() > 0) {
//				checkAllegatoObbligatorio(AllegatoTipoEnum.DECRETO_MINISTERIALE, body.getDecretoMinisterialeNew(), listaErroriRilevatiAllegati);
//				if (listaErroriRilevatiAllegati.size() > 0) {
//					listaErroriRilevati.addAll(listaErroriRilevatiAllegati);
//				}
//			}
//		} else { // caso post
//			checkAllegatoObbligatorio(AllegatoTipoEnum.DECRETO_MINISTERIALE, body.getDecretoMinisterialeNew(), listaErroriRilevatiAllegati);
//			if (listaErroriRilevatiAllegati.size() > 0) {
//				checkAllegatoObbligatorio(AllegatoTipoEnum.NULLA_OSTA, body.getNullaOstaNew(), listaErroriRilevatiAllegati);
// 				if (listaErroriRilevatiAllegati.size() > 0) {
//					listaErroriRilevati.addAll(listaErroriRilevatiAllegati);
//				}
//			}
//		}
//	}
	
	/**
	 * intervento.getIntPrioritaAnno() ANNO PRIORITA INTERVENTO > ANNO CORRENTE ANNO
	 * CORRENTE>= PRIMO ANNO della previsione di spesa
	 * controllo cronoprogramma 
	 * 
	 * @param request
	 * @param intervento
	 * @throws PgmeasException
	 */
	public void validateConsistencyPayload(RichiestaAmmissioneFinanziamentoDto request, Intervento intervento)
			throws PgmeasException {
		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
		Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if (intervento.getIntPrioritaAnno() > currentYear) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave("intPrioritaAnno");
			err.setValore(intervento.getIntPrioritaAnno().toString());
			errorList.add(err);
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST", errorList,
					"Priorita' anno intervento > anno corrente per l'intervento " + intervento.getIntCod());
		}
		List<InterventoPrevisioneSpesa> listaPrevioneSpesa = previsioniDiSpesaUtilityService
				.getInterventoPrevisioneSpesaListByInterventoId(intervento.getIntId(), intervento.getEnteId());
		
		if (!listaPrevioneSpesa.isEmpty()) {
			Collections.sort(listaPrevioneSpesa, Comparator.comparing(InterventoPrevisioneSpesa::getIntPrevSpesaAnno));
			InterventoPrevisioneSpesa intPrevSpesaMinore = listaPrevioneSpesa.get(0);
			if (currentYear < intPrevSpesaMinore.getIntPrevSpesaAnno()) {
				ErroreDettaglio err = new ErroreDettaglio();
				err.setChiave("previsione di spesa Anno");
				err.setValore(intPrevSpesaMinore.getIntPrevSpesaAnno().toString());
				errorList.add(err);
				ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST", errorList,
						"Primo anno previsione di spesa > anno corrente per l'intervento" + intervento.getIntCod());

			}
		}

		cronoProgrammaUtilityService.checkCronoProgramma(request, intervento);
	}
	
	/**
	 * CONTROLLO CHE NON ESISTA UN MODULO A oppure AA PER LO STESSO INTERVENTO
	 * 
	 * @param intervento
	 * @throws PgmeasException
	 */
	public void validateModuleAOrAAUnique(Intervento intervento, Integer enteId) throws PgmeasException {
		List<RInterventoModulo> moduloAOld = getModuliByTipoAndInterventoIdAndEnteId(intervento.getIntId(), ModuloTipoEnum.MODULO_A, enteId);
		List<RInterventoModulo> moduloAAOld = getModuliByTipoAndInterventoIdAndEnteId(intervento.getIntId(), ModuloTipoEnum.MODULO_A_A, enteId);
		if (!(moduloAOld.isEmpty()) || !(moduloAAOld.isEmpty())) {
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST",
					new ArrayList<ErroreDettaglio>(),
					"Modulo A o Modulo AA già presenti per l'intervento " + intervento.getIntCod());
		}
	}
	
	public void validateRequiredPostModuloAOrAA(RichiestaAmmissioneFinanziamentoDto body) throws PgmeasException {
		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
		checkRequiredRichiestaAmmissioneFinanziamentoDtoBase(body, errorList);
//		checkAllegatoObbligatorio(AllegatoTipoEnum.PROVVEDIMENTO_AZIENDALE_DI_APPROVAZIONE,
//				body.getAllegatoProvAzApp(), errorList);
//		// NO PROTOCOLLO NUMERO NO PROTOCOLLO DATA
//		checkAllegatoObbligatorio(AllegatoTipoEnum.RELAZIONE_TECNICA, body.getAllegatoRelTec(),
//				errorList);
		if (errorList.size() > 0) {
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD", errorList,
					"");
		}
	}
	
	public void validateRequiredPutModuloAOrAA(RichiestaAmmissioneFinanziamentoPutDto body, Integer rIntModuloId) throws PgmeasException {
		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
		checkRequiredRichiestaAmmissioneFinanziamentoDtoBase(body, errorList);
		integerNullOrNotValidValidator("rIntModuloId", rIntModuloId, errorList);
//		checkRichiestaAmmissioneFinanziamentoPutAllegati(body);
		
		if (errorList.size() > 0) {
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD", errorList,
					"");
		}
	}
	
//	private void checkRichiestaAmmissioneFinanziamentoPutAllegati(RichiestaAmmissioneFinanziamentoPutDto body)
//			throws PgmeasException {
//		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
//
//		if (body.getAllegatoProvAzApp() != null && body.getAllegatoProvAzAppToDelete() == null) {
//			ErroreDettaglio err = new ErroreDettaglio();
//			err.setChiave("AllegatoProvAzAppToDelete");
//			err.setValore("Null or Empy");
//			errorList.add(err);
//		} else if (body.getAllegatoProvAzAppToDelete() != null
//				&& body.getAllegatoProvAzAppToDelete().getIdAllegato() != null) {
//			checkAllegatoObbligatorio(AllegatoTipoEnum.PROVVEDIMENTO_AZIENDALE_DI_APPROVAZIONE,
//					body.getAllegatoProvAzApp(), errorList);
//		}
//
//		if (body.getAllegatoRelTec() != null && body.getAllegatoRelTecToDelete() == null) {
//			ErroreDettaglio err = new ErroreDettaglio();
//			err.setChiave("AllegatoRelTec");
//			err.setValore("Null or Empy");
//			errorList.add(err);
//		} else if (body.getAllegatoRelTecToDelete() != null
//				&& body.getAllegatoRelTecToDelete().getIdAllegato() != null) {
//			checkAllegatoObbligatorio(AllegatoTipoEnum.RELAZIONE_TECNICA, body.getAllegatoRelTec(),
//					errorList);
//		}
//
//		if (errorList.size() > 0) {
//			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD", errorList,
//					"Payload non compliant");
//		}
//
//	}
	
	public void validateRequiredInviaModuloAARegione(Intervento intervento) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
				
		List<Allegato> allegati = allegatoUtility.getAllegatiByIntervento(intervento.getIntId(), intervento.getEnteId());
		validateAllegatiPutAzienda(allegati, listaErroriRilevati);
		
		if (listaErroriRilevati.size() > 0) {
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "MSG-096: Attenzione! Per poter inviare il Modulo A a Regione deve essere valorizzato il 'provvedimento aziendale di approvazione' e la 'relazione tecnica'",
					listaErroriRilevati, "MSG-096: Attenzione! Per poter inviare il Modulo A a Regione deve essere valorizzato il 'provvedimento aziendale di approvazione' e la 'relazione tecnica'");
		}
	}
	
	private void validateAllegatiPutAzienda(List<Allegato> allegati, List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {
		AllegatoTipo allegatoTipoProvvedimentoAziendale = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(AllegatoTipoEnum.PROVVEDIMENTO_AZIENDALE_DI_APPROVAZIONE);
		//CORREZIONE SONARQUBE
		Optional<Allegato> allegatoOptProvAziendale = allegati.stream()
				.filter(a -> a.getAllegatoTipoId().equals(allegatoTipoProvvedimentoAziendale.getAllegatoTipoId())).findFirst();
		
		checkAllegatoObbligatorio(AllegatoTipoEnum.PROVVEDIMENTO_AZIENDALE_DI_APPROVAZIONE, allegatoOptProvAziendale, listaErroriRilevati);
		
		AllegatoTipo allegatoTipoRelazioneTecnica = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(AllegatoTipoEnum.RELAZIONE_TECNICA);
		//CORREZIONE SONARQUBE
		Optional<Allegato> allegatoOptRelazioneTecnica = allegati.stream()
				.filter(a -> a.getAllegatoTipoId().equals(allegatoTipoRelazioneTecnica.getAllegatoTipoId())).findFirst();
		
		checkAllegatoLightObbligatorio(AllegatoTipoEnum.RELAZIONE_TECNICA, allegatoOptRelazioneTecnica, listaErroriRilevati);
	}	
	
	public void validateRequiredApprovaModuloA(Intervento intervento) throws PgmeasException {
		List<ErroreDettaglio> listaErroriRilevati = new ArrayList<ErroreDettaglio>();
				
		List<Allegato> allegati = allegatoUtility.getAllegatiByIntervento(intervento.getIntId(), intervento.getEnteId());
		validateAllegatiPutRegione(allegati, listaErroriRilevati);
		
		if (listaErroriRilevati.size() == 0 || listaErroriRilevati.size() == 2) { //se mancano entrambi o ci sono entrambi
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "MSG-097: Attenzione! Per poter inviare approvare il modulo A deve essere valorizzato 'decreto ministeriale' oppure il 'nulla osta'",
					listaErroriRilevati, "MSG-097: Attenzione! Per poter inviare approvare il modulo A deve essere valorizzato 'decreto ministeriale' oppure il 'nulla osta'");
		}
	}
	
	private void validateAllegatiPutRegione(List<Allegato> allegati, List<ErroreDettaglio> listaErroriRilevati) throws PgmeasException {
		AllegatoTipo allegatoDecretoMinisteriale = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(AllegatoTipoEnum.DECRETO_MINISTERIALE);
		//CORREZIONE SONARQUBE
		Optional<Allegato> allegatoOptDecretoMinisteriale = allegati.stream()
				.filter(a -> a.getAllegatoTipoId().equals(allegatoDecretoMinisteriale.getAllegatoTipoId())).findFirst();
		
		checkAllegatoObbligatorio(AllegatoTipoEnum.DECRETO_MINISTERIALE, allegatoOptDecretoMinisteriale, listaErroriRilevati);
		
		AllegatoTipo allegatoTipoNullaOsta = allegatoTipoUtilityService.getAllegatoTipoByAllegatoTipoCod(AllegatoTipoEnum.NULLA_OSTA);
		//CORREZIONE SONARQUBE
		Optional<Allegato> allegatoOptNullaOsta = allegati.stream()
				.filter(a -> a.getAllegatoTipoId().equals(allegatoTipoNullaOsta.getAllegatoTipoId())).findFirst();
		
		checkAllegatoLightObbligatorio(AllegatoTipoEnum.NULLA_OSTA, allegatoOptNullaOsta, listaErroriRilevati);
	}	

	private void checkRequiredRichiestaAmmissioneFinanziamentoDtoBase(RichiestaAmmissioneFinanziamentoDto body,
			List<ErroreDettaglio> listaErroriRilevati) {
		objectNullValidator("richiestaAmmissioneFinanziamentoDto", body, listaErroriRilevati);
		integerNullOrNotValidValidator("intId", body.getIntId(), listaErroriRilevati);
		stringNullOrEmptyValidator("moduloTipo",body.getModuloTipo(),listaErroriRilevati);
		
		if(!StringUtils.isBlank(body.getIntDirettoreGeneraleCognome()) || !StringUtils.isBlank(body.getIntDirettoreGeneraleNome())
        		|| !StringUtils.isBlank(body.getIntDirettoreGeneraleCf())) {
            stringNullOrEmptyValidator("cognome del direttore generale", body.getIntDirettoreGeneraleCognome(), listaErroriRilevati);
            stringNullOrEmptyValidator("codice fiscale del direttore generale", body.getIntDirettoreGeneraleCf(), listaErroriRilevati);
            stringNullOrEmptyValidator("nome del direttore generale", body.getIntDirettoreGeneraleNome(), listaErroriRilevati);
        } 
        if(!StringUtils.isBlank(body.getIntCommissarioCognome()) || !StringUtils.isBlank(body.getIntCommissarioNome())
        		|| !StringUtils.isBlank(body.getIntCommissarioCf())) {
            stringNullOrEmptyValidator("cognome del commissario", body.getIntCommissarioCognome(), listaErroriRilevati);
            stringNullOrEmptyValidator("nome del commissario", body.getIntCommissarioNome(), listaErroriRilevati);
            stringNullOrEmptyValidator("codice fiscale del commissario", body.getIntCommissarioCf(), listaErroriRilevati);
        } 
        
	    stringNullOrEmptyValidator("cognome del RUP", body.getIntRupCognome(), listaErroriRilevati);
	    stringNullOrEmptyValidator("nome del RUP", body.getIntRupNome(), listaErroriRilevati);
	    stringNullOrEmptyValidator("codice fiscale del RUP", body.getIntRupCf(), listaErroriRilevati);
	    stringNullOrEmptyValidator("cognome del referente pratica", body.getIntReferentePraticaCognome(), listaErroriRilevati);
	    stringNullOrEmptyValidator("nome del referente pratica", body.getIntReferentePraticaNome(), listaErroriRilevati);
	    stringNullOrEmptyValidator("codice fiscale del referente pratica", body.getIntReferentePraticaCf(), listaErroriRilevati);

		mapNullOrEmptyValidator("interventoStrutturaMap", body.getInterventoStrutturaMap(), listaErroriRilevati);
		if (body.getInterventoStrutturaMap() != null && !(body.getInterventoStrutturaMap().entrySet().isEmpty())) {
			for (var entry : body.getInterventoStrutturaMap().entrySet()) {
				checkRafInterventoStrutturaDto(entry.getValue(), listaErroriRilevati, entry.getKey());
			}
		}
	}
	
	private void checkRafInterventoStrutturaDto(RafInterventoStrutturaDto intStr,
			List<ErroreDettaglio> listaErroriRilevati, Integer key) {
		integerNullOrNotValidValidator("giorni di progettazione", intStr.getIntStrProgettazioneGg(),
				listaErroriRilevati);
		integerNullOrNotValidValidator("giorni di affidamento lavori", intStr.getIntStrAffidamentoLavoriGg(),
				listaErroriRilevati);
		integerNullOrNotValidValidator("giorni di esecuzione lavori", intStr.getIntStrEsecuzioneLavoriGg(),
				listaErroriRilevati);
		integerNullOrNotValidValidator("giorni di collaudo", intStr.getIntStrCollaudoGg(),
				listaErroriRilevati);
		objectNullValidator("appalto integrato", intStr.getIntStrAppaltoIntegrato(),
				listaErroriRilevati);
		mapNullOrEmptyValidator("quadro economico", intStr.getQuadroEconMap(), listaErroriRilevati);

		mapNullOrEmptyValidator("dichiarazione di appaltabilità", intStr.getDicAppMap(), listaErroriRilevati);
	}

	public void checkQuadroEconomicoPerStruttura (RichiestaAmmissioneFinanziamentoDto body, List<InterventoStruttura> interventiStrutturaList) throws PgmeasException {
		for (var entry : body.getInterventoStrutturaMap().entrySet()) {
			InterventoStruttura interventoStruttura = interventiStrutturaList.stream().filter(is -> is.getIntStrId().equals(entry.getKey())).findFirst().get();
			checkQuadroEconomico(entry.getValue().getQuadroEconMap(), entry.getKey(), interventoStruttura);
		}
	}
	
	private void checkQuadroEconomico(Map<Integer, BigDecimal> quadroEconomicoMap,Integer intStrid, InterventoStruttura interventoStruttura) throws PgmeasException {
		Entry<Integer, BigDecimal> result=quadroEconomicoMap.entrySet().stream().filter(a -> (a.getValue()!=null && (a.getValue().compareTo(BigDecimal.ZERO))>0)).findAny().orElse(null);
		if(result==null) {
			Struttura struttura = strutturaUtilityService.getSrutturaById(interventoStruttura.getStrId());
			List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave("Inserire almeno un importo del quadro economico");
			err.setValore(ErroreEnum.MSG_100 + ": Inserire almeno un importo del quadro economico - Nessun importo valorizzato per la struttura: " + struttura.getStrDenominazione());
			errorList.add(err);
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST ON PAYLOAD", errorList,
					"");
		}
	}
	
	// VERIFICA A E AA INSERITO
	public void validateConsistecyModuloAOrAA(RichiestaAmmissioneFinanziamentoPutDto request, Intervento intervento, Integer rIntModuloId, Integer enteId) throws PgmeasException {
		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
		boolean moduloAIsPresent = moduloInseritoInterventoIdAndModuloTipoEnumAndRInterventoIdisPresentAndEnteId(intervento.getIntId(), ModuloTipoEnum.MODULO_A, rIntModuloId, enteId);
		boolean moduloAAIsPresent = moduloInseritoInterventoIdAndModuloTipoEnumAndRInterventoIdisPresentAndEnteId(intervento.getIntId(), ModuloTipoEnum.MODULO_A_A, rIntModuloId, enteId);
		if(!moduloAIsPresent && !moduloAAIsPresent) {
			ErroreDettaglio err = new ErroreDettaglio();
			err.setChiave("Modulo A");
			err.setValore(rIntModuloId.toString());
			errorList.add(err);
			ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST", errorList,
					"Modulo Richiesta di finanziamento di tipo A o AA non trovato");
		}
	}

	public void aggiornaInterventoStrutturaByRafInterventoStrutturaDto(Timestamp now, UserInfoRecord userInfo,
			InterventoStruttura to, RafInterventoStrutturaDto from) {
		to.setDataModifica(now);
		to.setUtenteModifica(userInfo.codiceFiscale());
		to.setAppaltoIntegrato(from.getIntStrAppaltoIntegrato());
		to.setAffidamentoLavoriGg(from.getIntStrAffidamentoLavoriGg());
		to.setProgettazioneGg(from.getIntStrProgettazioneGg());
		to.setEsecuzioneLavoriGg(from.getIntStrEsecuzioneLavoriGg());
		to.setCollaudoGg(from.getIntStrCollaudoGg());
		interventoStrutturaRepository.save(to);
	}

	public void aggiornaInterventoByRichiestaAmmissioneFinanziamentoDto(RichiestaAmmissioneFinanziamentoDto request,
			Intervento intervento, Timestamp now, UserInfoRecord userInfo) {
		intervento.setDataModifica(now);
		intervento.setUtenteModifica(userInfo.codiceFiscale());
		interventoRepository.save(intervento);
	}

	public void generaErrorePerInterventoStrutturaNonTrovato(InterventoStruttura to) throws PgmeasException {
		List<ErroreDettaglio> errorList = new ArrayList<ErroreDettaglio>();
		ErroreDettaglio err = new ErroreDettaglio();
		err.setChiave("interventoStruttura");
		err.setValore(to.getIntStrId().toString());
		errorList.add(err);
		ValidationUtils.generatePgmeasException(HttpStatus.BAD_REQUEST, "BAD_REQUEST", errorList,
				"InterventoStruttura non trovato");
	}
	
	public void makeInterventoFromRichiestaAmmissioneFinanziamento(Intervento intervento, Timestamp now,
			UserInfoRecord userInfo, RichiestaAmmissioneFinanziamentoDto body) {
		intervento.setDataModifica(now);
		intervento.setUtenteModifica(userInfo.codiceFiscale());
		// SEZIONE RESPONSABILI INTERVENTO
		intervento.setIntDirettoreGeneraleCognome(body.getIntDirettoreGeneraleCognome());
		intervento.setIntDirettoreGeneraleNome(body.getIntDirettoreGeneraleNome());
		intervento.setIntDirettoreGeneraleCf(body.getIntDirettoreGeneraleCf());
		intervento.setIntCommissarioCognome(body.getIntCommissarioCognome());
		intervento.setIntCommissarioNome(body.getIntCommissarioNome());
		intervento.setIntCommissarioCf(body.getIntCommissarioCf());
		intervento.setIntRupCognome(body.getIntRupCognome());
		intervento.setIntRupNome(body.getIntRupNome());
		intervento.setIntRupCf(body.getIntRupCf());
		intervento.setIntReferentePraticaCognome(body.getIntReferentePraticaCognome());
		intervento.setIntReferentePraticaNome(body.getIntReferentePraticaNome());
		intervento.setIntReferentePraticaEmail(body.getIntReferentePraticaEmail());
		intervento.setIntReferentePraticaTelefono(body.getIntReferentePraticaTelefono());
		intervento.setIntReferentePraticaCf(body.getIntReferentePraticaCf());
		intervento.setNote(body.getNote());
	}
	
	public ModuloFile getModuloFileFromFileIdAndEnteId(Integer fileId, Integer enteId) {
		ModuloFile moduloFile = moduloFileRepository.findValidByFileIdAndEnteId(fileId, enteId).orElseThrow();
		return moduloFile;
	}
}
