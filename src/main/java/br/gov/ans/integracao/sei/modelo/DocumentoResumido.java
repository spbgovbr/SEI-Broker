package br.gov.ans.integracao.sei.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@Entity
@SqlResultSetMapping(name = "DocumentoResumidoMapping", entities = { @EntityResult(entityClass = DocumentoResumido.class, fields = {
		@FieldResult(name = "numero", column = "numero"),
		@FieldResult(name = "numeroInformado", column = "numeroInformado"),
		@FieldResult(name = "unidade", column = "unidade"),
		@FieldResult(name = "origem", column = "origem"),
		@FieldResult(name = "processo", column = "processo"),
		@FieldResult(name = "tipoConferencia", column = "tipoConferencia"),
		@FieldResult(name = "assinado", column = "assinado"),
		@FieldResult(name = "dataGeracao", column = "dataGeracao"),
		@FieldResult(name = "tipo", column = "tipoCodigo")
		}),
		@EntityResult(
                entityClass = Tipo.class,
                fields = {
                    @FieldResult(name = "codigo", column = "tipoCodigo"),
                    @FieldResult(name = "nome", column = "tipoNome")})
		})
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DocumentoResumido {
	@Id
	private String numero;
	private String numeroInformado;
	private String unidade;
	private String origem;
	private Date dataGeracao;
	private String processo;
	
	@OneToOne
	private Tipo tipo;
	private String tipoConferencia;
	private boolean assinado;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
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

	public String getNumeroInformado() {
		return numeroInformado;
	}

	public void setNumeroInformado(String numeroInformado) {
		this.numeroInformado = numeroInformado;
	}

	public String getTipoConferencia() {
		return tipoConferencia;
	}

	public void setTipoConferencia(String tipoConferencia) {
		this.tipoConferencia = tipoConferencia;
	}

	public boolean isAssinado() {
		return assinado;
	}

	public void setAssinado(boolean assinado) {
		this.assinado = assinado;
	}

}
