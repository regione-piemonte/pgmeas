/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { formatDate } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Allegato } from '@pgmeas-library/model';
import { ProjectApiService } from 'src/app/services/project-api.service';

export interface DialogData {
  intId: number;
}

@Component({
  selector: 'app-year-selection-dialog',
  templateUrl: './year-selection-dialog.component.html',
  styleUrls: ['./year-selection-dialog.component.scss'],
})
export class YearSelectionDialogComponent implements OnInit {
  loading = true;
  interventoAllegatoList: Allegato[];
  interventoAllegato = new FormControl(null, Validators.required);

  constructor(
    private dialogRef: MatDialogRef<YearSelectionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: DialogData,
    private projectApiService: ProjectApiService
  ) {}

  ngOnInit() {
    this.projectApiService
      .getInterventoAllegatoListByIntervento(this.data.intId)
      .subscribe((interventoAllegatoList) => {
        this.interventoAllegatoList = interventoAllegatoList;
        this.loading = false;
      });
  }

  getInterventoAllegatoListByMonitoraggio() {
    return this.interventoAllegatoList.filter(ia => ia.allegatoTipoId === 15);
  }

  getInterventoAllegatoLabel(interventoAllegato: Allegato) {
    const pieces = [];

    if (interventoAllegato.allegatoProtocolloData) {
      pieces.push(
        formatDate(
          interventoAllegato.allegatoProtocolloData,
          'mediumDate',
          'it'
        )
      );
    }

    if (interventoAllegato.allegatoOggetto) {
      pieces.push(`"${interventoAllegato.allegatoOggetto}"`);
    }

    return pieces.length
      ? pieces.join(' - ')
      : `Allegato ${interventoAllegato.allegatoId}`;
  }

  ko() {
    this.dialogRef.close();
  }
}
