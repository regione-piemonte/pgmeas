/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.dto;

import java.io.Serializable;

public class EnteQuadranteDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5665562098716785836L;
	private Integer enteId;
    private String enteDesc;
    private Integer quadranteId;
    private String quadranteCod;
    private String quadranteDesc;

	public EnteQuadranteDto() {
		super();
	}

	
    // Costruttore
    public EnteQuadranteDto(Integer enteId, String enteDesc, Integer quadranteId, String quadranteCod, String quadranteDesc) {
        this.enteId = enteId;
        this.enteDesc = enteDesc;
        this.quadranteId = quadranteId;
        this.quadranteCod = quadranteCod;
        this.quadranteDesc = quadranteDesc;
    }




	public String getEnteDesc() {
		return enteDesc;
	}


	public void setEnteDesc(String enteDesc) {
		this.enteDesc = enteDesc;
	}




	public String getQuadranteCod() {
		return quadranteCod;
	}


	public void setQuadranteCod(String quadranteCod) {
		this.quadranteCod = quadranteCod;
	}


	public String getQuadranteDesc() {
		return quadranteDesc;
	}


	public void setQuadranteDesc(String quadranteDesc) {
		this.quadranteDesc = quadranteDesc;
	}


	public Integer getEnteId() {
		return enteId;
	}


	public void setEnteId(Integer enteId) {
		this.enteId = enteId;
	}


	public Integer getQuadranteId() {
		return quadranteId;
	}


	public void setQuadranteId(Integer quadranteId) {
		this.quadranteId = quadranteId;
	}
}
