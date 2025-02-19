/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.csi.pgmeas.service.gateway.proxy.record.ResponseMessageRecord;

@ControllerAdvice
public class MultipartExceptionHandler extends ResponseEntityExceptionHandler {

//	  @ExceptionHandler(MaxUploadSizeExceededException.class)
	//TODO da capire se funziona ancora dopo update springboot per vulnerabilità
	public ResponseEntity<ResponseMessageRecord> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exc) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new ResponseMessageRecord("File troppo grande!"));
	}

}
