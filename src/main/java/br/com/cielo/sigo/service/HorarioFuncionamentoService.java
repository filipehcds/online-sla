package br.com.cielo.sigo.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.cielo.sigo.dto.HorarioFuncionamentoDTO;
import br.com.cielo.sigo.model.DiaSemanaEnum;
import br.com.cielo.sigo.model.HorarioFuncionamento;
import br.com.cielo.sigo.repository.HorarioFuncionamentoRepository;
import br.com.cielo.sigo.service.exception.ErrosEnum;
import br.com.cielo.sigo.service.exception.HorarioFuncionamentoSemGrupoException;

/**
 * Classe de serviço da entidade HorarioFuncionamento responsável em tratar as regras de negócio
 * 
 * @author alexandre.oliveira
 * @since 28/03/2018
 *
 */
@Service
public class HorarioFuncionamentoService {

	@Autowired
	private HorarioFuncionamentoRepository horarioFuncionamentoRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Retorna os horários de funcionamento de acordo com os critérios informado
	 * 
	 * @param diaInicio
	 * @param diaFim
	 * @param horarioInicio
	 * @param horarioFim
	 * @param horarioInicioSabado
	 * @param horarioFimSabado
	 * @param horarioInicioDomingo
	 * @param horarioFimDomingo
	 * @return
	 */
	public List<HorarioFuncionamentoDTO> buscarPorDiaEHorario(
			DiaSemanaEnum diaInicio, 
			DiaSemanaEnum diaFim, 
			LocalTime horarioInicio, 
			LocalTime horarioFim,
			LocalTime horarioInicioSabado, 
			LocalTime horarioFimSabado,
			LocalTime horarioInicioDomingo, 
			LocalTime horarioFimDomingo) {
		
		validarSobreposicaoHorarioDaSemana(diaInicio, diaFim, horarioInicioSabado, horarioFimSabado, horarioInicioDomingo, horarioFimDomingo);
		validarSobreposicaoHorarioNoSabado(diaFim, horarioInicioSabado, horarioFimSabado);
		validarSobreposicaoHorarioNoDomingo(diaFim, horarioInicioDomingo, horarioFimDomingo);
		
		List<HorarioFuncionamento> listaHorarioFunciomamento = new ArrayList<>();
		HorarioFuncionamento horarioFuncionamentoSemana = null;
		if (horarioInicio != null || horarioFim != null) {
			horarioFuncionamentoSemana = horarioFuncionamentoRepository.buscarPorDiaEHorarioAproximado(diaInicio.getNumeroDia(), diaFim != null ? diaFim.getNumeroDia() : null, horarioInicio, horarioFim);
			if (horarioFuncionamentoSemana != null) {
				if (horarioInicio != horarioFuncionamentoSemana.getHorarioInicio() || horarioFim != horarioFuncionamentoSemana.getHorarioFim()) {
					horarioFuncionamentoSemana.setHorarioFuncionamentoAproximado(true);
				}
				
				listaHorarioFunciomamento.add(horarioFuncionamentoSemana);
			}	
		}
		
		HorarioFuncionamento horarioFuncionamentoSabado = null;
		if (horarioInicioSabado != null || horarioFimSabado != null) {
			horarioFuncionamentoSabado = horarioFuncionamentoRepository.buscarPorDiaEHorarioAproximado(DiaSemanaEnum.SABADO.getNumeroDia(), DiaSemanaEnum.SABADO.getNumeroDia(), horarioInicioSabado, horarioFimSabado);
			if (horarioFuncionamentoSabado != null) {
				if (horarioInicioSabado != horarioFuncionamentoSabado.getHorarioInicio() || horarioFimSabado != horarioFuncionamentoSabado.getHorarioFim()) {
					horarioFuncionamentoSabado.setHorarioFuncionamentoAproximado(true);
				}
				
				listaHorarioFunciomamento.add(horarioFuncionamentoSabado);
			}	
		}
		
		HorarioFuncionamento horarioFuncionamentoDomingo = null;
		if (horarioInicioDomingo != null || horarioFimDomingo != null) {
			horarioFuncionamentoDomingo = horarioFuncionamentoRepository.buscarPorDiaEHorarioAproximado(DiaSemanaEnum.DOMINGO.getNumeroDia(), DiaSemanaEnum.DOMINGO.getNumeroDia(), horarioInicioDomingo, horarioFimDomingo);
			if (horarioFuncionamentoDomingo != null) {
				if (horarioInicioDomingo != horarioFuncionamentoDomingo.getHorarioInicio() || horarioFimDomingo != horarioFuncionamentoDomingo.getHorarioFim()) {
					horarioFuncionamentoDomingo.setHorarioFuncionamentoAproximado(true);
				}
				
				listaHorarioFunciomamento.add(horarioFuncionamentoDomingo);
			}	
		}
		
		validarSeExisteCombinacoes(listaHorarioFunciomamento);
		validarSeExisteHorariosNosGruposInformados(horarioInicio, horarioFim, horarioInicioSabado, horarioFimSabado, horarioInicioDomingo, horarioFimDomingo, horarioFuncionamentoSemana, horarioFuncionamentoSabado, horarioFuncionamentoDomingo);
		validarSeListaContemCodigoAlfa(listaHorarioFunciomamento);
		
		return convertToDTO(listaHorarioFunciomamento);
	}

	/**
	 * Valida se existe qualquer tipo de combinação de horário
	 * 
	 * @param listaHorarioFunciomamento
	 */
	private void validarSeExisteCombinacoes(List<HorarioFuncionamento> listaHorarioFunciomamento) {
		if (listaHorarioFunciomamento == null || listaHorarioFunciomamento.isEmpty()) 
			throw new HorarioFuncionamentoSemGrupoException(ErrosEnum.HORARIO_FUNCIONAMENTO_SEM_GRUPO_ATENDIMENTO, messageSource);
	}

	/**
	 * Valida se para os critérios informado para cada grupo de pesquisa (semanal, sábado ou domingo) teve algum retorno
	 * 
	 * @param horarioInicio
	 * @param horarioFim
	 * @param horarioInicioSabado
	 * @param horarioFimSabado
	 * @param horarioInicioDomingo
	 * @param horarioFimDomingo
	 * @param horarioFuncionamentoSemana
	 * @param horarioFuncionamentoSabado
	 * @param horarioFuncionamentoDomingo
	 */
	private void validarSeExisteHorariosNosGruposInformados(LocalTime horarioInicio, LocalTime horarioFim,
			LocalTime horarioInicioSabado, LocalTime horarioFimSabado, LocalTime horarioInicioDomingo,
			LocalTime horarioFimDomingo, HorarioFuncionamento horarioFuncionamentoSemana,
			HorarioFuncionamento horarioFuncionamentoSabado, HorarioFuncionamento horarioFuncionamentoDomingo) {
		if (((horarioInicio != null || horarioFim != null) && horarioFuncionamentoSemana == null) || 
				((horarioInicioSabado != null || horarioFimSabado != null) && horarioFuncionamentoSabado == null) || 
				((horarioInicioDomingo != null || horarioFimDomingo != null) && horarioFuncionamentoDomingo == null)) {
			throw new HorarioFuncionamentoSemGrupoException(ErrosEnum.HORARIO_FUNCIONAMENTO_GRUPO_INFORMADO_HORARIO_NAO_IDENTIFICADO, messageSource);	
		}
	}

	/**
	 * Valida se existe alguma sobreposição de horário no Domingo
	 * 
	 * @param diaFim
	 * @param horarioInicioDomingo
	 * @param horarioFimDomingo
	 */
	private void validarSobreposicaoHorarioNoDomingo(DiaSemanaEnum diaFim, LocalTime horarioInicioDomingo,
			LocalTime horarioFimDomingo) {
		if (diaFim == DiaSemanaEnum.DOMINGO && (horarioInicioDomingo != null || horarioFimDomingo != null))
			throw new HorarioFuncionamentoSemGrupoException(ErrosEnum.HORARIO_FUNCIONAMENTO_DOMINGO_SENDO_SOBREPOSTO, messageSource);
	}

	/**
	 * Valida se existe alguma sobreposição de horário no Sábado
	 * 
	 * @param diaFim
	 * @param horarioInicioSabado
	 * @param horarioFimSabado
	 */
	private void validarSobreposicaoHorarioNoSabado(DiaSemanaEnum diaFim, LocalTime horarioInicioSabado,
			LocalTime horarioFimSabado) {
		if (diaFim == DiaSemanaEnum.SABADO && (horarioInicioSabado != null || horarioFimSabado != null))
			throw new HorarioFuncionamentoSemGrupoException(ErrosEnum.HORARIO_FUNCIONAMENTO_SABADO_SENDO_SOBREPOSTO, messageSource);
	}

	/**
	 * Valida se existe qualquer tipo de sobreposição de horário entre os dias da semana
	 * 
	 * @param diaInicio
	 * @param diaFim
	 * @param horarioInicioSabado
	 * @param horarioFimSabado
	 * @param horarioInicioDomingo
	 * @param horarioFimDomingo
	 */
	private void validarSobreposicaoHorarioDaSemana(DiaSemanaEnum diaInicio, DiaSemanaEnum diaFim,
			LocalTime horarioInicioSabado, LocalTime horarioFimSabado, LocalTime horarioInicioDomingo,
			LocalTime horarioFimDomingo) {
		if (diaInicio == diaFim && ((horarioInicioSabado != null || horarioFimSabado != null) || (horarioInicioDomingo != null || horarioFimDomingo != null)))
			throw new HorarioFuncionamentoSemGrupoException(ErrosEnum.HORARIO_FUNCIONAMENTO_SENDO_SOBREPOSTO, messageSource);
	}

	/**
	 * Valida se caso houver mais de 1 horário de funcionamento, o código não seja alfanumérica mas 1 letra de A a Z
	 * 
	 * @param listaHorarioFunciomamento
	 */
	private void validarSeListaContemCodigoAlfa(List<HorarioFuncionamento> listaHorarioFunciomamento) {
		if (listaHorarioFunciomamento.size() > 1) {
			for (HorarioFuncionamento horarioFuncionamento : listaHorarioFunciomamento) {
				if (horarioFuncionamento.getCodigo().length() > 1) {
					throw new HorarioFuncionamentoSemGrupoException(
							ErrosEnum.HORARIO_FUNCIONAMENTO_COMBINACAO_INVALIDA, messageSource);
				}
			}	
		}
	}
	
	/**
	 * Mapper para converter lista de entidades de horario de funcionamento para uma lista de DTO
	 * 
	 * @param listaHorarioFuncionamento
	 * @return
	 */
	private List<HorarioFuncionamentoDTO> convertToDTO(List<HorarioFuncionamento> listaHorarioFuncionamento) {
		List<HorarioFuncionamentoDTO> listaRetorno = null;
		
		for (HorarioFuncionamento horarioFuncionamento : listaHorarioFuncionamento) {
			if (listaRetorno == null) {
				listaRetorno = new ArrayList<>();
			}
			
			HorarioFuncionamentoDTO bean = new HorarioFuncionamentoDTO();
			BeanUtils.copyProperties(horarioFuncionamento, bean);
			bean.setDiaInicio(DiaSemanaEnum.obterDiaSemanaPeloNumero(horarioFuncionamento.getDiaInicio()));
			if (horarioFuncionamento.getDiaFim() != null) {
				bean.setDiaFim(DiaSemanaEnum.obterDiaSemanaPeloNumero(horarioFuncionamento.getDiaFim()));
			}
			listaRetorno.add(bean);
		}
		
		return listaRetorno;
	}
	
}
