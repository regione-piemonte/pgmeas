/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

export interface ProgrammazioneEnti {
  anno: number;
  faseInizio: string;
  faseFine: string;
  enti: Ente[];
}

export interface Ente {
  enteId: number;
  enteCodEsteso: string;
  enteDesc: string;
  faseProrogaInizio: string;
  faseProrogaFine: string;
}
