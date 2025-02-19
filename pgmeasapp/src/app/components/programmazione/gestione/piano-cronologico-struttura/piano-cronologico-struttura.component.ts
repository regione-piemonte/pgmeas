/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject, takeUntil} from 'rxjs';
import {Intervento, InterventoStruttura, Struttura} from '@pgmeas-library/model';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-piano-cronologico-struttura',
  templateUrl: './piano-cronologico-struttura.component.html',
  styleUrls: ['./piano-cronologico-struttura.component.scss']
})
export class PianoCronologicoStrutturaComponent implements OnInit, OnDestroy, OnChanges {

  @Input() interventoStruttura: InterventoStruttura[] = [];
  @Input() denominazioneStruttura: Struttura[] = [];
  @Input() canEdit = true;
  @Input() canEditRegione = true;
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();
  @Output() formValidity: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() appaltoIntegrato = new EventEmitter<{ idStruttura: number, appaltoIntegrato: boolean }>();

  public pianoCronologicoForm: FormGroup;
  private destroy$: Subject<boolean> = new Subject<boolean>();
  // protected totaleDurataIntervento: number;

  constructor(private fb: FormBuilder) {
  }

  get interventi(): FormArray {
    return this.pianoCronologicoForm?.get('interventi') as FormArray;
  }

  createIntervento(data?: InterventoStruttura): FormGroup {
    return this.fb.group({
      progettazioneGg: new FormControl({
        value: data?.progettazioneGg ?? '',
        disabled: !this.canEdit
      }, [Validators.required]),
      affidamentoLavoriGg: new FormControl({
        value: data?.affidamentoLavoriGg ?? '',
        disabled: !this.canEdit
      }, [Validators.required]),
      esecuzioneLavoriGg: new FormControl({
        value: data?.esecuzioneLavoriGg ?? '',
        disabled: !this.canEdit
      }, [Validators.required]),
      collaudoGg: new FormControl({
        value: data?.collaudoGg ?? '',
        disabled: !this.canEdit
      }, [Validators.required]),
      appaltoIntegrato: new FormControl({
        value: data?.appaltoIntegrato ?? false, // Booleano predefinito
        disabled: !this.canEdit || this.canEditRegione
      }, [Validators.required]),
      idStruttura: new FormControl({
        value: data?.intStrId ?? '',
        disabled: !this.canEdit
      }, [Validators.required]),
    });
  }

  loadInterventi(interventoStruttura: InterventoStruttura[]): void {
    if (this.interventoStruttura && this.interventoStruttura.length > 0) {
      interventoStruttura.forEach((intervento) => {
        this.interventi?.push(this.createIntervento(intervento));
      });
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit(): void {
    this.pianoCronologicoForm = this.fb.group({
      interventi: this.fb.array([])
    });
    if (this.interventoStruttura) {
      this.loadInterventi(this.interventoStruttura);
      this.formData.emit(this.pianoCronologicoForm.getRawValue());
      this.formValidity.emit(this.pianoCronologicoForm.valid);
      // this.calcolaTotaleIntervento(this.interventoStruttura);
    }

    this.pianoCronologicoForm.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe({
          next: (): void => {
            // this.calcolaTotaleInterventoChange(this.pianoCronologicoForm);
            this.formData.emit(this.pianoCronologicoForm.getRawValue());
            this.formValidity.emit(this.pianoCronologicoForm.valid);

          }
        }
      );
  }

  disableRegionali(): boolean {
    return (this.canEditRegione);
    }


  ngOnChanges(changes: SimpleChanges): void {
    if (changes['interventoStruttura'] && changes['interventoStruttura'].currentValue) {
      this.loadInterventi(this.interventoStruttura);
    }
  }

  markAsTouched(): void {
    this.pianoCronologicoForm.markAllAsTouched();
  }


  calcolaTotaleInterventoStruttura(index:number) {
    let totale=0
    if(this.pianoCronologicoForm.value){
      const formValues = this.pianoCronologicoForm.value;
      const progettazioneGg = formValues.interventi[index].progettazioneGg || 0;
      const affidamentoLavoriGg = formValues.interventi[index].affidamentoLavoriGg || 0;
      const esecuzioneLavoriGg = formValues.interventi[index].esecuzioneLavoriGg || 0;
      const collaudoGg = formValues.interventi[index].collaudoGg || 0;
       totale = progettazioneGg + affidamentoLavoriGg + esecuzioneLavoriGg + collaudoGg;
    }
     return totale;
    }

    onToggleChange(event: MatSlideToggleChange, idStruttura: number): void {
      const strutturaData = {
        idStruttura: idStruttura,
        appaltoIntegrato: event.checked
      };

      this.appaltoIntegrato.emit(strutturaData);
    }



  // private calcolaTotaleIntervento(interventoStruttura: InterventoStruttura[]): void {
  //   if (this.interventoStruttura && this.interventoStruttura.length > 0) {
  //     interventoStruttura.forEach((intervento) => {
  //       const progettazione = intervento.progettazioneGg || 0;
  //       const affidamento = intervento.affidamentoLavoriGg || 0;
  //       const esecuzione = intervento.esecuzioneLavoriGg || 0;
  //       const collaudo = intervento.collaudoGg || 0;

  //       const totale = progettazione + affidamento + esecuzione + collaudo;

  //        // Aggiorna il campo totale
  //   this.totaleDurataIntervento = totale;
  //     });
  //   }
  // }

  // private calcolaTotaleInterventoChange(pianoCronologicoForm: FormGroup): void {
  //   console.log(this.pianoCronologicoForm);
  //   console.log("calcolaTotaleInterventoChange: "+pianoCronologicoForm.get('progettazioneGg')?.value );
  //   const formValues = pianoCronologicoForm.value;
  //   const progettazioneGg = formValues.interventi[0].progettazioneGg || 0;
  //   const affidamentoLavoriGg = formValues.interventi[0].affidamentoLavoriGg || 0;
  //   const esecuzioneLavoriGg = formValues.interventi[0].esecuzioneLavoriGg || 0;
  //   const collaudoGg = formValues.interventi[0].collaudoGg || 0;
  //   const appaltoIntegrato = formValues.interventi[0].appaltoIntegrato || false;

  //   console.log("progettazioneGg: "+progettazioneGg);
  //   console.log("affidamentoLavoriGg: "+affidamentoLavoriGg);
  //   console.log("esecuzioneLavoriGg: "+esecuzioneLavoriGg);
  //   console.log("collaudoGg: "+collaudoGg);
  //   console.log("appaltoIntegrato"+appaltoIntegrato);

  //   //aggiorno il flag appaltoIntegrato per il quadro economico
  //   this.appaltoIntegrato.emit(appaltoIntegrato);

  //   const totale = progettazioneGg + affidamentoLavoriGg + esecuzioneLavoriGg + collaudoGg;
  //   console.log("totale: " + totale);
  //   this.totaleDurataIntervento = totale;
  //   // Aggiorna il campo totale
  //    this.pianoCronologicoForm.get('totaleDurataIntervento')?.setValue(totale, { emitEvent: false });
  //   //this.totaleDurataIntervento = totale;
  // }
}
