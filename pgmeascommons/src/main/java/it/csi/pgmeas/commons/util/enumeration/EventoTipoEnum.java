/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum EventoTipoEnum {
	REGIONE_DEFINISCE_PERIODO_PROGRAMMAZIONE("EV000001"),
    REGIONE_DEFINISCE_PROROGA("EV000002"),
    ASR_INVIA_A_REGIONE_UN_INTERVENTO("EV000003"),
    REGIONE_APPROVA_INTERVENTO("EV000004"),
    REGIONE_RESPINGE_INTERVENTO("EV000005"),
    ASR_INVIA_A_REGIONE_MODULO_A_AA("EV000006"),
    REGIONE_APPROVA_MODULO_A_AA("EV000007"),
    REGIONE_RESPINGE_MODULO_A_AA("EV000008");
	
	private String code;

	private EventoTipoEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static final List<EventoTipoEnum> EVENTI_REGIONE = 
		    Collections.unmodifiableList(
		    		Arrays.asList(
		    				EventoTipoEnum.ASR_INVIA_A_REGIONE_UN_INTERVENTO,
		    				EventoTipoEnum.ASR_INVIA_A_REGIONE_UN_INTERVENTO)
		    		);
	
	public static final List<EventoTipoEnum> EVENTI_ASR = 
		    Collections.unmodifiableList(
		    		Arrays.asList(
		    				EventoTipoEnum.REGIONE_DEFINISCE_PERIODO_PROGRAMMAZIONE,
		    				EventoTipoEnum.REGIONE_DEFINISCE_PROROGA,
		    				EventoTipoEnum.REGIONE_APPROVA_INTERVENTO,
		    				EventoTipoEnum.REGIONE_RESPINGE_INTERVENTO,
		    				EventoTipoEnum.REGIONE_APPROVA_MODULO_A_AA,
		    				EventoTipoEnum.REGIONE_RESPINGE_MODULO_A_AA
		    				));
}
