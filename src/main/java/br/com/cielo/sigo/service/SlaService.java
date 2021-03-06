package br.com.cielo.sigo.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.cielo.sigo.dto.DiaHorarioFuncionamentoDTO;
import br.com.cielo.sigo.dto.ParametrizacaoSlaDTO;
import br.com.cielo.sigo.dto.SlaDTO;
import br.com.cielo.sigo.model.DiaSemanaEnum;
import br.com.cielo.sigo.model.NivelAtendimentoEnum;
import br.com.cielo.sigo.repository.ParametrizacaoSlaRepository;
import br.com.cielo.sigo.service.exception.ErrosEnum;
import br.com.cielo.sigo.service.exception.SlaNaoEncontradoException;

/**
 * Classe de serviço para resolução da parametrização de SLA
 * 
 * @author alexandre.oliveira
 * @author leticia.moura
 * @since 27/04/2018
 */
@Service

public class SlaService {
	//variavel de controle para feriado
	boolean feriadoEncontrado = false;
	//variavel de controle de nível
	private int n;
	//private boolean n2_2223 = false;

	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(SlaService.class);

	private static float hrAdicionalN2 = 0;

	/**
	 * Serviço de feriado
	 */
	@Autowired
	private FeriadoService feriadoService;

	/**
	 * Repository da entnidade ParametrizacaoSla
	 */
	@Autowired
	private ParametrizacaoSlaRepository parametrizacaoSlaRepository;

	/**
	 * MessageSource do Spring utilizado para efeito de i18n
	 */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Define o pattern para formatar as datas para efeito de depuração
	 */
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd (E) HH:mm");

	/**
	 * Calcula a data prevista de atendimento com base na parametrização de SLA vs horário de funcionamento do cliente
	 * 
	 * @param slaDTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SlaDTO calcularSla(SlaDTO slaDTO) {

		// obtém a lista de feriados federais, estaduais e federais
		List<LocalDate> listaFeriados = feriadoService.obterListaFeriadosMunicipaisEstaduaisNacionaisAnoCorrenteEProximo(slaDTO.getDadosEndereco());
		LocalDateTime dataHoraAbertura = LocalDateTime.now().withSecond(0).withNano(0);

		LOG.debug("ABERTURA DO CHAMADO: " + dataHoraAbertura.format(formatter));

		/* List<ParametrizacaoSlaDTO> listaParametrizacaoSla = parametrizacaoSlaRepository.obterParametrizacaoSla(
				slaDTO.getCodigoCliente(), 
				slaDTO.getSiglaModeloSolucao(), 
				slaDTO.getDadosEndereco().getCidade(), 
				slaDTO.getDadosEndereco().getEstado(),
				slaDTO.getNivelAtendimento().getCodigo()); */
		// obtem a quantidade de horas de SLA com base no filtro informado
		List<ParametrizacaoSlaDTO> listaParametrizacaoSla = parametrizacaoSlaRepository.obterQuantidadeHorasSla(
				slaDTO.getAreaGeografica(),
				Integer.parseInt(slaDTO.getTipoSolucaoCaptura()),
				Integer.parseInt(slaDTO.getPacote()),
				slaDTO.getNivelAtendimento().getCodigo(),
				Integer.parseInt(slaDTO.getSegmento()));
		n = slaDTO.getNivelAtendimento().getCodigo();
		if (listaParametrizacaoSla.isEmpty()) {

			throw new SlaNaoEncontradoException(ErrosEnum.SLA_NAO_IDENTIFICADO, messageSource);

		}

		// dados da SLA deve ser única, logo, retorna a primeira posição da lista
		ParametrizacaoSlaDTO parametrizacaoSla = listaParametrizacaoSla.get(0);
		Integer quantidadeHorasSla = parametrizacaoSla.getQuantidadeHorasSla();

		// independente do nível de atendimento (em campo ou segundo nível), adicionar 1h em cima da SLA real
		if (!slaDTO.isHorarioFuncionamentoIdentificado()) {

			//FIXME TODO essa consistência não pode ser feita em código. É necessário existir alguma parametrização em banco de dados para para contemplar essa "gordura" de SLA pois isso pode ser alterado 
			quantidadeHorasSla += 1;

		}

		// calcula o prazo de atendimento do chamado
		SlaDTO sla = calcularSLA(dataHoraAbertura, slaDTO.getListaDiaHorarioFuncionamento(), quantidadeHorasSla, listaFeriados);

		if (slaDTO.getNivelAtendimento().getCodigo().equals(NivelAtendimentoEnum.SEGUNDO_NIVEL.getCodigo()))
			//			sla.setDataHoraPrevista(sla.getDataHoraPrevista().plusHours(horaDif));

			LOG.debug("PRAZO DE ATEDIMENTO DO CHAMADO: " + sla.getDataHoraPrevista().format(formatter));

		return sla;
	}

	/**
	 * Calcula a SLA de forma recursiva
	 * 
	 * @param dataHoraAbertura
	 * @param listaHorariosFuncionamento
	 * @param quantidadeHorasSLA
	 * @param listaFeriados
	 * @return
	 */
	public SlaDTO calcularSLA(LocalDateTime dataHoraAbertura,
			List<DiaHorarioFuncionamentoDTO> listaHorariosFuncionamento, int quantidadeHorasSLA, List<LocalDate> listaFeriados) {

		LocalDateTime dataHoraCalculada = dataHoraAbertura;

		LocalDateTime dataHoraUtil = dataHoraCalculada;
		int quantidadeDiasUteis = 0;

		// obtem o horario de funcionamento com base na dataHoraAbertura do chamado
		DiaHorarioFuncionamentoDTO horarioFuncionamento = obterHorarioFuncionamento(dataHoraAbertura, listaHorariosFuncionamento);

		// se a hora da abertura do chamado for após o horario que o estabelecimento fecha, de um dia que abre 
		// define o horario de funcionamento como nulo, assim irá buscar o próximo horario de funcionamento válido
		if(horarioFuncionamento != null) {

			if(dataHoraAbertura.toLocalTime().isAfter(horarioFuncionamento.getHorarioFinal())) {

				horarioFuncionamento = null;

			}

		}

		
		// procura o proximo horario de funcionamento valido
		// adicionado para descontar os feriados , feriadoEncontrado variavel boolean
		while (horarioFuncionamento == null || feriadoEncontrado) {
			dataHoraCalculada = dataHoraCalculada.plusDays(1);
			horarioFuncionamento = obterHorarioFuncionamento(dataHoraCalculada, listaHorariosFuncionamento);
			//caso encontre feriado, mudo o valor do boolean para true com isso ele vai ficar no looping até achar um horario válido
			feriadoEncontrado = listaFeriados.contains(dataHoraCalculada.toLocalDate());

		}

		// quando encontrar um horario de funcionamento valido, recuperar o horario de abertura e fechamento do estabelecimento
		LocalTime horarioInicialFuncionamento = horarioFuncionamento.getHorarioInicial();
		LocalTime horarioFinalFuncionamento = horarioFuncionamento.getHorarioFinal();

		// com o horario de funcionamento valido para começar a adicionar hora, valida está fora do horário comercial
		// se estiver, posiciona a dataHora base para o horario que o estabelecimento abre

		if(  !isHorarioComercial(dataHoraAbertura, horarioInicialFuncionamento, horarioFinalFuncionamento, listaFeriados, horarioFuncionamento) ) {

			dataHoraCalculada = LocalDateTime.of(dataHoraCalculada.toLocalDate(), horarioInicialFuncionamento);
			quantidadeDiasUteis++;

		}else {

			// adiciona um dia util se o chamado for abre dentro do prazo de atendimento			
			if(dataHoraAbertura.toLocalDate().equals(dataHoraCalculada.toLocalDate())) {
				quantidadeDiasUteis++;	
			}

		}

		// adicona uma hora para cada sla
		for (int i = 0; i < quantidadeHorasSLA + hrAdicionalN2; i++) {			

			// a partir da segunda hora, igualar a dataHoraUtil para identificar quando a proxima hora for em outro dia 
			if (i > 0) {
				dataHoraUtil = dataHoraCalculada;
			}

			// adiciona uma hora no prazo de atendimento  
			dataHoraCalculada = adicionarHoraSLA(dataHoraCalculada, listaHorariosFuncionamento, 0, listaFeriados);

			// depois de adicionar mais uma hora ao prazo de atendimento a data for diferente da hora anterior, significa que é uma novo dia
			// incrementa um dia na quantidade de dias úteis
			if (!dataHoraCalculada.toLocalDate().equals(dataHoraUtil.toLocalDate()) && i > 0) {
				quantidadeDiasUteis++;
			}

			LOG.debug((i + 1) +"° HORA ADICIONADA: " + dataHoraCalculada.format(formatter));

		}
		//System.out.println(quantidadeHorasSLA);
		LOG.debug("QUANTIDADE DE HORAS UTEIS: " + quantidadeHorasSLA);
		LOG.debug("QUANTIDADE DE DIAS UTEIS: " + quantidadeDiasUteis);

		return new SlaDTO(dataHoraCalculada, quantidadeDiasUteis, quantidadeHorasSLA);
	}

	/**
	 * Avança 1h na data com base no horário de funcionamento
	 * 
	 * @param dataHoraCalculada
	 * @param listaHorariosFuncionamento
	 * @param restante
	 * @param listaFeriados
	 * @return
	 */
	private LocalDateTime adicionarHoraSLA(LocalDateTime dataHoraCalculada,
			List<DiaHorarioFuncionamentoDTO> listaHorariosFuncionamento, long restante, List<LocalDate> listaFeriados) {

		// obtem o horario de funcionamento do estabelecimento com base na dataHora base, definida antes da adição de hora/sla
		DiaHorarioFuncionamentoDTO horarioFuncionamento = obterHorarioFuncionamento(dataHoraCalculada, listaHorariosFuncionamento);

		// procura o próximo horario de funcionamento válido, é necessário para os cenários em que o estabelecimento não abre no dia posterior
		// por exemplo: na primeira hora de um feriado
		//adicionado a logica de controle de feriados para ser descontado o feriado,  feriadoEncontrado variavel boolean
		while (horarioFuncionamento == null || feriadoEncontrado) {

			dataHoraCalculada = dataHoraCalculada.plusDays(1);
			horarioFuncionamento = obterHorarioFuncionamento(dataHoraCalculada, listaHorariosFuncionamento);
			// caso haja feriado seto meu boolean feriadoEncontrado para true
			feriadoEncontrado = listaFeriados.contains(dataHoraCalculada.toLocalDate());

		}		

		// quando encontrar um horario de funcionamento valido, recuperar o horario de abertura e fechamento do estabelecimento
		LocalTime horarioInicialFuncionamento = horarioFuncionamento.getHorarioInicial();
		LocalTime horarioFinalFuncionamento = horarioFuncionamento.getHorarioFinal();
		
		
		//solução de contorno, verfifico se o horario fianl de funcionamento do cliente é após as 22 horas(que é o horario de funcionamento da cielo),se for eu forço 
		//para as 22 horas e calculo o sla em cima desse horario, confiro támbem se o chamado é nivel 2 
	
			if(  horarioInicialFuncionamento.getHour() == 22 && n == 2 ) {
				horarioInicialFuncionamento = horarioInicialFuncionamento.minusHours(1);
			}
			if(  horarioFinalFuncionamento.getHour() == 23 && n == 2 ) {
				horarioFinalFuncionamento = horarioFinalFuncionamento.minusHours(1);
			}
			if(  horarioFinalFuncionamento.getHour() == 0&& n == 2 ) {
				horarioFinalFuncionamento = horarioFinalFuncionamento.minusHours(2);
			}
		
			

		// com a dataHora base, valida se é possível adicionar uma hora do prazo de atendimento, dentro do horário de funcionamento				
		if (isHorarioComercial(dataHoraCalculada.plusHours(1), horarioInicialFuncionamento, horarioFinalFuncionamento, listaFeriados, horarioFuncionamento) ) {

			// antes de adicionar uma hora, recupera a primeira hora válida, menos uma
			LocalTime primeiraHora = dataHoraCalculada.toLocalTime().minusHours(1);
			//			LocalTime primeiraHora = horarioInicialFuncionamento.minusHours(1);

			// se a posição a primeria hora valida do horario de funcionamento
			if (primeiraHora.isBefore(horarioInicialFuncionamento) && dataHoraCalculada.toLocalTime().isBefore(horarioInicialFuncionamento)) {
				//			if (primeiraHora.isBefore(horarioInicialFuncionamento)) {

				// verifica se no dia anterior sobrou minutos para ser considerado na primeira hora
				if (restante == 0) {

					// a quantidade de minutos restantes é zero, nesse caso, adiciona uma hora a partir do horario de abertura do estabelecimento
					dataHoraCalculada = LocalDateTime.of(dataHoraCalculada.toLocalDate(), horarioInicialFuncionamento.plusHours(1));

				} else {

					// a quantidade de minutos restantes é maior que zero, nesse caso, adiciona os minutos que faltam para completar uma hora
					dataHoraCalculada = LocalDateTime.of(dataHoraCalculada.toLocalDate(), horarioInicialFuncionamento.plusMinutes(60 - restante));

				}

			} else {

				// adiciona uma hora
				dataHoraCalculada = dataHoraCalculada.plusHours(1);

			}

		} else {
			// se a dataHora base é a proxima hora, após o fechamento
			if (dataHoraCalculada.toLocalTime().isBefore(horarioFinalFuncionamento) && dataHoraCalculada.plusHours(1).toLocalTime().isAfter(horarioFinalFuncionamento)) {
			
				// calcula a diferenca em minutos, que será descontada na primeira hora, de um horario de funcionamento valido
				long duration = Duration.between(dataHoraCalculada, LocalDateTime.of(dataHoraCalculada.toLocalDate(), horarioFinalFuncionamento)).toMinutes();				
				restante = duration;
				
				// Solução de contorno para adicionar 1 hora adicional no segundo nivel quando o cliente tem o horário de expediente após as 22:00
				DecimalFormat df = new DecimalFormat("#");
				df.setRoundingMode(RoundingMode.CEILING);
				hrAdicionalN2 = Float.valueOf(df.format((duration / 60F)));
				
				LOG.debug("HORAS ADICIONAIS NIVEL 2: " + hrAdicionalN2);
				LOG.debug("DIFERENCA: " + duration);
				
			}		
		
		// chama recursivamente o método para navegar entre as horas fora do horário comercial
		dataHoraCalculada = adicionarHoraSLA(dataHoraCalculada.plusHours(1), listaHorariosFuncionamento, restante, listaFeriados);

			//navego entro os horarios não comerciais
//			long duration = Duration.between(dataHoraCalculada, LocalDateTime.of(dataHoraCalculada.toLocalDate(), horarioFinalFuncionamento)).toMinutes();				
//			restante = duration;
//			DecimalFormat df = new DecimalFormat("#");
//			df.setRoundingMode(RoundingMode.CEILING);
//			hrAdicionalN2 = Float.valueOf(df.format((duration / 60F)));
//			LOG.debug("HORAS ADICIONAIS NIVEL 2: " + hrAdicionalN2);
//			LOG.debug("DIFERENCA: " + duration);
//			dataHoraCalculada = adicionarHoraSLA(dataHoraCalculada.plusHours(1), listaHorariosFuncionamento, restante, listaFeriados);
		}
		//confiro se tem algum feriado antes de chamar o retonar  a hora, caso exista este boolean servira para entrar no loop de calculo de horas
		//no metodo calcularSLA
		feriadoEncontrado = listaFeriados.contains(dataHoraCalculada.toLocalDate());
		return dataHoraCalculada;
	}

	/**
	 * Obtem dados referente ao dia e horarios de funcionamento com base na data
	 * 
	 * @param dataHoraAbertura
	 * @param listaHorariosFuncionamento
	 * @return
	 */
	private DiaHorarioFuncionamentoDTO obterHorarioFuncionamento(LocalDateTime dataHoraAbertura,
			List<DiaHorarioFuncionamentoDTO> listaHorariosFuncionamento) {

		DiaHorarioFuncionamentoDTO horarioFuncionamentoEstabelecimento = null;

		for (DiaHorarioFuncionamentoDTO horarioFuncionamento : listaHorariosFuncionamento) {

			if(DiaSemanaEnum.obterDayOfWeek(horarioFuncionamento.getDia().getNumeroDia()).equals(dataHoraAbertura.getDayOfWeek())) {

				horarioFuncionamentoEstabelecimento = horarioFuncionamento;

			}
		}

		return horarioFuncionamentoEstabelecimento;
	}

	/**
	 * Verifica se a data está contida dentro do horário comercial do cliente
	 * 
	 * @param dataHoraAbertura
	 * @param horarioInicialFuncionamento
	 * @param horarioFinalFuncionamento
	 * @param listaFeriados
	 * @return
	 */
	private boolean isHorarioComercial(LocalDateTime dataHoraAbertura, 
			LocalTime horarioInicialFuncionamento, LocalTime horarioFinalFuncionamento, List<LocalDate> listaFeriados, DiaHorarioFuncionamentoDTO horarioFuncionamento) {

		boolean horarioComercialValido = false;

		LocalTime horaAbertura = dataHoraAbertura.toLocalTime();

		if(DiaSemanaEnum.obterDayOfWeek(horarioFuncionamento.getDia().getNumeroDia()).equals(dataHoraAbertura.getDayOfWeek())) {

			if (horaAbertura == null || horarioInicialFuncionamento == null || horarioFinalFuncionamento == null ) {

				horarioComercialValido = false;

			} else {

				horarioComercialValido = horaAbertura.equals(horarioFinalFuncionamento) || 
						(horaAbertura.isAfter(horarioInicialFuncionamento) && horaAbertura.isBefore(horarioFinalFuncionamento));


			}
		}

		return horarioComercialValido;
	}

}