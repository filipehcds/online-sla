package br.com.cielo.sigo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import br.com.cielo.sigo.dto.FeriadoDTO;

/**
 * Entidade de RegiaoFeriado
 * 
 * @author alexandre.oliveira
 * @since 27/04/2018
 *
 */
@Entity
@Table(name = "TBSIGDR_REGO_FERI", schema = "SIGD")
@NamedNativeQuery(
		name = "RegiaoFeriado.obterListaRegiaoFeriado", 
		query = "SELECT\r\n" + 
				"    cf.dc_ctgr_feri AS tipo,\r\n" + 
				"    f.dc_feri AS descricao,\r\n" + 
				"    ag.dc_area_gegf AS cidade,\r\n" + 
				"    rf.sg_uf AS uf,\r\n" + 
				"    f.dt_feri AS data\r\n" + 
				"FROM\r\n" + 
				"    sigd.tbsigdr_rego_feri rf\r\n" + 
				"    INNER JOIN sigd.tbsigdr_ctgr_feri cf ON rf.cd_ctgr_feri = cf.cd_ctgr_feri\r\n" + 
				"    INNER JOIN sigd.tbsigdr_feri f ON rf.cd_feri = f.cd_feri\r\n" + 
				"    LEFT OUTER JOIN sigd.tbsigdr_area_gegf ag ON rf.cd_area_gegf = ag.cd_area_gegf\r\n" + 
				"WHERE\r\n" + 
				"   (\r\n" + 
				"        (\r\n" + 
				"            ag.dc_area_gegf = :cidade\r\n" + 
				"            AND rf.sg_uf = :uf\r\n" + 
				"        )\r\n" + 
				"        OR (\r\n" + 
				"            rf.sg_uf = :uf\r\n" + 
				"            AND rf.cd_area_gegf IS NULL\r\n" + 
				"        )\r\n" + 
				"        OR (\r\n" + 
				"            rf.sg_uf IS NULL\r\n" + 
				"            AND rf.cd_area_gegf IS NULL\r\n" + 
				"        )\r\n" + 
				"    )\r\n" +
				"AND YEAR(f.dt_feri) IN (:anoCorrente, :proximoAno)", 
		resultSetMapping = "RegiaoFeriado.obterListaRegiaoFeriadoDTO"
)
@SqlResultSetMapping(
		name = "RegiaoFeriado.obterListaRegiaoFeriadoDTO", 
		classes = {
			@ConstructorResult(
				targetClass = FeriadoDTO.class, 
				columns = { 
					@ColumnResult(name = "tipo", type = String.class),
					@ColumnResult(name = "descricao", type = String.class),
					@ColumnResult(name = "cidade", type = String.class),
					@ColumnResult(name = "uf", type = String.class), 
					@ColumnResult(name = "data", type = LocalDateTime.class)
			}) 
		})
public class RegiaoFeriado implements Serializable {

	private static final long serialVersionUID = -3972059901600640137L;

	/**
	 * Atributo mapeado para a coluna CD_REGO_FERI da tabela do banco de dados 
	 */
	@Id
	@Column(name = "CD_REGO_FERI")
	private Integer codigoRegiaoFeriado;

	/**
	 * Atributo mapeado para a coluna CD_FERI da tabela do banco de dados 
	 */
	@Column(name = "CD_FERI")
	private Integer codigoFeriado;

	/**
	 * Atributo mapeado para a coluna CD_CTGR_FERI da tabela do banco de dados 
	 */
	@Column(name = "CD_CTGR_FERI")
	private Integer codigoCategoriaFeriado;

	/**
	 * Atributo mapeado para a coluna SG_UF da tabela do banco de dados 
	 */
	@Column(name = "SG_UF")
	private String uf;

	/**
	 * Atributo mapeado para a coluna CD_AREA_GEGF da tabela do banco de dados 
	 */
	@Column(name = "CD_AREA_GEGF")
	private Integer codigoAreaGeografica;

	/*
	 * Getter & Setters
	 */
	public Integer getCodigoRegiaoFeriado() {
		return codigoRegiaoFeriado;
	}

	public void setCodigoRegiaoFeriado(Integer codigoRegiaoFeriado) {
		this.codigoRegiaoFeriado = codigoRegiaoFeriado;
	}

	public Integer getCodigoFeriado() {
		return codigoFeriado;
	}

	public void setCodigoFeriado(Integer codigoFeriado) {
		this.codigoFeriado = codigoFeriado;
	}

	public Integer getCodigoCategoriaFeriado() {
		return codigoCategoriaFeriado;
	}

	public void setCodigoCategoriaFeriado(Integer codigoCategoriaFeriado) {
		this.codigoCategoriaFeriado = codigoCategoriaFeriado;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Integer getCodigoAreaGeografica() {
		return codigoAreaGeografica;
	}

	public void setCodigoAreaGeografica(Integer codigoAreaGeografica) {
		this.codigoAreaGeografica = codigoAreaGeografica;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoRegiaoFeriado == null) ? 0 : codigoRegiaoFeriado.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegiaoFeriado other = (RegiaoFeriado) obj;
		if (codigoRegiaoFeriado == null) {
			if (other.codigoRegiaoFeriado != null)
				return false;
		} else if (!codigoRegiaoFeriado.equals(other.codigoRegiaoFeriado))
			return false;
		return true;
	}

}
