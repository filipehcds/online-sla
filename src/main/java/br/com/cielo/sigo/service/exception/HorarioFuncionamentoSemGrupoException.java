package br.com.cielo.sigo.service.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Exceção para tratar erros de negócio do serviço
 * 
 * @author alexandre.oliveira
 * @since 28/03/2018
 *
 */
public class HorarioFuncionamentoSemGrupoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -497809307522914669L;
	
	/**
	 * Código do erro
	 */
	private String codigo;
	
	public HorarioFuncionamentoSemGrupoException(ErrosEnum errosEnum, MessageSource messageSource) {
		super(messageSource.getMessage(errosEnum.getChaveMensagem(), null, LocaleContextHolder.getLocale()));
		this.codigo = errosEnum.getCodigoErro();
	}

	public String getCodigo() {
		return codigo;
	}
	
}
