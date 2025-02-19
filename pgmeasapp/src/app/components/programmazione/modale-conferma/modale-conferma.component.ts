/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FinanziamentoTipoDet} from '@pgmeas-library/model';
import {Subject, takeUntil} from 'rxjs';
import {FormBuilder, FormGroup} from '@angular/forms';
import { RegistryApiService } from 'src/app/services/registry-api.service';

@Component({
  selector: 'app-modale-conferma',
  templateUrl: './modale-conferma.component.html',
  styleUrls: ['./modale-conferma.component.scss']
})
export class ModaleConfermaComponent implements OnInit {
  private destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(
    public dialogRef: MatDialogRef<ModaleConfermaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
  }

  avvenimento: string = 'Pre';
  messaggio: string = 'Pro';

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  onSave(): void {
      this.dialogRef.close(true);
  }

  ngOnInit(): void {

  }
}
