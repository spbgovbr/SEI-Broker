package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AtribuicaoProcesso {
		
	private String processo;
	private boolean reabrir;
	
	public AtribuicaoProcesso(){}
	
	public String getProcesso() {
		return processo;
	}
	public void setProcesso(String processo) {
		this.processo = processo;
	}
	public boolean isReabrir() {
		return reabrir;
	}
	public void setReabrir(boolean reabrir) {
		this.reabrir = reabrir;
	}
	
}
