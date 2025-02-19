/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.service;

import org.springframework.beans.BeanUtils;

public class MappingUtils {
	public static <F, T> T copy(F from, T to) {
		BeanUtils.copyProperties(from, to);

		return to;
	}
}
