/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RichiestaAmmissioneFinanziamentoPutDto extends RichiestaAmmissioneFinanziamentoDto {
	private AllegatoLightExtDto allegatoProvAzAppToDelete=null;
	private AllegatoLightExtDto allegatoRelTecToDelete=null;
}
