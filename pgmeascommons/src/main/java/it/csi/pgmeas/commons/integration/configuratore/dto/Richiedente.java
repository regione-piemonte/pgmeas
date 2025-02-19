/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.integration.configuratore.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Richiedente {
	private String nome = null;
	private String cognome = null;
	private String codiceFiscale = null;
	private String ruolo = null;
	private List<Funzionalita> listaFunzionalita = new ArrayList<>();
	private ModelCollocazione collocazione = null;

	private String profilo = null;

	/**
	 **/

	@JsonProperty("nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 **/

	@JsonProperty("cognome")
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 **/

	@JsonProperty("codice_fiscale")
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 **/

	@JsonProperty("ruolo")
	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	/**
	 **/
	@JsonProperty("funzionalita")
	public List<Funzionalita> getListaFunzionalita() {
		return listaFunzionalita;
	}

	public void setListaFunzionalita(List<Funzionalita> listaFunzionalita) {
		if (listaFunzionalita != null) {
			this.listaFunzionalita = new ArrayList<Funzionalita>();
			for (Funzionalita funzionalita : listaFunzionalita) {
				Funzionalita f = new Funzionalita();
				// Assign values with trim on String attributes
				f.setCodice(funzionalita.getCodice() != null ? funzionalita.getCodice().trim() : null);
				f.setDescrizione(funzionalita.getDescrizione() != null ? funzionalita.getDescrizione().trim() : null);
				f.setCodiceFunzionalitaPadre(funzionalita.getCodiceFunzionalitaPadre() != null
						? funzionalita.getCodiceFunzionalitaPadre().trim()
						: null);
				f.setDescrizioneFunzionalitaPadre(funzionalita.getDescrizioneFunzionalitaPadre() != null
						? funzionalita.getDescrizioneFunzionalitaPadre().trim()
						: null);

				this.listaFunzionalita.add(f);

				if (funzionalita.getCodiceFunzionalitaPadre() == null) {
					this.profilo = funzionalita.getCodice();
				}
			}
		}else {
			listaFunzionalita = null;
		}
	}

	@JsonProperty("collocazione")
	public ModelCollocazione getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(ModelCollocazione collocazione) {
		this.collocazione = collocazione;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Richiedente richiedente = (Richiedente) o;
		return Objects.equals(nome, richiedente.nome) && Objects.equals(cognome, richiedente.cognome)
				&& Objects.equals(codiceFiscale, richiedente.codiceFiscale) && Objects.equals(ruolo, richiedente.ruolo)
				&& Objects.equals(collocazione, richiedente.collocazione);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, cognome, codiceFiscale, ruolo, collocazione);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Richiedente {\n");

		sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
		sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
		sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
		sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
		sb.append("    collocazione: ").append(toIndentedString(collocazione)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

	public String getProfilo() {
		return profilo;
	}

	public void setProfilo(String profilo) {
		this.profilo = profilo;
	}

}
