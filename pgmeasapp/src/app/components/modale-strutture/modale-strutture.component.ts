/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, Inject } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ClassificazioneTree } from '@pgmeas-library/model';
import { Struttura } from '@pgmeas-library/model/src/struttura';
import { StrutturaInserimentoIntervento } from '@pgmeas-library/model/src/struttura-inserimento-intervento';
import { StrutturaNonCensita } from '@pgmeas-library/model/src/struttura-non-censita';
import { combineLatest } from 'rxjs';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { RegistryApiService } from 'src/app/services/registry-api.service';
import { codiceFiscaleValidator } from 'src/app/utils/codice-fiscale-validator';

@Component({
  selector: 'app-modale-strutture',
  templateUrl: './modale-strutture.component.html',
  styleUrls: ['./modale-strutture.component.scss']
})
export class ModaleStruttureComponent {
  form: FormGroup;
  isSubmitted = false; // Flag per controllare la validazione
  strutturaControl = new FormControl();
  interventoEdilizioList: ClassificazioneTree[];
  quadroEconomicoList: ClassificazioneTree[];
  strutturaDaAggiungere: any;
  salvataggioEffettuato: boolean = false;
  isClosing = false; // Flag per evitare ricorsioni

  constructor(private fb: FormBuilder,
    private dialogRef: MatDialogRef<ModaleStruttureComponent>,
    private projectApiService: ProjectApiService,
    private registryApiService: RegistryApiService,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {

    combineLatest([
      this.registryApiService.getClassificazioneTreeList('IE'),
      this.registryApiService.getClassificazioneTreeList('QE')
    ]).subscribe(
      ([
        interventoEdilizioTree,
        quadroEconomicoTree
      ]) => {
        this.interventoEdilizioList = interventoEdilizioTree;
        this.interventoEdilizioList.forEach(intervento => {
          intervento.selezionato = false;
        })
        this.quadroEconomicoList = quadroEconomicoTree;
        this.quadroEconomicoList.forEach(quadro => {
          quadro.valoreNumerico = '';
        })
      }
    );

    this.form = this.fb.group({
      strutturaTipo: [false],
      denominazione: ['', Validators.required],
      comune: ['', Validators.required],
      indirizzo: [''],
      datiCatastali: [''],
      nota: ['']
    });

    // Imposta un valore di default prima che la modale si chiuda
    this.dialogRef.beforeClosed().subscribe(() => {
      if (!this.isClosing) {
        this.isClosing = true; // Previeni ulteriori chiamate a close()
        this.dialogRef.close(this.strutturaDaAggiungere || false);
      }
    });
  }

  groupValidator(): ValidationErrors | null {
    const indirizzo = this.form.get('indirizzo')?.value;
    const datiCatastali = this.form.get('datiCatastali')?.value;

    // Normalizzazione dei valori (rimuove spazi iniziali/finali)
    const indirizzoPresent = !!indirizzo?.trim();
    const datiCatastaliPresent = !!datiCatastali?.trim();

    // Logica: deve essere presente solo uno dei due campi
    if (indirizzoPresent && datiCatastaliPresent) {
      return { invalidCombination: true }; // Errore se entrambi sono presenti
    }

    if (!indirizzoPresent && !datiCatastaliPresent) {
      return { invalidCombination: true }; // Errore se nessuno è presente
    }

    return null; // Nessun errore
  }

  closeModal(event?: Event): void {
    if (event) {
      this.form.markAsUntouched();
      event.preventDefault();
      event.stopPropagation();
    }

    // Emetti il valore quando la modale viene chiusa
    this.dialogRef.close(this.strutturaDaAggiungere);
  }

  populateInterventiEdilizi(formGroup: FormGroup) {
    const listaInterventiEdiliziArray = formGroup.get('listaInterventiEdilizi') as FormArray;
    this.interventoEdilizioList.forEach(intervento => {
      const interventoGroup = this.fb.group({
        id: [intervento.classifTreeId],
        selezionato: [intervento.selezionato],
        livello: [intervento.livello],
        descrizione: [intervento.descrizione],
        selezionabile: [intervento.selezionabile]
      });
      listaInterventiEdiliziArray.push(interventoGroup);
    });
  }

  populateQuadroEconomico(formGroup: FormGroup) {
    const listaQuadroEconomicoArray = formGroup.get('listaQuadroEconomico') as FormArray;
    this.quadroEconomicoList.forEach(quadro => {
      const interventoGroup = this.fb.group({
        id: [quadro.classifTreeId],
        valoreNumerico: [quadro.valoreNumerico, [Validators.min(0)]],
        livello: [quadro.livello],
        descrizione: [quadro.descrizione],
        selezionabile: [quadro.selezionabile]
      });
      listaQuadroEconomicoArray.push(interventoGroup);
    });
  }

  private calcolaTotale(formGroup: FormGroup): void {

    const progettazione = formGroup.get('progettazione')?.value || 0;
    const affidamento = formGroup.get('affidamentoLavori')?.value || 0;
    const esecuzione = formGroup.get('esecuzioneLavori')?.value || 0;
    const collaudo = formGroup.get('collaudo')?.value || 0;

    const totale = progettazione + affidamento + esecuzione + collaudo;

    // Aggiorna il campo totale
    formGroup.get('totaleDurataStimataIntervento')?.setValue(totale, { emitEvent: false });
  }

  addStruttura(struttura: any) {
    const strutturaSelezionata = struttura as Struttura | null;
    if (strutturaSelezionata) {
      const strutturaFormGroup = this.fb.group({
        struttura: [strutturaSelezionata],
        costoStruttura: ['', [Validators.required, Validators.min(0)]],
        progettazione: ['', [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
        affidamentoLavori: ['', [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
        esecuzioneLavori: ['', [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
        collaudo: ['', [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
        totaleDurataIntervento: [0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
        appaltoIntegrato: [false, Validators.required],
        totaleDurataStimataIntervento: [0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
        listaInterventiEdilizi: this.fb.array([]),
        listaQuadroEconomico: this.fb.array([]),
        intstrRespStrComplesNome: [''],
        intstrRespStrComplesCognome: [''],
        intstrRespStrComplesCf: ['', codiceFiscaleValidator()],
        intstrRespStrSemplNome: [''],
        intstrRespStrSemplCognome: [''],
        intstrRespStrSemplCf: ['', codiceFiscaleValidator()],
        quadroEconomico: this.fb.group({})
      });
      this.populateInterventiEdilizi(strutturaFormGroup);
      this.populateQuadroEconomico(strutturaFormGroup);
      strutturaFormGroup.valueChanges.subscribe(() => {
        this.calcolaTotale(strutturaFormGroup);
      });

      this.data.formStrutture.push(strutturaFormGroup);
    }

    this.strutturaControl.reset();
  }


  onSubmit(): void {
    this.isSubmitted = true; // Indica che il form è stato inviato

    const groupErrors = this.groupValidator();
    if (groupErrors) {
      this.form.setErrors(groupErrors); // Applica gli errori personalizzati al form
    }

    let strutturaNonCensita: boolean = false;
    let strutturaNuova: boolean = false;
    if (this.form.get('strutturaTipo')?.value) {
      strutturaNonCensita = true;
      strutturaNuova = false;
    } else {
      strutturaNonCensita = false;
      strutturaNuova = true;
    }

    if (this.form.valid) {
      let strutturaDaCensire: StrutturaNonCensita = {
        strNonCensita: strutturaNonCensita,
        strNuova: strutturaNuova,
        strDenominazione: this.form.get('denominazione')?.value,
        strComune: this.form.get('comune')?.value,
        strIndirizzo: this.form.get('indirizzo')?.value,
        strDatiCatastali: this.form.get('datiCatastali')?.value,
        note: this.form.get('nota')?.value,
        enteId: this.data.enteId
      }
      this.projectApiService.censisciStruttura(strutturaDaCensire).subscribe(save => {
        this.addStruttura(save);
        this.strutturaDaAggiungere = save;
        this.salvataggioEffettuato = true;
      }, err => {

      })
    } else {
      console.log('Form invalid');
    }
  }
}
