package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InclusaoDocumentoBloco {
	private String bloco;
	private String documento;
	private String anotacao;

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getAnotacao() {
		return anotacao;
	}

	public void setAnotacao(String anotacao) {
		this.anotacao = anotacao;
	}

}
