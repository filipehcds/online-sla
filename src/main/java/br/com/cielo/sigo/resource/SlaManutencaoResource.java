package br.com.cielo.sigo.resource;

import java.util.Date;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.cielo.sigo.dto.SlaDTO;
import br.com.cielo.sigo.exceptionhandler.LogisticaExceptionHandler.Erro;
import br.com.cielo.sigo.service.SlaService;
import br.com.cielo.sigo.service.exception.SlaNaoEncontradoException;

/**
 * Representa o controlador do recurso de SLA
 * 
 * @author alexandre.oliveira
 * @since 28/03/2018
 *
 */
@RestController
@RequestMapping("/sla-manutencao/v1")
public class SlaManutencaoResource {

	private static final Logger LOG = Logger.getLogger(SlaManutencaoResource.class);
	
	@Autowired
	private SlaService slaService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Retorna os horários de funcionamento de acordo com os critérios informado
	 * 
	 * @param horarioFuncionamentoDTO
	 * @return
	 */
	@PostMapping(path = "/calcular")
	public ResponseEntity<SlaDTO> calcularSla(@Valid @RequestBody SlaDTO slaDTO) {
		
		SlaDTO slaDTOAux = slaService.calcularSla(slaDTO);
		
		if (slaDTOAux  != null) {
			return ResponseEntity.status(HttpStatus.OK).body(slaDTOAux);
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
	@ExceptionHandler({ SlaNaoEncontradoException.class })
	public ResponseEntity<Object> handleSemGrupoHorarioFuncionamentoExceptionPessoaInexistenteOuInativoException(SlaNaoEncontradoException ex, WebRequest request) {
		LOG.error(ex.getMessage(), ex);
		
		Erro erro = new Erro(
				ex.getCodigo(),
				new Date(), 
				HttpStatus.BAD_REQUEST.value(), 
				HttpStatus.BAD_REQUEST.getReasonPhrase(), 
				messageSource.getMessage("sla.erro.consulta.parametrizacao", null, LocaleContextHolder.getLocale()),
				ex.getMessage(),
				request.toString()
				);
		
		return ResponseEntity.badRequest().body(erro);
	}

}	
