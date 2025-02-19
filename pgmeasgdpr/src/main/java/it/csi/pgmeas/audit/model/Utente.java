/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.audit.model;

import java.io.Serializable;

public class Utente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5602007492075298016L;
	
	private String nome;
	private String cognome;
	private String ente;
	private String ruolo;
	private String codFisc;
	private int livAuth;
	private String community;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	public int getLivAuth() {
		return livAuth;
	}
	public void setLivAuth(int livAuth) {
		this.livAuth = livAuth;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	
	@Override
	public String toString() {
		return "Utente [nome=" + nome + ", cognome=" + cognome + ", ente=" + ente + ", ruolo=" + ruolo + ", codFisc="
				+ codFisc + ", livAuth=" + livAuth + ", community=" + community + "]";
	}
	
	
	

}
