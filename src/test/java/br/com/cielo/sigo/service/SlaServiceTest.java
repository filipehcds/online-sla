package br.com.cielo.sigo.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.cielo.sigo.dto.DiaHorarioFuncionamentoDTO;
import br.com.cielo.sigo.dto.SlaDTO;
import br.com.cielo.sigo.model.DiaSemanaEnum;

public class SlaServiceTest {

	// define o pattern para formatar as datas
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd (E) HH:mm");

	// 1° Cenário
	// Horário de funcionamento estabelecimento comercial: SEG à SEX, das 08:00 as 18:00
	// Hora da abertura do chamado anterior ao horario de abertura do estabelecimento: 07:39
	// 12 horas de SLA
	// Prazo de Atendimento é: 2018-04-24 (Ter) 10:00 
	
	@Test
	public void testAberturaChamadoAnteriorAoHorarioAberturaEstabelecimento() {
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("                        1° CENARIO                         ");
		System.out.println("-----------------------------------------------------------");
		
		// quantidade de SLA
		int quantidadeHorasSLA = 12;
	
		// cria dataHora de abertura do chamado
		LocalDateTime dataHoraAbertura = LocalDateTime.of(2018, 4, 23, 7, 39);
		
		// cria a lista de horario de funcionamento do estabelecimento
		List<DiaHorarioFuncionamentoDTO> listaHorariosFuncionamento = new ArrayList<>();				
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.TERCA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.QUARTA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.QUINTA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.SEXTA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		
		// calcula o SLA
		System.out.println("DATA HORA DE ABERTURA DO CHAMANDO: " +  dataHoraAbertura.format(formatter));
		SlaDTO sla = new SlaService().calcularSLA(dataHoraAbertura, listaHorariosFuncionamento, quantidadeHorasSLA, obterListaFeriados());
		System.out.println("DATA HORA DE PRAZO ATENDIMENTO DO CHAMANDO: " +  sla.getDataHoraPrevista().format(formatter));
		
		System.out.println("-----------------------------------------------------------\n");
		
		// valida se o prazo retornado do calculo é igual ao esperado (2018-04-24 (Ter) 10:00)
		boolean calculoCorretoSLA = sla.getDataHoraPrevista().equals(LocalDateTime.of(2018, 4, 24, 10, 0));
		
		if(calculoCorretoSLA) {
			assertTrue(calculoCorretoSLA);
		} else {
			fail("Prazo de Atendimento diferente o esperado (2018-04-24 (Ter) 10:00)");
		}		
	}
	
	
	// 2° Cenário
	// Horário de funcionamento estabelecimento comercial: SEG das 08:00 as 18:00 e QUA das 08:30 as 18:30
	// Hora da abertura do chamado anterior ao horario de abertura do estabelecimento: 07:39
	// 12 horas de SLA
	// Prazo de Atendimento é: 2018-04-25 (Qua) 10:30 
	
	@Test
	public void testAberturaChamadoAnteriorAoHorarioAberturaEstabelecimentoComFuncionamentoIntercalado() {
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("                        2° CENARIO                         ");
		System.out.println("-----------------------------------------------------------");
		
		// quantidade de SLA
		int quantidadeHorasSLA = 12;
	
		// cria dataHora de abertura do chamado
		LocalDateTime dataHoraAbertura = LocalDateTime.of(2018, 4, 23, 7, 39);
		
		// cria a lista de horario de funcionamento do estabelecimento
		List<DiaHorarioFuncionamentoDTO> listaHorariosFuncionamento = new ArrayList<>();				
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.QUARTA, LocalTime.of(8, 30), LocalTime.of(18, 30)));
		
		// calcula o SLA
		System.out.println("DATA HORA DE ABERTURA DO CHAMANDO: " +  dataHoraAbertura.format(formatter));
		SlaDTO sla = new SlaService().calcularSLA(dataHoraAbertura, listaHorariosFuncionamento, quantidadeHorasSLA, obterListaFeriados());
		System.out.println("DATA HORA DE PRAZO ATENDIMENTO DO CHAMANDO: " +  sla.getDataHoraPrevista().format(formatter));
		
		System.out.println("-----------------------------------------------------------\n");
		
		// valida se o prazo retornado do calculo é igual ao esperado (2018-04-25 (Qua) 10:30)
		boolean calculoCorretoSLA = sla.getDataHoraPrevista().equals(LocalDateTime.of(2018, 4, 25, 10, 30));
		
		if(calculoCorretoSLA) {
			assertTrue(calculoCorretoSLA);
		} else {
			fail("Prazo de Atendimento diferente o esperado (2018-04-25 (Qua) 10:30)");
		}		
	}

	// 3° Cenário
	// Horário de funcionamento estabelecimento comercial: SEG à SEX, das 08:00 as 18:00
	// Hora da abertura do chamado dentro do horario de abertura do estabelecimento: 08:37
	// 12 horas de SLA
	// Prazo de Atendimento é: 2018-04-24 (Ter) 10:37 
	
	@Test
	public void testAberturaChamadoDentroDoHorarioAberturaEstabelecimento() {
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("                        3° CENARIO                         ");
		System.out.println("-----------------------------------------------------------");
		
		// quantidade de SLA
		int quantidadeHorasSLA = 12;
	
		// cria dataHora de abertura do chamado
		LocalDateTime dataHoraAbertura = LocalDateTime.of(2018, 4, 23, 8, 37);
		
		// cria a lista de horario de funcionamento do estabelecimento
		List<DiaHorarioFuncionamentoDTO> listaHorariosFuncionamento = new ArrayList<>();				
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.TERCA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.QUARTA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.QUINTA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.SEXTA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		
		// calcula o SLA
		System.out.println("DATA HORA DE ABERTURA DO CHAMANDO: " +  dataHoraAbertura.format(formatter));
		SlaDTO sla = new SlaService().calcularSLA(dataHoraAbertura, listaHorariosFuncionamento, quantidadeHorasSLA, obterListaFeriados());
		System.out.println("DATA HORA DE PRAZO ATENDIMENTO DO CHAMANDO: " +  sla.getDataHoraPrevista().format(formatter));
		
		System.out.println("-----------------------------------------------------------\n");
		
		// valida se o prazo retornado do calculo é igual ao esperado (2018-04-24 (Ter) 10:37)
		boolean calculoCorretoSLA = sla.getDataHoraPrevista().equals(LocalDateTime.of(2018, 4, 24, 10, 37));
		
		if(calculoCorretoSLA) {
			assertTrue(calculoCorretoSLA);
		} else {
			fail("Prazo de Atendimento diferente o esperado (2018-04-24 (Ter) 10:37)");
		}		
	}

	// 4° Cenário
	// Horário de funcionamento estabelecimento comercial: SEG das 08:00 as 18:00 e QUA das 08:30 as 18:30
	// Hora da abertura do chamado dentro do horario de abertura do estabelecimento: 08:37
	// 12 horas de SLA
	// Prazo de Atendimento é: 2018-04-25 (Qua) 11:07 
	
	@Test
	public void testAberturaChamadoDentroDoHorarioAberturaEstabelecimentoComFuncionamentoIntercalado() {
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("                        4° CENARIO                         ");
		System.out.println("-----------------------------------------------------------");
		
		// quantidade de SLA
		int quantidadeHorasSLA = 12;
	
		// cria dataHora de abertura do chamado
		LocalDateTime dataHoraAbertura = LocalDateTime.of(2018, 4, 23, 8, 37);
		
		// cria a lista de horario de funcionamento do estabelecimento
		List<DiaHorarioFuncionamentoDTO> listaHorariosFuncionamento = new ArrayList<>();				
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.QUARTA, LocalTime.of(8, 30), LocalTime.of(18, 30)));
		
		// calcula o SLA
		System.out.println("DATA HORA DE ABERTURA DO CHAMANDO: " +  dataHoraAbertura.format(formatter));
		SlaDTO sla = new SlaService().calcularSLA(dataHoraAbertura, listaHorariosFuncionamento, quantidadeHorasSLA, obterListaFeriados());
		System.out.println("DATA HORA DE PRAZO ATENDIMENTO DO CHAMANDO: " +  sla.getDataHoraPrevista().format(formatter));
		
		System.out.println("-----------------------------------------------------------\n");
		
		// valida se o prazo retornado do calculo é igual ao esperado (2018-04-25 (Qua) 11:07)
		boolean calculoCorretoSLA = sla.getDataHoraPrevista().equals(LocalDateTime.of(2018, 4, 25, 11, 07));
		
		if(calculoCorretoSLA) {
			assertTrue(calculoCorretoSLA);
		} else {
			fail("Prazo de Atendimento diferente o esperado (2018-04-25 (Qua) 11:07)");
		}		
	}


	// 5° Cenário
	// Horário de funcionamento estabelecimento comercial: SEG à SEX, das 08:00 as 18:00
	// Hora da abertura do chamado fora do horario de abertura do estabelecimento: 19:10
	// 12 horas de SLA
	// Prazo de Atendimento é: 2018-04-25 (Qua) 10:00 
	
	@Test
	public void testAberturaChamadoForaDoHorarioAberturaEstabelecimento() {
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("                        5° CENARIO                         ");
		System.out.println("-----------------------------------------------------------");
		
		// quantidade de SLA
		int quantidadeHorasSLA = 12;
	
		// cria dataHora de abertura do chamado
		LocalDateTime dataHoraAbertura = LocalDateTime.of(2018, 4, 23, 19, 10);
		
		// cria a lista de horario de funcionamento do estabelecimento
		List<DiaHorarioFuncionamentoDTO> listaHorariosFuncionamento = new ArrayList<>();				
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.TERCA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.QUARTA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.QUINTA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.SEXTA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		
		// calcula o SLA
		System.out.println("DATA HORA DE ABERTURA DO CHAMANDO: " +  dataHoraAbertura.format(formatter));
		SlaDTO sla = new SlaService().calcularSLA(dataHoraAbertura, listaHorariosFuncionamento, quantidadeHorasSLA, obterListaFeriados());
		System.out.println("DATA HORA DE PRAZO ATENDIMENTO DO CHAMANDO: " +  sla.getDataHoraPrevista().format(formatter));
		
		System.out.println("-----------------------------------------------------------\n");
		
		// valida se o prazo retornado do calculo é igual ao esperado (2018-04-25 (Qua) 10:00)
		boolean calculoCorretoSLA = sla.getDataHoraPrevista().equals(LocalDateTime.of(2018, 4, 25, 10, 0));
		
		if(calculoCorretoSLA) {
			assertTrue(calculoCorretoSLA);
		} else {
			fail("Prazo de Atendimento diferente o esperado (2018-04-25 (Qua) 10:00)");
		}		
	}
	
	// 6° Cenário
	// Horário de funcionamento estabelecimento comercial: SEG das 08:00 as 18:00 e QUA das 08:30 as 18:30
	// Hora da abertura do chamado fora do horario de abertura do estabelecimento: 19:10
	// 12 horas de SLA
	// Com feriado no dia 2018-04-30
	// Prazo de Atendimento é: 2018-05-02 (Qua) 10:30 
	
	@Test
	public void testAberturaChamadoForaDoHorarioAberturaEstabelecimentoComFuncionamentoIntercalado() {
		
		System.out.println("-----------------------------------------------------------");
		System.out.println("                        6° CENARIO                         ");
		System.out.println("-----------------------------------------------------------");
		
		// quantidade de SLA
		int quantidadeHorasSLA = 12;
	
		// cria dataHora de abertura do chamado
		LocalDateTime dataHoraAbertura = LocalDateTime.of(2018, 4, 23, 19, 10);
		
		// cria a lista de horario de funcionamento do estabelecimento
		List<DiaHorarioFuncionamentoDTO> listaHorariosFuncionamento = new ArrayList<>();				
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.SEGUNDA, LocalTime.of(8, 0), LocalTime.of(18, 0)));
		listaHorariosFuncionamento.add(new DiaHorarioFuncionamentoDTO(DiaSemanaEnum.QUARTA, LocalTime.of(8, 30), LocalTime.of(18, 30)));
		
		// calcula o SLA
		System.out.println("DATA HORA DE ABERTURA DO CHAMANDO: " +  dataHoraAbertura.format(formatter));
		SlaDTO sla = new SlaService().calcularSLA(dataHoraAbertura, listaHorariosFuncionamento, quantidadeHorasSLA, obterListaFeriados());
		System.out.println("DATA HORA DE PRAZO ATENDIMENTO DO CHAMANDO: " +  sla.getDataHoraPrevista().format(formatter));
		
		System.out.println("-----------------------------------------------------------\n");
		
		// valida se o prazo retornado do calculo é igual ao esperado (2018-05-02 (Qua) 10:30)
		boolean calculoCorretoSLA = sla.getDataHoraPrevista().equals(LocalDateTime.of(2018, 5, 2, 10, 30));
		
		if(calculoCorretoSLA) {
			assertTrue(calculoCorretoSLA);
		} else {
			fail("Prazo de Atendimento diferente o esperado (2018-05-02 (Qua) 10:30)");
		}		
	}

	public static List<LocalDate> obterListaFeriados() {
		List<LocalDate> listaFeriados = new ArrayList<>();

		listaFeriados.add(LocalDate.of(2018, Month.APRIL, 28));
		listaFeriados.add(LocalDate.of(2018, Month.APRIL, 30));

		return listaFeriados;
	}

}
