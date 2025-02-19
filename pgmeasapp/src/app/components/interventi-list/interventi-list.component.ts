/*
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 *
 * SPDX-License-Identifier: EUPL-1.2
 */

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatExpansionPanel } from '@angular/material/expansion';
import { PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, combineLatest, map, Observable, of, tap } from 'rxjs';
import { InterventiFilter } from './../../../../projects/pgmeas-library/model/src/interventi-filter';
import { RicercaInterventiResult } from './../../../../projects/pgmeas-library/model/src/ricerca-interventi-result';

import {
  Ente,
  FinanziamentoTipo,
  FinanziamentoTipoDet,
  InterventoAppaltoTipo,
  InterventoCategoria,
  InterventoContrattoTipo,
  InterventoFinalita,
  InterventoObiettivo,
  InterventoStato,
  InterventoStatoProgettuale,
  InterventoTipo,
  Struttura,
  StrutturaTipo,
} from '@pgmeas-library/model';

import { MatDialog } from '@angular/material/dialog';
import { MatMenu } from '@angular/material/menu';
import { MatSelect } from '@angular/material/select';
import { ModuloStato } from '@pgmeas-library/model/src/modulo-stato';
import { jsPDF } from 'jspdf';
import autoTable from 'jspdf-autotable';
import { MessageService } from 'src/app/services/message.service';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { RegistryApiService } from 'src/app/services/registry-api.service';
import { UserService } from 'src/app/services/user.service';
import {
  getAziendaLabelByEnteId,
  getStatoInterventoByStatoIdLabel,
} from 'src/app/utils/intervento-utils';
import { PgmeasContextEnum } from 'src/app/utils/pgmeas_context_enum';
import { yesNo } from 'src/app/utils/pipe-utils';
import * as XLSX from 'xlsx';
import { ModaleAzioneFinanziamentoComponent } from '../modale-azione-finanziamento/modale-azione-finanziamento.component';
import { ModaleAzioneComponent } from '../modale-azione/modale-azione.component';

interface RicercaInterventiResultRow extends RicercaInterventiResult {
  azienda: string;
  statoIntervento: string;
}

interface SearchFilter {
  name: string;
  label: string;
  shownValues: string;
  tooltip: string;
}

@Component({
  selector: 'app-interventi-list',
  templateUrl: './interventi-list.component.html',
  styleUrls: ['./interventi-list.component.scss'],
})
export class InterventiListComponent implements OnInit {
  private storageKey = 'pgmeas_lastsearchForm';
  path: string;

  loading = true;

  enteList: Ente[];
  finanziamentoTipoDetList: FinanziamentoTipoDet[];
  finanziamentoTipoList: FinanziamentoTipo[];
  interventoCategoriaList: InterventoCategoria[];
  interventoFinalitaList: InterventoFinalita[];
  interventoObiettivoList: InterventoObiettivo[];
  interventoStatoList: InterventoStato[];
  interventoStatoProgettualeList: InterventoStatoProgettuale[];
  interventoTipoList: InterventoTipo[];
  interventoAppaltoTipoList: InterventoAppaltoTipo[];
  interventoContrattoTipoList: InterventoContrattoTipo[];
  strutturaList: Struttura[];
  moduloStatoList: ModuloStato[];
  strutturaTipoList: StrutturaTipo[];
  interventoAnnoList: number[];
  searchForm: FormGroup;
  rowPerPage: number = 10;
  pageNumber: number = 0;
  total: number = 0;
  orderBy: string | null;
  orderDirection: string | null;
  matSortVal: MatSort | null;

  tableLoading: 'NOT_SHOWN' | 'LOADING' | 'SHOWN' = 'NOT_SHOWN';

  searchFilters: SearchFilter[];
  searchFilterSchema: {
    [k: string]: { nameLabel: string; getValueLabel: (value: any) => string };
  } = {
    anno: {
      nameLabel: 'Anno di inserimento intervento',
      getValueLabel: (value) => value,
    },
    cup: { nameLabel: 'CUP', getValueLabel: (value) => value },
    titolo: { nameLabel: 'Titolo', getValueLabel: (value) => value },
    codicePgmeas: {
      nameLabel: 'Codice PGMEAS',
      getValueLabel: (value) => value,
    },
    ente: {
      nameLabel: 'Aziende',
      getValueLabel: (value) =>
        this.enteList.find((e) => e.enteId === value)!.enteDesc,
    },
    finanziamentoTipoDet: {
      nameLabel: 'Dettaglio tipologia di finanziamento',
      getValueLabel: (value) =>
        this.finanziamentoTipoDetList.find((ft) => ft.finTipoDetId === value)!
          .finTipoDetDesc,
    },
    finanziamentoTipo: {
      nameLabel: 'Tipologia di finanziamento',
      getValueLabel: (value) =>
        this.finanziamentoTipoList.find((ft) => ft.finTipoId === value)!
          .finTipoDesc,
    },
    interventoObiettivo: {
      nameLabel: 'Obiettivo intervento',
      getValueLabel: (value) =>
        this.interventoObiettivoList.find((io) => io.intObiettivoId === value)!
          .intObiettivoDesc,
    },
    interventoFinalita: {
      nameLabel: 'Finalità intervento',
      getValueLabel: (value) =>
        this.interventoFinalitaList.find((if_) => if_.intFinalitaId === value)!
          .intfinalitaDesc,
    },
    interventoCategoria: {
      nameLabel: 'Categoria intervento',
      getValueLabel: (value) =>
        this.interventoCategoriaList.find((ic) => ic.intCategoriaId === value)!
          .intCategoriaDesc,
    },
    interventoStato: {
      nameLabel: 'Stato intervento',
      getValueLabel: (value) =>
        this.interventoStatoList.find((is) => is.intStatoId === value)!
          .intStatoDesc,
    },
    interventoStatoProgettuale: {
      nameLabel: 'Stato progettuale',
      getValueLabel: (value) =>
        this.interventoStatoProgettualeList.find(
          (isp) => isp.intStatoProgId === value
        )!.intStatoProgDesc,
    },
    strutturaTipo: {
      nameLabel: 'Tipologia struttura',
      getValueLabel: (value) =>
        this.strutturaTipoList.find((st) => st.strutturaTipoId === value)!
          .strutturaTipoDesc,
    },
    interventoTipo: {
      nameLabel: 'Tipologia intervento',
      getValueLabel: (value) =>
        this.interventoTipoList.find((it) => it.intTipoId === value)!
          .intTipoDesc,
    },
    interventoAppaltoTipo: {
      nameLabel: 'Tipologia appalto',
      getValueLabel: (value) =>
        this.interventoAppaltoTipoList.find(
          (iat) => iat.intAppaltoTipoId === value
        )!.intAppaltoTipoDesc,
    },
    interventoContrattoTipo: {
      nameLabel: 'Tipologia contrattuale',
      getValueLabel: (value) =>
        this.interventoContrattoTipoList.find(
          (ict) => ict.intContrattoTipoId === value
        )!.intContrattoTipoDesc,
    },
    struttura: {
      nameLabel: 'Strutture',
      getValueLabel: (value) =>
        this.strutturaList.find((s) => s.strId === value)!.strDenominazione,
    },
    soloInterventiFinanziati: {
      nameLabel: 'Solo interventi finanziati',
      getValueLabel: (value) => yesNo(value),
    },
  };
  displayedColumns = [
    'azienda',
    'intCup',
    'intCod',
    'intTitolo',
    'statoIntervento',
    'intImporto',
    // 'finanziamentiRegionali',
    // 'finanziamentiStatali',
    // 'altriFinanziamenti',
    'actions',
  ];
  dataSource = new MatTableDataSource<RicercaInterventiResultRow>();

  pgmeasContext: PgmeasContextEnum;

  @ViewChild(MatExpansionPanel) expansionPanel: MatExpansionPanel;
  // @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private projectApiService: ProjectApiService,
    private registryApiService: RegistryApiService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private message: MessageService,
    private dialog: MatDialog,
    private router: Router,
    private user: UserService
  ) {}

  isRicercaStoricoInterventi(): boolean {
    return this.path === 'ricerca-storico-interventi';
  }

  ngOnInit() {
    this.pgmeasContext = this.route.snapshot.data['context'];
    const annoCorrente = new Date().getFullYear();
    this.searchForm = this.fb.group({
      anno: [
        this.isProgrammazioneInterventi() ? annoCorrente : null,
        Validators.required,
      ],
      cup: [null],
      titolo: [null],
      codicePgmeas: [null],
      interventoObiettivo: [[]],
      interventoFinalita: [[]],
      interventoCategoria: [[]],
      interventoStato: [[]],
      interventoStatoProgettuale: [[]],
      strutturaTipo: [[]],
      interventoTipo: [[]],
      interventoAppaltoTipo: [[]],
      interventoContrattoTipo: [[]],
      ente: [[]],
      struttura: [{ value: [], disabled: true }],
      soloInterventiFinanziati: [false],
      finanziamentoTipo: [[]],
      finanziamentoTipoDet: [{ value: [], disabled: true }],
    });

    this.path = this.route.routeConfig?.path!;

    combineLatest([
      this.registryApiService.getEnteList(),
      this.registryApiService.getFinanziamentoTipoDetList(),
      this.registryApiService.getFinanziamentoTipoList(),
      this.registryApiService.getInterventoCategoriaList(),
      this.registryApiService.getInterventoFinalitaList(),
      this.registryApiService.getInterventoObiettivoList(),
      this.registryApiService.getInterventoStatoList(),
      this.registryApiService.getInterventoStatoProgettualeList(),
      this.registryApiService.getInterventoTipoList(),
      this.registryApiService.getInterventoAppaltoTipoList(),
      this.registryApiService.getInterventoContrattoTipoList(),
      this.registryApiService.getStrutturaList(),
      this.registryApiService.getModuloStatoList(),
      // this.projectApiService.getFinanziamentoList(),
      this.projectApiService.getInterventoAnnoList(),
    ]).subscribe(
      ([
        enteList,
        finanziamentoTipoDetList,
        finanziamentoTipoList,
        interventoCategoriaList,
        interventoFinalitaList,
        interventoObiettivoList,
        interventoStatoList,
        interventoStatoProgettualeList,
        interventoTipoList,
        interventoAppaltoTipoList,
        interventoContrattoTipoList,
        strutturaList,
        moduloStatoList,
        interventoAnnoList,
      ]) => {
        this.enteList = enteList;
        this.finanziamentoTipoDetList = finanziamentoTipoDetList;
        this.finanziamentoTipoList = finanziamentoTipoList;
        this.interventoCategoriaList = interventoCategoriaList;
        this.interventoFinalitaList = interventoFinalitaList;
        this.interventoObiettivoList = interventoObiettivoList;
        this.interventoStatoList = interventoStatoList;
        this.interventoStatoProgettualeList = interventoStatoProgettualeList;
        this.interventoTipoList = interventoTipoList;
        this.interventoAppaltoTipoList = interventoAppaltoTipoList;
        this.interventoContrattoTipoList = interventoContrattoTipoList;
        this.strutturaList = strutturaList;
        this.moduloStatoList = moduloStatoList;
        this.interventoAnnoList = interventoAnnoList;

        if (!this.user.isSuperUser()) {
          const value = enteList
            .filter(
              (e) => e.enteCodEsteso === this.user.getUser().codiceAzienda
            )
            .map((e) => e.enteId);

          const ente = this.searchForm.get('ente')!;
          ente.setValue(value as any);
          ente.disable();
        }

        if (this.isRicercaStoricoInterventi()) {
          const value = interventoStatoList
            .filter((is) => is.intStatoCod === 'COLL')
            .map((is) => is.intStatoId);

          const interventoStato = this.searchForm.get('interventoStato')!;
          interventoStato.setValue(value as any);
          interventoStato.disable();
        }

        if (this.isProgrammazioneInterventi()) {
          this.interventoAnnoList = [annoCorrente];
        }

        //
        this.route.queryParams.subscribe((params) => {
          if (params['reload'] === 'last') {
            const savedState = localStorage.getItem(this.storageKey);
            if (savedState) {
              const state = JSON.parse(savedState);
              this.searchForm.patchValue(state.formValues); // Ripristina il form
              this.checkToSearch();
            }
          }
        });
        this.loading = false;
      }
    );

    this.searchForm.get('ente')!.valueChanges.subscribe((value: any) => {
      const struttura = this.searchForm.get('struttura')!;
      value?.length ? struttura.enable() : struttura.disable();
    });

    this.searchForm
      .get('finanziamentoTipo')!
      .valueChanges.subscribe((value: any) => {
        const finanziamentoTipoDet = this.searchForm.get(
          'finanziamentoTipoDet'
        )!;
        value?.length
          ? finanziamentoTipoDet.enable()
          : finanziamentoTipoDet.disable();
      });
  }

  checkToSearch() {
    if (this.expansionPanel) {
      this.submit();
    } else {
      setTimeout(() => this.checkToSearch(), 100);
    }
  }

  renderSelectAll(select: MatSelect, opened: boolean) {
    const selectAll = select.options.find((o) => o.value === '*')!;
    const options = select.options.filter((o) => o.value !== '*');

    const allSelected = options.filter((o) => !o.selected).length === 0;

    opened && allSelected ? selectAll.select() : selectAll.deselect();
  }

  selectAll(select: MatSelect) {
    const selectAll = select.options.find((o) => o.value === '*')!;
    const options = select.options.filter((o) => o.value !== '*');

    const allSelected = options.filter((o) => !o.selected).length === 0;

    options.forEach((o) => (allSelected ? o.deselect() : o.select()));
    allSelected ? selectAll.deselect() : selectAll.select();
  }

  getStrutturaListByEnte(): Struttura[] {
    const ente = this.searchForm.getRawValue().ente ?? [];
    return this.strutturaList.filter((s) => (ente as any).includes(s.enteId));
  }

  getFinanziamentoTipoDetListByTipo(): FinanziamentoTipoDet[] {
    const finanziamentoTipo = this.searchForm.value.finanziamentoTipo ?? [];
    return this.finanziamentoTipoDetList.filter((ftd) =>
      (finanziamentoTipo as any).includes(ftd.finTipoId)
    );
  }

  getAziendaLabelByEnteId(row: RicercaInterventiResult): string {
    return getAziendaLabelByEnteId(row.enteId, this.enteList);
  }

  getStatoInterventoLabelByStatoId(row: RicercaInterventiResult): string {
    return getStatoInterventoByStatoIdLabel(
      row.intStatoId,
      this.interventoStatoList
    );
  }

  isFinanziamentiVisibili(statoIntervento: string) {
    return (
      this.checkSuperUser() ||
      (!this.checkSuperUser() &&
        !this.checkStatoInserito(statoIntervento) &&
        !this.checkStatoProposto(statoIntervento))
    );
  }

  getExportHeaders(): string[] {
    return [
      'Azienda',
      'CUP',
      'Codice PGMEAS',
      'Titolo',
      'Stato Intervento',
      'Importo complessivo (€)',
      // 'Fin. Regionali disponibili (€)',
      // 'Fin. Statali disponibili (€)',
      // 'Altri fin. disponibili (€)',
    ];
  }

  getExportData(): Observable<any[][]> {
    const interventiFilter: InterventiFilter = {
      anno: this.searchForm.get('anno')?.value,
      cup: this.searchForm.get('cup')?.value,
      titolo: this.searchForm.get('titolo')?.value,
      codPgmeas: this.searchForm.get('codicePgmeas')?.value,
      obiettivi: this.searchForm.get('interventoObiettivo')?.value,
      finalita: this.searchForm.get('interventoFinalita')?.value,
      categorie: this.searchForm.get('interventoCategoria')?.value,
      stati: this.searchForm.get('interventoStato')?.value,
      statiProgettuali: this.searchForm.get('interventoStatoProgettuale')
        ?.value,
      tipi: this.searchForm.get('interventoTipo')?.value,
      appaltiTipo: this.searchForm.get('interventoAppaltoTipo')?.value,
      contrattiTipo: this.searchForm.get('interventoContrattoTipo')?.value,
      aziende: this.searchForm.get('ente')?.value,
      strutture: this.searchForm.get('struttura')?.value,
      soloInterventiFinanziati: this.searchForm.get('soloInterventiFinanziati')
        ?.value,
      finanziamenti: this.searchForm.get('finanziamentoTipo')?.value,
      finanziamentiTipo: this.searchForm.get('finanziamentoTipoDet')?.value,
      orderBy: this.isOrderValid() ? this.orderBy : null,
      orderDirection: this.isOrderValid() ? this.orderDirection : null,
      pageNumber: this.pageNumber,
      rowPerPage: this.rowPerPage,
    };
    return this.projectApiService
      .getInterventiByAllFilters(interventiFilter, this.pgmeasContext)
      .pipe(
        map((response) => {
          if (!Array.isArray(response)) {
            console.error('Errore: la risposta non è un array', response);
            return [];
          }

          return response.map((intervento) => [
            this.getAziendaLabelByEnteId(intervento),
            intervento.intCup,
            intervento.intCod,
            intervento.intTitolo,
            this.getStatoInterventoLabelByStatoId(intervento),
            intervento.intImporto,
            // intervento.finanziamentiRegionali,
            // intervento.finanziamentiStatali,
            // intervento.altriFinanziamenti,
          ]);
        }),
        catchError((err) => {
          console.error(err);
          return of([]); // Restituisce una lista vuota in caso di errore
        })
      );
  }

  getExportFilename(): string {
    return `interventi-${this.searchForm.value.anno}-${Date.now()}`;
  }

  downloadExcel() {
    this.getExportData().subscribe((data) => {
      console.log(data);
      const aoa = [this.getExportHeaders(), ...data];
      const ws = XLSX.utils.aoa_to_sheet(aoa);
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws);
      XLSX.writeFile(wb, `${this.getExportFilename()}.xlsx`);
    });
  }
  downloadPdf() {
    this.getExportData().subscribe((data) => {
      const doc = new jsPDF({ orientation: 'landscape' });

      autoTable(doc, {
        head: [this.getExportHeaders()],
        body: data, // I dati vengono recuperati in modo asincrono
      });

      doc.save(`${this.getExportFilename()}.pdf`);
    });
  }

  isFilterClearable(filter: SearchFilter): boolean {
    switch (filter.name) {
      case 'ente':
        return this.user.isSuperUser();
      case 'interventoStato':
        return !this.isRicercaStoricoInterventi();
      case 'anno':
        return !this.isProgrammazioneInterventi();
      default:
        return true;
    }
  }

  clearFilter(filter: SearchFilter) {
    this.searchForm.get(filter.name)!.setValue(null);
    this.submit();
  }

  checkButtonRespingiModuloARegione(moduloStato: number) {
    let flag = false;

    if (moduloStato != null) {
      //2=PRESENTATO
      if (this.isModuloStatoPresentato(moduloStato)) {
        flag = true;
      }
    } else {
      flag = false;
    }

    return flag;
  }

  checkButtonApprovaModuloARegione(moduloStato: number) {
    let flag = false;

    if (moduloStato != null) {
      //2=PRESENTATO
      if (this.isModuloStatoPresentato(moduloStato)) {
        flag = true;
      }
    } else {
      flag = false;
    }

    return flag;
  }

  checkProgrammazioneAperta() {
    let programmazioneAperta =
      this.user.getUser().programmazione.programmazioneAperta;
    return programmazioneAperta;
  }

  checkButtonInviaModuloARegione(moduloStato: number) {
    let flag = false;
    if (moduloStato != null) {
      if (this.isModuloStatoInserito(moduloStato)) {
        flag = true;
      }
    }
    return flag;
  }

  checkButtonModificaModuloAReg(moduloStato: number) {
    let flag = false;
    if (moduloStato != null) {
      if (this.isModuloStatoPresentato(moduloStato)) {
        flag = true;
      }
    }
    return flag;
  }

  checkButtonVisualizzaApprovato(moduloStato: number) {
    let flag = false;
    if (moduloStato != null) {
      if (this.isModuloStatoApprovato(moduloStato)) {
        flag = true;
      }
    }
    return flag;
  }

  checkButtonModificaModuloAASR(moduloStato: number) {
    let flag = false;
    if (moduloStato != null) {
      if (this.isModuloStatoInserito(moduloStato)) {
        flag = true;
      }
    }
    return flag;
  }

  checkButtonInviaInterventoARegione(
    row: RicercaInterventiResult,
    statoIntervento: string
  ) {
    let flag = false;

    let programmazioneAperta =
      this.user.getUser().programmazione.programmazioneAperta;
    let checkAnnoInserimento = false;
    let checkStatoIntervento = this.checkStatoInserito(statoIntervento);

    //intervento ha anno di inserimento uguale a anno corrente
    const currentYear: number = new Date().getFullYear();
    if (row.intAnno === currentYear) {
      checkAnnoInserimento = true;
    }

    if (programmazioneAperta && checkAnnoInserimento && checkStatoIntervento) {
      flag = true;
    }
    return flag;
  }

  checkButtonApprovaInterventoRegione(
    row: RicercaInterventiResult,
    statoIntervento: string
  ) {
    let flag = false;

    let checkStatoIntervento = this.checkStatoProposto(statoIntervento);
    let checkAnnoInserimento = false;
    //intervento ha anno di inserimento uguale a anno corrente
    const currentYear: number = new Date().getFullYear();
    if (row.intAnno === currentYear) {
      checkAnnoInserimento = true;
    }

    if (checkAnnoInserimento && checkStatoIntervento) {
      flag = true;
    }
    return flag;
  }

  checkButtonRespingiInterventoRegione(
    row: RicercaInterventiResult,
    statoIntervento: string
  ) {
    let flag = false;

    let checkStatoIntervento = this.checkStatoProposto(statoIntervento);
    let checkAnnoInserimento = false;
    //intervento ha anno di inserimento uguale a anno corrente
    const currentYear: number = new Date().getFullYear();
    if (row.intAnno === currentYear) {
      checkAnnoInserimento = true;
    }

    if (checkAnnoInserimento && checkStatoIntervento) {
      flag = true;
    }
    return flag;
  }

  checkButtonEliminaInterventoAsr(
    row: RicercaInterventiResult,
    statoIntervento: string
  ) {
    let flag = false;

    let programmazioneAperta =
      this.user.getUser().programmazione.programmazioneAperta;
    let checkStatoIntervento = this.checkStatoInserito(statoIntervento);
    let checkAnnoInserimento = false;
    //intervento ha anno di inserimento uguale a anno corrente
    const currentYear: number = new Date().getFullYear();
    if (row.intAnno === currentYear) {
      checkAnnoInserimento = true;
    }

    if (checkAnnoInserimento && checkStatoIntervento && programmazioneAperta) {
      flag = true;
    }
    return flag;
  }

  checkButtonModificaInterventoRegione(
    row: RicercaInterventiResult,
    statoIntervento: string
  ) {
    let flag = false;
    let checkStatoIntervento = this.checkStatoProposto(statoIntervento);
    let checkAnnoInserimento = false;
    //intervento ha anno di inserimento uguale a anno corrente
    const currentYear: number = new Date().getFullYear();
    if (row.intAnno === currentYear) {
      checkAnnoInserimento = true;
    }

    if (checkAnnoInserimento && checkStatoIntervento) {
      flag = true;
    }
    return flag;
  }

  checkButtonInsModModuloAAsr(
    row: RicercaInterventiResult,
    statoIntervento: string
  ) {
    let flag = false;
    let checkStatoIntervento = this.checkStatoFinanziabile(statoIntervento);
    let checkAnnoInserimento = false;
    //intervento ha anno di inserimento uguale a anno corrente
    const currentYear: number = new Date().getFullYear();
    if (row.intAnno === currentYear) {
      checkAnnoInserimento = true;
    }

    if (checkAnnoInserimento && checkStatoIntervento) {
      flag = true;
    }
    return flag;
  }

  checkButtonModificaInterventoAsr(
    row: RicercaInterventiResult,
    statoIntervento: string
  ) {
    let flag = false;
    let programmazioneAperta =
      this.user.getUser().programmazione.programmazioneAperta;
    let checkStatoIntervento = this.checkStatoInserito(statoIntervento);
    let checkAnnoInserimento = false;
    //intervento ha anno di inserimento uguale a anno corrente
    const currentYear: number = new Date().getFullYear();
    if (row.intAnno === currentYear) {
      checkAnnoInserimento = true;
    }

    if (programmazioneAperta && checkAnnoInserimento && checkStatoIntervento) {
      flag = true;
    }
    return flag;
  }

  apriModaleRegioneRespingeModuloA(rIntModuloAId: number, intId: number) {
    const dialogRef = this.dialog.open(ModaleAzioneFinanziamentoComponent, {
      width: '600px',
      disableClose: true, // Impedisce la chiusura al click esterno
      data: {
        titolo: 'Respingi richiesta ammissione al finanziamento',
        messaggio: 'Attenzione! Confermi il respingimento?',
        operazione: 'RESPINGIRICHIESTA',
        rIntModuloAId: rIntModuloAId,
        intId: intId,
        risultato: 'La richiesta ammissione finanziamento è stata respinta.',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //se true fa chiamata
        this.submit();
      }
    });
  }

  apriModaleRegioneApprovaModuloA(rIntModuloAId: number, intId: number) {
    const dialogRef = this.dialog.open(ModaleAzioneFinanziamentoComponent, {
      width: '600px',
      disableClose: true, // Impedisce la chiusura al click esterno
      data: {
        titolo: 'Approva richiesta ammissione al finanziamento',
        messaggio:
          "Attenzione! Stai per confermare l'approvazione della richiesta di ammissione al finanziamento.",
        operazione: 'APPROVARICHIESTA',
        rIntModuloAId: rIntModuloAId,
        intId: intId,
        risultato:
          'La richiesta di ammissione al finanziamento è stata correttamente approvata.',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //se true fa chiamata
        this.submit();
      }
    });
  }

  apriModaleInviaRegioneModuloA(rIntModuloAId: number, intId: number) {
    const dialogRef = this.dialog.open(ModaleAzioneFinanziamentoComponent, {
      width: '600px',
      disableClose: true, // Impedisce la chiusura al click esterno
      data: {
        titolo: 'Invio richiesta ammissione finanziamento a Regione',
        messaggio:
          "Attenzione! Dopo la conferma dell'invio a Regione Piemonte, la richiesta ammissione al finanziamento sarà consultabile in sola lettura",
        operazione: 'INVIAMODULOAAREGIONE',
        rIntModuloAId: rIntModuloAId,
        intId: intId,
        risultato:
          'La richiesta di ammissione al finanziamento è stata correttamente inviata a Regione Piemonte. Da questo momento sarà consultabile in sola lettura',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //se true fa chiamata
        this.submit();
      }
    });
  }

  apriModaleConfermaInvioRegione(intId: number) {
    const dialogRef = this.dialog.open(ModaleAzioneComponent, {
      width: '600px',
      disableClose: true, // Impedisce la chiusura al click esterno
      data: {
        titolo: 'Invia intervento a Regione Piemonte',
        messaggio:
          "Attenzione! Dopo la conferma dell'invio a Regione Piemonte l'intervento sarà consultabile in sola lettura.",
        operazione: 'INVIAREGIONE',
        intId: intId,
        risultato:
          "L'intervento è stato correttamente inviato a Regione Piemonte. Da questo momento sarà consultabile in sola lettura.",
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //se true fa chiamata
        this.submit();
      }
    });
  }

  apriModaleApprovaIntervento(intId: number) {
    const dialogRef = this.dialog.open(ModaleAzioneComponent, {
      width: '600px',
      disableClose: true, // Impedisce la chiusura al click esterno
      data: {
        titolo: 'Approva Intervento',
        messaggio:
          "Attenzione! Dopo l'approvazione l'intervento sarà consultabile in sola lettura.",
        operazione: 'APPROVAINTERVENTO',
        intId: intId,
        risultato:
          "L'intervento è stato correttamente approvato ed è stata inviata una notifica all'ASR di riferimento.",
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //se true fa chiamata
        this.submit();
      }
    });
  }

  apriModaleRespingiIntervento(intId: number) {
    const dialogRef = this.dialog.open(ModaleAzioneComponent, {
      width: '600px',
      disableClose: true, // Impedisce la chiusura al click esterno
      data: {
        titolo: 'Invia ad ASR',
        messaggio:
          "Attenzione! Dopo l'invio all'ASR l'intervento sarà consultabile in sola lettura e verrà inviata una notifica all'ASR di competenza.",
        operazione: 'RESPINGIINTERVENTO',
        intId: intId,
        risultato:
          "L'intervento è stato correttamente respinto ed è stata inviata una notifica all'ASR di riferimento.",
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //se true fa chiamata
        this.submit();
      }
    });
  }

  apriModaleEliminaIntervento(intId: number) {
    const dialogRef = this.dialog.open(ModaleAzioneComponent, {
      width: '600px',
      disableClose: true, // Impedisce la chiusura al click esterno
      data: {
        titolo: 'Elimina intervento',
        messaggio:
          "Attenzione! Se confermi l'operazione l'intervento verrà eliminato e non sarà più visibile.",
        operazione: 'ELIMINAINTERVENTO',
        intId: intId,
        risultato: "L'intervento è stato correttamente eliminato.",
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //se true fa chiamata
        this.submit();
      }
    });
  }

  checkSuperUser(): boolean {
    return this.user.isSuperUser();
  }

  checkDirigenteRegionale(): boolean {
    return this.user.isDirigenteRegionale();
  }

  // FINANZIABILE PROPOSTO INSERITO
  checkStatoInserito(statoIntervento: string): boolean {
    return 'INSERITO' === statoIntervento;
  }

  checkStatoProposto(statoIntervento: string): boolean {
    return 'PROPOSTO' === statoIntervento;
  }

  checkStatoFinanziabile(statoIntervento: string): boolean {
    return 'FINANZIABILE' === statoIntervento;
  }

  isRicercaInterventi(): boolean {
    return PgmeasContextEnum.RICERCA_INTERVENTI === this.pgmeasContext;
  }

  isProgrammazioneInterventi(): boolean {
    return PgmeasContextEnum.PROGRAMMAZIONE_INTERVENTI === this.pgmeasContext;
  }

  isGestioneInterventi(): boolean {
    return PgmeasContextEnum.GESTIONE_INTERVENTI === this.pgmeasContext;
  }

  isMonitoraggio(): boolean {
    return PgmeasContextEnum.MONITORAGGIO === this.pgmeasContext;
  }

  isModuloStatoPresentato(statoModulo: number) {
    const stato = this.moduloStatoList.find(
      (stato) => stato.moduloStatoId === statoModulo
    );
    return stato?.moduloStatoCod === 'PRES';
  }

  isModuloStatoInserito(statoModulo: number) {
    const stato = this.moduloStatoList.find(
      (stato) => stato.moduloStatoId === statoModulo
    );
    return stato?.moduloStatoCod === 'INS';
  }

  isModuloStatoApprovato(statoModulo: number) {
    const stato = this.moduloStatoList.find(
      (stato) => stato.moduloStatoId === statoModulo
    );
    return stato?.moduloStatoCod === 'APPR';
  }

  checkMoreBottoniPresenti(menuA: MatMenu): boolean {
    const menuItems = menuA._directDescendantItems.toArray();
    // Check if any menu item is not disabled
    const showMoreButton = menuItems.some((item) => !item.disabled);

    return showMoreButton;
  }
  reset() {
    const annoCorrente = new Date().getFullYear();
    this.searchForm = this.fb.group({
      anno: [
        this.isProgrammazioneInterventi() ? annoCorrente : null,
        Validators.required,
      ],
      cup: [null],
      codicePgmeas: [null],
      interventoObiettivo: [[]],
      interventoFinalita: [[]],
      interventoCategoria: [[]],
      interventoStato: [[]],
      interventoStatoProgettuale: [[]],
      strutturaTipo: [[]],
      interventoTipo: [[]],
      interventoAppaltoTipo: [[]],
      interventoContrattoTipo: [[]],
      ente: [[]],
      struttura: [{ value: [], disabled: true }],
      soloInterventiFinanziati: [false],
      finanziamentoTipo: [[]],
      finanziamentoTipoDet: [{ value: [], disabled: true }],
    });
  }

  pageEvent: PageEvent;

  sortData(sort: Sort) {
    this.orderBy = sort.active;
    this.orderDirection = sort.direction;
    this.matSortVal = this.dataSource.sort;
    this.submit();
  }

  handlePageEvent(e: PageEvent) {
    //OFFSET
    // this.pageNumber=e.pageSize*e.pageIndex;
    // LIMIT
    // this.rowPerPage =this.pageNumber+e.pageSize;
    this.pageNumber = e.pageIndex;
    this.rowPerPage = e.pageSize;

    this.submit();
  }

  submit() {
    if (this.searchForm.invalid) {
      this.tableLoading = 'NOT_SHOWN';
      this.expansionPanel.open();
      this.message.error('Riempire i campi obbligatori e riprovare');
      return;
    }

    if (this.expansionPanel) {
      this.expansionPanel.close();
    }

    this.tableLoading = 'LOADING';

    const value = this.searchForm.getRawValue() as any;
    const componentState = {
      formValues: this.searchForm.getRawValue(),
      lastUrl: this.router.url,
    };

    localStorage.setItem(this.storageKey, JSON.stringify(componentState));

    const interventiFilter: InterventiFilter = {
      anno: this.searchForm.get('anno')?.value,
      cup: this.searchForm.get('cup')?.value,
      titolo: this.searchForm.get('titolo')?.value,
      codPgmeas: this.searchForm.get('codicePgmeas')?.value,
      obiettivi: this.searchForm.get('interventoObiettivo')?.value,
      finalita: this.searchForm.get('interventoFinalita')?.value,
      categorie: this.searchForm.get('interventoCategoria')?.value,
      stati: this.searchForm.get('interventoStato')?.value,
      statiProgettuali: this.searchForm.get('interventoStatoProgettuale')
        ?.value,
      tipi: this.searchForm.get('interventoTipo')?.value,
      appaltiTipo: this.searchForm.get('interventoAppaltoTipo')?.value,
      contrattiTipo: this.searchForm.get('interventoContrattoTipo')?.value,
      aziende: this.searchForm.get('ente')?.value,
      strutture: this.searchForm.get('struttura')?.value,
      soloInterventiFinanziati: this.searchForm.get('soloInterventiFinanziati')
        ?.value,
      finanziamenti: this.searchForm.get('finanziamentoTipo')?.value,
      finanziamentiTipo: this.searchForm.get('finanziamentoTipoDet')?.value,
      orderBy: this.isOrderValid() ? this.orderBy : null,
      orderDirection: this.isOrderValid() ? this.orderDirection : null,
      pageNumber: this.pageNumber,
      rowPerPage: this.rowPerPage,
    };
    this.projectApiService
      .getInterventiByAllFilters(interventiFilter, this.pgmeasContext)
      .pipe(
        tap(() => {
          this.searchFilters = Object.entries(this.searchForm.controls)
            .filter(
              ([_, control]) =>
                (Array.isArray(control.value) && control.value.length) ||
                (!Array.isArray(control.value) && control.value)
            )
            .map(([name, control]) => {
              const schema = this.searchFilterSchema[name];

              let shownValues: string;
              let tooltip: string;

              if (Array.isArray(control.value)) {
                const values = control.value.map((v) =>
                  schema.getValueLabel(v)
                );

                if (control.value.length > 3) {
                  shownValues = values.slice(0, 3).join(', ') + ' e altri';
                  tooltip = values.join(', ');
                } else {
                  shownValues = values.join(', ');
                  tooltip = '';
                }
              } else {
                shownValues = schema.getValueLabel(control.value);
                tooltip = '';
              }

              return {
                name,
                label: schema.nameLabel,
                shownValues,
                tooltip,
              };
            });
        }),
        tap((interventoList) => {
          if (
            interventoList &&
            interventoList.length &&
            interventoList.length > 0
          ) {
            this.total = interventoList[0].total;
          } else {
            this.total = 0;
          }
        }),
        tap((response) => {
          this.dataSource.data = response.map((intervento) => ({
            ...intervento,
            azienda: this.getAziendaLabelByEnteId(intervento),
            statoIntervento: this.getStatoInterventoLabelByStatoId(intervento),
          }));
        }),
        catchError((err) => {
          console.error(err);
          return of([]); // Restituisce una lista vuota in caso di errore
        }),
        tap(() => (this.tableLoading = 'SHOWN')) // Side effect alla fine
      )
      .subscribe();

    this.dataSource.sort = this.matSortVal;
  }

  isOrderValid() {
    const isOrderValid = this.orderBy && this.orderDirection;
    return isOrderValid;
  }
}
