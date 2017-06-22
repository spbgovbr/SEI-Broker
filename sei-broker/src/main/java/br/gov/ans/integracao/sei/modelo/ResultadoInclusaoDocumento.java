package br.gov.ans.integracao.sei.modelo;

import br.gov.ans.integracao.sei.client.RetornoInclusaoDocumento;

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
