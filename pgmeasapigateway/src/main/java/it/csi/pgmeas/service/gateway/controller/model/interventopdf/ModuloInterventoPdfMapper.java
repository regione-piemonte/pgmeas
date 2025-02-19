/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.interventopdf;

import static it.csi.pgmeas.commons.util.ProfileUtils.checkIfAsr;
import static it.csi.pgmeas.commons.util.ProfileUtils.checkIfRegione;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpHeaders;

import it.csi.pgmeas.commons.dto.ClassificazioneTreeByClassTsTipoDto;
import it.csi.pgmeas.commons.dto.DichAppaltabilitaDto;
import it.csi.pgmeas.commons.dto.v2.ModuloIntervento;
import it.csi.pgmeas.commons.dto.v2.ModuloInterventoStruttura;
import it.csi.pgmeas.commons.exception.CustomLoginException;
import it.csi.pgmeas.commons.util.enumeration.InterventoStatoEnum;
import it.csi.pgmeas.commons.util.enumeration.ModuloTipoEnum;
import it.csi.pgmeas.commons.util.record.UserInfoRecord;
import it.csi.pgmeas.service.gateway.controller.model.interventopdf.util.PdfMapper;
import it.csi.pgmeas.service.gateway.exception.ApiGatewayCustomException;
import it.csi.pgmeas.service.gateway.proxy.utils.RestClient;

public class ModuloInterventoPdfMapper extends PdfMapper {

	private Map<Integer, ClassificazioneTreeByClassTsTipoDto> classificazioneTreeDAMap;
	private Map<Integer, ClassificazioneTreeByClassTsTipoDto> classificazioneTreeDAAMap;

	public InterventoPdf getModuloInterventoPdf(ModuloIntervento dto, ModuloInterventoStruttura[] interventoStruttura,
			UserInfoRecord userInfo) throws ApiGatewayCustomException, CustomLoginException {
		InterventoPdf interventoPdf = getGeneralInterventoPdf(dto, interventoStruttura, userInfo);

		String stato = buildDecode(statiMap, dto.getStato());
		boolean flgVisualizzaCampiRegione = checkIfRegione(userInfo)
				|| (checkIfAsr(userInfo) && 
						stato.equals(InterventoStatoEnum.AMMESSO_AL_FINANZIAMENTO.getCode()));
		interventoPdf.setFlgVisualizzaCampiRegione(flgVisualizzaCampiRegione);
		
		if (interventoStruttura != null) {
			List<InterventoStrutturaPdf> ispdf = Arrays.asList(interventoStruttura).stream()
					.map(s -> getInterventoStrutturaPdf(s, dto)) 
					.toList();
			interventoPdf.setInterventoStrutturaPdf(ispdf);
		}
		
		interventoPdf.setAllegatoProvvedimentoAziendaleApprovazione(dto.getAllegatoProvvedimentoAziendaleApprovazione());
		interventoPdf.setAllegatoRelazioneTecnica(dto.getAllegatoRelazioneTecnica());
		interventoPdf.setAllegatoNullaOsta(dto.getAllegatoNullaOsta());
		interventoPdf.setAllegatoDecretoMinisteriale(dto.getAllegatoDecretoMinisteriale());
		interventoPdf.setModuloTipo(dto.getModuloTipo());
		
		return interventoPdf;
	}

	public InterventoStrutturaPdf getInterventoStrutturaPdf(ModuloInterventoStruttura dto, ModuloIntervento moduloIntervento) {
		InterventoStrutturaPdf pdf = getGeneralInterventoStrutturaPdf(dto);
		pdf.setDichiarazioneAppaltabilita(getDichiarazioneAppaltabilita(dto.getDichiarazioneAppaltabilita(), moduloIntervento));
		return pdf;
	}
	
	private String getDescrizione(ClassificazioneTreeByClassTsTipoDto classificazione, 
			Map<Integer, DichAppaltabilitaDto> dichiarazioneAppaltabilitaMap, Integer id, ModuloIntervento moduloIntervento) {
		String descrizione = classificazione.getDescrizione();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if(dichiarazioneAppaltabilitaMap.get(id) != null) {
			String data = dichiarazioneAppaltabilitaMap.get(id).getIntstrDaTreeValidazioneData() != null 
					? sdf.format(dichiarazioneAppaltabilitaMap.get(id).getIntstrDaTreeValidazioneData()) : "";
			
			String attoNumero = dichiarazioneAppaltabilitaMap.get(id).getAttoNumero() != null 
					? dichiarazioneAppaltabilitaMap.get(id).getAttoNumero() : "";
			
			String attrezzature = String.join(", ", buildDecode(intervTipoDettMap, moduloIntervento.getDescrizioniAttrezzature()));
			
			descrizione = descrizione.replace("\n", "<br/>");
			descrizione = descrizione.replace("{1}", data);
			descrizione = descrizione.replace("{2}", attoNumero);
			descrizione = descrizione.replace("{3}", attrezzature);
		}
		
		return descrizione;
	}

	private Map<Integer, DichiarazioneAppaltabilitaPdf> getDichiarazioneAppaltabilita(
			Map<Integer, DichAppaltabilitaDto> dichiarazioneAppaltabilitaMap, ModuloIntervento moduloIntervento) {
		Map<Integer, DichiarazioneAppaltabilitaPdf> result = new LinkedHashMap<>();

		// Popolazione della mappa interventoEdilizioPdf con i dati di
		// classificazioneTreeEIMap
		
		String moduloTipo = moduloIntervento.getModuloTipo();
		Set<Map.Entry<Integer, ClassificazioneTreeByClassTsTipoDto>> classificazioneTreeMap = moduloTipo.equals(ModuloTipoEnum.MODULO_A.getCode()) 
				? classificazioneTreeDAMap.entrySet() : classificazioneTreeDAAMap.entrySet();
		
		for (Map.Entry<Integer, ClassificazioneTreeByClassTsTipoDto> entry : classificazioneTreeMap) {
			Integer id = entry.getKey();
			ClassificazioneTreeByClassTsTipoDto classificazione = entry.getValue();

			// Crea un nuovo oggetto InterventoEdilizioPdf per ogni entry in
			// classificazioneTreeEIMap
			DichiarazioneAppaltabilitaPdf dicAppalt = new DichiarazioneAppaltabilitaPdf();
			dicAppalt.setId(id);
			String descrizione = getDescrizione(classificazione, dichiarazioneAppaltabilitaMap, id, moduloIntervento);
			
			dicAppalt.setDescrizione(descrizione);
			dicAppalt.setLivello(classificazione.getLivello());
			dicAppalt.setEditabile(classificazione.getSelezionabile()); //TODO

			// Se l'ID è presente in interventoEdilizioDto, imposta setSelezionato su
			// true/false
			// altrimenti imposta setSelezionato su false
			Boolean selezionato = dichiarazioneAppaltabilitaMap.size() > 0 ? 
					dichiarazioneAppaltabilitaMap.get(id).getIntstrDaTreeSelezionata() : null;
			if (selezionato != null) {
				dicAppalt.setSelezionato(selezionato);
			} else {
				dicAppalt.setSelezionato(false); // Imposta su false se l'ID non è presente
			}
			if(dichiarazioneAppaltabilitaMap.size() > 0 && dichiarazioneAppaltabilitaMap.get(id).getIntstrDaTreeValidazioneData()!=null) {
				dicAppalt.setValidazioneData(dichiarazioneAppaltabilitaMap.get(id).getIntstrDaTreeValidazioneData());
			}
			// Aggiungi l'oggetto alla mappa
			result.put(id, dicAppalt);
		}
			return result;
	}

	public void initDecode(RestClient client, HttpHeaders headers) throws URISyntaxException {
		initGeneralDecode(client, headers);
		classificazioneTreeDAMap = client.getLinkedHashMap(ClassificazioneTreeByClassTsTipoDto[].class, headers,
				"/api/tipologiche/classifTree/DA", //
				ClassificazioneTreeByClassTsTipoDto::getClassifTreeId);
		classificazioneTreeDAAMap = client.getLinkedHashMap(ClassificazioneTreeByClassTsTipoDto[].class, headers,
				"/api/tipologiche/classifTree/DAA", //
				ClassificazioneTreeByClassTsTipoDto::getClassifTreeId);
	}

}
