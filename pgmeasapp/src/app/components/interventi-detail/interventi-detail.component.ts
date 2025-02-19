/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Allegato, ClassificazioneTree, Ente, FileBase64, Finanziamento, FinanziamentoTipo, FinanziamentoTipoDet, InterventoAppaltoTipo, InterventoCategoria, InterventoContrattoTipo, InterventoFinalita, InterventoFormaRealizzativa, InterventoObiettivo, InterventoStato, InterventoStatoProgettuale, InterventoStruttura, InterventoTipo, InterventoTipoDet, Quadrante, Struttura, UserInfo } from '@pgmeas-library/model';
import { IntAllegatoDelibera, InterventoDTO, InterventoStrutturaDTO } from '@pgmeas-library/model/src/intervento-salvataggio';
import { InterventoVisualizza, PianoFinanziario } from '@pgmeas-library/model/src/intervento-visualizza';
import { StrutturaInserimentoIntervento } from '@pgmeas-library/model/src/struttura-inserimento-intervento';
import { Parere, StrutturaInserimentoInterventoCopy } from '@pgmeas-library/model/src/struttura-inserimento-intervento copy';
import { combineLatest, map, Observable, startWith, switchMap } from 'rxjs';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { RegistryApiService } from 'src/app/services/registry-api.service';
import { UserService } from 'src/app/services/user.service';
import { ModaleConfermaComponent } from '../programmazione/modale-conferma/modale-conferma.component';
import { MatDialog } from '@angular/material/dialog';
import { AllegatoDelibera } from '@pgmeas-library/model/src/allegato-delibera';
import { PrevisioneDiSpesa } from '@pgmeas-library/model/src/intervento-copy';
import { AllegatoRegione } from '@pgmeas-library/model/src/allegato-regione';
import { FinanziamentoImporto, TipologicaFinanziamento } from '@pgmeas-library/model/src/tipologicheFinanziamento';
import { AllegatoRegioneModifica, FinanziamentoImportoTipo, InterventoModificaRegioneDTO, ParereRegione, PianoFinanziarioRegione } from '@pgmeas-library/model/src/intervento-modifica-regione';
import { ModaleAzioneComponent } from '../modale-azione/modale-azione.component';
import { MessageService } from 'src/app/services/message.service';
import { ErrorService } from 'src/app/services/error.service';
import { AriannaService } from 'src/app/services/arianna.service';


export interface FileAllegato {
  index: number;
  fileName: string;
  file: File;
  fileUrl: string;
}

export interface PianoFinanziarioTabellaRow {
  finTipoId: number | null;
  finTipoDesc: string | undefined;
  finTipoDetId: number | undefined;
  finTipoDetDesc: string | undefined;
  finImporto: number | undefined;
  finPercRegione: number | undefined;
  finPercStato: number | undefined;
  isPrincipale: boolean | undefined;
  finanziamentoImportoTipo: FinanziamentoImporto[];
}

const IMP_STATO = 'IMP_STATO';
const IMP_REGIONE = 'IMP_REGIONE';
const ACQUISTO_ATTREZZATURE='ACQ_ATTR';

@Component({
  selector: 'app-interventi-detail',
  templateUrl: './interventi-detail.component.html',
  styleUrls: ['./interventi-detail.component.scss']
})
export class InterventiDetailComponent {
  loading = true;

  user: UserInfo;
  intervento: InterventoVisualizza;
  enteList: Ente[];
  strutturaList: Struttura[];
  finanziamentoTipoDetList: FinanziamentoTipoDet[];
  tipologicheFinanziamento: TipologicaFinanziamento[];
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
  interventoStatoList: InterventoStato[];
  fileName: string | null = null;
  fileUrl: string | null = null;
  filesPPP: FileAllegato[] = [];
  filesPPPChanged: number[] = [];
  filesHTA: FileAllegato[] = [];
  filesHTAChanged: number[] = [];
  dgrApprovazioneChanged: boolean = false;
  dcrApprovazioneChanged: boolean = false;
  dgrPropConsRegionaleChanged: boolean = false;
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
  formPrevisione: FormGroup;
  displayedColumns: string[] = ['someValue', 'someOtherValue'];
  finanziamentoTipoList: FinanziamentoTipo[];
  selectedFinanziamentoId: number;
  selectedDettaglioId: number;
  isPrincipale: boolean;
  pianiFinanziariPrincipaliTabellaRows: PianoFinanziarioTabellaRow[] = [];
  importoTotale: number;
  importoRegione: number;
  importoStato: number;
  pianiFinanziariNonPrincipaliTabellaRows: PianoFinanziarioTabellaRow[] = [];
  isPercentualiRegioneEStato: boolean = false;
  isDettaglioSelezionato: boolean = false;
  errorFinanziamento: boolean = false;
  errorDettaglio: boolean = false;
  errorImportoRegione: boolean = false;
  errorImportoStato: boolean = false;
  errorImportoTotale: boolean = false;
  DGRApprovazione: AllegatoRegione = {};
  DGRPropostaConsiglioRegionale: AllegatoRegione = {};
  DCRApprovazione: AllegatoRegione = {};
  DeterminazioniDirigenziali: AllegatoRegione[] = [];
  DeterminazioniDirigenzialiDeleted: AllegatoRegione[] = [];
  DeterminazioniDirigenzialiAdded: AllegatoRegione[] = [];
  fileDGRApprovazione: File | null;
  fileDGRPropostaConsiglioRegionale: File | null;
  fileDCRApprovazione: File | null;
  fileDeterminazioniDirigenziali: File[] = [];

  fileUrlDGRApprovazione: string | null = null;
  fileUrlDGRPropostaConsRegionale: string | null = null;
  fileUrlDCRApprovazione: string | null = null;
  fileUrlDeterminazioniDirigenziali: string[] | null = [];
  finanziamentoTipologiche: FinanziamentoTipo[] = [];
  finanziamentoTipologicheDettagli: FinanziamentoTipoDet[] = [];
  isSaved: boolean = false;
  isAllegatiInvalid: boolean = false;
  isPrevSpesaTotInvalid: boolean = false;
  isPianiFinanziariInvalid: boolean = false;

  totImportoPrincipali: number = 0;
  totImportoRegionePrincipali: number = 0;
  totImportoStatoPrincipali: number = 0;
  totImportoAltri: number = 0;
  totImportoRegioneAltri: number = 0;
  totImportoStatoAltri: number = 0;
  totImporto: number = 0;
  totImportoRegione: number = 0;
  totImportoStato: number = 0;

  get struttureAggiunte(): FormArray {
    return this.insertionForm.get('struttureAggiunte') as FormArray;
  }

  get previsioniDiSpesa(): FormArray {
    return this.insertionForm.get('previsioniDiSpesa') as FormArray;
  }

  constructor(
    private userService: UserService,
    private projectApiService: ProjectApiService,
    private registryApiService: RegistryApiService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    public dialog: MatDialog,
    public messageService:MessageService,
    public errorService:ErrorService,
    private ariannaService: AriannaService
  ) {
    this.formPrevisione = new FormGroup({
      previsioneAnnoControl: new FormControl(null, {
        validators: [this.yearValidator],
        nonNullable: true
      })
    });
  }

  ngOnInit() {
    this.user = this.userService.getUser();

    this.strutturaFilteredList = this.strutturaControl.valueChanges.pipe(
      startWith(''),
      map(value => typeof value === 'string' ? value : value?.strDenominazione),
      map(name => name ? this._filter(name) : this.strutturaList.slice()),
      map(strutture => strutture.sort((a, b) => a.strDenominazione.localeCompare(b.strDenominazione)))
    );

    this.insertionForm = this.fb.group({
      struttureAggiunte: this.fb.array([]),
      cup: [{ value: '', disabled: true }, Validators.required],
      codInterventoPGMEAS: [{ value: '', disabled: true }],
      codInterventoOriginePGMEAS: [{ value: '', disabled: true }],
      annoInserimentoInterventoOrigine: [{ value: null, disabled: true }, [Validators.min(0), Validators.pattern('^[0-9]*$')]],
      codNSISIntervento: [{ value: '', disabled: true }, Validators.required],
      titoloIntervento: [{ value: '', disabled: true }, Validators.required],
      obiettivo: [[], Validators.required],
      finalita: [[], Validators.required],
      tipologia: [[], Validators.required],
      categoria: [[], Validators.required],
      attrezzatura: [[]],
      annoPriorita: [{ value: '', disabled: true }, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      priorita: [{ value: '', disabled: true }, [Validators.required]],
      sottoPriorita: [{ value: '', disabled: true }],
      contrattoTipo: [[], Validators.required],
      appaltoTipo: [[], Validators.required],
      statoProg: [[]],
      costoIntervento: [{ value: '', disabled: true }, [Validators.required, Validators.min(0)]],
      progettazione: [{ value: '', disabled: true }, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      affidamentoLavori: [{ value: '', disabled: true }, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      esecuzioneLavori: [{ value: '', disabled: true }, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      collaudo: [{ value: '', disabled: true }, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      appaltoIntegrato: [{ value: false, disabled: true }, Validators.required],
      totaleDurataIntervento: [0, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
      direttoreNome: [{ value: '', disabled: true }, Validators.required],
      direttoreCognome: [{ value: '', disabled: true }, Validators.required],
      direttoreCF: [{ value: '', disabled: true }, Validators.required],
      commissarioNome: [{ value: '', disabled: true }, Validators.required],
      commissarioCognome: [{ value: '', disabled: true }, Validators.required],
      commissarioCF: [{ value: '', disabled: true }, Validators.required],
      rupNome: [{ value: '', disabled: true }, Validators.required],
      rupCognome: [{ value: '', disabled: true }, Validators.required],
      rupCF: [{ value: '', disabled: true }, Validators.required],
      referenteNome: [{ value: '', disabled: true }, Validators.required],
      referenteCognome: [{ value: '', disabled: true }, Validators.required],
      referenteTelefono: [{ value: '', disabled: true }, Validators.required],
      referenteEmail: [{ value: '', disabled: true }, Validators.required],
      referenteCF: [{ value: '', disabled: true }, Validators.required],
      delibera: [{ value: '', disabled: true }, Validators.required],
      dataDelibera: [{ value: '', disabled: true }, Validators.required],
      allegato: [{ value: null, disabled: true }, Validators.required],
      nomeAllegato: [{ value: null, disabled: true }, Validators.required],
      tipoAllegato: [{ value: null, disabled: true }, Validators.required],
      note: [{ value: '', disabled: true }],
      finanziabile: [{ value: false, disabled: true }],
      //finanziabile: [{ value: false, disabled: !this.checkSuperUser() }, Validators.required],
      previsioniDiSpesa: this.fb.array([])
    });
    this.insertionForm.valueChanges.subscribe(() => {
      this.calcolaTotaleIntervento();
    });

    combineLatest([
      this.registryApiService.getEnteList(),
      // this.registryApiService.getStrutturaListByCodiceAzienda(this.getCodiceEstesoAzienda(this.intervento)),
      this.registryApiService.getFinanziamentoTipoList(),
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
      this.registryApiService.getInterventoStatoList(),
      this.route.paramMap.pipe(
        map((params) => Number(params.get('id'))),
        switchMap((id) =>
          combineLatest([
            this.projectApiService.getInterventoDetailV2(id),
            this.projectApiService.getFinanziamentoListByIntervento(id),
            this.projectApiService.getInterventoStrutturaListByIntervento(id),
            this.projectApiService.getStruttureInterventoList(id, false),
            this.registryApiService.getStrutturaListByInterventoId(id),
            this.registryApiService.getTipologicheFinanziamento(id),
          ])
        )
      ),
    ]).subscribe(
      ([
        enteList,
        // strutturaList,
        finanziamentoTipoList,
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
        [intervento, finanziamentoList, interventoStrutturaList, struttureList, strutturaList, tipologicheFinanziamento],
      ]) => {
        this.enteList = enteList;
        this.strutturaList = strutturaList;
        this.finanziamentoTipoList = finanziamentoTipoList;
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
        this.tipologicheFinanziamento = tipologicheFinanziamento;
        this.interventoStatoList=interventoStatoList;
        // this.interventoFormaRealizzativaList = interventoFormaRealizzativaList;
        this.intervento = intervento;
        this.finanziamentoList = finanziamentoList;
        this.interventoStrutturaList = interventoStrutturaList;
        this.struttureIntervento = struttureList;
        this.anno = this.intervento.intAnno;
        if (this.intervento && this.intervento.dataCreazione) {
          const data = new Date(this.intervento.dataCreazione); // Crea un oggetto Date
          // Formatta la data in "yyyy-mm-dd"
          const year = data.getFullYear();
          const month = String(data.getMonth() + 1).padStart(2, '0'); // i mesi vanno da 0 a 11
          const day = String(data.getDate()).padStart(2, '0');
          this.dataCreazione = `${day}/${month}/${year}`;
          if (this.intervento.pianiFinanziari?.length) {
            this.pianiFinanziariPrincipaliTabellaRows = this.buildTabellaPrincipaliRow();
            this.pianiFinanziariNonPrincipaliTabellaRows = this.buildTabellaNonPrincipaliRow();
          }
          // for (let d of this.intervento.allegatiDeterminazioniDirigenziali!) {
          //   let determinazioneDirigenziale: AllegatoRegione = {};
          //   determinazioneDirigenziale.intAllegatoNumero = d.intAllegatoNumero;
          //   if (d.intAllegatoData) {
          //     determinazioneDirigenziale.intAllegatoData = new Date(d.intAllegatoData);
          //   }
          //   this.DeterminazioniDirigenziali.push(determinazioneDirigenziale);
          // }
          this.buildAllegatiRegione();
          this.buildTipologiaFinanziamenti();
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

  submit() {
    let flagPrevisioneSpesa = this.checkPrevisioneSpesa();
    if(!flagPrevisioneSpesa) {
      this.isPrevSpesaTotInvalid = true;
    } else {
      this.isPrevSpesaTotInvalid = false;
    }
    let flagAllegatiRegione = false;
    if ((this.DGRApprovazione.base64 && this.DeterminazioniDirigenziali.length) || (this.DGRPropostaConsiglioRegionale.base64 && this.DCRApprovazione.base64 && this.DeterminazioniDirigenziali.length)) {
      flagAllegatiRegione = true;
      this.isAllegatiInvalid = false;
    } else {
      flagAllegatiRegione = false;
      this.isAllegatiInvalid = true;
    }
    let flagPianiFinanziari = this.checkPianiFinanziari();
    this.isPianiFinanziariInvalid = !this.checkPianiFinanziari();


    if (flagPrevisioneSpesa && flagAllegatiRegione && flagPianiFinanziari) {

      if(this.insertionForm.valid){
        const direttore = (this.insertionForm.get('direttoreNome')?.value && this.insertionForm.get('direttoreCognome')?.value && this.insertionForm.get('direttoreCF')?.value) ;
        const commissario=(this.insertionForm.get('commissarioNome')?.value && this.insertionForm.get('commissarioCognome')?.value && this.insertionForm.get('commissarioCF')?.value)
        const costoIntervento = this.insertionForm.get('costoIntervento')?.value;
        const totaleStrutture = this.getTotaleStrutture();
        const isResponsabiliConformi = this.isResponsabiliStrutturaConformi();
        if (parseFloat(costoIntervento) !== parseFloat(totaleStrutture)) {
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

        if(!isResponsabiliConformi){
          this.messageService.error("L'inserimento dei nominativi del Responsabile della Struttura Complessa e/o del Responsabile della Struttura Semplice è obbligatorio")
          return;
        }

        // console.log(this.insertionForm);
        this.salvaModificaRegione();
      } else {
        console.log('Invalido');
        // console.log(this.insertionForm);
        // Controlla gli errori di ogni controllo individuale nel FormGroup
        Object.keys(this.insertionForm.controls).forEach(controlName => {
          const control = this.insertionForm.get(controlName);
          if (control && control.errors) {
            console.log(`Errore nel controllo ${controlName}:`, control.errors);
          }
        });
        return;
      }
    }
  }

  // salvataggioIntervento() {
  //   this.loading = true;
  //   const date = this.insertionForm.get('dataDelibera')?.value;
  //   const year = date.getFullYear();
  //   const month = String(date.getMonth() + 1).padStart(2, '0'); // I mesi sono zero-indicizzati
  //   const day = String(date.getDate()).padStart(2, '0');

  //   // Formatta come yyyy-mm-gg
  //   const formattedDate = `${year}-${month}-${day}`;
  //   let salvataggio: InterventoDTO = {
  //     interventoStrutturaList: this.buildInterventoStrutturaList(),
  //     intCup: this.insertionForm.get('cup')?.value,
  //     intCodPgmeas: this.insertionForm.get('codInterventoPGMEAS')?.value,
  //     intCodPgmeasOrig: this.insertionForm.get('codInterventoOriginePGMEAS')?.value,
  //     intAnnoOrig: this.insertionForm.get('annoInserimentoInterventoOrigine')?.value,
  //     intCodicNsis: this.insertionForm.get('codNSISIntervento')?.value,
  //     intTitolo: this.insertionForm.get('titoloIntervento')?.value,
  //     listaIntObiettivoId: this.insertionForm.get('obiettivo')?.value,
  //     listaIntFinalitaId: this.insertionForm.get('finalita')?.value,
  //     listaIntTipoId: this.insertionForm.get('tipologia')?.value,
  //     listaIntCategoriaId: this.insertionForm.get('categoria')?.value,
  //     listaIntTipoDetId: this.insertionForm.get('attrezzatura')?.value,
  //     intPrioritaAnno: this.insertionForm.get('annoPriorita')?.value,
  //     intPriorita: this.insertionForm.get('priorita')?.value,
  //     intSottopriorita: this.insertionForm.get('sottoPriorita')?.value,
  //     listaIntContrattoId: this.insertionForm.get('contrattoTipo')?.value,
  //     listaIntAppaltoTipoId: this.insertionForm.get('appaltoTipo')?.value,
  //     listaIntStatoProgettualeId: this.insertionForm.get('statoProg')?.value,
  //     intImporto: this.insertionForm.get('costoIntervento')?.value ? this.insertionForm.get('costoIntervento')?.value.toString().replace(',', '.'):'0.00',
  //     intProgettazioneGg: this.insertionForm.get('progettazione')?.value,
  //     intAffidamentoLavoriGg: this.insertionForm.get('affidamentoLavori')?.value,
  //     intEsecuzioneLavoriGg: this.insertionForm.get('esecuzioneLavori')?.value,
  //     intCollaudoGg: this.insertionForm.get('collaudo')?.value,
  //     intAppaltoIntegrato: this.insertionForm.get('appaltoIntegrato')?.value,
  //     intDirettoreGeneraleNome: this.insertionForm.get('direttoreNome')?.value,
  //     intDirettoreGeneraleCognome: this.insertionForm.get('direttoreCognome')?.value,
  //     intDirettoreGeneraleCf: this.insertionForm.get('direttoreCF')?.value,
  //     intCommissarioNome: this.insertionForm.get('commissarioNome')?.value,
  //     intCommissarioCognome: this.insertionForm.get('commissarioCognome')?.value,
  //     intCommissarioCf: this.insertionForm.get('commissarioCF')?.value,
  //     intRupNome: this.insertionForm.get('rupNome')?.value,
  //     intRupCognome: this.insertionForm.get('rupCognome')?.value,
  //     intRupCf: this.insertionForm.get('rupCF')?.value,
  //     intReferentePraticaNome: this.insertionForm.get('referenteNome')?.value,
  //     intReferentePraticaCognome: this.insertionForm.get('referenteCognome')?.value,
  //     intReferentePraticaTelefono: this.insertionForm.get('referenteTelefono')?.value,
  //     intReferentePraticaEmail: this.insertionForm.get('referenteEmail')?.value,
  //     intReferentePraticaCf: this.insertionForm.get('referenteCF')?.value,
  //     intNote: this.insertionForm.get('note')?.value,
  //     intAnno: this.anno,
  //     intQuadranteId: parseInt(this.user.idQuadrante),
  //     intCopiatoEnteId: this.intCopiatoEnteId,
  //     intCopiatoId: this.intCopiatoId,
  //     intId: this.intervento.intId,
  //     dataCreazione: this.intervento.dataCreazione,
  //     dataCancellazione: null,
  //     dataModifica: null,
  //     intFinanziabile: this.insertionForm.get('intFinanziabile')?.value,
  //     previsioniDiSpesa: this.buildPrevisioneDiSpesaList()
  //   };
  //   if (this.controlloDelibera(formattedDate)) {
  //     salvataggio.intAllegatoDeliberaNew = {
  //       intAllegatoNumero: this.insertionForm.get('delibera')?.value,
  //       intAllegatoData: formattedDate,
  //       idAllegato: null,
  //       fileNameUser: this.insertionForm.get('nomeAllegato')?.value,
  //       fileType: this.insertionForm.get('tipoAllegato')?.value,
  //       base64: this.insertionForm.get('allegato')?.value,
  //     }
  //     salvataggio.intAllegatoDeliberaToDelete = {
  //       intAllegatoNumero: this.deliberaVecchia.intAllegatoNumero,
  //       intAllegatoData: this.deliberaVecchia.intAllegatoData,
  //       idAllegato: this.deliberaVecchia.idAllegato,
  //       fileNameUser: this.deliberaVecchia.fileNameUser,
  //       fileType: this.deliberaVecchia.fileType,
  //       base64: this.deliberaVecchia.base64,
  //     }
  //     // salvataggio.intAllegatoDeliberaToDelete = this.deliberaVecchia;
  //   } else {
  //     salvataggio.intAllegatoDeliberaNew = null;
  //     salvataggio.intAllegatoDeliberaToDelete = null;
  //   }

  //   console.log(salvataggio);
  //   this.projectApiService.editIntervento(salvataggio, this.intervento.intId).subscribe(save => {
  //     this.intId = save.intId;
  //     for (let i = 0; i<this.struttureAggiunte.controls.length; i++) {
  //       this.struttureAggiunte.removeAt(i);
  //     }
  //     this.removeFile();
  //     this.insertionForm.reset();
  //     this.salvataggioEffettuato = true;
  //     this.loading = false;
  //   }, err => {
  //     this.loading = false;
  //   })
  // }

  buildInterventoStrutturaList(): InterventoStrutturaDTO[] {
    let listaStrutture: InterventoStrutturaDTO[] = [];
    for (let str of this.struttureAggiunte.controls) {
      let newStr: InterventoStrutturaDTO = {
        intStrAffidamentoLavoriGg: str.value.affidamentoLavori || 0,
        intStrAppaltoIntegrato: str.value.appaltoIntegrato || false,
        intStrCollaudoGg: str.value.collaudo || 0,
        intStrEsecuzioneLavoriGg: str.value.esecuzioneLavori || 0,
        intStrId: str.value.intStrId,
        strId: str.value.struttura.strId,
        intStrImporto: str.value.costoStruttura?str.value.costoStruttura.toString().replace(',', '.'):'0.00',
        intStrProgettazioneGg: str.value.progettazione || 0,
        intstrRespStrComplesCf: str.value.intstrRespStrComplesCf || '',
        intstrRespStrComplesCognome: str.value.intstrRespStrComplesCognome || '',
        intstrRespStrComplesNome: str.value.intstrRespStrComplesNome || '',
        intstrRespStrSemplCf: str.value.intstrRespStrComplesCf || '',
        intstrRespStrSemplCognome: str.value.intstrRespStrSemplCognome || '',
        intstrRespStrSemplNome: str.value.intstrRespStrSemplNome || '',
        intStrTipoIntEdilMap: this.buildinterventiEdiliziListMap(str.value.listaInterventiEdilizi || []),
        quadroEconMap: this.buildQuadroEconomicoMap(str.value.listaQuadroEconomico || []),
        parerePPP: str.value.parerePPP.parere || false,
        parereHTA: str.value.parerePPP.parere || false
      };
      listaStrutture.push(newStr);
    }
    return listaStrutture;
  }

  buildPrevisioneDiSpesaList(): PrevisioneDiSpesa[] {
    let listaStrutture: PrevisioneDiSpesa[] = [];
    // for(let str of this.struttureAggiunte.controls){
    //   let newStr: InterventoStrutturaDTO = {
    //     intStrAffidamentoLavoriGg: str.value.affidamentoLavori || 0,
    //     intStrAppaltoIntegrato: str.value.appaltoIntegrato || false,
    //     intStrCollaudoGg: str.value.collaudo || 0,
    //     intStrEsecuzioneLavoriGg:  str.value.esecuzioneLavori || 0,
    //     intStrId: str.value.intStrId,
    //     strId: str.value.struttura.strId,
    //     intStrImporto: parseFloat((str.value.costoStruttura || '0').replace(',', '.')),
    //     intStrProgettazioneGg: str.value.progettazione || 0,
    //     intstrRespStrComplesCf: null,
    //     intstrRespStrComplesCognome: str.value.intstrRespStrComplesCognome || '',
    //     intstrRespStrComplesNome:  str.value.intstrRespStrComplesNome || '',
    //     intstrRespStrSemplCf: null,
    //     intstrRespStrSemplCognome: str.value.intstrRespStrSemplCognome || '',
    //     intstrRespStrSemplNome: str.value.intstrRespStrSemplNome || '',
    //     intStrTipoIntEdilMap: this.buildinterventiEdiliziListMap(str.value.listaInterventiEdilizi || []),
    //     quadroEconMap: this.buildQuadroEconomicoMap(str.value.listaQuadroEconomico || []),
    //     parerePPP: str.value.parerePPP.parere || false,
    //     parereHTA: str.value.parerePPP.parere || false
    //   };
    //   listaStrutture.push(newStr);
    // }
    return listaStrutture;
  }

  removeFile(): void {
    this.fileName = null;
    this.fileUrl = null;
    this.file = null;
    this.insertionForm.patchValue({
      allegato: null // Imposta il file nel form
    });
  }

  removeFilePPP(index: number): void {
    this.filesPPP.splice(index, 1);
  }

  removeFileHTA(index: number): void {
    this.filesHTA.splice(index, 1);
    if (this.struttureIntervento[index].parereHta.allegati && this.struttureIntervento[index].parereHta.allegati.length) {
      if (this.struttureIntervento[index].parereHta.allegati[0].idAllegato) {
        this.filesHTAChanged.push(index);
      }
    }
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

  // buildParere(par: boolean, allegato: AllegatoDelibera): Parere {






  //   // let p: Parere = {
  //   //   parere: par,
  //   //   allegati: allegato
  //   // }

  //   // return p;
  // }

  displayFn(struttura: any): string {
    return struttura ? struttura.strDenominazione + " (" + struttura.strCod + ")" : '';
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
        costoStruttura: [{ value: '', disabled: true }],
        progettazione: [{ value: '', disabled: true }],
        affidamentoLavori: [''],
        esecuzioneLavori: [''],
        collaudo: [''],
        totaleDurataIntervento: [0],
        appaltoIntegrato: [false],
        totaleDurataStimataIntervento: [0],
        listaInterventiEdilizi: this.fb.array([]),
        listaQuadroEconomico: this.fb.array([]),
        intstrRespStrComplesNome: [''],
        intstrRespStrComplesCognome: [''],
        intstrRespStrComplesCf: [''],
        intstrRespStrSemplNome: [''],
        intstrRespStrSemplCognome: [''],
        intstrRespStrSemplCf: [''],
        parerePPP: [false],
        numeroPPP: [''],
        dataPPP: [{ value: '', disabled: false }, [Validators.required, Validators.pattern('^([0-2][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$')]],
        allegatoPPP: [{ value: null, disabled: false }, Validators.required],
        nomeAllegatoPPP: [{ value: null, disabled: false }, Validators.required],
        tipoAllegatoPPP: [{ value: null, disabled: false }, Validators.required],
        parereHTA: [false, Validators.required],
        numeroHTA: [''],
        dataHTA: [{ value: '', disabled: false }],
        allegatoHTA: [{ value: null, disabled: false }],
        nomeAllegatoHTA: [{ value: null, disabled: false }],
        tipoAllegatoHTA: [{ value: null, disabled: false }],
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
        selezionato: [{ value: intervento.selezionato, disabled: true }],
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
        valoreNumerico: [{ value: quadro.valoreNumerico }, [Validators.min(0)]],
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

  getDescrizioneAzienda(intervento: InterventoVisualizza): string | any {
    return this.enteList.find(ente => ente.enteId === intervento.enteId)?.enteDesc;
  }

  // getCodiceEstesoAzienda(intervento: InterventoVisualizza): string | any {
  //   return this.enteList.find(ente => ente.enteId === intervento.enteId)?.enteCodEsteso;
  // }

  getDescrizioneQuadrante(intervento: InterventoVisualizza): string | any {
    return this.quadranteList.find(quadrante => quadrante.quadranteId === intervento.quadranteId)?.quadranteDesc;
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
    if(file){
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

  onFileSelectedPPP(event: any, index: number): void {
   const maxFileSize = 20 * 1024 * 1024; // 20MB in byte
    const input = event.target as HTMLInputElement;
    const file: File = event.target.files[0];
    if(file){
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

    let indexToRemove = this.filesPPP.findIndex(f => f.index === index);
    if (indexToRemove !== -1) {
      this.filesPPP.splice(indexToRemove, 1);
    }
    if (file) {
      let filePPP = {
        index: index,
        fileName: file.name,
        file: file,
        fileUrl: URL.createObjectURL(file)
      }
      this.fileToBase64(file).then(base64 => {
        // Verifica se il FormArray ha l'indice corretto
        const formGroup = this.struttureAggiunte.at(index) as FormGroup;
        if (formGroup) {
          formGroup.patchValue({
            allegatoPPP: base64.split(',')[1],
            nomeAllegatoPPP: file.name,
            tipoAllegatoPPP: file.type
          });
        } else {
          console.error('FormGroup non trovato per l\'indice:', index);
        }
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });

      if (indexToRemove !== -1) {
        this.filesPPPChanged.push(index);
      }

      this.filesPPP.push(filePPP);
      input.value = '';
    }
  }

  onFileSelectedHTA(event: any, index: number): void {
   const maxFileSize = 20 * 1024 * 1024; // 20MB in byte
    const input = event.target as HTMLInputElement;
    const file: File = event.target.files[0];
    if(file){
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
    let indexToRemove = this.filesHTA.findIndex(f => f.index === index);
    if (indexToRemove !== -1) {
      this.filesHTA.splice(indexToRemove, 1);
    }
    if (file) {
      let fileHTA = {
        index: index,
        fileName: file.name,
        file: file,
        fileUrl: URL.createObjectURL(file)
      }
      this.fileToBase64(file).then(base64 => {
        // Verifica se il FormArray ha l'indice corretto
        const formGroup = this.struttureAggiunte.at(index) as FormGroup;
        if (formGroup) {
          formGroup.patchValue({
            allegatoHTA: base64.split(',')[1],
            nomeAllegatoHTA: file.name,
            tipoAllegatoHTA: file.type
          });
        } else {
          console.error('FormGroup non trovato per l\'indice:', index);
        }
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });

      if (indexToRemove !== -1) {
        this.filesHTAChanged.push(index);
      }

      this.filesHTA.push(fileHTA);
      input.value = '';
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

  getFileNamePPP(index: number): string {
    const file = this.filesPPP.find(f => f.index === index);
    return file?.fileName ?? "";
  }

  getFileUrlPPP(index: number): string {
    const file = this.filesPPP.find(f => f.index === index);
    return file?.fileUrl ?? "";
  }

  checkFilePPP(index: number): boolean {
    const file = this.filesPPP.find(f => f.index === index);
    return file !== undefined;
  }

  getFileNameHTA(index: number): string {
    const file = this.filesHTA.find(f => f.index === index);
    return file?.fileName ?? "";
  }

  getFileUrlHTA(index: number): string {
    const file = this.filesHTA.find(f => f.index === index);
    return file?.fileUrl ?? "";
  }

  checkFileHTA(index: number): boolean {
    const file = this.filesHTA.find(f => f.index === index);
    return file !== undefined;
  }

  private _filter(name: string): any[] {
    const filterValue = name.toLowerCase();
    return this.strutturaList.filter(option =>
      option.strDenominazione.toLowerCase().includes(filterValue)
    );
  }

  inizializzazioneModifica() {
    this.loading = true;
    if (this.intervento) {
      this.intCopiatoEnteId = this.intervento.enteId;
      this.intCopiatoId = this.intervento.intId;
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
        dataDelibera: all?.inteAllegatoDataFormated,
        finanziabile: this.intervento.intFinanziabile,
        previsioniDiSpesa: this.intervento.previsioniDiSpesa
      });

      if (this.intervento.previsioniDiSpesa) {
        for (let previsione of this.intervento.previsioniDiSpesa) {
          let previsioneFormGroup = this.fb.group({
            annoPrevisione: [{ value: 0, disabled: false }, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]],
            importoPrevisione: [{ value: 0, disabled: false }, [Validators.required, Validators.min(0), Validators.pattern('^[0-9]*$')]]
          });

          previsioneFormGroup.patchValue({
            annoPrevisione: previsione.anno,
            importoPrevisione: previsione.importo
          })

          this.previsioniDiSpesa.push(previsioneFormGroup);

        }
      }

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
            }
          }

        }
      }

      if (this.allegato && all) {
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

      for (let [index, struttura] of this.struttureIntervento.entries()) {
        let strutturaSelezionata = this.strutturaList.find(str => str.strId == struttura.strId);
        let strutturaFormGroup = this.fb.group({
          struttura: [strutturaSelezionata],
          costoStruttura: [{ value: '', disabled: true }],
          progettazione: [{ value: 0, disabled: true }],
          affidamentoLavori: [{ value: 0, disabled: true }],
          esecuzioneLavori: [{ value: 0, disabled: true }],
          collaudo: [{ value: 0, disabled: true }],
          totaleDurataIntervento: [0],
          appaltoIntegrato: [{ value: false, disabled: true }],
          totaleDurataStimataIntervento: [0],
          listaInterventiEdilizi: this.fb.array([]),
          listaQuadroEconomico: this.fb.array([]),
          intstrRespStrComplesNome: [{ value: '', disabled: true }],
          intstrRespStrComplesCognome: [{ value: '', disabled: true }],
          intstrRespStrComplesCf: [{ value: '', disabled: true }],
          intstrRespStrSemplNome: [{ value: '', disabled: true }],
          intstrRespStrSemplCognome: [{ value: '', disabled: true }],
          intstrRespStrSemplCf: [{ value: '', disabled: true }],
          intStrId: [0],
          parerePPP: [{ value: false, disabled: true }],
          numeroPPP: [{ value: '', disabled: false }, Validators.required],
          dataPPP: [{ value: new Date(), disabled: false }, Validators.required],
          allegatoPPP: [{ value: null, disabled: false }, Validators.required],
          nomeAllegatoPPP: [{ value: '', disabled: false }, Validators.required],
          tipoAllegatoPPP: [{ value: null, disabled: false }, Validators.required],
          parereHTA: [{ value: false, disabled: true }],
          numeroHTA: [{ value: '', disabled: false }],
          dataHTA: [{ value: new Date(), disabled: false }],
          allegatoHTA: [{ value: null, disabled: false }],
          nomeAllegatoHTA: [{ value: '', disabled: false }],
          tipoAllegatoHTA: [{ value: null, disabled: false }]
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
          intStrId: struttura.intStrId,
          parerePPP: struttura.parerePPP.parere || false,
          numeroPPP: struttura.parerePPP?.allegati?.[0]?.intAllegatoNumero || '',
          dataPPP: struttura.parerePPP?.allegati?.[0]?.intAllegatoData ? new Date(struttura.parerePPP?.allegati?.[0]?.intAllegatoData) : null,
          allegatoPPP: null,
          nomeAllegatoPPP: '',
          tipoAllegatoPPP: null,
          parereHTA: struttura.parereHta.parere || false,
          numeroHTA: struttura.parereHta?.allegati?.[0]?.intAllegatoNumero || '',
          dataHTA: struttura.parereHta?.allegati?.[0]?.intAllegatoData ? new Date(struttura.parereHta?.allegati?.[0]?.intAllegatoData) : null,
          allegatoHTA: null,
          nomeAllegatoHTA: '',
          tipoAllegatoHTA: null
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

        if (struttura.parerePPP.allegati && struttura.parerePPP.allegati.length) {
          let allegatoPPP = struttura.parerePPP.allegati[0];
          if (allegatoPPP) {
            this.projectApiService.downloadAllegatoById(allegatoPPP.idAllegato!).subscribe(allegato => {
              let file = this.downloadFile(allegato.fileName, allegato.fileType, allegato.base64);
              let fileAll: FileAllegato = {
                index: index,
                fileName: allegato.fileName,
                file: file,
                fileUrl: URL.createObjectURL(file)
              }
              this.filesPPP.push(fileAll);
            })
          }
        }

        if (struttura.parereHta.allegati && struttura.parereHta.allegati.length) {
          let allegatoHta = struttura.parereHta.allegati[0];
          if (allegatoHta) {
            this.projectApiService.downloadAllegatoById(allegatoHta.idAllegato!).subscribe(allegato => {
              let file = this.downloadFile(allegato.fileName, allegato.fileType, allegato.base64);
              let fileAll: FileAllegato = {
                index: index,
                fileName: allegato.fileName,
                file: file,
                fileUrl: URL.createObjectURL(file)
              }
              this.filesHTA.push(fileAll);
            })
          }
        }

        if(struttura.parerePPP.parere) {
          strutturaFormGroup?.get('numeroPPP')!.enable();
          strutturaFormGroup?.get('dataPPP')!.enable();
        } else {
          strutturaFormGroup?.get('numeroPPP')!.disable();
          strutturaFormGroup?.get('dataPPP')!.disable();
        }

        if(struttura.parereHta.parere) {
          strutturaFormGroup?.get('numeroHTA')!.enable();
          strutturaFormGroup?.get('dataHTA')!.enable();
        } else {
          strutturaFormGroup?.get('numeroHTA')!.disable();
          strutturaFormGroup?.get('dataHTA')!.disable();
        }

        this.struttureAggiunte.push(strutturaFormGroup);
        this.loading = false;
      }

    } else {
      this.loading = false;
    }
  };

  controlloDelibera(formattedDate: string) {

    if (this.deliberaVecchia.base64 !== this.insertionForm.get('allegato')?.value) {
      return true;
    }
    if (this.deliberaVecchia.intAllegatoNumero !== this.insertionForm.get('delibera')?.value) {
      return true;
    }
    if (this.deliberaVecchia.intAllegatoDataFormatted !== formattedDate) {
      return true;
    }
    return false;
  }

  back() {
    const back = history.state.back ?? '/consultazione-interventi';
    this.router.navigateByUrl(back);
  }

  addPrevisione() {
    if (this.formPrevisione) {
      if (this.formPrevisione.valid) {
        const annoPrevisione = this.formPrevisione.get('previsioneAnnoControl')?.value;

        const anniPrevisione = this.previsioniDiSpesa.controls.map(control => control.get('annoPrevisione')?.value);

        const annoEsistente = anniPrevisione.some(anno => anno === annoPrevisione);

        if (annoEsistente) {
          this.formPrevisione.get('previsioneAnnoControl')?.setErrors({ existingYear: true });
          return;
        }

        this.formPrevisione.get('previsioneAnnoControl')?.setErrors(null);

        let previsioneFormGroup = this.fb.group({
          annoPrevisione: [{ value: annoPrevisione, disabled: false }, [Validators.required]],
          importoPrevisione: [{ value: null, disabled: false }, [Validators.required]]
        });

        this.previsioniDiSpesa.push(previsioneFormGroup);
      }
    }
  }

  yearValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const currentYear = new Date().getFullYear();
    const value = control.value;

    if (value < currentYear) {
      return { invalidYear: true }; // Restituisci errore se l'anno è < all'anno corrente
    }
    return null; // Restituisci null se il valore è valido
  };

  removeRow(index: number) {
    this.previsioniDiSpesa.removeAt(index);
  }

  onInputRegioneChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.importoRegione = parseFloat(input.value);
  }

  onInputStatoChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.importoStato = parseFloat(input.value);
  }

  onInputChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.importoTotale = parseFloat(input.value);
  }

  // Funzione per limitare i caratteri a numeri e '/'
  onInputDate(event: KeyboardEvent) {
    const allowedKeys = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', 'Backspace', 'Tab', 'ArrowLeft', 'ArrowRight'];

    if (!allowedKeys.includes(event.key)) {
      event.preventDefault();
    }
  }

  buildTabellaPrincipaliRow(): PianoFinanziarioTabellaRow[] {
    if (this.intervento.pianiFinanziari) {
      for (let pianoFinanziario of this.intervento.pianiFinanziari) {
        let finTipoId = pianoFinanziario.tipologiaId;
        let finTipoDesc = this.finanziamentoTipoList.find(f => f.finTipoId === finTipoId)?.finTipoDesc;
        let finTipoDetId = pianoFinanziario.tipologiaDettaglioId;
        let finTipoDetDesc = this.finanziamentoTipoDetList.find(f => f.finTipoDetId === finTipoDetId)?.finTipoDetDesc;
        let finImporto = pianoFinanziario.importoTotale;
        let finPercRegione = pianoFinanziario.finanziamentoImportoTipo?.find(t => t.finanziamentoImportoTipoCod === IMP_REGIONE)?.finanziamentoImporto ?? 0;
        let finPercStato = pianoFinanziario.finanziamentoImportoTipo?.find(t => t.finanziamentoImportoTipoCod === IMP_STATO)?.finanziamentoImporto ?? 0;
        let isPrincipale = pianoFinanziario.isPrincipale;

        //trovo il tipo di finaziamento
        let finanziamento = this.tipologicheFinanziamento.find(t => t.tipologiaId === finTipoId);
        let dettaglioFinanziamento = finanziamento?.finanziamentoTipiDettaglio.find(d => d.tipologiaDettaglioId === finTipoDetId);

        let importoRegione = 0;
        let importoStato = 0;
        if (finImporto > 0 && ((finPercRegione && finPercRegione > 0) || !finPercRegione)) {
          importoRegione = pianoFinanziario.finanziamentoImportoTipo.find(t => t.finanziamentoImportoTipoCod === IMP_REGIONE)?.finanziamentoImporto ?? 0;
        } else {
          importoRegione = 0;
        }

        if (finImporto > 0 && ((finPercStato && finPercStato > 0) || !finPercStato)) {
          importoStato = pianoFinanziario.finanziamentoImportoTipo.find(t => t.finanziamentoImportoTipoCod === IMP_STATO)?.finanziamentoImporto ?? 0;
        } else {
          importoStato = 0;
        }

        let pianiFinanziariTabellaRow: PianoFinanziarioTabellaRow = {
          finTipoId: finTipoId,
          finTipoDesc: finTipoDesc,
          finTipoDetId: finTipoDetId,
          finTipoDetDesc: finTipoDetDesc,
          finImporto: finImporto,
          finPercRegione: importoRegione,
          finPercStato: importoStato,
          isPrincipale: isPrincipale,
          finanziamentoImportoTipo: dettaglioFinanziamento?.finanziamentoImportiTipo ?? []
        }

        if (isPrincipale) {
          this.pianiFinanziariPrincipaliTabellaRows.push(pianiFinanziariTabellaRow);
          this.totImportoRegionePrincipali += importoRegione;
          this.totImportoStatoPrincipali += importoStato;
          this.totImportoPrincipali += finImporto;
          this.totImportoRegione += importoRegione;
          this.totImportoStato += importoStato;
          this.totImporto += finImporto;
        }
      }
    }

    return this.pianiFinanziariPrincipaliTabellaRows;
  }

  buildTabellaNonPrincipaliRow(): PianoFinanziarioTabellaRow[] {
    if (this.intervento.pianiFinanziari) {
      for (let pianoFinanziario of this.intervento.pianiFinanziari) {
        let finTipoId = pianoFinanziario.tipologiaId;
        let finTipoDesc = this.finanziamentoTipoList.find(f => f.finTipoId === finTipoId)?.finTipoDesc;
        let finTipoDetId = pianoFinanziario.tipologiaDettaglioId;
        let finTipoDetDesc = this.finanziamentoTipoDetList.find(f => f.finTipoDetId === finTipoDetId)?.finTipoDetDesc;
        let finImporto = pianoFinanziario.importoTotale;
        let finPercRegione = pianoFinanziario.finanziamentoImportoTipo?.find(t => t.finanziamentoImportoTipoCod === IMP_REGIONE)?.finanziamentoImporto ?? 0;
        let finPercStato = pianoFinanziario.finanziamentoImportoTipo?.find(t => t.finanziamentoImportoTipoCod === IMP_STATO)?.finanziamentoImporto ?? 0;
        let isPrincipale = pianoFinanziario.isPrincipale;

        let finanziamento = this.tipologicheFinanziamento.find(t => t.tipologiaId === finTipoId);
        let dettaglioFinanziamento = finanziamento?.finanziamentoTipiDettaglio.find(d => d.tipologiaDettaglioId === finTipoDetId);

        let importoRegione = 0;
        let importoStato = 0;
        if (finImporto > 0 && ((finPercRegione && finPercRegione > 0) || !finPercRegione)) {
          importoRegione = pianoFinanziario.finanziamentoImportoTipo.find(t => t.finanziamentoImportoTipoCod === IMP_REGIONE)?.finanziamentoImporto ?? 0;
        } else {
          importoRegione = 0;
        }

        if (finImporto > 0 && ((finPercStato && finPercStato > 0) || !finPercStato)) {
          importoStato = pianoFinanziario.finanziamentoImportoTipo.find(t => t.finanziamentoImportoTipoCod === IMP_STATO)?.finanziamentoImporto ?? 0;
        } else {
          importoStato = 0;
        }

        let pianiFinanziariTabellaRow: PianoFinanziarioTabellaRow = {
          finTipoId: finTipoId,
          finTipoDesc: finTipoDesc,
          finTipoDetId: finTipoDetId,
          finTipoDetDesc: finTipoDetDesc,
          finImporto: finImporto,
          finPercRegione: importoRegione,
          finPercStato: importoStato,
          isPrincipale: isPrincipale,
          finanziamentoImportoTipo: dettaglioFinanziamento?.finanziamentoImportiTipo ?? []
        }

        if (!isPrincipale) {
          this.pianiFinanziariNonPrincipaliTabellaRows.push(pianiFinanziariTabellaRow);
          this.totImportoRegioneAltri += importoRegione;
          this.totImportoStatoAltri += importoStato;
          this.totImportoAltri += finImporto;
          this.totImportoRegione += importoRegione;
          this.totImportoStato += importoStato;
          this.totImporto += finImporto;
        }
      }
    }

    return this.pianiFinanziariNonPrincipaliTabellaRows;
  }


  rimuoviPianoFinanziarioPrincipale(index: number) {
    if (index >= 0 && index < this.pianiFinanziariPrincipaliTabellaRows.length) {
      this.pianiFinanziariPrincipaliTabellaRows.splice(index, 1);
    }
  }

  rimuoviPianoFinanziarioNonPrincipale(index: number) {
    if (index >= 0 && index < this.pianiFinanziariNonPrincipaliTabellaRows.length) {
      this.pianiFinanziariNonPrincipaliTabellaRows.splice(index, 1);
    }
  }

  onSelectionChangeTipologia(event: any) {
    this.finanziamentoTipologicheDettagli.splice(0, this.finanziamentoTipologicheDettagli.length);
    let finTipoId = event.value;
    if (this.tipologicheFinanziamento) {
      let tipologica = this.tipologicheFinanziamento.find(t => t.tipologiaId === finTipoId);
      if (tipologica) {
        for (let t of tipologica.finanziamentoTipiDettaglio) {
          let dettaglio: FinanziamentoTipoDet = {
            finTipoDetId: t.tipologiaDettaglioId,
            finTipoDetCod: t.tipologiaDettaglioCod,
            finTipoDetDesc: t.tipologiaDettaglioDesc,
            finTipoId: finTipoId,
            validitaInizio: this.finanziamentoTipoDetList.find(d => d.finTipoDetId === t.tipologiaDettaglioId)?.validitaInizio as number,
            validitaFine: this.finanziamentoTipoDetList.find(d => d.finTipoDetId === t.tipologiaDettaglioId)?.validitaFine as number,
            finTipoDetPercentualeStato: t.finanziamentoImportiTipo.find(i => i.finanziamentoImportoTipoCod === IMP_STATO)?.finanziamentoPercentuale ?? undefined,
            finTipoDetPercentualeRegione: t.finanziamentoImportiTipo.find(i => i.finanziamentoImportoTipoCod === IMP_REGIONE)?.finanziamentoPercentuale ?? undefined,
          }

          this.finanziamentoTipologicheDettagli.push(dettaglio);
        }
      }
    }

    // finTipoDetId: number;
    // finTipoDetCod: string;
    // finTipoDetDesc: string;
    // finTipoId: number;
    // finTipoDetPercentualeStato?: number;
    // finTipoDetPercentualeRegione?: number;
    // finTipoDetPercentualeAltro?: number;
  }

  onSelectionChangeDettaglio(event: any) {
    let finTipoDetId = event.value;
    let finTipoDet = this.finanziamentoTipoDetList.find(f => f.finTipoDetId === finTipoDetId);
    if (finTipoDet?.finTipoDetPercentualeRegione && finTipoDet?.finTipoDetPercentualeStato) {
      if (finTipoDet?.finTipoDetPercentualeRegione === 0 && finTipoDet?.finTipoDetPercentualeStato === 0) {
        this.isPercentualiRegioneEStato = false;
      } else {
        this.isPercentualiRegioneEStato = true;
      }
    } else {
      this.isPercentualiRegioneEStato = false;
    }
    this.isDettaglioSelezionato = true;
  }

  onFileDGRApprovazioneSelected(event: any): void {
    const file: File = event.target.files[0];
    const input = event.target as HTMLInputElement;

    if (file) {
      this.DGRApprovazione!.fileNameUser = file.name;
      this.fileDGRApprovazione = file;
      this.fileToBase64(file).then(base64 => {
        this.DGRApprovazione!.base64 = base64.split(',')[1];
        this.DGRApprovazione!.fileType = file.type;
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });
      this.fileUrlDGRApprovazione = URL.createObjectURL(file);
      this.dgrApprovazioneChanged = true;
      input.value = ''; // Resetta l'input per permettere il cambio di file con stesso nome
    }
  }

  onFileDGRPropostaConsRegionaleSelected(event: any): void {
    const file: File = event.target.files[0];
    const input = event.target as HTMLInputElement;

    if (file) {
      this.DGRPropostaConsiglioRegionale!.fileNameUser = file.name;
      this.fileDGRPropostaConsiglioRegionale = file;
      this.fileToBase64(file).then(base64 => {
        this.DGRPropostaConsiglioRegionale!.base64 = base64.split(',')[1];
        this.DGRPropostaConsiglioRegionale!.fileType = file.type;
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });
      this.fileUrlDGRPropostaConsRegionale = URL.createObjectURL(file);
      this.dgrPropConsRegionaleChanged = true;
      input.value = ''; // Resetta l'input per permettere il cambio di file con stesso nome
    }
  }

  onFileDCRApprovazioneSelected(event: any): void {
    const file: File = event.target.files[0];
    const input = event.target as HTMLInputElement;

    if (file) {
      this.DCRApprovazione!.fileNameUser = file.name;
      this.fileDCRApprovazione = file;
      this.fileToBase64(file).then(base64 => {
        this.DCRApprovazione!.base64 = base64.split(',')[1];
        this.DCRApprovazione!.fileType = file.type;
      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      });
      this.fileUrlDCRApprovazione = URL.createObjectURL(file);
      this.dcrApprovazioneChanged = true;
      input.value = ''; // Resetta l'input per permettere il cambio di file con stesso nome
    }
  }

  removeFileDGRApprovazione(): void {
    this.fileUrlDGRApprovazione = null;
    this.fileDGRApprovazione = null;
    this.DGRApprovazione = {};
  }

  removeFileDGRPropostaConsiglioRegionale(): void {
    this.fileUrlDGRPropostaConsRegionale = null;
    this.fileDGRPropostaConsiglioRegionale = null;
    this.DGRPropostaConsiglioRegionale = {};
  }

  removeFileDCRApprovazione(): void {
    this.fileUrlDCRApprovazione = null;
    this.fileDCRApprovazione = null;
    this.DCRApprovazione = {};
  }

  onFileDeterminazioniDirigenzialiSelected(event: any): void {
    const file: File = event.target.files[0];
    const input = event.target as HTMLInputElement;

    if (file) {
      let DeterminazioneDirigenziale: AllegatoRegione = {};
      DeterminazioneDirigenziale.fileNameUser = file.name;
      this.fileToBase64(file).then(base64 => {
        DeterminazioneDirigenziale!.base64 = base64.split(',')[1];
        DeterminazioneDirigenziale!.fileType = file.type;

        this.fileDeterminazioniDirigenziali.push(file);
        this.DeterminazioniDirigenziali.push(DeterminazioneDirigenziale);
        this.DeterminazioniDirigenzialiAdded.push(DeterminazioneDirigenziale);

      }).catch(error => {
        console.error('Errore nella conversione del file:', error);
      })
      this.fileUrlDeterminazioniDirigenziali?.push(URL.createObjectURL(file));
      input.value = '';
    }
  }

  removeFileDeterminazioneDirigenziale(index: number): void {
    if (index >= 0 && index < this.DeterminazioniDirigenziali.length) {
      if (this.DeterminazioniDirigenziali[index].idAllegato) {
        this.DeterminazioniDirigenzialiDeleted.push(this.DeterminazioniDirigenziali[index]);
      }
      this.DeterminazioniDirigenziali.splice(index, 1);
      this.fileDeterminazioniDirigenziali.splice(index, 1);
      this.fileUrlDeterminazioniDirigenziali?.splice(index, 1);
      this.DeterminazioniDirigenzialiAdded.splice(index, 1);
    }
  }

  buildAllegatiRegione() {
    if (this.intervento.allegatoDgrApprovazione?.length) {
      this.projectApiService.downloadAllegatoById(this.intervento.allegatoDgrApprovazione[0].idAllegato).subscribe(allegato => {
        this.DGRApprovazione.intAllegatoNumero = this.intervento.allegatoDgrApprovazione![0].intAllegatoNumero;
        if (this.intervento.allegatoDgrApprovazione![0].intAllegatoData) {
          this.DGRApprovazione.intAllegatoData = new Date(this.intervento.allegatoDgrApprovazione![0].intAllegatoData);
        }
        this.DGRApprovazione.fileNameUser = allegato.fileName;
        this.DGRApprovazione.base64 = allegato.base64;
        let file: File = this.downloadFile(allegato.fileName, allegato.fileType, allegato.base64);
        this.fileDGRApprovazione = file;
        this.fileUrlDGRApprovazione = URL.createObjectURL(file);
      })
    }

    if (this.intervento.allegatoDgrPropostaCR?.length) {
      this.projectApiService.downloadAllegatoById(this.intervento.allegatoDgrPropostaCR[0].idAllegato).subscribe(allegato => {
        this.DGRPropostaConsiglioRegionale.intAllegatoNumero = this.intervento.allegatoDgrPropostaCR![0].intAllegatoNumero;
        if (this.intervento.allegatoDgrPropostaCR![0].intAllegatoData) {
          this.DGRPropostaConsiglioRegionale.intAllegatoData = new Date(this.intervento.allegatoDgrPropostaCR![0].intAllegatoData);
        }
        this.DGRPropostaConsiglioRegionale.fileNameUser = allegato.fileName;
        this.DGRPropostaConsiglioRegionale.base64 = allegato.base64;
        let file: File = this.downloadFile(allegato.fileName, allegato.fileType, allegato.base64);
        this.fileDGRPropostaConsiglioRegionale = file;
        this.fileUrlDGRPropostaConsRegionale = URL.createObjectURL(file);
      })
    }

    if (this.intervento.allegatoDcrApprovazione?.length) {
      this.projectApiService.downloadAllegatoById(this.intervento.allegatoDcrApprovazione[0].idAllegato).subscribe(allegato => {
        this.DCRApprovazione.intAllegatoNumero = this.intervento.allegatoDcrApprovazione![0].intAllegatoNumero;
        if (this.intervento.allegatoDcrApprovazione![0].intAllegatoData) {
          this.DCRApprovazione.intAllegatoData = new Date(this.intervento.allegatoDcrApprovazione![0].intAllegatoData);
        }
        this.DCRApprovazione.fileNameUser = allegato.fileName;
        this.DCRApprovazione.base64 = allegato.base64;
        let file: File = this.downloadFile(allegato.fileName, allegato.fileType, allegato.base64);
        this.fileDCRApprovazione = file;
        this.fileUrlDCRApprovazione = URL.createObjectURL(file);
      })
    }

    if (this.intervento.allegatiDeterminazioniDirigenziali?.length) {
      for (let d of this.intervento.allegatiDeterminazioniDirigenziali) {
        this.projectApiService.downloadAllegatoById(d.idAllegato).subscribe(allegato => {
          let determinazioneDirigenziale: AllegatoRegione = {};
          determinazioneDirigenziale.idAllegato = d.idAllegato;
          determinazioneDirigenziale.intAllegatoNumero = d.intAllegatoNumero;
          determinazioneDirigenziale.base64 = allegato.base64;
          determinazioneDirigenziale.fileNameUser = allegato.fileName;
          determinazioneDirigenziale.fileType = allegato.fileType;
          if (d.intAllegatoData) {
            determinazioneDirigenziale.intAllegatoData = new Date(d.intAllegatoData);
          }
          determinazioneDirigenziale.fileNameUser = allegato.fileName;
          let file: File = this.downloadFile(allegato.fileName, allegato.fileType, allegato.base64);
          this.fileDeterminazioniDirigenziali.push(file);
          this.fileUrlDeterminazioniDirigenziali?.push(URL.createObjectURL(file));
          this.DeterminazioniDirigenziali.push(determinazioneDirigenziale);
        })
      }
    }
  }

  buildTipologiaFinanziamenti() {
    if (this.tipologicheFinanziamento) {
      for (let f of this.tipologicheFinanziamento) {
        let finanziamentoTipo: FinanziamentoTipo = {
          finTipoId: f.tipologiaId,
          finTipoCod: f.tipologiaCod,
          finTipoDesc: f.tipologiaDesc,
          validitaInizio: this.finanziamentoTipoList.find(ft => ft.finTipoId === f.tipologiaId)?.validitaInizio as number,
          validitaFine: this.finanziamentoTipoList.find(ft => ft.finTipoId === f.tipologiaId)?.validitaFine as number
        }
        this.finanziamentoTipologiche.push(finanziamentoTipo);
      }
    }
  }

  checkSuperUser(): boolean {
    return this.userService.isSuperUser();
  }

  async buildInterventoModificaRegione(): Promise<InterventoModificaRegioneDTO> {

    let pareri = [];

    //PARERI
    for (let [index, str] of this.struttureAggiunte.controls.entries()) {
      //solo se è stato allegato un nuovo file faccio partire l'inserimento
      let allegatoParerePPPAggiunto = this.filesPPP[index];
      let intStrId = str.value.intStrId;
      let struttura = this.struttureIntervento.find(s => s.intStrId === intStrId);
      let parerePppStrutturaOld = struttura?.parerePPP;
      let parereHtaStrutturaOld = struttura?.parereHta;
      let allegatoToDeletePPP: AllegatoRegioneModifica | null = null; // inizializza allegatoToDelete come null
      let allegatoToDeleteHta: AllegatoRegioneModifica | null = null; // inizializza allegatoToDelete come null
      let allegatoNew: AllegatoRegioneModifica | null = null; // inizializza allegatoNew come null
      let allegatoHtaNew: AllegatoRegioneModifica | null = null; // inizializza allegatoNew come null

      let checkChangedPPP = this.filesPPPChanged.includes(index);

      if (allegatoParerePPPAggiunto) {

        if (parerePppStrutturaOld) {
          if (checkChangedPPP && parerePppStrutturaOld.allegati[0].idAllegato) {
            try {
              // Aspetta che il download dell'allegato sia completato
              const allegato = await this.projectApiService.downloadAllegatoById(parerePppStrutturaOld.allegati[0].idAllegato).toPromise();

              // Controlla la data dell'allegato
              let year = parerePppStrutturaOld.allegati[0].intAllegatoData!.getFullYear();
              let month = String(parerePppStrutturaOld.allegati[0].intAllegatoData!.getMonth() + 1).padStart(2, '0');
              let day = String(parerePppStrutturaOld.allegati[0].intAllegatoData!.getDate()).padStart(2, '0');

              // Format the date
              let formattedDate = `${year}-${month}-${day}`;

              // Imposta allegatoToDelete
              if (allegato) {
                allegatoToDeletePPP = {
                  idAllegato: parerePppStrutturaOld.allegati[0].idAllegato,
                  intAllegatoNumero: parerePppStrutturaOld.allegati[0].intAllegatoNumero,
                  intAllegatoData: formattedDate,
                  fileNameUser: allegato.fileName,
                  fileType: allegato.fileType,
                  base64: allegato.base64
                };
              }
            } catch (err) {
              // Gestisci l'errore nell'API (se c'è un errore durante il download)
              console.error('Errore nel download dell\'allegato:', err);
              allegatoToDeletePPP = null; // Imposta allegatoToDelete su null in caso di errore
            }
          } else {
            // Se non ci sono i dati necessari (parerePppStrutturaOld o idAllegato), imposta allegatoToDelete su null
            allegatoToDeletePPP = null;
          }
        }


        // Controlla la data dell'allegato
        let data = str.value.dataPPP;

        let year = data.getFullYear();
        let month = String(data.getMonth() + 1).padStart(2, '0');
        let day = String(data.getDate()).padStart(2, '0');

        // Format the date
        let formattedDate = `${year}-${month}-${day}`;

        try {
          // Usa fileToBase64 per ottenere la stringa base64 in modo asincrono
          const base64 = await this.fileToBase64(allegatoParerePPPAggiunto.file);

          allegatoNew = {
            idAllegato: null,
            intAllegatoNumero: str.value.numeroPPP,
            intAllegatoData: formattedDate,
            fileNameUser: allegatoParerePPPAggiunto.fileName,
            fileType: allegatoParerePPPAggiunto.file.type,
            base64: base64.split(',')[1]
          };
        } catch (err) {
          console.error('Errore nella conversione del file in base64:', err);
          allegatoNew = null; // Imposta allegatoNew su null in caso di errore
        }
      }

      if (!allegatoNew) {
        if (parerePppStrutturaOld) {
          if(parerePppStrutturaOld.allegati) {
            if (parerePppStrutturaOld.allegati.length) {
              // Aspetta che il download dell'allegato sia completato
              const allegato = await this.projectApiService.downloadAllegatoById(parerePppStrutturaOld.allegati[0].idAllegato!).toPromise();

              // Controlla la data dell'allegato
              let year = parerePppStrutturaOld.allegati[0].intAllegatoData!.getFullYear();
              let month = String(parerePppStrutturaOld.allegati[0].intAllegatoData!.getMonth() + 1).padStart(2, '0');
              let day = String(parerePppStrutturaOld.allegati[0].intAllegatoData!.getDate()).padStart(2, '0');

              // Format the date
              let formattedDate = `${year}-${month}-${day}`;

              // Imposta allegatoToDelete
              if (allegato) {
                allegatoNew = {
                  idAllegato: parerePppStrutturaOld.allegati[0].idAllegato,
                  intAllegatoNumero: parerePppStrutturaOld.allegati[0].intAllegatoNumero,
                  intAllegatoData: formattedDate,
                  fileNameUser: allegato.fileName,
                  fileType: allegato.fileType,
                  base64: allegato.base64
                };
              }
            }
          }
        }
      }

      let parerePPP = {
        parere: str.value.parerePPP,
        allegatoNew: allegatoNew || null, // imposta allegatoNew come null se non è stato creato
        allegatoToDelete: allegatoToDeletePPP || null// assegna il valore calcolato per allegatoToDelete
      };

      let allegatoParereHtaAggiunto = this.filesHTA[index];
      let checkChangedHTA = this.filesHTAChanged.includes(index);
      if (allegatoParereHtaAggiunto) {
        if (parereHtaStrutturaOld) {
          if (checkChangedHTA && parereHtaStrutturaOld.allegati[0].idAllegato) {
            try {
              // Aspetta che il download dell'allegato sia completato
              const allegato = await this.projectApiService.downloadAllegatoById(parereHtaStrutturaOld.allegati[0].idAllegato).toPromise();

              // Controlla la data dell'allegato
              let year = parereHtaStrutturaOld.allegati[0].intAllegatoData!.getFullYear();
              let month = String(parereHtaStrutturaOld.allegati[0].intAllegatoData!.getMonth() + 1).padStart(2, '0');
              let day = String(parereHtaStrutturaOld.allegati[0].intAllegatoData!.getDate()).padStart(2, '0');

              // Format the date
              let formattedDate = `${year}-${month}-${day}`;

              // Imposta allegatoToDelete
              if (allegato) {
                allegatoToDeleteHta = {
                  idAllegato: parereHtaStrutturaOld.allegati[0].idAllegato,
                  intAllegatoNumero: parereHtaStrutturaOld.allegati[0].intAllegatoNumero,
                  intAllegatoData: formattedDate,
                  fileNameUser: allegato.fileName,
                  fileType: allegato.fileType,
                  base64: allegato.base64
                };
              }
            } catch (err) {
              // Gestisci l'errore nell'API (se c'è un errore durante il download)
              console.error('Errore nel download dell\'allegato:', err);
              allegatoToDeletePPP = null; // Imposta allegatoToDelete su null in caso di errore
            }
          } else {
            // Se non ci sono i dati necessari (parerePppStrutturaOld o idAllegato), imposta allegatoToDelete su null
            allegatoToDeleteHta = null;
          }
        }

        // Controlla la data dell'allegato
        let data = str.value.dataHTA;

        let year = data.getFullYear();
        let month = String(data.getMonth() + 1).padStart(2, '0');
        let day = String(data.getDate()).padStart(2, '0');

        // Format the date
        let formattedDate = `${year}-${month}-${day}`;

        try {
          // Usa fileToBase64 per ottenere la stringa base64 in modo asincrono
          const base64 = await this.fileToBase64(allegatoParereHtaAggiunto.file);

          allegatoHtaNew = {
            idAllegato: null,
            intAllegatoNumero: str.value.numeroHta,
            intAllegatoData: formattedDate,
            fileNameUser: allegatoParereHtaAggiunto.fileName,
            fileType: allegatoParereHtaAggiunto.file.type,
            base64: base64.split(',')[1]
          };
        } catch (err) {
          console.error('Errore nella conversione del file in base64:', err);
          allegatoHtaNew = null; // Imposta allegatoNew su null in caso di errore
        }

      }

      if (!allegatoHtaNew) {
        if (parereHtaStrutturaOld) {
          if(parereHtaStrutturaOld.allegati) {
            if (parereHtaStrutturaOld.allegati.length) {
              // Aspetta che il download dell'allegato sia completato
              const allegato = await this.projectApiService.downloadAllegatoById(parereHtaStrutturaOld.allegati[0].idAllegato!).toPromise();

              // Controlla la data dell'allegato
              let year = parereHtaStrutturaOld.allegati[0].intAllegatoData!.getFullYear();
              let month = String(parereHtaStrutturaOld.allegati[0].intAllegatoData!.getMonth() + 1).padStart(2, '0');
              let day = String(parereHtaStrutturaOld.allegati[0].intAllegatoData!.getDate()).padStart(2, '0');

              // Format the date
              let formattedDate = `${year}-${month}-${day}`;

              // Imposta allegatoToDelete
              if (allegato) {
                allegatoNew = {
                  idAllegato: parereHtaStrutturaOld.allegati[0].idAllegato,
                  intAllegatoNumero: parereHtaStrutturaOld.allegati[0].intAllegatoNumero,
                  intAllegatoData: formattedDate,
                  fileNameUser: allegato.fileName,
                  fileType: allegato.fileType,
                  base64: allegato.base64
                };
              }
            }
          }
        }
      }


      // Costruisci l'oggetto ParereHta
      let parereHta = {
        parere: str.value.parereHTA,
        allegatoNew: allegatoHtaNew || null,
        allegatoToDelete: allegatoToDeleteHta
      };


      let parere: ParereRegione = {
        intStrId: intStrId,
        parerePpp: parerePPP,
        parereHta: parereHta
      }

      pareri.push(parere);
    }


    //FLAG FINANZIABILE
    let finanziabile = this.insertionForm.get('finanziabile')?.value;
    if (finanziabile === undefined) {
      finanziabile = false;
    }

    //PREVISIONI DI SPESA
    let previsioniDiSpesa = [];
    for (let previsioneDiSpesaForm of this.previsioniDiSpesa.controls) {
      let previsione = {
        anno: previsioneDiSpesaForm.get('annoPrevisione')?.value,
        importo: previsioneDiSpesaForm.get('importoPrevisione')?.value
      }

      previsioniDiSpesa.push(previsione);
    }

    //PIANI FINANZIARI
    let pianiFinanziari = [];

    for (let piano of this.pianiFinanziariPrincipaliTabellaRows) {
      //se la lunghezza è maggiore di 1 trattasi di Stato e Regione
      if (piano.finanziamentoImportoTipo.length > 1) {
        let importoRegione = 0;
        let importoStato = 0;

        if (piano.finImporto && piano.finPercRegione) {
          importoRegione = piano.finPercRegione;
        }

        if (piano.finImporto && piano.finPercStato) {
          importoStato = piano.finPercStato;
        }

        let importiTipoRegione = [];

        for (let f of piano.finanziamentoImportoTipo) {
          let finanziamentoTipoImportoRegione: FinanziamentoImportoTipo = {
            finanziamentoImportoTipoId: f.finanziamentoImportoTipoId,
            finanziamentoImportoTipoCod: f.finanziamentoImportoTipoCod,
            finanziamentoImporto: f.finanziamentoImportoTipoCod == IMP_STATO ? importoStato : importoRegione
          }

          importiTipoRegione.push(finanziamentoTipoImportoRegione);
        }

        //compongo il piano finanziario
        let pianoFinanziarioRegione: PianoFinanziarioRegione = {
          finanziamentoId: piano.finTipoId ?? null,
          tipologiaDettaglioId: piano.finTipoDetId ?? 0,
          importoTotale: piano.finImporto ?? 0,
          isPrincipale: piano.isPrincipale ?? false,
          finanziamentoImportoTipo: importiTipoRegione
        }

        pianiFinanziari.push(pianoFinanziarioRegione);

      } else {

        let importiTipoRegione = [];

        for (let f of piano.finanziamentoImportoTipo) {
          let finanziamentoTipoImportoRegione: FinanziamentoImportoTipo;

          if (piano.finImporto) {
            finanziamentoTipoImportoRegione = {
              finanziamentoImportoTipoId: f.finanziamentoImportoTipoId,
              finanziamentoImportoTipoCod: f.finanziamentoImportoTipoCod,
              finanziamentoImporto: piano.finImporto
            }

            importiTipoRegione.push(finanziamentoTipoImportoRegione);
          }

        }

        //compongo il piano finanziario
        let pianoFinanziarioRegione: PianoFinanziarioRegione = {
          finanziamentoId: piano.finTipoId ?? null,
          tipologiaDettaglioId: piano.finTipoDetId ?? 0,
          importoTotale: piano.finImporto ?? 0,
          isPrincipale: piano.isPrincipale ?? false,
          finanziamentoImportoTipo: importiTipoRegione
        }

        pianiFinanziari.push(pianoFinanziarioRegione);
      }

    }

    for (let piano of this.pianiFinanziariNonPrincipaliTabellaRows) {
      //se la lunghezza è maggiore di 1 trattasi di Stato e Regione
      if (piano.finanziamentoImportoTipo.length > 1) {
        let importoRegione = 0;
        let importoStato = 0;

        if (piano.finImporto && piano.finPercRegione) {
          importoRegione = piano.finPercRegione;
        }

        if (piano.finImporto && piano.finPercStato) {
          importoStato = piano.finPercStato;
        }

        let importiTipoRegione = [];

        for (let f of piano.finanziamentoImportoTipo) {
          let finanziamentoTipoImportoRegione: FinanziamentoImportoTipo = {
            finanziamentoImportoTipoId: f.finanziamentoImportoTipoId,
            finanziamentoImportoTipoCod: f.finanziamentoImportoTipoCod,
            finanziamentoImporto: f.finanziamentoImportoTipoCod == IMP_STATO ? importoStato : importoRegione
          }

          importiTipoRegione.push(finanziamentoTipoImportoRegione);
        }

        //compongo il piano finanziario
        let pianoFinanziarioRegione: PianoFinanziarioRegione = {
          finanziamentoId: piano.finTipoId ?? null,
          tipologiaDettaglioId: piano.finTipoDetId ?? 0,
          importoTotale: piano.finImporto ?? 0,
          isPrincipale: piano.isPrincipale ?? false,
          finanziamentoImportoTipo: importiTipoRegione
        }

        pianiFinanziari.push(pianoFinanziarioRegione);

      } else {

        let importiTipoRegione = [];

        for (let f of piano.finanziamentoImportoTipo) {
          let finanziamentoTipoImportoRegione: FinanziamentoImportoTipo;

          if (piano.finImporto) {
            finanziamentoTipoImportoRegione = {
              finanziamentoImportoTipoId: f.finanziamentoImportoTipoId,
              finanziamentoImportoTipoCod: f.finanziamentoImportoTipoCod,
              finanziamentoImporto: piano.finImporto
            }

            importiTipoRegione.push(finanziamentoTipoImportoRegione);
          }

        }

        //compongo il piano finanziario
        let pianoFinanziarioRegione: PianoFinanziarioRegione = {
          finanziamentoId: piano.finTipoId ?? null,
          tipologiaDettaglioId: piano.finTipoDetId ?? 0,
          importoTotale: piano.finImporto ?? 0,
          isPrincipale: piano.isPrincipale ?? false,
          finanziamentoImportoTipo: importiTipoRegione
        }

        pianiFinanziari.push(pianoFinanziarioRegione);
      }

    }

    //ALLEGATO DGR APPROVAZIONE

    let allegatoDgrApprovazione = this.fileDGRApprovazione;
    let allegatoToDeleteDGR: AllegatoRegioneModifica | null = null; // inizializza allegatoToDelete come null
    let allegatoNewDGR: AllegatoRegioneModifica | null = null; // inizializza allegatoNew come null

    if (allegatoDgrApprovazione && this.dgrApprovazioneChanged) {

      if (this.intervento.allegatoDgrApprovazione?.length) {
        if (this.intervento.allegatoDgrApprovazione[0].idAllegato) {
          try {
            // Aspetta che il download dell'allegato sia completato
            const allegato = await this.projectApiService.downloadAllegatoById(this.intervento.allegatoDgrApprovazione[0].idAllegato).toPromise();

            // Controlla la data dell'allegato
            let data = new Date(this.intervento.allegatoDgrApprovazione[0].intAllegatoData);

            let year = data.getFullYear();
            let month = String(data.getMonth() + 1).padStart(2, '0');
            let day = String(data.getDate()).padStart(2, '0');

            // Format the date
            let formattedDate = `${year}-${month}-${day}`;

            // Imposta allegatoToDelete
            if (allegato) {
              allegatoToDeleteDGR = {
                idAllegato: this.intervento.allegatoDgrApprovazione[0].idAllegato,
                intAllegatoNumero: this.intervento.allegatoDgrApprovazione[0].intAllegatoNumero,
                intAllegatoData: formattedDate,
                fileNameUser: allegato.fileName,
                fileType: allegato.fileType,
                base64: allegato.base64
              };
            }
          } catch (err) {
            // Gestisci l'errore nell'API (se c'è un errore durante il download)
            console.error('Errore nel download dell\'allegato:', err);
            allegatoToDeleteDGR = null; // Imposta allegatoToDelete su null in caso di errore
          }
        } else {
          // Se non ci sono i dati necessari (parerePppStrutturaOld o idAllegato), imposta allegatoToDelete su null
          allegatoToDeleteDGR = null;
        }
      } else {
        allegatoToDeleteDGR = null;
      }

      // Controlla la data dell'allegato
      let data = this.DGRApprovazione.intAllegatoData!;

      let year = data.getFullYear();
      let month = String(data.getMonth() + 1).padStart(2, '0');
      let day = String(data.getDate()).padStart(2, '0');

      // Format the date
      let formattedDate = `${year}-${month}-${day}`;


      allegatoNewDGR = {
        idAllegato: null,
        intAllegatoNumero: this.DGRApprovazione.intAllegatoNumero ?? null,
        intAllegatoData: formattedDate,
        fileNameUser: this.DGRApprovazione.fileNameUser ?? null,
        fileType: this.DGRApprovazione.fileType ?? null,
        base64: this.DGRApprovazione.base64 ?? null
      };
    }

    //controllo se allegato è null vedo se ne esiste uno preesistente e devo mandare comunque quello preesistente tra i new
    if (!allegatoNewDGR) {
      try {
        if (this.intervento.allegatoDgrApprovazione) {
          // Aspetta che il download dell'allegato sia completato
          const allegato = await this.projectApiService.downloadAllegatoById(this.intervento.allegatoDgrApprovazione[0].idAllegato).toPromise();

          // Controlla la data dell'allegato
          let data = new Date(this.intervento.allegatoDgrApprovazione[0].intAllegatoData);

          let year = data.getFullYear();
          let month = String(data.getMonth() + 1).padStart(2, '0');
          let day = String(data.getDate()).padStart(2, '0');

          // Format the date
          let formattedDate = `${year}-${month}-${day}`;

          // Imposta allegatoNew
          if (allegato) {
            allegatoNewDGR = {
              idAllegato: this.intervento.allegatoDgrApprovazione[0].idAllegato,
              intAllegatoNumero: this.intervento.allegatoDgrApprovazione[0].intAllegatoNumero,
              intAllegatoData: formattedDate,
              fileNameUser: allegato.fileName,
              fileType: allegato.fileType,
              base64: allegato.base64
            };
          }
        }
      } catch (err) {
        // Gestisci l'errore nell'API (se c'è un errore durante il download)
        console.error('Errore nel download dell\'allegato:', err);
        allegatoNewDGR = null; // Imposta allegatoToDelete su null in caso di errore
      }
    }

    //ALLEGATO DCR
    let allegatoPropostaConsRegionale = this.DGRPropostaConsiglioRegionale;
    let allegatoToDeleteDGRPropConsRegionale: AllegatoRegioneModifica | null = null; // inizializza allegatoToDelete come null
    let allegatoNewDGRPropConsRegionale: AllegatoRegioneModifica | null = null; // inizializza allegatoNew come null

    if (allegatoPropostaConsRegionale && this.dgrPropConsRegionaleChanged) {

      if (this.intervento.allegatoDgrPropostaCR?.length) {
        if (this.intervento.allegatoDgrPropostaCR[0].idAllegato) {
          try {
            // Aspetta che il download dell'allegato sia completato
            const allegato = await this.projectApiService.downloadAllegatoById(this.intervento.allegatoDgrPropostaCR[0].idAllegato).toPromise();

            // Controlla la data dell'allegato
            let data = new Date(this.intervento.allegatoDgrPropostaCR[0].intAllegatoData);

            let year = data.getFullYear();
            let month = String(data.getMonth() + 1).padStart(2, '0');
            let day = String(data.getDate()).padStart(2, '0');

            // Format the date
            let formattedDate = `${year}-${month}-${day}`;

            // Imposta allegatoToDelete
            if (allegato) {
              allegatoToDeleteDGRPropConsRegionale = {
                idAllegato: this.intervento.allegatoDgrPropostaCR[0].idAllegato,
                intAllegatoNumero: this.intervento.allegatoDgrPropostaCR[0].intAllegatoNumero,
                intAllegatoData: formattedDate,
                fileNameUser: allegato.fileName,
                fileType: allegato.fileType,
                base64: allegato.base64
              };
            }
          } catch (err) {
            // Gestisci l'errore nell'API (se c'è un errore durante il download)
            console.error('Errore nel download dell\'allegato:', err);
            allegatoToDeleteDGRPropConsRegionale = null; // Imposta allegatoToDelete su null in caso di errore
          }
        } else {
          // Se non ci sono i dati necessari (parerePppStrutturaOld o idAllegato), imposta allegatoToDelete su null
          allegatoToDeleteDGRPropConsRegionale = null;
        }
      } else {
        allegatoToDeleteDGRPropConsRegionale = null;
      }


      // Controlla la data dell'allegato
      let data = this.DGRPropostaConsiglioRegionale.intAllegatoData!;

      let year = data.getFullYear();
      let month = String(data.getMonth() + 1).padStart(2, '0');
      let day = String(data.getDate()).padStart(2, '0');

      // Format the date
      let formattedDate = `${year}-${month}-${day}`;

      allegatoNewDGRPropConsRegionale = {
        idAllegato: null,
        intAllegatoNumero: this.DGRPropostaConsiglioRegionale.intAllegatoNumero ?? null,
        intAllegatoData: formattedDate,
        fileNameUser: this.DGRPropostaConsiglioRegionale.fileNameUser ?? null,
        fileType: this.DGRPropostaConsiglioRegionale.fileType ?? null,
        base64: this.DGRPropostaConsiglioRegionale.base64 ?? null
      };

    }

    //controllo se allegato è null vedo se ne esiste uno preesistente e devo mandare comunque quello preesistente tra i new
    if (!allegatoNewDGRPropConsRegionale) {
      try {
        if (this.intervento.allegatoDgrPropostaCR) {
          // Aspetta che il download dell'allegato sia completato
          const allegato = await this.projectApiService.downloadAllegatoById(this.intervento.allegatoDgrPropostaCR[0].idAllegato).toPromise();

          // Controlla la data dell'allegato
          let data = new Date(this.intervento.allegatoDgrPropostaCR[0].intAllegatoData);

          let year = data.getFullYear();
          let month = String(data.getMonth() + 1).padStart(2, '0');
          let day = String(data.getDate()).padStart(2, '0');

          // Format the date
          let formattedDate = `${year}-${month}-${day}`;

          // Imposta allegatoNew
          if (allegato) {
            allegatoNewDGR = {
              idAllegato: this.intervento.allegatoDgrPropostaCR[0].idAllegato,
              intAllegatoNumero: this.intervento.allegatoDgrPropostaCR[0].intAllegatoNumero,
              intAllegatoData: formattedDate,
              fileNameUser: allegato.fileName,
              fileType: allegato.fileType,
              base64: allegato.base64
            };
          }
        }
      } catch (err) {
        // Gestisci l'errore nell'API (se c'è un errore durante il download)
        console.error('Errore nel download dell\'allegato:', err);
        allegatoNewDGRPropConsRegionale = null; // Imposta allegatoToDelete su null in caso di errore
      }
    }

    //ALLEGATO DCR
    let allegatoDcrApprovazione = this.fileDCRApprovazione;
    let allegatoToDeleteDCR: AllegatoRegioneModifica | null = null; // inizializza allegatoToDelete come null
    let allegatoNewDCR: AllegatoRegioneModifica | null = null; // inizializza allegatoNew come null

    if (allegatoDcrApprovazione && this.dcrApprovazioneChanged) {

      if (this.intervento.allegatoDcrApprovazione?.length) {
        if (this.intervento.allegatoDcrApprovazione[0].idAllegato) {
          try {
            // Aspetta che il download dell'allegato sia completato
            const allegato = await this.projectApiService.downloadAllegatoById(this.intervento.allegatoDcrApprovazione[0].idAllegato).toPromise();

            // Controlla la data dell'allegato
            let data = new Date(this.intervento.allegatoDcrApprovazione[0].intAllegatoData);

            let year = data.getFullYear();
            let month = String(data.getMonth() + 1).padStart(2, '0');
            let day = String(data.getDate()).padStart(2, '0');

            // Format the date
            let formattedDate = `${year}-${month}-${day}`;

            // Imposta allegatoToDelete
            if (allegato) {
              allegatoToDeleteDCR = {
                idAllegato: this.intervento.allegatoDcrApprovazione[0].idAllegato,
                intAllegatoNumero: this.intervento.allegatoDcrApprovazione[0].intAllegatoNumero,
                intAllegatoData: formattedDate,
                fileNameUser: allegato.fileName,
                fileType: allegato.fileType,
                base64: allegato.base64
              };
            }
          } catch (err) {
            // Gestisci l'errore nell'API (se c'è un errore durante il download)
            console.error('Errore nel download dell\'allegato:', err);
            allegatoToDeleteDCR = null; // Imposta allegatoToDelete su null in caso di errore
          }
        } else {
          // Se non ci sono i dati necessari (parerePppStrutturaOld o idAllegato), imposta allegatoToDelete su null
          allegatoToDeleteDCR = null;
        }
      } else {
        allegatoToDeleteDCR = null;
      }

      // Controlla la data dell'allegato
      let data = this.DCRApprovazione.intAllegatoData!;

      let year = data.getFullYear();
      let month = String(data.getMonth() + 1).padStart(2, '0');
      let day = String(data.getDate()).padStart(2, '0');

      // Format the date
      let formattedDate = `${year}-${month}-${day}`;

      allegatoNewDCR = {
        idAllegato: null,
        intAllegatoNumero: this.DCRApprovazione.intAllegatoNumero ?? null,
        intAllegatoData: formattedDate,
        fileNameUser: this.DCRApprovazione.fileNameUser ?? null,
        fileType: this.DCRApprovazione.fileType ?? null,
        base64: this.DCRApprovazione.base64 ?? null
      };
    }

    if (!allegatoNewDCR) {
      try {
        if (this.intervento.allegatoDcrApprovazione) {
          // Aspetta che il download dell'allegato sia completato
          const allegato = await this.projectApiService.downloadAllegatoById(this.intervento.allegatoDcrApprovazione[0].idAllegato).toPromise();

          // Controlla la data dell'allegato
          let data = new Date(this.intervento.allegatoDcrApprovazione[0].intAllegatoData);

          let year = data.getFullYear();
          let month = String(data.getMonth() + 1).padStart(2, '0');
          let day = String(data.getDate()).padStart(2, '0');

          // Format the date
          let formattedDate = `${year}-${month}-${day}`;

          // Imposta allegatoNew
          if (allegato) {
            allegatoNewDGR = {
              idAllegato: this.intervento.allegatoDcrApprovazione[0].idAllegato,
              intAllegatoNumero: this.intervento.allegatoDcrApprovazione[0].intAllegatoNumero,
              intAllegatoData: formattedDate,
              fileNameUser: allegato.fileName,
              fileType: allegato.fileType,
              base64: allegato.base64
            };
          }
        }
      } catch (err) {
        // Gestisci l'errore nell'API (se c'è un errore durante il download)
        console.error('Errore nel download dell\'allegato:', err);
        allegatoNewDGRPropConsRegionale = null; // Imposta allegatoToDelete su null in caso di errore
      }
    }

    //DETERMINAZIONI DIRIGENZIALI
    //costruisco quelle da eliminare
    let determinazioniDirigenzialiToDelete: AllegatoRegioneModifica[] = [];
    for (let determinazione of this.DeterminazioniDirigenzialiDeleted) {

      let data = determinazione.intAllegatoData!;

      let year = data.getFullYear();
      let month = String(data.getMonth() + 1).padStart(2, '0');
      let day = String(data.getDate()).padStart(2, '0');

      // Format the date
      let formattedDate = `${year}-${month}-${day}`;

      let allegatoRegioneModifica: AllegatoRegioneModifica = {
        idAllegato: determinazione.idAllegato ?? null,
        intAllegatoNumero: determinazione.intAllegatoNumero ?? null,
        intAllegatoData: formattedDate,
        fileNameUser: determinazione.fileNameUser ?? null,
        fileType: determinazione.fileType ?? null,
        base64: determinazione.base64 ?? null
      }

      determinazioniDirigenzialiToDelete.push(allegatoRegioneModifica);
    }

    //costruisco quelle da salvare
    let determinazioniDirigenzialiNew: AllegatoRegioneModifica[] = [];
    for (let determinazione of this.DeterminazioniDirigenzialiAdded) {

      let formattedDate;

      if (determinazione.intAllegatoData) {
        let data = determinazione.intAllegatoData!;

        let year = data.getFullYear();
        let month = String(data.getMonth() + 1).padStart(2, '0');
        let day = String(data.getDate()).padStart(2, '0');

        // Format the date
        formattedDate = `${year}-${month}-${day}`;
      }

      let allegatoRegioneModifica: AllegatoRegioneModifica = {
        idAllegato: determinazione.idAllegato ?? null,
        intAllegatoNumero: determinazione.intAllegatoNumero ?? null,
        intAllegatoData: formattedDate ?? null,
        fileNameUser: determinazione.fileNameUser ?? null,
        fileType: determinazione.fileType ?? null,
        base64: determinazione.base64 ?? null
      }

      determinazioniDirigenzialiNew.push(allegatoRegioneModifica);
    }

    //nelle determinazioni dirigenziali da inviare devo comunque includere quelle già presenti
    if (this.DeterminazioniDirigenziali.length) {
      for (let det of this.DeterminazioniDirigenziali) {
        let formattedDate;

        if (det.intAllegatoData) {
          let data = det.intAllegatoData!;

          let year = data.getFullYear();
          let month = String(data.getMonth() + 1).padStart(2, '0');
          let day = String(data.getDate()).padStart(2, '0');

          // Format the date
          formattedDate = `${year}-${month}-${day}`;
        }

        let allegatoRegioneModifica: AllegatoRegioneModifica = {
          idAllegato: det.idAllegato ?? null,
          intAllegatoNumero: det.intAllegatoNumero ?? null,
          intAllegatoData: formattedDate ?? null,
          fileNameUser: det.fileNameUser ?? null,
          fileType: det.fileType ?? null,
          base64: det.base64 ?? null
        }

        determinazioniDirigenzialiNew.push(allegatoRegioneModifica);
      }
    }

    let interventoModificaRegioneDTO: InterventoModificaRegioneDTO = {
      pareri: pareri,
      finanziabile: finanziabile,
      previsioniDiSpesa: previsioniDiSpesa,
      pianiFinanziari: pianiFinanziari,
      dgrApprovazioneNew: allegatoNewDGR,
      dgrApprovazioneToDelete: allegatoToDeleteDGR,
      dgrConsiglioRegionaleNew: allegatoNewDGRPropConsRegionale,
      dgrConsiglioRegionaleToDelete: allegatoToDeleteDGRPropConsRegionale,
      dcrApprovazioneNew: allegatoNewDCR,
      dcrApprovazioneToDelete: allegatoToDeleteDCR,
      determinazioniDirigenzialiNew: determinazioniDirigenzialiNew,
      determinazioniDirigenzialiToDelete: determinazioniDirigenzialiToDelete
    }

    return interventoModificaRegioneDTO;
  }



  checkParerePPPEnabled(index: number) {
    const formGroup = this.struttureAggiunte.at(index);
    let flag = formGroup?.get('parerePPP')?.value;

    if(flag) {
      formGroup?.get('numeroPPP')!.enable();
      formGroup?.get('dataPPP')!.enable();
    } else {
      formGroup?.get('numeroPPP')!.disable();
      formGroup?.get('dataPPP')!.disable();
    }
  }

  checkParereHtaEnabled(index: number) {
    const formGroup = this.struttureAggiunte.at(index);
    let flag = formGroup?.get('parereHTA')?.value;

    if(flag) {
      formGroup?.get('numeroHTA')!.enable();
      formGroup?.get('dataHTA')!.enable();
    } else {
      formGroup?.get('numeroHTA')!.disable();
      formGroup?.get('dataHTA')!.disable();
    }
  }

  onYearChange(event: any): void {
    let value = event.target.value;

    // Rimuove tutti i caratteri che non sono numeri
    value = value.replace(/[^0-9]/g, '');

    // Impedisce di scrivere più di 4 caratteri
    if (value.length > 4) {
      value = value.slice(0, 4); // Limita a 4 caratteri
    }

    // Imposta il valore modificato nell'input
    event.target.value = value;
  }


  async salvaModificaRegione() {
    this.loading = true;
    let interventoModificaRegione = this.buildInterventoModificaRegione();
    let interventoModificaRegioneResolved = await interventoModificaRegione;
    this.projectApiService.editInterventoRegione(interventoModificaRegioneResolved, this.intervento.intId)
      .subscribe(
        save => {
          this.salvataggioEffettuato = true;
          this.loading = false;
        },
        err => {
          this.salvataggioEffettuato = false;
          this.loading = false;
        }
      );
  }

  checkPrevisioneSpesa(): boolean {
    let costoComplessivo = this.intervento.intImporto;
    let sommaImporti = 0;

    for (let previsioneDiSpesaForm of this.previsioniDiSpesa.controls) {
      let anno = previsioneDiSpesaForm.get('annoPrevisione')?.value;
      let importo = previsioneDiSpesaForm.get('importoPrevisione')?.value;

      if (anno && importo) {
        sommaImporti += importo;
      }
    }

    return sommaImporti === costoComplessivo;
  }

  checkPianiFinanziari(): boolean {
    let hasPrincipale = false;
    if(this.pianiFinanziariPrincipaliTabellaRows.length) {
      for (let piano of this.pianiFinanziariPrincipaliTabellaRows) {
        if(piano.isPrincipale) {
          hasPrincipale = true;
        }
      }
    }
    return hasPrincipale;
  }

  getObiettiviList(): string {
    let listaObiettivi = '';
    let first = true;
    for (let obiettivoIntervento of this.intervento.obiettivi) {
      for (let obiettivo of this.obiettivoList) {
        if (obiettivo.intObiettivoId === obiettivoIntervento) {
          if (!first) {
            listaObiettivi += '; ';
          }
          listaObiettivi += obiettivo.intObiettivoDesc;
          first = false;
        }
      }
    }
    return listaObiettivi;
  }

  getFinalitaList(): string {
    let listaFinalita = '';
    let first = true;
    for (let finalitaIntervento of this.intervento.finalita) {
      for (let finalita of this.finalitaList) {
        if (finalita.intFinalitaId === finalitaIntervento) {
          if (!first) {
            listaFinalita += '; ';
          }
          listaFinalita += finalita.intfinalitaDesc;
          first = false;
        }
      }
    }
    return listaFinalita;
  }

  getTipologiaList(): string {
    let listaTipologie = '';
    let first = true;
    for (let tipologiaIntervento of this.intervento.tipi) {
      for (let tipologia of this.interventoTipoList) {
        if (tipologia.intTipoId === tipologiaIntervento) {
          if (!first) {
            listaTipologie += '; ';
          }
          listaTipologie += tipologia.intTipoDesc;
          first = false;
        }
      }
    }
    return listaTipologie;
  }

  getDescrizioneAttrezzature(): string {
    let listaTipologie = '';
    let first = true;
    for (let descrizioneAttrezzatura of this.intervento.descrizioniAttrezzature) {
      for (let attrezzatura of this.attrezzaturaList) {
        if (attrezzatura.intTipoDetId === descrizioneAttrezzatura) {
          if (!first) {
            listaTipologie += '; ';
          }
          listaTipologie += attrezzatura.intTipoDetDesc;
          first = false;
        }
      }
    }
    return listaTipologie;
  }


  getCategorieIntervento(): string {
    let listaCategorie = '';
    let first = true;
    for (let categoriaIntervento of this.intervento.categorie) {
      for (let categoria of this.categoriaList) {
        if (categoria.intCategoriaId === categoriaIntervento) {
          if (!first) {
            listaCategorie += '; ';
          }
          listaCategorie += categoria.intCategoriaDesc;
          first = false;
        }
      }
    }
    return listaCategorie;
  }

  getTipologiaContrattuale(): string {
    let listaTipologie = '';
    let first = true;
    for (let tipologiaIntervento of this.intervento.contrattiTipo) {
      for (let tipologiaContrattuale of this.contrattoTipoList) {
        if (tipologiaContrattuale.intContrattoTipoId === tipologiaIntervento) {
          if (!first) {
            listaTipologie += '; ';
          }
          listaTipologie += tipologiaContrattuale.intContrattoTipoDesc;
          first = false;
        }
      }
    }
    return listaTipologie;
  }

  getAppaltiTipologie(): string {
    let listaAppalti = '';
    let first = true;
    for (let tipologiaAppalto of this.intervento.appaltiTipo) {
      for (let appaltoTipologia of this.appaltoTipoList) {
        if (appaltoTipologia.intAppaltoTipoId === tipologiaAppalto) {
          if (!first) {
            listaAppalti += '; ';
          }
          listaAppalti += appaltoTipologia.intAppaltoTipoDesc;
          first = false;
        }
      }
    }
    return listaAppalti;
  }

  getStatoProgettuale(): string {
    let listaStati = '';
    let first = true;
    for (let stato of this.intervento.statiProgettuali) {
      for (let statoProgettuale of this.statoProgList) {
        if (statoProgettuale.intStatoProgId === stato) {
          if (!first) {
            listaStati += '; ';
          }
          listaStati += statoProgettuale.intStatoProgDesc;
          first = false;
        }
      }
    }
    return listaStati;
  }

  approva() {
    const dialogRef = this.dialog.open(ModaleAzioneComponent, {
      width: '600px',
      disableClose: true,
      data: {
        titolo: 'Approva Intervento',
        messaggio: 'Attenzione! Dopo l\'approvazione l\'intervento sarà consultabile in sola lettura.',
        operazione: 'APPROVAINTERVENTO',
        intId: this.intervento.intId,
        risultato: 'L\'intervento è stato correttamente approvato ed è stata inviata una notifica all\'ASR di riferimento.'
      }
    });
  }

  // export interface AllegatoRegione extends BaseModel {
  //   idAllegato?: number;
  //   intAllegatoNumero?: number;
  //   intAllegatoData?: string;
  //   fileNameUser?: string;
  //   fileType?: string;
  //   base64?: string;
  // }
  getTotaleStrutture(){
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

  stampaIntervento(){
    if(this.intervento && this.intervento.intId){
      this.loading=true;
      if(this.isStatoFinanziabile(this.intervento.stato)) {
        this.projectApiService.downloadModuloByRIntModuloIdAndInterventoId(this.intervento.rIntModuloId!, this.intervento.intId).subscribe(modulo => {
          let file = this.downloadFile(modulo.fileName, modulo.fileType, modulo.base64);
          if(file){
            const filename = modulo.fileName;
            const blob = new Blob([file], { type: modulo.fileType });
            const link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = filename ? filename.replace(/"/g, '') : 'file.pdf';
            link.click();
          }
            this.loading = false;
          }, err=>{
            this.loading=false;
          });
      } else {
        this.projectApiService.stampaIntervento(this.intervento.intId).subscribe(response => {
          if (response.body) {
            const contentDisposition = response.headers.get('Content-Disposition');

            const filename = contentDisposition
              ? contentDisposition.split(';').find(n => n.includes('filename'))?.split('=')[1].trim()
              : 'default-filename.pdf';

            const blob = new Blob([response.body], { type: response.body.type });
            const link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = filename ? filename.replace(/"/g, '') : 'file.pdf';
            link.click();
          }
          this.loading = false;
        }, err => {
          this.loading = false;
        });
      }
      
    }
  }

  isStatoNotInseritoAndNotProposto(statoIntervento:number){
    const stato = this.interventoStatoList.find((stato)=> stato.intStatoId === statoIntervento );
    return (stato?.intStatoCod !=='INS' && stato?.intStatoCod !== 'PROP' );
  }

  isStatoInserito(statoIntervento:number){
    const stato = this.interventoStatoList.find((stato)=> stato.intStatoId === statoIntervento );
    return stato?.intStatoCod ==='INS';
  }

  isStatoFinanziabile(statoIntervento:number){
    const stato = this.interventoStatoList.find((stato)=> stato.intStatoId === statoIntervento );
    return stato?.intStatoCod ==='FIN';
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

  isRegione():boolean{
    return this.userService.isSuperUser();
  }

  indietro(){
    const indietroUrl = this.ariannaService.getIndietroUrl();
    this.router.navigate([ indietroUrl ], { queryParams: { reload: 'last' } });
  }

  checkStruttura(idStr: number): boolean {
    const struttura = this.strutturaList.find(x => x.strId === idStr);
    return struttura ? struttura.strPgmeas : false;
  }

  calcolaTotalePrevisioneSpesa() {
    let previsioniDiSpesaTotale = 0;
    if(this.previsioniDiSpesa.length) {
      for (let previsioneDiSpesaForm of this.previsioniDiSpesa.controls) {
        previsioniDiSpesaTotale += previsioneDiSpesaForm.get('importoPrevisione')?.value;
      }
    }

    return previsioniDiSpesaTotale;
  }
}
