package br.gov.ans.integracao.sei.modelo.enums;

public enum Acao {
	ALTERAR_INCLUIR("A"),
	EXCLUIR("E"),
	DESATIVAR("D"),
	REATIVAR("R");
	
	private String codigoAcao;

	private Acao(String codigoAcao){
		this.codigoAcao = codigoAcao;
	}
	
	public String getCodigoAcao() {
		return codigoAcao;
	}

	public void setCodigoAcao(String codigoAcao) {
		this.codigoAcao = codigoAcao;
	}	
	
	@Override
	public String toString(){
		return codigoAcao;
	}
}
