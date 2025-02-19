/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { GestisciProgrammazione } from '@pgmeas-library/model/src/gestisci-programmazione';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { ModaleProgrammazioneComponent } from '../modale-programmazione/modale-programmazione.component';
import { ProgrammazioneEnti } from '@pgmeas-library/model/src/programmazione-enti';

@Component({
  selector: 'app-manage-programming',
  templateUrl: './manage-programming.component.html',
  styleUrls: ['./manage-programming.component.scss']
})

export class ManageProgrammingComponent {

  triennio: string;
  dataInizio: Date;
  dataFine: Date;
  messaggioErrore: string[] = [];
  isValidDate: boolean = true;
  loading: boolean = false;
  isProroga: boolean = false;
  isNewProgrammazione: boolean = false;
  isEdit: boolean = false;
  isDateDisabled: boolean = false;

  constructor(
    private router: Router,
    private projectApiService: ProjectApiService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    let annoCorrente = new Date().getFullYear();
    this.loading = true;
    this.projectApiService.getProgrammazioneByAnno(annoCorrente).subscribe(programmazione => {
      this.checkStatoProgrammazione(programmazione);

      if(this.isNewProgrammazione) {

        this.dataInizio = new Date();
        this.dataFine = new Date();

      } else if(this.isEdit) {

        this.dataInizio = new Date(this.parseDate(programmazione.faseInizio));
        this.dataFine = new Date(this.parseDate(programmazione.faseFine));

      } else {

        this.dataInizio = new Date(this.parseDate(programmazione.faseInizio));
        this.dataFine = new Date(this.parseDate(programmazione.faseFine));
        this.isDateDisabled = true;

      }

      this.loading = false;
    }, err => {
      this.loading = false;
    })
  }

  calcolaTriennio(): string {
    const annoCorrente = new Date().getFullYear();
    const annoInizio = annoCorrente;
    const annoFine = annoCorrente + 2;

    return `${annoInizio}-${annoFine}`;
  }

    // Funzione per limitare i caratteri a numeri e '/'
    onInputDate(event: KeyboardEvent) {
      const allowedKeys = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', 'Backspace', 'Tab', 'ArrowLeft', 'ArrowRight'];

      if (!allowedKeys.includes(event.key)) {
        event.preventDefault();
      }
    }

    back() {
      const back = history.state.back ?? '/home';
      this.router.navigateByUrl(back);
    }

    validaDate(): boolean {
      let result: boolean = true;
      let resultControlloTriennio: boolean = true;
      let resultControlloDate: boolean = true;

      this.messaggioErrore = [];

      if(this.dataInizio && this.dataFine) {
        let annoCorrente = new Date().getFullYear();

        let annoDataInizio = this.dataInizio.getFullYear();
        let annoDataFine = this.dataFine.getFullYear();


        if (annoDataInizio != annoCorrente || annoDataFine != annoCorrente) {
          resultControlloTriennio = false;
          this.messaggioErrore.push("Attenzione! il formato delle date non è corretto");
        }

        if (this.dataFine <= this.dataInizio) {
          resultControlloDate = false;
          this.messaggioErrore.push("Attenzione! la data di inizio deve essere inferiore alla data di fine programmazione");
        }

        if(!resultControlloTriennio || !resultControlloDate) {
          result = false;
        }
      } else {
        result = false;
        this.messaggioErrore.push("Attenzione! il formato delle date non è corretto");
      }

      return result;
    }

    buildProgrammazione(): GestisciProgrammazione {

      let annoCorrente = new Date().getFullYear();

      let faseInizio = this.dataInizio;
      let giornoInizio = String(faseInizio.getDate()).padStart(2, '0');
      let meseInizio = String(faseInizio.getMonth() + 1).padStart(2, '0');
      let annoInizio = faseInizio.getFullYear();
      let dataInizioFormattata = `${giornoInizio}/${meseInizio}/${annoInizio}`;

      let faseFine = this.dataFine;
      let giornoFine = String(faseFine.getDate()).padStart(2, '0');
      let meseFine = String(faseFine.getMonth() + 1).padStart(2, '0');
      let annoFine = faseFine.getFullYear();
      let dataFineFormattata = `${giornoFine}/${meseFine}/${annoFine}`;

      let gestisciProgrammazione: GestisciProgrammazione = {
        anno: annoCorrente,
        faseInizio: dataInizioFormattata,
        faseFine: dataFineFormattata
      }

      return gestisciProgrammazione;
    }

    apriModaleConfermaInvioRegione(gestisciProgrammazione: GestisciProgrammazione) {
      const dialogRef = this.dialog.open(ModaleProgrammazioneComponent, {
        width: '600px',
        data: {
          titolo: 'Periodo operativo della programmazione',
          messaggio: 'Confermi l\'impostazione delle 2 date per la fase di programmazione?',
          gestisciProgrammazione: gestisciProgrammazione,
          risultato: 'Le date di inizio e di fine programmazione per l\'anno in corso sono state correttamente inserite.'
        }
      });
    }

    checkStatoProgrammazione(programmazioneEnti: ProgrammazioneEnti) {
      if(!programmazioneEnti.faseInizio || !programmazioneEnti.faseFine) {
        //caso in cui la programmazione deve essere impostata
        this.isNewProgrammazione = true;
      } else {
        //controllo se si tratta di una modifica o se si tratta di proroga
        let hasProroghe = programmazioneEnti.enti.some(ente => ente.faseProrogaInizio && ente.faseProrogaFine);
        if(hasProroghe) {
          this.isProroga = true;
        } else {
          this.isEdit = true;
        }
      }
    }


    parseDate(dateString: string): Date {
      const [day, month, year] = dateString.split('/').map(Number);
      return new Date(year, month - 1, day);
    }

    navigateToProrogaProgrammazione(): void {
      this.router.navigate(['/proroga-programmazione']);
    }

    confermaDate() {
      this.isValidDate = this.validaDate();

      if (this.isValidDate) {
        let gestisciProgrammazione = this.buildProgrammazione();
        this.apriModaleConfermaInvioRegione(gestisciProgrammazione);
      }
    }

}
