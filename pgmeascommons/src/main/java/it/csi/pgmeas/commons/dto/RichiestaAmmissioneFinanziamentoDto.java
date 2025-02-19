/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class RichiestaAmmissioneFinanziamentoDto {
	private Integer intId=null;
	private String intDirettoreGeneraleCognome = null;
	private String intDirettoreGeneraleNome = null;
	private String intDirettoreGeneraleCf=null;
	private String intCommissarioCognome = null;
	private String intCommissarioNome = null;
	private String intCommissarioCf=null;
	private String intReferentePraticaCognome = null;
	private String intReferentePraticaNome = null;
	private String intReferentePraticaCf=null;
	private String intReferentePraticaTelefono = null;
	private String intReferentePraticaEmail = null;
	private String intRupNome = null;
	private String intRupCognome = null;
	private String intRupCf=null;
	private AllegatoLightExtDto allegatoProvAzApp=null;
	private AllegatoLightExtDto allegatoRelTec=null;
	private List<Integer> intStatoProgIdList=null;
	private Map<Integer, RafInterventoStrutturaDto> interventoStrutturaMap=null;
	private String note=null;
	private String moduloTipo;

}
