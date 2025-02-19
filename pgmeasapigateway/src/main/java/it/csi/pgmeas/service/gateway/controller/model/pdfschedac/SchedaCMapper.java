/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.pdfschedac;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import it.csi.pgmeas.commons.dto.LiquidazioneDto;
import it.csi.pgmeas.commons.dto.StrutturaDto;
import it.csi.pgmeas.service.gateway.exception.ApiGatewayCustomException;

public class SchedaCMapper {
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private final DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.ITALIAN));
	private final DecimalFormat dfi = new DecimalFormat("#,##0", new DecimalFormatSymbols(Locale.ITALIAN));

	private static final Logger LOG = LoggerFactory.getLogger(SchedaCMapper.class);

	InterventoResultDto intervento;
	AllegatoDto allegato;
	private List<FinanziamentoLiquidazioneRichiestaDto> finanziamentoLiquidazioneRichiestaList;
	private List<FinanziamentoLiquidazioneDto> finanziamentoLiquidazioneList;

	List<EnteDto> ente;
	List<FinanziamentoTipoDetDto> finanziamentoTipoDet;
	List<FinanziamentoTipoDto> finanziamentoTipo;
	// List<StrutturaDto> struttura;
	List<InterventoStatoProgettualeDto> interventoStatoProgettuale;
	List<FinanziamentoResultDto> finanziamentiList;
	Map<Integer, VociPianoFinanziario> localFinanziamento = new LinkedHashMap<Integer, VociPianoFinanziario>();

	List<InterventoFinanziamentoPrevSpesaDto> interventoFinanziamentoPrevSpesaList;

	private String fonteFinanzimentoPrincipale = "";
	private List<InterventoStrutturaDto> interventoStrutturaList;
	private List<InterventoGaraAppaltoDto2> interventoGaraAppaltoList;
	private List<StrutturaDto> strutturaList;

	Map<String, BigDecimal> totaliVociPrevisioneAvanzamentoSpesaAnni = new LinkedHashMap<String, BigDecimal>();

	public SchedaC getSchedaC() throws ApiGatewayCustomException {

		EnteDto e = ente.stream().filter(E -> E.getEnteId().equals(intervento.getEnteId())).findFirst()
				.orElseThrow(ApiGatewayCustomException::new);

		FonteFinanziamento fonteFinanziamento = new FonteFinanziamento(new ArrayList<String>(),
				toString(allegato.getAllegatoProtocolloData()));

		String statoLavori = //
				this.interventoStatoProgettuale.stream() //
						.filter(si -> intervento.getListaIntStatoProgettualeId().contains(si.getIntStatoProgId())) //
						.map(InterventoStatoProgettualeDto::getIntStatoProgDesc) //
						.collect(Collectors.joining("; "));

		String codiceCIG = this.interventoGaraAppaltoList.stream() //
				.map(InterventoGaraAppaltoDto2::getGaraAppaltoCigCod) //
				.collect(Collectors.joining(", "));

		List<Presidio> presidi = interventoStrutturaList.stream().map(is -> { //

			
			//********* controllare che il legame sia ok
			StrutturaDto str = strutturaList.stream() //
					.filter(p -> p.getStrId().equals(is.getStrId())) //
					.findFirst().orElse(new StrutturaDto());

			return new Presidio( //
					/* codicePresidio */ str.getStrCod(), //
					/* nomePresidio */ str.getStrDenominazione(), //
					/* dataAperturaCantierePrevista */
					toString(is.getIntstrAperturaCantiereDataPrevista()), //
					/* dataAperturaCantiereEffettiva */ toString(is.getIntstrAperturaCantiereDataEffettiva()), //
					/* dataCollaudoPrevista */ toString(is.getIntstrCollaudoDataPrevista()), //
					/* dataCollaudoEffettiva */toString(is.getIntstrCollaudoDataEffettiva())//
			);
		}).collect(Collectors.toList());

		DatiIntervento datiIntervento = new DatiIntervento(//
				/* codiceAzienda */ e.getEnteCod(), //
				/* nomeAzienda */ e.getEnteDesc(), //
				/* interventoTitolo */ intervento.getIntTitolo(), //
				/* rupNome */ intervento.getIntReferentePraticaNome(), //
				/* rupCognome */ intervento.getIntReferentePraticaCognome(), //
				/* idInterventoProceduraRegionale */ intervento.getIntCod(), //
				/* codiceNSIS */ intervento.getIntCodicNsis(), //
				/* codiceCUP */ intervento.getIntCup(), //
				/* provvedimentiAssegnazione */ intervento.getIntDgRegionaleApprovazione(), //
				/* dataDecretoMinisteroSalute */ toString(intervento.getIntDgRegionaleApprovazioneData()), //
				/* dataAggiudicazione */ toString(intervento.getIntDgRegionaleApprovazioneData()), //
				/* importoComplessivo */ toString(intervento.getIntImporto()), //
				/* statoLavori */ statoLavori, //
				/* codiceGIG elenco Codici CIG: */ codiceCIG, //
				presidi
		//
		);

		BigDecimal totPianoFinanziario = finanziamentiList.stream() //
				.map(FinanziamentoResultDto::getFinImporto) //
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		PianoFinanziario pianoFinanziario = new PianoFinanziario(getVociPianoFinanziario(),
				toString(totPianoFinanziario));
		PrevisioneAvanzamentoSpesa previsioneAvanzamentoSpesa = new PrevisioneAvanzamentoSpesa(
				getAnniVociPrevisioneAvanzamentoSpesa(), getVociPrevisioneAvanzamentoSpesa(),
				getTotalePrevisioneSpesAnni(), //
				getTotalePrevisioneSpesa());

		RichiestaFinanziamento richiestaFinanziamentoPrincipale = new RichiestaFinanziamento(
				fonteFinanzimentoPrincipale, getRichiestaFinanziamentoPrincipale(),
				getTotaleRichiestaFinanziamentoPrincipale());

		List<String> fonti = localFinanziamento.values().stream().filter(t -> !t.finPrincipale())
				.map(VociPianoFinanziario::fonteFinanziamento).distinct().collect(Collectors.toList());

		fonteFinanziamento.fondi().addAll(//
				localFinanziamento.values().stream()//
						.map(VociPianoFinanziario::fonteFinanziamento)//
						.distinct().collect(Collectors.toList()));

		List<RichiestaFinanziamento> richiestaFinanziamento = new ArrayList<RichiestaFinanziamento>();
		fonti.forEach(fnt -> {
			richiestaFinanziamento.add(new RichiestaFinanziamento(fnt, getRichiestaFinanziamento(fnt),
					getTotaliRichiestaFinanziamento(fnt)));
		});

		TotaleLiquidato totaleLiquidato = new TotaleLiquidato(getTotaleLiquidato(), getTotaleVociTotaleLiquidato());

		ImportiSnse importiSnse = new ImportiSnse(getImportiSnse(), getTotaliVociImportiSnse());

		return new SchedaC(//
				/* FonteFinanziamento */ fonteFinanziamento, //
				/* DatiIntervento */ datiIntervento, //
				/* PianoFinanziario */ pianoFinanziario, //
				/* PrevisioneAvanzamentoSpesa */ previsioneAvanzamentoSpesa, //
				/* RichiestaFinanziamento */ richiestaFinanziamentoPrincipale, //
				/* RichiestaFinanziamento */ richiestaFinanziamento, //
				/* TotaleLiquidato */ totaleLiquidato, //
				/* ImportiSnse */ importiSnse //
		);

	}

	private VociImportiSnse getTotaliVociImportiSnse() {

		List<BigDecimal> importiPianoFinanziario = new ArrayList<BigDecimal>();
		List<BigDecimal> importiTotaliSpesi = new ArrayList<BigDecimal>();
		List<BigDecimal> importoEconomiaFinale = new ArrayList<BigDecimal>();

		localFinanziamento.keySet().forEach(k -> {
			List<LiquidazioneDto> liqs = new ArrayList<LiquidazioneDto>();

			finanziamentoLiquidazioneRichiestaList.stream().filter(f -> f.getFinId().equals(k)).forEach(el -> {
				el.getListaLiquidazione().forEach(feel -> liqs.add(feel));
			});

			BigDecimal importoFinanziamento = localFinanziamento.get(k).origImportoFinanziamento();
			importiPianoFinanziario.add(importoFinanziamento);

			BigDecimal origImportiTotaliSpesi = liqs.stream().map(LiquidazioneDto::getLiqImportoTotaleSpesoAzienda) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
			importiTotaliSpesi.add(origImportiTotaliSpesi);

			importoEconomiaFinale.add(importoFinanziamento.subtract(origImportiTotaliSpesi));

		});

		BigDecimal iTotaliSpesi = getTotale(importiTotaliSpesi);
		BigDecimal iTotalPf = getTotale(importiPianoFinanziario);
		BigDecimal bdPercAvanzamentoSpesa = iTotaliSpesi.divideToIntegralValue(iTotalPf);

		String percAvanzamentoSpesa = toStringPerc(bdPercAvanzamentoSpesa.multiply(BigDecimal.valueOf(100)));
		String percEconomia = toStringPerc(
				BigDecimal.valueOf(1).subtract(bdPercAvanzamentoSpesa).multiply(BigDecimal.valueOf(100)));

		return new VociImportiSnse(null, toString(iTotalPf), toString(iTotaliSpesi), percAvanzamentoSpesa, percEconomia,
				toStringTotali(importoEconomiaFinale));

	}

	private VociTotaleLiquidato getTotaleVociTotaleLiquidato() {
		List<BigDecimal> totaleRichieste = new ArrayList<BigDecimal>();
		List<BigDecimal> totaleLiquidato = new ArrayList<BigDecimal>();
		List<BigDecimal> totaleLiquidare = new ArrayList<BigDecimal>();
		List<BigDecimal> totaleErogato = new ArrayList<BigDecimal>();
		List<BigDecimal> totaleIncassato = new ArrayList<BigDecimal>();

		localFinanziamento.keySet().forEach(k -> {
			String fonteFinanziamento = localFinanziamento.get(k).fonteFinanziamento();
			BigDecimal iTotRichieste = finanziamentoLiquidazioneRichiestaList.stream()
					.filter(f -> f.getFinId().equals(k)) //
					.map(FinanziamentoLiquidazioneRichiestaDto::getLiqRicImporto) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

			BigDecimal iTotLiquiodato = finanziamentoLiquidazioneList.stream().filter(f -> f.getFinId().equals(k)) //
					.map(FinanziamentoLiquidazioneDto::getLiqImporto) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

			totaleRichieste.add(iTotRichieste);
			totaleLiquidato.add(iTotLiquiodato);

			totaleLiquidare.add(iTotRichieste.subtract(iTotLiquiodato));

			List<LiquidazioneDto> liqs = new ArrayList<LiquidazioneDto>();

			finanziamentoLiquidazioneRichiestaList.stream().filter(f -> f.getFinId().equals(k)).forEach(el -> {
				el.getListaLiquidazione().forEach(feel -> liqs.add(feel));
			});

			totaleErogato.add(liqs.stream().map(LiquidazioneDto::getLiqImportoErogato) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));

			totaleIncassato.add(liqs.stream().map(LiquidazioneDto::getLiqImportoIncassato) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));

		});
		return new VociTotaleLiquidato(null, toStringTotali(totaleRichieste), toStringTotali(totaleLiquidato),
				toStringTotali(totaleLiquidare), toStringTotali(totaleErogato), toStringTotali(totaleIncassato));
	}

	private TotaliRichiestaFinanziamento getTotaliRichiestaFinanziamento(String fnt) {

		List<BigDecimal> totImportoRichiesta = new ArrayList<BigDecimal>();
		List<BigDecimal> totImportoLiquidato = new ArrayList<BigDecimal>();
		List<BigDecimal> totImportoLiquidare = new ArrayList<BigDecimal>();
		List<BigDecimal> totImportoErogato = new ArrayList<BigDecimal>();
		List<BigDecimal> totImportoIncassato = new ArrayList<BigDecimal>();

		finanziamentoLiquidazioneRichiestaList.forEach(f -> {
			VociPianoFinanziario fin = localFinanziamento.get(f.getFinId());
			if (fin.fonteFinanziamento().equalsIgnoreCase(fnt)) {

				totImportoErogato.add(f.getListaLiquidazione().stream().map(LiquidazioneDto::getLiqImportoErogato)
						.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));
				totImportoIncassato.add(f.getListaLiquidazione().stream().map(LiquidazioneDto::getLiqImportoIncassato)
						.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));

				BigDecimal iLiquidato = finanziamentoLiquidazioneList.stream()
						.filter(r -> r.getFinId().equals(f.getFinId())).findFirst().get().getLiqImporto();

				totImportoLiquidato.add(iLiquidato);

				totImportoRichiesta.add(f.getLiqRicImporto());

				totImportoLiquidare.add(f.getLiqRicImporto().subtract(iLiquidato));// this.getImportoTotaleDelleRichieste(finId)
																					// -
																					// this.getImportoTotaleLiquidato(finId);

			}

		});

		return new TotaliRichiestaFinanziamento(toStringTotali(totImportoRichiesta),
				toStringTotali(totImportoLiquidato), toStringTotali(totImportoLiquidare),
				toStringTotali(totImportoErogato), toStringTotali(totImportoIncassato));
	}

	private String getTotalePrevisioneSpesa() {
		BigDecimal tot = totaliVociPrevisioneAvanzamentoSpesaAnni.values().stream().reduce(BigDecimal.ZERO,
				BigDecimal::add);
		return toString(tot);
	}

	private Map<String, String> getTotalePrevisioneSpesAnni() {
		Map<String, String> values = new LinkedHashMap<String, String>();
		totaliVociPrevisioneAvanzamentoSpesaAnni.entrySet().forEach(el -> {
			values.put(el.getKey(), toString(el.getValue()));
		});
		return values;
	}

	private List<VociImportiSnse> getImportiSnse() {
		List<VociImportiSnse> impsnse = new ArrayList<>();
		localFinanziamento.keySet().forEach(k -> {
			String fonteFinanziamento = localFinanziamento.get(k).fonteFinanziamento();
			List<LiquidazioneDto> liqs = new ArrayList<LiquidazioneDto>();

			finanziamentoLiquidazioneRichiestaList.stream().filter(f -> f.getFinId().equals(k)).forEach(el -> {
				el.getListaLiquidazione().forEach(feel -> liqs.add(feel));
			});

			String importiPianoFinanziario = localFinanziamento.get(k).importoFinanziamento();
			BigDecimal importoFinanziamento = localFinanziamento.get(k).origImportoFinanziamento();

			BigDecimal origImportiTotaliSpesi = liqs.stream().map(LiquidazioneDto::getLiqImportoTotaleSpesoAzienda) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
			String importiTotaliSpesi = toString(origImportiTotaliSpesi);
			BigDecimal bdPercAvanzamentoSpesa = origImportiTotaliSpesi.divideToIntegralValue(importoFinanziamento);

			String percAvanzamentoSpesa = toStringPerc(bdPercAvanzamentoSpesa.multiply(BigDecimal.valueOf(100)));
			String percEconomia = toStringPerc(
					BigDecimal.valueOf(1).subtract(bdPercAvanzamentoSpesa).multiply(BigDecimal.valueOf(100)));

			String importoEconomiaFinale = toString(importoFinanziamento.subtract(origImportiTotaliSpesi));

			impsnse.add(new VociImportiSnse(fonteFinanziamento, importiPianoFinanziario, importiTotaliSpesi,
					percAvanzamentoSpesa, percEconomia, importoEconomiaFinale));

		});
		return impsnse;
	}

	private List<VociTotaleLiquidato> getTotaleLiquidato() {
		List<VociTotaleLiquidato> voci = new ArrayList<VociTotaleLiquidato>();

		localFinanziamento.keySet().forEach(k -> {
			String fonteFinanziamento = localFinanziamento.get(k).fonteFinanziamento();
			BigDecimal iTotRichieste = finanziamentoLiquidazioneRichiestaList.stream()
					.filter(f -> f.getFinId().equals(k)) //
					.map(FinanziamentoLiquidazioneRichiestaDto::getLiqRicImporto) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

			BigDecimal iTotLiquiodato = finanziamentoLiquidazioneList.stream().filter(f -> f.getFinId().equals(k)) //
					.map(FinanziamentoLiquidazioneDto::getLiqImporto) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

			String totaleRichieste = toString(iTotRichieste);
			String totaleLiquidato = toString(iTotLiquiodato);

			String totaleLiquidare = toString(iTotRichieste.subtract(iTotLiquiodato));

			List<LiquidazioneDto> liqs = new ArrayList<LiquidazioneDto>();

			finanziamentoLiquidazioneRichiestaList.stream().filter(f -> f.getFinId().equals(k)).forEach(el -> {
				el.getListaLiquidazione().forEach(feel -> liqs.add(feel));
			});

			String totaleErogato = toString(liqs.stream().map(LiquidazioneDto::getLiqImportoErogato) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));

			String totaleIncassato = toString(liqs.stream().map(LiquidazioneDto::getLiqImportoIncassato) //
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));

			voci.add(new VociTotaleLiquidato(fonteFinanziamento, totaleRichieste, totaleLiquidato, totaleLiquidare,
					totaleErogato, totaleIncassato));

		});

		return voci;
	}

	private List<VociRichiestaFinanziamento> getRichiestaFinanziamento(String fnt) {
		List<VociRichiestaFinanziamento> fintr = new ArrayList<VociRichiestaFinanziamento>();

		finanziamentoLiquidazioneRichiestaList.forEach(f -> {
			VociPianoFinanziario fin = localFinanziamento.get(f.getFinId());
			if (fin.fonteFinanziamento().equalsIgnoreCase(fnt)) {
				String noRichiesta = f.getLiqRicNumero().toString();
				String dataRichiesta = toString(f.getLiqRicProtocolloData());
				String riferimentoRichiesta = "";

				// f.stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add)

				BigDecimal iErogato = f.getListaLiquidazione().stream().map(LiquidazioneDto::getLiqImportoErogato)
						.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
				BigDecimal iIncassato = f.getListaLiquidazione().stream().map(LiquidazioneDto::getLiqImportoIncassato)
						.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

				BigDecimal iLiquidato = finanziamentoLiquidazioneList.stream()
						.filter(r -> r.getFinId().equals(f.getFinId())).findFirst().get().getLiqImporto();

				String importoRichiesta = toString(f.getLiqRicImporto());
				String importoLiquidato = toString(iLiquidato);
				String importoLiquidare = toString(f.getLiqRicImporto().subtract(iLiquidato));// this.getImportoTotaleDelleRichieste(finId)
																								// -
																								// this.getImportoTotaleLiquidato(finId);
				String importoErogato = toString(iErogato);
				String importoIncassato = toString(iIncassato);

				fintr.add(new VociRichiestaFinanziamento(noRichiesta, dataRichiesta, riferimentoRichiesta,
						importoRichiesta, importoLiquidato, importoLiquidare, importoErogato, importoIncassato));
			}

		});

		return fintr;

	}

	private List<VociRichiestaFinanziamento> getRichiestaFinanziamentoPrincipale() {
		List<VociRichiestaFinanziamento> fintr = new ArrayList<VociRichiestaFinanziamento>();

		finanziamentoLiquidazioneRichiestaList.forEach(f -> {
			VociPianoFinanziario fin = localFinanziamento.get(f.getFinId());
			if (fin.finPrincipale()) {
				String noRichiesta = f.getLiqRicNumero().toString();
				String dataRichiesta = toString(f.getLiqRicProtocolloData());
				String riferimentoRichiesta = "";

				// f.stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add)

				BigDecimal iErogato = f.getListaLiquidazione().stream().map(LiquidazioneDto::getLiqImportoErogato)
						.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
				BigDecimal iIncassato = f.getListaLiquidazione().stream().map(LiquidazioneDto::getLiqImportoIncassato)
						.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

				BigDecimal iLiquidato = finanziamentoLiquidazioneList.stream()
						.filter(r -> r.getFinId().equals(f.getFinId())).findFirst().get().getLiqImporto();

				String importoRichiesta = toString(f.getLiqRicImporto());
				String importoLiquidato = toString(iLiquidato);
				String importoLiquidare = toString(f.getLiqRicImporto().subtract(iLiquidato));// this.getImportoTotaleDelleRichieste(finId)
																								// -
																								// this.getImportoTotaleLiquidato(finId);
				String importoErogato = toString(iErogato);
				String importoIncassato = toString(iIncassato);

				fintr.add(new VociRichiestaFinanziamento(noRichiesta, dataRichiesta, riferimentoRichiesta,
						importoRichiesta, importoLiquidato, importoLiquidare, importoErogato, importoIncassato));
			}

		});

		return fintr;
	}

	private TotaliRichiestaFinanziamento getTotaleRichiestaFinanziamentoPrincipale() {
		List<BigDecimal> totImportoRichiesta = new ArrayList<BigDecimal>();
		List<BigDecimal> totImportoLiquidato = new ArrayList<BigDecimal>();
		List<BigDecimal> totImportoLiquidare = new ArrayList<BigDecimal>();
		List<BigDecimal> totImportoErogato = new ArrayList<BigDecimal>();
		List<BigDecimal> totImportoIncassato = new ArrayList<BigDecimal>();

		finanziamentoLiquidazioneRichiestaList.forEach(f -> {
			VociPianoFinanziario fin = localFinanziamento.get(f.getFinId());
			if (fin.finPrincipale()) {
				totImportoErogato.add(f.getListaLiquidazione().stream().map(LiquidazioneDto::getLiqImportoErogato)
						.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));

				totImportoIncassato.add(f.getListaLiquidazione().stream().map(LiquidazioneDto::getLiqImportoIncassato)
						.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));

				BigDecimal iLiquidato = finanziamentoLiquidazioneList.stream()
						.filter(r -> r.getFinId().equals(f.getFinId())).findFirst().get().getLiqImporto();

				totImportoLiquidato.add(iLiquidato);

				totImportoRichiesta.add(f.getLiqRicImporto());
				totImportoLiquidare.add(f.getLiqRicImporto().subtract(iLiquidato));// this.getImportoTotaleDelleRichieste(finId)
																					// -
																					// this.getImportoTotaleLiquidato(finId);

			}

		});

		return new TotaliRichiestaFinanziamento(toStringTotali(totImportoRichiesta),
				toStringTotali(totImportoLiquidato), toStringTotali(totImportoLiquidare),
				toStringTotali(totImportoErogato), toStringTotali(totImportoIncassato));
	}

	private List<String> getAnniVociPrevisioneAvanzamentoSpesa() {
		List<String> anni = new ArrayList<String>();
		interventoFinanziamentoPrevSpesaList.forEach(e -> {
			anni.add(e.getIntFinPrevSpesaAnno().toString());
		});

		List<String> retValues = anni.stream().distinct().collect(Collectors.toList());
		Collections.sort(retValues);
		return retValues;
	}

	private List<VociPrevisioneAvanzamentoSpesa> getVociPrevisioneAvanzamentoSpesa() {
		List<VociPrevisioneAvanzamentoSpesa> v = new ArrayList<VociPrevisioneAvanzamentoSpesa>();

		Map<Integer, VociPrevisioneAvanzamentoSpesa> elsencoEsercizioFinanziario = new LinkedHashMap<>();
		Map<Integer, BigDecimal> totali = new LinkedHashMap<Integer, BigDecimal>();
		interventoFinanziamentoPrevSpesaList.forEach(e -> {
			VociPrevisioneAvanzamentoSpesa el = elsencoEsercizioFinanziario.get(e.getFinId());
			if (el == null) {

				String fonte = localFinanziamento.get(e.getFinId()).fonteFinanziamento();

				el = new VociPrevisioneAvanzamentoSpesa(fonte, new LinkedHashMap<>(), null);
				elsencoEsercizioFinanziario.put(e.getFinId(), el);
			}
			String anno = e.getIntFinPrevSpesaAnno().toString();
			el.esercizioFinanziario().put(anno, toString(e.getIntFinPrevSpesaImporto()));

			// totale per anno
			BigDecimal newtotanno;
			if (totaliVociPrevisioneAvanzamentoSpesaAnni.containsKey(anno)) {
				newtotanno = e.getIntFinPrevSpesaImporto().add(totaliVociPrevisioneAvanzamentoSpesaAnni.get(anno));
			} else {
				newtotanno = e.getIntFinPrevSpesaImporto();
			}

			totaliVociPrevisioneAvanzamentoSpesaAnni.put(anno, newtotanno);
			// --- totale per finanzimanto
			BigDecimal newtot;
			if (totali.containsKey(e.getFinId())) {
				newtot = e.getIntFinPrevSpesaImporto().add(totali.get(e.getFinId()));
			} else {
				newtot = e.getIntFinPrevSpesaImporto();
			}

			totali.put(e.getFinId(), newtot);
		});

		elsencoEsercizioFinanziario.entrySet().forEach(el -> {
			v.add(el.getValue().withTotale(toString(totali.get(el.getKey()))));
		});

		return v;
	}

	private List<VociPianoFinanziario> getVociPianoFinanziario() {
		List<VociPianoFinanziario> l = new ArrayList<>();

		finanziamentiList.forEach(f -> {
			FinanziamentoTipoDetDto ftd = finanziamentoTipoDet.stream()
					.filter(P -> P.getFinTipoDetId().equals(f.getFinTipoDetId())).findFirst().get();

			Optional<FinanziamentoTipoDto> otpipoFin = finanziamentoTipo.stream()
					.filter(P -> P.getFinTipoId().equals(ftd.getFinTipoId())).findFirst();

			if (otpipoFin.isPresent()) {
				FinanziamentoTipoDto tipoFin = otpipoFin.get();
				if (f.getFinPrincipale()) {
					fonteFinanzimentoPrincipale = tipoFin.getFinTipoDesc();
				}
				VociPianoFinanziario el = new VociPianoFinanziario(//
						f.getFinPrincipale(), //
						tipoFin.getFinTipoDesc(), //
						ftd.getFinTipoDetDesc(), //
						toString(f.getFinImporto()), //
						f.getFinImporto()//
				);

				l.add(el);
				localFinanziamento.put(f.getFinId(), el);
			} else {
				LOG.warn("[SchedaCMapper::getVociPianoFinanziario] elemento [" + ftd.getFinTipoDetId()
						+ "] non presente");
			}

		});

		return l;
	}

	private String toStringPerc(BigDecimal data) {
		String s = "-";
		if (data != null) {
			s = dfi.format(data);
		}

		return s;
	}

	private String toStringTotali(List<BigDecimal> data) {
		String s = "-";
		BigDecimal tot = getTotale(data);
		if (tot != null) {
			s = toString(getTotale(data));
		}

		return s;
	}

	private BigDecimal getTotale(List<BigDecimal> data) {
		BigDecimal tot = null;
		if (data != null) {
			tot = data.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		return tot;
	}

	private String toString(BigDecimal data) {
		String s = "-";
		if (data != null) {
			s = df.format(data);
		}

		return s;
	}

	private String toString(Timestamp data) {
		String s = "-";
		if (data != null) {
			s = sdf.format(data);
		}

		return s;
	}

	public void setIntervento(InterventoResultDto entity) {
		this.intervento = entity;
	}

	public void setAllegato(AllegatoDto allegato) {
		this.allegato = allegato;
	}

	public void setEnte(EnteDto[] ente) {
		this.ente = Arrays.asList(ente);
	}

	public void setFinanziamentoTipoDet(FinanziamentoTipoDetDto[] finanziamento) {
		this.finanziamentoTipoDet = Arrays.asList(finanziamento);
	}

	public void setFinanziamentoTipo(FinanziamentoTipoDto[] finanziamentoTipo) {
		this.finanziamentoTipo = Arrays.asList(finanziamentoTipo);
	}

	public void setFinanziamenti(FinanziamentoResultDto[] FinanziamentiList) {
		this.finanziamentiList = Arrays.asList(FinanziamentiList);

	}

	public void setInterventoFinanziamentoPrevSpesa(
			InterventoFinanziamentoPrevSpesaDto[] interventoFinanziamentoPrevSpesa) {
		this.interventoFinanziamentoPrevSpesaList = Arrays.asList(interventoFinanziamentoPrevSpesa);

	}

	public void setFinanziamentoLiquidazioneRichiesta(
			FinanziamentoLiquidazioneRichiestaDto[] finanziamentoLiquidazioneRichiesta) {
		this.finanziamentoLiquidazioneRichiestaList = Arrays.asList(finanziamentoLiquidazioneRichiesta);

	}

	public void setFinanziamentoLiquidazione(FinanziamentoLiquidazioneDto[] finanziamentoLiquidazione) {
		this.finanziamentoLiquidazioneList = Arrays.asList(finanziamentoLiquidazione);

	}

	public void setInterventoStruttura(InterventoStrutturaDto[] interventoStruttura) {
		this.interventoStrutturaList = Arrays.asList(interventoStruttura);

	}

	public void setInterventoStatoProgettuale(InterventoStatoProgettualeDto[] interventoStatoProgettuale) {
		this.interventoStatoProgettuale = Arrays.asList(interventoStatoProgettuale);
	}

	public void setGaraAppalto(InterventoGaraAppaltoDto2[] interventoGaraAppalto) {
		this.interventoGaraAppaltoList = Arrays.asList(interventoGaraAppalto);

	}

	public void setStruttura(StrutturaDto[] struttura) {
		this.strutturaList = Arrays.asList(struttura);

	}
}
