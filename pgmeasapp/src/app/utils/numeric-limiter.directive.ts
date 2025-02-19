/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/

import { Directive, ElementRef, HostListener, Input, Renderer2 } from "@angular/core";

@Directive({
  selector: '[appNumericLimiter]'
})
export class NumericLimiterDirective {
  @Input('appNumericLimiter') maxLength!: number; // Accetta la lunghezza massima come input

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  @HostListener('input', ['$event']) onInputChange(event: any) {
    const input = event.target;
    let value = input.value;

    // Permette solo numeri e tronca se supera la lunghezza massima
    if (value.length > this.maxLength) {
      value = value.slice(0, this.maxLength);
      this.renderer.setProperty(this.el.nativeElement, 'value', value);
    }

    // Disabilita l'input se la lunghezza massima è raggiunta
    if (value.length >= this.maxLength) {
      this.el.nativeElement.disabled = true;
      setTimeout(() => this.el.nativeElement.disabled = false, 100); // Rimuove il disabilitato dopo un breve ritardo
    }
  }
}
