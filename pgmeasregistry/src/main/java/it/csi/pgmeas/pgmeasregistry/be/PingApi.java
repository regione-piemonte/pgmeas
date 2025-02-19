/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.pgmeasregistry.be;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ping")
public interface PingApi  {
   
    /**
     * summary = servizio di ping del backend
     * description = Restituisce una stringa per confermare la disponibilità del backend
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = stringa di conferma funzionamento<br>
           schema implementation = { @see Object }</p>
         </li>
         <li>    
           <p>responseCode = 200, description = Unexpected error<br>
           schema implementation = { @see String }</p>
         </li>
       </ul>
    */
    @GetMapping
	ResponseEntity<?> ping(HttpHeaders httpHeaders, HttpServletRequest httpRequest);

}
