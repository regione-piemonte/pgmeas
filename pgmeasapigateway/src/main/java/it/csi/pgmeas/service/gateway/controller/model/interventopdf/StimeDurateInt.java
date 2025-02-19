/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.service.gateway.controller.model.interventopdf;

public class StimeDurateInt {
	private Integer progettazioneGg = null;
	private Integer affidamentoLavoriGg = null;
	private Integer esecuzioneLavoriGg = null;
	private Integer collaudoGg = null;

	public StimeDurateInt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StimeDurateInt(Integer progettazioneGg, Integer affidamentoLavoriGg, Integer esecuzioneLavoriGg,
			Integer collaudoGg) {
		super();
		this.progettazioneGg = progettazioneGg;
		this.affidamentoLavoriGg = affidamentoLavoriGg;
		this.esecuzioneLavoriGg = esecuzioneLavoriGg;
		this.collaudoGg = collaudoGg;
	}

    public int getTotaleDurata() {
        int totale = (progettazioneGg != null ? progettazioneGg : 0) +
                     (affidamentoLavoriGg != null ? affidamentoLavoriGg : 0) +
                     (esecuzioneLavoriGg != null ? esecuzioneLavoriGg : 0) +
                     (collaudoGg != null ? collaudoGg : 0);
        return totale;
    }
    
	public Integer getProgettazioneGg() {
		return progettazioneGg;
	}

	public void setProgettazioneGg(Integer progettazioneGg) {
		this.progettazioneGg = progettazioneGg;
	}

	public Integer getAffidamentoLavoriGg() {
		return affidamentoLavoriGg;
	}

	public void setAffidamentoLavoriGg(Integer affidamentoLavoriGg) {
		this.affidamentoLavoriGg = affidamentoLavoriGg;
	}

	public Integer getEsecuzioneLavoriGg() {
		return esecuzioneLavoriGg;
	}

	public void setEsecuzioneLavoriGg(Integer esecuzioneLavoriGg) {
		this.esecuzioneLavoriGg = esecuzioneLavoriGg;
	}

	public Integer getCollaudoGg() {
		return collaudoGg;
	}

	public void setCollaudoGg(Integer collaudoGg) {
		this.collaudoGg = collaudoGg;
	}

    // Getter con prefisso 'getStr' e restituzione "--" se il valore è null
    public String getStrProgettazioneGg() {
        return (progettazioneGg != null) ? progettazioneGg.toString() : "--";
    }

    public String getStrAffidamentoLavoriGg() {
        return (affidamentoLavoriGg != null) ? affidamentoLavoriGg.toString() : "--";
    }

    public String getStrEsecuzioneLavoriGg() {
        return (esecuzioneLavoriGg != null) ? esecuzioneLavoriGg.toString() : "--";
    }

    public String getStrCollaudoGg() {
        return (collaudoGg != null) ? collaudoGg.toString() : "--";
    }
    
	public String getStrTotaleDurata() {
    	int iTot = getTotaleDurata() ;
        return (iTot>0 ) ? Integer.toString(iTot) : "--";
    	
    }

}
