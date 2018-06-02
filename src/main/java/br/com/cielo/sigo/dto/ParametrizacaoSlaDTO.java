package br.com.cielo.sigo.dto;

/**
 * DTO ParametrizacaoSlaDTO
 * 
 * @author alexandre.oliveira
 * @since 27/04/2018
 */
public class ParametrizacaoSlaDTO {

	/**
	 * Código do cliente
	 */
	private Long codigoCliente;
	
	/**
	 * Nome fantasia
	 */
	private String nomeFantasia;
	
	/**
	 * Razão social
	 */
	private String razaoSocial;
	
	/**
	 * Código pacote
	 */
	private Integer codigoPacote;
	
	/**
	 * Código do segmento
	 */
	private Integer codigoSegmento;
	
	/**
	 * Sigla da classe de faturamento
	 */
	private String siglaClasseFaturamento;
	
	/**
	 * Sigla modelo solucao
	 */
	private String siglaModeloSolucao;
	
	/**
	 * Grupo modelo solução
	 */
	private String grupoModeloSolucao;
	
	/**
	 * Cidade
	 */
	private String cidade;
	
	/**
	 * Estado
	 */
	private String estado;
	
	/**
	 * Nível de atendimento
	 */
	private String nivelAtendimento;
	
	/**
	 * Quantidade de horas de SLA
	 */
	private Integer quantidadeHorasSla;

	public ParametrizacaoSlaDTO() {
	}
	
	public ParametrizacaoSlaDTO(Integer quantidadeHorasSla) {
		super();
		this.quantidadeHorasSla = quantidadeHorasSla;
	}
	
	public ParametrizacaoSlaDTO(Long codigoCliente, String nomeFantasia, String razaoSocial, Integer codigoPacote,
			Integer codigoSegmento, String siglaClasseFaturamento, String siglaModeloSolucao, String grupoModeloSolucao,
			String cidade, String estado, String nivelAtendimento, Integer quantidadeHorasSla) {
		super();
		this.codigoCliente = codigoCliente;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.codigoPacote = codigoPacote;
		this.codigoSegmento = codigoSegmento;
		this.siglaClasseFaturamento = siglaClasseFaturamento;
		this.siglaModeloSolucao = siglaModeloSolucao;
		this.grupoModeloSolucao = grupoModeloSolucao;
		this.cidade = cidade;
		this.estado = estado;
		this.nivelAtendimento = nivelAtendimento;
		this.quantidadeHorasSla = quantidadeHorasSla;
	}

	/*
	 * Getters & Setters
	 */
	public Long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Integer getCodigoPacote() {
		return codigoPacote;
	}

	public void setCodigoPacote(Integer codigoPacote) {
		this.codigoPacote = codigoPacote;
	}

	public Integer getCodigoSegmento() {
		return codigoSegmento;
	}

	public void setCodigoSegmento(Integer codigoSegmento) {
		this.codigoSegmento = codigoSegmento;
	}

	public String getSiglaClasseFaturamento() {
		return siglaClasseFaturamento;
	}

	public void setSiglaClasseFaturamento(String siglaClasseFaturamento) {
		this.siglaClasseFaturamento = siglaClasseFaturamento;
	}

	public String getSiglaModeloSolucao() {
		return siglaModeloSolucao;
	}

	public void setSiglaModeloSolucao(String siglaModeloSolucao) {
		this.siglaModeloSolucao = siglaModeloSolucao;
	}

	public String getGrupoModeloSolucao() {
		return grupoModeloSolucao;
	}

	public void setGrupoModeloSolucao(String grupoModeloSolucao) {
		this.grupoModeloSolucao = grupoModeloSolucao;
	}

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

	public String getNivelAtendimento() {
		return nivelAtendimento;
	}

	public void setNivelAtendimento(String nivelAtendimento) {
		this.nivelAtendimento = nivelAtendimento;
	}

	public Integer getQuantidadeHorasSla() {
		return quantidadeHorasSla;
	}

	public void setQuantidadeHorasSla(Integer quantidadeHorasSla) {
		this.quantidadeHorasSla = quantidadeHorasSla;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParametrizacaoSlaDTO [codigoCliente=" + codigoCliente + ", nomeFantasia=" + nomeFantasia
				+ ", razaoSocial=" + razaoSocial + ", codigoPacote=" + codigoPacote + ", codigoSegmento="
				+ codigoSegmento + ", siglaClasseFaturamento=" + siglaClasseFaturamento + ", siglaModeloSolucao="
				+ siglaModeloSolucao + ", grupoModeloSolucao=" + grupoModeloSolucao + ", cidade=" + cidade + ", estado="
				+ estado + ", nivelAtendimento=" + nivelAtendimento + ", quantidadeHorasSla=" + quantidadeHorasSla
				+ "]";
	}

}
