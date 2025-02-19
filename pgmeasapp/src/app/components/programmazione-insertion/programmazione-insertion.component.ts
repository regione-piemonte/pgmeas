/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { MessageService } from 'src/app/services/message.service';
import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { ClassificazioneTree, Ente, Intervento, InterventoAppaltoTipo, InterventoCategoria, InterventoContrattoTipo, InterventoFinalita, InterventoObiettivo, InterventoStatoProgettuale, InterventoStruttura, InterventoTipo, InterventoTipoDet, Struttura, UserInfo } from '@pgmeas-library/model';
import { combineLatest, debounceTime, distinctUntilChanged, map, Observable, startWith } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { AbstractControl, FormArray, FormArrayName, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RegistryApiService } from 'src/app/services/registry-api.service';
import { StrutturaInserimentoIntervento } from '@pgmeas-library/model/src/struttura-inserimento-intervento';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { MatDialog } from '@angular/material/dialog';
import { ModaleConfermaComponent } from '../programmazione/modale-conferma/modale-conferma.component';
import { ModaleCopiaInterventoComponent } from './modale-copia-intervento/modale-copia-intervento.component';
import { InterventoCopy } from '@pgmeas-library/model/src/intervento-copy';
import { InterventoDTO, InterventoStrutturaDTO } from '@pgmeas-library/model/src/intervento-salvataggio';
import { yearRangeValidator } from 'src/app/utils/yearRangeValidator';
import { codiceFiscaleValidator } from 'src/app/utils/codice-fiscale-validator';
import { emailValidator } from 'src/app/utils/email-validator';
import { ErrorService } from 'src/app/services/error.service';
import { CommonModule } from '@angular/common';
import { QuadroEconomicoCentralizedComponent } from '../quadro-economico-centralized/quadro-economico-centralized.component';
import { QuadroEconomicoComponent } from '../programmazione/gestione/quadro-economico/quadro-economico.component';
import { quadroEconomicoService } from 'src/app/services/quadroEconomico.service';
import { NgxCurrencyInputMode } from 'ngx-currency';

const ACQUISTO_ATTREZZATURE = 'ACQ_ATTR';
@Component({
  selector: 'app-programmazione-insertion',
  templateUrl: './programmazione-insertion.component.html',
  styleUrls: ['./programmazione-insertion.component.scss'],
  providers: [DatePipe],

})
export class ProgrammazioneInsertionComponent {
  maxDate = new Date();
  title = 'Inserisci intervento';
  user: UserInfo;
  loading = true;
  today: string;
  enteList: Ente[];
  strutturaList: Struttura[];
  interventoTipoList: InterventoTipo[];
  erroreDuplicato: boolean = false;
  interventoEdilizioList: ClassificazioneTree[];
  quadroEconomicoList: ClassificazioneTree[];
  insertionForm: FormGroup;
  obiettivoList: InterventoObiettivo[];
  finalitaList: InterventoFinalita[];
  categoriaList: InterventoCategoria[];
  contrattoTipoList: InterventoContrattoTipo[];
  appaltoTipoList: InterventoAppaltoTipo[];
  statoProgList: InterventoStatoProgettuale[];
  attrezzaturaList: InterventoTipoDet[];
  fileName: string | null = null;
  fileUrl: string | null = null;
  file: File | null = null;
  intCopiatoId: number | null = null;
  intCopiatoEnteId: number | null = null;
  salvataggioEffettuato: boolean = false;
  intId: number | null = null;
  isSubmitting = false;
  natural=NgxCurrencyInputMode.Natural;
  strutturaCensita: boolean = true;

  get struttureAggiunte(): FormArray {
    return this.insertionForm.get('struttureAggiunte') as FormArray;
  }


  strutturaControl = new FormControl(); // FormControl per il campo
  strutturaFilteredList: Observable<Struttura[]>; // Lista filtrata


  constructor(
    private userService: UserService,
    private datePipe: DatePipe,
    private projectApiService: ProjectApiService,
    private registryApiService: RegistryApiService,
    private fb: FormBuilder,
    public dialog: MatDialog,
    public messageService: MessageService,
    public errorService: ErrorService,
    public quadroEconomicoService: quadroEconomicoService
  ) { }

  ngOnInit() {
    this.today = this.datePipe.transform(new Date(), 'dd/MM/yyyy')!;
    this.user = this.userService.getUser();
    // Configura l'Observable
    this.strutturaFilteredList = this.strutturaControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      map(value => typeof value === 'string' ? value.toLowerCase() : value?.strDenominazione.toLowerCase()),
      map(name => name ? this._filter(name) : [...this.strutturaList])
    );


    this.insertionForm = this.fb.group({
      struttureAggiunte: this.fb.array([]),
      cup: ['', [Validators.minLength(15), Validators.maxLength(15)]],
      codInterventoPGMEAS: [{ value: '', disabled: true }],
      codInterventoOriginePGMEAS: [{ value: '', disabled: true }],
      annoInserimentoInterventoOrigine: [{ value: null, disabled: true }, [Validators.min(0), Validators.pattern('^[0-9]*$')]],
      codNSISIntervento: [''],
      titoloIntervento: ['', Validators.required],
      obiettivo: [[], Validators.required],
      finalita: [[], Validators.required],
      tipologia: [[], Validators.required],
      categoria: [[], Validators.required],
      attrezzatura: [{ value: [], disabled: true }],
      annoPriorita: ['', [Validators.required, yearRangeValidator(new Date().getFullYear()), Validators.pattern('^[0-9]{4}$')]],
      priorita: ['', [Validators.required]],
      sottoPriorita: [''],
      contrattoTipo: [[], Validators.required],
      appaltoTipo: [[], Validators.required],
      statoProg: [[]],
      costoIntervento: ['', [Validators.required, Validators.min(0)]],
      progettazione: ['', [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      affidamentoLavori: ['', [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      esecuzioneLavori: ['', [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      collaudo: ['', [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      appaltoIntegrato: [false, Validators.required],
      totaleDurataIntervento: [0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      direttoreNome: [''],
      direttoreCognome: [''],
      direttoreCF: ['', codiceFiscaleValidator()],
      commissarioNome: [''],
      commissarioCognome: [''],
      commissarioCF: ['', codiceFiscaleValidator()],
      rupNome: ['', Validators.required],
      rupCognome: ['', Validators.required],
      rupCF: ['', [Validators.required, codiceFiscaleValidator()]],
      referenteNome: ['', Validators.required],
      referenteCognome: ['', Validators.required],
      referenteTelefono: ['', Validators.required],
      referenteEmail: ['', [Validators.required, emailValidator()]],
      referenteCF: ['', [Validators.required, codiceFiscaleValidator()]],
      delibera: [''],
      dataDelibera: [''],
      allegato: [null],
      nomeAllegato: [null],
      tipoAllegato: [null],
      note: [''],

    });

    this.insertionForm.get("tipologia")?.valueChanges
      .pipe()
      .subscribe(tipologie => {
        let presente = false;
        tipologie.forEach((id: number) => {
          this.interventoTipoList.forEach(tipo => {
            if (tipo.intTipoId == id && tipo.intTipoCod == ACQUISTO_ATTREZZATURE) {
              presente = true;
            }
          }
          )
        })
        if (presente) {
          this.insertionForm.get("attrezzatura")?.enable()
        } else {
          this.insertionForm.get("attrezzatura")?.reset();
          this.insertionForm.get("attrezzatura")?.disable();
        }

      })

    this.insertionForm.valueChanges.subscribe(() => {
      this.calcolaTotaleIntervento();
    });
    combineLatest([
      this.registryApiService.getStrutturaListByCodiceAzienda(this.user.codiceAzienda),
      this.registryApiService.getEnteList(),
      this.registryApiService.getInterventoTipoList(),
      this.registryApiService.getClassificazioneTreeList('IE'),
      this.registryApiService.getClassificazioneTreeList('QE'),
      this.registryApiService.getInterventoObiettivoList(),
      this.registryApiService.getInterventoFinalitaList(),
      this.registryApiService.getInterventoCategoriaList(),
      this.registryApiService.getInterventoContrattoTipoList(),
      this.registryApiService.getInterventoAppaltoTipoList(),
      this.registryApiService.getInterventoStatoProgettualeList(),
      this.registryApiService.getInterventoTipoDetList(ACQUISTO_ATTREZZATURE),
    ]).subscribe(
      ([
        strutturaList,
        enteList,
        interventoTipoList,
        interventoEdilizioTree,
        quadroEconomicoTree,
        obiettivoList,
        finalitaList,
        categoriaList,
        contrattoList,
        appaltoTipoList,
        statoProgList,
        attrezzaturaList,
      ]) => {
        this.strutturaList = strutturaList;
        this.enteList = enteList;
        this.interventoTipoList = interventoTipoList;
        this.interventoEdilizioList = interventoEdilizioTree;
        this.interventoEdilizioList.forEach(intervento => {
          intervento.selezionato = false;
        })
        this.quadroEconomicoList = quadroEconomicoTree;
        this.quadroEconomicoList.forEach(quadro => {
          quadro.valoreNumerico = '';
        })
        this.obiettivoList = obiettivoList;
        this.finalitaList = finalitaList;
        this.categoriaList = categoriaList;
        this.contrattoTipoList = contrattoList;
        this.appaltoTipoList = appaltoTipoList;
        this.statoProgList = statoProgList;
        this.attrezzaturaList = attrezzaturaList;
        this.loading = false;
      }
    );

    this.insertionForm.get('struttura')?.valueChanges.subscribe(() => {
      this.erroreDuplicato = false;
    });
  }

  private markInvalidControlsAsTouched(control: AbstractControl): void {
    if (control instanceof FormGroup || control instanceof FormArray) {
      Object.values(control.controls).forEach(childControl =>
        this.markInvalidControlsAsTouched(childControl)
      );
    } else if (control.invalid) {
      control.markAsTouched();
    }
  }

  submit() {
    this.blockSubmitting();
    if (this.insertionForm.get('cup')!.invalid) {
      this.messageService.error("Se valorizzato, il codice CUP deve essere di 15 caratteri");
      return;
    }

    if (this.insertionForm.valid) {
      const direttore = (this.insertionForm.get('direttoreNome')?.value && this.insertionForm.get('direttoreCognome')?.value && this.insertionForm.get('direttoreCF')?.value);
      const commissario = (this.insertionForm.get('commissarioNome')?.value && this.insertionForm.get('commissarioCognome')?.value && this.insertionForm.get('commissarioCF')?.value)
      const costoIntervento = this.insertionForm.get('costoIntervento')?.value;
      const totaleStrutture = this.getTotaleStrutture();
      const isResponsabiliConformi = this.isResponsabiliStrutturaConformi();
      const isAllegatoDeliberaCompliant = this.isDeliberaCompliant();
      if (parseFloat(costoIntervento) !== parseFloat(totaleStrutture)) {
        this.messageService.error("Il Costo complessivo dell'intervento deve corrispondere esattamente al costo della struttura, o, in caso di più strutture, alla somma dei costi delle strutture aggiunte. Si prega di verificare e correggere i valori inseriti.");
        return;
      }
      if (!direttore && !commissario) {
        this.messageService.error("L'inserimento dei dati relativi del Direttore Generale o del Commissario è obbligatorio.")
        return;
      }
      if (this.isNotDirettoreOrCommissarioPopulatedValidator()) {
        this.messageService.error("MSG-082: Valorizzare i dati del direttore generale o del commissario, non entrambi.")
        return;
      }

      if (!isResponsabiliConformi) {
        this.messageService.error("L'inserimento dei nominativi del Responsabile della Struttura Complessa e/o del Responsabile della Struttura Semplice è obbligatorio")
        return;
      }

      if (!isAllegatoDeliberaCompliant) {
        this.messageService.error("Delibere aziendali dell'intervento non valorizzata correttamente. Inserire tutti i valori")
        return;
      }

      this.salvataggioIntervento();
    } else {
      console.log('Invalido');
      this.markInvalidControlsAsTouched(this.insertionForm);
      this.checkFormValidity()
      return;
    }

    // if (this.searchForm.invalid) {

    //   return;
    // }
  }

  salvataggioIntervento() {
    this.loading = true;
    let formattedDate = null;
    const date = this.insertionForm.get('dataDelibera')?.value;
    if (date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0'); // I mesi sono zero-indicizzati
      const day = String(date.getDate()).padStart(2, '0');

      // Formatta come yyyy-mm-gg
      formattedDate = `${year}-${month}-${day}`;
    }

    let salvataggio: InterventoDTO = {
      interventoStrutturaList: this.buildInterventoStrutturaList(),
      intCup: this.insertionForm.get('cup')?.value,
      intCodPgmeas: this.insertionForm.get('codInterventoPGMEAS')?.value,
      intCodPgmeasOrig: this.insertionForm.get('codInterventoOriginePGMEAS')?.value,
      intAnnoOrig: this.insertionForm.get('annoInserimentoInterventoOrigine')?.value,
      intCodicNsis: this.insertionForm.get('codNSISIntervento')?.value,
      intTitolo: this.insertionForm.get('titoloIntervento')?.value,
      listaIntObiettivoId: this.insertionForm.get('obiettivo')?.value,
      listaIntFinalitaId: this.insertionForm.get('finalita')?.value,
      listaIntTipoId: this.insertionForm.get('tipologia')?.value,
      listaIntCategoriaId: this.insertionForm.get('categoria')?.value,
      listaIntTipoDetId: this.insertionForm.get('attrezzatura')?.value,
      intPrioritaAnno: this.insertionForm.get('annoPriorita')?.value,
      intPriorita: this.insertionForm.get('priorita')?.value,
      intSottopriorita: this.insertionForm.get('sottoPriorita')?.value,
      listaIntContrattoId: this.insertionForm.get('contrattoTipo')?.value,
      listaIntAppaltoTipoId: this.insertionForm.get('appaltoTipo')?.value,
      listaIntStatoProgettualeId: this.insertionForm.get('statoProg')?.value,
      intImporto: this.insertionForm.get('costoIntervento')?.value ? this.insertionForm.get('costoIntervento')?.value.toString().replace(',', '.') : '0.00',
      intProgettazioneGg: this.insertionForm.get('progettazione')?.value,
      intAffidamentoLavoriGg: this.insertionForm.get('affidamentoLavori')?.value,
      intEsecuzioneLavoriGg: this.insertionForm.get('esecuzioneLavori')?.value,
      intCollaudoGg: this.insertionForm.get('collaudo')?.value,
      intAppaltoIntegrato: this.insertionForm.get('appaltoIntegrato')?.value,
      intDirettoreGeneraleNome: this.insertionForm.get('direttoreNome')?.value,
      intDirettoreGeneraleCognome: this.insertionForm.get('direttoreCognome')?.value,
      intDirettoreGeneraleCf: this.insertionForm.get('direttoreCF')?.value,
      intCommissarioNome: this.insertionForm.get('commissarioNome')?.value,
      intCommissarioCognome: this.insertionForm.get('commissarioCognome')?.value,
      intCommissarioCf: this.insertionForm.get('commissarioCF')?.value,
      intRupNome: this.insertionForm.get('rupNome')?.value,
      intRupCognome: this.insertionForm.get('rupCognome')?.value,
      intRupCf: this.insertionForm.get('rupCF')?.value,
      intReferentePraticaNome: this.insertionForm.get('referenteNome')?.value,
      intReferentePraticaCognome: this.insertionForm.get('referenteCognome')?.value,
      intReferentePraticaTelefono: this.insertionForm.get('referenteTelefono')?.value,
      intReferentePraticaEmail: this.insertionForm.get('referenteEmail')?.value,
      intReferentePraticaCf: this.insertionForm.get('referenteCF')?.value,
      intNote: this.insertionForm.get('note')?.value,
      intAllegatoDelibera: {
        intAllegatoNumero: this.insertionForm.get('delibera')?.value,
        intAllegatoData: formattedDate,
        idAllegato: null,
        fileNameUser: this.insertionForm.get('nomeAllegato')?.value,
        fileType: this.insertionForm.get('tipoAllegato')?.value,
        base64: this.insertionForm.get('allegato')?.value,
      },
      intAnno: parseInt(this.user.programmazione.annoInserimentIntervento),
      intQuadranteId: parseInt(this.user.idQuadrante),
      intCopiatoEnteId: this.intCopiatoEnteId,
      intCopiatoId: this.intCopiatoId,
      intId: null,
      dataCreazione: null
    };
    this.projectApiService.saveIntervento(salvataggio).subscribe(save => {
      this.intId = save.intId;
      for (let i = 0; i < this.struttureAggiunte.controls.length; i++) {
        this.struttureAggiunte.removeAt(i);
      }
      this.removeFile();
      this.insertionForm.reset();
      this.salvataggioEffettuato = true;
      this.loading = false;
    }, err => {
      console.log(" errore");
      this.loading = false;
    })
  }

  buildInterventoStrutturaList(): InterventoStrutturaDTO[] {
    let listaStrutture: InterventoStrutturaDTO[] = [];
    let struttureComplete = this.struttureAggiunte.getRawValue();
    for (let str of struttureComplete) {
      let newStr: InterventoStrutturaDTO = {
        intStrAffidamentoLavoriGg: str.affidamentoLavori || 0,
        intStrAppaltoIntegrato: str.appaltoIntegrato || false,
        intStrCollaudoGg: str.collaudo || 0,
        intStrEsecuzioneLavoriGg: str.esecuzioneLavori || 0,
        intStrId: null,
        strId: str.struttura.strId,
        intStrImporto: str.costoStruttura ? str.costoStruttura.toString().replace(',', '.') : '0.00',
        intStrProgettazioneGg: str.progettazione || 0,
        intstrRespStrComplesCf: str.intstrRespStrComplesCf || '',
        intstrRespStrComplesCognome: str.intstrRespStrComplesCognome || '',
        intstrRespStrComplesNome: str.intstrRespStrComplesNome || '',
        intstrRespStrSemplCf: str.intstrRespStrSemplCf,
        intstrRespStrSemplCognome: str.intstrRespStrSemplCognome || '',
        intstrRespStrSemplNome: str.intstrRespStrSemplNome || '',
        intStrTipoIntEdilMap: this.buildinterventiEdiliziListMap(str.listaInterventiEdilizi || []),
        quadroEconMap: this.quadroEconomicoService.buildQuadroEconomicoMap(str.quadroEconomicoArray || [])
      };
      listaStrutture.push(newStr);
    }
    return listaStrutture;
  }

  buildQuadroEconomicoMap(quadroEconList: any[]): { [key: string]: number } {
    let quadroEconMap: { [key: string]: number } = {};

    quadroEconList.forEach(quadro => {
      let id = quadro.id;
      let valore = parseFloat((quadro.valoreNumerico || '0').replace(',', '.'));

      quadroEconMap[id] = valore;
    });

    return quadroEconMap;
  }

  buildinterventiEdiliziListMap(interventiEdiliziList: any[]): { [key: string]: boolean } {
    let interventiEdiliziMap: { [key: string]: boolean } = {};

    interventiEdiliziList.forEach(intEdil => {
      let id = intEdil.id;
      let selezionato = intEdil.selezionato;

      interventiEdiliziMap[id] = selezionato;
    });

    return interventiEdiliziMap;
  }

  disableInserisciStruttura(): boolean {
    return !(this.strutturaControl.value);
  }



  addStruttura() {
    this.loading = true;
    const strutturaSelezionata = this.strutturaControl.value as Struttura | null;
    const struttureAggiunteValori: StrutturaInserimentoIntervento[] = this.struttureAggiunte.getRawValue();
    this.erroreDuplicato = false;
    if (strutturaSelezionata) {
      if (!strutturaSelezionata.strDenominazione) {
        this.messageService.error("La struttura indicata non risulta tra quelle autorizzate nell'elenco predefinito.")
        this.strutturaControl.reset();
        this.loading = false;
        return;
      }
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

      if (struttureAggiunteValori.length) {
        const strutturaDuplicata = struttureAggiunteValori.find(x => x.struttura.strId === strutturaSelezionata.strId);

        if (strutturaDuplicata) {
          this.erroreDuplicato = true;
        } else {
          this.struttureAggiunte.push(strutturaFormGroup);
        }
      } else {
        this.struttureAggiunte.push(strutturaFormGroup);
      }
      this.loading = false;
    }

    this.strutturaControl.reset();
    this.loading = false;
  }

  removeStruttura(index: number) {
    const dialogRef = this.dialog.open(ModaleConfermaComponent, {
      width: '600px',
      data: { titolo: 'Conferma cancellazione struttura', messaggio: 'Sei sicuro di voler eliminare la struttura?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.struttureAggiunte.removeAt(index);
      }
    });

  }

  getMarginFromSpazi(livello: number): number {
    if (livello > 1)
      return livello * 20;  // 10px per ogni spazio, ad esempio
    return 0;
  }

  updateInterventoEdilizio(index: number, event: MatSlideToggleChange, i: number) {
    const updatedValue = (event.checked as boolean);
    let listaInterventi = this.struttureAggiunte.at(index).get('listaInterventiEdilizi') as FormArray;
    listaInterventi.value.at(i).selezionato = updatedValue;
  }

  updateQuadroEconomico(index: number, event: Event, i: number) {
    const updatedValue = (event.target as HTMLInputElement).value;
    let listaQueadro = this.struttureAggiunte.at(index).get('listaQuadroEconomico') as FormArray;
    listaQueadro.value.at(i).valoreNumerico = updatedValue;
  }

  private _filter(value: string | Struttura): Struttura[] {
    let filterValue: string;

    // Se il valore è una stringa, la usa direttamente per il filtro
    if (typeof value === 'string') {
      filterValue = value.toLowerCase();
    }
    // Se il valore è un oggetto Struttura, prende la sua denominazione
    else {
      filterValue = value.strDenominazione.toLowerCase();
    }

    // Applica il filtro
    return this.strutturaList.filter(option =>
      option.strDenominazione.toLowerCase().includes(filterValue)
    );
  }

  // Funzione di visualizzazione per l'autocomplete
  displayFn(struttura: any): string {
    return struttura
      ? struttura.strDenominazione +
      (struttura.strPgmeas === false
        ? " (" + struttura.strCod + ")"
        : " - Struttura non censita")
      : '';
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

  private calcolaTotaleIntervento(): void {
    const progettazione = this.insertionForm.get('progettazione')?.value || 0;
    const affidamento = this.insertionForm.get('affidamentoLavori')?.value || 0;
    const esecuzione = this.insertionForm.get('esecuzioneLavori')?.value || 0;
    const collaudo = this.insertionForm.get('collaudo')?.value || 0;

    const totale = progettazione + affidamento + esecuzione + collaudo;

    // Aggiorna il campo totale
    this.insertionForm.get('totaleDurataIntervento')?.setValue(totale, { emitEvent: false });
  }

  allowOnlyNumbers(event: KeyboardEvent): boolean {
    const charCode = event.which ? event.which : event.keyCode;

    // Consenti i numeri (0-9), virgola (44) e il tasto backspace (8)
    if (
      (charCode >= 48 && charCode <= 57) || // numeri
      charCode === 44 || // virgola
      charCode === 8 || // backspace
      charCode === 46 // punto (opzionale)
    ) {
      return true;
    } else {
      event.preventDefault();
      return false;
    }
  }

  onlyNumbers(event: KeyboardEvent): boolean {
    const charCode = event.which ? event.which : event.keyCode;

    // Consenti i numeri (0-9), virgola (44) e il tasto backspace (8)
    if (
      (charCode >= 48 && charCode <= 57)
    ) {
      return true;
    } else {
      event.preventDefault();
      return false;
    }
  }

  // Gestisce la selezione del file
  onFileSelected(event: any): void {
    const maxFileSize = 20 * 1024 * 1024; // 20MB in byte
    const input = event.target as HTMLInputElement;
    const file: File = event.target.files[0];
    if (file) {
      if (file.type != 'application/pdf') {
        this.messageService.error('Errore: il file deve essere in formato PDF.')
        input.value = ''; // Resetta l'input
        return;
      }  
      if (file.size > maxFileSize) {
        this.messageService.error('Errore: il file deve essere inferiore a 20MB.')
        input.value = ''; // Resetta l'input
        return;
      }
    }

    if (file) {
      this.fileName = file.name;
      this.file = file;
      this.fileToBase64(file).then(base64 => {
        this.insertionForm.patchValue({
          allegato: base64.split(',')[1],
          nomeAllegato: file.name,
          tipoAllegato: file.type
        });
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });
      this.fileUrl = URL.createObjectURL(file);
      input.value = ''; // Resetta l'input per permettere il cambio di file con stesso nome
    }
  }

  removeFile(): void {
    this.fileName = null;
    this.fileUrl = null;
    this.file = null;
    this.insertionForm.patchValue({
      allegato: null, // Imposta il file nel form,
      delibera: null,
      dataDelibera: null
    });
  }

  // DIALOG Recupera Intervento
  apriCopiaIntervento(): void {
    let anniSelezionabili: number[] = [(parseInt(this.user.programmazione.annoInserimentIntervento) - 2), (parseInt(this.user.programmazione.annoInserimentIntervento) - 1)];
    const dialogRef = this.dialog.open(ModaleCopiaInterventoComponent, {
      width: '90%',       // 90% della larghezza della finestra
      maxWidth: '100vw',  // Massima larghezza al 100% della viewport
      panelClass: 'custom-dialog-container',  // Classe CSS personalizzata
      data: { titolo: 'Ricerca intervento da inserire mediante copia', triennio: this.user.programmazione.triennio, anniSelezionabili: anniSelezionabili }
    });

    dialogRef.afterClosed().subscribe((intervento: InterventoCopy) => {
      this.loading = true;
      if (intervento) {
        this.intCopiatoEnteId = intervento.enteId;
        this.intCopiatoId = intervento.intId;
        let all = null;
        if (intervento.allegatoDeliberaApprovazione && intervento.allegatoDeliberaApprovazione.length > 0) {
          all = intervento.allegatoDeliberaApprovazione[0];
          const data = new Date(all.intAllegatoData); // Crea un oggetto Date
          // Formatta la data in "yyyy-mm-dd"
          const year = data.getFullYear();
          const month = String(data.getMonth() + 1).padStart(2, '0'); // i mesi vanno da 0 a 11
          const day = String(data.getDate()).padStart(2, '0');

          all.inteAllegatoDataFormated = data;
        }

        this.insertionForm.patchValue({
          struttureAggiunte: intervento.interventiStruttura,
          cup: intervento.intCup,
          codInterventoPGMEAS: '',
          codInterventoOriginePGMEAS: intervento.intCod,
          annoInserimentoInterventoOrigine: intervento.intAnno,
          codNSISIntervento: intervento.intCodicNsis,
          titoloIntervento: intervento.intTitolo,
          obiettivo: intervento.obiettivi,
          finalita: intervento.finalita,
          tipologia: intervento.tipi,
          categoria: intervento.categorie,
          annoPriorita: intervento.intPrioritaAnno,
          priorita: intervento.intPriorita,
          sottoPriorita: intervento.intSottopriorita,
          attrezzatura: intervento.descrizioniAttrezzature,
          contrattoTipo: intervento.contrattiTipo,
          appaltoTipo: intervento.appaltiTipo,
          statoProg: intervento.statiProgettuali,
          costoIntervento: intervento.intImporto.toString().replace('.', ','),
          progettazione: intervento.progettazioneGg || 0,
          affidamentoLavori: intervento.affidamentoLavoriGg || 0,
          esecuzioneLavori: intervento.esecuzioneLavoriGg || 0,
          collaudo: intervento.collaudoGg || 0,
          appaltoIntegrato: intervento.appaltoIntegrato || false,
          direttoreNome: intervento.intDirettoreGeneraleNome,
          direttoreCognome: intervento.intDirettoreGeneraleCognome,
          direttoreCF: intervento.intDirettoreGeneraleCf,
          commissarioNome: intervento.intCommissarioNome,
          commissarioCognome: intervento.intCommissarioCognome,
          commissarioCF: intervento.intCommissarioCf,
          rupNome: intervento.intRupNome,
          rupCognome: intervento.intRupCognome,
          rupCF: intervento.intRupCf,
          referenteNome: intervento.intReferentePraticaNome,
          referenteCognome: intervento.intReferentePraticaCognome,
          referenteTelefono: intervento.intReferentePraticaTelefono,
          referenteEmail: intervento.intReferentePraticaEmail,
          referenteCF: intervento.intRupCf,
          note: intervento.note,
          delibera: all?.intAllegatoNumero,
          dataDelibera: all?.inteAllegatoDataFormated
        });
        this.calcolaTotaleIntervento();
        let interventoAllegato = intervento.allegatoDeliberaApprovazione;
        let idIntervento = intervento.intId;
        if (all) {
          if (all.idAllegato) {
            this.projectApiService.downloadAllegatoById(all.idAllegato).subscribe(allegato => {
              const all = this.insertionForm.get('allegato');
              const nome = this.insertionForm.get('nomeAllegato')
              const tipo = this.insertionForm.get('tipoAllegato')
              if (all) {
                this.fileName = allegato.fileName
                nome?.patchValue(allegato.fileName);
                tipo?.patchValue(allegato.fileType)
                all.patchValue(allegato.base64);
                let file: File = this.downloadFile(allegato.fileName, allegato.fileType, allegato.base64);
                this.fileUrl = URL.createObjectURL(file);
                this.file = file;
              }
            })
          }
        }
        this.struttureAggiunte.clear();
        this.projectApiService.getStruttureInterventoList(idIntervento, true).subscribe(strutture => {
          for (let struttura of strutture) {
            let strutturaSelezionata = this.strutturaList.find(str => str.strId == struttura.strId);
            let strutturaFormGroup = this.fb.group({
              struttura: [strutturaSelezionata],
              costoStruttura: ['', [Validators.required, Validators.min(0)]],
              progettazione: [0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
              affidamentoLavori: [0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
              esecuzioneLavori: [0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
              collaudo: [0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
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
            });
            strutturaFormGroup.patchValue({
              costoStruttura: (struttura.intstrImporto || 0).toString().replace(',', '.'),
              progettazione: struttura.progettazioneGg || 0,
              affidamentoLavori: struttura.affidamentoLavoriGg || 0,
              esecuzioneLavori: struttura.esecuzioneLavoriGg || 0,
              collaudo: struttura.collaudoGg || 0,
              appaltoIntegrato: struttura.appaltoIntegrato || false,
              intstrRespStrComplesNome: struttura.intstrResponsabileStrutturaComplessaNome || '',
              intstrRespStrComplesCognome: struttura.intstrResponsabileStrutturaComplessaCognome || '',
              intstrRespStrComplesCf: struttura.intstrResponsabileStrutturaComplessaCf || '',
              intstrRespStrSemplNome: struttura.intstrResponsabileStrutturaSempliceNome || '',
              intstrRespStrSemplCognome: struttura.intstrResponsabileStrutturaSempliceCognome || '',
              intstrRespStrSemplCf: struttura.intstrResponsabileStrutturaSempliceCf || ''
            });
            this.calcolaTotale(strutturaFormGroup);
            strutturaFormGroup.valueChanges.subscribe(() => {
              this.calcolaTotale(strutturaFormGroup);
            });
            this.populateInterventiEdilizi(strutturaFormGroup);
            this.populateQuadroEconomico(strutturaFormGroup);
            const listaInterventiEdilizi = strutturaFormGroup.get('listaInterventiEdilizi') as FormArray;
            for (let int of listaInterventiEdilizi.controls) {
              const id = int.value.id;
              if (struttura.interventoEdilizio[id]) {
                const selezionatoControl = int.get('selezionato');
                if (selezionatoControl) {
                  selezionatoControl.patchValue(struttura.interventoEdilizio[id]);
                }
              }
            }
            const listaQuadroEconomico = strutturaFormGroup.get('listaQuadroEconomico') as FormArray;
            for (let qua of listaQuadroEconomico.controls) {
              // Controlla che l'id sia valido
              const id = qua.value.id;
              if (struttura.quadroEconomico[id]) {
                const selezionatoControl = qua.get('valoreNumerico');
                if (selezionatoControl) {
                  selezionatoControl.patchValue(struttura.quadroEconomico[id].toString().replace('.', ','));
                }
              }
            }
            this.struttureAggiunte.push(strutturaFormGroup);
            this.loading = false;
          }
        });
      } else {
        this.loading = false;
      }
    });
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

  getListaInterventiEdilizi(i: number): FormArray {
    let interventi = this.struttureAggiunte.at(i).get('listaInterventiEdilizi') as FormArray;
    return interventi;
  }

  getListaQuadroEconomico(i: number): FormArray {
    let quardro = this.struttureAggiunte.at(i).get('listaQuadroEconomico') as FormArray;
    return quardro;
  }

  downloadFile(fileName: string, fileType: string, base64: string): File {
    // Decodifica la stringa base64
    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);

    // Crea un Blob
    const blob = new Blob([byteArray], { type: fileType });

    // Crea e restituisci un oggetto File
    return new File([blob], fileName, { type: fileType });
  }

  fileToBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        // Ritorna la stringa Base64
        resolve(reader.result as string);
      };
      reader.onerror = reject; // Gestisci eventuali errori
      reader.readAsDataURL(file); // Leggi il file come URL data
    });
  }

  reset() {
    this.loading = true;
    setInterval(() => {
      for (let i = 0; i < this.struttureAggiunte.controls.length; i++) {
        this.struttureAggiunte.removeAt(i);
      }
      this.removeFile();
      this.insertionForm.reset();
      this.loading = false;
    }, 1000);
  }

  allowOnlyChar(event: KeyboardEvent): boolean {
    const key = event.key

    // Ottieni il carattere digitato

    // Consenti solo lettere (A-Z, a-z)
    if (/^[A-Za-z]$/.test(key)) {
      return true;
    } else {
      event.preventDefault();
      return false;
    }
  }


  // CHECK VALIDITA MESSAGGIO
  checkAnnoPriorita(event: Event) {
    const annoFormControl = this.insertionForm.get('annoPriorita');
    if (annoFormControl) {
      if (annoFormControl?.getError('yearOutOfRange')) {
        this.messageService.error("MSG-057  Attenzione! L'anno di priorità inserito non è all'interno del triennio corrente");
      }
      annoFormControl.updateValueAndValidity();
    }
  }


  // CHECK 218
  public checkFormValidity() {
    const invalidControls = this.errorService.findInvalidControls(this.insertionForm);

    if (invalidControls.length > 0) {
      // Creare un messaggio dettagliato per ogni controllo invalido
      const errorMessage = invalidControls
        .map(controlPath => this.errorService.getFriendlyName(controlPath))
        .join(',');

      this.messageService.error('MSG-005 Inserire campo obbligatorio: ' + errorMessage)
    }
  }

  getStrutturaForm(index: number) {
    return this.struttureAggiunte.at(index) as FormGroup;
  }


  getTotaleStrutture() {
    const totaleCosto = this.struttureAggiunte.controls.reduce((totale, gruppo) => {
      const costo = gruppo.get('costoStruttura')?.value || 0; // Ottieni il valore di costoStruttura o usa 0 se nullo
      return totale + parseFloat(costo); // Assicuriamoci che sia un numero
    }, 0);
    return totaleCosto.toFixed(2);
  }

  isResponsabiliStrutturaConformi(): boolean {
    for (const gruppo of this.struttureAggiunte.controls) {
      const respComp =
        gruppo.get("intstrRespStrComplesNome")?.value &&
        gruppo.get("intstrRespStrComplesCognome")?.value &&
        gruppo.get("intstrRespStrComplesCf")?.value;

      const respSemp =
        gruppo.get("intstrRespStrSemplNome")?.value &&
        gruppo.get("intstrRespStrSemplCognome")?.value &&
        gruppo.get("intstrRespStrSemplCf")?.value;

      if (!respComp && !respSemp) {
        // Se nessun responsabile è inserito
        return false;
      }
    }

    return true;
  }

  blockSubmitting() {
    if (this.isSubmitting) return;

    this.isSubmitting = true;
    console.log("Elaborazione del form iniziata...");

    setTimeout(() => {
      console.log("Elaborazione completata!");
      this.isSubmitting = false; // Riabilita il pulsante
    }, 1000);
  }

  // const direttore = (this.insertionForm.get('direttoreNome')?.value && this.insertionForm.get('direttoreCognome')?.value && this.insertionForm.get('direttoreCF')?.value) ;
  // const commissario=(this.insertionForm.get('commissarioNome')?.value && this.insertionForm.get('commissarioCognome')?.value && this.insertionForm.get('commissarioCF')?.value)

  disabilitaCommissario() {
    return (this.insertionForm.get('direttoreNome')?.value ||
      this.insertionForm.get('direttoreCognome')?.value ||
      this.insertionForm.get('direttoreCF')?.value);
  }
  disabilitaDirettore() {
    return (this.insertionForm.get('commissarioNome')?.value ||
      this.insertionForm.get('commissarioCognome')?.value
      || this.insertionForm.get('commissarioCF')?.value);

  }

  isNotDirettoreOrCommissarioPopulatedValidator(): boolean {
    // Controlla se almeno un campo è popolato per il Direttore
    const direttorePopolato = (this.insertionForm.get('direttoreNome')?.value ||
      this.insertionForm.get('direttoreCognome')?.value ||
      this.insertionForm.get('direttoreCF')?.value) ? true : false;
    // console.log(direttorePopolato)
    // Controlla se almeno un campo è popolato per il Commissario
    const commissarioPopolato = (this.insertionForm.get('commissarioNome')?.value ||
      this.insertionForm.get('commissarioCognome')?.value
      || this.insertionForm.get('commissarioCF')?.value) ? true : false;
    // console.log(commissarioPopolato)
    // Valida che SOLO uno tra Direttore e Commissario sia popolato
    if ((direttorePopolato && commissarioPopolato) || (!direttorePopolato && !commissarioPopolato)) {
      return true;
    }
    return false; // Valido
  }

  isDeliberaCompliant(): boolean {
    let count = 0;
    let allegatoNumero = this.insertionForm.get("delibera")?.value;
    let allegatoData = this.insertionForm.get('dataDelibera')?.value;
    if (allegatoNumero != null && allegatoNumero !== '') count++;
    if (allegatoData != null && allegatoData !== '') count++; // Verifica se la data è valorizzata
    if (this.file != null) count++;

    if (count === 1 || count === 2) {
      return false;
    }

    return true;
  }

  checkStruttura(idStr: number): boolean {
    const struttura = this.strutturaList.find(x => x.strId === idStr);
    return struttura ? struttura.strPgmeas : false;
  }

  gestisciStrutturaAggiunta(struttura: Struttura): void {
    if (struttura) {
      // Aggiungi la nuova struttura alla lista principale
      this.strutturaList.push(struttura);

      // Ripopola la lista filtrata
      this.strutturaFilteredList = this.strutturaControl.valueChanges.pipe(
        debounceTime(300),  // Ritarda l'esecuzione per evitare input lag
        distinctUntilChanged(), // Evita chiamate ripetute con lo stesso valore
        map(value => typeof value === 'string' ? value.toLowerCase() : value?.strDenominazione.toLowerCase()),
        map(name => name ? this._filter(name) : [...this.strutturaList]) // Clona invece di ordinare ogni volta
      );
    }
  }

  resetStrutturaFilteredList() {
    if (!this.strutturaControl.value || this.strutturaControl.value === '') {
      this.strutturaFilteredList = this.strutturaControl.valueChanges.pipe(
        startWith(''),
        debounceTime(300),
        distinctUntilChanged(),
        map(value => this._filter(value))
      );
    }
  }

}
