/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatoLightExt } from "./allegato-light-ext";
import { BaseModel } from "./base-model";
import { PianoFinanziario } from "./piano-finanziario";
import { PresavisioneSpesa } from "./presavisione-spesa";

export interface Intervento extends BaseModel {
  //DATI PRECEDENTI
  intId: number;
  intCod: string;
  intTitolo: string;
  intAnno: number;
  intCup: string;
  intImporto: number;
  intCodicNsis: string;
  //intDgAziendaleApprovazione: string;     //non usato/CORRISPONDE A allegatoDeliberaApprovazione
  //intDgAziendaleApprovazioneData: number; //non usato/CORRISPONDE A allegatoDeliberaApprovazione
  //intDgRegionaleApprovazione: string;     //SOSTITUITO/CORRISPONDE A allegatoDgrApprovazione
  //intDgRegionaleApprovazioneData: number; //SOSTITUITO/CORRISPONDE A allegatoDgrApprovazione
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
  //intFormaRealizzativaId: number;     //non piu usato/ELIMINATO
  intReferentePraticaFax: string;       //non piu usato/NON ELIMINABILE PER IL MOMENTO
  //intStrTipoId: number;               //non piu usato/ ELIMINATO
  //intQuadranteId: number;             //SOSTITUITO/CORRISPONDE A quadranteId
  listaIntFinalitaId: number[];       //SOSTITUITO/CORRISPONDE A finalita
  listaIntObiettivoId: number[];      //SOSTITUITO/CORRISPONDE A obiettivi
  listaIntCategoriaId: number[];      //SOSTITUITO/CORRISPONDE A categorie
  listaIntStatoId: number[];            //non piu usato/ok
  listaIntStatoProgettualeId: number[]; //SOSTITUITO/CORRISPONDE A statiProgettuali
  listaIntTipoId: number[];           //SOSTITUITO/CORRISPONDE A tipi
  listaIntAppaltoTipoId: number[];    //SOSTITUITO/CORRISPONDE A appaltiTipo
  listaIntContrattoId: number[];      //SOSTITUITO/CORRISPONDE A contrattiTipo
  listaIntStrutturaId: number[];      //SOSTITUITO/CORRISPONDE A interventiStruttura
  //listaAllegatoMonitoraggioId: number[]; //non piu usato/COMMENTATO HTML
  //allegatoRichiestaAmmissioneFinanziamentoId: number; //SOSTITUITO/CORRISPONDE A rIntModuloId
  //intMacroareaId: number;             //non piu usato/ELIMINATO
  enteId: number;

  //DATI NUOVI
  rIntModuloStatoAId: number;  
  rIntModuloAId: number;
  rIntModuloId: number;
  moduloTipo: string;
  allegatoProvvedimentoAziendaleApprovazione: AllegatoLightExt[];
  allegatoRelazioneTecnica: AllegatoLightExt[];
  allegatoNullaOsta: AllegatoLightExt[];
  allegatoDecretoMinisteriale: AllegatoLightExt[];
  noteRespingimentoModulo: string;
  intRupCognome: string;
  intRupNome: string;
  intRupCf: string;
  quadranteId: number;
  intPrioritaAnno: number;
  intPriorita: number;
  intSottopriorita: string;
  intFinanziabile: boolean;
  copiatoDaIntId: number;
  copiatoDaEnteId: number;
  copiatoDaAnno: number;
  copiatoDaCodicePgmeas: string;
  appaltoIntegrato: boolean;
  progettazioneGg: number;
  affidamentoLavoriGg: number;
  esecuzioneLavoriGg: number;
  collaudoGg: number;
  interventiStruttura: number[];
  obiettivi: number[];
  finalita: number[];
  tipi: number[]; 
  descrizioniAttrezzature: number[];
  categorie: number[];
  contrattiTipo: number[];
  appaltiTipo: number[];
  statiProgettuali: number[]; 
  stato: number; //listaIntStatoId
  statoNota: string;
  note: string;
  previsioniDiSpesa: PresavisioneSpesa[];
  pianiFinanziari: PianoFinanziario[];
  allegatoDeliberaApprovazione: AllegatoLightExt[]; //NON VIENE USATO
  allegatoDgrApprovazione: AllegatoLightExt[]; 
  allegatoDgrPropostaCR: AllegatoLightExt[];
  allegatoDcrApprovazione: AllegatoLightExt[];
  allegatiDeterminazioniDirigenziali: AllegatoLightExt[];


}
