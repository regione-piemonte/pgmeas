/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ProjectApiService } from 'src/app/services/project-api.service';

@Component({
  selector: 'app-modale-programmazione',
  templateUrl: './modale-programmazione.component.html',
  styleUrls: ['./modale-programmazione.component.scss']
})
export class ModaleProgrammazioneComponent implements OnInit {

  isSalvataggio: boolean = false;
  isBroken: boolean = false;
  messaggioErrore: string = 'Attenzione! Il sistema a causa di errori tecnici non ha potuto completare l\'operazione. Riprovare più tardi.';
  loading: boolean = false;
  isSubmitting:boolean = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<ModaleProgrammazioneComponent>,
    private projectApiService: ProjectApiService
  ) {}

  ngOnInit(): void {
    this.isSubmitting = false;
  }

  onNoClick(): void {
    this.dialogRef.close(null);
  }

  // Funzione per limitare i caratteri a numeri e '/'
  onInputDate(event: KeyboardEvent) {
    const allowedKeys = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', 'Backspace', 'Tab', 'ArrowLeft', 'ArrowRight'];

    if (!allowedKeys.includes(event.key)) {
      event.preventDefault();
    }
  }

  onSave(): void {
    this.isSalvataggio = false;
    this.isSubmitting = true;
    this.projectApiService.salvaProgrammazione(this.data.gestisciProgrammazione).subscribe(save => {
      this.isSalvataggio = true;
    }, err => {
      this.onNoClick();
      // this.messaggioErrore = err.error.title;
      // this.isBroken = true;
    })
  }
}
