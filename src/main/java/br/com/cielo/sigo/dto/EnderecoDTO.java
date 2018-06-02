package br.com.cielo.sigo.dto;

import javax.validation.constraints.NotEmpty;

/**
 * DTO EnderecoDTO
 * 
 * @author alexandre.oliveira
 * @since 27/04/2018
 *
 */
public class EnderecoDTO {

	/**
	 * Cidade
	 */
	@NotEmpty
	private String cidade;

	/**
	 * Estado
	 */
	@NotEmpty
	private String estado;

	public EnderecoDTO() {
	}

	public EnderecoDTO(String cidade, String estado) {
		super();
		this.cidade = cidade;
		this.estado = estado;
	}

	/*
	 * Getters & Setters
	 */
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EnderecoDTO [cidade=" + cidade + ", estado=" + estado + "]";
	}

}
