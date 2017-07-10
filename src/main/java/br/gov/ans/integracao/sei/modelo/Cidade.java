package br.gov.ans.integracao.sei.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement
@Entity
@Table(name="cidade")
@NamedQueries({@NamedQuery(name="Cidade.pesquisarPorID", query="select c from Cidade c where c.id = :id"),
	@NamedQuery(name="Cidade.pesquisarPorIbge", query="select c from Cidade c where c.codigoIbge = :codigoIbge")})
public class Cidade {
	@Id
	@Column(name="id_cidade")
	@JsonIgnore
	@XmlTransient
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_uf")
	private Estado estado;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="codigo_ibge")
	private String codigoIbge;

	@JsonIgnore
	@XmlTransient
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}	
}
