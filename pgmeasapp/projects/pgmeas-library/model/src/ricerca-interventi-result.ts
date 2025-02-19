/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

export interface RicercaInterventiResult {
  total: number;
  intId: number;
  enteId: number;
  intCup: string;
  intCod: string;
  intAnno:number;
  intTitolo: string;
  intStatoId: number;
  intImporto: number;
  azienda: string;
  // finanziamentiRegionali: number;
  // finanziamentiStatali: number;
  // altriFinanziamenti: number;
  creaModuloA:boolean;
	rIntModuloAId: number;
	allegatoRichiestaAmmissioneFinanziamentoId: number;
	allegatoRichiestaAmmissioneFinanziamentoStatoId: number;
}
