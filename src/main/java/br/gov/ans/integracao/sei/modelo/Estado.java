package br.gov.ans.integracao.sei.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement
@Entity
@Table(name = "uf")
public class Estado {
	@JsonIgnore
	@XmlTransient
	@Id
	@Column(name = "id_uf")
	private Integer id;

	@Column(name = "sigla")
	private String sigla;

	@Column(name = "nome")
	private String nome;

	@JsonIgnore
	@XmlTransient
	@Column(name = "id_pais")
	private String idPais;

	@JsonIgnore
	@XmlTransient
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@JsonIgnore
	@XmlTransient
	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
}
