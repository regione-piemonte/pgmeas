/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modale-errore',
  templateUrl: './modale-errore.component.html',
  styleUrls: ['./modale-errore.component.scss']
})
export class ModaleErroreComponent {
  titolo: string = 'Errore generico';
  messaggioErrore: string = 'Attenzione! Il sistema a causa di errori tecnici non ha potuto completare l\'operazione. Riprovare più tardi.';
  dialog: any;
  dialogConfirm: boolean = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<ModaleErroreComponent>
  ) { }

  ngOnInit() {

  }

  onNoClick(): void {
    this.dialogRef.close(null);
  }

  getMessaggioErrore(): string {
    return this.data.message ? this.data.message : this.messaggioErrore;
  }

  getTitolo(): string {
    return this.data.title ? this.data.title : this.titolo;
  }
}
