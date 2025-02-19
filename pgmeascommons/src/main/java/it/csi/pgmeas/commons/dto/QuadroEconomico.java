/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;


@Data
public class QuadroEconomico implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8028372484086793648L;
	
	private Map<Integer,BigDecimal> quadroEconomico;

}
