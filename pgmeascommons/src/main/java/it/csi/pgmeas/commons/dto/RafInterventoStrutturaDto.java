/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class RafInterventoStrutturaDto {
	private Boolean intStrAppaltoIntegrato;
	private Integer intStrProgettazioneGg;
	private Integer intStrAffidamentoLavoriGg;
	private Integer intStrEsecuzioneLavoriGg;
	private Integer intStrCollaudoGg;
	private Map<Integer, DichAppaltabilitaDto> dicAppMap;
	private Map<Integer, BigDecimal> quadroEconMap;
}
