/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ModaleStruttureComponent } from '../modale-strutture/modale-strutture.component';
import { FormArray } from '@angular/forms';
import { Observable } from 'rxjs';
import { Struttura } from '@pgmeas-library/model';

@Component({
  selector: 'app-add-strutture',
  templateUrl: './add-strutture.component.html',
  styleUrls: ['./add-strutture.component.scss']
})
export class AddStruttureComponent {

  @Input() formStrutture!: FormArray;
  @Input() enteId!: number;
  @Input() strutturaFilteredList!: Observable<Struttura[]>;
  @Output() strutturaAggiunta: EventEmitter<Struttura> = new EventEmitter<Struttura>(); // Output per notificare il componente genitore

  constructor(
    public dialog: MatDialog
  ) {}

  openAddStruttureModal() {
    const dialogRef = this.dialog.open(ModaleStruttureComponent, {
      width: '100%',
      data: { formStrutture: this.formStrutture, enteId: this.enteId, struttureFilteredList: this.strutturaFilteredList }
    });

    dialogRef.afterClosed().subscribe((result: Struttura | undefined) => {
      this.strutturaAggiunta.emit(result); // Emetti la struttura aggiunta al genitore
    });
  }

}
