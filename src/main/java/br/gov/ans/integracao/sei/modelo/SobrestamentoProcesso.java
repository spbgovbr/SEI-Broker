package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SobrestamentoProcesso {
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

}
