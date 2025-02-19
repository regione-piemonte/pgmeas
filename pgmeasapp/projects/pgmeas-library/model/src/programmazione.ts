/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface Programmazione extends BaseModel {
  programmazioneAperta: boolean;
  triennio: string;
  annoInserimentIntervento: string;
  dataInizioProgrammazione: string;
  DataFineProgrammazione: string;
  dataFineProroga: string;
}
