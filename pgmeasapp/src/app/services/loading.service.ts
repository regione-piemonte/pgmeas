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
import { LoadingDialogComponent } from '../components/loading-dialog/loading-dialog.component';

@Injectable({
  providedIn: 'root',
})
export class LoadingService {
  ref: MatDialogRef<LoadingDialogComponent>;

  constructor(private dialog: MatDialog) {}

  on() {
    if (!this.ref || this.ref.getState() !== MatDialogState.OPEN) {
      this.ref = this.dialog.open(LoadingDialogComponent, {
        disableClose: true,
        panelClass: 'pgmeas-transparent-dialog',
      });
    }
  }

  off() {
    if (this.ref && this.ref.getState() === MatDialogState.OPEN) {
      this.ref.close();
    }
  }
}
