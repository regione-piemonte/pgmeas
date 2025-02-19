/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";

export interface InterventoCopy extends BaseModel {
  intId: number;
  intCod: string;
  intTitolo: string;
  intAnno: number;
  intCup: string;
  intImporto: number;
  intCodicNsis: string;
  intDirettoreGeneraleCognome: string;
  intDirettoreGeneraleNome: string;
  intDirettoreGeneraleCf: string;
  intCommissarioCognome: string;
  intCommissarioNome: string;
  intCommissarioCf: string;
  intReferentePraticaCognome: string;
  intReferentePraticaNome: string;
  intReferentePraticaTelefono: string;
  intReferentePraticaFax: string;
  intReferentePraticaEmail: string;
  intReferentePraticaCf: string;
  intRupCognome: string;
  intRupNome: string;
  intRupCf: string | null;
  quadranteId: number;
  enteId: number;
  intPrioritaAnno: number | null;
  intPriorita: number;
  intSottopriorita: string;
  intFinanziabile: boolean | null;
  copiatoDaIntId: number | null;
  copiatoDaEnteId: number | null;
  appaltoIntegrato: boolean | null;
  progettazioneGg: number | null;
  affidamentoLavoriGg: number | null;
  esecuzioneLavoriGg: number | null;
  collaudoGg: number | null;
  interventiStruttura: number[];
  obiettivi: number[];
  finalita: number[];
  tipi: number[];
  descrizioniAttrezzature: number[];
  categorie: number[];
  contrattiTipo: number[];
  appaltiTipo: number[];
  statiProgettuali: number[];
  stato: number;
  statoNota: string | null;
  previsioniDiSpesa: PrevisioneDiSpesa[];
  pianiFinanziari: PianoFinanziario[];
  allegatoDeliberaApprovazione: Allegato[] | null;
  allegatoDgrApprovazione: Allegato[] | null;
  allegatoDgrPropostaCR: Allegato[] | null;
  allegatoDcrApprovazione: Allegato[] | null;
  allegatiDeterminazioniDirigenziali: Allegato[] | null;
  note: string;
}

export interface PrevisioneDiSpesa {
  anno: number;
  importo: number;
}

export interface PianoFinanziario {
  tipologiaId: number;
  tipologia: string;
  tipologiaDettaglioId: number;
  tipologiaDettaglio: string;
  isPrincipale: boolean;
  importo: number;
  percentualeStato: number;
  importoStato: number;
  percentualeRegione: number;
  importoRegione: number;
  percentualeAltro: number;
  importoAltro: number;
}

export interface Allegato {
  idAllegato: number;
  intAllegatoNumero: string;
  intAllegatoData: number;
  inteAllegatoDataFormated: Date;
}
