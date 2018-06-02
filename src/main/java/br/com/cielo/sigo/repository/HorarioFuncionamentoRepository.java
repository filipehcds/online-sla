package br.com.cielo.sigo.repository;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cielo.sigo.model.HorarioFuncionamento;

/**
 * Repositório de horário de funcionamento
 * 
 * @author alexandre.oliveira
 * @since 28/03/2018
 *
 */
public interface HorarioFuncionamentoRepository extends JpaRepository<HorarioFuncionamento, String> {
	
	/**
	 * Retorna horário de funcionamento aproximado de acordo com os critérios de pesquisa considerando ranges de 2h a partir do horário inicial e final
	 * 
	 * @param numeroDiaInicio
	 * @param numeroDiaFim
	 * @param horarioInicio
	 * @param horarioFim
	 * @return
	 */
	@Query(nativeQuery = true)
	HorarioFuncionamento buscarPorDiaEHorarioAproximado(
			@Param("numeroDiaInicio") Integer numeroDiaInicio, 
			@Param("numeroDiaFim") Integer numeroDiaFim, 
			@Param("horarioInicio") LocalTime horarioInicio, 
			@Param("horarioFim") LocalTime horarioFim);
	
}
