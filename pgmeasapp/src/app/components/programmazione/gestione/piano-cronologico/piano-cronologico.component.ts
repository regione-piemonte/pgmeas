/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject, takeUntil} from 'rxjs';
import {Intervento, InterventoStruttura, Struttura} from '@pgmeas-library/model';

@Component({
  selector: 'app-piano-cronologico',
  templateUrl: './piano-cronologico.component.html',
  styleUrls: ['./piano-cronologico.component.scss']
})
export class PianoCronologicoComponent implements OnInit, OnDestroy, OnChanges {
  @Input() intervento: Intervento;
  @Input() canEdit = true;
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();
  @Output() formValidity: EventEmitter<boolean> = new EventEmitter<boolean>();

  protected pianoCronologicoForm: FormGroup;
  private destroy$: Subject<boolean> = new Subject<boolean>();
  protected totaleDurataIntervento: number;

  constructor(private fb: FormBuilder) {
  }

  get interventi(): FormArray {
    return this.pianoCronologicoForm?.get('interventi') as FormArray;
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit(): void {
    this.pianoCronologicoForm = this.fb.group({
      interventi: this.fb.array([]),
      appaltoIntegrato: [false]
    });
    if (this.intervento) {
      this.loadInterventi(this.intervento);
      this.calcolaTotaleIntervento(this.intervento);
    }   
    
    this.pianoCronologicoForm.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe({
          next: (): void => {
            this.formData.emit(this.pianoCronologicoForm.getRawValue());
            this.formValidity.emit(this.pianoCronologicoForm.valid);
          }
        }
      );
  }

  loadInterventi(intervento: Intervento): void {
    if (this.intervento) {
      console.log("loadInterventi APPALTO INTEGRATO: "+intervento.appaltoIntegrato);
      this.interventi?.push(this.createIntervento(intervento));
    }
  }

  createIntervento(data: Intervento): FormGroup {
    const formGroup = this.fb.group({
      appaltoIntegrato: new FormControl({
        value:  [false],
        disabled: !this.canEdit
      },[Validators.required]),
    });
  return formGroup;
  }

  ngOnChanges(changes: SimpleChanges): void {}

  private calcolaTotaleIntervento(intervento: Intervento): void {
    const progettazione = intervento.progettazioneGg || 0;
    const affidamento = intervento.affidamentoLavoriGg || 0;
    const esecuzione = intervento.esecuzioneLavoriGg || 0;
    const collaudo = intervento.collaudoGg || 0;

    const totale = progettazione + affidamento + esecuzione + collaudo;

    // Aggiorna il campo totale
    this.totaleDurataIntervento = totale;
  }
}
