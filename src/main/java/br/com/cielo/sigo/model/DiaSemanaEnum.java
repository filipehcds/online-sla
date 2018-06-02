package br.com.cielo.sigo.model;

import java.time.DayOfWeek;

/**
 * Representa os dias da semana utilizado tanto na pesquisa quanto no resultado.
 * 
 * @author alexandre.oliveira
 * @since 28/04/2018
 *
 */
public enum DiaSemanaEnum {

	NA(0), SEGUNDA(1), TERCA(2), QUARTA(3), QUINTA(4), SEXTA(5), SABADO(6), DOMINGO(7);

	/**
	 * Número que representa um determinado dia da semana
	 */
	final int numeroDia;

	private DiaSemanaEnum(int numeroDia) {
		this.numeroDia = numeroDia;
	}

	public int getNumeroDia() {
		return numeroDia;
	}
	
	/**
	 * Obtém a Enum referente ao dia da semana filtrado pelo número
	 * 
	 * @param numeroDia
	 * @return
	 */
	public static DiaSemanaEnum obterDiaSemanaPeloNumero(final int numeroDia) {
		for (DiaSemanaEnum diaSemana : DiaSemanaEnum.values()) {
			if (diaSemana.numeroDia == numeroDia) {
				return diaSemana;
			}
		}
		
		return NA;
	}
	
	public static DayOfWeek obterDayOfWeek(final int numeroDia) {
		for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
			if (dayOfWeek.getValue() == numeroDia) {
				return dayOfWeek;
			}
		}
		
		return null;
	}

}
