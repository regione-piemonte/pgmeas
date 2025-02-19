/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface Ente extends Tipologica {
  enteId: number;
  enteCodEsteso: string;
  enteRegioneCod: string;
  enteRegioneDesc: string;
  enteCod: string;
  enteDesc: string;
  enteIndirizzo: string;
  enteCap: string;
  enteComune: string;
  enteProvinciaSigla: string;
  enteTelefono: string;
  enteFax: string;
  enteEmail: string;
  enteSitoWeb: string;
  entePartitaIva: string;
  enteTipoId: string;
}
