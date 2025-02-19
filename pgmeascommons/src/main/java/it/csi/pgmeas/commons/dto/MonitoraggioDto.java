/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MonitoraggioDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7501500776625815485L;

	private Integer intId;
	private Integer enteId;
	private String allegatoOggetto;
	private List<Integer> listaIntStatoProgettualeId; //lista di int_stato_prog_id
	private List<InterventoStrutturaDto> listaIntStruttura;
	private List<InterventoFinanziamentoPrevSpesaDto> listaIntFinanziamentoPrevSpesa;
	private List<LiquidazioneDto> listaFinanziamentoLiquidazioneRichiesta;
	
}