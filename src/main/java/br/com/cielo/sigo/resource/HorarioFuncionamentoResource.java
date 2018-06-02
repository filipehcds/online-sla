package br.com.cielo.sigo.resource;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.cielo.sigo.dto.HorarioFuncionamentoDTO;
import br.com.cielo.sigo.dto.HorarioFuncionamentoListaDTO;
import br.com.cielo.sigo.exceptionhandler.LogisticaExceptionHandler.Erro;
import br.com.cielo.sigo.service.HorarioFuncionamentoService;
import br.com.cielo.sigo.service.exception.HorarioFuncionamentoSemGrupoException;

/**
 * Representa o controlador do recurso de Horário de Funcionamento
 * 
 * @author alexandre.oliveira
 * @since 28/03/2018
 *
 */
@RestController
@RequestMapping("/horario-funcionamento/v1")
public class HorarioFuncionamentoResource {

	private static final Logger LOG = Logger.getLogger(HorarioFuncionamentoResource.class);
	
	@Autowired
	private HorarioFuncionamentoService horarioFuncionamentoService;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * Retorna os horários de funcionamento de acordo com os critérios informado
	 * 
	 * @param horarioFuncionamentoDTO
	 * @return
	 */
	@GetMapping(path = "/filtro")
	public ResponseEntity<HorarioFuncionamentoListaDTO> buscarCodigoPorDiaEHorario(HorarioFuncionamentoDTO horarioFuncionamentoDTO) {
		
		List<HorarioFuncionamentoDTO> listaHorarioFuncionamento = horarioFuncionamentoService.buscarPorDiaEHorario(
				horarioFuncionamentoDTO.getDiaInicio(), 
				horarioFuncionamentoDTO.getDiaFim(), 
				horarioFuncionamentoDTO.getHorarioInicio(),
				horarioFuncionamentoDTO.getHorarioFim(), 
				horarioFuncionamentoDTO.getHorarioInicioSabado(), 
				horarioFuncionamentoDTO.getHorarioFimSabado(), 
				horarioFuncionamentoDTO.getHorarioInicioDomingo(), 
				horarioFuncionamentoDTO.getHorarioFimDomingo());
		
		if (listaHorarioFuncionamento != null && !listaHorarioFuncionamento.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new HorarioFuncionamentoListaDTO(listaHorarioFuncionamento));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/**
	 * Handler de exceção para tratar erros de negócio do tipo HorarioFuncionamentoSemGrupoException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ HorarioFuncionamentoSemGrupoException.class })
	public ResponseEntity<Object> handleSemGrupoHorarioFuncionamentoExceptionPessoaInexistenteOuInativoException(HorarioFuncionamentoSemGrupoException ex, WebRequest request) {
		LOG.error(ex.getMessage(), ex);
		
		Erro erro = new Erro(
				ex.getCodigo(),
				new Date(), 
				HttpStatus.BAD_REQUEST.value(), 
				HttpStatus.BAD_REQUEST.getReasonPhrase(), 
				messageSource.getMessage("horario.funcionamento.sem.grupo.atendimento", null, LocaleContextHolder.getLocale()),
				ex.getMessage(),
				request.toString()
				);
		
		return ResponseEntity.badRequest().body(erro);
	}
	
}