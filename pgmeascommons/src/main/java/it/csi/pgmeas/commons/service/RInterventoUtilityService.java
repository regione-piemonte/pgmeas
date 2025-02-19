/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.RichiestaAmmissioneFinanziamentoDto;
import it.csi.pgmeas.commons.dto.v2.InterventoBase;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoTipo;
import it.csi.pgmeas.commons.model.RInterventoAppaltoTipo;
import it.csi.pgmeas.commons.model.RInterventoCategoria;
import it.csi.pgmeas.commons.model.RInterventoContrattoTipo;
import it.csi.pgmeas.commons.model.RInterventoFinalita;
import it.csi.pgmeas.commons.model.RInterventoObiettivo;
import it.csi.pgmeas.commons.model.RInterventoStatoProgettuale;
import it.csi.pgmeas.commons.model.RInterventoTipo;
import it.csi.pgmeas.commons.repository.RInterventoAppaltoTipoRepository;
import it.csi.pgmeas.commons.repository.RInterventoCategoriaRepository;
import it.csi.pgmeas.commons.repository.RInterventoContrattoTipoRepository;
import it.csi.pgmeas.commons.repository.RInterventoFinalitaRepository;
import it.csi.pgmeas.commons.repository.RInterventoObiettivoRepository;
import it.csi.pgmeas.commons.repository.RInterventoStatoProgettualeRepository;
import it.csi.pgmeas.commons.repository.RInterventoTipoRepository;
import it.csi.pgmeas.commons.util.enumeration.InterventoTipoEnum;
import it.csi.pgmeas.commons.util.intervento.InterventoUtils;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;

@Service
public class RInterventoUtilityService {

	@Autowired
	private RInterventoObiettivoRepository rinterventoObiettivoRepository;
	@Autowired
	private RInterventoFinalitaRepository rinterventoFinalitaRepository;
	@Autowired
	private RInterventoTipoRepository rinterventoTipoRepository;
	@Autowired
	private RInterventoCategoriaRepository rinterventoCategoriaRepository;
	@Autowired
	private RInterventoContrattoTipoRepository rinterventoContrattoTipoRepository;
	@Autowired
	private RInterventoAppaltoTipoRepository rinterventoAppaltoTipoRepository;
	@Autowired
	private RInterventoStatoProgettualeRepository rInterventoStatoProgettualeRepository;
	@Autowired
	private InterventoTipoUtilityService interventoTipoUtilityService;
	
	public void salvaRInterventoStatoProgettuale(InterventoBase body, UserInfoRecord userInfo,
			Timestamp now, Intervento interventoSaved) {
		salvaRInterventoStatoProgettuale(body.getListaIntStatoProgettualeId(), userInfo, now, interventoSaved);
	}
	
	public void salvaRInterventoStatoProgettuale(RichiestaAmmissioneFinanziamentoDto body, UserInfoRecord userInfo,
			Timestamp now, Intervento interventoSaved) {
		salvaRInterventoStatoProgettuale(body.getIntStatoProgIdList(), userInfo, now, interventoSaved);
	}
	
	private void salvaRInterventoStatoProgettuale(List<Integer> intStatoProgIdList, UserInfoRecord userInfo,
			Timestamp now, Intervento interventoSaved) {
		List<RInterventoStatoProgettuale> rInterventoStatoProgettualeListNew = InterventoUtils
				.buildStatoProgettualeInterventoFromModel(intStatoProgIdList, userInfo, now, interventoSaved);
		rInterventoStatoProgettualeRepository.saveAll(rInterventoStatoProgettualeListNew);
	}
	
	public void storicizzaRInterventoStatoProgettuale(Integer interventoId, UserInfoRecord userInfo, Timestamp now, Integer enteId) {
		List<RInterventoStatoProgettuale> rInterventoStatoProgettualeListOld = rInterventoStatoProgettualeRepository.findAllValidByIntIdAndEnteId(interventoId, enteId);
		for (RInterventoStatoProgettuale rInterventoStatoProgettuale : rInterventoStatoProgettualeListOld) {
			rInterventoStatoProgettuale.setValiditaFine(now);
			rInterventoStatoProgettuale.setDataModifica(now);
			rInterventoStatoProgettuale.setUtenteModifica(userInfo.codiceFiscale());
		}
		rInterventoStatoProgettualeRepository.saveAll(rInterventoStatoProgettualeListOld );
	}

	public void salvaRInteventoAppaltoTipo(InterventoBase body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved) {
		List<RInterventoAppaltoTipo> rInterventoAppaltoTipoListNew  = InterventoUtils.buildTipologiaAppaltoInterventoFromModel(body,
				userInfo, now, interventoSaved);
		rinterventoAppaltoTipoRepository.saveAll(rInterventoAppaltoTipoListNew );
	}

	public void storicizzaRInterventoAppaltoTipo(Integer interventoId, UserInfoRecord userInfo, Timestamp now, Integer enteId) {
		List<RInterventoAppaltoTipo> rInterventoAppaltoTipoListOld = rinterventoAppaltoTipoRepository.findAllValidByIntIdAndEnteId(interventoId, enteId);
		for (RInterventoAppaltoTipo rInterventoAppaltoTipo : rInterventoAppaltoTipoListOld) {
			rInterventoAppaltoTipo.setValiditaFine(now);
			rInterventoAppaltoTipo.setDataModifica(now);
			rInterventoAppaltoTipo.setUtenteModifica(userInfo.codiceFiscale());
		}
		rinterventoAppaltoTipoRepository.saveAll(rInterventoAppaltoTipoListOld );
	}

	public void salvaRInteventoContrattoTipo(InterventoBase body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved) {
		List<RInterventoContrattoTipo> rInterventoContrattoTipoListNew = InterventoUtils.buildContrattoInterventoListFromModel(body,
				userInfo, now, interventoSaved);
		rinterventoContrattoTipoRepository.saveAll(rInterventoContrattoTipoListNew );
	}

	public void storicizzaRInterventoContrattoTipo(Integer interventoId, UserInfoRecord userInfo, Timestamp now, Integer enteId) {
		List<RInterventoContrattoTipo> rInterventoContrattoTipoListOld = rinterventoContrattoTipoRepository.findAllValidByIntIdAndEnteId(interventoId, enteId);
		for (RInterventoContrattoTipo rInterventoContrattoTipo : rInterventoContrattoTipoListOld) {
			rInterventoContrattoTipo.setValiditaFine(now);
			rInterventoContrattoTipo.setDataModifica(now);
			rInterventoContrattoTipo.setUtenteModifica(userInfo.codiceFiscale());
		}
		rinterventoContrattoTipoRepository.saveAll(rInterventoContrattoTipoListOld );
	}

	public void salvaRInterventoCategoria(InterventoBase body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved) {
		List<RInterventoCategoria> rInterventoCategoriaListNew = InterventoUtils.buildCategoriaInterventoListFromModel(body, userInfo, now,
				interventoSaved);
		rinterventoCategoriaRepository.saveAll(rInterventoCategoriaListNew);
	}

	public void storicizzaRInterventoCategoria(Integer interventoId, UserInfoRecord userInfo, Timestamp now, Integer enteId) {
		List<RInterventoCategoria> rInterventoCategoriaListOld = rinterventoCategoriaRepository
				.findAllValidByIntIdAndEnteId(interventoId, enteId);
		for (RInterventoCategoria rInterventoCategoria : rInterventoCategoriaListOld) {
			rInterventoCategoria.setValiditaFine(now);
			rInterventoCategoria.setDataModifica(now);
			rInterventoCategoria.setUtenteModifica(userInfo.codiceFiscale());
		}
			rinterventoCategoriaRepository.saveAll(rInterventoCategoriaListOld);
	}

	public void salvaRInterventoTipo(InterventoBase body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved) throws PgmeasException {
		// TODO RECUPERA CODICE INTERVENO EDILIZIO PER DECODIC
		InterventoTipo interventoTipo = interventoTipoUtilityService
				.getInterventoTipoValidoByInterventoTipoCod(InterventoTipoEnum.ACQ_ATTR);
		List<RInterventoTipo> rInterventoTipoListNew = InterventoUtils.buildInterventoTipoListFromModel(body, userInfo,
				now, interventoSaved,interventoTipo);
		rinterventoTipoRepository.saveAll(rInterventoTipoListNew);
	}

	public void storicizzaRInterventoTIpo(Integer interventoId, UserInfoRecord userInfo, Timestamp now, Integer enteId) {
		List<RInterventoTipo> rInterventoTipoListOld = rinterventoTipoRepository
				.findAllValidByIntIdAndEnteId(interventoId, enteId);
		for (RInterventoTipo rInterventoTipo : rInterventoTipoListOld) {
			rInterventoTipo.setValiditaFine(now);
			rInterventoTipo.setDataModifica(now);
			rInterventoTipo.setUtenteModifica(userInfo.codiceFiscale());
		}
		rinterventoTipoRepository.saveAll(rInterventoTipoListOld);
	}
	
	public void salvaRInterventoFinalita(InterventoBase body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved) {
		List<RInterventoFinalita> rInterventoFinalitaList = InterventoUtils.buildFinalitaInterventoListFromModel(body,
				userInfo, now, interventoSaved);
		rinterventoFinalitaRepository.saveAll(rInterventoFinalitaList);
	}

	public void storicizzaRInterventoFinalita(Integer interventoId, UserInfoRecord userInfo, Timestamp now, Integer enteId) {
		List<RInterventoFinalita> rInterventoFinalitaListOld = rinterventoFinalitaRepository
				.findAllValidByIntIdAndEnteId(interventoId, enteId);
		for (RInterventoFinalita rInterventoFinalita : rInterventoFinalitaListOld) {
			rInterventoFinalita.setValiditaFine(now);
			rInterventoFinalita.setDataModifica(now);
			rInterventoFinalita.setUtenteModifica(userInfo.codiceFiscale());
		}
		rinterventoFinalitaRepository.saveAll(rInterventoFinalitaListOld);
	}

	public void salvaRInterventoObiettivo(InterventoBase body, UserInfoRecord userInfo, Timestamp now,
			Intervento interventoSaved) {
		List<RInterventoObiettivo> rInterventoObiettoListNew = InterventoUtils
				.buildObiettivoInterventoListFromModel(body, userInfo, now, interventoSaved);
		rinterventoObiettivoRepository.saveAll(rInterventoObiettoListNew);
	}

	public void storicizzaRInterventoObiettivo(Integer interventoId, UserInfoRecord userInfo, Timestamp now, Integer enteId) {
		List<RInterventoObiettivo> rInterventoObiettoListOld = rinterventoObiettivoRepository
				.findAllValidByIntIdAndEnteId(interventoId, enteId);

		for (RInterventoObiettivo rInterventoObiettivo : rInterventoObiettoListOld) {
			rInterventoObiettivo.setValiditaFine(now);
			rInterventoObiettivo.setDataModifica(now);
			rInterventoObiettivo.setUtenteModifica(userInfo.codiceFiscale());
		}
		rinterventoObiettivoRepository.saveAll(rInterventoObiettoListOld);
	}
	
}
