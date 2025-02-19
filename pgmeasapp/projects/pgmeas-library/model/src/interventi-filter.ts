/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

export interface InterventiFilter{
  anno: number,
  cup: string|null,
  titolo:string|null,
  codPgmeas: string|null,
  obiettivi: number[],
  finalita: number[],
  categorie: number[],
  stati:number[],
  statiProgettuali: number[],
  tipi: number[],
  appaltiTipo: number[],
  contrattiTipo: number[],
  aziende: number[],
  strutture: number[],
  soloInterventiFinanziati: boolean,
  finanziamenti: number[],
  finanziamentiTipo: number[],
  orderBy: string|null,
  orderDirection: string|null,
  pageNumber: number|null,
  rowPerPage: number|null
}
