package br.com.cielo.sigo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cielo.sigo.model.ParametrizacaoSla;

/**
 * Repository da entidade ParametrizacaoSla
 * 
 * @author alexandre.oliveira
 * @since 27/04/2018
 *
 */
public interface ParametrizacaoSlaRepository extends JpaRepository<ParametrizacaoSla, Long> {

	/**
	 * Obtem a lista de parametrizacao de SLA com base nos filtros informados
	 * 
	 * @param codigoCliente
	 * @param siglaModeloSolucao
	 * @param cidade
	 * @param estado
	 * @param codigoNivelAtendimento
	 * @return
	 */
	@Query(nativeQuery = true)
	List obterParametrizacaoSla(
			@Param("codigoCliente") Long codigoCliente, 
			@Param("siglaModeloSolucao") String siglaModeloSolucao,
			@Param("cidade") String cidade,
			@Param("estado") String estado,
			@Param("codigoNivelAtendimento") Integer codigoNivelAtendimento);
	
	/**
	 * Obtem a quantidade de horas de SLA com base nos filtros informados
	 * 
	 * @param localizacao
	 * @param solucaoCaptura
	 * @param pacote
	 * @param nivelAtendimento
	 * @param segmento
	 * @return
	 */
	@Query(nativeQuery = true)
	List obterQuantidadeHorasSla(
			@Param("localizacao") String localizacao, 
			@Param("solucaoCaptura") Integer solucaoCaptura,
			@Param("pacote") Integer pacote,
			@Param("nivelAtendimento") Integer nivelAtendimento,
			@Param("segmento") Integer segmento);
	
}
