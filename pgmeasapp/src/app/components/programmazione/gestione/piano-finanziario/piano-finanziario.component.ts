/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges} from '@angular/core';
import {Ente, FinanziamentoTipo, FinanziamentoTipoDet, Intervento, InterventoStruttura, Struttura} from '@pgmeas-library/model';
import {MatDialog} from '@angular/material/dialog';
import {ModaleAggiuntaFinanziamentoComponent} from './modale-aggiunta-finanziamento/modale-aggiunta-finanziamento.component';
import {AbstractControl, FormArray, FormBuilder, FormGroup, ɵTypedOrUntyped} from '@angular/forms';
import {combineLatest, map, Subject, switchMap, takeUntil} from 'rxjs';
import { PianoFinanziario } from '@pgmeas-library/model/src/piano-finanziario';
import { FinanziamentoImporto, TipologicaFinanziamento } from '@pgmeas-library/model/src/tipologicheFinanziamento';
import { RegistryApiService } from 'src/app/services/registry-api.service';
import { ActivatedRoute } from '@angular/router';

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
  selector: 'app-piano-finanziario',
  templateUrl: './piano-finanziario.component.html',
  styleUrls: ['./piano-finanziario.component.scss']
})
export class PianoFinanziarioComponent implements OnChanges, OnInit, OnDestroy {
  @Input() data: any;
  @Input() denominazioneStruttura: Struttura[] = [];
  @Input() intervento: Intervento;
  @Input() interventoStruttura: InterventoStruttura[] = [];
  @Input() finanziamenti: any[] = [];
  @Input() enti: Ente[] = [];
  @Input() canEdit = true;
  @Output() formData: EventEmitter<any> = new EventEmitter<any>();
  @Output() formValidity: EventEmitter<boolean> = new EventEmitter<boolean>();
  displayedColumns: string[] = ['tipo', 'dettaglio', 'importo', 'quotaRegione', 'quotaStato', 'quotaAltro'];
  form: FormGroup;
  protected readonly FormGroup = FormGroup;
  private destroy$: Subject<boolean> = new Subject<boolean>();
  protected tipologiaDettaglioDesc: string;
  protected tipologiaDesc: string;
  protected importoTotale: number;
  protected finanziamentoImportoStato: number;
  protected finanziamentoImportoRegione: number;
  protected finanziamentoImportoAltro: number;

  //DETTAGLIO TOTALE FINANZIAMENTI
  pianiFinanziariPrincipaliTabellaRows: PianoFinanziarioTabellaRow[] = [];
  pianiFinanziariNonPrincipaliTabellaRows: PianoFinanziarioTabellaRow[] = [];
  finanziamentoTipoList: FinanziamentoTipo[];
  finanziamentoTipoDetList: FinanziamentoTipoDet[];
  tipologicheFinanziamento: TipologicaFinanziamento[];
  totImportoPrincipali: number = 0;
  totImportoRegionePrincipali: number = 0;
  totImportoStatoPrincipali: number = 0;
  totImportoAltri: number = 0;
  totImportoRegioneAltri: number = 0;
  totImportoStatoAltri: number = 0;
  totImporto: number = 0;
  totImportoRegione: number = 0;
  totImportoStato: number = 0;

  constructor(public dialog: MatDialog, private fb: FormBuilder, private registryApiService: RegistryApiService,
    private route: ActivatedRoute,
  ) {
    // this.form = this.fb.group({
    //   finanziamentiForm: this.fb.array(this.finanziamenti.map(f => this.createFinancialPlanElement(f))),
    //   altriFinanziamenti: this.fb.array([])
    // });
    this.form = new FormGroup({
    });
  }


  get finanziamentiForm(): FormArray {
    return this.form.get('finanziamentiForm') as FormArray;
  }

  get altriFinanziamentiForm(): FormArray {
    return this.form.get('altriFinanziamenti') as FormArray;
  }

  get altriFinanziamentiDisponibili(): boolean{
    let result = false
    this.intervento.pianiFinanziari.forEach(piano => {
      if(!piano.isPrincipale){
        result=true;
      }
    })
    return result;
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
  }

  ngOnInit(): void {
    // console.log("PASSAGGIO DA PIANO-FINANZIARIO.COMPONENT.TS")
    this.initForm();

    //DETTAGLIO TOTALE FINANZIAMENTI
    combineLatest([
      this.registryApiService.getFinanziamentoTipoList(),
      this.registryApiService.getFinanziamentoTipoDetList(),
      this.route.paramMap.pipe(
      map((params) => Number(params.get('id'))),
      switchMap((id) =>
        combineLatest([
        this.registryApiService.getTipologicheFinanziamento(id),
        ])
      )
      ),
    ]).subscribe(
      ([
      finanziamentoTipoList,
      finanziamentoTipoDetList,
      [tipologicheFinanziamento],
      ]) => {
      this.finanziamentoTipoList = finanziamentoTipoList;
      this.finanziamentoTipoDetList = finanziamentoTipoDetList;
      this.tipologicheFinanziamento = tipologicheFinanziamento;
      if (this.intervento) {
        if (this.intervento.pianiFinanziari?.length) {
        this.pianiFinanziariPrincipaliTabellaRows = this.buildTabellaPrincipaliRow();
        this.pianiFinanziariNonPrincipaliTabellaRows = this.buildTabellaNonPrincipaliRow();
        }
      }
      }
    );

    //this.caricaPianiFinanziari();
    //  if (this.finanziamenti) {
    //   const formValue = {...this.form.getRawValue(), totalePianoFin: this.getTotaleComplessivo()};
    //   this.formData.emit(formValue);
    // }
    // this.form?.valueChanges
    //   .pipe(takeUntil(this.destroy$))
    //   .subscribe({
    //     next: (): void => {
    //       const formValue = {...this.form.getRawValue(), totalePianoFin: this.getTotaleComplessivo()};
    //       this.formData.emit(formValue);
    //       this.formValidity.emit(this.form.valid);
    //     }
    //   });
  }

    initForm(): void {
      const intervento = this.intervento;
      this.form = this.fb.group({
        //nomeDirettore: new FormControl({value: intervento.intDirettoreGeneraleNome ?? '', disabled: !this.canEdit}, [Validators.required]),

      });
      const formValue = this.form?.getRawValue();
      this.formData.emit(formValue);
    }

  caricaPianiFinanziari(){
    // console.log("caricaPianiFinanziari 1");
    if(this.intervento.pianiFinanziari!=null){
      // console.log("caricaPianiFinanziari 2");
      this.intervento.pianiFinanziari.forEach(pianoFinanziario => {
        // console.log("caricaPianiFinanziari 3");
        this.tipologiaDettaglioDesc = pianoFinanziario.tipologiaDettaglioDesc;
        // console.log("this.tipologiaDettaglioDesc: "+this.tipologiaDettaglioDesc);
        this.tipologiaDesc = pianoFinanziario.tipologiaDesc;
        this.importoTotale =  pianoFinanziario.importoTotale;
        if(pianoFinanziario.finanziamentoImportoTipo){
          pianoFinanziario.finanziamentoImportoTipo.forEach(importoTipo => {
            if(importoTipo.finanziamentoImportoTipoCod==='IMP_STATO'){
              this.finanziamentoImportoStato = importoTipo.finanziamentoImporto;
            }
            else if(importoTipo.finanziamentoImportoTipoCod==='IMP_REGIONE'){
              this.finanziamentoImportoRegione = importoTipo.finanziamentoImporto;
            }
            else{
              //ALTRO
              this.finanziamentoImportoAltro = importoTipo.finanziamentoImporto;
            }
          });
        }
      });
    }
  }


  ngOnChanges(changes: SimpleChanges): void {
       if (changes['finanziamenti'] && changes['finanziamenti'].currentValue) {
        //this.resetForm();
      changes['finanziamenti'].currentValue.forEach((f: any) => {
        this.finanziamentiForm.push(this.createFinancialPlanElement(f));
      });
    }
  }
// TODO APPROFONDIRE SE ELIMINARE
  resetForm(): void {
    this.finanziamentiForm.clear();
    this.altriFinanziamentiForm.clear();
  }
  // DIALOG inserimento intervento
  // openDialog(): void {
  //   const dialogRef = this.dialog.open(ModaleAggiuntaFinanziamentoComponent, {
  //     width: '600px',
  //     data: {tipo: '', dettaglio: '', quotaRegione: 0, quotaStato: 0, importo: 0}
  //   });

  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result) {
  //       const group = this.createElementFromDialog(result);
  //       this.altriFinanziamentiForm.push(group);
  //     }
  //   });
  // }
  // usata per creare un elemento finanziamento da aggiungere alla lista del form intervento iesimo
  createFinancialPlanElement(element: any): FormGroup {
    return this.fb.group({
      tipo: element.tipoFinanziamento,
      dettaglio: element.finNote ?? ' - ',
      quotaRegione: element.finImporto ? element.finImporto * (element.tipoFinanziamento.finTipoDetPercentualeRegione / 100) : ' - ',
      quotaStato: element.finImporto ? element.finImporto * (element.tipoFinanziamento.finTipoDetPercentualeStato / 100) : ' - ',
      quotaAltro: element.finImporto ? element.finImporto * (element.tipoFinanziamento.finTipoDetPercentualeAltro / 100) : ' - ',
      //atto: element.provvedimento.provTitolo ?? ' - ',
      importo: element.finImporto ?? 0
    });
  }
  // estrazione dati del form
  createElementFromDialog(data: any): FormGroup {
    return this.fb.group({
      tipo: data.tipo,
      dettaglio: data.dettaglio,
      quotaRegione: '',
      quotaStato: '',
      //atto: data.atto,
      importo: data.importo
    });
  }
  // get tolale di dei totali
  getTotaleComplessivo(): number {
        const totaleFinanziamenti = this.calculateTotalFromValues(this.intervento.pianiFinanziari);
        //const totaleAltriFinanziamenti = this.calculateTotalFromValues(this.altriFinanziamentiForm.value);
        return totaleFinanziamenti; //+ totaleAltriFinanziamenti;
  }

  getTotaleComplessivoPrincipale(): number {
    const totaleFinanziamenti = this.calculateTotalFromValues(this.intervento.pianiFinanziari.filter((piano)=>piano.isPrincipale=== true));
    //const totaleAltriFinanziamenti = this.calculateTotalFromValues(this.altriFinanziamentiForm.value);
    return totaleFinanziamenti; //+ totaleAltriFinanziamenti;
  }


  getTotaleRegionePrincipale(): number {
    const totaleFinanziamenti = this.calculateTotalRegioneFromValues(this.intervento.pianiFinanziari.filter((piano)=>piano.isPrincipale=== true));
    //const totaleAltriFinanziamenti = this.calculateTotalFromValues(this.altriFinanziamentiForm.value);
    return totaleFinanziamenti; //+ totaleAltriFinanziamenti;
  }

  getTotaleStatoPrincipale(): number{
    const totaleFinanziamenti = this.calculateTotalStatoFromValues(this.intervento.pianiFinanziari.filter((piano)=>piano.isPrincipale=== true));
    //const totaleAltriFinanziamenti = this.calculateTotalFromValues(this.altriFinanziamentiForm.value);
    return totaleFinanziamenti;
  }

  getTotaleComplessivoSecondario(): number {
    const totaleFinanziamenti = this.calculateTotalFromValues(this.intervento.pianiFinanziari.filter((piano)=>piano.isPrincipale=== false));
    //const totaleAltriFinanziamenti = this.calculateTotalFromValues(this.altriFinanziamentiForm.value);
    return totaleFinanziamenti; //+ totaleAltriFinanziamenti;
  }


  getTotaleRegioneSecondario(): number {
    const totaleFinanziamenti = this.calculateTotalRegioneFromValues(this.intervento.pianiFinanziari.filter((piano)=>piano.isPrincipale=== false));
    //const totaleAltriFinanziamenti = this.calculateTotalFromValues(this.altriFinanziamentiForm.value);
    return totaleFinanziamenti; //+ totaleAltriFinanziamenti;
  }

  getTotaleStatoSecondario(): number{
    const totaleFinanziamenti = this.calculateTotalStatoFromValues(this.intervento.pianiFinanziari.filter((piano)=>piano.isPrincipale=== false));
    //const totaleAltriFinanziamenti = this.calculateTotalFromValues(this.altriFinanziamentiForm.value);
    return totaleFinanziamenti;
  }


  // funzione a supporto per il calcolo del totale dei finanziamenti
   calculateTotalFromValues(values: any[]): number {
     return values
       .filter(item => item.importoTotale !== null && !isNaN(item.importoTotale))
       .reduce((total, item) => total + Number(item.importoTotale), 0);
   }

  calculateTotalRegioneFromValues(values: PianoFinanziario[]): number {
    let totale = 0;
    values.forEach(piano=>{
      totale+=this.getRegionImport(piano)
    })
    return totale;
  }

  calculateTotalStatoFromValues(values: PianoFinanziario[]): number {
    let totale = 0;
    values.forEach(piano=>{
      totale+=this.getStatoImport(piano)
    })
    return totale;
  }

   getRegionImport(pianoFinanziario: PianoFinanziario): number {
    let result = 0;
    pianoFinanziario.finanziamentoImportoTipo.forEach(
      (finanziamentoTipo)=>{
        if(finanziamentoTipo.finanziamentoImportoTipoCod=='IMP_REGIONE'){
          result =  finanziamentoTipo.finanziamentoImporto;
        }
      }
    )
    return result;
  }

  getStatoImport(pianoFinanziario:PianoFinanziario) : number{
    let result = 0;
    pianoFinanziario.finanziamentoImportoTipo.forEach(
      (finanziamentoTipo)=>{
        if(finanziamentoTipo.finanziamentoImportoTipoCod=='IMP_STATO'){
          result =  finanziamentoTipo.finanziamentoImporto;
        }
      }
    )
    return result;
  }

  //DETTAGLIO TOTALE FINANZIAMENTI
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

    //DETTAGLIO TOTALE FINANZIAMENTI
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
}
