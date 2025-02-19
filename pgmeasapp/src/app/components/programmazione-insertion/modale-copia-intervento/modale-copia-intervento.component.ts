/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FinanziamentoTipoDet, Intervento} from '@pgmeas-library/model';
import {Subject, takeUntil} from 'rxjs';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { RegistryApiService } from 'src/app/services/registry-api.service';
import { ProjectApiService } from 'src/app/services/project-api.service';

@Component({
  selector: 'app-modale-copia-intervento',
  templateUrl: './modale-copia-intervento.component.html',
  styleUrls: ['./modale-copia-intervento.component.scss']
})
export class ModaleCopiaInterventoComponent implements OnInit {
  copiaForm: FormGroup;
  constructor(
    public dialogRef: MatDialogRef<ModaleCopiaInterventoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private projectApiService: ProjectApiService
  ) {
  }

  avvenimento: string = 'Pre';
  messaggio: string = 'Pro';
  displayedColumns: string[] = ['intCod', 'intTitolo', 'intImporto', 'copia'];
  interventiList: Intervento[] = [];
  loading: boolean = false;

  onNoClick(): void {
    this.dialogRef.close(null);
  }
  annulla():void{
    this.copiaForm.reset()
  }

  ngOnInit(): void {
    this.copiaForm = this.fb.group({
      anno: [0],
      cup: [''],
      codInterventoPGMEAS: [''],
      titoloIntervento: [''],
    });
  }

  cerca(){
    this.loading = true;
    let anno = this.copiaForm.get('anno')?.value && this.copiaForm.get('anno')?.value!="" ? this.copiaForm.get('anno')?.value : null;
    let cup = this.copiaForm.get('cup')?.value && this.copiaForm.get('cup')?.value!="" ? this.copiaForm.get('cup')?.value : null;
    let codInterventoPGMEAS = this.copiaForm.get('codInterventoPGMEAS')?.value && this.copiaForm.get('codInterventoPGMEAS')?.value!="" ? this.copiaForm.get('codInterventoPGMEAS')?.value : null;
    let titoloIntervento = this.copiaForm.get('titoloIntervento')?.value && this.copiaForm.get('titoloIntervento')?.value!="" ? this.copiaForm.get('titoloIntervento')?.value : null;
    this.projectApiService.getInterventoListForCopy(anno, codInterventoPGMEAS, titoloIntervento, cup).subscribe(interventi =>{
      this.interventiList = interventi;
      this.loading = false;
    }, err => {
      this.loading = false;
    });
  }

  copia(intervento: Intervento){
    this.dialogRef.close(intervento);
  }
}
