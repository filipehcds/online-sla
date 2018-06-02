package br.com.cielo.sigo.dto;

import java.util.List;

/**
 * DTO de lista do Horario de Funcionamento
 * 
 * @author alexandre.oliveira
 *
 */
public class HorarioFuncionamentoListaDTO {

	/**
	 * Lista de horários de funcionamento
	 */
	private List<HorarioFuncionamentoDTO> listaHorarioFuncionamento;
	
	public HorarioFuncionamentoListaDTO(List<HorarioFuncionamentoDTO> listaHorarioFuncionamento) {
		super();
		this.listaHorarioFuncionamento = listaHorarioFuncionamento;
	}

	public List<HorarioFuncionamentoDTO> getListaHorarioFuncionamento() {
		return listaHorarioFuncionamento;
	}

	public void setListaHorarioFuncionamento(List<HorarioFuncionamentoDTO> listaHorarioFuncionamento) {
		this.listaHorarioFuncionamento = listaHorarioFuncionamento;
	}

	@Override
	public String toString() {
		return "HorarioFuncionamentoListaDTO [listaHorarioFuncionamento=" + listaHorarioFuncionamento + "]";
	}

}
