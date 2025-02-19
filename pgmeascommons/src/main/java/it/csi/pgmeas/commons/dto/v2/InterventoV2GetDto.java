/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.dto.CommonDto;
import lombok.Data;

@Data
public class InterventoV2GetDto extends CommonDto implements Serializable {

	private static final long serialVersionUID = 8639925314641644606L;

	private Integer intId = null;
	private String intCod = null;
	private String intTitolo = null;
	private Integer intAnno = null;
	private String intCup = null;
	private BigDecimal intImporto = null;
	private String intCodicNsis = null;
	private String intDirettoreGeneraleCognome = null;
	private String intDirettoreGeneraleNome = null;
	private String intDirettoreGeneraleCf = null;
	private String intCommissarioCognome = null;
	private String intCommissarioNome = null;
	private String intCommissarioCf = null;
	private String intReferentePraticaCognome = null;
	private String intReferentePraticaNome = null;
	private String intReferentePraticaCf = null;
	private String intReferentePraticaTelefono = null;
	private String intReferentePraticaEmail = null;
	private String intRupCognome = null;
	private String intRupNome = null;
	private String intRupCf = null;
	private Integer quadranteId = null;
	private Integer enteId = null;
	private Integer intPrioritaAnno = null;
	private Integer intPriorita = null;
	private String intSottopriorita = null;
	private Boolean intFinanziabile = null;
	private Integer copiatoDaIntId = null;
	private Integer copiatoDaEnteId = null;
	private Integer copiatoDaAnno = null;
	private String copiatoDaCodicePgmeas = null;
	private Boolean appaltoIntegrato = null;
	private Integer progettazioneGg = null;
	private Integer affidamentoLavoriGg = null;
	private Integer esecuzioneLavoriGg = null;
	private Integer collaudoGg = null;
	private List<Integer> interventiStruttura = null;
	private List<Integer> obiettivi = null;
	private List<Integer> finalita = null;
	private List<Integer> tipi = null;
	private List<Integer> descrizioniAttrezzature = null;
	private List<Integer> categorie = null;
	private List<Integer> contrattiTipo = null;
	private List<Integer> appaltiTipo = null;
	private List<Integer> statiProgettuali = null;
	private Integer stato = null;
	private String statoNota = null;
	private String note = null;
	private List<PrevisioneSpesaDto> previsioniDiSpesa = null;
	private List<PianoFinanziarioDto> pianiFinanziari = null;
	private List<AllegatoLightExtDto> allegatoDeliberaApprovazione = null;
	private List<AllegatoLightExtDto> allegatoDgrApprovazione = null;
	private List<AllegatoLightExtDto> allegatoDgrPropostaCR = null;
	private List<AllegatoLightExtDto> allegatoDcrApprovazione = null;
	private List<AllegatoLightExtDto> allegatiDeterminazioniDirigenziali = null;
	@JsonProperty("rIntModuloId") private Integer rIntModuloId;
	@JsonProperty("rIntModuloStatoId") private Integer rIntModuloStatoId = null;
}
