/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {
  AllegatoTipo,
  ClassificazioneTree,
  Ente,
  EnteTipo,
  Fase,
  FinanziamentoImportoTipo,
  FinanziamentoTipo,
  FinanziamentoTipoDet,
  InterventoAppaltoTipo,
  InterventoCategoria,
  InterventoContrattoTipo,
  InterventoFinalita,
  InterventoFormaRealizzativa,
  InterventoObiettivo,
  InterventoStato,
  InterventoStatoProgettuale,
  InterventoTipo,
  InterventoTipoDet,
  Organo,
  Parametro,
  ParametroTipo,
  PianoTriennale,
  ProvvedimentoLivello,
  ProvvedimentoTipo,
  Quadrante,
  Struttura,
  StrutturaTipo
} from '@pgmeas-library/model';
import { TipologicaFinanziamento } from '@pgmeas-library/model/src/tipologicheFinanziamento';
import { ModuloTipo } from '@pgmeas-library/model/src/modulo-tipo';
import { ModuloStato } from '@pgmeas-library/model/src/modulo-stato';

@Injectable({ providedIn: 'root' })
export class RegistryApiService {
  constructor(private httpClient: HttpClient) {}

  getPianoTriennaleList(): Observable<PianoTriennale[]> {
    return this.httpClient.get<PianoTriennale[]>(
      `~/tipologiche/piano`
    );
  }

  getInterventoObiettivoList(): Observable<InterventoObiettivo[]> {
    return this.httpClient.get<InterventoObiettivo[]>(
      `~/tipologiche/intervObiettivo`
    );
  }

  getInterventoFinalitaList(): Observable<InterventoFinalita[]> {
    return this.httpClient.get<InterventoFinalita[]>(
      `~/tipologiche/intervFinalita`
    );
  }

  getInterventoCategoriaList(): Observable<InterventoCategoria[]> {
    return this.httpClient.get<InterventoCategoria[]>(
      `~/tipologiche/intervCategoria`
    );
  }

  getInterventoStatoList(): Observable<InterventoStato[]> {
    return this.httpClient.get<InterventoStato[]>(
      `~/tipologiche/intervStato`
    );
  }

  getInterventoStatoProgettualeList(): Observable<InterventoStatoProgettuale[]> {
    return this.httpClient.get<InterventoStatoProgettuale[]>(
      `~/tipologiche/intervStatoProgettuale`
    );
  }

  getInterventoTipoList(): Observable<InterventoTipo[]> {
    return this.httpClient.get<InterventoTipo[]>(
      `~/tipologiche/intervTipo`
    );
  }

  getInterventoAppaltoTipoList(): Observable<InterventoAppaltoTipo[]> {
    return this.httpClient.get<InterventoAppaltoTipo[]>(
      `~/tipologiche/intervAppaltoTipo`
    );
  }

  getInterventoContrattoTipoList(): Observable<InterventoContrattoTipo[]> {
    return this.httpClient.get<InterventoContrattoTipo[]>(
      `~/tipologiche/intervContrattoTipo`
    );
  }

  getEnteList(): Observable<Ente[]> {
    return this.httpClient.get<Ente[]>(`~/tipologiche/ente`);
  }

  getStrutturaList(): Observable<Struttura[]> {
    return this.httpClient.get<Struttura[]>(
      `~/tipologiche/struttura`
    );
  }

  getStrutturaListByCodiceAzienda(codiceAzienda: string): Observable<Struttura[]> {
    return this.httpClient.get<Struttura[]>(
      `~/tipologiche/struttura/${codiceAzienda}`
    );
  }

  getStrutturaListByInterventoId(interventoId: number): Observable<Struttura[]> {
    return this.httpClient.get<Struttura[]>(
      `~/tipologiche/struttura/intervento/${interventoId}`
    );
  }

  getFinanziamentoTipoList(): Observable<FinanziamentoTipo[]> {
    return this.httpClient.get<FinanziamentoTipo[]>(
      `~/tipologiche/finanzTipo`
    );
  }

  getFinanziamentoImportoTipoList(): Observable<FinanziamentoImportoTipo[]> {
    return this.httpClient.get<FinanziamentoImportoTipo[]>(
      `~/tipologiche/finanzImportoTipo`
    );
  }

  getFinanziamentoTipoDetList(): Observable<FinanziamentoTipoDet[]> {
    return this.httpClient.get<FinanziamentoTipoDet[]>(
      `~/tipologiche/finanzTipoDet`
    );
  }

  getQuadranteList(): Observable<Quadrante[]> {
    return this.httpClient.get<Quadrante[]>(
      `~/tipologiche/quadrante`
    );
  }

  getParametroList(): Observable<Parametro[]> {
    return this.httpClient.get<Parametro[]>(
      `~/tipologiche/parametro`
    );
  }

  getParametroTipoList(): Observable<ParametroTipo[]> {
    return this.httpClient.get<ParametroTipo[]>(
      `~/tipologiche/parametroTipo`
    );
  }

  getAllegatoTipoList(): Observable<AllegatoTipo[]> {
    return this.httpClient.get<AllegatoTipo[]>(
      `~/tipologiche/tipoAllegato`
    );
  }

  getInterventoFormaRealizzativaList(): Observable<InterventoFormaRealizzativa[]> {
    return this.httpClient.get<InterventoFormaRealizzativa[]>(
      `~/tipologiche/intervFormaRealizzativa`
    );
  }

  getClassificazioneTreeList(tipo: string): Observable<ClassificazioneTree[]> {
    return this.httpClient.get<ClassificazioneTree[]>(
      `~/tipologiche/classifTree/${tipo}`
    );
  }

  getEnteTipoList(): Observable<EnteTipo[]> {
    return this.httpClient.get<EnteTipo[]>(
      `~/tipologiche/enteTipo`
    );
  }

  getFaseList(): Observable<Fase[]> {
    return this.httpClient.get<Fase[]>(
      `~/tipologiche/fase`
    );
  }

  getInterventoTipoDetList(tipo:string): Observable<InterventoTipoDet[]> {
    return this.httpClient.get<InterventoTipoDet[]>(
      `~/tipologiche/intervTipoDett/${tipo}`
    );
  }

  getOrganoList(): Observable<Organo[]> {
    return this.httpClient.get<Organo[]>(
      `~/tipologiche/organo`
    );
  }

  getStrutturaTipoList(): Observable<StrutturaTipo[]> {
    return this.httpClient.get<StrutturaTipo[]>(
      `~/tipologiche/strutturaTipo`
    );
  }

  getProvvedimentoLivelloList(): Observable<ProvvedimentoLivello[]> {
    return this.httpClient.get<ProvvedimentoLivello[]>(
      `~/tipologiche/provvedimentoLivello`
    );
  }

  getProvvedimentoTipoList(): Observable<ProvvedimentoTipo[]> {
    return this.httpClient.get<ProvvedimentoTipo[]>(
      `~/tipologiche/provvedimentoTipo`
    );
  }

  getTipologicheFinanziamento(id: number): Observable<TipologicaFinanziamento[]> {
    return this.httpClient.get<TipologicaFinanziamento[]>(
      `~/tipologiche/finanziamento/${id}`
    );
  }

  getModuloTipoList(): Observable<ModuloTipo[]> {
    return this.httpClient.get<ModuloTipo[]>(
      `~/tipologiche/moduloTipo`
    );
  }

  getModuloStatoList(): Observable<ModuloStato[]> {
    return this.httpClient.get<ModuloStato[]>(
      `~/tipologiche/moduloStato`
    );
  }
}
