package br.gov.ans.integracao.sei.modelo;

public class CancelamentoDocumento {
	private String documento;
	private String motivo;

	public CancelamentoDocumento(){}
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
