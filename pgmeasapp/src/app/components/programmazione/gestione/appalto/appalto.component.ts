/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject, takeUntil} from 'rxjs';
import {Ente, Intervento, InterventoStatoProgettuale, InterventoStruttura, Struttura} from '@pgmeas-library/model';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { LoadingService } from 'src/app/services/loading.service';

@Component({
  selector: 'app-appalto',
  templateUrl: './appalto.component.html',
  styleUrls: ['./appalto.component.scss']
})
export class AppaltoComponent implements OnInit, OnDestroy, OnChanges {
  @Input() intervento: Intervento;
  @Input() statoProgList: InterventoStatoProgettuale[];
  @Input() interventoStruttura: InterventoStruttura[] = [];
  @Input() enti: Ente[] = [];
  @Input() denominazioneStruttura: Struttura[] = [];
  @Input() canEdit = true;
  @Input() canEditRegione = true;
  @Input() dataFromBe: any[] = [];
  @Input() allegati: any[] = [];
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();
  @Output() formValidity: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() tipoModello: EventEmitter<string> = new EventEmitter<string>();
  protected entiGroup: Ente[] = [];
  protected checkboxOptions:{ label: string; value: number }[] = [
   /*
    {
      label: 'DOCFAP',
      value: 1
    },
    {
      label: 'DIP',
      value: 2
    },
    {
      label: 'PFTE',
      value: 3
    },
    {
      label: 'Progetto Esecutivo',
      value: 4
    },
    {
      label: 'Capitolato Prestazionale Forniture',
      value: 5
    }
      */
  ];
  protected appaltoForm: FormGroup;
  protected ente: Ente[] = [];
  protected readonly Number = Number;
  private destroy$: Subject<boolean> = new Subject<boolean>();
  protected moduloTipo: boolean;
  //parerePPP= new FormControl();

  protected fraseOggetto: string;
  radioLabels: { [key: string]: string } = {
    'MOD_A': 'Richiesta di ammissione al finanziamento per edilizia sanitaria',
    'MOD_A_A': 'Richiesta di ammissione al finanziamento per attrezzature sanitarie'
  };
  onChange($event: any) {
    const value = $event.value;
    this.fraseOggetto= this.radioLabels[value];
    this.moduloTipo=!this.moduloTipo
    this.intervento.moduloTipo=value;
    // console.log("FRASE OGGETTO: "+this.fraseOggetto);
  }

  constructor(private fb: FormBuilder, private projectApiService: ProjectApiService,
    private loadingService: LoadingService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!this.canEdit && changes['dataFromBe'] && changes['dataFromBe'].currentValue && changes['allegati'] && changes['allegati'].currentValue) {
      this.allegati = changes['allegati'].currentValue;
      this.appaltoForm = this.createIntervento(this.intervento, this.interventoStruttura, this.dataFromBe,this.allegati);
    }
    if (changes['intervento']
      && changes['intervento'].currentValue
      && changes['interventoStruttura']
      && changes['interventoStruttura'].currentValue) {
      this.appaltoForm = this.createIntervento(this.intervento, this.interventoStruttura);
    }
    if (changes['enti'] && changes['enti'].currentValue) {
      this.entiGroup = this.filterEnti(this.interventoStruttura);
      this.ente = this.filterEnte(this.intervento);
    }
  }

  filterEnti(interventoStruttura: InterventoStruttura[]): Ente[] {
    const strutturaEntiIds = interventoStruttura.map(struttura => struttura.strId);
    return this.enti.filter(ente => strutturaEntiIds.includes(ente.enteId));
  }

  filterEnte(intervento: Intervento): Ente[] {
    return this.enti.filter(ente => ente.enteId === intervento.enteId);
  }

  createIntervento(intervento: Intervento, interventoStruttura: InterventoStruttura[], dataBe?: any[], allegati?: any[]): FormGroup {
    const formGroup = this.fb.group({
      oggetto: new FormControl({
        //value: dataBe ?  dataBe[0]?.allegatoOggetto[0]?.allegatoOggetto : '',
        value:this.intervento.moduloTipo,
        disabled: !this.canEdit
      }, [Validators.required]),
      statoProgetto:  new FormControl({
        value: this.intervento.statiProgettuali,
        disabled: !this.canEdit
      }, [Validators.required]),
      strutture: this.fb.array([])
    });
    //console.log("OGGETTO VALORE: "+formGroup.get('oggetto')?.value);
    const struttureFormArray = formGroup.get('strutture') as FormArray;

    //DA RIVEDERE const parerePPP = formGroup.get('parerePPP')?.value;

    interventoStruttura.forEach((interventoStruttura) => {
      const strutturaFormGroup = this.fb.group({
        cig: new FormControl({
          value: dataBe ? dataBe[0]?.garaAppalto?.filter((data: any) => data.intstrId === interventoStruttura.intStrId).map((garaAppalto: any) => garaAppalto.garaAppaltoCigCod).join() : '',
          disabled: !this.canEdit
        }, [Validators.required]),
        parerePPP: new FormControl({
          // value: dataBe ? dataBe[0]?.strutture.filter((data: any) => data.intStrId === interventoStruttura.intStrId)[0]?.intstrParereVincolantePpp: false,
          // disabled: !this.canEdit
        },
          [Validators.required]),
        determinaAccertamento: new FormControl({
          value: dataBe ? dataBe[0]?.strutture.filter((data: any) => data.intStrId === interventoStruttura.intStrId)[0]?.intstrParereVincolantePppNprotocollo : '',
          disabled: !this.canEdit
        }, [Validators.required])
      });

      struttureFormArray.push(strutturaFormGroup);
    });
    return formGroup;
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
  }

  ngOnInit(): void {
    if (this.intervento && this.interventoStruttura) {
      // console.log("ngOnInitAPPALTO modulo tipo "+this.intervento.moduloTipo);
      // console.log("ngOnInitAPPALTO int id "+this.intervento.intId);
      if(!this.intervento.moduloTipo){
        this.intervento.moduloTipo= 'MOD_A'
        this.fraseOggetto = "Richiesta di ammissione al finanziamento per edilizia sanitaria";
      }else{
        this.fraseOggetto = "Richiesta di ammissione al finanziamento per attrezzature sanitarie";
      }
      this.moduloTipo = this.intervento.moduloTipo=='MOD_A';



      // 3. Prepare the form value for submission
      const formValue = {
        oggetto: this.intervento.moduloTipo,
        statoProgetto: this.intervento.statiProgettuali,  // Emit the selected options (the array of values)
        strutture: this.appaltoForm.get('strutture')?.value
      };
      // console.log("ngOnInitAPPALTO OGGETTO A: "+formValue.oggetto);
      this.formData.emit(formValue);
      this.formValidity.emit(this.appaltoForm.valid);
    }
    this.appaltoForm?.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (): void => {
            const selectedOptions = this.appaltoForm.value.statoProgetto
              .map((checked: boolean, index: number) => checked ? this.checkboxOptions[index].value : null)
              .filter((value: string | null) => value !== null);
            const formValue = {
              oggetto: this.appaltoForm.get('oggetto')?.value,
              statoProgetto: selectedOptions,
              strutture: this.appaltoForm.get('strutture')?.value
            };
            // console.log("ngOnInitAPPALTO OGGETTO B: "+formValue.oggetto);
            this.formData.emit(formValue);
            this.formValidity.emit(this.appaltoForm.valid);
        }
      });

      this.appaltoForm.get('oggetto')?.valueChanges.subscribe( txt=>{
        // console.log("ngOnInitAPPALTO TIPO MODELLO: "+txt);
        this.tipoModello.emit(txt);
      })

      this.checkboxOptions = this.statoProgList.map(interventoStatoProgettuale => ({
        "label": interventoStatoProgettuale.intStatoProgDesc,
        "value": interventoStatoProgettuale.intStatoProgId
      }));


  }

  disableRegionali(): boolean {
    return (this.canEditRegione);
  }

  markAsTouched(): void {
    this.appaltoForm.markAllAsTouched();
  }

  downloadPdf(id: number) {
    this.loadingService.on();

    this.projectApiService.downloadAllegatoById(id).subscribe({
      next: (file) => {
        const tab = window.open();

        if (tab) {
          const src = `data:application/pdf;base64,${encodeURI(file.base64)}`;
          const html = `<iframe src="${src}" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none;"></iframe>`;
          tab.document.write(html);
        }

        this.loadingService.off();
      },
      error: () => this.loadingService.off(),
    });
  }
}
