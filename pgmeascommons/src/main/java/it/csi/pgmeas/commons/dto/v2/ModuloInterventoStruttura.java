/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.util.Map;

import it.csi.pgmeas.commons.dto.DichAppaltabilitaDto;
import lombok.Data;

@Data
public class ModuloInterventoStruttura extends InterventoStrutturaV2GetDto   {
  private Map<Integer, DichAppaltabilitaDto> dichiarazioneAppaltabilita = null;
}
