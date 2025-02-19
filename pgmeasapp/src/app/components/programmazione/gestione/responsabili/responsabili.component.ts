/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject, takeUntil} from 'rxjs';
import {Intervento, InterventoStruttura} from '@pgmeas-library/model';

@Component({
  selector: 'app-responsabili',
  templateUrl: './responsabili.component.html',
  styleUrls: ['./responsabili.component.scss']
})
export class ResponsabiliComponent implements OnInit, OnDestroy {
  @Input() data: any;
  @Input() intervento: Intervento;
  @Input () interventoStruttura: InterventoStruttura[] = [];
  @Input() canEdit: boolean;
  @Input() canEditRegione = true;
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();
  public responsabiliForm: FormGroup;
  private destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(private fb: FormBuilder) {

  }

  ngOnDestroy(): void {
    this.destroy$.next(false);
    this.destroy$.complete();
  }

  ngOnInit(): void {
    this.initForm();
    this.responsabiliForm.valueChanges
      .pipe(
        takeUntil(this.destroy$)
      )
      .subscribe({
          next: () => {
            const formValue = this.responsabiliForm.getRawValue();
            this.formData.emit(formValue);
            
          }
        }
      );
  }

  initForm(): void {
    const intervento = this.intervento;
    const interventoStruttura = this.interventoStruttura;
    this.responsabiliForm = this.fb.group({
      responsabileProcedimento: this.fb.array([]),
      nomeDirettore: new FormControl({value: intervento.intDirettoreGeneraleNome ?? '', disabled: !this.canEdit}),
      cognomeDirettore: new FormControl({value: intervento.intDirettoreGeneraleCognome ?? '', disabled: !this.canEdit}),
      codiceFiscaleDirettore: new FormControl({value: intervento.intDirettoreGeneraleCf ?? '', disabled: !this.canEdit}),
      nomeCommissario: new FormControl({value: intervento.intCommissarioNome ?? '', disabled: !this.canEdit}),
      cognomeCommissario: new FormControl({value: intervento.intCommissarioCognome ?? '', disabled: !this.canEdit}),
      codiceFiscaleCommissario: new FormControl({value: intervento.intCommissarioCf ?? '', disabled: !this.canEdit}),
      nomeRup: new FormControl({value: intervento.intRupNome ?? '', disabled: !this.canEdit}, [Validators.required]),
      cognomeRup: new FormControl({value: intervento.intRupCognome ?? '', disabled: !this.canEdit}, [Validators.required]),
      codiceFiscaleRup: new FormControl({value: intervento.intRupCf ?? '', disabled: !this.canEdit}, [Validators.required]),      
      nomeReferentePratica: new FormControl({value: intervento.intReferentePraticaNome ?? '', disabled: !this.canEdit}, [Validators.required]),
      cognomeReferentePratica: new FormControl({value: intervento.intReferentePraticaCognome ?? '', disabled: !this.canEdit}, [Validators.required]),
      codiceFiscaleReferentePratica: new FormControl({value: intervento.intReferentePraticaCf ?? '', disabled: !this.canEdit}, [Validators.required]),
      telefonoReferentePratica: new FormControl({value: intervento.intReferentePraticaTelefono ?? '', disabled: !this.canEdit}, [Validators.required]),
      emailReferentePratica: new FormControl({
        value: intervento.intReferentePraticaEmail ?? '',
        disabled: !this.canEdit
      }, [Validators.required, Validators.email])
    });

    const formValue = this.responsabiliForm?.getRawValue();
    this.formData.emit(formValue);
    this.responsabiliForm.updateValueAndValidity();
  }

  get responsabileProcedimento() {
    return this.responsabiliForm.get('responsabileProcedimento') as FormArray;
  }

  disableRegionali(): boolean {
    return (this.canEditRegione);
    }


  markAsTouched(): void {
    this.responsabiliForm.markAllAsTouched();
  }
}
