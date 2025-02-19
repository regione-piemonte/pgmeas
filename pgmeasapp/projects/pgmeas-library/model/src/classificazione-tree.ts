/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Tipologica } from "./tipologica";

export interface ClassificazioneTree extends Tipologica {
  classifTreeId: number;
  classifTsId: number;
  classifId: number;
  classifIdPadre: number;
  classifTreeLivello: number;
  classifTreeOrdine: number;
  selezionabile: boolean;
  descrizione: string;
  spazio: string;
  selezionato: boolean;
  livello: number;
  valoreNumerico: string;
  // Campi aggiuntivi opzionali
  classifElemCod?: string;
  classifTreeImportoDecimali?: number;
  classifTreeConImporto?: boolean;
  classifTreeEditabile?: boolean;
  classifSimbolo?: string,
  classifEtichetta?: string
}
