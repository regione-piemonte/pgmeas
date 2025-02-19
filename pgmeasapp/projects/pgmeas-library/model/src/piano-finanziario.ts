/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { FinanziamentoImportoTipoNuovo } from "./finanziamento-importo-tipo-nuovo";

export interface PianoFinanziario {
    tipologiaId: number;
    tipologiaCod: string;
    tipologiaDesc: string;
    tipologiaDettaglioId: number;
    tipologiaDettaglioCod: string;
    tipologiaDettaglioDesc: string;
    finanziamentoId: number;
    isPrincipale: boolean;
    importoTotale: number;
    finanziamentoImportoTipo: FinanziamentoImportoTipoNuovo[];
}