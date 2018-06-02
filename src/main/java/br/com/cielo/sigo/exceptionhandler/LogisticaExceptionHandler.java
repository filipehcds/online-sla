package br.com.cielo.sigo.exceptionhandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handler de exceção genérica para tratar erros mais comuns nas requisições
 * 
 * @author alexandre.oliveira
 * @since 29/03/2018
 *
 */
@ControllerAdvice
public class LogisticaExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = Logger.getLogger(LogisticaExceptionHandler.class);
	
	@Autowired
	private MessageSource messageSource;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleHttpMessageNotReadable(org.springframework.http.converter.HttpMessageNotReadableException, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOG.error(ex.getMessage(), ex);
		
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		
		List<Erro> listaErros = criarListaErros(mensagemUsuario, status, null, request);
		return handleExceptionInternal(ex, listaErros, headers, status, request);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.springframework.web.bind.MethodArgumentNotValidException, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOG.error(ex.getMessage(), ex);
		
		List<Erro> listaErros = criarListaErros(null, status, ex.getBindingResult(), request);
		return handleExceptionInternal(ex, listaErros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * Método utilitário para criar lista de erros
	 * 
	 * @param mensagemUsuario
	 * @param status
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	private List<Erro> criarListaErros(String mensagemUsuario, HttpStatus status, BindingResult bindingResult, WebRequest request) {
		List<Erro> erros = new ArrayList<>();
		
		if (bindingResult != null) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				erros.add(new Erro(
						null,
						new Date(), 
						status.value(), 
						status.getReasonPhrase(), 
						messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()),
						null,
						request.getContextPath()));
			}
		} else {
			erros.add(new Erro(
					null,
					new Date(), 
					status.value(), 
					status.getReasonPhrase(), 
					mensagemUsuario, 
					null,
					request.getContextPath()));
		}
		
		return erros;
	}
	
	/**
	 * Handler para tratar exceções do tipo EmptyResultDataAccessException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		LOG.error(ex.getMessage(), ex);
		
		Erro erro = new Erro(
				null,
				new Date(), 
				HttpStatus.NOT_FOUND.value(), 
				HttpStatus.NOT_FOUND.getReasonPhrase(), 
				messageSource.getMessage("mensagem.recurso.nao.encontrado", null, LocaleContextHolder.getLocale()), 
				ExceptionUtils.getRootCauseMessage(ex),
				request.toString()
				);
		
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	/**
	 * Handler para tratar exceções do tipo NoSuchElementException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ NoSuchElementException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
		LOG.error(ex.getMessage(), ex);
		
		Erro erro = new Erro(
				null,
				new Date(), 
				HttpStatus.NOT_FOUND.value(), 
				HttpStatus.NOT_FOUND.getReasonPhrase(), 
				messageSource.getMessage("mensagem.recurso.nao.encontrado", null, LocaleContextHolder.getLocale()), 
				ExceptionUtils.getRootCauseMessage(ex),
				request.toString()
				);
		
		return handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	/**
	 * Estrutura padrão de erros lançada pelos handlers
	 * 
	 * @author alexandre.oliveira
	 * @since 28/03/2018
	 *
	 */
	public static class Erro {

		/**
		 * Código do erro de negócio
		 */
		private String errorCode;
		
		/**
		 * Data e hora do erro gerado
		 */
		private Date timestamp;
		
		/**
		 * Código do HTTP Status gerado
		 */
		private int status;
		
		/**
		 * Descrição do erro HTTP gerado
		 */
		private String error;
		
		/**
		 * Mensagem amigável e resumida do erro
		 */
		private String message;
		
		/**
		 * Detalhe do erro lançada pela camada de negócio. Conterá os detalhes o qual gerou o referido erro
		 */
		private String detail;
		
		/**
		 * URI da requisição geradora do erro
		 */
		private String path;

		public Erro(String errorCode, Date timestamp, int status, String error, String message, String detail, String path) {
			super();
			this.errorCode = errorCode;
			this.timestamp = timestamp;
			this.status = status;
			this.error = error;
			this.message = message;
			this.detail = detail;
			this.path = path;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public Date getTimestamp() {
			return timestamp;
		}

		public int getStatus() {
			return status;
		}

		public String getError() {
			return error;
		}

		public String getMessage() {
			return message;
		}

		public String getPath() {
			return path;
		}

		public String getDetail() {
			return detail;
		}

	}

}
