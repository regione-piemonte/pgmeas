/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.model;

import java.io.Serializable;
import java.math.BigDecimal;

import it.csi.pgmeas.commons.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="pgmeas_t_intervento")
public class Intervento extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7665415707332022708L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "int_id")
    private Integer intId;

    @Column(name = "int_cod", nullable=false)
    private String intCod;

    @Column(name = "int_titolo", nullable=false)
    private String intTitolo;

    @Column(name = "int_anno", nullable=false)
    private Integer intAnno;

    @Column(name = "int_cup")
    private String intCup;

    @Column(name = "int_importo", nullable=false)
    private BigDecimal intImporto;

    @Column(name = "int_codic_nsis")
    private String intCodicNsis;

    @Column(name = "int_direttore_generale_cognome")
    private String intDirettoreGeneraleCognome;

    @Column(name = "int_direttore_generale_nome")
    private String intDirettoreGeneraleNome;

    @Column(name = "int_commissario_cognome")
    private String intCommissarioCognome;

    @Column(name = "int_commissario_nome")
    private String intCommissarioNome;

    @Column(name = "int_referente_pratica_cognome")
    private String intReferentePraticaCognome;

    @Column(name = "int_referente_pratica_nome")
    private String intReferentePraticaNome;

    @Column(name = "int_referente_pratica_telefono")
    private String intReferentePraticaTelefono;

    @Column(name = "int_referente_pratica_email")
    private String intReferentePraticaEmail;

    @Column(name = "quadrante_id")
    private Integer intQuadranteId;

    @Column(name = "ente_id", nullable=false)
    private Integer enteId;

    // Nuovi campi aggiunti
    @Column(name = "int_rup_cognome")
    private String intRupCognome;

    @Column(name = "int_rup_nome")
    private String intRupNome;

    @Column(name = "int_rup_cf")
    private String intRupCf;

    @Column(name = "int_priorita_anno")
    private Integer intPrioritaAnno;

    @Column(name = "int_priorita")
    private Integer intPriorita;

    @Column(name = "int_sottopriorita")
    private String intSottopriorita;

    @Column(name = "int_finanziabile")
    private Boolean intFinanziabile;

    @Column(name = "copiato_da_int_id")
    private Integer copiatoDaIntId;

    @Column(name = "copiato_da_ente_id")
    private Integer copiatoDaEnteId;
    
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
    
    @Column(name = "note")
    private String note;
    
    @Column(name = "int_direttore_generale_cf")
    private String intDirettoreGeneraleCf;
    
    @Column(name = "int_commissario_cf")
    private String intCommissarioCf;
    
    @Column(name = "int_referente_pratica_cf")
    private String intReferentePraticaCf;
    
}
