/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { InterventoFinanziamentoPrevSpesa } from "./intervento-finanziamento-prev-spesa";
import { InterventoStruttura } from "./intervento-struttura";
import { Liquidazione } from "./liquidazione";

export interface CompilaMonitoraggio {
  intId: number;
  enteId: number;
  allegatoOggetto: string;
  listaIntStatoProgettualeId: number[];
  listaIntStruttura: Partial<InterventoStruttura>[];
  listaIntFinanziamentoPrevSpesa: Partial<InterventoFinanziamentoPrevSpesa>[];
  listaFinanziamentoLiquidazioneRichiesta: Partial<Liquidazione>[];
}
