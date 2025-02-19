/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface PianoTriennale extends Tipologica {
  pianoId: number;
  pianoCod: string;
  pianoDesc: string;
  annoDa: number;
  annoA: number;
}
