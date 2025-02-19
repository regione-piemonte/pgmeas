/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import it.csi.pgmeas.commons.dto.CommonDto;
import lombok.Data;

@Data
public class InterventoStrutturaV2GetDto extends CommonDto   {
  private Integer intStrId = null;	
  private Integer intId = null;	
  private Integer strId = null;
  private BigDecimal intstrImporto = null;
  private InterventoParereDto parerePPP = null;
  private InterventoParereDto parereHta = null;
  private Date intstrAperturaCantiereDataPrevista = null;
  private Date intstrAperturaCantiereDataEffettiva = null;
  private Date intstrCollaudoDataPrevista = null;
  private Date intstrCollaudoDataEffettiva = null;
  private Integer enteId = null;
  private String intstrResponsabileStrutturaComplessaCognome = null;
  private String intstrResponsabileStrutturaComplessaNome = null;
  private String intstrResponsabileStrutturaComplessaCf = null;
  private String intstrResponsabileStrutturaSempliceCognome = null;
  private String intstrResponsabileStrutturaSempliceNome = null;
  private String intstrResponsabileStrutturaSempliceCf = null;
  private Boolean appaltoIntegrato = null;
  private Integer progettazioneGg = null;
  private Integer affidamentoLavoriGg = null;
  private Integer esecuzioneLavoriGg = null;
  private Integer collaudoGg = null;
  private Map<Integer, BigDecimal> quadroEconomico = null;
  private Map<Integer, Boolean> interventoEdilizio = null;
}
