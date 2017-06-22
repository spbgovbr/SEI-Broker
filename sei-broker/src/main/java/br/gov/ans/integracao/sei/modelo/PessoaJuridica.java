package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PessoaJuridica extends Pessoa {
	private String cnpj;
	private String website;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
