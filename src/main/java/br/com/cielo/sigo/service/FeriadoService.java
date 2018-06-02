package br.com.cielo.sigo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cielo.sigo.dto.EnderecoDTO;
import br.com.cielo.sigo.dto.FeriadoDTO;
import br.com.cielo.sigo.repository.RegiaoFeriadoRepository;

/**
 * Classe de serviço responsável em obter a lista de feriados 
 * 
 * @author alexandre.oliveira
 *
 */
@Service
public class FeriadoService {

	/**
	 * Repository da entidade RegiaoFeriado
	 */
	@Autowired
	private RegiaoFeriadoRepository regiaoFeriadoRepository;
	
	/**
	 * Obtem lista de feriados municipais, estaduais e municipais
	 * 
	 * @param enderecoDTO
	 * @return
	 */
	public List<LocalDate> obterListaFeriadosMunicipaisEstaduaisNacionaisAnoCorrenteEProximo(EnderecoDTO enderecoDTO) {
		
		List<LocalDate> listaDataFeriados = new ArrayList<>();
		
		List<FeriadoDTO> listarRegiaoFeriado = regiaoFeriadoRepository.obterListaRegiaoFeriado(enderecoDTO.getCidade(), enderecoDTO.getEstado(), LocalDate.now().getYear(), LocalDate.now().getYear() + 1);
		if (listarRegiaoFeriado != null && !listarRegiaoFeriado.isEmpty()) {
		
			for (FeriadoDTO regiaoFeriado : listarRegiaoFeriado) {
				listaDataFeriados.add(regiaoFeriado.getData().toLocalDate());
			}
			
		}
		
		return listaDataFeriados;
	}

}
