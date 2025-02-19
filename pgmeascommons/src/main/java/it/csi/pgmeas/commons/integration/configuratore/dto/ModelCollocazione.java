/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.integration.configuratore.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelCollocazione   {
  private String codiceCollocazione = null;
  private String descrizioneCollocazione = null;
  private String codiceAzienda = null;
  private String descrizioneAzienda = null;

  /**
   * codice della collocazione
   **/
  
  @JsonProperty("codice_collocazione")
  public String getCodiceCollocazione() {
    return codiceCollocazione;
  }
  public void setCodiceCollocazione(String codiceCollocazione) {
    this.codiceCollocazione = codiceCollocazione;
  }

  /**
   * descrizione della collocazione
   **/
  
  @JsonProperty("descrizione_collocazione")
  public String getDescrizioneCollocazione() {
    return descrizioneCollocazione;
  }
  public void setDescrizioneCollocazione(String descrizioneCollocazione) {
    this.descrizioneCollocazione = descrizioneCollocazione;
  }

  /**
   * codice dell&#x27;azienda
   **/
  
  @JsonProperty("codice_azienda")
  public String getCodiceAzienda() {
    return codiceAzienda;
  }
  public void setCodiceAzienda(String codiceAzienda) {
    this.codiceAzienda = codiceAzienda;
  }

  /**
   * descrizione del azienda
   **/
  
  @JsonProperty("descrizione_azienda")
  public String getDescrizioneAzienda() {
    return descrizioneAzienda;
  }
  public void setDescrizioneAzienda(String descrizioneAzienda) {
    this.descrizioneAzienda = descrizioneAzienda;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelCollocazione modelCollocazione = (ModelCollocazione) o;
    return Objects.equals(codiceCollocazione, modelCollocazione.codiceCollocazione) &&
        Objects.equals(descrizioneCollocazione, modelCollocazione.descrizioneCollocazione) &&
        Objects.equals(codiceAzienda, modelCollocazione.codiceAzienda) &&
        Objects.equals(descrizioneAzienda, modelCollocazione.descrizioneAzienda);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceCollocazione, descrizioneCollocazione, codiceAzienda, descrizioneAzienda);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelCollocazione {\n");
    
    sb.append("    codiceCollocazione: ").append(toIndentedString(codiceCollocazione)).append("\n");
    sb.append("    descrizioneCollocazione: ").append(toIndentedString(descrizioneCollocazione)).append("\n");
    sb.append("    codiceAzienda: ").append(toIndentedString(codiceAzienda)).append("\n");
    sb.append("    descrizioneAzienda: ").append(toIndentedString(descrizioneAzienda)).append("\n");
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
}
