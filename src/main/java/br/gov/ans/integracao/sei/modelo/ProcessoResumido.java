package br.gov.ans.integracao.sei.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@Entity
@SqlResultSetMapping(name = "ProcessoResumidoMapping", entities = { @EntityResult(entityClass = ProcessoResumido.class, fields = {
		@FieldResult(name = "numero", column = "numero"),
		@FieldResult(name = "numeroFormatado", column = "numeroFormatado"),
		@FieldResult(name = "descricao", column = "descricao"),
		@FieldResult(name = "unidade", column = "unidade"),
		@FieldResult(name = "dataGeracao", column = "dataGeracao"),
		@FieldResult(name = "tipo", column = "tipoCodigo") 
		}),
		@EntityResult(
                entityClass = Tipo.class,
                fields = {
                    @FieldResult(name = "codigo", column = "tipoCodigo"),
                    @FieldResult(name = "nome", column = "tipoNome")})
		})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessoResumido {

	@Id
	private String numero;
	private String numeroFormatado;
	private String descricao;
	private String unidade;
	private Date dataGeracao;
	@OneToOne
	private Tipo tipo;

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

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
}
