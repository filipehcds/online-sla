package br.com.cielo.sigo.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.cielo.sigo.model.NivelAtendimentoEnum;
import br.com.cielo.sigo.model.TipoServicoEnum;

public class SlaDTO {

	// entrada
	@NotNull
	private NivelAtendimentoEnum nivelAtendimento;

	@NotNull
	private Long codigoCliente;

	@NotEmpty
	private String siglaModeloSolucao;

	@Valid
	@NotNull
	private EnderecoDTO dadosEndereco;

	@NotNull
	private List<DiaHorarioFuncionamentoDTO> listaDiaHorarioFuncionamento;
	private TipoServicoEnum tipoServico;

	@NotNull
	private Boolean isHorarioFuncionamentoIdentificado;

	// saída
	private LocalDateTime dataHoraPrevista;
	private Integer quantidadeHorasUteis;
	private Integer quantidadeDiasUteis;

	public SlaDTO() {
		super();
	}

	public SlaDTO(Integer quantidadeHorasUteis, LocalDateTime dataHoraPrevista) {
		super();
		this.quantidadeHorasUteis = quantidadeHorasUteis;
		this.dataHoraPrevista = dataHoraPrevista;
		this.quantidadeDiasUteis = this.getQuantidadeDiasUteis();
	}

	public SlaDTO(LocalDateTime dataHoraPrevista, Integer quantidadeDiasUteis, Integer quantidadeHorasUteis) {
		this.dataHoraPrevista = dataHoraPrevista;
		this.quantidadeDiasUteis = quantidadeDiasUteis;
		this.quantidadeHorasUteis = quantidadeHorasUteis;
	}

	public Integer getQuantidadeHorasUteis() {
		return quantidadeHorasUteis;
	}

	public void setQuantidadeHorasUteis(Integer quantidadeHorasUteis) {
		this.quantidadeHorasUteis = quantidadeHorasUteis;
	}

	public Integer getQuantidadeDiasUteis() {
		return quantidadeDiasUteis;
	}

	public void setQuantidadeDiasUteis(Integer quantidadeDiasUteis) {
		this.quantidadeDiasUteis = quantidadeDiasUteis;
	}

	public NivelAtendimentoEnum getNivelAtendimento() {
		return nivelAtendimento;
	}

	public void setNivelAtendimento(NivelAtendimentoEnum nivelAtendimento) {
		this.nivelAtendimento = nivelAtendimento;
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getSiglaModeloSolucao() {
		return siglaModeloSolucao;
	}

	public void setSiglaModeloSolucao(String siglaModeloSolucao) {
		this.siglaModeloSolucao = siglaModeloSolucao;
	}

	public EnderecoDTO getDadosEndereco() {
		return dadosEndereco;
	}

	public void setDadosEndereco(EnderecoDTO dadosEndereco) {
		this.dadosEndereco = dadosEndereco;
	}

	public List<DiaHorarioFuncionamentoDTO> getListaDiaHorarioFuncionamento() {
		return listaDiaHorarioFuncionamento;
	}

	public void setListaDiaHorarioFuncionamento(List<DiaHorarioFuncionamentoDTO> listaDiaHorarioFuncionamento) {
		this.listaDiaHorarioFuncionamento = listaDiaHorarioFuncionamento;
	}

	public TipoServicoEnum getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServicoEnum tipoServico) {
		this.tipoServico = tipoServico;
	}

	public Boolean isHorarioFuncionamentoIdentificado() {
		return isHorarioFuncionamentoIdentificado;
	}

	public void setIsHorarioFuncionamentoIdentificado(Boolean isHorarioFuncionamentoIdentificado) {
		this.isHorarioFuncionamentoIdentificado = isHorarioFuncionamentoIdentificado;
	}

	public LocalDateTime getDataHoraPrevista() {
		return dataHoraPrevista;
	}

	public void setDataHoraPrevista(LocalDateTime dataHoraPrevista) {
		this.dataHoraPrevista = dataHoraPrevista;
	}

	@Override
	public String toString() {
		return "SlaDTO [nivelAtendimento=" + nivelAtendimento + ", codigoCliente=" + codigoCliente
				+ ", siglaModeloSolucao=" + siglaModeloSolucao + ", dadosEndereco=" + dadosEndereco
				+ ", listaDiaHorarioFuncionamento=" + listaDiaHorarioFuncionamento + ", tipoServico=" + tipoServico
				+ ", isHorarioFuncionamentoIdentificado=" + isHorarioFuncionamentoIdentificado + ", dataHoraPrevista="
				+ dataHoraPrevista + ", quantidadeHorasUteis=" + quantidadeHorasUteis + ", quantidadeDiasUteis="
				+ quantidadeDiasUteis + "]";
	}

}
