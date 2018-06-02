package br.com.cielo.sigo.dto;

import java.time.LocalTime;

import br.com.cielo.sigo.model.DiaSemanaEnum;

/**
 * DTO DiaHorarioFuncionamentoDTO
 * 
 * @author alexandre.oliveira
 * @since 27/04/2018
 *
 */
public class DiaHorarioFuncionamentoDTO {

	/**
	 * Dia da semana
	 */
	private DiaSemanaEnum dia;
	
	/**
	 * Horário inicial
	 */
	private LocalTime horarioInicial;
	
	/**
	 * Horário final
	 */
	private LocalTime horarioFinal;

	public DiaHorarioFuncionamentoDTO() {
		super();
	}
	
	public DiaHorarioFuncionamentoDTO(DiaSemanaEnum dia, LocalTime horarioInicial, LocalTime horarioFinal) {
		super();
		this.dia = dia;
		this.horarioInicial = horarioInicial;
		this.horarioFinal = horarioFinal;
	}

	/*
	 * Getters & Setters
	 */
	public DiaSemanaEnum getDia() {
		return dia;
	}

	public LocalTime getHorarioInicial() {
		return horarioInicial;
	}

	public LocalTime getHorarioFinal() {
		return horarioFinal;
	}

	@Override
	public String toString() {
		return "DiaHorarioFuncionamentoDTO [dia=" + dia + ", horarioInicial=" + horarioInicial + ", horarioFinal="
				+ horarioFinal + "]";
	}

}