package br.com.cielo.sigo.model;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entidade que representa a tabela de HORARIO_FUNCIONAMENTO do banco de dados
 * 
 * @author alexandre.oliveira
 * @since 28/03/2018
 *
 */
@Entity
@Table(name = "TBCFLR_HRAR_FNCM", schema = "CFL")
@NamedNativeQueries({
	@NamedNativeQuery(
			name = "HorarioFuncionamento.buscarPorDiaEHorarioAproximado",
			query = "select top 1 hf.* from\n" + 
					"CFL.TBCFLR_HRAR_FNCM hf where \n" + 
					"hf.CD_DIA_SMNA_INCO = :numeroDiaInicio and\n" + 
					"(hf.CD_DIA_SMNA_FIM = :numeroDiaFim or (:numeroDiaFim is null and hf.CD_DIA_SMNA_FIM is null)) and\n" + 
					"(:horarioInicio between hf.HR_INCO_PRDO_TBHO and dateadd(HOUR, 2, hf.HR_INCO_PRDO_TBHO)) and\n" + 
					"(:horarioFim between dateadd(HOUR, -2, hf.HR_FIM_PRDO_TBHO) and hf.HR_FIM_PRDO_TBHO)\n" + 
					"ORDER BY ABS(DATEDIFF(MILLISECOND, HR_FIM_PRDO_TBHO, HR_INCO_PRDO_TBHO)) asc",
			resultClass = HorarioFuncionamento.class)})
public class HorarioFuncionamento implements Serializable {

	private static final long serialVersionUID = 8707429124448129095L;

	/**
	 * Atributo mapeado para a coluna CODIGO da tabela do banco de dados
	 */
	@Id
	@Column(name = "CD_CNTR_HRAR_EXRN")
	private String codigo;

	/**
	 * Atributo mapeado para a coluna DH_DIA_INCO da tabela do banco de dados
	 */
	@Column(name = "CD_DIA_SMNA_INCO")
	private Integer diaInicio;

	/**
	 * Atributo mapeado para a coluna DH_DIA_FIM da tabela do banco de dados
	 */
	@Column(name = "CD_DIA_SMNA_FIM")
	private Integer diaFim;

	/**
	 * Atributo mapeado para a coluna DH_HRAR_INCO da tabela do banco de dados
	 */
	@Column(name = "HR_INCO_PRDO_TBHO")
	private LocalTime horarioInicio;

	/**
	 * Atributo mapeado para a coluna DH_HRAR_FIM da tabela do banco de dados
	 */
	@Column(name = "HR_FIM_PRDO_TBHO")
	private LocalTime horarioFim;

	/**
	 * Atributo mapeado para a coluna FLAG_HORARIO_ALMOCO da tabela do banco de dados
	 */
	@Column(name = "IN_HRAR_ITVL")
	private Boolean indicadorHorarioAlmoco;

	/**
	 * Atributo mapeado para a coluna HORARIO_INICIO_ALMOCO da tabela do banco de dados
	 */
	@Column(name = "HR_INCO_PRDO_ALMO")
	private LocalTime horarioInicioAlmoco;

	/**
	 * Atributo mapeado para a coluna HORARIO_FIM_ALMOCO da tabela do banco de dados
	 */
	@Column(name = "HR_FIM_PRDO_ALMO")
	private LocalTime horarioFimAlmoco;
	
	/**
	 * Atributo transiente para indicar se o horário de funcionamento é aproximado ao pesquisado
	 */
	@Transient
	private boolean isHorarioFuncionamentoAproximado;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(Integer diaInicio) {
		this.diaInicio = diaInicio;
	}

	public Integer getDiaFim() {
		return diaFim;
	}

	public void setDiaFim(Integer diaFim) {
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

	public Boolean getIndicadorHorarioAlmoco() {
		return indicadorHorarioAlmoco;
	}

	public void setIndicadorHorarioAlmoco(Boolean indicadorHorarioAlmoco) {
		this.indicadorHorarioAlmoco = indicadorHorarioAlmoco;
	}

	public LocalTime getHorarioInicioAlmoco() {
		return horarioInicioAlmoco;
	}

	public void setHorarioInicioAlmoco(LocalTime horarioInicioAlmoco) {
		this.horarioInicioAlmoco = horarioInicioAlmoco;
	}

	public LocalTime getHorarioFimAlmoco() {
		return horarioFimAlmoco;
	}

	public void setHorarioFimAlmoco(LocalTime horarioFimAlmoco) {
		this.horarioFimAlmoco = horarioFimAlmoco;
	}
	
	public boolean isHorarioFuncionamentoAproximado() {
		return isHorarioFuncionamentoAproximado;
	}

	public void setHorarioFuncionamentoAproximado(boolean isHorarioFuncionamentoAproximado) {
		this.isHorarioFuncionamentoAproximado = isHorarioFuncionamentoAproximado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((diaInicio == null) ? 0 : diaInicio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HorarioFuncionamento other = (HorarioFuncionamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (diaInicio == null) {
			if (other.diaInicio != null)
				return false;
		} else if (!diaInicio.equals(other.diaInicio))
			return false;
		return true;
	}

}