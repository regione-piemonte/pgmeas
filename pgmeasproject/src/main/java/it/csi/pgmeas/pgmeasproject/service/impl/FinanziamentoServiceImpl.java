/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import static it.csi.pgmeas.commons.util.APISecurityFilterUtils.getUser;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Year;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.base.BaseApiController;
import it.csi.pgmeas.commons.dto.AllegatoDto;
import it.csi.pgmeas.commons.dto.FinanziamentoResultDto;
import it.csi.pgmeas.commons.dto.InterventoFinanziamentoPrevSpesaDto;
import it.csi.pgmeas.commons.dto.InterventoStrutturaDto;
import it.csi.pgmeas.commons.dto.LiquidazioneDto;
import it.csi.pgmeas.commons.dto.MonitoraggioDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Allegato;
import it.csi.pgmeas.commons.model.IntStrDicAppaltabilitaTree;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.model.RInterventoFinanziamentoPrevSpesa;
import it.csi.pgmeas.commons.model.RInterventoStatoProgettuale;
import it.csi.pgmeas.commons.model.RLiquidazione;
import it.csi.pgmeas.commons.repository.AllegatoRepository;
import it.csi.pgmeas.commons.repository.FinanziamentoRepository;
import it.csi.pgmeas.commons.repository.IntStrDicAppaltabilitaTreeRepository;
import it.csi.pgmeas.commons.repository.IntStrDicAppaltabilitaTsRepository;
import it.csi.pgmeas.commons.repository.InterventoGaraAppaltoRepository;
import it.csi.pgmeas.commons.repository.InterventoRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaQuadroEconomicoTreeRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaQuadroEconomicoTsRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaRepository;
import it.csi.pgmeas.commons.repository.ProvvedimentoRepository;
import it.csi.pgmeas.commons.repository.RInterventoFinanziamentoPrevSpesaRepository;
import it.csi.pgmeas.commons.repository.RInterventoStatoProgettualeRepository;
import it.csi.pgmeas.commons.repository.RLiquidazioneRepository;
import it.csi.pgmeas.commons.service.EnteUtilityService;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.pgmeasproject.service.FinanziamentoService;
import it.csi.pgmeas.pgmeasproject.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
@Service
public class FinanziamentoServiceImpl extends BaseApiController implements FinanziamentoService{
	
	private static final Logger LOG = LoggerFactory.getLogger(FinanziamentoServiceImpl.class);
	
	@Autowired
	FinanziamentoRepository finanziamentoRepository;
	
	@Autowired
	AllegatoRepository allegatoRepository;
	
	@Autowired
	InterventoGaraAppaltoRepository interventoGaraAppaltoRepository;
	
	@Autowired
	InterventoStrutturaRepository interventoStrutturaRepository;
	
	@Autowired
	InterventoRepository interventoRepository;
	
	@Autowired
	IntStrDicAppaltabilitaTreeRepository intStrDicAppaltabilitaRepository;
	
	@Autowired
	IntStrDicAppaltabilitaTsRepository intStrDicAppaltabilitaTsRepository;
	
	@Autowired
	InterventoStrutturaQuadroEconomicoTreeRepository intStrutturaQeTreeRepository;
	
	@Autowired
	InterventoStrutturaQuadroEconomicoTsRepository intStrutturaQeTsRepository;
	
	@Autowired
	RInterventoStatoProgettualeRepository rIntStatoProgRepository;
	
	@Autowired
	ProvvedimentoRepository provvedimentoRepository;
	
	@Autowired
	RInterventoFinanziamentoPrevSpesaRepository rInterventoFinanziamentoPrevSpesaRepository;
	
	@Autowired
	RLiquidazioneRepository rLiquidazioneRepository;	

	@Autowired
	private EnteUtilityService enteUtilityService;
	

	@Override
	public List<FinanziamentoResultDto> getAllData() {
		List<FinanziamentoResultDto> data = finanziamentoRepository.findAllSenzaCancellati().stream()
				.map(f -> MappingUtils.copy(f, new FinanziamentoResultDto())).toList();
		return data;
	}

	@Override
	public List<FinanziamentoResultDto> getFinanziamentiByIntId(Integer intId) {
		List<FinanziamentoResultDto> data = finanziamentoRepository.findAllSenzaCancellatiByIntId(intId).stream()
				.map(from -> MappingUtils.copy(from, new FinanziamentoResultDto())).toList();
		return data;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public AllegatoDto inserimentoDatiMonitoraggio(MonitoraggioDto richiestaMonitoraggioDto, HttpServletRequest httpRequest) throws PgmeasException {
		var ente = enteUtilityService.getEnteByEnteId(richiestaMonitoraggioDto.getEnteId());

		UserInfoRecord user = getUser(httpRequest);

		String cf = user.codiceFiscale();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// (1)
		// Salvataggio tabella pgmeas_t_allegato (ALLEGATO)
		var fileNameSystem = "%s.pdf".formatted(UUID.randomUUID());
		var filePath = "%s/%s/%s".formatted(ente.getEnteCod(), Year.now(),richiestaMonitoraggioDto.getIntId());

		
		var allegato = new Allegato();
		allegato.setEnteId(richiestaMonitoraggioDto.getEnteId());
		allegato.setIntId(richiestaMonitoraggioDto.getIntId());

		allegato.setFileNameSystem(fileNameSystem);
		allegato.setFileNameUser(fileNameSystem); // Workaround in attesa di campo nullabile
		allegato.setFilePath(filePath);
		
		allegato.setAllegatoOggetto(richiestaMonitoraggioDto.getAllegatoOggetto());
		allegato.setAllegatoProtocolloData(timestamp);
		allegato.setAllegatoTipoId(Constants.ALLEGATO_MONITORAGGIO_ID);
		allegato.setUtenteCreazione(cf);
		allegato.setUtenteModifica(cf);
		allegato.setDataCreazione(timestamp);
		allegato.setDataModifica(timestamp);
		allegatoRepository.save(allegato);

		// (2)
		// Salvataggio tabella pgmeas_t_intervento_struttura (INTERVENTO_STRUTTURA)
		for (InterventoStrutturaDto interventoStrutturaDto : richiestaMonitoraggioDto.getListaIntStruttura()) {
			InterventoStruttura interventoStruttura = interventoStrutturaRepository
					.findByIntStrId(interventoStrutturaDto.getIntStrId());
			if (interventoStruttura != null) {
				interventoStruttura.setIntstrAperturaCantiereDataEffettiva(
						interventoStrutturaDto.getIntstrAperturaCantiereDataEffettiva() != null
								? new Date(interventoStrutturaDto.getIntstrAperturaCantiereDataEffettiva().getTime())
								: null);
				interventoStruttura.setIntstrAperturaCantiereDataPrevista(
						interventoStrutturaDto.getIntstrAperturaCantiereDataPrevista() != null
								? new Date(interventoStrutturaDto.getIntstrAperturaCantiereDataPrevista().getTime())
								: null);
				interventoStruttura
						.setIntstrCollaudoDataEffettiva(interventoStrutturaDto.getIntstrCollaudoDataEffettiva() != null
								? new Date(interventoStrutturaDto.getIntstrCollaudoDataEffettiva().getTime())
								: null);
				interventoStruttura
						.setIntstrCollaudoDataPrevista(interventoStrutturaDto.getIntstrCollaudoDataPrevista() != null
								? new Date(interventoStrutturaDto.getIntstrCollaudoDataPrevista().getTime())
								: null);
				interventoStruttura.setDataModifica(timestamp);
				interventoStruttura.setUtenteModifica(cf);
				interventoStrutturaRepository.save(interventoStruttura);
			} else {
				interventoStruttura = new InterventoStruttura();
				interventoStruttura.setIntStrId(interventoStrutturaDto.getIntStrId());
				interventoStruttura.setEnteId(richiestaMonitoraggioDto.getEnteId());
				interventoStruttura.setIntId(richiestaMonitoraggioDto.getIntId());
				interventoStruttura.setIntstrAperturaCantiereDataEffettiva(
						interventoStrutturaDto.getIntstrAperturaCantiereDataEffettiva() != null
								? new Date(interventoStrutturaDto.getIntstrAperturaCantiereDataEffettiva().getTime())
								: null);
				interventoStruttura.setIntstrAperturaCantiereDataPrevista(
						interventoStrutturaDto.getIntstrAperturaCantiereDataPrevista() != null
								? new Date(interventoStrutturaDto.getIntstrAperturaCantiereDataPrevista().getTime())
								: null);
				interventoStruttura
						.setIntstrCollaudoDataEffettiva(interventoStrutturaDto.getIntstrCollaudoDataEffettiva() != null
								? new Date(interventoStrutturaDto.getIntstrCollaudoDataEffettiva().getTime())
								: null);
				interventoStruttura
						.setIntstrCollaudoDataPrevista(interventoStrutturaDto.getIntstrCollaudoDataPrevista() != null
								? new Date(interventoStrutturaDto.getIntstrCollaudoDataPrevista().getTime())
								: null);
//				interventoStruttura.setIntstrPriorita(1); // Obbligatorio ma da verificare (1,2,3...)
//				interventoStruttura.setIntstrSottopriorita("a"); // Obbligatorio ma da verificare (a,b,c...)
				interventoStruttura.setUtenteCreazione(cf);
				interventoStruttura.setDataCreazione(timestamp);
				interventoStruttura.setDataModifica(timestamp);
				interventoStruttura.setUtenteModifica(cf);
				interventoStrutturaRepository.save(interventoStruttura);
			}
		}

		// (3)
		// Salvataggio tabella pgmeas_r_intervento_stato_progettuale (associativa RINTERVENTO STATO PROGETTUALE)
		// Eseguire la lettura sulla tabella pgmeas_r_intervento_stato_progettuale per
		// ente e Intervento
		// Aprire un loop sulla tabella dei record ricevuti e verificare che
		// l'identificativo dello stato sia già presente nell'array dello stato lavori
		// presente nel json che ci arriva
		// Se non esiste viene inserito un record nella tabella
		// pgmeas_r_intervento_stato_progettuale altrimenti si effettua un update
		//
		List<RInterventoStatoProgettuale> listRintStatoProgRepository = rIntStatoProgRepository
				.findByIntIdAndEnteId(richiestaMonitoraggioDto.getIntId(), richiestaMonitoraggioDto.getEnteId());

		// Se la tabella identificativi stato lavoro in arrivo è vuota allora cancello
		// tutti i record se presenti in tabella
		if (richiestaMonitoraggioDto.getListaIntStatoProgettualeId().isEmpty()) {
			listRintStatoProgRepository.stream().forEach(rec -> {
				rIntStatoProgRepository.delete(rec);
			});
		} else {
			for (Integer intStatoProgId : richiestaMonitoraggioDto.getListaIntStatoProgettualeId()) {
				RInterventoStatoProgettuale rInterventoStatoProgettuale = rIntStatoProgRepository
						.findByIntIdAndIntStatoProgIdAndEnteId(richiestaMonitoraggioDto.getIntId(), intStatoProgId,
								richiestaMonitoraggioDto.getEnteId());
				if (rInterventoStatoProgettuale != null) {
					rInterventoStatoProgettuale.setDataModifica(timestamp);
					rInterventoStatoProgettuale.setUtenteModifica(cf);
					// rIntStatoProgRepository.save(rInterventoStatoProgettuale);
				} else {
					rInterventoStatoProgettuale = new RInterventoStatoProgettuale();
					rInterventoStatoProgettuale.setIntStatoProgId(intStatoProgId);
					rInterventoStatoProgettuale.setEnteId(richiestaMonitoraggioDto.getEnteId());
					rInterventoStatoProgettuale.setIntId(richiestaMonitoraggioDto.getIntId());
					rInterventoStatoProgettuale.setUtenteCreazione(cf);
					rInterventoStatoProgettuale.setValiditaInizio(timestamp);
					rInterventoStatoProgettuale.setDataCreazione(timestamp);
					rInterventoStatoProgettuale.setDataModifica(timestamp);
					rInterventoStatoProgettuale.setUtenteModifica(cf);
					rIntStatoProgRepository.save(rInterventoStatoProgettuale);

				}
			}
			// Cancella dalla tabella tutti i record che non trovano corrispondenza nella
			// lista degli identificativi stato lavoro ricevuta nel json
			for (RInterventoStatoProgettuale rInterventoStatoProgettuale : listRintStatoProgRepository) {
				boolean found = false;
				for (Integer intStatoProgId : richiestaMonitoraggioDto.getListaIntStatoProgettualeId()) {
					if (rInterventoStatoProgettuale.getIntStatoProgId().equals(intStatoProgId))
						found = true;
				}
				if (!found)
					rIntStatoProgRepository.delete(rInterventoStatoProgettuale);
			}
		}

		// (4)
		// Salvataggio tabella pgmeas_r_intervento_finanziamento_prev_spesa
		for (InterventoFinanziamentoPrevSpesaDto rInterventoFinanziamentoPrevSpesaDto : richiestaMonitoraggioDto
				.getListaIntFinanziamentoPrevSpesa()) {
			List<RInterventoFinanziamentoPrevSpesa> listRInterventoFinanziamentoPrevSpesa = rInterventoFinanziamentoPrevSpesaRepository
					.findByIntIdAndFinIdAndEnteIdAndIntFinPrevSpesaAnnoAndValiditaFineIsNull(
							richiestaMonitoraggioDto.getIntId(), rInterventoFinanziamentoPrevSpesaDto.getFinId(),
							richiestaMonitoraggioDto.getEnteId(),
							rInterventoFinanziamentoPrevSpesaDto.getIntFinPrevSpesaAnno());
			if (listRInterventoFinanziamentoPrevSpesa.isEmpty()) {
				RInterventoFinanziamentoPrevSpesa rInterventoFinanziamentoPrevSpesa = preparaRInterventoFinanziamentoPrevSpesa(
						rInterventoFinanziamentoPrevSpesaDto, richiestaMonitoraggioDto, timestamp, cf);
				rInterventoFinanziamentoPrevSpesaRepository.save(rInterventoFinanziamentoPrevSpesa);
			} else {
				listRInterventoFinanziamentoPrevSpesa.get(0)
						.setIntFinPrevSpesaImporto(rInterventoFinanziamentoPrevSpesaDto.getIntFinPrevSpesaImporto());
				listRInterventoFinanziamentoPrevSpesa.get(0).setDataModifica(timestamp);
				listRInterventoFinanziamentoPrevSpesa.get(0).setUtenteModifica(cf);
				// listRInterventoFinanziamentoPrevSpesa.get(0).setValiditaFine(timestamp);
				rInterventoFinanziamentoPrevSpesaRepository.save(listRInterventoFinanziamentoPrevSpesa.get(0));
//					RInterventoFinanziamentoPrevSpesa rInterventoFinanziamentoPrevSpesa = preparaRInterventoFinanziamentoPrevSpesa(
//							rInterventoFinanziamentoPrevSpesaDto, 
//							richiestaMonitoraggioDto, 
//							timestamp, 
//							cf);
//					rInterventoFinanziamentoPrevSpesaRepository.save(rInterventoFinanziamentoPrevSpesa);
			}
		}

		// (5)
		// Salvataggio tabella pgmeas_r_liquidazione
		for (LiquidazioneDto rLiquidazioneDto : richiestaMonitoraggioDto
				.getListaFinanziamentoLiquidazioneRichiesta()) {
			RLiquidazione rLiquidazione = rLiquidazioneRepository
					.findByLiqRicIdAndEnteId(rLiquidazioneDto.getLiqRicId(), richiestaMonitoraggioDto.getEnteId());
			rLiquidazione.setLiqImportoIncassato(rLiquidazioneDto.getLiqImportoIncassato());
			rLiquidazione.setDataModifica(timestamp);
			rLiquidazione.setUtenteModifica(cf);
			rLiquidazioneRepository.save(rLiquidazione);
		}

		LOG.info("Salvo Dati Monitoraggio");

		AllegatoDto data = MappingUtils.copy(allegato, new AllegatoDto());
		return data;
	}

	private static RInterventoFinanziamentoPrevSpesa preparaRInterventoFinanziamentoPrevSpesa(
			InterventoFinanziamentoPrevSpesaDto rInterventoFinanziamentoPrevSpesaDto, 
			MonitoraggioDto richiestaMonitoraggioDto,
			Timestamp timestamp,
			String cf) {
		RInterventoFinanziamentoPrevSpesa rInterventoFinanziamentoPrevSpesa = new RInterventoFinanziamentoPrevSpesa();
		rInterventoFinanziamentoPrevSpesa.setFinId(rInterventoFinanziamentoPrevSpesaDto.getFinId());
		rInterventoFinanziamentoPrevSpesa.setEnteId(richiestaMonitoraggioDto.getEnteId());
		rInterventoFinanziamentoPrevSpesa.setIntId(richiestaMonitoraggioDto.getIntId());
		rInterventoFinanziamentoPrevSpesa.setIntFinPrevSpesaAnno(rInterventoFinanziamentoPrevSpesaDto.getIntFinPrevSpesaAnno());
		rInterventoFinanziamentoPrevSpesa.setIntFinPrevSpesaImporto(rInterventoFinanziamentoPrevSpesaDto.getIntFinPrevSpesaImporto());
		rInterventoFinanziamentoPrevSpesa.setValiditaInizio(timestamp);
		rInterventoFinanziamentoPrevSpesa.setUtenteCreazione(cf);
		rInterventoFinanziamentoPrevSpesa.setDataCreazione(timestamp);
		rInterventoFinanziamentoPrevSpesa.setDataModifica(timestamp);
		rInterventoFinanziamentoPrevSpesa.setUtenteModifica(cf);
		return rInterventoFinanziamentoPrevSpesa;
	}

	@Override
	public Timestamp getIntDataValidDicAppalto(Integer classifTreeId, Integer enteId, Integer intstrDaTsId) {
		IntStrDicAppaltabilitaTree dicAppaltabilita = intStrDicAppaltabilitaRepository.findByClassifTreeIdAndEnteIdAndIntstrDaTsId(classifTreeId, enteId, intstrDaTsId);
		Timestamp dataValidazione = null;
		if(dicAppaltabilita != null) {
			if(dicAppaltabilita.getIntstrDaTreeSelezionata()) {
				dataValidazione = dicAppaltabilita.getIntstrDaTreeValidazioneData();
			}
		}
		
		return dataValidazione;
	}
}

