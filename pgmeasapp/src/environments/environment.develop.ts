/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

export const environment = {
  appName: "pgmeas",
  production: true,
  disableSignIn: false,
  baseUrl: 'http://localhost:9090/pgmeasapigateway/proxy/api',
  simpleBaseUrl: 'http://localhost:9090/pgmeasapigateway',
  apiEndpoint: {
  },
  secrets: {
    lcceToken: 'e2afdf87-351f-4d22-a885-693690b9d8e3',
    ShibIrideIdentitaDigitale:'AAAAAA00B77B000F/NOME/COGNOME/SP//1//'
  }
};
