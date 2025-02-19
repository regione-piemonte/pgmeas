/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpContext, HttpParams, HttpResponse } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {
  Allegato,
  AllegatoLightExt,
  CompilaMonitoraggio,
  FileBase64,
  Finanziamento,
  FinanziamentoLiquidazione,
  FinanziamentoLiquidazioneRichiesta,
  Intervento,
  InterventoFinanziamentoPrevSpesa,
  InterventoGaraAppalto,
  InterventoStruttura,

  Provvedimento,
  Struttura
} from '@pgmeas-library/model';
import { FinanziamentoQuadroEconomico } from '@pgmeas-library/model/src/finanziamento-quadro-economico';
import { ERROR_HANDLING_MODE } from '@pgmeas-library/rest';
import { StrutturaInserimentoIntervento } from '@pgmeas-library/model/src/struttura-inserimento-intervento';
import { StrutturaInserimentoInterventoCopy } from '@pgmeas-library/model/src/struttura-inserimento-intervento copy';
import { InterventoDTO } from '@pgmeas-library/model/src/intervento-salvataggio';
import { InterventoVisualizza } from '@pgmeas-library/model/src/intervento-visualizza';
import { InterventoModificaRegioneDTO } from '@pgmeas-library/model/src/intervento-modifica-regione';
import { GestisciProgrammazione } from '@pgmeas-library/model/src/gestisci-programmazione';
import { ProgrammazioneEnti } from '@pgmeas-library/model/src/programmazione-enti';
import { ProrogaEnte } from '@pgmeas-library/model/src/proroga-ente';
import { ProrogaTutteAsr } from '@pgmeas-library/model/src/proroga-tutte-asr';

import {ENVIRONMENT} from '@pgmeas-library/src';
import {Environments} from '@pgmeas-library/contracts';
import { Respingimento } from '@pgmeas-library/model/src/respingimento';
import { RintrventoModulo } from '@pgmeas-library/model/src/rintervento-modulo';
import { RichiestaAmmissioneFinanziamentoModifica, RichiestaAmmissioneFinanziamentoModificaRegione, RichiestaAmmissioneFinanziamentoNew } from '@pgmeas-library/model/src/richiesta-ammissione-finanziamento-new';
import { StrutturaNonCensita } from '@pgmeas-library/model/src/struttura-non-censita';
import { InterventiFilter } from '@pgmeas-library/model/src/interventi-filter';
import { RicercaInterventiResult } from '@pgmeas-library/model/src/ricerca-interventi-result';
import { PgmeasContextEnum } from '../utils/pgmeas_context_enum';


const  PGMEAS_CONTEX_TO_URL_MAP: Record<PgmeasContextEnum, string> = {
  [PgmeasContextEnum.RICERCA_INTERVENTI]: '',
  [PgmeasContextEnum.PROGRAMMAZIONE_INTERVENTI]: 'interventi',
  [PgmeasContextEnum.GESTIONE_INTERVENTI]: 'moduloA',
  [PgmeasContextEnum.MONITORAGGIO]: 'moduloA',
};

@Injectable({ providedIn: 'root' })
export class ProjectApiService {
  private headers: {
    [k: string]: string;
  };

  constructor(
    private httpClient: HttpClient,
    @Inject(ENVIRONMENT) private env: Environments) {
    this.initHeaders();
  }

  private initHeaders() {
    this.headers = this.env.production ? {
      Authorization: 'Basic YXBpcmlscHJlcHJvZDphcGlyaWxldmF6JjAx'
    } : {
      Authorization: 'Basic YXBpcmlscHJlcHJvZDphcGlyaWxldmF6JjAx',
      'Shib-Iride-IdentitaDigitale': this.env.secrets.ShibIrideIdentitaDigitale
    };
  }

  getProvvedimentoList(): Observable<Provvedimento[]> {
    return this.httpClient.get<Provvedimento[]>(
      `~/provvedimenti`,
      { headers: this.headers,context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  getInterventoFinanziamentoPrevSpesaListByIntervento(intId: number): Observable<InterventoFinanziamentoPrevSpesa[]> {
    return this.httpClient.get<InterventoFinanziamentoPrevSpesa[]>(
      `~/intervento/finanziamentoPrevSpesa/${intId}`,
      { headers: this.headers,context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  getFinanziamentoLiquidazioneListByIntervento(intId: number): Observable<FinanziamentoLiquidazione[]> {
    return this.httpClient.get<FinanziamentoLiquidazione[]>(
      `~/intervento/finanziamentoLiquidazione/${intId}`,
      { headers: this.headers,context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  getFinanziamentoLiquidazioneRichiestaListByIntervento(intId: number): Observable<FinanziamentoLiquidazioneRichiesta[]> {
    return this.httpClient.get<FinanziamentoLiquidazioneRichiesta[]>(
      `~/intervento/finanziamentoLiquidazioneRichiesta/${intId}`,
      { headers: this.headers,context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  // getFinanziamentoList(): Observable<Finanziamento[]> {
  //   return this.httpClient.get<Finanziamento[]>(
  //     `~/intervento/finanziamenti`,
  //     { headers: this.headers,context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
  //   );
  // }

  getFinanziamentoListByIntervento(intId: number): Observable<Finanziamento[]> {
    return this.httpClient.get<Finanziamento[]>(
      `~/intervento/finanziamenti/${intId}`,
      { headers: this.headers,context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }
  //TODO ELIMINARE SUL RIFACIMENTO DEL MODULO_CS
  getInterventoStrutturaListByIntervento(intId: number): Observable<InterventoStruttura[]> {
    return this.httpClient.get<InterventoStruttura[]>(
      `~/intervento/struttura/${intId}`,
      { headers: this.headers,context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  getInterventoGaraAppaltoListByIntervento(intId: number): Observable<InterventoGaraAppalto[]> {
    return this.httpClient.get<InterventoGaraAppalto[]>(
      `~/intervento/garaAppalto/${intId}`,
      { headers: this.headers,context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  getInterventoAllegatoListByIntervento(intId: number): Observable<Allegato[]> {
    return this.httpClient.get<Allegato[]>(
      `~/intervento/allegato/${intId}`,
      { headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar')
       }
    );
  }

  // getInterventoList(): Observable<Intervento[]> {
  //   return this.httpClient.get<Intervento[]>(
  //     `~/intervento/interventi`,
  //     { headers: this.headers,
  //       context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar')
  //      }
  //   );
  // }

  // getInterventoListByAnno(anno: number): Observable<Intervento[]> {
  //   return this.httpClient.get<Intervento[]>(
  //     `~/intervento/interventi/${anno}`,
  //     { headers: this.headers,
  //       context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar')
  //      }
  //   );
  // }

  //NUOVI SERVIZI. INIZIO
  getModuloAInterventoDetail(id: number): Observable<Intervento> {
    return this.httpClient.get<Intervento>(
      `~/modulo/A/${id}`,
      { headers: this.headers }
    );
  }

  getModuloAInterventoStrutturaDetail(id: number): Observable<InterventoStruttura[]> {
    return this.httpClient.get<InterventoStruttura[]>(
      `~/modulo/A/${id}/interventiStruttura`,
      { headers: this.headers }
    );
  }

  postAggiornaAmmissioneFinanziamento(richiestaAmmissioneFinanziamento: RichiestaAmmissioneFinanziamentoNew): Observable<RintrventoModulo> {
    console.log("postAggiornaAmmissioneFinanziamento: INIZIO");
    return this.httpClient.post<RintrventoModulo>(
      `~/modulo/A`,
      richiestaAmmissioneFinanziamento,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }

  putAggiornaAmmissioneFinanziamentoRegione(id:number,richiestaAmmissioneFinanziamento: RichiestaAmmissioneFinanziamentoModificaRegione): Observable<RintrventoModulo> {
    console.log("postAggiornaAmmissioneFinanziamento: INIZIO");
    return this.httpClient.put<RintrventoModulo>(
      `~/modulo/A/${id}/datiRegione`,
      richiestaAmmissioneFinanziamento,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }

  putAggiornaAmmissioneFinanziamento(id:number,richiestaAmmissioneFinanziamento: RichiestaAmmissioneFinanziamentoModifica): Observable<RintrventoModulo> {
    console.log("postAggiornaAmmissioneFinanziamento: INIZIO");
    return this.httpClient.put<RintrventoModulo>(
      `~/modulo/A/${id}`,
      richiestaAmmissioneFinanziamento,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }

  //NUOVI SERVIZI: FINE

  getStruttureInterventoList(intId: number, copia: boolean): Observable<StrutturaInserimentoInterventoCopy[]> {
    const params = new HttpParams().set('copy', copia.toString());
    return this.httpClient.get<StrutturaInserimentoInterventoCopy[]>(
      `~/v2/intervento/${intId}/interventiStruttura`,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
        params: params
      }
    );
  }

  getInterventoListForCopy(anno: number, codice: string, titolo: string, cup: string): Observable<Intervento[]> {
    // Crea un oggetto vuoto
  let params: any = {};

  // Aggiungi solo i parametri che non sono null o undefined
  if (anno !== null && anno !== undefined) {
    params.anno = anno.toString();
  }
  if (codice) {
    params.codice = codice;
  }
  if (titolo) {
    params.titolo = titolo;
  }
  if (cup) {
    params.cup = cup;
  }
    return this.httpClient.get<Intervento[]>(
      `~/v2/intervento/interventi`,
      { headers: this.headers, params, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') } //
    );
  }

  // getInterventoListByAnni(anni: number[]): Observable<Intervento[]> {
  //   return this.httpClient.post<Intervento[]>(
  //     `~/intervento/interventi`,
  //     { anni },
  //     { headers: this.headers,
  //       context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar')
  //      }
  //   );
  // }

  saveIntervento(salvataggio: InterventoDTO): Observable<InterventoDTO> {
    return this.httpClient.post<InterventoDTO>(
      `~/v2/intervento`,
      salvataggio,
      { headers: this.headers, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  editIntervento(salvataggio: InterventoDTO, intId: number): Observable<InterventoDTO> {
    return this.httpClient.put<InterventoDTO>(
      `~/v2/intervento/${intId}`,
      salvataggio,
      { headers: this.headers, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  editInterventoRegione(salvataggio: InterventoModificaRegioneDTO, intId: number): Observable<InterventoModificaRegioneDTO> {
    return this.httpClient.put<InterventoModificaRegioneDTO>(
      `~/v2/intervento/${intId}/datiRegione`,
      salvataggio,
      { headers: this.headers, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  inviaIntervento(intId: number): Observable<Response> {
    return this.httpClient.put<Response>(
      `*/iter/v1/intervento/${intId}/invia`,
      { headers: this.headers, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  approvaIntervento(intId: number): Observable<Response> {
    return this.httpClient.put<Response>(
      `*/iter/v1/intervento/${intId}/approva`,
      { headers: this.headers, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  respingiIntervento(intId: number, respingimento: Respingimento): Observable<Response> {
    return this.httpClient.put<Response>(
      `*/iter/v1/intervento/${intId}/rifiuta`,
      respingimento,
      { headers: this.headers, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  eliminaIntervento(intId: number): Observable<Response> {
    return this.httpClient.put<Response>(
      `*/iter/v1/intervento/${intId}/elimina`,
      { headers: this.headers, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  stampaIntervento(id: number):  Observable<HttpResponse<Blob>> {
    return this.httpClient.get<Blob>(
      `*/moduli/v1/intervento/${id}/stampa`,
      { headers: this.headers,
        observe: 'response',
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
        responseType: 'blob' as 'json' // Assicuriamoci che la risposta sia gestita come un Blob
      }
    );
  }

  respingiRichiestaFinanziamento(rIntModuloAId: number, intId: number, respingimento: Respingimento): Observable<Response> {
    return this.httpClient.put<Response>(
      `*/iter/v1/modulo/A/${rIntModuloAId}/intervento/${intId}/respinge`,
      respingimento,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  approvaRichiestaFinanziamento(rIntModuloAId: number, intId: number): Observable<Response> {
    return this.httpClient.put<Response>(
      `*/iter/v1/modulo/A/${rIntModuloAId}/intervento/${intId}/approva`,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  inviaARegioneRichiestaFinanziamento(rIntModuloAId: number, intId: number): Observable<Response> {
    return this.httpClient.put<Response>(
      `*/iter/v1/modulo/A/${rIntModuloAId}/intervento/${intId}/invia`,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }
 //TODO ELIMINARE SUL RIFACIMENTO DEL MODULO_CS
  getInterventoDetail(id: number): Observable<Intervento> {
    return this.httpClient.get<Intervento>(
      `~/intervento/${id}`,
      { headers: this.headers,   context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }

  getInterventoDetailV2(id: number): Observable<InterventoVisualizza> {
    return this.httpClient.get<InterventoVisualizza>(
      `~/v2/intervento/${id}`,
      { headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar')
       }
    );
  }

  getInterventoAnnoList(): Observable<number[]> {
    return this.httpClient.get<number[]>(
      `~/intervento/anni`,
      { headers: this.headers ,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar')
      }
    );
  }

  compilaMonitoraggio(request: CompilaMonitoraggio): Observable<Allegato> {
    return this.httpClient.post<Allegato>(
      /* '~/intervento/finanziamento/datiMonitoraggio', */
      '*/schedac/datiMonitoraggio',
      request,
      { headers: this.headers, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }
  // //TODO ELIMINARE CAPIRE PERCHE' SUL MODULOA
  getInterventoQuadroEconomico(id: string): Observable<FinanziamentoQuadroEconomico[]> {
    return this.httpClient.get<FinanziamentoQuadroEconomico[]>(
      `~/intervento/quadroeconomico/${id}`,
      {headers: this.headers}
    );
  }

  postPrintPdf(body: any): Observable<FileBase64> {
    const context = new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar');
    return this.httpClient.post<FileBase64>(
      `*/richiestaammissione/moduloa`,
      body,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }

  downloadAllegatoById(id: number): Observable<FileBase64> {
    return this.httpClient.get<FileBase64>(`~/allegato/${id}/download`, {
      headers: this.headers,
      context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
    });
  }

  downloadModuloByRIntModuloIdAndInterventoId(rIntModuloId: number, intId: number): Observable<FileBase64> {
    return this.httpClient.get<FileBase64>(`~/modulo/${rIntModuloId}/intervento/${intId}/download`, {
      headers: this.headers,
      context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
    });
  }


  downloadModuloAById(id: number):  Observable<HttpResponse<Blob>> {
    return this.httpClient.get<Blob>(
      `*/moduli/v1/intervento/${id}/modulo/A/stampa`,
      { headers: this.headers,
        observe: 'response',
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
        responseType: 'blob' as 'json' // Assicuriamoci che la risposta sia gestita come un Blob
      }
    );
  }
  richiestaAmmissioneFinanziamento(
    request: RichiestaAmmissioneFinanziamentoNew
  ): Observable<Allegato> {
    return this.httpClient.post<Allegato>(
      `~/allegato/richiestaAmmissioneFinanziamento`,
      request,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }

  richiestaAmmissioneFinanziamentoLegacyLike(
    request: any,
    allegatoProvAzApp: AllegatoLightExt,
    allegatoRelTec: AllegatoLightExt,
    pdfBase64: string
  ): Observable<Allegato> {
    const finanziamentoList = request.richiestaAmmissioneStruttura
      .flatMap((ras: any) => ras.altriFinanziamentiDisp)
      .map((afd: any) => ({
        finImporto: afd.importo,
        finNote: afd.dettaglio,
        finTipoDetId: afd.tipo.finTipoDetId,
        provTitolo: afd.atto,
      }));
    const interventoStrutturaMap = request.richiestaAmmissioneStruttura.reduce(
      (a: any, c: any) => {
        const dicAppMap = Object.entries(
          c.dichiarazioneAppaltabilita.dichiarazioneAppaltabilitaDisp
        ).reduce(
          (b, [k, v]) => ({
            ...b,
            [k]: {
              intstrDaTreeSelezionata: (v as any).selezionata,
              intstrDaTreeValidazioneData: (v as any).validazioneData,
            },
          }),
          {}
        );
        const quadroEconMap = Object.values(c.quadroEconomico).reduce(
          (b: any, d: any) => ({ ...b, ...d }),
          {}
        );

        return {
          ...a,
          [c.intStr]: {
            intstrParereVincolantePpp: c.intGaraAppalto.parereRegionePPP,  //NO
            intstrParereVincolantePppNprotocollo:
              c.intGaraAppalto.determinaAccertamento,                      //NO
            garaAppaltoCigCodList: c.intGaraAppalto.cig.split(','),        //NO
            intstrPftePrevisioneDurataGg:
              c.pianoCronologico.tempoPrevistoPerPFTE,                     //NO
            intstrGaraPrevisioneDurataGg:
              c.pianoCronologico.tempoPrevistoPerGARA,                     //NO
            intstrProgettoesecutivoPrevisioneDurataGg:
              c.pianoCronologico.tempoPrevistoPerProgettoEsecutivo,        //NO
            intstrLavoriPrevisioneDurataGg:
              c.pianoCronologico.tempoPrevistoPerLavori,                   //NO
            intstrCollaudoPrevisioneDurataGg:
              c.pianoCronologico.tempoPrevistoPerCollaudo,                 //NO
            dicAppMap,                                                     //CONFERMATO
            quadroEconMap,                                                 //CONFERMATO
            intstrResponsabileProcedimentoCognome:
              c.respProcedimentoCognome,                                   //NO
            intstrResponsabileProcedimentoNome: c.respProcedimentoNome,    //NO
          },
        };
      },
      {}
    );

    const newRequest = {
      intId: request.intId,                                               //CONFERMATO
      //allegatoOggetto: request.oggetto,                                 //NO
      moduloTipo: request.oggetto,                                        //NEW
      allegatoProvAzApp,                                                  //NUOVO
      allegatoRelTec,                                                     //NUOVO
      intStatoProgIdList: request.listaStatoId,                           //CONFERMATO
      interventoStrutturaMap,                                             //DA CAMBIARE I CAMPI IL DTO E' LO STESSO
      finanziamentoList,                                                  //DA CAMBIARE IN PianoFinanziarioSave
      intDirettoreGeneraleCognome: request.dirGeneraleCognome,            //NO
      intDirettoreGeneraleNome: request.dirGeneraleNome,                  //NO
      intCommissarioCognome: request.intCommissarioCognome,               //NO
      intCommissarioNome: request.intCommissarioNome,                     //NO
      intReferentePraticaCognome: request.refPratica.refPraticaCognome,   //NO
      intReferentePraticaNome: request.refPratica.refPraticaNome,         //NO
      intReferentePraticaEmail: request.refPratica.refPraticaEmail,       //NO
      intReferentePraticaFax: request.refPratica.refPraticaFax,           //NO
      intReferentePraticaTelefono: request.refPratica.refPraticaTel,      //NO
      pdfBase64,
      note: ''                                                      //NO
    };

    return this.richiestaAmmissioneFinanziamento(newRequest);
  }

  salvataggioAmmissioneFinanziamento(
    data:any, intervento: Intervento, interventoStruttura: any,
    allegatoProvAzApp: AllegatoLightExt|null,
    allegatoRelTec: AllegatoLightExt|null
  ): Observable<RintrventoModulo> {
    console.log("********************************************");
    console.log("aggiornamentoAmmissioneFinanziamento: INIZIO");
    console.log("allegatoProvAzApp");
    if(allegatoProvAzApp){
      console.log("base64: "+allegatoProvAzApp.base64);
      console.log("fileNameUser: "+allegatoProvAzApp.fileNameUser);
      console.log("fileType: "+allegatoProvAzApp.fileType);
      console.log("idAllegato: "+allegatoProvAzApp.idAllegato);
      console.log("intAllegatoData: "+allegatoProvAzApp.intAllegatoData);
      console.log("intAllegatoNumero: "+allegatoProvAzApp.intAllegatoNumero);
    }
    console.log("allegatoRelTec");
    if(allegatoRelTec){
      console.log("base64: "+allegatoRelTec.base64);
      console.log("fileNameUser: "+allegatoRelTec.fileNameUser);
      console.log("fileType: "+allegatoRelTec.fileType);
      console.log("idAllegato: "+allegatoRelTec.idAllegato);
      console.log("intAllegatoData: "+allegatoRelTec.intAllegatoData);
      console.log("intAllegatoNumero: "+allegatoRelTec.intAllegatoNumero);
    }
    console.log("intId: "+intervento.intId);
    console.log("moduloTipo: "+intervento.moduloTipo);
    console.log("intStatoProgIdList: "+data.appalto.statoProgetto);

    console.log("note: "+intervento.note);
    console.log(interventoStruttura);
    console.log("salvataggio: FINE");
    console.log("********************************************");


    const newRequest = {
      intId: intervento.intId,
      moduloTipo: intervento.moduloTipo,
      intStatoProgIdList: data.appalto.statoProgetto,
      allegatoProvAzApp:  allegatoProvAzApp,
      allegatoRelTec: allegatoRelTec,
      note: intervento.note,
      interventoStrutturaMap: interventoStruttura,
      intDirettoreGeneraleCognome: intervento.intDirettoreGeneraleCognome,
      intDirettoreGeneraleNome: intervento.intDirettoreGeneraleNome,
      intDirettoreGeneraleCf: intervento.intDirettoreGeneraleCf,
      intCommissarioCognome : intervento.intCommissarioCognome,
	    intCommissarioNome: intervento.intCommissarioNome,
	    intCommissarioCf: intervento.intCommissarioCf,
      intReferentePraticaCognome: intervento.intReferentePraticaCognome,
      intReferentePraticaNome: intervento.intReferentePraticaNome,
      intReferentePraticaCf: intervento.intReferentePraticaCf,
      intReferentePraticaTelefono: intervento.intReferentePraticaTelefono,
      intReferentePraticaEmail: intervento.intReferentePraticaEmail,
      intRupNome: intervento.intRupNome,
      intRupCognome: intervento.intRupCognome ,
      intRupCf: intervento. intRupCf
    }

    console.log(newRequest);

    return this.postAggiornaAmmissioneFinanziamento(newRequest);
  }
  aggiornamentoAmmissioneFinanziamento(
    data:any, intervento: Intervento, interventoStruttura: any,
    allegatoProvAzApp: AllegatoLightExt|null,
    allegatoRelTec: AllegatoLightExt|null,
    allegatoProvAzAppToDelete: AllegatoLightExt|null,
    allegatoRelTecToDelete: AllegatoLightExt|null
  ): Observable<RintrventoModulo> {
    console.log("********************************************");
    console.log("aggiornamentoAmmissioneFinanziamento: INIZIO");
    console.log("allegatoProvAzApp");

    console.log("intId: "+intervento.intId);
    console.log("moduloTipo: "+intervento.moduloTipo);
    console.log("intStatoProgIdList: "+data.appalto.statoProgetto);

    console.log("note: "+intervento.note);
    console.log(interventoStruttura);
    console.log("aggiornamentoAmmissioneFinanziamento: FINE");
    console.log("********************************************");

    const newRequest = {
      intId: intervento.intId,
      moduloTipo: intervento.moduloTipo,
      intStatoProgIdList: data.appalto.statoProgetto,
      allegatoProvAzApp:  allegatoProvAzApp,
      allegatoRelTec: allegatoRelTec,
      allegatoProvAzAppToDelete: allegatoProvAzAppToDelete,
      allegatoRelTecToDelete: allegatoRelTecToDelete,
      note: intervento.note,
      interventoStrutturaMap: interventoStruttura,
      intDirettoreGeneraleCognome: intervento.intDirettoreGeneraleCognome,
      intDirettoreGeneraleNome: intervento.intDirettoreGeneraleNome,
      intDirettoreGeneraleCf: intervento.intDirettoreGeneraleCf,
      intCommissarioCognome : intervento.intCommissarioCognome,
	    intCommissarioNome: intervento.intCommissarioNome,
	    intCommissarioCf: intervento.intCommissarioCf,
      intReferentePraticaCognome: intervento.intReferentePraticaCognome,
      intReferentePraticaNome: intervento.intReferentePraticaNome,
      intReferentePraticaCf: intervento.intReferentePraticaCf,
      intReferentePraticaTelefono: intervento.intReferentePraticaTelefono,
      intReferentePraticaEmail: intervento.intReferentePraticaEmail,
      intRupNome: intervento.intRupNome,
      intRupCognome: intervento.intRupCognome ,
      intRupCf: intervento. intRupCf
    }

    return this.putAggiornaAmmissioneFinanziamento(intervento.rIntModuloId,newRequest);
  }

  aggiornamentoAmmissioneFinanziamentoRegione(
    intervento: Intervento,
    allegatoNullaOsta: AllegatoLightExt|null,
    allegatoDecretoMinisteriale: AllegatoLightExt|null,
    allegatoNullaOstaToDelete: AllegatoLightExt|null,
    allegatoDecretoMinisterialeToDelete: AllegatoLightExt|null
  ): Observable<RintrventoModulo> {


    const newRequest = {
      intId: intervento.intId,
      nullaOstaNew: allegatoNullaOsta,
      nullaOstaToDelete: allegatoNullaOstaToDelete,
      decretoMinisterialeNew: allegatoDecretoMinisteriale,
      decretoMinisterialeToDelete: allegatoDecretoMinisterialeToDelete
    }

    return this.putAggiornaAmmissioneFinanziamentoRegione(intervento.rIntModuloId,newRequest);
  }

  salvaProgrammazione(
    request: GestisciProgrammazione
  ): Observable<GestisciProgrammazione[]> {
    return this.httpClient.post<GestisciProgrammazione[]>(
      `~/programmazione`,
      request,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }

  getProgrammazioneByAnno(
    anno: number
  ): Observable<ProgrammazioneEnti> {
    return this.httpClient.get<ProgrammazioneEnti>(
      `~/programmazione/${anno}`,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }

  salvaProrogaPerEnte(
    anno: number,
    enteProroga: ProrogaEnte
  ): Observable<ProgrammazioneEnti> {
    return this.httpClient.put<ProgrammazioneEnti>(
      `~/programmazione/${anno}/${enteProroga.enteCodEsteso}`,
      enteProroga,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }

  salvaProrogaAll(
    anno: number,
    proroga: ProrogaTutteAsr
  ): Observable<ProgrammazioneEnti> {
    return this.httpClient.put<ProgrammazioneEnti>(
      `~/programmazione/${anno}`,
      proroga,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }

  censisciStruttura(
    request: StrutturaNonCensita
  ): Observable<StrutturaNonCensita[]> {
    return this.httpClient.post<StrutturaNonCensita[]>(
      `~/struttura`,
      request,
      {
        headers: this.headers,
        context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar'),
      }
    );
  }
  // RICERCA INTERVENTO V2



  getInterventiByAllFilters(interventiFilter: InterventiFilter, contesto:PgmeasContextEnum): Observable<RicercaInterventiResult[]> {
    const endpoint =PGMEAS_CONTEX_TO_URL_MAP[contesto]||'';
    return this.httpClient.post<RicercaInterventiResult[]>(
      `~/ricerca/${endpoint}`,
      interventiFilter,
      { headers: this.headers, context: new HttpContext().set(ERROR_HANDLING_MODE, 'snackbar') }
    );
  }
}
