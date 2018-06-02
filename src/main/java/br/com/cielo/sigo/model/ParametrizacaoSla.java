package br.com.cielo.sigo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import br.com.cielo.sigo.dto.ParametrizacaoSlaDTO;

/**
 * Entidade ParametrizacaoSla mapeado para tabela TBSIGDR_PRMT_SLA_MDLO_SLCO
 * 
 * @author alexandre.oliveira
 * @since 27/04/2018
 */
@Entity
@Table(name = "TBSIGDR_PRMT_SLA_MDLO_SLCO", schema = "SIGD")
@NamedNativeQueries({
	@NamedNativeQuery(
			name = "ParametrizacaoSla.obterParametrizacaoSla", 
			query = "SELECT\r\n" + 
					"    ec.nu_ec AS codigoCliente,\r\n" + 
					"    ec.nm_fnts AS nomeFantasia,\r\n" + 
					"    ec.nm_rzsc AS razaoSocial,\r\n" + 
					"    sla.cd_pcte AS codigoPacote,\r\n" + 
					"    sla.cd_sgmt AS codigoSegmento,\r\n" + 
					"    ec.cd_clse_fat_pdro AS siglaClasseFaturamento,\r\n" + 
					"    sla.cd_mdlo_slco AS siglaModeloSolucao,\r\n" + 
					"    hdwr.dc_tipo_hdwr_eqpm AS grupoModeloSolucao,\r\n" + 
					"    area.dc_area_gegf AS cidade,\r\n" + 
					"    area.sg_uf AS estado,\r\n" + 
					"    atnd.dc_tipo_nivl_atnd AS nivelAtendimento,\r\n" + 
					"    sla.qt_hora_sla AS quantidadeHorasSla\r\n" + 
					"FROM\r\n" + 
					"    sigd.tbsigdr_prmt_sla_mdlo_slco sla\r\n" + 
					"    INNER JOIN sigd.tbsigdr_area_gegf area ON sla.cd_area_gegf = area.cd_area_gegf\r\n" + 
					"    INNER JOIN sigs.tbsigsr_mdlo_slco mdlo ON sla.cd_mdlo_slco = mdlo.cd_mdlo_slco\r\n" + 
					"    INNER JOIN sigs.tbsigsr_tipo_hdwr_eqpm hdwr ON mdlo.cd_tipo_hdwr_eqpm = hdwr.cd_tipo_hdwr_eqpm\r\n" + 
					"    INNER JOIN sigc.tbsigcr_ec ec ON ec.CD_CLSE_FAT_PDRO = sla.CD_CLSE_FAT_PDRO\r\n" + 
					"                                 AND ( ec.cd_sgmt = sla.cd_sgmt OR sla.cd_sgmt IS NULL )\r\n" + 
					"                                 AND ec.cd_pcte = sla.cd_pcte\r\n" + 
					"    INNER JOIN sigc.tbsigcr_sgmt sgmt ON ec.cd_sgmt = sgmt.cd_sgmt\r\n" + 
					"    INNER JOIN sigc.tbsigcr_pcte_srvc pcte ON ec.cd_pcte = pcte.cd_pcte\r\n" + 
					"    INNER JOIN sigd.tbsigdr_tipo_nivl_atnd atnd ON sla.cd_tipo_nivl_atnd = atnd.cd_tipo_nivl_atnd\r\n" + 
					"WHERE\r\n" + 
					"    ec.nu_ec = :codigoCliente\r\n" + 
					"    AND atnd.cd_tipo_nivl_atnd = :codigoNivelAtendimento\r\n" + 
					"    AND mdlo.cd_mdlo_slco = :siglaModeloSolucao\r\n" + 
					"    AND area.dc_area_gegf = :cidade\r\n" + 
					"    AND area.sg_uf = :estado", 
			resultSetMapping = "ParametrizacaoSla.calcularSlaManutencaoDTO"
	),
	@NamedNativeQuery(
			name = "ParametrizacaoSla.obterQuantidadeHorasSla", 
			query = "SELECT\r\n" +
					"    qt_hora_sla AS quantidadeHorasSla\r\n" +
					"FROM\r\n" +
					"    sigd.tbsigdr_mtrz_sla_mdlo_slco\r\n" +
					"WHERE\r\n" +
					"    cd_grpo_area_gegf = :localizacao\r\n" +
					"    AND cd_tipo_hdwr_eqpm = :solucaoCaptura\r\n" +
					"    AND cd_pcte = :pacote\r\n" +
					"    AND cd_tipo_nivl_atnd = :nivelAtendimento\r\n" +
					"    AND cd_sgmt = :segmento",
			resultSetMapping = "ParametrizacaoSla.obterSlaManutencaoDTO"
	)
})
@SqlResultSetMappings({
	@SqlResultSetMapping(
			name = "ParametrizacaoSla.calcularSlaManutencaoDTO", 
			classes = {
				@ConstructorResult(
					targetClass = ParametrizacaoSlaDTO.class, 
					columns = { 
						@ColumnResult(name = "codigoCliente", type = Long.class),
						@ColumnResult(name = "nomeFantasia", type = String.class),
						@ColumnResult(name = "razaoSocial", type = String.class),
						@ColumnResult(name = "codigoPacote", type = Integer.class), 
						@ColumnResult(name = "codigoSegmento", type = Integer.class),
						@ColumnResult(name = "siglaClasseFaturamento", type = String.class), 
						@ColumnResult(name = "siglaModeloSolucao", type = String.class), 
						@ColumnResult(name = "grupoModeloSolucao", type = String.class), 
						@ColumnResult(name = "cidade", type = String.class), 
						@ColumnResult(name = "estado", type = String.class), 
						@ColumnResult(name = "nivelAtendimento", type = String.class), 
						@ColumnResult(name = "quantidadeHorasSla", type = Integer.class), 
				}) 
		}),
	@SqlResultSetMapping(
			name = "ParametrizacaoSla.obterSlaManutencaoDTO", 
			classes = {
				@ConstructorResult(
					targetClass = ParametrizacaoSlaDTO.class, 
					columns = { 
						@ColumnResult(name = "quantidadeHorasSla", type = Integer.class), 
				}) 
		})
})
public class ParametrizacaoSla implements Serializable {

	private static final long serialVersionUID = 3451793132947589616L;

	/**
	 * Atributo mapeado para a coluna CD_PRMT_SLA_MDLO_SLCO da tabela do banco de dados
	 */
	@Id
	@Column(name = "CD_PRMT_SLA_MDLO_SLCO")
	private Long codigoParametroSla;

	/**
	 * Atributo mapeado para a coluna CD_AREA_GEGF da tabela do banco de dados
	 */
	@Column(name = "CD_AREA_GEGF")
	private Integer codigoAreaGeografica;

	/**
	 * Atributo mapeado para a coluna CD_MCC da tabela do banco de dados
	 */
	@Column(name = "CD_MCC")
	private Integer codigoRamoAtividade;

	/**
	 * Atributo mapeado para a coluna CD_SGMT da tabela do banco de dados
	 */
	@Column(name = "CD_SGMT")
	private Integer codigoSegmento;

	/**
	 * Atributo mapeado para a coluna CD_PCTE da tabela do banco de dados
	 */
	@Column(name = "CD_PCTE")
	private Integer codigoPacote;

	/**
	 * Atributo mapeado para a coluna CD_MDLO_SLCO da tabela do banco de dados
	 */
	@Column(name = "CD_MDLO_SLCO")
	private String siglaModeloSolucao;

	/**
	 * Atributo mapeado para a coluna CD_TIPO_NIVL_ATND da tabela do banco de dados
	 */
	@Column(name = "CD_TIPO_NIVL_ATND")
	private Integer codigoNivelAtendimento;

	/**
	 * Atributo mapeado para a coluna QT_HORA_SLA da tabela do banco de dados
	 */
	@Column(name = "QT_HORA_SLA")
	private Integer quantidadeHorasSla;

	/*
	 * Getters & Setters
	 */
	public Long getCodigoParametroSla() {
		return codigoParametroSla;
	}

	public void setCodigoParametroSla(Long codigoParametroSla) {
		this.codigoParametroSla = codigoParametroSla;
	}

	public Integer getCodigoAreaGeografica() {
		return codigoAreaGeografica;
	}

	public void setCodigoAreaGeografica(Integer codigoAreaGeografica) {
		this.codigoAreaGeografica = codigoAreaGeografica;
	}

	public Integer getCodigoRamoAtividade() {
		return codigoRamoAtividade;
	}

	public void setCodigoRamoAtividade(Integer codigoRamoAtividade) {
		this.codigoRamoAtividade = codigoRamoAtividade;
	}

	public Integer getCodigoSegmento() {
		return codigoSegmento;
	}

	public void setCodigoSegmento(Integer codigoSegmento) {
		this.codigoSegmento = codigoSegmento;
	}

	public Integer getCodigoPacote() {
		return codigoPacote;
	}

	public void setCodigoPacote(Integer codigoPacote) {
		this.codigoPacote = codigoPacote;
	}

	public String getSiglaModeloSolucao() {
		return siglaModeloSolucao;
	}

	public void setSiglaModeloSolucao(String siglaModeloSolucao) {
		this.siglaModeloSolucao = siglaModeloSolucao;
	}

	public Integer getCodigoNivelAtendimento() {
		return codigoNivelAtendimento;
	}

	public void setCodigoNivelAtendimento(Integer codigoNivelAtendimento) {
		this.codigoNivelAtendimento = codigoNivelAtendimento;
	}

	public Integer getQuantidadeHorasSla() {
		return quantidadeHorasSla;
	}

	public void setQuantidadeHorasSla(Integer quantidadeHorasSla) {
		this.quantidadeHorasSla = quantidadeHorasSla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoParametroSla == null) ? 0 : codigoParametroSla.hashCode());
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
		ParametrizacaoSla other = (ParametrizacaoSla) obj;
		if (codigoParametroSla == null) {
			if (other.codigoParametroSla != null)
				return false;
		} else if (!codigoParametroSla.equals(other.codigoParametroSla))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParametrizacaoSla [codigoParametroSla=" + codigoParametroSla + ", codigoAreaGeografica="
				+ codigoAreaGeografica + ", codigoRamoAtividade=" + codigoRamoAtividade + ", codigoSegmento="
				+ codigoSegmento + ", codigoPacote=" + codigoPacote + ", siglaModeloSolucao=" + siglaModeloSolucao
				+ ", codigoNivelAtendimento=" + codigoNivelAtendimento + ", quantidadeHorasSla=" + quantidadeHorasSla
				+ "]";
	}
	
}
