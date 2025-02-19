/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Funzionalita } from "./funzionalita";
import { Programmazione } from "./programmazione";

export interface UserInfo {
  codiceFiscale: string;
  cognome: string;
  nome: string;
  ruolo: string;
  profilo: string;
  enteId: number;
  codiceAzienda: string;
  codiceColl: string;
  descAzienda: string;
  descCollocazione: string;
  idQuadrante: string;
  descrQuadrante: string;
  listaFunzionalita: Funzionalita[];
  programmazione: Programmazione;
}
