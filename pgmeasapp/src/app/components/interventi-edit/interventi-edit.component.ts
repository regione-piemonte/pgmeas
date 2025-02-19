/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClassificazioneTree, Ente, FileBase64, Finanziamento, FinanziamentoTipoDet, InterventoAppaltoTipo, InterventoCategoria, InterventoContrattoTipo, InterventoFinalita, InterventoFormaRealizzativa, InterventoObiettivo, InterventoStato, InterventoStatoProgettuale, InterventoStruttura, InterventoTipo, InterventoTipoDet, Quadrante, Struttura, UserInfo } from '@pgmeas-library/model';
import { IntAllegatoDelibera, InterventoDTO, InterventoStrutturaDTO } from '@pgmeas-library/model/src/intervento-salvataggio';
import { InterventoVisualizza } from '@pgmeas-library/model/src/intervento-visualizza';
import { StrutturaInserimentoIntervento } from '@pgmeas-library/model/src/struttura-inserimento-intervento';
import { StrutturaInserimentoInterventoCopy } from '@pgmeas-library/model/src/struttura-inserimento-intervento copy';
import { combineLatest, debounceTime, distinctUntilChanged, map, Observable, startWith, switchMap } from 'rxjs';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { RegistryApiService } from 'src/app/services/registry-api.service';
import { UserService } from 'src/app/services/user.service';
import { ModaleConfermaComponent } from '../programmazione/modale-conferma/modale-conferma.component';
import { MatDialog } from '@angular/material/dialog';
import { codiceFiscaleValidator } from 'src/app/utils/codice-fiscale-validator';
import { emailValidator } from 'src/app/utils/email-validator';
import { MessageService } from 'src/app/services/message.service';
import { ErrorService } from 'src/app/services/error.service';
import { yearRangeValidator } from 'src/app/utils/yearRangeValidator';
const ACQUISTO_ATTREZZATURE='ACQ_ATTR';
import { quadroEconomicoService } from 'src/app/services/quadroEconomico.service';
import { NgxCurrencyInputMode } from 'ngx-currency';


@Component({
  selector: 'app-interventi-edit',
  templateUrl: './interventi-edit.component.html',
  styleUrls: ['./interventi-edit.component.scss']
})
export class InterventiEditComponent {
  maxDate = new Date();
  title = 'Modifica intervento';
  loading = true;

  user: UserInfo;
  intervento: InterventoVisualizza;
  enteList: Ente[];
  strutturaList: Struttura[];
  finanziamentoTipoDetList: FinanziamentoTipoDet[];
  quadranteList: Quadrante[];
  finanziamentoList: Finanziamento[];
  interventoStrutturaList: InterventoStruttura[];
  interventoObiettivoList: InterventoObiettivo[];
  interventoFinalitaList: InterventoFinalita[];
  interventoTipoList: InterventoTipo[];
  interventoCategoriaList: InterventoCategoria[];
  interventoFormaRealizzativaList: InterventoFormaRealizzativa[];
  struttureIntervento: StrutturaInserimentoInterventoCopy[] = [];
  interventoEdilizioList: ClassificazioneTree[];
  quadroEconomicoList: ClassificazioneTree[];
  obiettivoList: InterventoObiettivo[];
  finalitaList: InterventoFinalita[];
  attrezzaturaList: InterventoTipoDet[];
  categoriaList: InterventoCategoria[];
  contrattoTipoList: InterventoContrattoTipo[];
  appaltoTipoList: InterventoAppaltoTipo[];
  statoProgList: InterventoStatoProgettuale[];
  fileName: string | null = null;
  fileUrl: string | null = null;
  file: File | null = null;
  salvataggioEffettuato: boolean = false;
  insertionForm: FormGroup;
  intCopiatoId: number | null = null;
  intCopiatoEnteId: number | null = null;
  intId: number | null = null;
  strutturaControl = new FormControl(); // FormControl per il campo
  strutturaFilteredList!: Observable<Struttura[]>; // Lista filtrata
  erroreDuplicato: boolean = false;
  allegato: FileBase64;
  deliberaVecchia: IntAllegatoDelibera;
  dataCreazione: string;
  anno: number;
  interventoStatoList: InterventoStato[];
   natural=NgxCurrencyInputMode.Natural;

  get struttureAggiunte(): FormArray {
    return this.insertionForm.get('struttureAggiunte') as FormArray;
  }


  constructor(
    private userService: UserService,
    private projectApiService: ProjectApiService,
    private registryApiService: RegistryApiService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    public dialog: MatDialog,
    public messageService: MessageService,
    public errorService: ErrorService,
    public quadroEconomicoService: quadroEconomicoService
  ) { }

  ngOnInit() {
    this.user = this.userService.getUser();
    this.strutturaFilteredList = this.strutturaControl.valueChanges.pipe(
      debounceTime(300),  // Ritarda l'esecuzione per evitare input lag
      distinctUntilChanged(), // Evita chiamate ripetute con lo stesso valore
      map(value => typeof value === 'string' ? value.toLowerCase() : value?.strDenominazione.toLowerCase()),
      map(name => name ? this._filter(name) : [...this.strutturaList]) // Clona invece di ordinare ogni volta
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
      attrezzatura: [''],
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
      direttoreCF: ['',codiceFiscaleValidator()],
      commissarioNome: [''],
      commissarioCognome: [''],
      commissarioCF: ['',codiceFiscaleValidator()],
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
      this.registryApiService.getEnteList(),
      this.registryApiService.getStrutturaListByCodiceAzienda(this.user.codiceAzienda),
      this.registryApiService.getFinanziamentoTipoDetList(),
      this.registryApiService.getQuadranteList(),
      this.registryApiService.getInterventoObiettivoList(),
      this.registryApiService.getInterventoFinalitaList(),
      this.registryApiService.getInterventoTipoList(),
      this.registryApiService.getInterventoCategoriaList(),
      this.registryApiService.getClassificazioneTreeList('IE'),
      this.registryApiService.getClassificazioneTreeList('QE'),
      this.registryApiService.getInterventoObiettivoList(),
      this.registryApiService.getInterventoFinalitaList(),
      this.registryApiService.getInterventoTipoDetList(ACQUISTO_ATTREZZATURE),
      this.registryApiService.getInterventoCategoriaList(),
      this.registryApiService.getInterventoContrattoTipoList(),
      this.registryApiService.getInterventoAppaltoTipoList(),
      this.registryApiService.getInterventoStatoProgettualeList(),
      // this.registryApiService.getInterventoFormaRealizzativaList(),
      this.registryApiService.getInterventoStatoList(),
      this.route.paramMap.pipe(
        map((params) => Number(params.get('id'))),
        switchMap((id) =>
          combineLatest([
            this.projectApiService.getInterventoDetailV2(id),
            this.projectApiService.getFinanziamentoListByIntervento(id),
            this.projectApiService.getInterventoStrutturaListByIntervento(id),
            this.projectApiService.getStruttureInterventoList(id, false)
          ])
        )
      ),
    ]).subscribe(
      ([
        enteList,
        strutturaList,
        finanziamentoTipoDetList,
        quadranteList,
        interventoObiettivoList,
        interventoFinalitaList,
        interventoTipoList,
        interventoCategoriaList,
        interventoEdilizioTree,
        quadroEconomicoTree,
        obiettivoList,
        finalitaList,
        attrezzaturaList,
        categoriaList,
        contrattoList,
        appaltoTipoList,
        statoProgList,
        interventoStatoList,
        // interventoFormaRealizzativaList,
        [intervento, finanziamentoList, interventoStrutturaList, struttureList],
      ]) => {
        this.enteList = enteList;
        this.strutturaList = strutturaList;
        this.finanziamentoTipoDetList = finanziamentoTipoDetList;
        this.quadranteList = quadranteList;
        this.interventoObiettivoList = interventoObiettivoList;
        this.interventoFinalitaList = interventoFinalitaList;
        this.interventoTipoList = interventoTipoList;
        this.interventoCategoriaList = interventoCategoriaList;
        this.interventoEdilizioList = interventoEdilizioTree;
        this.quadroEconomicoList = quadroEconomicoTree;
        this.obiettivoList = obiettivoList;
        this.finalitaList = finalitaList;
        this.attrezzaturaList = attrezzaturaList;
        this.categoriaList = categoriaList;
        this.contrattoTipoList = contrattoList;
        this.appaltoTipoList = appaltoTipoList;
        this.statoProgList = statoProgList;
        this.interventoStatoList=interventoStatoList;
        // this.interventoFormaRealizzativaList = interventoFormaRealizzativaList;
        this.intervento = intervento;
        this.finanziamentoList = finanziamentoList;
        this.interventoStrutturaList = interventoStrutturaList;
        this.struttureIntervento = struttureList;
        this.anno = this.intervento.intAnno;
        // console.log(intervento)
        // console.log(this.intervento)

        if (this.intervento && this.intervento.dataCreazione) {
          const data = new Date(this.intervento.dataCreazione); // Crea un oggetto Date
          // Formatta la data in "yyyy-mm-dd"
          const year = data.getFullYear();
          const month = String(data.getMonth() + 1).padStart(2, '0'); // i mesi vanno da 0 a 11
          const day = String(data.getDate()).padStart(2, '0');
          this.dataCreazione = `${day}/${month}/${year}`;

        }
        if (this.intervento.allegatoDeliberaApprovazione) {
          let all = this.intervento.allegatoDeliberaApprovazione[0];
          if (all.idAllegato) {
            this.projectApiService.downloadAllegatoById(all.idAllegato).subscribe(allegato => {
              this.allegato = allegato;
              this.fileName = allegato.fileName
              let file: File = this.downloadFile(allegato.fileName, allegato.fileType, allegato.base64);
              this.fileUrl = URL.createObjectURL(file);
              this.inizializzazioneModifica();
            }, err => {
              this.inizializzazioneModifica();
            })
          }
        } else {
          this.inizializzazioneModifica();
        }

      }
    );

    this.insertionForm.get('struttura')?.valueChanges.subscribe(() => {
      this.erroreDuplicato = false;
    });
  }

  downloadFile(fileName: string, fileType: string, base64: string): File {
    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);

    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: fileType });

    return new File([blob], fileName, { type: fileType });
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
    console.log(this.insertionForm.get('costoIntervento')?.value)
    if(this.insertionForm.get('cup')!.invalid) {
      this.messageService.error("Se valorizzato, il codice CUP deve essere di 15 caratteri");
      return;
    }

    if (this.insertionForm.valid) {
      const direttore = (this.insertionForm.get('direttoreNome')?.value && this.insertionForm.get('direttoreCognome')?.value && this.insertionForm.get('direttoreCF')?.value);
      const commissario = (this.insertionForm.get('commissarioNome')?.value && this.insertionForm.get('commissarioCognome')?.value && this.insertionForm.get('commissarioCF')?.value)
      const costoIntervento = this.insertionForm.get('costoIntervento')?.value;
      const totaleStrutture = this.getTotaleStrutture();
      const isResponsabiliConformi = this.isResponsabiliStrutturaConformi();
      const isAllegatoDeliberaCompliant=this.isDeliberaCompliant();
      if (parseFloat(costoIntervento) !== parseFloat(totaleStrutture)) {
          console.log(parseFloat(costoIntervento))
          console.log(parseFloat(totaleStrutture))
        this.messageService.error("Il Costo complessivo dell'intervento deve corrispondere esattamente al costo della struttura, o, in caso di più strutture, alla somma dei costi delle strutture aggiunte. Si prega di verificare e correggere i valori inseriti.");
        return;
      }
      if(!direttore && !commissario){
        this.messageService.error("L'inserimento dei dati relativi del Direttore Generale o del Commissario è obbligatorio.")
        return;
      }
      if(this.isNotDirettoreOrCommissarioPopulatedValidator()){
        this.messageService.error("MSG-082: Valorizzare i dati del direttore generale o del commissario, non entrambi.")
        return;
      }

      if (!isResponsabiliConformi) {
        this.messageService.error("L'inserimento dei nominativi del Responsabile della Struttura Complessa e/o del Responsabile della Struttura Semplice è obbligatorio")
        return;
      }
      if(!isAllegatoDeliberaCompliant){
        this.messageService.error("Delibere aziendali dell'intervento non valorizzata correttamente. Inserire tutti i valori")
        return;
      }

      // console.log(this.insertionForm);
      this.salvataggioIntervento();
    } else {
      console.log('Invalido');
      // console.log(this.insertionForm);
      this.markInvalidControlsAsTouched(this.insertionForm);
      this.checkFormValidity()
      return;
    }
  }

  salvataggioIntervento() {
    this.loading = true;
    let formattedDate = null;
    const date = this.insertionForm.get('dataDelibera')?.value;
    if(date) {
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
      intAnno: this.anno,
      intQuadranteId: parseInt(this.user.idQuadrante),
      intCopiatoEnteId: this.intCopiatoEnteId,
      intCopiatoId: this.intCopiatoId,
      intId: this.intervento.intId,
      dataCreazione: this.intervento.dataCreazione,
      dataCancellazione: null,
      dataModifica: null
    };
    if (this.controlloDelibera(formattedDate)) {
      salvataggio.intAllegatoDeliberaNew = {
        intAllegatoNumero: this.insertionForm.get('delibera')?.value,
        intAllegatoData: formattedDate,
        idAllegato: null,
        fileNameUser: this.insertionForm.get('nomeAllegato')?.value,
        fileType: this.insertionForm.get('tipoAllegato')?.value,
        base64: this.insertionForm.get('allegato')?.value,
      }
      if(this.deliberaVecchia) {
        salvataggio.intAllegatoDeliberaToDelete = {
          intAllegatoNumero: this.deliberaVecchia.intAllegatoNumero,
          intAllegatoData: this.deliberaVecchia.intAllegatoData,
          idAllegato: this.deliberaVecchia.idAllegato,
          fileNameUser: this.deliberaVecchia.fileNameUser,
          fileType: this.deliberaVecchia.fileType,
          base64: this.deliberaVecchia.base64,
        }
      } else {
        salvataggio.intAllegatoDeliberaToDelete = null;
      }
      // salvataggio.intAllegatoDeliberaToDelete = this.deliberaVecchia;
    } else {
      salvataggio.intAllegatoDeliberaNew = null;
      salvataggio.intAllegatoDeliberaToDelete = null;
    }

      this.projectApiService.editIntervento(salvataggio, this.intervento.intId).subscribe(save =>{
        this.intId = save.intId;
        // for(let i=0; i<this.struttureAggiunte.controls.length; i++){
        //   console.log(this.struttureAggiunte)
        //   this.struttureAggiunte.removeAt(i);
        // }
        // this.removeFile();
        // this.insertionForm.reset();
        this.salvataggioEffettuato=true;
        this.loading = false;
    }, err=>{
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
        intStrId: str.intStrId,
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

  removeFile(): void {
    this.fileName = null;
    this.fileUrl = null;
    this.file = null;
    this.insertionForm.patchValue({
      allegato: null, // Imposta il file nel form,
    });
    this.insertionForm.get('delibera')?.reset();
    this.insertionForm.get('dataDelibera')?.reset();
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

  buildQuadroEconomicoMap(quadroEconList: any[]): { [key: string]: number } {
    let quadroEconMap: { [key: string]: number } = {};

    quadroEconList.forEach(quadro => {
      let id = quadro.id;
      let valore = parseFloat((quadro.valoreNumerico || '0').replace(',', '.'));

      quadroEconMap[id] = valore;
    });

    return quadroEconMap;
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
        intstrRespStrComplesNome: ['', Validators.required],
        intstrRespStrComplesCognome: ['', Validators.required],
        intstrRespStrComplesCf: ['',  codiceFiscaleValidator()],
        intstrRespStrSemplNome: [''],
        intstrRespStrSemplCognome: [''],
        intstrRespStrSemplCf: ['',  codiceFiscaleValidator()],
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
    this.loading = false;
    this.strutturaControl.reset();
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

  getListaInterventiEdilizi(i: number): FormArray {
    let interventi = this.struttureAggiunte.at(i).get('listaInterventiEdilizi') as FormArray;
    return interventi;
  }

  getListaQuadroEconomico(i: number): FormArray {
    let quardro = this.struttureAggiunte.at(i).get('listaQuadroEconomico') as FormArray;
    return quardro;
  }

  getMarginFromSpazi(livello: number): number {
    if (livello > 1)
      return livello * 20;  // 10px per ogni spazio, ad esempio
    return 0;
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

  inizializzazioneModifica() {
    this.loading = true;
    if (this.intervento) {
      this.intCopiatoEnteId = this.intervento.copiatoDaEnteId;
      this.intCopiatoId = this.intervento.copiatoDaIntId;
      let all = null;
      let formattedDate = '';

      if (this.intervento.allegatoDeliberaApprovazione && this.intervento.allegatoDeliberaApprovazione.length > 0) {
        all = this.intervento.allegatoDeliberaApprovazione[0];
        const data = new Date(all.intAllegatoData); // Crea un oggetto Date
        // Formatta la data in "yyyy-mm-dd"
        const year = data.getFullYear();
        const month = String(data.getMonth() + 1).padStart(2, '0'); // i mesi vanno da 0 a 11
        const day = String(data.getDate()).padStart(2, '0');
        formattedDate = `${year}-${month}-${day}`;
        all.inteAllegatoDataFormated = data;
      }

      this.insertionForm.patchValue({
        cup: this.intervento.intCup,
        codInterventoPGMEAS: this.intervento.intCod,
        codInterventoOriginePGMEAS: this.intervento.copiatoDaCodicePgmeas,
        annoInserimentoInterventoOrigine: this.intervento.copiatoDaAnno,
        codNSISIntervento: this.intervento.intCodicNsis,
        titoloIntervento: this.intervento.intTitolo,
        obiettivo: this.intervento.obiettivi,
        finalita: this.intervento.finalita,
        tipologia: this.intervento.tipi,
        categoria: this.intervento.categorie,
        annoPriorita: this.intervento.intPrioritaAnno,
        priorita: this.intervento.intPriorita,
        sottoPriorita: this.intervento.intSottopriorita,
        attrezzatura: this.intervento.descrizioniAttrezzature,
        contrattoTipo: this.intervento.contrattiTipo,
        appaltoTipo: this.intervento.appaltiTipo,
        statoProg: this.intervento.statiProgettuali,
        costoIntervento: this.intervento.intImporto.toString().replace(',', '.'),
        progettazione: this.intervento.progettazioneGg || 0,
        affidamentoLavori: this.intervento.affidamentoLavoriGg || 0,
        esecuzioneLavori: this.intervento.esecuzioneLavoriGg || 0,
        collaudo: this.intervento.collaudoGg || 0,
        appaltoIntegrato: this.intervento.appaltoIntegrato || false,
        direttoreNome: this.intervento.intDirettoreGeneraleNome,
        direttoreCognome: this.intervento.intDirettoreGeneraleCognome,
        direttoreCF: this.intervento.intDirettoreGeneraleCf,
        commissarioNome: this.intervento.intCommissarioNome,
        commissarioCognome: this.intervento.intCommissarioCognome,
        commissarioCF: this.intervento.intCommissarioCf,
        rupNome: this.intervento.intRupNome,
        rupCognome: this.intervento.intRupCognome,
        rupCF: this.intervento.intRupCf,
        referenteNome: this.intervento.intReferentePraticaNome,
        referenteCognome: this.intervento.intReferentePraticaCognome,
        referenteTelefono: this.intervento.intReferentePraticaTelefono,
        referenteEmail: this.intervento.intReferentePraticaEmail,
        referenteCF: this.intervento.intReferentePraticaCf,
        note: this.intervento.note,
        delibera: all?.intAllegatoNumero,
        dataDelibera: all?.inteAllegatoDataFormated
      });


      this.calcolaTotaleIntervento();
      let interventoAllegato = this.intervento.allegatoDeliberaApprovazione;
      let idIntervento = this.intervento.intId;
      if (all) {
        if (all.idAllegato) {
          const alleg = this.insertionForm.get('allegato');
          const nome = this.insertionForm.get('nomeAllegato')
          const tipo = this.insertionForm.get('tipoAllegato')
          if (alleg) {
            if (this.allegato) {
              this.fileName = this.allegato.fileName
              nome?.patchValue(this.allegato.fileName);
              tipo?.patchValue(this.allegato.fileType)
              alleg.patchValue(this.allegato.base64);
              let file: File = this.downloadFile(this.allegato.fileName, this.allegato.fileType, this.allegato.base64);
              this.fileUrl = URL.createObjectURL(file);
              this.file = file;
            }
          }

        }
      }

      if (this.allegato) {
        this.deliberaVecchia = {
          intAllegatoNumero: all?.intAllegatoNumero || '',
          intAllegatoDataFormatted: formattedDate || '',
          base64: this.allegato.base64,
          idAllegato: all?.idAllegato,
          fileNameUser: this.allegato.fileName,
          fileType: this.allegato.fileType,
          intAllegatoData: formattedDate || ''
        }
      }

        for(let struttura of this.struttureIntervento){
          let strutturaSelezionata = this.strutturaList.find(str => str.strId==struttura.strId);
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
            intstrRespStrComplesCf: ['',codiceFiscaleValidator()],
            intstrRespStrSemplNome: [''],
            intstrRespStrSemplCognome: [''],
            intstrRespStrSemplCf: ['', codiceFiscaleValidator()],
            intStrId: [0, Validators.required]
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
            intstrRespStrSemplCf: struttura.intstrResponsabileStrutturaSempliceCf || '',
            intStrId: struttura.intStrId
          });
        this.calcolaTotale(strutturaFormGroup);
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

    } else {
      this.loading = false;
    }
  };

  controlloDelibera(formattedDate: string | null) {

    // if (!this.deliberaVecchia) {
    //   return false;
    // }

    if(this.deliberaVecchia) {
      if (this.deliberaVecchia.base64 !== this.insertionForm.get('allegato')?.value) {
        return true;
      }
      if (this.deliberaVecchia.intAllegatoNumero !== this.insertionForm.get('delibera')?.value) {
        return true;
      }
      if (formattedDate != null && this.deliberaVecchia.intAllegatoDataFormatted !== formattedDate) {
        return true;
      }
      return false;
    }

    return true;
  }

  back() {
    const back = history.state.back ?? '/consultazione-interventi';
    this.router.navigateByUrl(back);
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

  getStrutturaForm(index: number) {
    return this.struttureAggiunte.at(index) as FormGroup;
  }

  isNotDirettoreOrCommissarioPopulatedValidator():boolean {
    // Controlla se almeno un campo è popolato per il Direttore
    const direttorePopolato = (this.insertionForm.get('direttoreNome')?.value ||
    this.insertionForm.get('direttoreCognome')?.value ||
    this.insertionForm.get('direttoreCF')?.value)?true:false;
    // console.log(direttorePopolato)
    // Controlla se almeno un campo è popolato per il Commissario
    const commissarioPopolato = ( this.insertionForm.get('commissarioNome')?.value ||
    this.insertionForm.get('commissarioCognome')?.value
    || this.insertionForm.get('commissarioCF')?.value)?true:false;
    // console.log(commissarioPopolato)
    // Valida che SOLO uno tra Direttore e Commissario sia popolato
    if ((direttorePopolato && commissarioPopolato) || (!direttorePopolato && !commissarioPopolato)) {
      return true;
    }
    return false; // Valido
  }

  isStatoInserito(statoIntervento:number){
    const stato = this.interventoStatoList.find((stato)=> stato.intStatoId === statoIntervento );
    return stato?.intStatoDesc ==='INSERITO';
  }

  isDeliberaCompliant():boolean {
    let count = 0;
    let allegatoNumero = this.insertionForm.get("delibera")?.value;
    let allegatoData = this.insertionForm.get('dataDelibera')?.value;
    if (allegatoNumero != null && allegatoNumero !== '') count++;
    if (allegatoData != null) count++; // Verifica se la data è valorizzata
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


