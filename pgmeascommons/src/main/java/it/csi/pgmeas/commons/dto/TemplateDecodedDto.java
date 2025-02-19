/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import lombok.Data;
@Data
public class TemplateDecodedDto implements Serializable{

	private static final long serialVersionUID = 8363328909590638877L;
	
		private Integer templateId;
		private String templateCod;
		private String templateDesc;
		private String templateOggetto;
		private String templateCorpo;
		private String templateTipoCod;
		

}
