/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller;

import static it.csi.pgmeas.commons.util.HeadersUtils.getHttpHeaders;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.csi.pgmeas.commons.dto.AllegatoDto;
import it.csi.pgmeas.commons.dto.EnteDto;
import it.csi.pgmeas.commons.dto.FinanziamentoLiquidazioneDto;
import it.csi.pgmeas.commons.dto.FinanziamentoLiquidazioneRichiestaDto;
import it.csi.pgmeas.commons.dto.FinanziamentoResultDto;
import it.csi.pgmeas.commons.dto.FinanziamentoTipoDetDto;
import it.csi.pgmeas.commons.dto.FinanziamentoTipoDto;
import it.csi.pgmeas.commons.dto.InterventoFinanziamentoPrevSpesaDto;
import it.csi.pgmeas.commons.dto.InterventoGaraAppaltoDto2;
import it.csi.pgmeas.commons.dto.InterventoResultDto;
import it.csi.pgmeas.commons.dto.InterventoStatoProgettualeDto;
import it.csi.pgmeas.commons.dto.InterventoStrutturaDto;
import it.csi.pgmeas.commons.dto.MonitoraggioDto;
import it.csi.pgmeas.commons.dto.StrutturaDto;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.service.gateway.controller.model.pdfschedac.SchedaC;
import it.csi.pgmeas.service.gateway.controller.model.pdfschedac.SchedaCMapper;
import it.csi.pgmeas.service.gateway.controller.utils.ThymeleafTemplateProcessor;
import it.csi.pgmeas.service.gateway.exception.ApiGatewayCustomException;
import it.csi.pgmeas.service.gateway.filter.UserIdAdapterFilter;
import it.csi.pgmeas.service.gateway.proxy.utils.RestClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("schedac")
public class SchedaCController extends BaseGatewayController {
	@Value("${download.base.path}")
	private String basePath;

	private static final Logger LOG = LoggerFactory.getLogger(SchedaCController.class);

	@Autowired
	private RestClient client;

	// http://localhost:9090/pgmeasapigateway/schedac/test/2/allegato/326?lcceToken=1
	@GetMapping(value = "/test/{id}/allegato/{idAllegato}")
	public @ResponseBody void testGen(HttpServletRequest request, HttpServletResponse response,
			@PathVariable Integer id, @PathVariable Integer idAllegato) throws Exception {
		try {
			response.reset();
			// response.setBufferSize(DEFAULT_BUFFER_SIZE);
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"mod_c_word.pdf\"");

			getSchedaC(request, id, idAllegato, response.getOutputStream());
		} catch (Exception e) {
			LOG.error("[SchedaCController::testGen] ", e);
			throw e;
		}

	}

	// http://localhost:9090/pgmeasapigateway/schedac/datiMonitoraggio?lcceToken=1
	@PostMapping(value = "/datiMonitoraggio", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> inserimentoDatiMonitoraggio(@RequestBody MonitoraggioDto r,
			@RequestHeader(UserIdAdapterFilter.AUTH_ID_MARKER) String shibIdentita,
			@RequestHeader(JWT_USER_HEADER) String jwttoken, HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpHeaders headers = getHttpHeaders(request);

			AllegatoDto data = client.post(AllegatoDto.class, headers, r, //
					"/api/intervento/finanziamento/datiMonitoraggio" //
			);

			if (data == null) {
				throw new ApiGatewayCustomException(
						"[SchedaCController::inserimentoDatiMonitoraggio] nessuna risposta da datiMonitoraggio");

			}

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			Files.createDirectories(Path.of(data.getFilePath()));
			getSchedaC(request, data.getIntId(), data.getAllegatoId(), os);
			byte[] pdf = os.toByteArray();
			LOG.debug("[SchedaCController::inserimentoDatiMonitoraggio] data.getFilePath()       :: {}",
					data.getFilePath());
			LOG.debug("[SchedaCController::inserimentoDatiMonitoraggio] data.getFileNameSystem() :: {}",
					data.getFileNameSystem());

			Path path = Path.of(basePath, data.getFilePath(), data.getFileNameSystem());

			LOG.debug("[SchedaCController::inserimentoDatiMonitoraggio] path.toString() :: {}", path.toString());
			LOG.debug("[SchedaCController::inserimentoDatiMonitoraggio] path.getParent().toString() :: {}",
					path.getParent().toString());

			Files.createDirectories(path.getParent());
			Files.write(path, pdf);

			LOG.debug("[SchedaCController::inserimentoDatiMonitoraggio] file witten!");

			return ResponseEntity.ok(data);

		} catch (ApiGatewayCustomException ae) {
			LOG.error("[SchedaCController::inserimentoDatiMonitoraggio] ", ae);
			return handleApiGatewayCustomException(ae);
		} catch (Exception e) {
			LOG.error("[SchedaCController::inserimentoDatiMonitoraggio] ", e);
			return handleExceptionRE(e);
		}
	}

	private void getSchedaC(HttpServletRequest request, Integer id, Integer idAllegato, OutputStream outputStream)
			throws CustomLoginException, URISyntaxException, ApiGatewayCustomException {

		HttpHeaders headers = getHttpHeaders(request);

		SchedaCMapper mapper = new SchedaCMapper();

		mapper.setIntervento(client.get(InterventoResultDto.class, headers, //
				"/api/intervento/{id}", //
				id));

		List<AllegatoDto> allegato = Arrays.asList(client.get(AllegatoDto[].class, headers, //
				"/api/intervento/allegato/{id}", //
				id));

		Optional<AllegatoDto> allegatoInt = allegato.stream().filter(P -> P.getAllegatoId().equals(idAllegato))
				.findFirst();

		if (!allegatoInt.isPresent())
			throw new ApiGatewayCustomException("[SchedaCController::getSchedaa] manca l'allegato");

		mapper.setAllegato(allegatoInt.get());

		//

		mapper.setEnte(//
				client.get(EnteDto[].class, headers, //
						"/api/tipologiche/ente" //
				)//
		);
		mapper.setFinanziamentoTipoDet(//
				client.get(FinanziamentoTipoDetDto[].class, headers, //
						"/api/tipologiche/finanzTipoDet" //
				)//
		);
		mapper.setFinanziamentoTipo(//
				client.get(FinanziamentoTipoDto[].class, headers, //
						"/api/tipologiche/finanzTipo" //
				)//
		);
		mapper.setFinanziamenti(client.get(FinanziamentoResultDto[].class, headers, //
				"/api/intervento/finanziamenti/{id}", //
				id)//
		);

		mapper.setInterventoFinanziamentoPrevSpesa(client.get(InterventoFinanziamentoPrevSpesaDto[].class, headers, //
				"/api/intervento/finanziamentoPrevSpesa/{id}", //
				id)//
		);

		mapper.setFinanziamentoLiquidazioneRichiesta(client.get(FinanziamentoLiquidazioneRichiestaDto[].class, headers, //
				"/api/intervento/finanziamentoLiquidazioneRichiesta/{id}", //
				id)//
		);

		mapper.setFinanziamentoLiquidazione(client.get(FinanziamentoLiquidazioneDto[].class, headers, //
				"/api/intervento/finanziamentoLiquidazione/{id}", //
				id)//
		);

		mapper.setInterventoStruttura(client.get(InterventoStrutturaDto[].class, headers, //
				"/api/intervento/struttura/{id}", //
				id)//
		);

		// non usati::
		mapper.setStruttura(//
				client.get(StrutturaDto[].class, headers, //
						"/api/tipologiche/struttura" //
				)//
		);

		mapper.setInterventoStatoProgettuale(//
				client.get(InterventoStatoProgettualeDto[].class, headers, //
						"/api/tipologiche/intervStatoProgettuale" //
				)//
		);

		mapper.setGaraAppalto(//
				client.get(InterventoGaraAppaltoDto2[].class, headers, //
						"/api/intervento/garaAppalto/{id}", //
						id)//
		);

		// mapper.setInterventoTipo(//
		// client.exchange(InterventoTipoDto[].class, headers, //
		// "/api/tipologiche/intervTipo" //
		// )//
		// );

		// intervento.getAllegatoRichiestaAmmissioneFinanziamentoId()

		SchedaC schedac = mapper.getSchedaC();

		// do other stuff
		// byte[] file = //get your file from the location and convert it to bytes

		ThymeleafTemplateProcessor processor = new ThymeleafTemplateProcessor("mod_c_word");
		processor.setOutputStream(outputStream);
		processor.setVariable("data", schedac);

//		processor.setVariable("code", "code-for-" + name);
		processor.generatePdf();

		LOG.debug("[SchedaCController::getSchedaa] pdf generato!");

	}

}
