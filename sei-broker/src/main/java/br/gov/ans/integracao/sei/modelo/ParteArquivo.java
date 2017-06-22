package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParteArquivo {
	private String arquivo;
	private String conteudo;

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
