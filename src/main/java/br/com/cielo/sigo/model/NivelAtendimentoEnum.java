package br.com.cielo.sigo.model;

/**
 * Representa o nível de atendimento
 * 
 * @author alexandre.oliveira
 * @since 27/04/2018
 *
 */
public enum NivelAtendimentoEnum {

	EM_CAMPO(1), SEGUNDO_NIVEL(2);
	
	final Integer codigo;

	private NivelAtendimentoEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

}
