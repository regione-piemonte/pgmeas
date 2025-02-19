/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto.v2;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
@Data
public class InterventoStrutturaToSave   {
  private Integer intStrId=null;	
  private Integer strId = null;
  private Map<Integer, Boolean> intStrTipoIntEdilMap = new HashMap<Integer, Boolean>();
  private BigDecimal intStrImporto = null;
  private Boolean intStrAppaltoIntegrato=false;
  private Integer intStrProgettazioneGg=null;
  private Integer intStrAffidamentoLavoriGg=null;
  private Integer intStrEsecuzioneLavoriGg=null;
  private Integer intStrCollaudoGg=null;
  private Map<Integer, BigDecimal> quadroEconMap= new HashMap<Integer, BigDecimal>();
//  private Boolean intstrParereVincolantePppSOLOPERPUT = null;
//  private String intstrParereVincolantePppNprotocolloSOLOPERPUT = null;
//  private List<String> garaAppaltoCigCodListSOLOPERPUT = new ArrayList<String>();
//  private InterventoStrutturaToSaveDicAppMapSOLOPERPUT dicAppMapSOLOPERPUT = null;
  private String intstrRespStrComplesNome = null;
  private String intstrRespStrComplesCognome = null;
  private String intstrRespStrComplesCf = null;
  private String intstrRespStrSemplNome = null;
  private String intstrRespStrSemplCognome = null;
  private String intstrRespStrSemplCf = null;
 
}
