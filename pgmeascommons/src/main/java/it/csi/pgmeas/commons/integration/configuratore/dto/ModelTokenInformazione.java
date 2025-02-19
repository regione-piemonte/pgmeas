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

public class ModelTokenInformazione   {
  private Richiedente richiedente = null;
  private List<ParametroLogin> parametriLogin = new ArrayList<ParametroLogin>();
  private List<Funzionalita> funzionalita = new ArrayList<Funzionalita>();

  /**
   **/
  
  @JsonProperty("richiedente")
  public Richiedente getRichiedente() {
    return richiedente;
  }
  public void setRichiedente(Richiedente richiedente) {
    this.richiedente = richiedente;
  }

  /**
   **/
  
  @JsonProperty("parametri_login")
  public List<ParametroLogin> getParametriLogin() {
    return parametriLogin;
  }
  public void setParametriLogin(List<ParametroLogin> parametriLogin) {
    this.parametriLogin = parametriLogin;
  }

  /**
   **/
  
  @JsonProperty("funzionalita")
  public List<Funzionalita> getFunzionalita() {
    return funzionalita;
  }
  public void setFunzionalita(List<Funzionalita> funzionalita) {
    this.funzionalita = funzionalita;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelTokenInformazione modelTokenInformazione = (ModelTokenInformazione) o;
    return Objects.equals(richiedente, modelTokenInformazione.richiedente) &&
        Objects.equals(parametriLogin, modelTokenInformazione.parametriLogin) &&
        Objects.equals(funzionalita, modelTokenInformazione.funzionalita);
  }

  @Override
  public int hashCode() {
    return Objects.hash(richiedente, parametriLogin, funzionalita);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelTokenInformazione {\n");
    
    sb.append("    richiedente: ").append(toIndentedString(richiedente)).append("\n");
    sb.append("    parametriLogin: ").append(toIndentedString(parametriLogin)).append("\n");
    sb.append("    funzionalita: ").append(toIndentedString(funzionalita)).append("\n");
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
