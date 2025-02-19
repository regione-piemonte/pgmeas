/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Errore   {
  private Integer status = null;
  private String code = null;
  private String title = null;
  private List<ErroreDettaglio> detail = new ArrayList<ErroreDettaglio>();
  private List<String> links = new ArrayList<String>();

  /**
   **/
  
  @JsonProperty("status")
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * Codice univoco di errore interno
   **/
  
  @JsonProperty("code")
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Testo dell&#x27;errore da mostrare all&#x27;utente
   **/
  
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   **/
  
  @JsonProperty("detail")
  public List<ErroreDettaglio> getDetail() {
    return detail;
  }
  public void setDetail(List<ErroreDettaglio> detail) {
    this.detail = detail;
  }

  /**
   **/
  
  @JsonProperty("links")
  public List<String> getLinks() {
    return links;
  }
  public void setLinks(List<String> links) {
    this.links = links;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Errore errore = (Errore) o;
    return Objects.equals(status, errore.status) &&
        Objects.equals(code, errore.code) &&
        Objects.equals(title, errore.title) &&
        Objects.equals(detail, errore.detail) &&
        Objects.equals(links, errore.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, code, title, detail, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Errore {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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
