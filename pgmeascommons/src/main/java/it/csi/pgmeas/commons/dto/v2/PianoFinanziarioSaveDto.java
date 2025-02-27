/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PianoFinanziarioSaveDto implements Serializable {
	private static final long serialVersionUID = -4601712009686669653L;

	private Integer finanziamentoId;
	private Integer tipologiaDettaglioId;
	private Boolean isPrincipale;
	private BigDecimal importoTotale;
	private List<FinanziamentoImportoTipoSaveDto> finanziamentoImportoTipo;
}
