package br.com.cielo.sigo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cielo.sigo.model.RegiaoFeriado;

/**
 * Repository da entidade RegiaoFeriado
 * 
 * @author alexandre.oliveira
 * @since 27/04/2018
 *
 */
public interface RegiaoFeriadoRepository extends JpaRepository<RegiaoFeriado, Integer> {

	/**
	 * Obtem a lista de feriados nacionais, federais e municipais do ano corrente e do próximo ano
	 * 
	 * @param cidade
	 * @param uf
	 * @param anoCorrente
	 * @param proximoAno
	 * @return
	 */
	@Query(nativeQuery = true)
	List obterListaRegiaoFeriado(
			@Param("cidade") String cidade, 
			@Param("uf") String uf,
			@Param("anoCorrente") Integer anoCorrente,
			@Param("proximoAno") Integer proximoAno);
	
}
