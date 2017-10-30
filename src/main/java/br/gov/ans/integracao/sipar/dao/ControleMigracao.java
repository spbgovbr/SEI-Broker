package br.gov.ans.integracao.sipar.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(schema="DBPSIPAR", name="TB_CONTROLE_MIGRACAO_SIPAR_SEI")
public class ControleMigracao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ControleMigracaoId id;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ULTIMA_ALTERACAO_CONTROLE")
	private Date dataUltimaAlteracao;
	
    @Column(name="LG_DOCUMENTO_MIGRACAO_SEI")
	private Integer migrado;

	public ControleMigracaoId getId() {
		return id;
	}

	public void setId(ControleMigracaoId id) {
		this.id = id;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Integer getMigrado() {
		return migrado;
	}

	public void setMigrado(Integer migrado) {
		this.migrado = migrado;
	}

	public boolean isProcessoMigrado() {
		return this.migrado == 1 ? true : false;
	}
}
