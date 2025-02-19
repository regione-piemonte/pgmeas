/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface Struttura extends Tipologica {
  strId: number;
  strCod: string;
  strDenominazione: string;
  strHsp11Cod: string;
  strFimCod: string;
  strBisCod: string;
  strIndirizzo: string;
  strCoordinataX: number;
  strCoordinataY: number;
  enteId: number;
  strPgmeas: boolean;
  strNonCensita: boolean;
  strNuova: boolean;
  strComune: string;
  strDatiCatastali: string;
  note: string;
}
