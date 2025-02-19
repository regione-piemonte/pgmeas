/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatoDelibera } from "./allegato-delibera";
import { BaseModel } from "./base-model";
import { PrevisioneDiSpesa } from "./intervento-copy";
import { IntAllegatoDelibera } from "./intervento-salvataggio";

export interface InterventoVisualizza extends BaseModel {
  intId: number;
  intCod: string;
  intTitolo: string;
  intAnno: number;
  intCup: string;
  intImporto: number;
  intCodicNsis: string;
  intDgAziendaleApprovazione: string;
  intDgAziendaleApprovazioneData: number;
  intDgRegionaleApprovazione: string;
  intDgRegionaleApprovazioneData: number;
  intDirettoreGeneraleCognome: string;
  intDirettoreGeneraleNome: string;
  intDirettoreGeneraleCf: string;
  intCommissarioCognome: string;
  intCommissarioNome: string;
  intCommissarioCf: string;
  intReferentePraticaCognome: string;
  intReferentePraticaNome: string;
  intReferentePraticaTelefono: string;
  intReferentePraticaEmail: string;
  intReferentePraticaCf: string;
  intRupNome: string;
  intRupCognome: string;
  intRupCf: string;
  intFormaRealizzativaId: number;
  intReferentePraticaFax: string;
  intStrTipoId: number;
  intQuadranteId: number;
  quadranteId: number;
  finalita: number[];
  obiettivi: number[];
  categorie: number[];
  listaIntStatoId: number[];
  statiProgettuali: number[];
  tipi: number[];
  descrizioniAttrezzature: number[];
  appaltiTipo: number[];
  contrattiTipo: number[];
  listaIntStrutturaId: number[];
  listaAllegatoMonitoraggioId: number[];
  allegatoRichiestaAmmissioneFinanziamentoId: number;
  intMacroareaId: number;
  enteId: number;
  copiatoDaAnno: number;
  copiatoDaCodicePgmeas: string;
  copiatoDaIntId :number;
  copiatoDaEnteId:number
  intPrioritaAnno: number;
  intPriorita: number;
  intSottopriorita: string;
  progettazioneGg: number;
  affidamentoLavoriGg: number;
  esecuzioneLavoriGg: number;
  collaudoGg: number;
  appaltoIntegrato: boolean;
  allegatoDeliberaApprovazione: AllegatoDelibera[] | null;
  note: string;
  intFinanziabile?: boolean;
  previsioniDiSpesa?: PrevisioneDiSpesa[];
  pianiFinanziari?: PianoFinanziario[];
  allegatoDgrApprovazione: AllegatoDelibera[] | null;
  allegatoDgrPropostaCR: AllegatoDelibera[] | null;
  allegatoDcrApprovazione: AllegatoDelibera[] | null;
  allegatiDeterminazioniDirigenziali: AllegatoDelibera[] | null;
  stato:number;
  statoNota:string| null;
  rIntModuloId: number|null;
  rIntModuloStatoId: number|null;
}

export interface FinanziamentoImportoTipo {
  finanziamentoImportoTipoId: number;
  finanziamentoImportoTipoCod: string;
  finanziamentoImportoTipoDesc: string;
  finanziamentoImporto: number;
}

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
  finanziamentoImportoTipo: FinanziamentoImportoTipo[];
}
