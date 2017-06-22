package br.gov.ans.integracao.sei.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@SqlResultSetMapping(name = "DocumentoResumidoMapping", entities = { @EntityResult(entityClass = DocumentoResumido.class, fields = {
		@FieldResult(name = "numero", column = "numero"),
		@FieldResult(name = "tipo", column = "tipo"),
		@FieldResult(name = "processo", column = "processo"),
		@FieldResult(name = "unidade", column = "unidade"),
		@FieldResult(name = "origem", column = "origem"),
		@FieldResult(name = "dataGeracao", column = "data_geracao") }) })
public class DocumentoResumido {
	private String numero;
	private String tipo;
	private String processo;
	private String unidade;
	private String origem;
	private Date dataGeracao;

	@Id
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Date getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

}
