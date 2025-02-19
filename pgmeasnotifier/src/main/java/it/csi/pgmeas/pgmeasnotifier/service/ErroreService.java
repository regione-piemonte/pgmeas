/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.CategoriaErroreEnum;
import it.csi.pgmeas.pgmeasnotifier.model.DBatchErrore;
import it.csi.pgmeas.pgmeasnotifier.model.DErroreCategoria;
import it.csi.pgmeas.pgmeasnotifier.repository.DBatchErroreRepository;
import it.csi.pgmeas.pgmeasnotifier.repository.DErroreCategoriaRepository;
import jakarta.annotation.PostConstruct;

@Service
public class ErroreService {

	private static final Logger log = LoggerFactory.getLogger(ErroreService.class);
	
	private DErroreCategoriaRepository dErroreCategoriaRepository;
	private DBatchErroreRepository dBatchErroreRepository;
	
	private static final String UTENTE = "UTENTE_BATCH";
	
	private List<DErroreCategoria> dErroreCategoriaList;

	public ErroreService(DErroreCategoriaRepository dErroreCategoriaRepository,
			DBatchErroreRepository dBatchErroreRepository) {
		this.dErroreCategoriaRepository = dErroreCategoriaRepository;
		this.dBatchErroreRepository = dBatchErroreRepository;
	}

	@PostConstruct
	public void init() {
		caricaCategorieErrore();
	}

	private void caricaCategorieErrore() {
		this.dErroreCategoriaList = dErroreCategoriaRepository.findAllValid();
	}

	public void traceError(Exception ex, CategoriaErroreEnum categoriaErrore, String tabella, Long idCausa) {
		try {
			Integer idcategoria=findCategoriaErrore(categoriaErrore);
			DBatchErrore errore;
			List<DBatchErrore>batchErroreList=null;
			if(StringUtils.isNotBlank(tabella)&& idCausa!=null) {
				batchErroreList=dBatchErroreRepository.findCurrendDayByParameters(ex.getMessage(),idcategoria,tabella,idCausa);
			}else {
				batchErroreList=dBatchErroreRepository.findCurrendDayByParameters(ex.getMessage(),idcategoria);
			}
			if( batchErroreList==null || batchErroreList.isEmpty()) {
				 errore = buildDBatchErrore(ex, idcategoria, tabella, idCausa);
			}else {
				Timestamp now = new Timestamp(System.currentTimeMillis());
				errore = batchErroreList.get(0);
				errore.setDataModifica(now);
			}
			dBatchErroreRepository.save(errore);
		} catch (Exception e) {
			log.error("Trace Error exception: {}", e.getMessage(), e);
		}
	}

	public void traceError(Exception ex, CategoriaErroreEnum categoriaErrore) {
		traceError(ex, categoriaErrore, null, null);
	}

	private DBatchErrore buildDBatchErrore(Exception ex, Integer idcategoria, String tabella,
			Long idCausa)  {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		DBatchErrore errore = new DBatchErrore();
		errore.setErrCategoriaId(idcategoria);
		errore.setBatchErrMessaggio(ex.getMessage());
		errore.setBatchErrDescrizione(ExceptionUtils.getStackTrace(ex));
		errore.setBatchErrDovutoaTabNome(tabella);
		errore.setBatchErrDovutoaTabId(idCausa);
		errore.setBatchErrData(now);
		errore.setDataCreazione(now);
		errore.setDataModifica(now);
		errore.setUtenteCreazione(UTENTE);
		errore.setUtenteModifica(UTENTE);
		return errore;
	}

	private Integer findCategoriaErrore(CategoriaErroreEnum categoriaEnum) throws PgmeasException {
		DErroreCategoria result = this.dErroreCategoriaList.stream()
				.filter(dto -> categoriaEnum.getCode().equals(dto.getErrCategoriaCod())).findFirst().orElse(null);
		if (result != null) {
			return result.getErrCategoriaId();
		} else {
			throw new PgmeasException("Errore categoria non supportato: " + categoriaEnum);
		}
	}

}
