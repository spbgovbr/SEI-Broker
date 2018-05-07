package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import br.gov.ans.integracao.sei.client.RetornoInclusaoDocumento;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
