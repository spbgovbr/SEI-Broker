package br.gov.ans.integracao.sipar.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(name="DocumentoSiparMapping", entities={
		@EntityResult(entityClass = DocumentoSipar.class,
				fields = {
			@FieldResult(name = "digito", column = "NU_DIGITO_DOCUMENTO"),
			@FieldResult(name = "operadora", column = "CO_OPERADORA"),			
			@FieldResult(name = "emissao", column = "DT_EMISSAO_DOCUMENTO"),
			@FieldResult(name = "registro", column = "DT_REGISTRO"),
			@FieldResult(name = "tipo", column = "CO_TIPO_DOCUMENTO"),
			@FieldResult(name = "resumo", column = "DS_RESUMO_DOCUMENTO"),
			@FieldResult(name = "orgaoPosse", column = "CO_ORGAO_POSSE"),
			@FieldResult(name = "orgaoOrigem", column = "CO_ORGAO_ORIGEM"),
			@FieldResult(name = "orgaoRegistro", column = "CO_ORGAO_REGISTRO"),			
			@FieldResult(name = "assunto", column = "CO_ASSUNTO")
		})
})
@NamedNativeQuery(name = "documentoPorNumeroAnoDigito", resultSetMapping = "DocumentoSiparMapping",
query = "SELECT TB.NU_DIGITO_DOCUMENTO, TB.CO_OPERADORA, TB.DT_EMISSAO_DOCUMENTO, TB.DT_REGISTRO, TB.CO_TIPO_DOCUMENTO, "
		+ "TB.DS_RESUMO_DOCUMENTO, TB.CO_ORGAO_POSSE, TB.CO_ORGAO_ORIGEM, TB.CO_ORGAO_REGISTRO, RL.CO_ASSUNTO "
		+ "FROM DBPSIPAR.TB_DOCUMENTO TB "
		+ "LEFT JOIN DBPSIPAR.RL_ASSUNTO_DOCUMENTO RL ON RL.CO_DOCUMENTO = TB.CO_SEQ_DOCUMENTO "
		+ "AND RL.NU_ANO_DOCUMENTO = TB.NU_ANO_DOCUMENTO "
		+ "WHERE TB.CO_SEQ_DOCUMENTO = :numeroDocumento "
		+ "AND TB.NU_ANO_DOCUMENTO = :anoDocumento "
		+ "AND TB.NU_DIGITO_DOCUMENTO = :digitoDocumento")
public class DocumentoSipar implements Serializable{
	@Id
	private String digito;
	private String operadora;
	private Date emissao;
	private Date registro;
	private String tipo;
	private String resumo;
	private Long orgaoPosse;
	private Long orgaoOrigem;
	private Long orgaoRegistro;
	private Long assunto;
	
	public String getDigito() {
		return digito;
	}
	
	public void setDigito(String digito) {
		this.digito = digito;
	}
	
	public Long getAssunto() {
		return assunto;
	}
	
	public void setAssunto(Long assunto) {
		this.assunto = assunto;
	}
	
	public String getOperadora() {
		return operadora;
	}
	
	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public Long getOrgaoPosse() {
		return orgaoPosse;
	}

	public void setOrgaoPosse(Long orgaoPosse) {
		this.orgaoPosse = orgaoPosse;
	}

	public Long getOrgaoOrigem() {
		return orgaoOrigem;
	}

	public void setOrgaoOrigem(Long orgaoOrigem) {
		this.orgaoOrigem = orgaoOrigem;
	}

	public Long getOrgaoRegistro() {
		return orgaoRegistro;
	}

	public void setOrgaoRegistro(Long orgaoRegistro) {
		this.orgaoRegistro = orgaoRegistro;
	}

	public Date getRegistro() {
		return registro;
	}

	public void setRegistro(Date registro) {
		this.registro = registro;
	}
		
}
