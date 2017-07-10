package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TipoDocumento {
	private String identificador;
	private String nome;
	private String aplicabilidade;
	
	public TipoDocumento(){}
	
	public TipoDocumento(String identificador, String nome, String aplicabilidade){
		this.identificador = identificador;
		this.nome = nome;
		this.aplicabilidade = aplicabilidade;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAplicabilidade() {
		return aplicabilidade;
	}

	public void setAplicabilidade(String aplicabilidade) {
		this.aplicabilidade = aplicabilidade;
	}
}
