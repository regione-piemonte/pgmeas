/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { AllegatoLightExt } from "./allegato-light-ext";

export interface RichiestaAmmissioneFinanziamentoNew {
  intId: number;   
  allegatoProvAzApp: AllegatoLightExt|null;
  allegatoRelTec: AllegatoLightExt|null;
  intStatoProgIdList: number[];
  interventoStrutturaMap: { [k: number]: RafInterventoStruttura };
  note: string;
  moduloTipo: string; 
}
export interface RichiestaAmmissioneFinanziamentoModifica {
  intId: number;   
  allegatoProvAzApp: AllegatoLightExt|null;
  allegatoRelTec: AllegatoLightExt|null;
  allegatoProvAzAppToDelete: AllegatoLightExt|null;
  allegatoRelTecToDelete: AllegatoLightExt|null;
  intStatoProgIdList: number[];
  interventoStrutturaMap: { [k: number]: RafInterventoStruttura };
  note: string;
  moduloTipo: string; 
}
export interface RichiestaAmmissioneFinanziamentoModificaRegione {
  intId: number;   
  decretoMinisterialeNew: AllegatoLightExt|null;
  decretoMinisterialeToDelete: AllegatoLightExt|null;
  nullaOstaNew: AllegatoLightExt|null;
  nullaOstaToDelete: AllegatoLightExt|null;
  
}
//ANCORA VALIDO MA CON CAMPI DIVERSI
export interface RafInterventoStruttura {
  intStrAppaltoIntegrato: boolean;
  intStrProgettazioneGg: number;
  intStrAffidamentoLavoriGg: number;
  intStrEsecuzioneLavoriGg: number;
  intStrCollaudoGg: number;
  dicAppMap: { [k: number]: RafDicApp };
  quadroEconMap: { [k: number]: number };
}

//CORRISPONDE A DichAppaltabilita
export interface RafDicApp {
  intstrDaTreeSelezionata: boolean;
  intstrDaTreeValidazioneData: number;
}



//NEW
export interface FinanziamentoImportoTipoSave {
  finanziamentoImportoTipoId: number;
  finanziamentoImporto: number;
}

export interface PianoFinanziarioSave {
	finanziamentoId: number;
  tipologiaDettaglioId: number;
  isPrincipale: boolean;
  importoTotale: number;
	finanziamentoImportoTipo:FinanziamentoImportoTipoSave[];			
}