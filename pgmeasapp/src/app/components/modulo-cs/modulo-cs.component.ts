/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MatTabGroup } from "@angular/material/tabs";
import { ActivatedRoute, Router } from "@angular/router";
import { Allegato, Ente, Finanziamento, FinanziamentoLiquidazione, FinanziamentoLiquidazioneRichiesta, FinanziamentoTipo, FinanziamentoTipoDet, Intervento, InterventoFinanziamentoPrevSpesa, InterventoGaraAppalto, InterventoStatoProgettuale, InterventoStruttura, InterventoTipo, Provvedimento, Struttura } from "@pgmeas-library/model";
import { combineLatest, map, switchMap } from "rxjs";
import { LoadingService } from "src/app/services/loading.service";
import { MessageService } from "src/app/services/message.service";
import { ProjectApiService } from "src/app/services/project-api.service";
import { RegistryApiService } from "src/app/services/registry-api.service";
import { SuccessDialogComponent } from "../success-dialog/success-dialog.component";
import { MatDialog } from "@angular/material/dialog";
import { YearSelectionDialogComponent } from "../year-selection-dialog/year-selection-dialog.component";

@Component({
  selector: 'app-modulo-cs',
  templateUrl: './modulo-cs.component.html',
  styleUrls: ['./modulo-cs.component.scss']
})
export class ModuloCsComponent implements OnInit {
  loading = true;

  entity: Intervento;
  interventoTipoList: InterventoTipo[];
  enteList: Ente[];
  finanziamentoTipoList: FinanziamentoTipo[];
  finanziamentoTipoDetList: FinanziamentoTipoDet[];
  provvedimentoList: Provvedimento[];
  finanziamentoList: Finanziamento[];
  interventoFinanziamentoPrevSpesaList: InterventoFinanziamentoPrevSpesa[];
  finanziamentoLiquidazioneRichiestaList: FinanziamentoLiquidazioneRichiesta[];
  finanziamentoLiquidazioneList: FinanziamentoLiquidazione[];
  interventoGaraAppalto: InterventoGaraAppalto[];
  strutturaList: Struttura[];
  interventoStrutturaList: InterventoStruttura[];
  interventoStatoProgettualeList: InterventoStatoProgettuale[];
  allegato: Allegato;
  addedPrevSpesaAnnoColumns: number[] = [];
  readOnly: boolean;
  intDgRegionaleApprovazioneData: number;
  intDgRegionaleApprovazione: string;

  myForm: FormGroup;

  @ViewChild(MatTabGroup)
  private matTabGroup: MatTabGroup;

  constructor(
    private projectApiService: ProjectApiService,
    private registryApiService: RegistryApiService,
    private route: ActivatedRoute,
    private message: MessageService,
    private router: Router,
    private fb: FormBuilder,
    private loadingService: LoadingService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    combineLatest([
      this.registryApiService.getInterventoTipoList(),
      this.registryApiService.getEnteList(),
      this.registryApiService.getFinanziamentoTipoList(),
      this.registryApiService.getFinanziamentoTipoDetList(),
      this.projectApiService.getProvvedimentoList(),
      this.registryApiService.getStrutturaList(),
      this.registryApiService.getInterventoStatoProgettualeList(),
      this.route.paramMap.pipe(
        map((params) => Number(params.get('id'))),
        switchMap((id) =>
          combineLatest([
            this.projectApiService.getInterventoDetail(id),
            this.projectApiService.getFinanziamentoListByIntervento(id),
            this.projectApiService.getInterventoFinanziamentoPrevSpesaListByIntervento(id),
            this.projectApiService.getFinanziamentoLiquidazioneRichiestaListByIntervento(id),
            this.projectApiService.getFinanziamentoLiquidazioneListByIntervento(id),
            this.projectApiService.getInterventoGaraAppaltoListByIntervento(id),
            this.projectApiService.getInterventoStrutturaListByIntervento(id),
            this.projectApiService.getInterventoAllegatoListByIntervento(id)
          ])
        )
      ),
      this.route.paramMap
    ]).subscribe(
      ([
        interventoTipoList,
        enteList,
        finanziamentoTipoList,
        finanziamentoTipoDetList,
        provvedimentoList,
        strutturaList,
        interventoStatoProgettualeList,
        [intervento, finanziamentoList, interventoFinanziamentoPrevSpesaList, finanziamentoLiquidazioneRichiestaList, finanziamentoLiquidazioneList, interventoGaraAppalto, interventoStrutturaList, interventoAllegatoList],
        paramMap
      ]) => {
        this.interventoTipoList = interventoTipoList;
        this.enteList = enteList;
        this.finanziamentoTipoList = finanziamentoTipoList;
        this.finanziamentoTipoDetList = finanziamentoTipoDetList;
        this.provvedimentoList = provvedimentoList;
        this.entity = intervento;
        this.finanziamentoList = finanziamentoList;
        this.interventoFinanziamentoPrevSpesaList = interventoFinanziamentoPrevSpesaList;
        this.finanziamentoLiquidazioneRichiestaList = finanziamentoLiquidazioneRichiestaList;
        this.finanziamentoLiquidazioneList = finanziamentoLiquidazioneList;
        this.interventoGaraAppalto = interventoGaraAppalto;
        this.strutturaList = strutturaList;
        this.interventoStrutturaList = interventoStrutturaList;
        this.interventoStatoProgettualeList = interventoStatoProgettualeList;

        if (paramMap.get('mode') == 'view') {
          this.readOnly = paramMap.get('mode') === 'view';
        } else {
          this.readOnly = false;
        }
        this.allegato = {allegatoOggetto: '', allegatoProtocolloData: new Date().getTime()} as Allegato;

        if(this.entity.allegatoDgrApprovazione != null){
          this.entity.allegatoDgrApprovazione.forEach(allegato => {
            if(allegato.intAllegatoData!=null){
              this.intDgRegionaleApprovazioneData = allegato.intAllegatoData;
            }else{
              this.intDgRegionaleApprovazione  =allegato.intAllegatoNumero;
            }
          });         
        }

        //if (paramMap.has('allegatoId')) {
        //  const allegatoId = Number(paramMap.get('allegatoId'));
        //  this.allegato = interventoAllegatoList.find(ia => ia.allegatoId === allegatoId)!;
        //  this.readOnly = paramMap.get('mode') === 'view';
        //} else {
        //  this.allegato = {allegatoOggetto: '', allegatoProtocolloData: new Date().getTime()} as Allegato;
        //  this.readOnly = false;
        //}

        const interventoStatoProgettualeIds = interventoStatoProgettualeList.map(isp => isp.intStatoProgId);

        const items = interventoStrutturaList.map(is => {
          const cantierePrevista = is.intstrAperturaCantiereDataPrevista ? new Date(is.intstrAperturaCantiereDataPrevista) : null;
          const cantiereEffettiva = is.intstrAperturaCantiereDataEffettiva ? new Date(is.intstrAperturaCantiereDataEffettiva) : null;
          const collaudoPrevista = is.intstrCollaudoDataPrevista ? new Date(is.intstrCollaudoDataPrevista) : null;
          const collaudoEffettiva = is.intstrCollaudoDataEffettiva ? new Date(is.intstrCollaudoDataEffettiva) : null;

          return this.fb.group({
            intStrId: [is.intStrId, Validators.required],
            intstrAperturaCantiereDataPrevista: [{value: cantierePrevista, disabled: this.readOnly}],
            intstrAperturaCantiereDataEffettiva: [{value: cantiereEffettiva, disabled: this.readOnly}],
            intstrCollaudoDataPrevista: [{value: collaudoPrevista, disabled: this.readOnly}],
            intstrCollaudoDataEffettiva: [{value: collaudoEffettiva, disabled: this.readOnly}]
          });
        });

        if (this.readOnly) {
          this.myForm = this.fb.group({
            interventoStatoProgettuale: [{value: interventoStatoProgettualeIds, disabled: this.readOnly}],
            items: this.fb.array(items)
          });
        } else {
          this.myForm = this.fb.group({
            allegatoOggetto: [{value: this.allegato.allegatoOggetto, disabled: this.readOnly}, Validators.required],
            interventoStatoProgettuale: [{value: interventoStatoProgettualeIds, disabled: this.readOnly}],
            items: this.fb.array(items)
          });
        }

        this.loading = false;

        // Workaround per caricare il contenuto della tab "Previsione avanzamento di spesa"
        if (!this.readOnly) {
          setTimeout(() => this.matTabGroup.selectedIndex = 1, 0);
          setTimeout(() => this.matTabGroup.selectedIndex = 0, 1);
        }
      }
    );
  }

  // Fonte del finanziamento

  getFonteDelFinanziamentoLabel(): string {
    const finTipoDetIds = this.finanziamentoList.map(f => f.finTipoDetId);
    const finTipoIds = this.finanziamentoTipoDetList.filter(ftd => finTipoDetIds.includes(ftd.finTipoDetId)).map(ftd => ftd.finTipoId);
    return this.finanziamentoTipoList.filter(ft => finTipoIds.includes(ft.finTipoId)).map(ft => ft.finTipoDesc).join(', ');
  }

  // Dati intervento

  getEnte(): Ente | undefined {
    return this.enteList.find(e => e.enteId === this.entity.enteId);
  }

  getCodiciCigLabel(): string {
    return this.interventoGaraAppalto.map(iga => iga.garaAppaltoCigCod).join(', ');
  }

  getStruttura(interventoStruttura: InterventoStruttura): Struttura | undefined {
    return this.strutturaList.find((s) => s.strId === interventoStruttura.strId);
  }

  getStrutturaLabel(interventoStruttura: InterventoStruttura): string {
    return this.getStruttura(interventoStruttura)?.strDenominazione ?? String(interventoStruttura.strId);
  }

  // Piano finanziario

  getFinanziamentoListByPrincipale(principale: boolean): Finanziamento[] {
    return this.finanziamentoList.filter(f => f.finPrincipale === principale);
  }

  getFinanziamentoTipoDet(finanziamento: Finanziamento): FinanziamentoTipoDet | undefined {
    return this.finanziamentoTipoDetList.find(ftd => ftd.finTipoDetId === finanziamento.finTipoDetId);
  }

  getFinanziamentoTipo(finanziamento: Finanziamento): FinanziamentoTipo | undefined {
    const finanziamentoTipoDet = this.getFinanziamentoTipoDet(finanziamento);
    return finanziamentoTipoDet ? this.finanziamentoTipoList.find(ft => ft.finTipoId === finanziamentoTipoDet.finTipoId) : undefined;
  }

  getProvvedimento(finanziamento: Finanziamento): Provvedimento | undefined {
    return this.provvedimentoList.find(p => p.provId === finanziamento.provId);
  }

  getTotaleProvvedimentoImportoByPrincipale(principale: boolean): number {
    const provIds = this.getFinanziamentoListByPrincipale(principale).map(f => f.provId);
    return this.provvedimentoList.filter(p => provIds.includes(p.provId)).map(p => p.provImporto).reduce((a, c) => a + c, 0);
  }

  // Previsione avanzamento di spesa

  private getInterventoFinanziamentoPrevSpesaAnnoList(): number[] {
    return this.interventoFinanziamentoPrevSpesaList.map(ifps => ifps.intFinPrevSpesaAnno).reduce((a, c) => a.includes(c) ? a : [...a, c], [] as number[]);
  }

  getInterventoFinanziamentoPrevSpesa(finanziamento: Finanziamento, anno: number): InterventoFinanziamentoPrevSpesa | undefined {
    return this.interventoFinanziamentoPrevSpesaList.find(ifps => ifps.finId === finanziamento.finId && ifps.intFinPrevSpesaAnno === anno);
  }

  getInterventoFinanziamentoPrevSpesaTotaleRiga(finanziamento: Finanziamento): number {
    return Array.from(document.querySelectorAll<HTMLInputElement>(`input[data-finId="${finanziamento.finId}"]`)).map(e => e.valueAsNumber || 0).reduce((a, c) => a + c, 0);
  }

  getInterventoFinanziamentoPrevSpesaTotaleColonna(anno: number): number {
    return Array.from(document.querySelectorAll<HTMLInputElement>(`input[data-anno="${anno}"]`)).map(e => e.valueAsNumber || 0).reduce((a, c) => a + c, 0);
  }

  getInterventoFinanziamentoPrevSpesaTotaleFinale(): number {
    return Array.from(document.querySelectorAll<HTMLInputElement>('input[data-finId][data-anno]')).map(e => e.valueAsNumber || 0).reduce((a, c) => a + c, 0);
  }

  addPrevSpesaAnnoColumn(anno: number) {
    if (!anno) {
      this.message.error('Inserire un valore');
      return;
    }

    if (this.getPrevSpesaAnnoColumns().includes(anno)) {
      this.message.error('Anno già presente');
      return;
    }

    this.addedPrevSpesaAnnoColumns.push(anno);
  }

  isPrevSpesaAnnoColumnRemovable(anno: number) {
    return this.addedPrevSpesaAnnoColumns.includes(anno);
  }

  removePrevSpesaAnnoColumn(anno: number) {
    this.addedPrevSpesaAnnoColumns = this.addedPrevSpesaAnnoColumns.filter(c => c !== anno);
  }

  getPrevSpesaAnnoColumns(): number[] {
    return [ ...this.getInterventoFinanziamentoPrevSpesaAnnoList(), ...this.addedPrevSpesaAnnoColumns].sort((a, b) => a - b);
  }

  // Elenco delle liquidazioni

  getCurrentlyUsedFinanziamentoTipoListByPrincipale(principale: boolean): FinanziamentoTipo[] {
    const finTipoDetIds = this.getFinanziamentoListByPrincipale(principale).map(f => f.finTipoDetId);
    const finTipoIds = this.finanziamentoTipoDetList.filter(ftd => finTipoDetIds.includes(ftd.finTipoDetId)).map(ftd => ftd.finTipoId);
    return this.finanziamentoTipoList.filter(ft => finTipoIds.includes(ft.finTipoId));
  }

  getCurrentlyUsedPrincipaleValues() {
    return this.finanziamentoList
      .filter(f => this.finanziamentoLiquidazioneRichiestaList.some(flr => flr.finId === f.finId))
      .map(f => f.finPrincipale)
      .reduce((a, c) => a.includes(c) ? a : [...a, c], [] as boolean[]);
  }

  getFinanziamentoLiquidazioneRichiestaListByFinanziamentoPrincipaleAndTipo(principale: boolean, finanziamentoTipo: FinanziamentoTipo): FinanziamentoLiquidazioneRichiesta[] {
    const finTipoDetIds = this.finanziamentoTipoDetList.filter(ftd => ftd.finTipoId === finanziamentoTipo.finTipoId).map(ftd => ftd.finTipoDetId);
    const finIds = this.finanziamentoList.filter(f => f.finPrincipale === principale && finTipoDetIds.includes(f.finTipoDetId)).map(f => f.finId);
    return this.finanziamentoLiquidazioneRichiestaList.filter(flr => finIds.includes(flr.finId));
  }

  getFinanziamentoTipoByLiquidazionRichiesta(finanziamentoLiquidazioneRichiesta: FinanziamentoLiquidazioneRichiesta) {
    const finanziamento = this.finanziamentoList.find(f => f.finId === finanziamentoLiquidazioneRichiesta.finId);
    return finanziamento && this.getFinanziamentoTipo(finanziamento);
  }

  getFinanziamentoTipoDetByLiquidazionRichiesta(finanziamentoLiquidazioneRichiesta: FinanziamentoLiquidazioneRichiesta) {
    const finanziamento = this.finanziamentoList.find(f => f.finId === finanziamentoLiquidazioneRichiesta.finId);
    return finanziamento && this.getFinanziamentoTipoDet(finanziamento);
  }

  getFinanziamentoLiquidazioneImporto(finanziamentoLiquidazioneRichiesta: FinanziamentoLiquidazioneRichiesta): number {
    return this.finanziamentoLiquidazioneList.filter(fl => finanziamentoLiquidazioneRichiesta.listaLiquidazione.some(l => l.liqId === fl.liqId)).reduce((a, c) => a + c.liqImporto, 0);
  }

  getImportoDaLiquidare(finanziamentoLiquidazioneRichiesta: FinanziamentoLiquidazioneRichiesta) {
    return finanziamentoLiquidazioneRichiesta.liqRicImporto && this.getFinanziamentoLiquidazioneImporto(finanziamentoLiquidazioneRichiesta) ?
      finanziamentoLiquidazioneRichiesta.liqRicImporto - this.getFinanziamentoLiquidazioneImporto(finanziamentoLiquidazioneRichiesta) :
      undefined
  }

  getLiquidazioneImportoErogato(finanziamentoLiquidazioneRichiesta: FinanziamentoLiquidazioneRichiesta) {
    return finanziamentoLiquidazioneRichiesta.listaLiquidazione.reduce((a, c) => a + c.liqImportoErogato, 0);
  }

  getLiquidazioneImportoIncassato(finanziamentoLiquidazioneRichiesta: FinanziamentoLiquidazioneRichiesta) {
    return finanziamentoLiquidazioneRichiesta.listaLiquidazione.reduce((a, c) => a + c.liqImportoIncassato, 0);
  }

  getFinanziamentoLiquidazioneRichiestaImportoTotale(principale: boolean, finanziamentoTipo: FinanziamentoTipo): number {
    return this.getFinanziamentoLiquidazioneRichiestaListByFinanziamentoPrincipaleAndTipo(principale, finanziamentoTipo).map(flr => flr.liqRicImporto).reduce((a, c) => a + c, 0);
  }

  getFinanziamentoLiquidazioneImportoTotale(principale: boolean, finanziamentoTipo: FinanziamentoTipo): number {
    const liqIds = this.getFinanziamentoLiquidazioneRichiestaListByFinanziamentoPrincipaleAndTipo(principale, finanziamentoTipo).flatMap(flr => flr.listaLiquidazione).map(l => l.liqId);
    return this.finanziamentoLiquidazioneList.filter(fl => liqIds.includes(fl.liqId)).map(fl => fl.liqImporto).reduce((a, c) => a + c, 0);
  }

  getImportoDaLiquidareTotale(principale: boolean, finanziamentoTipo: FinanziamentoTipo): number {
    return this.getFinanziamentoLiquidazioneRichiestaImportoTotale(principale, finanziamentoTipo) - this.getFinanziamentoLiquidazioneImportoTotale(principale, finanziamentoTipo);
  }

  getLiquidazioneImportoErogatoTotale(principale: boolean, finanziamentoTipo: FinanziamentoTipo): number {
    return this.getFinanziamentoLiquidazioneRichiestaListByFinanziamentoPrincipaleAndTipo(principale, finanziamentoTipo).flatMap(flr => flr.listaLiquidazione).map(l => l.liqImportoErogato).reduce((a, c) => a + c, 0);
  }

  getLiquidazioneImportoIncassatoTotale(principale: boolean, finanziamentoTipo: FinanziamentoTipo): number {
    return Array.from(document.querySelectorAll<HTMLInputElement>(`input[data-principale="${principale}"][data-finTipoId="${finanziamentoTipo.finTipoId}"][data-liqRicId]`)).map(e => e.valueAsNumber || 0).reduce((a, c) => a + c, 0);
  }

  // Importo totale liquidato

  getImportoTotaleDelleRichieste(finId?: number): number {
    return this.finanziamentoLiquidazioneRichiestaList
        .filter(flr => !finId || flr.finId === finId)
        .map(flr => flr.liqRicImporto)
        .reduce((a, c) => a + c, 0);
  }

  getImportoTotaleLiquidato(finId?: number): number {
    return this.finanziamentoLiquidazioneList
        .filter(fl => !finId || fl.finId === finId)
        .map(fl => fl.liqImporto)
        .reduce((a, c) => a + c, 0);
  }

  getImportoTotaleDaLiquidare(finId?: number): number {
    return this.getImportoTotaleDelleRichieste(finId) - this.getImportoTotaleLiquidato(finId);
  }

  getImportoTotaleErogato(finId?: number): number {
    return this.finanziamentoLiquidazioneRichiestaList
        .filter(flr => !finId || flr.finId === finId)
        .flatMap(flr => flr.listaLiquidazione)
        .map(l => l.liqImportoErogato)
        .reduce((a, c) => a + c, 0);
  }

  getImportoTotaleIncassato(finId?: number): number {
    return this.finanziamentoLiquidazioneRichiestaList
        .filter(flr => !finId || flr.finId === finId)
        .flatMap(flr => flr.listaLiquidazione)
        .map(l => l.liqImportoIncassato)
        .reduce((a, c) => a + c, 0);
  }

  // Importo della spesa - Non speso - Economie

  getCurrentlyUsedFinanziamentoTipoDetList(): FinanziamentoTipoDet[] {
    const finTipoDetIds = this.finanziamentoList.map(f => f.finTipoDetId);
    return this.finanziamentoTipoDetList.filter(ftd => finTipoDetIds.includes(ftd.finTipoDetId));
  }

  getFinanziamentoTipoByTipoDet(finanziamentoTipoDet: FinanziamentoTipoDet): FinanziamentoTipo | undefined {
    return this.finanziamentoTipoList.find(ft => ft.finTipoId === finanziamentoTipoDet.finTipoId);
  }

  getImportoFinanziatoTotale(finanziamentoTipoDet?: FinanziamentoTipoDet): number {
    return this.finanziamentoList.filter(f => !finanziamentoTipoDet || f.finTipoDetId === finanziamentoTipoDet.finTipoDetId).map(f => f.finImporto).reduce((a, c) => a + c, 0);
  }

  getPercentualeAvanzamentoSpesa(finanziamentoTipoDet: FinanziamentoTipoDet): number | undefined {
    const input = document.querySelector<HTMLInputElement>(`input[data-finTipoDetId="${finanziamentoTipoDet.finTipoDetId}"]`);
    return input?.valueAsNumber ? input.valueAsNumber / this.getImportoFinanziatoTotale(finanziamentoTipoDet) : undefined;
  }

  getPercentualeEconomia(finanziamentoTipoDet: FinanziamentoTipoDet): number | undefined {
    const input = document.querySelector<HTMLInputElement>(`input[data-finTipoDetId="${finanziamentoTipoDet.finTipoDetId}"]`);
    return input?.valueAsNumber && this.getImportoFinanziatoTotale(finanziamentoTipoDet) - input.valueAsNumber > 0 ? 1 - this.getPercentualeAvanzamentoSpesa(finanziamentoTipoDet)! : undefined;
  }

  getImportoEconomiaFinale(finanziamentoTipoDet: FinanziamentoTipoDet): number | undefined {
    const input = document.querySelector<HTMLInputElement>(`input[data-finTipoDetId="${finanziamentoTipoDet.finTipoDetId}"]`);
    return input?.valueAsNumber && this.getImportoFinanziatoTotale(finanziamentoTipoDet) - input.valueAsNumber > 0 ? this.getImportoFinanziatoTotale(finanziamentoTipoDet) - input.valueAsNumber : undefined;
  }

  getImportoTotaleSpesoDallAziendaTotale(): number {
    return Array.from(document.querySelectorAll<HTMLInputElement>('input[data-finTipoDetId]')).map(e => e.valueAsNumber || 0).reduce((a, c) => a + c, 0);
  }

  getPercentualeAvanzamentoSpesaTotale(): number {
    return this.getImportoTotaleSpesoDallAziendaTotale() / this.getImportoFinanziatoTotale();
  }

  getPercentualeEconomiaTotale(): number | undefined {
    return this.getImportoEconomiaFinaleTotale() ? 1 - this.getPercentualeAvanzamentoSpesaTotale() : undefined;
  }

  getImportoEconomiaFinaleTotale(): number | undefined {
    const delta = this.getImportoFinanziatoTotale() - this.getImportoTotaleSpesoDallAziendaTotale();
    return delta > 0 ? delta : undefined;
  }

  // Submit

  submit() {
    if (this.myForm.invalid) {
      this.message.error("Riempire i campi obbligatori e riprovare");
      return;
    }

    const campiPrevisioneAvanzamentoSpesa = Array.from(document.querySelectorAll<HTMLInputElement>('input[data-finId][data-anno]'));

    if (campiPrevisioneAvanzamentoSpesa.some(e => !e.value)) {
      this.message.error("Riempire i campi Previsione avanzamento di spesa");
      return;
    }

    this.loadingService.on();

    const value = this.myForm.value;

    const listaIntStruttura = value.items.map((is: any) => ({
      intStrId: is.intStrId,
      intstrAperturaCantiereDataPrevista: is.intstrAperturaCantiereDataPrevista?.getTime(),
      intstrAperturaCantiereDataEffettiva: is.intstrAperturaCantiereDataEffettiva?.getTime(),
      intstrCollaudoDataPrevista: is.intstrCollaudoDataPrevista?.getTime(),
      intstrCollaudoDataEffettiva: is.intstrCollaudoDataEffettiva?.getTime()
    }));

    const listaIntFinanziamentoPrevSpesa = campiPrevisioneAvanzamentoSpesa.map(e =>
      ({ finId: Number(e.dataset['finid']), intFinPrevSpesaAnno: Number(e.dataset['anno']), intFinPrevSpesaImporto: e.valueAsNumber }));

    const listaFinanziamentoLiquidazioneRichiesta = Array.from(document.querySelectorAll<HTMLInputElement>(`input[data-principale][data-finTipoId][data-liqRicId]`)).map(e =>
      ({ liqRicId: Number(e.dataset['liqricid']), liqImportoIncassato: e.valueAsNumber }));

    const request = {
      intId: this.entity.intId,
      enteId: this.entity.enteId,
      allegatoOggetto: value.allegatoOggetto,
      listaIntStatoProgettualeId: value.interventoStatoProgettuale,
      listaIntStruttura,
      listaIntFinanziamentoPrevSpesa,
      listaFinanziamentoLiquidazioneRichiesta,
    };
    this.projectApiService.compilaMonitoraggio(request).subscribe({
      next: allegato => {
        this.loadingService.off();
        this.router.navigate(['/', 'modulo-c', this.entity.intId, allegato.allegatoId, 'view']);
        this.dialog.open(SuccessDialogComponent, {
          width: '800px',
          data: {
            title: 'Modulo C',
            message: 'Il Modulo C è stato salvato correttamente',
            button: {
              label: 'Scarica PDF',
              onClick: () => this.downloadPdf(allegato.allegatoId)
            }
          }
        });
      },
      error: () => this.loadingService.off()
    });
  }

  // Download

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

  richiestaAllegato(id: number) {
    this.dialog.open(YearSelectionDialogComponent, {
      width: '800px',
      data: { intId: id },
    }).afterClosed().subscribe(allegatoId =>
      this.downloadPdf(allegatoId));
  }

  back() {
    const back = history.state.back ?? '/consultazione-interventi';
    this.router.navigateByUrl(back);
  }
}
