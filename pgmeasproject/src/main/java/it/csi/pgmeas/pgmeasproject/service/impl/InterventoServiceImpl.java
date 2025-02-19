/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.AllegatoDto;
import it.csi.pgmeas.commons.dto.DicAppItem;
import it.csi.pgmeas.commons.dto.FinanziamentoLiquidazioneDto;
import it.csi.pgmeas.commons.dto.FinanziamentoLiquidazioneRichiestaDto;
import it.csi.pgmeas.commons.dto.InterventoFinanziamentoPrevSpesaDto;
import it.csi.pgmeas.commons.dto.InterventoGaraAppaltoDto2;
import it.csi.pgmeas.commons.dto.InterventoResultDto;
import it.csi.pgmeas.commons.dto.InterventoStrutturaDto;
import it.csi.pgmeas.commons.dto.LiquidazioneDto;
import it.csi.pgmeas.commons.model.Allegato;
import it.csi.pgmeas.commons.model.ClassificazioneTree;
import it.csi.pgmeas.commons.model.Intervento;
import it.csi.pgmeas.commons.model.InterventoPrevisioneSpesa;
import it.csi.pgmeas.commons.model.InterventoStruttura;
import it.csi.pgmeas.commons.model.Modulo;
import it.csi.pgmeas.commons.model.RInterventoAppaltoTipo;
import it.csi.pgmeas.commons.model.RInterventoCategoria;
import it.csi.pgmeas.commons.model.RInterventoContrattoTipo;
import it.csi.pgmeas.commons.model.RInterventoFinalita;
import it.csi.pgmeas.commons.model.RInterventoObiettivo;
import it.csi.pgmeas.commons.model.RInterventoStato;
import it.csi.pgmeas.commons.model.RInterventoStatoProgettuale;
import it.csi.pgmeas.commons.model.RInterventoTipo;
import it.csi.pgmeas.commons.repository.AllegatoRepository;
import it.csi.pgmeas.commons.repository.ClassificazioneTreeRepository;
import it.csi.pgmeas.commons.repository.FinanziamentoLiquidazioneRepository;
import it.csi.pgmeas.commons.repository.FinanziamentoLiquidazioneRichiestaRepository;
import it.csi.pgmeas.commons.repository.IntStrDicAppaltabilitaTreeRepository;
import it.csi.pgmeas.commons.repository.IntStrDicAppaltabilitaTsRepository;
import it.csi.pgmeas.commons.repository.InterventoGaraAppaltoRepository;
import it.csi.pgmeas.commons.repository.InterventoPrevisioneSpesaRepository;
import it.csi.pgmeas.commons.repository.InterventoRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaQuadroEconomicoTreeRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaQuadroEconomicoTsRepository;
import it.csi.pgmeas.commons.repository.InterventoStrutturaRepository;
import it.csi.pgmeas.commons.repository.ModuloRepository;
import it.csi.pgmeas.commons.repository.RInterventoAppaltoTipoRepository;
import it.csi.pgmeas.commons.repository.RInterventoCategoriaRepository;
import it.csi.pgmeas.commons.repository.RInterventoContrattoTipoRepository;
import it.csi.pgmeas.commons.repository.RInterventoFinalitaRepository;
import it.csi.pgmeas.commons.repository.RInterventoFinanziamentoPrevSpesaRepository;
import it.csi.pgmeas.commons.repository.RInterventoModuloRepository;
import it.csi.pgmeas.commons.repository.RInterventoObiettivoRepository;
import it.csi.pgmeas.commons.repository.RInterventoStatoProgettualeRepository;
import it.csi.pgmeas.commons.repository.RInterventoStatoRepository;
import it.csi.pgmeas.commons.repository.RInterventoTipoRepository;
import it.csi.pgmeas.commons.repository.RLiquidazioneRepository;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import it.csi.pgmeas.pgmeasproject.service.InterventoService;
import it.csi.pgmeas.pgmeasproject.util.Constants;
import it.csi.pgmeas.pgmeasproject.util.service.AllegatoUtilityService;


@Service
public class InterventoServiceImpl implements InterventoService{
	@Autowired
	InterventoRepository interventoRepository;
	
	@Autowired
	AllegatoUtilityService allegatoUtilityService;
	
	@Autowired
	ModuloRepository moduloRepository;
	
	@Autowired
	RInterventoModuloRepository rInterventoModuloRepository;
	
	@Autowired
	InterventoStrutturaRepository interventoStrutturaRepository;
	
	@Autowired
	RInterventoFinalitaRepository rInterventoFinalitaRepository;
	
	@Autowired
	RInterventoObiettivoRepository rInterventoObiettivoRepository;
	
	@Autowired
	RInterventoCategoriaRepository rInterventoCategoriaRepository;
	
	@Autowired
	RInterventoStatoRepository rInterventoStatoRepository;
	
	@Autowired
	RInterventoStatoProgettualeRepository rInterventoStatoProgRepository;
	
	@Autowired
	RInterventoTipoRepository rInterventoTipoRepository;
	
	@Autowired
	RInterventoAppaltoTipoRepository rInterventoAppTipoRepository;
	
	@Autowired
	RInterventoContrattoTipoRepository rInterventoContTipoRepository;
	
	@Autowired
	RInterventoFinanziamentoPrevSpesaRepository rInterventoFinanziamentoPrevSpesaRepository;
	
	@Autowired
	FinanziamentoLiquidazioneRepository finanziamentoLiquidazioneRepository;
	
	@Autowired
	FinanziamentoLiquidazioneRichiestaRepository finanziamentoLiquidazioneRichiestaRepository;
	
	@Autowired
	InterventoGaraAppaltoRepository interventoGaraAppaltoRepository;
	
	@Autowired
	InterventoStrutturaQuadroEconomicoTsRepository intStrutturaQeTsRepository;
	
	@Autowired
	InterventoStrutturaQuadroEconomicoTreeRepository intStrutturaQeTreeRepository;
	
	@Autowired
	IntStrDicAppaltabilitaTsRepository intStrDicAppaltabilitaTsRepository;
	
	@Autowired
	IntStrDicAppaltabilitaTreeRepository intStrDicAppaltabilitaTreeRepository;
	
	@Autowired
	RLiquidazioneRepository rLiquidazioneRepository;
	
	@Autowired
	AllegatoRepository allegatoRepository;
	
	@Autowired
	ClassificazioneTreeRepository classificazioneTreeRepository;
	
	@Autowired
	private InterventoPrevisioneSpesaRepository interventoPrevisioneSpesaRepository;

	@Override  // CDU01 Intervento per id intervento (dettaglio intervento) //TODO DELETE
	public InterventoResultDto getInterventoById(Integer id) {
		try {
			var interventoOptional = interventoRepository.findNonCancellatoById(id);
			if (interventoOptional.isPresent()) {
				InterventoResultDto dto = MappingUtils.copy(interventoOptional.get(), new InterventoResultDto());
				Integer enteId = dto.getEnteId();
				var appaltoTipoFuture = CompletableFuture.supplyAsync(() -> rInterventoAppTipoRepository.findAllSenzaCancellatiByIntIdAndEnteId(id, enteId));
				var categoriaFuture = CompletableFuture.supplyAsync(() -> rInterventoCategoriaRepository.findAllSenzaCancellatiByIntIdAndEnteId(id, enteId));
				var contrattoFuture = CompletableFuture.supplyAsync(() -> rInterventoContTipoRepository.findAllSenzaCancellatiByIntIdAndEnteId(id, enteId));
				var finalitaFuture = CompletableFuture.supplyAsync(() -> rInterventoFinalitaRepository.findAllSenzaCancellatiByIntIdAndEnteId(id, enteId));
				var obiettivoFuture = CompletableFuture.supplyAsync(() -> rInterventoObiettivoRepository.findAllSenzaCancellatiByIntIdAndEnteId(id, enteId));
				var statoFuture = CompletableFuture.supplyAsync(() -> rInterventoStatoRepository.findAllSenzaCancellatiByIntIdAndEnteId(id, enteId));
				var statoProgFuture = CompletableFuture.supplyAsync(() -> rInterventoStatoProgRepository.findAllSenzaCancellatiByIntIdAndEnteId(id, enteId));
				var strutturaFuture = CompletableFuture.supplyAsync(() -> interventoStrutturaRepository.findAllSenzaCancellatiByIntIdAndEnteId(id, enteId));
				var tipoFuture = CompletableFuture.supplyAsync(() -> rInterventoTipoRepository.findAllSenzaCancellatiByIntIdAndEnteId(id, enteId));
				var allegatoFuture = CompletableFuture.supplyAsync(() -> allegatoRepository.findAllValidByIntIdAndEnteId(id, enteId));
	
				CompletableFuture.allOf( appaltoTipoFuture, categoriaFuture, contrattoFuture,
						finalitaFuture, obiettivoFuture, statoFuture, statoProgFuture, strutturaFuture, tipoFuture, allegatoFuture).join();
	
				var appaltoTipoList = appaltoTipoFuture.get();
				var categoriaList = categoriaFuture.get();
				var contrattoList = contrattoFuture.get();
				var finalitaList = finalitaFuture.get();
				var obiettivoList = obiettivoFuture.get();
				var statoList = statoFuture.get();
				var statoProgList = statoProgFuture.get();
				var strutturaList = strutturaFuture.get();
				var tipoList = tipoFuture.get();
				var allegatoList = allegatoFuture.get();
			
				var listaIntAppaltoTipoId = appaltoTipoList.stream().map(RInterventoAppaltoTipo::getIntAppaltoTipoId)
						.toList();
				var listaIntCategoriaId = categoriaList.stream().map(RInterventoCategoria::getIntCategoriaId).toList();
				var listaIntContrattoId = contrattoList.stream().map(RInterventoContrattoTipo::getIntContrattoTipoId)
						.toList();
				var listaIntFinalitaId = finalitaList.stream().map(RInterventoFinalita::getIntFinalitaId).toList();
				var listaIntObiettivoId = obiettivoList.stream().map(RInterventoObiettivo::getIntObiettivoId).toList();
				var listaIntStatoId = statoList.stream().map(RInterventoStato::getIntStatoId).toList();
				var listaIntStatoProgettualeId = statoProgList.stream()
						.map(RInterventoStatoProgettuale::getIntStatoProgId).toList();
				var listaIntStrutturaId = strutturaList.stream().map(InterventoStruttura::getStrId).toList();
				var listaIntTipoId = tipoList.stream().map(RInterventoTipo::getIntTipoId).toList();
				var listaAllegatoMonitoraggioId = allegatoList.stream()
						.filter(a -> Constants.ALLEGATO_MONITORAGGIO_ID.equals(a.getAllegatoTipoId()))
						.map(Allegato::getAllegatoId).toList();
				var allegatoRichiestaAmmissioneFinanziamentoId = allegatoList.stream()
						.filter(a -> Constants.ALLEGATO_RICHIESTA_AMMISSIONE_FINANZIAMENTO_ID.equals(a.getAllegatoTipoId()))
						.map(Allegato::getAllegatoId).findFirst().orElse(null);

				
				dto.setListaIntAppaltoTipoId(listaIntAppaltoTipoId);
				dto.setListaIntCategoriaId(listaIntCategoriaId);
				dto.setListaIntContrattoId(listaIntContrattoId);
				dto.setListaIntFinalitaId(listaIntFinalitaId);
				dto.setListaIntObiettivoId(listaIntObiettivoId);
				dto.setListaIntStatoId(listaIntStatoId);
				dto.setListaIntStatoProgettualeId(listaIntStatoProgettualeId);
				dto.setListaIntStrutturaId(listaIntStrutturaId);
				dto.setListaIntTipoId(listaIntTipoId);
				dto.setListaAllegatoMonitoraggioId(listaAllegatoMonitoraggioId);
				dto.setAllegatoRichiestaAmmissioneFinanziamentoId(allegatoRichiestaAmmissioneFinanziamentoId);

				return dto;
			}

			return null;
		} catch (Exception e) {
			if (e instanceof InterruptedException)
				Thread.currentThread().interrupt();				
			throw new RuntimeException();
		}
	}

	@Override  //CDU01 Lista di tutti gli interventi in un determinato anno //TODO 2024 rimane così - DA CAPIRE
	public List<InterventoResultDto> getInterventoByAnno(Integer anno) {
		try {
			List<Intervento> interventi = interventoRepository.findAllSenzaCancellatiAndAnnullatiByIntAnno(anno);
			List<Modulo> moduloATipo = moduloRepository.findAllByModuloCod(ModuloTipoEnum.MODULO_A.getCode());
			Integer moduloATipoId = moduloATipo.get(0).getModuloId();
			List<Modulo> moduloAATipo = moduloRepository.findAllByModuloCod(ModuloTipoEnum.MODULO_A_A.getCode());
			Integer moduloAATipoId = moduloAATipo.get(0).getModuloId();
			List<Modulo> schedaCTipo = moduloRepository.findAllByModuloCod(ModuloTipoEnum.SCHEDA_C.getCode());
			Integer schedaCTipoId = schedaCTipo.get(0).getModuloId();
			
			List<InterventoResultDto> dtoList = new ArrayList<InterventoResultDto>();
			for(Intervento intervento : interventi) {
				Integer intId = intervento.getIntId();
				Integer enteId = intervento.getEnteId();
				var appaltoTipoFuture = CompletableFuture.supplyAsync(() -> rInterventoAppTipoRepository.findAllValidByIntIdAndEnteId(intId, enteId));
				var categoriaFuture = CompletableFuture.supplyAsync(() -> rInterventoCategoriaRepository.findAllValidByIntIdAndEnteId(intId, enteId));
				var contrattoFuture = CompletableFuture.supplyAsync(() -> rInterventoContTipoRepository.findAllValidByIntIdAndEnteId(intId, enteId));
				var finalitaFuture = CompletableFuture.supplyAsync(() -> rInterventoFinalitaRepository.findAllValidByIntIdAndEnteId(intId, enteId));
				var obiettivoFuture = CompletableFuture.supplyAsync(() -> rInterventoObiettivoRepository.findAllValidByIntIdAndEnteId(intId, enteId));
				var statoFuture = CompletableFuture.supplyAsync(() -> rInterventoStatoRepository.findValidByIntIdAndEnteId(intId, enteId));
				var statoProgFuture = CompletableFuture.supplyAsync(() -> rInterventoStatoProgRepository.findAllValidByIntIdAndEnteId(intId, enteId));
				var strutturaFuture = CompletableFuture.supplyAsync(() -> interventoStrutturaRepository.findAllSenzaCancellatiByIntIdAndEnteId(intId, enteId));
				var tipoFuture = CompletableFuture.supplyAsync(() -> rInterventoTipoRepository.findAllValidByIntIdAndEnteId(intId, enteId));
				var moduloAFuture = CompletableFuture.supplyAsync(() -> rInterventoModuloRepository.findAllValidByIntIdAndModuloIdAndEnteId(intId, moduloATipoId, enteId));
				var moduloAAFuture = CompletableFuture.supplyAsync(() -> rInterventoModuloRepository.findAllValidByIntIdAndModuloIdAndEnteId(intId, moduloAATipoId, enteId));
				var schedaCFuture = CompletableFuture.supplyAsync(() -> rInterventoModuloRepository.findAllValidByIntIdAndModuloIdAndEnteId(intId, schedaCTipoId, enteId));
				CompletableFuture<List<InterventoPrevisioneSpesa>> interventoPrevisioneSpesaFuture = CompletableFuture
						.supplyAsync(() -> interventoPrevisioneSpesaRepository.findAllValidByIntIdAndEnteId(intervento.getIntId(), enteId));
				
				CompletableFuture
						.allOf(appaltoTipoFuture, categoriaFuture, contrattoFuture, finalitaFuture,
								obiettivoFuture, statoFuture, statoProgFuture, strutturaFuture, tipoFuture, moduloAFuture, schedaCFuture,
								interventoPrevisioneSpesaFuture)
						.join();
				
				var appaltoTipoList = appaltoTipoFuture.get();
				var categoriaList = categoriaFuture.get();
				var contrattoList = contrattoFuture.get();
				var finalitaList = finalitaFuture.get();
				var obiettivoList = obiettivoFuture.get();
				var stato = statoFuture.get();
				var statoProgList = statoProgFuture.get();
				var strutturaList = strutturaFuture.get();
				var tipoList = tipoFuture.get();
				var moduloAList = moduloAFuture.get();
				var moduloAAList = moduloAAFuture.get();
				var schedaCList = schedaCFuture.get();
				var interventoPrevisioneSpesa = interventoPrevisioneSpesaFuture.get();
				
				var listaIntAppaltoTipoId = appaltoTipoList.stream()
						.map(RInterventoAppaltoTipo::getIntAppaltoTipoId).toList();
				var listaIntCategoriaId = categoriaList.stream()
						.map(RInterventoCategoria::getIntCategoriaId).toList();
				var listaIntContrattoId = contrattoList.stream()
						.map(RInterventoContrattoTipo::getIntContrattoTipoId).toList();
				var listaIntFinalitaId = finalitaList.stream()
						.map(RInterventoFinalita::getIntFinalitaId).toList();
				var listaIntObiettivoId = obiettivoList.stream()
						.map(RInterventoObiettivo::getIntObiettivoId).toList();
				var listaIntStatoId = new ArrayList<Integer>();
				listaIntStatoId.add(stato.getIntStatoId());
				var listaIntStatoProgettualeId = statoProgList.stream()
						.map(RInterventoStatoProgettuale::getIntStatoProgId).toList();
				var listaIntStrutturaId = strutturaList.stream()
						.map(InterventoStruttura::getStrId).toList();
				var listaIntTipoId = tipoList.stream()
						.map(RInterventoTipo::getIntTipoId).toList();
				var listaAllegatoMonitoraggioId = schedaCList.stream()
						.map(modulo -> modulo.getModuloId()).toList();
				var rIntModuloAId = moduloAList.stream()
						.map(modulo -> modulo.getRIntModuloId()).findFirst()
						.orElse(moduloAAList.stream()
						.map(modulo -> modulo.getRIntModuloId()).findFirst().orElse(null));
				var allegatoRichiestaAmmissioneFinanziamentoId = moduloAList.stream()
						.map(modulo -> modulo.getModuloId()).findFirst()
						.orElse(moduloAAList.stream()
						.map(modulo -> modulo.getModuloId()).findFirst().orElse(null));
				var allegatoRichiestaAmmissioneFinanziamentoStatoId = moduloAList.stream()
						.map(modulo -> modulo.getModuloStatoId()).findFirst()
						.orElse(moduloAAList.stream()
						.map(modulo -> modulo.getModuloStatoId()).findFirst().orElse(null));
						
				Collections.sort(interventoPrevisioneSpesa, Comparator.comparing(InterventoPrevisioneSpesa::getIntPrevSpesaAnno));
				InterventoPrevisioneSpesa intPrevSpesaMinore = interventoPrevisioneSpesa.size() > 0 ? interventoPrevisioneSpesa.get(0) : null;
				
				Integer currentYear = Year.now().getValue();
				Boolean esisteModuloA = allegatoRichiestaAmmissioneFinanziamentoId != null;
				Boolean prioritaAnnoCorretta = intervento.getIntPrioritaAnno() != null && intervento.getIntPrioritaAnno() <= currentYear;
				Boolean previsioneSpesaCorretta = intPrevSpesaMinore != null && intPrevSpesaMinore.getIntPrevSpesaAnno() <= currentYear;
				Boolean creaModuloA = !esisteModuloA && prioritaAnnoCorretta && previsioneSpesaCorretta;
				
				var dto = MappingUtils.copy(intervento, new InterventoResultDto());
				dto.setListaIntAppaltoTipoId(listaIntAppaltoTipoId);
				dto.setListaIntCategoriaId(listaIntCategoriaId);
				dto.setListaIntContrattoId(listaIntContrattoId);
				dto.setListaIntFinalitaId(listaIntFinalitaId);
				dto.setListaIntObiettivoId(listaIntObiettivoId);
				dto.setListaIntStatoId(listaIntStatoId);
				dto.setListaIntStatoProgettualeId(listaIntStatoProgettualeId);
				dto.setListaIntStrutturaId(listaIntStrutturaId);
				dto.setListaIntTipoId(listaIntTipoId);
				dto.setListaAllegatoMonitoraggioId(listaAllegatoMonitoraggioId);
				dto.setRIntModuloAId(rIntModuloAId);
				dto.setAllegatoRichiestaAmmissioneFinanziamentoId(allegatoRichiestaAmmissioneFinanziamentoId);
				dto.setAllegatoRichiestaAmmissioneFinanziamentoStatoId(allegatoRichiestaAmmissioneFinanziamentoStatoId);
				dto.setCreaModuloA(creaModuloA);
				dtoList.add(dto);
			}

			Comparator<InterventoResultDto> interventoComparator = Comparator.comparing(InterventoResultDto::getIntCod);
			return dtoList.stream()
					.sorted(interventoComparator.reversed()).collect(Collectors.toList());
		} catch (Exception e) {
			if (e instanceof InterruptedException)
				Thread.currentThread().interrupt();				
			throw new RuntimeException();
		}
	}


	@Override  //CDU01 Lista di tutte le strutture per un dato intervento //TODO DELETE
	public List<InterventoStrutturaDto> getInterventoStrutturaByIdInt(Integer intId) {
		List<InterventoStrutturaDto> data = interventoStrutturaRepository.findAllSenzaCancellatiByIntId(intId).stream().map(from -> {
			var intstrAperturaCantiereDataPrevista = from.getIntstrAperturaCantiereDataPrevista() != null
					? new Timestamp(from.getIntstrAperturaCantiereDataPrevista().getTime())
					: null;
			var intstrAperturaCantiereDataEffettiva = from.getIntstrAperturaCantiereDataEffettiva() != null
					? new Timestamp(from.getIntstrAperturaCantiereDataEffettiva().getTime())
					: null;
			var intstrCollaudoDataPrevista = from.getIntstrCollaudoDataPrevista() != null
					? new Timestamp(from.getIntstrCollaudoDataPrevista().getTime())
					: null;
			var intstrCollaudoDataEffettiva = from.getIntstrCollaudoDataEffettiva() != null
					? new Timestamp(from.getIntstrCollaudoDataEffettiva().getTime())
					: null;

			var to = MappingUtils.copy(from, new InterventoStrutturaDto());
			to.setIntstrAperturaCantiereDataPrevista(intstrAperturaCantiereDataPrevista);
			to.setIntstrAperturaCantiereDataEffettiva(intstrAperturaCantiereDataEffettiva);
			to.setIntstrCollaudoDataPrevista(intstrCollaudoDataPrevista);
			to.setIntstrCollaudoDataEffettiva(intstrCollaudoDataEffettiva);

			return to;
		}).toList();
		return data;
	}

	@Override  //CDU01 Lista di tutti gli anni presenti nella tabella intervento //TODO tenere
	public List<Integer> getAnniIntervento() {
		List<Integer> data = interventoRepository.getAnniFromInterventiNonCancellati();
		return data;
	}

	//TODO capire da qui
	@Override
	public List<InterventoFinanziamentoPrevSpesaDto> getInterventoFinanziamentoPrevSpesaByIdInt(Integer intId) { 
		List<InterventoFinanziamentoPrevSpesaDto> data = rInterventoFinanziamentoPrevSpesaRepository.findAllSenzaCancellatiByIntId(intId).stream()
				.map(from -> MappingUtils.copy(from, new InterventoFinanziamentoPrevSpesaDto())).toList();
		return data;
	}

	@Override
	public List<FinanziamentoLiquidazioneDto> getFinanziamentoLiquidazioneByIdInt(Integer intId) { 
		List<FinanziamentoLiquidazioneDto> data = finanziamentoLiquidazioneRepository.findAllSenzaCancellatiByIntId(intId).stream()
				.map(from -> MappingUtils.copy(from, new FinanziamentoLiquidazioneDto())).toList();
		return data;
	}

	@Override
	public List<FinanziamentoLiquidazioneRichiestaDto> getFinanziamentoLiquidazioneRichiestaByIdInt(Integer intId) {
		var liquidazioni = rLiquidazioneRepository.findAllSenzaCancellati();

		List<FinanziamentoLiquidazioneRichiestaDto> data = finanziamentoLiquidazioneRichiestaRepository.findAllSenzaCancellatiByIntId(intId).stream().map(from -> {
			var listaLiquidazione = liquidazioni.stream().filter(l -> l.getLiqRicId().equals(from.getLiqRicId()))
					.map(l -> MappingUtils.copy(l, new LiquidazioneDto())).toList();

			var dto = MappingUtils.copy(from, new FinanziamentoLiquidazioneRichiestaDto());
			dto.setListaLiquidazione(listaLiquidazione);

			return dto;
		}).toList();

		return data;
	}

	@Override
	public List<InterventoGaraAppaltoDto2> getInterventoGaraAppaltoByIdInt(Integer intId) {
		List<InterventoGaraAppaltoDto2> data = interventoGaraAppaltoRepository.findAllSenzaCancellatiByIntId(intId).stream()
				.map(from -> MappingUtils.copy(from, new InterventoGaraAppaltoDto2())).toList();
		return data;
	}

	@Override //TODO delete
	public List<AllegatoDto> getInterventoAllegatoByIdInt(Integer intId) {
		List<AllegatoDto> data = allegatoRepository.findAllValidByIntId(intId).stream()
				.map(from -> MappingUtils.copy(from, new AllegatoDto())).toList();
		return data;
	}

	@Override //TODO delete
	public List<InterventoStrutturaDto> getQePerIdIntervento(Integer intId) {
		try {
			var intStrFuture = CompletableFuture
					.supplyAsync(() -> interventoStrutturaRepository.findAllSenzaCancellatiByIntId(intId));
			var classifTreeFuture = CompletableFuture
					.supplyAsync(() -> classificazioneTreeRepository.findAllSenzaCancellatiByClassifTsIds(
							List.of(Constants.QUADRO_ECONOMICO_ID, Constants.DIC_APP_ID)));
			var qeTsFuture = CompletableFuture.supplyAsync(() -> intStrutturaQeTsRepository
					.findAllSenzaCancellatiByClassifTsId(Constants.QUADRO_ECONOMICO_ID));
			var qeTreeFuture = CompletableFuture.supplyAsync(intStrutturaQeTreeRepository::findAllSenzaCancellati);
			var dicAppTsFuture = CompletableFuture.supplyAsync(
					() -> intStrDicAppaltabilitaTsRepository.findAllSenzaCancellatiByClassifTsId(Constants.DIC_APP_ID));
			var dicAppTreeFuture = CompletableFuture
					.supplyAsync(intStrDicAppaltabilitaTreeRepository::findAllSenzaCancellati);

			CompletableFuture
					.allOf(intStrFuture, classifTreeFuture, qeTsFuture, qeTreeFuture, dicAppTsFuture, dicAppTreeFuture)
					.join();

			var intStrList = intStrFuture.get();
			var classifTreeList = classifTreeFuture.get();
			var qeTsList = qeTsFuture.get();
			var qeTreeList = qeTreeFuture.get();
			var daTsList = dicAppTsFuture.get();
			var daTreeList = dicAppTreeFuture.get();

			var qeClassifTreeList = classifTreeList.stream()
					.filter(a -> Constants.QUADRO_ECONOMICO_ID.equals(a.getClassifTsId())).toList();
			var daClassifTreeList = classifTreeList.stream()
					.filter(a -> Constants.DIC_APP_ID.equals(a.getClassifTsId())).toList();

			List<InterventoStrutturaDto> dtoList = intStrList.stream().map(is -> {
				/* var qeMap = new HashMap<Integer, BigDecimal>();
				qeTsList.stream().filter(x -> x.getIntstrId().equals(is.getIntStrId()))
						.forEach(x -> qeTreeList.stream().filter(y -> y.getIntstrQeTsId().equals(x.getIntstrQeTsId()))
								.forEach(y -> qeMap.put(y.getClassifTreeId(), y.getIntstrQeTreeImporto())));

				var dicAppMap = new HashMap<Integer, DicAppItem>();
				dicAppTsList.stream().filter(a -> a.getIntstrId().equals(is.getIntStrId()))
						.forEach(a -> dicAppTreeList.stream().filter(b -> b.getIntstrDaTsId().equals(a.getIntstrDaTsId()))
								.forEach(b -> dicAppMap.put(b.getClassifTreeId(), DicAppItem.builder().selezionata(b.getIntstrDaTreeSelezionata()).validazioneData(b.getIntstrDaTreeValidazioneData()).build()))); */

				var qeTs = qeTsList.stream().filter(a -> a.getIntstrId().equals(is.getIntStrId())).findAny()
						.orElse(null);
				var qeMap = qeClassifTreeList.stream()
						.collect(Collectors.toMap(ClassificazioneTree::getClassifTreeId, a -> {
							BigDecimal importo = BigDecimal.ZERO;

							if (qeTs != null) {
								var qeTree = qeTreeList.stream()
										.filter(b -> b.getClassifTreeId().equals(a.getClassifTreeId())
												&& b.getIntstrQeTsId().equals(qeTs.getIntstrQeTsId()))
										.findAny();

								if (qeTree.isPresent()) {
									importo = qeTree.get().getIntstrQeTreeImporto();
								}
							}

							return importo;
						}));

				var daTs = daTsList.stream().filter(a -> a.getIntstrId().equals(is.getIntStrId())).findAny()
						.orElse(null);
				var daMap = daClassifTreeList.stream()
						.collect(Collectors.toMap(ClassificazioneTree::getClassifTreeId, a -> {
							DicAppItem item = new DicAppItem();

							if (daTs != null) {
								var daTree = daTreeList.stream()
										.filter(b -> b.getClassifTreeId().equals(a.getClassifTreeId())
												&& b.getIntstrDaTsId().equals(daTs.getIntstrDaTsId()))
										.findAny();

								if (daTree.isPresent()) {
									item.setSelezionata(daTree.get().getIntstrDaTreeSelezionata());
									item.setValidazioneData(daTree.get().getIntstrDaTreeValidazioneData());
								}
							}

							return item;
						}));

				var dto = MappingUtils.copy(is, new InterventoStrutturaDto());
				dto.setMapClassfifTreeIdImporto(qeMap);
				dto.setMapClassfifTreeIdDaSelezionata(daMap);

				return dto;
			}).toList();

			return dtoList;
		} catch (Exception e) {
			if (e instanceof InterruptedException)
				Thread.currentThread().interrupt();				
			throw new RuntimeException();
		}
	}
}
