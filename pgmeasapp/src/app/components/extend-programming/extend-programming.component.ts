/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { GestisciProgrammazione } from '@pgmeas-library/model/src/gestisci-programmazione';
import { Ente, ProgrammazioneEnti } from '@pgmeas-library/model/src/programmazione-enti';
import { ProjectApiService } from 'src/app/services/project-api.service';
import { ModaleProgrammazioneComponent } from '../modale-programmazione/modale-programmazione.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { ModaleExtendProgrammingComponent } from '../modale-extend-programming/modale-extend-programming.component';

@Component({
  selector: 'app-extend-programming',
  templateUrl: './extend-programming.component.html',
  styleUrls: ['./extend-programming.component.scss']
})
export class ExtendProgrammingComponent {
  triennio: string;
  dataInizio: string;
  dataFine: string;
  loading: boolean = false;
  isDateDisabled: boolean = true;
  displayedColumns: string[] = ['enteCodEsteso', 'faseProrogaFine', 'actions'];
  dataSource: any;

  constructor(
    private router: Router,
    private projectApiService: ProjectApiService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    let annoCorrente = new Date().getFullYear();
    this.loading = true;
    this.projectApiService.getProgrammazioneByAnno(annoCorrente).subscribe(programmazione => {

      this.dataInizio = programmazione.faseInizio;
      this.dataFine = programmazione.faseFine;
      this.dataSource = new MatTableDataSource<Ente>;
      this.dataSource.data = programmazione.enti;
      setTimeout(() => this.dataSource.paginator = this.paginator);
      this.dataSource.sort = this.sort;

      this.loading = false;
    }, err => {
      this.loading = false;
    })
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  calcolaTriennio(): string {
    const annoCorrente = new Date().getFullYear();
    const annoInizio = annoCorrente;
    const annoFine = annoCorrente + 2;

    return `${annoInizio}-${annoFine}`;
  }

    // Funzione per limitare i caratteri a numeri e '/'
    onInputDate(event: KeyboardEvent) {
      const allowedKeys = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', 'Backspace', 'Tab', 'ArrowLeft', 'ArrowRight'];

      if (!allowedKeys.includes(event.key)) {
        event.preventDefault();
      }
    }

    back() {
      const back = history.state.back ?? '/gestisci-programmazione';
      this.router.navigateByUrl(back);
    }

    parseDate(dateString: string): Date {
      const [day, month, year] = dateString.split('/').map(Number);
      return new Date(year, month - 1, day);
    }

    apriModaleProroga(modalita: string, ente?: Ente) {
      // console.log(modalita);
      // console.log(ente);
      const dialogRef = this.dialog.open(ModaleExtendProgrammingComponent, {
        width: '600px',
        data: {
          titolo: 'Proroga programmazione',
          messaggio: modalita === 'TUTTE' ? 'La data di fine proroga vale per tutte le ASR' : 'Azienda: '+ ente?.enteDesc,
          risultato: 'La data di fine proroga per la programmazione dell\'anno in corso è stata correttamente inserita',
          modalita: modalita,
          dataInizio: this.dataInizio,
          dataFine: this.dataFine,
          ente: ente
        }
      });

      let annoCorrente = new Date().getFullYear();
      dialogRef.afterClosed().subscribe(result => {
        this.projectApiService.getProgrammazioneByAnno(annoCorrente).subscribe(programmazione => {
          this.loading = true;
          this.dataInizio = programmazione.faseInizio;
          this.dataFine = programmazione.faseFine;
          this.dataSource = new MatTableDataSource<Ente>;
          this.dataSource.data = programmazione.enti;
          setTimeout(() => this.dataSource.paginator = this.paginator);
          this.dataSource.sort = this.sort;
          this.loading = false;
        }, err => {
          this.loading = false;
        })
      });
    }


}
