/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasnotifier.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.csi.pgmeas.commons.dto.EventoDecodedDto;
import it.csi.pgmeas.commons.dto.EventoTipoDecodedDto;
import it.csi.pgmeas.commons.exception.PgmeasException;
import it.csi.pgmeas.commons.util.enumeration.EventoTipoEnum;
import it.csi.pgmeas.pgmeasnotifier.dto.enumeration.CategoriaErroreEnum;
import it.csi.pgmeas.pgmeasnotifier.exception.BatchBuilderException;
import it.csi.pgmeas.pgmeasnotifier.exception.ConfiguratoreException;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.NotificaBuilderInterface;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder.AsrInviaIntNotificaBuilder;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder.AsrInviaModuloANotificaBuilder;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder.RegAppIntNotificaBuilder;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder.RegAppModuloANotificaBuilder;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder.RegCreaProgNotificaBuilder;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder.RegCreaProrogaProgNotificaBuilder;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder.RegRespIntNotificaBuilder;
import it.csi.pgmeas.pgmeasnotifier.notifichebuilder.builder.RegRespModuloANotificaBuilder;
import jakarta.annotation.PostConstruct;

@Service
public class BuilderNotificheService {
	
	private static final Logger log= LoggerFactory.getLogger(BuilderNotificheService.class);
	
	  private final static Map<String, NotificaBuilderInterface> builderNotificaMap = new HashMap<String, NotificaBuilderInterface>();
	  
	  private RegCreaProgNotificaBuilder regCreaProgramNotificaBuilder;
	  private RegCreaProrogaProgNotificaBuilder regCreaProrogaProgNotificaBuilder;
	  private AsrInviaIntNotificaBuilder asrInviaIntNotificaBuilder;
	  private RegAppIntNotificaBuilder regAppIntNotificaBuilder;
	  private RegRespIntNotificaBuilder regRespIntNotificaBuilder;
	  private AsrInviaModuloANotificaBuilder asrInviaModuloANotificaBuilder;
	  private RegAppModuloANotificaBuilder regAppModuloANotificaBuilder;
	  private RegRespModuloANotificaBuilder regRespModuloANotificaBuilder;
	  private DecodificheService decodificheService;
	  private ErroreService erroreService;

	  public BuilderNotificheService(
			   RegCreaProgNotificaBuilder regCreaProgramNotificaBuilder,
			   RegCreaProrogaProgNotificaBuilder regCreaProrogaProgNotificaBuilder,
			   AsrInviaIntNotificaBuilder asrInviaIntNotificaBuilder,
			   RegAppIntNotificaBuilder regAppIntNotificaBuilder,
			   RegRespIntNotificaBuilder regRespIntNotificaBuilder,
			   AsrInviaModuloANotificaBuilder asrInviaModuloANotificaBuilder,
			   RegAppModuloANotificaBuilder regAppModuloANotificaBuilder,
			   RegRespModuloANotificaBuilder regRespModuloANotificaBuilder,
			   DecodificheService decodificheService,
			   ErroreService erroreService) {
	        this.regCreaProgramNotificaBuilder = regCreaProgramNotificaBuilder;
	        this.regCreaProrogaProgNotificaBuilder=regCreaProrogaProgNotificaBuilder;
	        this.asrInviaIntNotificaBuilder=asrInviaIntNotificaBuilder;
	        this.regAppIntNotificaBuilder=regAppIntNotificaBuilder;
	        this.regRespIntNotificaBuilder=regRespIntNotificaBuilder;
	        this.asrInviaModuloANotificaBuilder=asrInviaModuloANotificaBuilder;
	        this.regAppModuloANotificaBuilder=regAppModuloANotificaBuilder;
	        this.regRespModuloANotificaBuilder=regRespModuloANotificaBuilder;
	        this.decodificheService=decodificheService;
	        this.erroreService=erroreService;
	    }

	    @PostConstruct
	    public void init() {
	    	log.info("init map builder");
	    	builderNotificaMap.put(EventoTipoEnum.REGIONE_DEFINISCE_PERIODO_PROGRAMMAZIONE.getCode(), regCreaProgramNotificaBuilder);
	    	builderNotificaMap.put(EventoTipoEnum.REGIONE_DEFINISCE_PROROGA.getCode(), regCreaProrogaProgNotificaBuilder);
	    	builderNotificaMap.put(EventoTipoEnum.ASR_INVIA_A_REGIONE_UN_INTERVENTO.getCode(), asrInviaIntNotificaBuilder);
	    	builderNotificaMap.put(EventoTipoEnum.REGIONE_APPROVA_INTERVENTO.getCode(), regAppIntNotificaBuilder);
	    	builderNotificaMap.put(EventoTipoEnum.REGIONE_RESPINGE_INTERVENTO.getCode(), regRespIntNotificaBuilder);
	        builderNotificaMap.put(EventoTipoEnum.ASR_INVIA_A_REGIONE_MODULO_A_AA.getCode(), asrInviaModuloANotificaBuilder);
	        builderNotificaMap.put(EventoTipoEnum.REGIONE_APPROVA_MODULO_A_AA.getCode(), regAppModuloANotificaBuilder);
	        builderNotificaMap.put(EventoTipoEnum.REGIONE_RESPINGE_MODULO_A_AA.getCode(), regRespModuloANotificaBuilder);
	    }
	  
	    public void build(EventoDecodedDto eventoDecoded)throws Exception {
	    	String tabella="Evento";
	    	Long causa= (eventoDecoded.getEventoId() != null) ? Long.valueOf(eventoDecoded.getEventoId()) : null;
	    	try {
	    		log.info("Evento da elaborare di tipo: {} ", eventoDecoded.getEventoTipoCod());
		    	String tipo = eventoDecoded.getEventoTipoCod();
		    	NotificaBuilderInterface builder = getBuilderFromMap(tipo);
		    	log.info("Avvio build Notifica sul builder: {} ", builder.getClass().getName());
		    	EventoTipoDecodedDto eventoTipoDecoded = decodificheService.findByEventoTipoCod(tipo);
		    	log.info("Estrazione eventoTipoCorrelato: {} ", eventoTipoDecoded.toString());
		    	builder.build(eventoDecoded, eventoTipoDecoded);
		    	
				
				} catch (ConfiguratoreException ce) {
					log.error("ConfiguratoreException {}", ce.getErrorPayload());
					log.error("ConfiguratoreException {}",ce.getMessage(), ce);
					erroreService.traceError(ce, CategoriaErroreEnum.CONFIGURATORE, tabella, causa);
				} catch (BatchBuilderException bbe) {
					log.error("BatchBuilderException {}", bbe.getMessage(),bbe);
					erroreService.traceError(bbe, CategoriaErroreEnum.BATCH_BUILDER, tabella, causa);
				} catch (PgmeasException bdE) {
					log.error("PgmeasException {}", bdE.getMessage(),bdE);
					erroreService.traceError(bdE, CategoriaErroreEnum.PGMEAS_EXC, tabella, causa);
				} catch (Exception e) {
					// TODO GESTIRE I CATCH
					log.error("Exception {}",e.getMessage(), e);
					erroreService.traceError(e, CategoriaErroreEnum.GENERICO, tabella, causa);
				}
	    }
	    
	    private NotificaBuilderInterface getBuilderFromMap(String tipo) throws BatchBuilderException {
			NotificaBuilderInterface builder = builderNotificaMap.get(tipo);
			if(builder!=null) {
				return builder;
	    	}else {
	    		 throw new BatchBuilderException("Builder non presente per il tipo di notifica: "+ tipo);
	    	}
		}

		
}
