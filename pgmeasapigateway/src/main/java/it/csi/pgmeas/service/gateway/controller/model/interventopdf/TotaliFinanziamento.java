/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.interventopdf;

import java.math.BigDecimal;

public class TotaliFinanziamento {
	private BigDecimal totaleRegione = BigDecimal.ZERO;
	private BigDecimal totaleStato = BigDecimal.ZERO;
	private BigDecimal totale = BigDecimal.ZERO;

	public TotaliFinanziamento() {
		super();
	}

	public TotaliFinanziamento(BigDecimal totaleRegione, BigDecimal totaleStato, BigDecimal totale) {
		super();
		this.totaleRegione = totaleRegione;
		this.totaleStato = totaleStato;
		this.totale = totale;
	}

	public BigDecimal getTotaleRegione() {
		return totaleRegione;
	}

	public void setTotaleRegione(BigDecimal totaleRegione) {
		this.totaleRegione = totaleRegione;
	}

	public BigDecimal getTotaleStato() {
		return totaleStato;
	}

	public void setTotaleStato(BigDecimal totaleStato) {
		this.totaleStato = totaleStato;
	}

	public BigDecimal getTotale() {
		return totale;
	}

	public void setTotale(BigDecimal totale) {
		this.totale = totale;
	}

}
