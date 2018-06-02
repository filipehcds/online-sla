package br.com.cielo.sigo.service.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Classe de exceção para parâmetros de SLA não encontrados
 * 
 * @author alexandre.oliveira
 * @since 27/04/2018
 *
 */
public class SlaNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 8676053622095344802L;
	
	/**
	 * Código do erro
	 */
	private String codigo;
	
	public SlaNaoEncontradoException(ErrosEnum errosEnum, MessageSource messageSource) {
		super(messageSource.getMessage(errosEnum.getChaveMensagem(), null, LocaleContextHolder.getLocale()));
		this.codigo = errosEnum.getCodigoErro();
	}

	public String getCodigo() {
		return codigo;
	} 

}
