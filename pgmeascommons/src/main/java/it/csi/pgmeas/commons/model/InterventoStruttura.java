/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pgmeas.commons.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pgmeas_t_intervento_struttura")
@Data
public class InterventoStruttura extends BaseEntity implements Serializable{
	

	private static final long serialVersionUID = 8328251558048670756L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intstr_id")
    private Integer intStrId;

    @Column(name = "int_id", nullable = false)
    private Integer intId;

    @Column(name = "str_id", nullable = false)
    private Integer strId;

    @Column(name = "intstr_importo", nullable = false)
    private BigDecimal intstrImporto;

    @Column(name = "intstr_parere_ppp")
    private Boolean intstrParerePpp;

    @Column(name = "intstr_apertura_cantiere_data_prevista")
    private Date intstrAperturaCantiereDataPrevista;

    @Column(name = "intstr_apertura_cantiere_data_effettiva")
    private Date intstrAperturaCantiereDataEffettiva;

    @Column(name = "intstr_collaudo_data_prevista")
    private Date intstrCollaudoDataPrevista;

    @Column(name = "intstr_collaudo_data_effettiva")
    private Date intstrCollaudoDataEffettiva;

    @Column(name = "ente_id", nullable = false)
    private Integer enteId;

    @Column(name = "intstr_responsabile_struttura_complessa_cognome")
    private String intstrResponsabileStrutturaComplessaCognome;

    @Column(name = "intstr_responsabile_struttura_complessa_nome")
    private String intstrResponsabileStrutturaComplessaNome;

    @Column(name = "intstr_responsabile_struttura_complessa_cf")
    private String intstrResponsabileStrutturaComplessaCf;

    @Column(name = "intstr_responsabile_struttura_semplice_cognome")
    private String intstrResponsabileStrutturaSempliceCognome;

    @Column(name = "intstr_responsabile_struttura_semplice_nome")
    private String intstrResponsabileStrutturaSempliceNome;

    @Column(name = "intstr_responsabile_struttura_semplice_cf")
    private String intstrResponsabileStrutturaSempliceCf;

    @Column(name = "intstr_parere_hta")
    private Boolean intstrParereHta;
    
    @Column(name = "appalto_integrato")
    private Boolean appaltoIntegrato;
    
    @Column(name = "progettazione_gg")
    private Integer progettazioneGg;
    
    @Column(name = "affidamento_lavori_gg")
    private Integer affidamentoLavoriGg;
    
    @Column(name = "esecuzione_lavori_gg")
    private Integer esecuzioneLavoriGg;
     
    @Column(name = "collaudo_gg")
    private Integer collaudoGg;
}
