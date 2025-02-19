/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Directive, ElementRef, HostListener, OnInit } from '@angular/core';

@Directive({
  selector: 'input[type="number"][appForceTwoDecimals]',
})
export class ForceTwoDecimalsDirective implements OnInit {
  constructor(private el: ElementRef<HTMLInputElement>) {}

  private updateValue() {
    if (this.el.nativeElement.value) {
      this.el.nativeElement.value =
        this.el.nativeElement.valueAsNumber.toFixed(2);
    }
  }

  ngOnInit() {
    this.updateValue();
  }

  @HostListener('blur')
  onBlur() {
    this.updateValue();
  }
}
