package br.com.cielo.sigo.service.exception;

/**
 * Enum que representa os possíveis erros de negócio com seus respectivos códigos e chaves das mensagens
 * 
 * @author alexandre.oliveira
 * @since 28/03/2018
 *
 */
public enum ErrosEnum {

	HORARIO_FUNCIONAMENTO_SEM_GRUPO_ATENDIMENTO("HF001", "horario.funcionamento.sem.grupo.atendimento"),
	HORARIO_FUNCIONAMENTO_SENDO_SOBREPOSTO("HF002", "horario.funcionamento.sendo.sobreposto"),
	HORARIO_FUNCIONAMENTO_SABADO_SENDO_SOBREPOSTO("HF003", "horario.funcionamento.sabado.sendo.sobreposto"),
	HORARIO_FUNCIONAMENTO_DOMINGO_SENDO_SOBREPOSTO("HF004", "horario.funcionamento.domingo.sendo.sobreposto"),
	HORARIO_FUNCIONAMENTO_NAO_FOI_IDENTIFICADO_GRUPO("HF005", "horario.funcionamento.nao.foi.identificado.grupo"),
	HORARIO_FUNCIONAMENTO_GRUPO_INFORMADO_HORARIO_NAO_IDENTIFICADO("HF006", "horario.funcionamento.grupo.informado.horario.nao.identificado"),
	HORARIO_FUNCIONAMENTO_COMBINACAO_DOIS_HORARIOS_NAO_PERMITIDA("HF007", "horario.funcionamento.combinacao.dois.horarios.nao.permitida"),
	HORARIO_FUNCIONAMENTO_COMBINACAO_INVALIDA("HF008", "horario.funcionamento.combinacao.invalida"),
	SLA_NAO_IDENTIFICADO("SLA001", "sla.nao.identificado");
	
	/**
	 * Código do erro
	 */
	private String codigoErro;
	
	/**
	 * Chave da mensagem do erro
	 */
	private String chaveMensagem;
	
	private ErrosEnum(String codigoErro, String chaveMensagem) {
		this.codigoErro = codigoErro;
		this.chaveMensagem = chaveMensagem;
	}

	/*
	 * Getters & Setters
	 */
	public String getCodigoErro() {
		return codigoErro;
	}

	public String getChaveMensagem() {
		return chaveMensagem;
	}
	
}
