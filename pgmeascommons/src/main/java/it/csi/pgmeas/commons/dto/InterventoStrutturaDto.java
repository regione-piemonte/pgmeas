/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class InterventoStrutturaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3814057155121863885L;

	private Integer intStrId;
	private Integer intId;	
	private Integer strId;	
	private BigDecimal intstrImporto;
	private String intstrResponsabileCognome;	
	private String intstrResponsabileNome;
	private String intstrResponsabileProcedimentoCognome;		
	private String intstrResponsabileProcedimentoNome;		
	private Boolean intstrParereVincolantePpp;	
	private String intstrParereVincolantePppNprotocollo;	
	private Boolean intstrParereCabinaDiRegia;	
	private String intstrParereCabinaDiRegiaNprotocollo;
	private Timestamp intstrAperturaCantiereDataPrevista;
	private Timestamp intstrAperturaCantiereDataEffettiva;
	private String intstrPrioritaAnno;
	private Integer intstrPriorita;
	private String intstrSottopriorita;
	private Timestamp intstrCollaudoDataPrevista;
	private Timestamp intstrCollaudoDataEffettiva;
	private Integer intstrPftePrevisioneDurataGg;
	private Integer intstrGaraPrevisioneDurataGg;
	private Integer intstrCollaudoPrevisioneDurataGg;	
	private Integer intstrProgettoesecutivoPrevisioneDurataGg;	
	private Integer intstrLavoriPrevisioneDurataGg;
	private Integer intstrAttivazionePrevisioneDurataGg;
	private Map<Integer,BigDecimal> mapClassfifTreeIdImporto = new HashMap<>();
	private Map<Integer,DicAppItem> mapClassfifTreeIdDaSelezionata = new HashMap<>();
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;	
	private String utenteCancellazione;
	private Integer enteId;

}
