package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SobrestamentoProcesso {
	private String processo;
	private String motivo;
	private String processoVinculado;

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getProcessoVinculado() {
		return processoVinculado;
	}

	public void setProcessoVinculado(String processoVinculado) {
		this.processoVinculado = processoVinculado;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

}
