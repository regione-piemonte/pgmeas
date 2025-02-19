/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { BaseModel } from "./base-model";
import { InterventoParere } from "./intervento-parere";

export interface InterventoStruttura extends BaseModel {
  //DATI PRECEDENTI
  intStrId: number;
  intId: number;
  strId: number;
  intstrImporto: number;
  //NON LEGGERLI PIU E SOSTITUIRLI SECONDO ANALISI
  intstrResponsabileCognome: string;                //corrisponde a intstrResponsabileStrutturaComplessaCognome
  intstrResponsabileNome: string;                   //corrisponde a intstrResponsabileStrutturaComplessaNome
  intstrResponsabileProcedimentoCognome: string;    //corrisponde a intstrResponsabileStrutturaSempliceCognome
  intstrResponsabileProcedimentoNome: string;       //corrisponde a intstrResponsabileStrutturaSempliceNome

  //SOSTITUIRLI: COMMENTATI NON HANNO DATO ERRORI
  //intstrParereVincolantePpp: boolean;               //corrisponde a parerePPP
  //intstrParereVincolantePppNprotocollo: string;     //corrisponde a parerePPP
  //intstrParereCabinaDiRegia: boolean;               //corrisponde a parereHta
  //intstrParereCabinaDiRegiaNprotocollo: string;     //corrisponde a parereHta

  intstrAperturaCantiereDataPrevista: number;
  intstrAperturaCantiereDataEffettiva: number;

  //RIMUOVERLI: COMMENTATI NON HANNO DATO ERRORI
  //intstrPrioritaAnno: string;                       //corrisponde a intPrioritaAnno in struttura
  //intstrPriorita: number;                           //corrisponde a intPriorita in struttura
  //intstrSottopriorita: string;                      //corrisponde a intSottopriorita in struttura

  intstrCollaudoDataPrevista: number;
  intstrCollaudoDataEffettiva: number;

  //NON LEGGERLI PIU E SOSTITUIRLI SECONDO ANALISI. I NUOVI SONO DI MENO.
  intstrPftePrevisioneDurataGg: number;             //appaltoIntegrato 
  intstrGaraPrevisioneDurataGg: number;             //progettazioneGg
  intstrCollaudoPrevisioneDurataGg: number;         //affidamentoLavoriGg
  intstrProgettoesecutivoPrevisioneDurataGg: number;//esecuzioneLavoriGg
  intstrLavoriPrevisioneDurataGg: number;           //collaudoGg
  intstrAttivazionePrevisioneDurataGg: number;      

  enteId: number;
  //RIMUOVERLO: COMMENTATO NON HA DATO ERRORE
  //listaIntStatoProgettualeId: number[];             //in piu?/è passato nell'intervento come statiProgettuali

   //DATI NUOVI
   dichiarazioneAppaltabilita: DichiarazioneAppaltabilita;
   parerePPP: InterventoParere;
   parereHta: InterventoParere;
   intstrResponsabileStrutturaComplessaCognome: string;//LEGGERE QUESTI 
   intstrResponsabileStrutturaComplessaNome: string;
   intstrResponsabileStrutturaComplessaCf: string;
   intstrResponsabileStrutturaSempliceCognome: string;
   intstrResponsabileStrutturaSempliceNome: string;
   intstrResponsabileStrutturaSempliceCf: string;
   appaltoIntegrato: boolean;
   progettazioneGg: number;
   affidamentoLavoriGg: number;
   esecuzioneLavoriGg: number;
   collaudoGg: number;
   quadroEconomico: QuadroEonomico;
   interventoEdilizio: InterventoEdilizio;
}

export interface DichiarazioneAppaltabilita {
  [key : string] : { intstrDaTreeSelezionata: boolean; intstrDaTreeValidazioneData: Date; attoNumero: string };
}

export interface QuadroEonomico {
  [key : string] : number;
}

export interface InterventoEdilizio {
  [key : string] : boolean;
}


