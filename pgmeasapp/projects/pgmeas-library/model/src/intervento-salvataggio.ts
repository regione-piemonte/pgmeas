/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";
import { PrevisioneDiSpesa } from "./intervento-copy";

export interface InterventoStrutturaDTO {
  intStrId: number | null;
  strId: number;
  intStrTipoIntEdilMap: { [key: number]: boolean };
  intStrImporto: number;
  intStrAppaltoIntegrato: boolean;
  intStrProgettazioneGg: number;
  intStrAffidamentoLavoriGg: number;
  intStrEsecuzioneLavoriGg: number;
  intStrCollaudoGg: number;
  quadroEconMap: { [key: number]: number };
  intstrRespStrComplesNome: string;
  intstrRespStrComplesCognome: string;
  intstrRespStrComplesCf: string | null;
  intstrRespStrSemplNome: string;
  intstrRespStrSemplCognome: string;
  intstrRespStrSemplCf: string | null;
  parerePPP?: Parere;
  parereHTA?: Parere
}

export interface IntAllegatoDelibera {
  idAllegato?: number | null;
  intAllegatoNumero: string | null;
  intAllegatoData: string | null;
  fileNameUser?: string | null;
  fileType?: string | null;
  base64: string | null;
  intAllegatoDataFormatted?: string | null;
}

export interface InterventoDTO extends BaseModel{
  intId: number | null;
  intCodPgmeas: string | null;
  intCodPgmeasOrig: string | null;
  intAnnoOrig: number | null;
  intCopiatoId: number | null;
  intCopiatoEnteId: number | null;
  intAnno: number;
  intTitolo: string;
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
  intReferentePraticaEmail: string;
  intReferentePraticaCf: string;
  intRupNome: string;
  intRupCognome: string;
  intRupCf: string;
  intQuadranteId: number;
  listaIntFinalitaId: number[];
  listaIntObiettivoId: number[];
  listaIntCategoriaId: number[];
  listaIntStatoProgettualeId: number[];
  listaIntTipoId: number[];
  listaIntTipoDetId: number [];
  listaIntAppaltoTipoId: number[];
  listaIntContrattoId: number[];
  intAppaltoIntegrato: boolean;
  intProgettazioneGg: number;
  intAffidamentoLavoriGg: number;
  intEsecuzioneLavoriGg: number;
  intCollaudoGg: number;
  interventoStrutturaList: InterventoStrutturaDTO[];
  intPrioritaAnno: number;
  intPriorita: number;
  intSottopriorita: string;
  intAllegatoDelibera?: IntAllegatoDelibera;
  intAllegatoDeliberaNew?: IntAllegatoDelibera | null;
  intAllegatoDeliberaToDelete?: IntAllegatoDelibera | null;
  intNote: string;
  intFinanziabile?: boolean;
  previsioniDiSpesa?: PrevisioneDiSpesa[];
}

interface Parere {
  parere: boolean | null;
  allegati: any; // You can specify a more precise type if you know the structure of allegati
}
