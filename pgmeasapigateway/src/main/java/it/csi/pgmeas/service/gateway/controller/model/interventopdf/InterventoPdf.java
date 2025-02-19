/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.interventopdf;

import java.util.List;

import it.csi.pgmeas.commons.dto.AllegatoLightExtDto;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterventoPdf {
	private String dataInserimentoIntervento;
	private String dataOraGenerazione;// *
	private String codice;// *
	private String titolo;// *
	private String anno;// *
	private String cup; // *
	private String codiceOrig;
	private Integer annoOrig;
	private String codiceNSIS;// *
	
	private String importo; // *
	
	private String direttoreGenerale;
	private String commissario;
	private String referentePratica;
	private String rup;

	//
	private String quadrante;
	private String ente;

	private Boolean intFinanziabile = null;
	private Boolean appaltoIntegrato = null;

	private Integer annoPriorita;
	private Integer priorita;
	private String sottopriorita;
	
	//
	private StimeDurateInt stimeDurataInt;

	//
	private List<String> obiettivi = null;// *
	private List<String> finalita;// *
	private List<String> tipi;// *
	private List<String> categorie;// *

	private List<String> appaltiTipo = null;
	private List<String> statiProgettuali = null;

	private boolean flgAttrezzatura = false;
	private List<String> descrizioniAttrezzature = null;

	// Liste per la stampa, disaccoppiate dagli oggetti originali
	private List<PrevisioneSpesaPdf> previsioniDiSpesa;
	private String  totalePrevisioniDiSpesa;

	private List<PianoFinanziarioPdf> pianoFinanziarioPrincipale;
	private String totalePianoFinanziarioPrincipale;
	private String totalePianoFinanziarioRegionePrincipale;
	private String totalePianoFinanziarioStatoPrincipale;
	private List<PianoFinanziarioPdf> pianoFinanziarioAltro;
	private String totalePianoFinanziarioAltro;
	private String totalePianoFinanziarioRegioneAltro;
	private String totalePianoFinanziarioStatoAltro;
	private String totalePianoFinanziario;
	private String totalePianoFinanziarioRegione;
	private String totalePianoFinanziarioStato;

	private List<InterventoStrutturaPdf> interventoStrutturaPdf;
	private List<String> contrattiTipo;
	
	//ALLEGATI
	private List<AllegatoLightExtDto> allegatoProvvedimentoAziendaleApprovazione;
	private List<AllegatoLightExtDto> allegatoRelazioneTecnica;
	private List<AllegatoLightExtDto> allegatoDeliberaApprovazione;
	private List<AllegatoLightExtDto> allegatoDgrApprovazione;
	private List<AllegatoLightExtDto> allegatoDgrPropostaCR;
	private List<AllegatoLightExtDto> allegatoDcrApprovazione;
	private List<AllegatoLightExtDto> allegatiDeterminazioniDirigenziali;
	private List<AllegatoLightExtDto> allegatoNullaOsta;
	private List<AllegatoLightExtDto> allegatoDecretoMinisteriale;
	
	private String note;
	
	private Boolean flgVisualizzaCampiRegione; 
	private Boolean isRegione;
	
	private String moduloTipo;

	public boolean isModuloAA() {
		return ModuloTipoEnum.MODULO_A_A.getCode().equals(moduloTipo);
	}
}
