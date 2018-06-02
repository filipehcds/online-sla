package br.com.cielo.sigo.dto;

import java.io.Serializable;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.cielo.sigo.model.DiaSemanaEnum;

/**
 * DTO do Horario de Funcionamento
 * 
 * @author alexandre.oliveira
 *
 */
public class HorarioFuncionamentoDTO implements Serializable {

	private static final long serialVersionUID = -5524681879431999291L;

	/**
	 * Código do horário de funcionamento representa por letras ou uma máscara pré determinada
	 */
	private String codigo;
	
	/**
	 * Dia do início da pesquisa
	 */
	private DiaSemanaEnum diaInicio;
	
	/**
	 * Dia do fim da pesquisa
	 */
	private DiaSemanaEnum diaFim;
	
	/**
	 * Horário de início da pesquisa
	 */
	@DateTimeFormat(pattern = "kk:mm:ss")
	private LocalTime horarioInicio;
	
	/**
	 * Horário de fim da pesquisa
	 */
	@DateTimeFormat(pattern = "kk:mm:ss")
	private LocalTime horarioFim;
	
	/**
	 * Horário de início utilizada para pesquisar horario de funcionamento no Sábado
	 */
	@DateTimeFormat(pattern = "kk:mm:ss")
	private LocalTime horarioInicioSabado;
	
	/**
	 * Horário de fim utilizada para pesquisar horário de funcionamento no Sábado
	 */
	@DateTimeFormat(pattern = "kk:mm:ss")
	private LocalTime horarioFimSabado;
	
	/**
	 * Horário de início utilizada para pesquisar horario de funcionamento no Domingo
	 */
	@DateTimeFormat(pattern = "kk:mm:ss")
	private LocalTime horarioInicioDomingo;
	
	/**
	 * Horário de início utilizada para pesquisar horario de funcionamento no Domingo
	 */
	@DateTimeFormat(pattern = "kk:mm:ss")
	private LocalTime horarioFimDomingo;
	
	/**
	 * Indicador que representa se o resultado da pesquisa é aproximado ao horário de funcionamento do cliente
	 */
	private boolean isHorarioFuncionamentoAproximado;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public DiaSemanaEnum getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(DiaSemanaEnum diaInicio) {
		this.diaInicio = diaInicio;
	}

	public DiaSemanaEnum getDiaFim() {
		return diaFim;
	}

	public void setDiaFim(DiaSemanaEnum diaFim) {
		this.diaFim = diaFim;
	}

	public LocalTime getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(LocalTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public LocalTime getHorarioFim() {
		return horarioFim;
	}

	public void setHorarioFim(LocalTime horarioFim) {
		this.horarioFim = horarioFim;
	}

	public LocalTime getHorarioInicioSabado() {
		return horarioInicioSabado;
	}

	public void setHorarioInicioSabado(LocalTime horarioInicioSabado) {
		this.horarioInicioSabado = horarioInicioSabado;
	}

	public LocalTime getHorarioFimSabado() {
		return horarioFimSabado;
	}

	public void setHorarioFimSabado(LocalTime horarioFimSabado) {
		this.horarioFimSabado = horarioFimSabado;
	}

	public LocalTime getHorarioInicioDomingo() {
		return horarioInicioDomingo;
	}

	public void setHorarioInicioDomingo(LocalTime horarioInicioDomingo) {
		this.horarioInicioDomingo = horarioInicioDomingo;
	}

	public LocalTime getHorarioFimDomingo() {
		return horarioFimDomingo;
	}

	public void setHorarioFimDomingo(LocalTime horarioFimDomingo) {
		this.horarioFimDomingo = horarioFimDomingo;
	}

	public boolean isHorarioFuncionamentoAproximado() {
		return isHorarioFuncionamentoAproximado;
	}

	public void setHorarioFuncionamentoAproximado(boolean isHorarioFuncionamentoAproximado) {
		this.isHorarioFuncionamentoAproximado = isHorarioFuncionamentoAproximado;
	}
	
}
