package br.com.cielo.sigo.dto;

import java.time.LocalDateTime;

/**
 * DTO FeriadoDTO 
 * 
 * @author alexandre.oliveira
 *
 */
public class FeriadoDTO {

	/**
	 * Tipo do feriado: Federal, Estadual ou Municipal
	 */
	private String tipo;
	
	/**
	 * Descriçao do feriado
	 */
	private String descricao;
	
	/**
	 * Cidade
	 */
	private String cidade;
	
	/**
	 * Estado
	 */
	private String uf;
	
	/**
	 * Data do feriado
	 */
	private LocalDateTime data;

	public FeriadoDTO() {
	}
	
	public FeriadoDTO(String tipo, String descricao, String cidade, String uf, LocalDateTime data) {
		super();
		this.tipo = tipo;
		this.descricao = descricao;
		this.cidade = cidade;
		this.uf = uf;
		this.data = data;
	}

	/*
	 * Getters & Setters
	 */
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "FeriadoDTO [tipo=" + tipo + ", descricao=" + descricao + ", cidade=" + cidade + ", uf=" + uf + ", data="
				+ data + "]";
	}
	
}
