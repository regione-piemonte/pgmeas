/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 
*/
package it.csi.pgmeas.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.pgmeas.commons.dto.ClassificazioneTreeByClassTsTipoDto;
import it.csi.pgmeas.commons.model.ClassificazioneTree;

@Repository
public interface ClassificazioneTreeRepository extends JpaRepository<ClassificazioneTree, Integer> {
	
	@Query("select new it.csi.pgmeas.commons.dto.ClassificazioneTreeByClassTsTipoDto("
			+ "pdct2.classifTreeId as classifTreeId, "
			+ "pdct.classifTsId as classifTsId, "
			+ "pdce.classifCod as classifElemCod, "
			+ "pdce.classifDesc as descrizione, "
			+ "pdct2.classifTreeLivello as livello, "
            + "case "
            + "     when (select count (1) from ClassificazioneTree b where b.classifIdPadre = pdct2.classifTreeId)>0 then false "
            + "     else true "
            + "end as selezionabile, "
			+ "pdct2.classifTreeImportoDecimali as classifTreeImportoDecimali, "
			+ "pdct2.classifTreeConImporto as classifTreeConImporto, "
			+ "pdct2.classifTreeEditabile as classifTreeEditabile,"
			+ "pdce.classifSimbolo as classifSimbolo, "
			+ "pdce.classifEtichetta as classifEtichetta) "
			+ "from ClassificazioneTsTipo pdctt "
			+ "join ClassificazioneTs pdct on pdct.classifTsTipoId = pdctt.classifTsTipoId "
			+ "join ClassificazioneTree pdct2 on pdct2.classifTsId = pdct.classifTsId "
			+ "join ClassificazioneElemento pdce on pdce.classifId = pdct2.classifId "
			+ "where "
			+ "pdctt.classifTsTipoCod = :classifTsTipoCod " 
			+ "and pdctt.dataCancellazione is null "
			+ "and pdctt.validitaInizio <= current_timestamp "
			+ "and (pdctt.validitaFine is null or pdctt.validitaFine >= current_timestamp) "
			+ "and pdct.dataCancellazione is null "
			+ "and pdct.validitaInizio <= current_timestamp "
			+ "and (pdct.validitaFine is null or pdct.validitaFine >= current_timestamp) "
			+ "and pdct2.dataCancellazione is null "
			+ "and pdct2.validitaInizio <= current_timestamp "
			+ "and (pdct2.validitaFine is null or pdct2.validitaFine >= current_timestamp) "
			+ "and pdce.dataCancellazione is null "
			+ "and pdce.validitaInizio <= current_timestamp "
			+ "and (pdce.validitaFine is null or pdce.validitaFine >= current_timestamp) "
			+ "order by pdct2.classifTreeOrdine ")
	List<ClassificazioneTreeByClassTsTipoDto> findValidTreeByClassifTsTipoCod(@Param("classifTsTipoCod") String classifTsTipoCod);
	
	@Query("select new it.csi.pgmeas.commons.dto.ClassificazioneTreeByClassTsTipoDto("
			+ "pdct2.classifTreeId as classifTreeId, "
			+ "pdct.classifTsId as classifTsId, "
			+ "pdce.classifCod as classifElemCod, "
			+ "pdce.classifDesc as descrizione, "
			+ "pdct2.classifTreeLivello as livello, "
            + "case "
            + "     when (select count (1) from ClassificazioneTree b where b.classifIdPadre = pdct2.classifTreeId)>0 then false "
            + "     else true "
            + "end as selezionabile, "
			+ "pdct2.classifTreeImportoDecimali as classifTreeImportoDecimali, "
			+ "pdct2.classifTreeConImporto as classifTreeConImporto, "
			+ "pdct2.classifTreeEditabile as classifTreeEditabile,"
			+ "pdce.classifSimbolo as classifSimbolo, "
			+ "pdce.classifEtichetta as classifEtichetta) "
			+ "from ClassificazioneTsTipo pdctt "
			+ "join ClassificazioneTs pdct on pdct.classifTsTipoId = pdctt.classifTsTipoId "
			+ "join ClassificazioneTree pdct2 on pdct2.classifTsId = pdct.classifTsId "
			+ "join ClassificazioneElemento pdce on pdce.classifId = pdct2.classifId "
			+ "where "
			+ "pdctt.classifTsTipoCod = :classifTsTipoCod " 
			+ "and pdctt.dataCancellazione is null "
			+ "and pdct.dataCancellazione is null "
			+ "and pdct2.dataCancellazione is null "
			+ "and pdce.dataCancellazione is null "
			+ "order by pdct2.classifTreeOrdine ")
	List<ClassificazioneTreeByClassTsTipoDto> findAllTreeByClassifTsTipoCod(@Param("classifTsTipoCod") String classifTsTipoCod);
	
	//TODO delete
	@Query("select a from ClassificazioneTree a where (a.dataCancellazione is null or a.dataCancellazione > current_timestamp) and a.classifTsId in (:classifTsIds) "
			+ "order by a.classifTreeId")
	List<ClassificazioneTree> findAllSenzaCancellatiByClassifTsIds(@Param("classifTsIds") List<Integer> classifTsIds);

	@Query("select "
			+ "pdct2.classifTreeId "
			+ "from ClassificazioneTsTipo pdctt "
			+ "join ClassificazioneTs pdct on pdct.classifTsTipoId = pdctt.classifTsTipoId "
			+ "join ClassificazioneTree pdct2 on pdct2.classifTsId = pdct.classifTsId "
			+ "join ClassificazioneElemento pdce on pdce.classifId = pdct2.classifId "
			+ "where "
			+ "pdctt.classifTsTipoCod = 'QE' " 
			+ "and pdctt.dataCancellazione is null "
			+ "and pdctt.validitaInizio <= current_timestamp "
			+ "and (pdctt.validitaFine is null or pdctt.validitaFine >= current_timestamp) "
			+ "and pdct.dataCancellazione is null "
			+ "and pdct.validitaInizio <= current_timestamp "
			+ "and (pdct.validitaFine is null or pdct.validitaFine >= current_timestamp) "
			+ "and pdct2.dataCancellazione is null "
			+ "and pdct2.validitaInizio <= current_timestamp "
			+ "and (pdct2.validitaFine is null or pdct2.validitaFine >= current_timestamp) "
			+ "and pdce.dataCancellazione is null "
			+ "and pdce.validitaInizio <= current_timestamp "
			+ "and (pdce.validitaFine is null or pdce.validitaFine >= current_timestamp) "
			+" and pdce.classifCod = 'QE_TOT_a+b+c+d+e+f-g' ")
	List<Integer> findTreeQuadroEconomicoNotToTal();
	
}
