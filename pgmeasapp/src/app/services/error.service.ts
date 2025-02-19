/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Injectable } from '@angular/core';
import {
  MatDialog,
  MatDialogRef,
  MatDialogState,
} from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';
import { ModaleErroreComponent } from '../components/modale-errore/modale-errore.component';
import { FormArray, FormGroup } from '@angular/forms';

const TITOLO = 'Errore generico';
const MESSAGGIO =
  "Attenzione! Il sistema a causa di errori tecnici non ha potuto completare l'operazione. Riprovare più tardi.";
const mappaIntervento: { [key: string]: string } = {
  struttureAggiunte: 'Intervento Struttura non compilato correttamente',
  cup: 'CUP (Codice Unico di Progetto)',
  codInterventoPGMEAS: 'Codice Intervento PGMEAS',
  codInterventoOriginePGMEAS: 'Codice Intervento Origine PGMEAS',
  annoInserimentoInterventoOrigine: 'Anno di Inserimento Intervento Origine',
  codNSISIntervento: 'Codice NSIS Intervento',
  titoloIntervento: 'Titolo Intervento',
  obiettivo: 'Obiettivo',
  finalita: 'Finalità',
  tipologia: 'Tipologia',
  categoria: 'Categoria',
  attrezzatura: 'Attrezzatura',
  annoPriorita: 'Anno Priorità',
  priorita: 'Priorità',
  sottoPriorita: 'Sotto-Priorità',
  contrattoTipo: 'Tipo Contratto',
  appaltoTipo: 'Tipo Appalto',
  statoProg: 'Stato di Progettazione',
  costoIntervento: 'Costo Intervento',
  progettazione: 'Durata Progettazione',
  affidamentoLavori: 'Durata Affidamento Lavori',
  esecuzioneLavori: 'Durata Esecuzione Lavori',
  collaudo: 'Durata Collaudo',
  appaltoIntegrato: 'Appalto Integrato',
  totaleDurataIntervento: 'Durata Totale Intervento',
  direttoreNome: 'Nome Direttore',
  direttoreCognome: 'Cognome Direttore',
  direttoreCF: 'Codice Fiscale Direttore',
  commissarioNome: 'Nome Commissario',
  commissarioCognome: 'Cognome Commissario',
  commissarioCF: 'Codice Fiscale Commissario',
  rupNome: 'Nome RUP (Responsabile Unico del Procedimento)',
  rupCognome: 'Cognome RUP',
  rupCF: 'Codice Fiscale RUP',
  referenteNome: 'Nome Referente',
  referenteCognome: 'Cognome Referente',
  referenteTelefono: 'Telefono Referente',
  referenteEmail: 'Email Referente',
  referenteCF: 'Codice Fiscale Referente',
  delibera: 'Delibera',
  dataDelibera: 'Data Delibera',
  allegato: 'Allegato',
  nomeAllegato: 'Nome Allegato',
  tipoAllegato: 'Tipo Allegato',
  note: 'Note',

};
const mappaNomiInterventoStruttura: { [key: string]: string } = {
  struttureAggiunte: 'Strutture Aggiunte',
  'struttureAggiunte.n.struttura': 'Struttura (Struttura #)',
  'struttureAggiunte.n.costoStruttura': 'Costo Struttura (Struttura #)',
  'struttureAggiunte.n.progettazione': 'Durata Progettazione (Struttura #)',
  'struttureAggiunte.n.affidamentoLavori': 'Durata Affidamento Lavori (Struttura #)',
  'struttureAggiunte.n.esecuzioneLavori': 'Durata Esecuzione Lavori (Struttura #)',
  'struttureAggiunte.n.collaudo': 'Durata Collaudo (Struttura #)',
  'struttureAggiunte.n.totaleDurataIntervento': 'Durata Totale Intervento (Struttura #)',
  'struttureAggiunte.n.appaltoIntegrato': 'Appalto Integrato (Struttura #)',
  'struttureAggiunte.n.totaleDurataStimataIntervento': 'Durata Stimata Totale Intervento (Struttura #)',
  'struttureAggiunte.n.intstrRespStrComplesNome': 'Nome Responsabile Struttura Complessa (Struttura #)',
  'struttureAggiunte.n.intstrRespStrComplesCognome': 'Cognome Responsabile Struttura Complessa (Struttura #)',
  'struttureAggiunte.n.intstrRespStrComplesCf': 'Codice Fiscale Responsabile Struttura Complessa (Struttura #)',
  'struttureAggiunte.n.intstrRespStrSemplNome': 'Nome Responsabile Struttura Semplice (Struttura #)',
  'struttureAggiunte.n.intstrRespStrSemplCognome': 'Cognome Responsabile Struttura Semplice (Struttura #)',
  'struttureAggiunte.n.intstrRespStrSemplCf': 'Codice Fiscale Responsabile Struttura Semplice (Struttura #)',
  // Altri campi figli di listaInterventiEdilizi e listaQuadroEconomico possono essere aggiunti dinamicamente
};
@Injectable({
  providedIn: 'root',
})
export class ErrorService {
  ref: MatDialogRef<ModaleErroreComponent>;

  constructor(private dialog: MatDialog) {}

  // mostraErrore(errore: HttpErrorResponse, title: string) {
  //   let titolo = title;
  //   let messaggio = TITOLO + '<br>' + MESSAGGIO;
  //   let dettaglio = '';

  //   if (errore.status == 400) {
  //     dettaglio = errore.error.detail
  //       ? errore.error.detail
  //           .map(
  //             (obj: { chiave: string; valore: string }) =>
  //               `${obj.chiave}: ${obj.valore}`
  //           )
  //           .join('<br>')
  //       : '';
  //     messaggio = errore.error.title + '<br>' + dettaglio;
  //   }

  //   this.ref = this.dialog.open(ModaleErroreComponent, {
  //     data: {
  //       title: titolo,
  //       message: messaggio,
  //     },
  //     disableClose: true,
  //     width: '600px',
  //   });
  // }

  public findInvalidControls(formGroup: FormGroup | FormArray): string[] {
    const invalid: string[] = [];

    const recursiveCheck = (
      controls: { [key: string]: any },
      path: string = ''
    ) => {
      Object.keys(controls).forEach((key) => {
        const control = controls[key];
        const currentPath = path ? `${path}.${key}` : key;

        // if (control instanceof FormGroup || control instanceof FormArray) {
        //   // Se è un FormGroup o FormArray, esegui una chiamata ricorsiva
        //   recursiveCheck(control.controls, currentPath);
        // } else if (control.invalid) {
        //   // Aggiungi il percorso completo del controllo invalido
        if(control.invalid) {
          invalid.push(currentPath);
        }
          
        // }
      });
    };

    if (formGroup instanceof FormGroup || formGroup instanceof FormArray) {
      recursiveCheck(formGroup.controls);
    }

    return invalid;
  }

  public getFriendlyName(controlPath: string): string {
    const match = controlPath.match(/^struttureAggiunte\.(\d+)\.(.+)/);

  if (match) {
    const index = match[1];
    const field = match[2];
    const key = `struttureAggiunte.n.${field}`;
    return (mappaNomiInterventoStruttura[key] || key).replace('#', `${+index + 1}`); // Indice umano leggibile (1-based)
  }


    return mappaIntervento[controlPath] || controlPath;
  }
}
