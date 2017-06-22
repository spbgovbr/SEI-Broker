package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

import br.gov.ans.integracao.sei.client.RetornoInclusaoDocumento;

@XmlRootElement
public class ResultadoInclusaoDocumento {
	private RetornoInclusaoDocumento retorno;
	private String mensagem;
	
	public RetornoInclusaoDocumento getRetorno() {
		return retorno;
	}
	public void setRetorno(RetornoInclusaoDocumento retorno) {
		this.retorno = retorno;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}	
}
