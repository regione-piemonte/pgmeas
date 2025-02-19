/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller;

import static it.csi.pgmeas.commons.util.HeadersUtils.getUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.csi.pgmeas.commons.base.BaseApiController;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.CommonConstants;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class LoginController extends BaseApiController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Value("${IrideIdAdapterFilter.devmode:false}")
	private boolean devmode;

	@Value("${logout.url:#}")
	private String logoutUrl;

	@GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(HttpServletRequest httpRequest) {
		httpRequest.getSession().invalidate();
		// return ResponseEntity.ok(new MessageRecord("logout eseguito"));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", logoutUrl);
		return new ResponseEntity<String>(headers, HttpStatus.FOUND);

	}

//	http://localhost:9090/pgmeasapigateway/user/info?lcceToken=1
	@GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfoRecord> info(HttpServletRequest httpRequest) throws CustomLoginException {
		UserInfoRecord user = getUser(httpRequest);
		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "/tohome", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> redirectToHome(@RequestParam(CommonConstants.LCCE_TOKEN) String lcceToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", String.format("/?%s=%s", CommonConstants.LCCE_TOKEN, lcceToken));
		return new ResponseEntity<String>(headers, HttpStatus.FOUND);
	}

}
