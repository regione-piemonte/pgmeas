/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {RegistryApiService} from '../../../../../services/registry-api.service';
import {FinanziamentoTipoDet} from '@pgmeas-library/model';
import {Subject, takeUntil} from 'rxjs';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-modale-aggiunta-finanziamento',
  templateUrl: './modale-aggiunta-finanziamento.component.html',
  styleUrls: ['./modale-aggiunta-finanziamento.component.scss']
})
export class ModaleAggiuntaFinanziamentoComponent implements OnInit {
  selectedValue: any;
  protected finanziamentiOptions: FinanziamentoTipoDet[] = [];
  protected form: FormGroup;
  private destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(
    public dialogRef: MatDialogRef<ModaleAggiuntaFinanziamentoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private registryApiService: RegistryApiService
  ) {
    this.form = this.fb.group({
      fonte: [data.tipo],
      tipo: [data.tipo],
      dettaglio: [data.dettaglio],
      quotaRegione: ' - ',
      quotaStato: ' - ',
      quotaAltro: ' - ',
      //atto: [data.atto],
      importo: [data.importo]
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.form.valid) {
      this.dialogRef.close(this.form.value);
    }
  }

  ngOnInit(): void {
    this.registryApiService.getFinanziamentoTipoDetList()
      .pipe(
        takeUntil(this.destroy$)
      )
      .subscribe((data) => {
        this.finanziamentiOptions = data
          .filter((finanz) => finanz.finTipoId === 1)
          .sort((a, b) => a.finTipoDetDesc.toUpperCase().localeCompare(b.finTipoDetDesc.toUpperCase()));
      });
  }
}
