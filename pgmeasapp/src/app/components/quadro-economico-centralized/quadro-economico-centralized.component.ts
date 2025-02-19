/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

import { Component, Input, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegistryApiService } from 'src/app/services/registry-api.service';
import { ClassificazioneTree } from '@pgmeas-library/model';
import { combineLatest, tap } from 'rxjs';
import { CommonModule } from '@angular/common'; // Importa CommonModule
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { NgxCurrencyDirective, NgxCurrencyInputMode } from 'ngx-currency';


const IVA = 0.22;
const DLIMIT = 0.02;
const ELIMITMIN = 0.00;
const ELIMITMED = 0.04;
const ELIMITMAGG = 0.10;
const GLIMIT = 0.10;

@Component({
  selector: 'app-quadro-economico-centralized',
  standalone: true,
  templateUrl: './quadro-economico-centralized.component.html',
  styleUrls: ['./quadro-economico-centralized.component.scss'],
  imports: [
    ReactiveFormsModule,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    NgxCurrencyDirective,

  ],

})

export class QuadroEconomicoCentralizedComponent implements OnInit, OnDestroy {
  @Input() quadroEconomico?: any; // Dato opzionale
  @Input() appaltoIntegrato: boolean = false; // Inizializza con un valore predefinito
  quadroEconomicoList: ClassificazioneTree[] = [];
  @Input() form!: FormGroup;
  @Input() isVisualizzazione: boolean = false;
  @Input() isRequired: boolean = false;
  @Input() idIntervento?: any;
  private updatingForm: boolean = false; // Flag per prevenire loop ricorsivi
  quadroEconomicoArray!: FormArray; // Proprietà per il FormArray
  natural=NgxCurrencyInputMode.Natural;
  previousAppaltoIntegrato: boolean;
  subscribeValueChange:any;

  errorMessages: { [key: string]: { [key: string]: string } } = {
    QE_D1: { outOfBounds: 'Importo non corretto: tale cifra, comprensiva di IVA, non deve superare il 2% del costo complessivo dell\'opera.' },
    QE_E5: { outOfBounds: 'Importo non corretto: tale cifra, comprensiva di IVA e altri oneri, deve essere ricompresa fra il 0% e il 10% dell\'importo dei lavori a base di gara, comprensivi dei costi della sicurezza.' },
    QE_E20: { outOfBounds: 'Importo non corretto: tale cifra deve essere ricompresa fra lo 0% e il 4% di a0.' },
    "QE_TOT_a+b+c+d+e+f-g": { outOfBounds: 'Il quadro economico deve essere valorizzato.' }
  };

  constructor(
    private registryApiService: RegistryApiService,
    private fb: FormBuilder,
    private projectApiService: ProjectApiService
  ) { }

  ngOnInit(): void {
    console.log("appalto integrato in quadro figlio" + this.appaltoIntegrato);
    this.loadQuadroEconomicoData();
    //this.inizializzaA0();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['appaltoIntegrato']) {
      if (changes['appaltoIntegrato'].currentValue) {
        this.handleAppaltoIntegratoTrue();
      } else {
        this.handleAppaltoIntegratoFalse();
      }
    }
  }

  handleAppaltoIntegratoTrue(): void {
    this.updateFormForAppaltoIntegrato();
  }

  handleAppaltoIntegratoFalse(): void {
    this.updateFormForNonAppaltoIntegrato();
  }

  updateFormForAppaltoIntegrato(): void {
    const control = this.getControl('QE_0A');
    if (control) {
      const targetControl = control.get('valoreNumerico');
      if (targetControl) {
        targetControl.enable();
        // console.log('Campo QE_0A reso editabile e impostato a null.');
      } else {
        // console.error('Control "valoreNumerico" non trovato per QE_0A.');
      }
    }
  }

  updateFormForNonAppaltoIntegrato(): void {
    const getDecimals = (controlName: string): number => {
      return Number(this.getControl(controlName)?.get('classifTreeImportoDecimali')?.value) || 2;
    };
    const control = this.getControl('QE_0A');
    if (control) {
      const targetControl = control.get('valoreNumerico');
      if (targetControl) {
        targetControl.disable();
        const arrotondamentoA0 = getDecimals('QE_0A');
        const a0 = parseFloat((Number(0)).toFixed(arrotondamentoA0));
        this.setControlValue('QE_0A', a0);
        console.log('Campo QE_0A disabilitato e impostato a 0.');
      } else {
        console.error('Control "valoreNumerico" non trovato per QE_0A.');
      }
    }
  }

  loadQuadroEconomicoData(): void {
    this.registryApiService.getClassificazioneTreeList('QE')
    .pipe(
      tap(quadroEconomicoTree => {
        this.quadroEconomicoList = quadroEconomicoTree;
      })  )  .subscribe(() => {
      this.createForm();
      this.buildQuadroEconomico();
    });
  }

  createForm(): void {
    // Aggiungi il FormArray al FormGroup principale se non esiste
    if (!this.form.contains('quadroEconomicoArray')) {
      this.form.addControl('quadroEconomicoArray', this.fb.array([]));
    }

    // Assegna il FormArray alla proprietà
    this.quadroEconomicoArray = this.form.get('quadroEconomicoArray') as FormArray;

    // Pulisci il FormArray per evitare duplicati
    //this.quadroEconomicoArray.clear();

    // Popola il FormArray con FormGroup per ogni elemento della lista
    if (this.quadroEconomicoArray.length === 0) {
      this.quadroEconomicoList.forEach(item => {
        if (item.classifElemCod) {
          this.quadroEconomicoArray.push(this.createItemFormGroup(item));
        } else {
          console.warn(`Elemento senza classifElemCod:`, item);
        }
      });

      //se id intervento è valorizzato vuol dire che potrebbero esserci dei dati con cui prepopolare il quadro economico
      if (this.idIntervento != null && this.idIntervento !== undefined) {
        this.projectApiService.getStruttureInterventoList(this.idIntervento, true).subscribe(strutture => {
          if (strutture.length) {
            for (let struttura of strutture) {
              if (this.form.getRawValue().struttura.strId === struttura.strId) {
                for (let key in struttura.quadroEconomico) {
                  for (let [index, controllo] of this.quadroEconomicoArray.controls.entries()) {
                    if (Number(key) === controllo.value.classifTreeId) {
                      let value = struttura.quadroEconomico[key];
                      let valoreNumericoString = String(value).replace(',', '.'); // Sostituisci la virgola con il punto
                      let valoreNumerico = Number(valoreNumericoString);
                      let valoreDaPopolare = controllo.value;
                      let decimals = valoreDaPopolare.classifTreeImportoDecimali;

                      if (!isNaN(valoreNumerico)) { // Controlla che la conversione sia riuscita
                        let valore = valoreNumerico.toFixed(decimals);
                        (controllo as FormGroup).patchValue({ valoreNumerico: valore });
                      } else {
                        // console.log("Conversione non riuscita: " + value);
                        // console.log(valoreDaPopolare);
                      }
                    }
                  }
                }
              }
            }
          }
        });
      }
      this.inizializzaA0();
    } else {
      // console.log('Il FormArray quadroEconomicoArray è già popolato.');
    }
  }

  createItemFormGroup(item: ClassificazioneTree): FormGroup {
    const group = this.fb.group({
      classifTreeId: [item.classifTreeId],
      classifTsId: [item.classifTsId],
      livello: [item.livello],
      descrizione: [item.descrizione, Validators.required],
      selezionabile: [item.selezionabile],
      classifTreeImportoDecimali: [item.classifTreeImportoDecimali],
      classifTreeConImporto: [item.classifTreeConImporto],
      classifTreeEditabile: [item.classifTreeEditabile],
      classifElemCod: [item.classifElemCod],
      valoreNumerico: [
        {
          value: (0).toFixed(item.classifTreeImportoDecimali || 0),
          disabled: !item.classifTreeEditabile
        },
        [
          Validators.min(0),
          ...(item.classifTreeEditabile && this.isRequired ? [Validators.required] : [])
        ]
      ],
      classifEtichetta: [item.classifEtichetta],
      classifSimbolo: [item.classifSimbolo]
    });

    return group;
  }

  getControl(key: string): FormGroup | null {
    const formArray = this.form.get('quadroEconomicoArray') as FormArray;
    if (!formArray) {
      return null;
    }

    const control = formArray.controls.find(
      group => group.get('classifElemCod')?.value === key
    ) as FormGroup | null;

    return control;
  }

  allowOnlyNumbers(event: KeyboardEvent): void {
    const charCode = event.which ? event.which : event.keyCode;
    const char = String.fromCharCode(charCode);

    // Consenti numeri (0-9) e la virgola (,)
    if (!/[0-9,]/.test(char) && charCode > 31) {
      event.preventDefault();
    }
  }

  getMarginFromSpazi(livello: number): number {
    return livello * 20;
  }

  setControlValue(code: string, value: any): void {
    const control = this.getControl(code);
    if (control) {
      const targetControl = control.get('valoreNumerico');
      if (targetControl) {
        const decimal = control?.get('classifTreeImportoDecimali')?.value;
        const formattedValue = parseFloat(value).toFixed(decimal);
        targetControl.setValue(formattedValue, { emitEvent: false });
      } else {
        console.error(`Control "valoreNumerico" non trovato in "${code}".`);
      }
    }
  }

  inizializzaA0(): void {
    if (this.appaltoIntegrato) {
      this.handleAppaltoIntegratoTrue();
    } else {
      this.handleAppaltoIntegratoFalse();
    }
  }

  buildQuadroEconomico(): void {
    this.subscribeValueChange = this.form.valueChanges.subscribe(values => {
      if (this.updatingForm) {
        return;
      }
      this.updatingForm = true;

      try {
        //const currentAppaltoIntegrato = !!this.form.get('appaltoIntegrato')?.value;
        const currentAppaltoIntegrato = this.appaltoIntegrato;

        if (currentAppaltoIntegrato !== this.previousAppaltoIntegrato) {
          this.previousAppaltoIntegrato = currentAppaltoIntegrato;

          if (currentAppaltoIntegrato) {
            this.handleAppaltoIntegratoTrue();
          } else {
            this.handleAppaltoIntegratoFalse();
          }
        }

        const getDecimals = (controlName: string): number => {
          return Number(this.getControl(controlName)?.get('classifTreeImportoDecimali')?.value) || 2;
        };

        // Calcolo a0 arrotondato dinamicamente
        const arrotondamentoA0 = getDecimals('QE_0A');
        const a0 = parseFloat(
          (Number(this.getControl('QE_0A')?.get('valoreNumerico')?.value) || 0).toFixed(arrotondamentoA0)
        );

        // Calcolo totaleA arrotondato dinamicamente
        const arrotondamentoA = getDecimals('QE_TOT_a');
        const totaleA = parseFloat(
          (
            (Number(this.getControl('QE_A1')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_A2')?.get('valoreNumerico')?.value) || 0)
          ).toFixed(arrotondamentoA)
        );
        this.setControlValue('QE_TOT_a', totaleA);

        // Calcolo totaleAB arrotondato dinamicamente
        const arrotondamentoAB = getDecimals('QE_TOT_a+b');
        const totaleAB = parseFloat(
          (totaleA + a0 + (Number(this.getControl('QE_B')?.get('valoreNumerico')?.value) || 0)).toFixed(arrotondamentoAB)
        );
        this.setControlValue('QE_TOT_a+b', totaleAB);

        // Calcolo totaleABC arrotondato dinamicamente
        const arrotondamentoABC = getDecimals('QE_TOT_a+b+c');
        const totaleABC = parseFloat(
          (totaleAB + (Number(this.getControl('QE_C')?.get('valoreNumerico')?.value) || 0)).toFixed(arrotondamentoABC)
        );
        this.setControlValue('QE_TOT_a+b+c', totaleABC);

        // Calcolo totaleD1D2 arrotondato dinamicamente
        const arrotondamentoD1D2 = getDecimals('QE_TOT_d');
        const totaleD1D2 = parseFloat(
          (
            (Number(this.getControl('QE_D1')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_D2')?.get('valoreNumerico')?.value) || 0)
          ).toFixed(arrotondamentoD1D2)
        );
        this.setControlValue('QE_TOT_d', totaleD1D2);

        // Calcolo totaleABCD arrotondato dinamicamente
        const arrotondamentoABCD = getDecimals('QE_TOT_a+b+c+d');
        const totaleABCD = parseFloat(
          (totaleABC + totaleD1D2).toFixed(arrotondamentoABCD)
        );
        this.setControlValue('QE_TOT_a+b+c+d', totaleABCD);

        // Imposto E18 arrotondato dinamicamente
        this.setControlValue('QE_E18', 0.00);

        const valoreQE_E20 = this.getControl('QE_E20')?.get('valoreNumerico');

        // Imposto E21 arrotondato dinamicamente
        // if (valoreQE_E20 && valoreQE_E20.value) {
        //   const arrotondamentoE21 = getDecimals('QE_E21');
        //   const e21 = parseFloat((IVA * (a0 + parseFloat(valoreQE_E20.value))).toFixed(arrotondamentoE21));
        //   this.setControlValue('QE_E21', e21);
        // } else {
        //   const arrotondamentoE21 = getDecimals('QE_E21');
        //   const e21 = parseFloat((IVA * a0).toFixed(arrotondamentoE21));
        //   this.setControlValue('QE_E21', e21);
        // }

        //imposto il valore del totale di e
        const arrotondamentoE = getDecimals('QE_TOT_e');
        const totaleE = parseFloat(
          (
            (Number(this.getControl('QE_E1')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E2')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E3')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E4')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E5')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E6')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E7')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E8')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E9')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E10')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E11')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E12')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E13')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E14')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E15')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E16')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E17')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E19')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E20')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_E21')?.get('valoreNumerico')?.value) || 0)
          ).toFixed(arrotondamentoE)
        );
        this.setControlValue('QE_TOT_e', totaleE);

        // Calcolo F3 arrotondato dinamicamente
        const arrotondamentoF3 = getDecimals('QE_F3');
        const totaleF1F2 = parseFloat(
          (
            (Number(this.getControl('QE_F1')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_F2')?.get('valoreNumerico')?.value) || 0)
          ).toFixed(arrotondamentoF3)
        );
        // const F3 = parseFloat((IVA * totaleF1F2).toFixed(arrotondamentoF3));
        // this.setControlValue('QE_F3', F3);

        //imposto il valore del totale di f
        const arrotondamentoQE_TOT_f = getDecimals('QE_TOT_f');
        const totaleF = parseFloat(
          (
            (Number(this.getControl('QE_F1')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_F2')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_F3')?.get('valoreNumerico')?.value) || 0)
          ).toFixed(arrotondamentoQE_TOT_f)
        );
        this.setControlValue('QE_TOT_f', totaleF);

        //imposto il totale di a+b+c+d+e+f
        const arrotondamentoQE_TOT_abcdef = getDecimals('QE_TOT_a+b+c+d+e+f');
        const totABCDEF = (totaleABCD + totaleE + totaleF).toFixed(arrotondamentoQE_TOT_abcdef);
        this.setControlValue('QE_TOT_a+b+c+d+e+f', totABCDEF);

        //imposto il campo g2
        const arrotondamentoG2 = getDecimals('QE_G2');
        let valoreG1 = Number(this.getControl('QE_G1')?.get('valoreNumerico')?.value) || 0;

        const G2 = parseFloat(
          (
            (valoreG1 / 100) *
            totaleA
          ).toFixed(arrotondamentoG2)
        );
        this.setControlValue('QE_G2', G2);

        //imposto il campo g3
        // const arrotondamentoG3 = getDecimals('QE_G3');
        // const G3 = parseFloat(
        //   (
        //     GLIMIT *
        //     G2
        //   ).toFixed(arrotondamentoG3)
        // );
        // this.setControlValue('QE_G3', G3);

        //imposto il campo g5
        const arrotondamentoG5 = getDecimals('QE_G5');
        const G5 = parseFloat(
          (
            ((Number(this.getControl('QE_G4')?.get('valoreNumerico')?.value) || 0) *
              totaleF1F2) / 100
          ).toFixed(arrotondamentoG5)
        );
        this.setControlValue('QE_G5', G5);

        //imposto il campo g6
        // const arrotondamentoG6 = getDecimals('QE_G6');
        // const G6 = parseFloat(
        //   (
        //     IVA *
        //     (Number(this.getControl('QE_G5')?.get('valoreNumerico')?.value) || 0)
        //   ).toFixed(arrotondamentoG6)
        // );
        // this.setControlValue('QE_G6', G6);

        //imposto il campo g8
        const arrotondamentoG8 = getDecimals('QE_G8');
        const G8 = parseFloat(
          (
            ((Number(this.getControl('QE_G7')?.get('valoreNumerico')?.value) || 0) *
              a0) / 100
          ).toFixed(arrotondamentoG8)
        );
        this.setControlValue('QE_G8', G8);

        //imposto il campo g9
        // const arrotondamentoG9 = getDecimals('QE_G9');
        // const G9 = parseFloat(
        //   (
        //     IVA *
        //     (Number(this.getControl('QE_G8')?.get('valoreNumerico')?.value) || 0)
        //   ).toFixed(arrotondamentoG9)
        // );
        // this.setControlValue('QE_G9', G9);

        //imposto il totale di g
        const arrotondamentoTotG = getDecimals('QE_TOT_g');
        const totaleG = parseFloat(
          (
            (Number(this.getControl('QE_G2')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_G3')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_G5')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_G6')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_G8')?.get('valoreNumerico')?.value) || 0) +
            (Number(this.getControl('QE_G9')?.get('valoreNumerico')?.value) || 0)
          ).toFixed(arrotondamentoTotG)
        );
        this.setControlValue('QE_TOT_g', totaleG);

        //imposto il totale di a+b+c+d+e+f
        const arrotondamentoQE_TOT_abcdefg = getDecimals('QE_TOT_a+b+c+d+e+f-g');
        const totABCDEFG = (totaleABCD + totaleE + totaleF - totaleG).toFixed(arrotondamentoQE_TOT_abcdefg);
        this.setControlValue('QE_TOT_a+b+c+d+e+f-g', totABCDEFG);

        const totaleQuadro = this.getControl('QE_TOT_a+b+c+d+e+f-g')?.get('valoreNumerico');
        const totControllo = parseFloat((totaleABCD + totaleE + totaleF - totaleG).toFixed(arrotondamentoQE_TOT_abcdefg));
        if(!this.isRequired) {
          //aggiorna obbligatorietà del form il base al valore del totale se diverso da 0 i campi editabili diventano obbligatori
          if (totControllo !== 0) {
            this.updateFieldValidators(true); // Rendi i campi obbligatori
          } else {
            this.updateFieldValidators(false); // Rendi i campi non obbligatori
          }
        } else {
          if (totaleQuadro) {
            //mi trovo nel modulo A il totale del quadro economico deve essere maggiore di 0
            if (totControllo == 0) {
              totaleQuadro.setErrors({ outOfBounds: { totControllo, valore: totControllo.toString() } });
            } else {
              totaleQuadro.setErrors(null);
            }
          }
        }

        // Controllo su D1
        const limiteD1 = (DLIMIT * ((totaleABC) + ((IVA) * (totaleABC))));
        const valoreQE_D1 = this.getControl('QE_D1')?.get('valoreNumerico');
        if (valoreQE_D1) {
          const valore = parseFloat(valoreQE_D1.value) || 0;
          if (valore) {
            if (valore > limiteD1) {
              valoreQE_D1.setErrors({ outOfBounds: { limiteD1, valore } });
            } else {
              valoreQE_D1.setErrors(null);
            }
          }
        }

        //Controllo su E5
        const limiteInferioreE5 = (ELIMITMIN * (totaleAB));
        const limiteSuperioreE5 = (ELIMITMAGG * (totaleAB));
        const valoreQE_E5 = this.getControl('QE_E5')?.get('valoreNumerico');
        if (valoreQE_E5) {
          const valore = valoreQE_E5.value || 0;
          if (valore!=null) {
            if (valore < limiteInferioreE5 || valore > limiteSuperioreE5) {
              valoreQE_E5.setErrors({
                outOfBounds: { limiteInferioreE5, limiteSuperioreE5, valore },
              });
            } else {
              valoreQE_E5.setErrors(null);
            }
          }
        }

        // Controllo su E20
        const limiteE20 = ELIMITMED * a0;
        if (valoreQE_E20) {
          const valore = parseFloat(valoreQE_E20.value) || 0;
          if (valore) {
            if (valore > limiteE20) {
              valoreQE_E20.setErrors({ outOfBounds: { limiteE20, valore } });
            } else {
              valoreQE_E20.setErrors(null);
            }
          }
        }

      } finally {
        this.updatingForm = false;
      }
    });
  }

  getErrorMessage(itemCode: string): string {
    const messages = this.errorMessages[itemCode];
    return messages['outOfBounds'];
  }

  roundToDecimals(controlKey: string, decimals: number): void {
    const control = this.getControl(controlKey)?.get('valoreNumerico');
    if (control) {
      const value = parseFloat(control.value); // Converte il valore in numero
      if (!isNaN(value)) {
        control.setValue(value.toFixed(decimals));
      }
    }
  }

  getNumero(value: string): number {
    return parseFloat(value);
  }

  //aggiorna l'obbligatorietà dei campi del form
  updateFieldValidators(isRequired: boolean): void {
    const formArray = this.form.get('quadroEconomicoArray') as FormArray;
    if (!formArray) {
      return;
    }

    formArray.controls.forEach(control => {
      const valoreNumericoControl = control.get('valoreNumerico');
      const classifTreeEditabile = control.get('classifTreeEditabile');
      if(classifTreeEditabile) {
        if (valoreNumericoControl) {
          const validators = [Validators.min(0)];
          if (isRequired) {
            validators.push(Validators.required);
          }
          valoreNumericoControl.setValidators(validators);
          valoreNumericoControl.updateValueAndValidity();
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.subscribeValueChange.unsubscribe();
  }

}
