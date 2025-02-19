/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InterventoResultDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1579429211602659451L;
	
	private Integer intId;
	private String intCod;
	private String intTitolo;
	private Integer intAnno;
	private String intCup;
	private BigDecimal intImporto;
	private String intCodicNsis;
	private String intDgAziendaleApprovazione;
	private Timestamp intDgAziendaleApprovazioneData;
	private String intDgRegionaleApprovazione;
	private Timestamp intDgRegionaleApprovazioneData;
	private String intDirettoreGeneraleCognome;
	private String intDirettoreGeneraleNome;
	private String intCommissarioCognome;
	private String intCommissarioNome;
	private String intReferentePraticaCognome;
	private String intReferentePraticaNome;
	private String intReferentePraticaTelefono;
	private String intReferentePraticaFax;
	private String intReferentePraticaEmail;
	private Integer intFormaRealizzativaId;
	private Integer intStrTipoId;
	private Integer intQuadranteId;
	private List<Integer> listaIntFinalitaId = new ArrayList<>();
	private List<Integer> listaIntObiettivoId = new ArrayList<>();
	private List<Integer> listaIntCategoriaId = new ArrayList<>();
	private List<Integer> listaIntStatoId = new ArrayList<>();
	private List<Integer> listaIntStatoProgettualeId = new ArrayList<>();
	private List<Integer> listaIntTipoId = new ArrayList<>();
	private List<Integer> listaIntAppaltoTipoId = new ArrayList<>();
	private List<Integer> listaIntContrattoId = new ArrayList<>();
	private List<Integer> listaIntStrutturaId = new ArrayList<>();
	private List<Integer> listaAllegatoMonitoraggioId = new ArrayList<>();
	@JsonProperty("rIntModuloAId")
	private Integer rIntModuloAId;
	private Integer allegatoRichiestaAmmissioneFinanziamentoId;
	private Integer allegatoRichiestaAmmissioneFinanziamentoStatoId;
	private Timestamp dataCreazione;
	private Timestamp dataModifica;
	private Timestamp dataCancellazione;
	private String utenteCreazione;
	private String utenteModifica;
	private String utenteCancellazione;
	private Integer enteId;
	private Boolean creaModuloA;
	
}
