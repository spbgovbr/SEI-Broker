package br.gov.ans.integracao.sei.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="contato")
@NamedQueries({@NamedQuery(name="Contato.pesquisarPorID", query="select c from Contato c where c.id = :id"),
	@NamedQuery(name="Contato.pesquisarPorSigla", query="select c from Contato c where c.sigla = :sigla")})
public class Contato{

	@Id
	@Column(name="id_contato")
	private Integer id;

	@Column(name="nome")
	private String nome;

	@Column(name="sigla")
	private String sigla;

	@ManyToOne
	@JoinColumn(name = "id_tipo_contato")
	private TipoContato tipo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public TipoContato getTipo() {
		return tipo;
	}

	public void setTipo(TipoContato tipo) {
		this.tipo = tipo;
	}
		
}
