/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.util.List;

import it.csi.pgmeas.commons.model.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
public class EventoTipoDecodedDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer eventoTipoId;
	
	private String eventoTipoCod;
	
	private String eventoTipoDesc;
	
	private Integer eventoMaxNumeroRetryNotifica;
	
	private Integer eventoMaxGgRetryNotifica;
	
	private Integer eventoGruppoId;
	private String eventoGruppoCod;
	private String eventoGruppoDesc;
	
	private List<TemplateDecodedDto> templateList;

}
