/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class AllegatoLightDto implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -3995596011588469722L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String fileNameUser;
	 @JsonInclude(JsonInclude.Include.NON_NULL)
	private String fileType;
	 @JsonInclude(JsonInclude.Include.NON_NULL)
	private String base64;
}
