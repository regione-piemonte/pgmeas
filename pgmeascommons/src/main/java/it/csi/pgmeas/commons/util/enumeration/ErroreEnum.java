/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.util.enumeration;

public enum ErroreEnum {
	MSG_057("MSG-057"),
	MSG_059("MSG-059"),
	MSG_069("MSG-069"),
	MSG_070("MSG-070"),
	MSG_071("MSG-071"),
	MSG_074("MSG-074"),
	MSG_079("MSG-079"),
	MSG_090("MSG-090"),
	MSG_092("MSG-092"),
	MSG_093("MSG-093"),
	MSG_094("MSG-094"),
	MSG_100("MSG-100");
	
	private String code;

	private ErroreEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
