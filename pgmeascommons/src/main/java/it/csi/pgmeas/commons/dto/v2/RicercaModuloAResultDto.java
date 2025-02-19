/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RicercaModuloAResultDto extends RicercaInterventiResultDto implements Serializable {

	private static final long serialVersionUID = 8639925314641644606L;

	private Boolean creaModuloA = null;
	@JsonProperty("rIntModuloAId")
	private Integer rIntModuloAId;
	private Integer allegatoRichiestaAmmissioneFinanziamentoId;
	private Integer allegatoRichiestaAmmissioneFinanziamentoStatoId;
}
