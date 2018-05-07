package br.gov.ans.integracao.sei.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
//@SqlResultSetMapping(name = "TipoMapping", entities = { @EntityResult(entityClass = Tipo.class, fields = {
//		@FieldResult(name = "codigo", column = "tipoCodigo"), @FieldResult(name = "nome", column = "tipoNome") }) })
@XmlRootElement
public class Tipo {
	@Id
	private String codigo;
	private String nome;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
