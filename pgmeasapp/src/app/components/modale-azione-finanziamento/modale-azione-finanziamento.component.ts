/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject, OnInit } from "@angular/core";
import { FormControl, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Respingimento } from "@pgmeas-library/model/src/respingimento";
import { Subject } from "rxjs";
import { ErrorService } from "src/app/services/error.service";
import { ProjectApiService } from "src/app/services/project-api.service";


@Component({
  selector: 'app-modale-azione-finanziamento',
  templateUrl: './modale-azione-finanziamento.component.html',
  styleUrls: ['./modale-azione-finanziamento.component.scss']
})
export class ModaleAzioneFinanziamentoComponent implements OnInit {
  private destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(
    public dialogRef: MatDialogRef<ModaleAzioneFinanziamentoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private projectApiService: ProjectApiService,
    private errorService: ErrorService
  ) {
  }

  isBroken: boolean = false;
  messaggioErroreRespingi: string = 'Attenzione! Il sistema non è riuscito a respingere la richiesta di ammissione al finanziamento.'
  messaggioErroreApprova: string = 'Attenzione! Il sistema non è riuscito ad approvare la richiesta di ammissione al finanziamento.'
  isSalvataggio: boolean = false;
  avvenimento: string = 'Pre';
  messaggio: string = 'Pro';
  isRespingimento: boolean = false;
  nota: string = '';
  notaError: boolean = false;
  isCompilazioneNota: boolean = false;
  isAllOk:boolean=false;
  isSubmitting:boolean = false;

  onNoClick(): void {
    this.dialogRef.close(this.isAllOk);
  }

  onSave(): void {
    this.isSalvataggio = false;
    this.isSubmitting = true;

    if (this.data.operazione == 'RESPINGIRICHIESTA') {
      // console.log("NOTE: "+this.nota);
      let respingimento: Respingimento = { note: this.nota };
      this.projectApiService.respingiRichiestaFinanziamento(this.data.rIntModuloAId, this.data.intId, respingimento).subscribe(save => {
        this.isSalvataggio = true;
        this.isAllOk=true;
        this.isSubmitting = false;
      }, err => {
        this.isBroken = true;
        //this.onNoClick()
      })
    } else if (this.data.operazione == 'APPROVARICHIESTA') {
      this.projectApiService.approvaRichiestaFinanziamento(this.data.rIntModuloAId, this.data.intId).subscribe(save => {
        this.isSalvataggio = true;
        this.isAllOk=true;
        this.isSubmitting = false;
      }, err => {
        this.isBroken = true;
        //this.onNoClick()
      })
    }  else if (this.data.operazione == 'INVIAMODULOAAREGIONE') {
      this.projectApiService.inviaARegioneRichiestaFinanziamento(this.data.rIntModuloAId, this.data.intId).subscribe(save => {
        this.isSalvataggio = true;
        this.isAllOk=true;
        this.isSubmitting = false;
      }, err => {
        this.isBroken = true;
        //this.onNoClick()
      })
    }
  }
  //this.dialogRef.close(true);
  //dopo la chiamata imposto isSalvataggio a true per permettere di vedere l'altro messaggio

  ngOnInit(): void {
    this.isSubmitting = false;
    if (this.data.operazione == "RESPINGIRICHIESTA") {
      this.isRespingimento = true;
    }
  }

  compilaNota() {
    if(this.nota === '') {
      this.notaError = true;
    } else {
      this.isCompilazioneNota = true;
    }
  }
}
