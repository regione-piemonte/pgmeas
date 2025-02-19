/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { GestisciProgrammazione } from '@pgmeas-library/model/src/gestisci-programmazione';
import { ProrogaEnte } from '@pgmeas-library/model/src/proroga-ente';
import { ProrogaTutteAsr } from '@pgmeas-library/model/src/proroga-tutte-asr';
import { ErrorService } from 'src/app/services/error.service';
import { ProjectApiService } from 'src/app/services/project-api.service';

@Component({
  selector: 'app-modale-extend-programming',
  templateUrl: './modale-extend-programming.component.html',
  styleUrls: ['./modale-extend-programming.component.scss']
})
export class ModaleExtendProgrammingComponent {
  isSalvataggio: boolean = false;
  isBroken: boolean = false;
  messaggioErrore: string = 'Attenzione! Il sistema a causa di errori tecnici non ha potuto completare l\'operazione. Riprovare più tardi.';
  loading: boolean = false;
  dataFineProroga: Date;
  hasErrorDate: boolean = false;
  dialog: any;
  dialogConfirm: boolean = false;
  isSubmitting:boolean = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<ModaleExtendProgrammingComponent>,
    private projectApiService: ProjectApiService,
    private erroreService: ErrorService
  ) { }

  ngOnInit() {
    this.isSubmitting = false;
    if(this.data.modalita != 'TUTTE') {
      if(this.data.ente.faseProrogaFine) {
        this.dataFineProroga = new Date(this.parseDate(this.data.ente.faseProrogaFine));
      }
    }
  }

  // Funzione per limitare i caratteri a numeri e '/'
  onInputDate(event: KeyboardEvent) {
    const allowedKeys = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', 'Backspace', 'Tab', 'ArrowLeft', 'ArrowRight'];

    if (!allowedKeys.includes(event.key)) {
      event.preventDefault();
    }
  }

  parseDate(dateString: string): Date {
    const [day, month, year] = dateString.split('/').map(Number);
    return new Date(year, month - 1, day);
  }

  calcolaTriennio(): string {
    const annoCorrente = new Date().getFullYear();
    const annoInizio = annoCorrente;
    const annoFine = annoCorrente + 2;

    return `${annoInizio}-${annoFine}`;
  }

  onNoClick(): void {
    this.dialogRef.close(null);
  }

  onSave(): void {
    this.isSalvataggio = false;
    this.hasErrorDate = false;
    this.isSubmitting = true;
    if (!this.dataFineProroga || (this.dataFineProroga.getFullYear() != new Date().getFullYear()) || (this.dataFineProroga <= new Date(this.parseDate(this.data.dataFine)))) {
      this.hasErrorDate = true
    } else {
      if (this.data.modalita != 'TUTTE') {

        let giornoFineProroga = String(this.dataFineProroga.getDate()).padStart(2, '0');
        let meseFineProroga = String(this.dataFineProroga.getMonth() + 1).padStart(2, '0');
        let annoFineProroga = this.dataFineProroga.getFullYear();
        let dataFineProrogaFormattata = `${giornoFineProroga}/${meseFineProroga}/${annoFineProroga}`;

        let prorogaEnte: ProrogaEnte = {
          enteId: this.data.ente.enteId,
          enteCodEsteso: this.data.ente.enteCodEsteso,
          enteDesc: this.data.ente.enteDesc,
          faseProrogaInizio: null,
          faseProrogaFine: dataFineProrogaFormattata
        }

        this.projectApiService.salvaProrogaPerEnte(annoFineProroga, prorogaEnte).subscribe(save => {
          this.isSalvataggio = true;
        }, err => {
          this.onNoClick();
        })

      } else {
        this.dialogConfirm = true;
      }
    }

  }

  onSaveAll(): void {
    let giornoFineProroga = String(this.dataFineProroga.getDate()).padStart(2, '0');
    let meseFineProroga = String(this.dataFineProroga.getMonth() + 1).padStart(2, '0');
    let annoFineProroga = this.dataFineProroga.getFullYear();
    let dataFineProrogaFormattata = `${giornoFineProroga}/${meseFineProroga}/${annoFineProroga}`;

    let prorogaTutteAsr: ProrogaTutteAsr = {
      anno: annoFineProroga,
      faseInizio: this.data.dataInizio,
      faseFine: this.data.dataFine,
      faseProrogaInizio: null,
      faseProrogaFine: dataFineProrogaFormattata
    }


    this.projectApiService.salvaProrogaAll(annoFineProroga, prorogaTutteAsr).subscribe(save => {
      this.isSalvataggio = true;
    }, err => {
      this.isBroken = true;
    })
  }
}
