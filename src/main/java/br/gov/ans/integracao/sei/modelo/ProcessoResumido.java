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
@SqlResultSetMapping(name = "ProcessoResumidoMapping", entities = { @EntityResult(entityClass = ProcessoResumido.class, fields = {
		@FieldResult(name = "numero", column = "protocolo"),
		@FieldResult(name = "numeroFormatado", column = "protocolo_formatado"),
		@FieldResult(name = "descricao", column = "descricao"),
		@FieldResult(name = "unidade", column = "unidade"),
		@FieldResult(name = "dataGeracao", column = "data_geracao") }) })
public class ProcessoResumido {

	@Id
	private String numero;
	private String numeroFormatado;
	private String descricao;
	private String unidade;
	private Date dataGeracao;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroFormatado() {
		return numeroFormatado;
	}

	public void setNumeroFormatado(String numeroFormatado) {
		this.numeroFormatado = numeroFormatado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Date getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

}
