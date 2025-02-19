/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDto {
	private String base64;
	private String fileName;
	private String fileType;
}
