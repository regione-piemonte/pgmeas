/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class InterventoBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6148448244895244257L;
	private Integer intId = null;
	private String intCodPgmeas = null;
	private String intCodPgmeasOrig = null;
	private Integer intAnnoOrig = null;
	private Integer intCopiatoId = null;
	private Integer intCopiatoEnteId= null;
	private Integer intAnno = null;
	private String intTitolo = null;
	private String intCup = null;
	private BigDecimal intImporto = null;
	private String intCodicNsis = null;
	private String intDirettoreGeneraleCognome = null;
	private String intDirettoreGeneraleNome = null;
	private String intDirettoreGeneraleCf=null;
	private String intCommissarioCognome = null;
	private String intCommissarioNome = null;
	private String intCommissarioCf=null;
	private String intReferentePraticaCognome = null;
	private String intReferentePraticaNome = null;
	private String intReferentePraticaCf=null;
	private String intReferentePraticaTelefono = null;
	private String intReferentePraticaEmail = null;
	private String intRupNome = null;
	private String intRupCognome = null;
	private String intRupCf=null;
	private Integer intQuadranteId = null;
	private List<Integer> listaIntFinalitaId = new ArrayList<Integer>();
	private List<Integer> listaIntObiettivoId = new ArrayList<Integer>();
	private List<Integer> listaIntCategoriaId = new ArrayList<Integer>();
	private List<Integer> listaIntStatoProgettualeId = new ArrayList<Integer>();
	private List<Integer> listaIntTipoId = new ArrayList<Integer>();
	private List<Integer> listaIntTipoDetId = null;
	private List<Integer> listaIntAppaltoTipoId = new ArrayList<Integer>();
	private List<Integer> listaIntContrattoId = new ArrayList<Integer>();
	private List <InterventoStrutturaToSave> interventoStrutturaList =  new ArrayList<InterventoStrutturaToSave>();
	//TODO DA CAPIRE SE TENERE
//	private List<Integer> listaAllegatoMonitoraggioId = new ArrayList<Integer>();
	//TODO DA CAPIRE SE TENERE
//	private Long allegatoRichiestaAmmissioneFinanziamentoId = null;
	private Date dataCreazione = null;
	private Date dataModifica = null;
	private Date dataCancellazione = null;
	private String utenteCreazione = null;
	private String utenteModifica = null;
	private String utenteCancellazione = null;
	private Boolean intAppaltoIntegrato=false;
	private Integer intProgettazioneGg=null;
	private Integer intAffidamentoLavoriGg=null;
	private Integer intEsecuzioneLavoriGg=null;
	private Integer intCollaudoGg=null;
	private Integer intPrioritaAnno = null;
	private Integer intPriorita = null;
	private String intSottopriorita = null;
	private String intNote=null;
}
