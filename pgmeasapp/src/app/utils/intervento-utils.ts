/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Finanziamento, FinanziamentoTipo, FinanziamentoTipoDet, InterventoStato, InterventoStruttura, PianoFinanziarioSave, Provvedimento} from '@pgmeas-library/model';
import {Ente} from '@pgmeas-library/model/src/ente';
import {Intervento} from '@pgmeas-library/model/src/intervento';
import {Struttura} from '@pgmeas-library/model/src/struttura';
import {FinanziamentoQuadroEconomico} from '@pgmeas-library/model/src/finanziamento-quadro-economico';
import { InterventoVisualizza } from '@pgmeas-library/model/src/intervento-visualizza';
import { AllegatoLightExt } from '@pgmeas-library/model/src/allegato-light-ext';
const FINANZIAMENTO_TIPO_REGIONALE_COD = 'R';
const FINANZIAMENTO_TIPO_STATALE_COD = 'S';
const FINANZIAMENTO_TIPO_INAIL_COD = 'I';
const FINANZIAMENTO_TIPO_ALTRO_COD = 'A';

export function getAziendaLabel(
  intervento: Intervento,
  enteList: Ente[]
): string {
  const ente = enteList.find((ente) => ente.enteId === intervento.enteId);
  return ente
    ? ente.enteDesc
    : `Nessun ente con identificativo ${intervento.enteId}`;
}

export function getStatoInterventoLabel(intervento: Intervento, interventoStatoList : InterventoStato[]): string{

  if(intervento.listaIntStatoId && intervento.listaIntStatoId[0]){
    const statoInterventoId=intervento.listaIntStatoId[0];
  const stato = interventoStatoList.find((stato)=> stato.intStatoId === statoInterventoId )
    return stato?stato.intStatoDesc:`Nessuno stato con identificativo ${statoInterventoId}`;
  }
  return `Nessuno stato intervento presente`
}

export function getAziendaLabelByEnteId(
  enteId: number,
  enteList: Ente[]
): string {
  const ente = enteList.find((ente) => ente.enteId === enteId);
  return ente
    ? ente.enteDesc
    : `Nessun ente con identificativo ${enteId}`;
}

export function getStatoInterventoByStatoIdLabel(statoInterventoId: number, interventoStatoList : InterventoStato[]): string{

  if( statoInterventoId){
  const stato = interventoStatoList.find((stato)=> stato.intStatoId === statoInterventoId )
    return stato?stato.intStatoDesc:`Nessuno stato con identificativo ${statoInterventoId}`;
  }
  return `Nessuno stato intervento presente`
}

export function getStrutturaLabel(
  intervento: Intervento,
  strutturaList: Struttura[]
): string {
  const struttura = strutturaList.find(
    (struttura) => struttura.enteId === intervento.enteId
  );
  return struttura
    ? struttura.strDenominazione
    : `Nessuna struttura con ente ${intervento.enteId}`;
}

export function getTotaleFinanziamenti(
  intervento: Intervento,
  finanziamentoList: Finanziamento[]
): number {
  return finanziamentoList.filter(f => f.intId === intervento.intId).map(f => f.finImporto).reduce((a, c) => a + c, 0);
}

function getFinanziamenti(
  intervento: Intervento,
  finanziamentoTipoCod: string,
  finanziamentoList: Finanziamento[],
  finanziamentoTipoList: FinanziamentoTipo[],
  finanziamentoTipoDetList: FinanziamentoTipoDet[]
): number {
  let finanziamentoTipo = finanziamentoTipoList.find(ftd => ftd.finTipoCod === finanziamentoTipoCod);
  const finTipoDetIds = finanziamentoTipoDetList.filter(ftd => ftd.finTipoId === finanziamentoTipo?.finTipoId).map(ftd => ftd.finTipoDetId);
  return finanziamentoList.filter(f => f.intId === intervento.intId && finTipoDetIds.includes(f.finTipoDetId)).map(f => f.finImporto).reduce((a, c) => a + c, 0);
}

export function getFinanziamentiRegionali(
  intervento: Intervento,
  finanziamentoList: Finanziamento[],
  finanziamentoTipoList: FinanziamentoTipo[],
  finanziamentoTipoDetList: FinanziamentoTipoDet[]
): number {
  return getFinanziamenti(
    intervento,
    FINANZIAMENTO_TIPO_REGIONALE_COD,
    finanziamentoList,
    finanziamentoTipoList,
    finanziamentoTipoDetList
  );
}

export function getFinanziamentiStatali(
  intervento: Intervento,
  finanziamentoList: Finanziamento[],
  finanziamentoTipoList: FinanziamentoTipo[],
  finanziamentoTipoDetList: FinanziamentoTipoDet[]
): number {
  return getFinanziamenti(
    intervento,
    FINANZIAMENTO_TIPO_STATALE_COD,
    finanziamentoList,
    finanziamentoTipoList,
    finanziamentoTipoDetList
  );
}

export function getAltriFinanziamenti(
  intervento: Intervento,
  finanziamentoList: Finanziamento[],
  finanziamentoTipoList: FinanziamentoTipo[],
  finanziamentoTipoDetList: FinanziamentoTipoDet[]
): number {
  return getFinanziamenti(
    intervento,
    FINANZIAMENTO_TIPO_ALTRO_COD,
    finanziamentoList,
    finanziamentoTipoList,
    finanziamentoTipoDetList
  ) + getFinanziamenti(
    intervento,
    FINANZIAMENTO_TIPO_INAIL_COD,
    finanziamentoList,
    finanziamentoTipoList,
    finanziamentoTipoDetList
  )
}

export function mapFinanziamenti(finanziamenti: any[], tipiFinanziamento: any[], provvedimenti: Provvedimento[]): any[] {
  return finanziamenti.map(finanziamento => {
    const tipoFinanziamento = tipiFinanziamento.find(tipo => tipo.finTipoDetId === finanziamento.finTipoDetId);
    const provvedimento = provvedimenti.find(provvedimento => provvedimento.finTipoDetId === finanziamento.finTipoDetId);
    return {
      ...finanziamento,
      tipoFinanziamento: tipoFinanziamento ? tipoFinanziamento : null,
      provvedimento: provvedimento ? provvedimento : null
    };
  });
}

export function mapPianiFinanziari(pianiFinanziari: PianoFinanziarioSave[]) : any[]{
  console.log("mapPianiFinanziari INIZIO");
  let pianiFinanziariRequest: any[] = [];
  let pianiFinanziariReq: PianoFinanziarioSave[];

  pianiFinanziari.forEach(piano => {
    //let serviceToAdd = piano;

    const serviceToAdd = {
      finanziamentoId: piano.finanziamentoId,
      finanziamentoImportoTipo: piano.finanziamentoImportoTipo,
      importoTotale: piano.importoTotale,
      isPrincipale: piano.isPrincipale
    };

    // serviceToAdd.finanziamentoId = piano.finanziamentoId;
    // serviceToAdd.finanziamentoImportoTipo = piano.finanziamentoImportoTipo;
    // serviceToAdd.importoTotale = piano.importoTotale;
    // serviceToAdd.isPrincipale  = piano.isPrincipale;

    piano.finanziamentoImportoTipo.forEach((importo:any) => {

      const importoToAdd = {
        finanziamentoImporto: importo.finanziamentoImporto,
        finanziamentoImportoTipoId: importo.finanziamentoImportoTipoId
      };


      //let importoToAdd = importo;
      // importoToAdd.finanziamentoImporto = importo.finanziamentoImporto;
      // importoToAdd.finanziamentoImportoTipoId = importo.finanziamentoImportoTipoId;
      serviceToAdd.finanziamentoImportoTipo.push(importoToAdd);
    });
    pianiFinanziariRequest.push(serviceToAdd);
  });
  pianiFinanziariReq =pianiFinanziariRequest;

  pianiFinanziariReq.forEach(piano => {
    console.log("finanziamentoId: "+piano.finanziamentoId);
    console.log("importoTotale: "+piano.importoTotale);
    console.log("isPrincipale: "+piano.isPrincipale);
    console.log("tipologiaDettaglioId: "+piano.tipologiaDettaglioId);
    piano.finanziamentoImportoTipo.forEach((importo:any) => {
      console.log("finanziamentoImporto: "+importo.finanziamentoImporto);
      console.log("finanziamentoImportoTipoId: "+importo.finanziamentoImportoTipoId);
    });
  });
  console.log("mapPianiFinanziari FINE");

  return  pianiFinanziariRequest;
}

export function transformToArray(data: any): any[] {
  return Object.keys(data).map(key => {
    if (data[key] !== null) {
      return data[key];
    } else {
      return {};
    }
  });
}

export function creaDichiarazioneAppaltabilita(dichiarazioneDiAppaltabilita: any[], index: number) {
  console.log("creaDichiarazioneAppaltabilita");
  let dichiarazione = dichiarazioneDiAppaltabilita[index];
  let dichiarazioneAppaltabilita: any = {
    dichiarazioneAppaltabilitaDisp: {}
  };

  for (const key in dichiarazione) {
    if (dichiarazione.hasOwnProperty(key) && key !== 'dataValidazione') {
      dichiarazioneAppaltabilita.dichiarazioneAppaltabilitaDisp[key] = {
        selezionata: dichiarazione[key].selezionata as boolean,
        validazioneData: key === '59' ? new Date(dichiarazione[key].dataValidazione).getTime() : null
      };
    }
  }

  return dichiarazioneAppaltabilita;
}

export function interventiStrutturaRequest(interventiStruttura: InterventoStruttura[]): any {
  //let interventoStrutturaMap: any[] = [];

  let interventoStrutturaInsert;

  interventiStruttura.forEach(intervento => {

  let interventoStruttura = {
        [intervento.intStrId]: {
        intStrAppaltoIntegrato :intervento.appaltoIntegrato,
        intStrProgettazioneGg :intervento.progettazioneGg,
        intStrAffidamentoLavoriGg :intervento.affidamentoLavoriGg,
        intStrEsecuzioneLavoriGg :intervento.esecuzioneLavoriGg,
        intStrCollaudoGg: intervento.collaudoGg,
    quadroEconMap: {
      ["1"]: 10
    },
    dicAppMap: {
      "60": { "intstrDaTreeSelezionata": true, "intstrDaTreeValidazioneData": null,"attoNumero": null},
    }
  }
};
    console.log("INTERVENTO STRUTTURA CONST");
    console.log(interventoStruttura);
    interventoStrutturaInsert=interventoStruttura;
  });

  //return interventoStruttura;
  return  interventoStrutturaInsert;
}

export function allegatoProvvedimentoAziendaleRequestForm(allegati:any){
  if(allegati.allegatoProvvedimentoAziendaleApprovazione == null &&
    allegati.intAllegatoNumero == null &&
    allegati.intAllegatoData == null){
    return null;
  }
  let result:AllegatoLightExt = {
    idAllegato: null,
    intAllegatoNumero: allegati.intAllegatoNumero,
    intAllegatoData: allegati.intAllegatoData,
    fileNameUser: allegati.nomeAllegatoProvvedimentoAziendaleDiApprovazione,
    fileType: allegati.tipoAllegatoProvvedimentoAziendaleDiApprovazione,
    base64: allegati.allegatoProvvedimentoAziendaleApprovazione

  };
  return result;
}

export function allegatoRelazioneTecnicaRequestForm(allegati:any){
  if(allegati.allegatoRelazioneTecnica == null &&
    allegati.intAllegatoNumeroRT == null &&
    allegati.intAllegatoDataRT == null  ){
    return null;
  }
  let result:AllegatoLightExt = {
    idAllegato: null,
    intAllegatoNumero: allegati.intAllegatoNumeroRT,
    intAllegatoData: allegati.intAllegatoDataRT,
    fileNameUser: allegati.nomeAllegatoRelazioneTecnica,
    fileType: allegati.tipoAllegatoRelazioneTecnica,
    base64: allegati.allegatoRelazioneTecnica
  };
  return result;
}

export function allegatoNullaOstaRequestForm(allegati:any){
  if(!allegati.allegatoNullaOsta){
    return null;
  }
  let result:AllegatoLightExt = {
    idAllegato: null,
    intAllegatoNumero: allegati.intAllegatoNumeroNO,
    intAllegatoData: allegati.intAllegatoDataNO,
    fileNameUser: allegati.nomeAllegatoNullaOsta,
    fileType: allegati.tipoAllegatoNullaOsta,
    base64: allegati.allegatoNullaOsta

  };
  return result;
}

export function allegatoDecretoMinisterialeRequestForm(allegati:any){
  if(!allegati.allegatoDecretoMinisteriale){
    return null;
  }
  let result:AllegatoLightExt = {
    idAllegato: null,
    intAllegatoNumero: allegati.intAllegatoNumeroDM,
    intAllegatoData: allegati.intAllegatoDataDM,
    fileNameUser: allegati.nomeAllegatoDecretoMinisteriale,
    fileType: allegati.tipoAllegatoDecretoMinisteriale,
    base64: allegati.allegatoDecretoMinisteriale
  };
  return result;
}

export function interventiStrutturaRequestForm(moduloTipo:String, interventiStruttura: any, dichiarazioneAppaltabilita: any, quadroEconomico: any[]): any {
  let interventoStrutturaInsert= new Map();
  interventiStruttura["interventi"].forEach((intervento:any) => {

      let dichiarazioneAppaltabilitaCurr= new Map()
      if(moduloTipo=="MOD_A"){
        if(dichiarazioneAppaltabilita["treeDA"] && dichiarazioneAppaltabilita["treeDA"]["listaDichiarazioneDiAppaltabilitaModelloA"]){
          dichiarazioneAppaltabilita["treeDA"]["listaDichiarazioneDiAppaltabilitaModelloA"].forEach((appaltabilita:any)=>{
            if(intervento.idStruttura==appaltabilita.idStruttura){

              appaltabilita.listaDichiarazioni.forEach((dichiarazione:any)=>{
                let formattedDate = null;
                if(dichiarazione.dataValidazione){
                  formattedDate = dichiarazione.dataValidazione.toISOString().split('T')[0];
                }
                let dichiarazioneTemp={"intstrDaTreeSelezionata": dichiarazione.intstrDaTreeSelezionata,
                  "intstrDaTreeValidazioneData":formattedDate,
                  "attoNumero":dichiarazione.atto
                }
                dichiarazioneAppaltabilitaCurr.set(dichiarazione.classifTreeId,dichiarazioneTemp)

              })

            }
          })
        }
      }else{
        if(dichiarazioneAppaltabilita["treeDA"] && dichiarazioneAppaltabilita["treeDA"]["listaDichiarazioneDiAppaltabilitaModelloAA"]){
          dichiarazioneAppaltabilita["treeDA"]["listaDichiarazioneDiAppaltabilitaModelloAA"].forEach((appaltabilita:any)=>{
            if(intervento.idStruttura==appaltabilita.idStruttura){
              appaltabilita.listaDichiarazioni.forEach((dichiarazione:any)=>{
                let formattedDate = null;
                console.log("DATA VALIDAZIONE: "+dichiarazione.dataValidazione); 

                if(dichiarazione.dataValidazione){
                  let data =  dichiarazione.dataValidazione;
                  console.log("DATA: "+data);
                  let year = data.getFullYear();
                  let month = String(data.getMonth() + 1).padStart(2, '0');
                  let day = String(data.getDate()).padStart(2, '0');

                  let dataFormattata = `${year}-${month}-${day}`;
                  console.log("DATA FORMATTATA: "+formattedDate);
                  formattedDate = dataFormattata;
                  //formattedDate = dichiarazione.dataValidazione.toISOString().split('T')[0];
                }
                let dichiarazioneTemp={"intstrDaTreeSelezionata": dichiarazione.intstrDaTreeSelezionata,
                  "intstrDaTreeValidazioneData":formattedDate,
                  "attoNumero":dichiarazione.atto
                }
                dichiarazioneAppaltabilitaCurr.set(dichiarazione.classifTreeId,dichiarazioneTemp)
              })
            }
          })
        }
      }

      //assegno i valori del quadro economico per la specifica struttura
      let quadroEconomicoCurr= {}
      if(quadroEconomico && quadroEconomico.length) {
        for(let quadro of quadroEconomico) {
          if(quadro.idStruttura === intervento.idStruttura) {
            quadroEconomicoCurr = quadro.valori;
          }
        }
      }

      console.log(quadroEconomicoCurr);

      let interventoStruttura = {

          intStrAppaltoIntegrato :intervento.appaltoIntegrato,
          intStrProgettazioneGg :intervento.progettazioneGg,
          intStrAffidamentoLavoriGg :intervento.affidamentoLavoriGg,
          intStrEsecuzioneLavoriGg :intervento.esecuzioneLavoriGg,
          intStrCollaudoGg: intervento.collaudoGg,
          quadroEconMap: quadroEconomicoCurr,
          dicAppMap: mapToJson(dichiarazioneAppaltabilitaCurr)

      }
      interventoStrutturaInsert.set(intervento.idStruttura,interventoStruttura)
  })
  return  mapToJson(interventoStrutturaInsert);

}

function mapToJson(map: Map<any, any>): { [key: string]: any } {
  const obj: { [key: string]: any } = {};
  map.forEach((value, key) => {
      // If value is a Map, recursively convert it to JSON
      if (value instanceof Map) {
          obj[key] = mapToJson(value);  // Recursively convert inner Maps
      } else {
          obj[key] = value;
      }
  });
  return obj;
}
