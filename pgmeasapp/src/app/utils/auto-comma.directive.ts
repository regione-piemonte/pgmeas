/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import {Directive, ElementRef, HostListener} from '@angular/core';
import {NgControl} from '@angular/forms';

@Directive({
  selector: '[appAutoComma]'
})
export class AutoCommaDirective {

  constructor(private el: ElementRef, private control: NgControl) {
  }

  @HostListener('input', ['$event'])
  onInput(event: Event): void {
    const input = this.el.nativeElement;
    let value = input.value.replace(/,/g, '');
    value = value.replace(/(.{10})/g, '$1,');

    if (value.endsWith(',')) {
      value = value.slice(0, -1);
    }

    // @ts-ignore
    this.control?.control.setValue(value, {emitEvent: false});
  }

}
