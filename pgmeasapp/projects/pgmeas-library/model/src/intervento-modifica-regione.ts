/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

export interface InterventoModificaRegioneDTO {
  pareri: ParereRegione[]
  finanziabile: boolean;
  previsioniDiSpesa: PrevisioneSpesa[];
  pianiFinanziari: PianoFinanziarioRegione[];
  dgrApprovazioneNew: AllegatoRegioneModifica | null;
  dgrApprovazioneToDelete: AllegatoRegioneModifica | null;
  dgrConsiglioRegionaleNew: AllegatoRegioneModifica | null;
  dgrConsiglioRegionaleToDelete: AllegatoRegioneModifica | null;
  dcrApprovazioneNew: AllegatoRegioneModifica | null;
  dcrApprovazioneToDelete: AllegatoRegioneModifica | null;
  determinazioniDirigenzialiNew: AllegatoRegioneModifica[];
  determinazioniDirigenzialiToDelete: AllegatoRegioneModifica[];
}

export interface ParereRegione {
  intStrId: number; // Aggiunta della proprietà intStrId
  parerePpp: {
    parere: boolean;
    allegatoToDelete: AllegatoRegioneModifica | null;
    allegatoNew: AllegatoRegioneModifica | null;
  };
  parereHta: {
    parere: boolean;
    allegatoToDelete: AllegatoRegioneModifica | null;
    allegatoNew: AllegatoRegioneModifica | null;
  };
}

export interface AllegatoRegioneModifica {
  idAllegato: number | null;
  intAllegatoNumero: string | null;
  intAllegatoData: string | null;
  fileNameUser: string | null;
  fileType: string | null;
  base64: string | null;
}

export interface FinanziamentoImportoTipo {
  finanziamentoImportoTipoId: number;
  finanziamentoImportoTipoCod: string;
  finanziamentoImporto: number;
}

export interface PianoFinanziarioRegione {
  finanziamentoId: number | null;
  tipologiaDettaglioId: number;
  isPrincipale: boolean;
  importoTotale: number;
  finanziamentoImportoTipo: FinanziamentoImportoTipo[];
}

interface PrevisioneSpesa {
  anno: number;
  importo: number;
}

