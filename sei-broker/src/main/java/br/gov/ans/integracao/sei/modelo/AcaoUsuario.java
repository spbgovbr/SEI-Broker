package br.gov.ans.integracao.sei.modelo;

public enum AcaoUsuario {
	ALTERAR_INCLUIR("A"),
	EXCLUIR("E"),
	DESATIVAR("D"),
	REATIVAR("R");
	
	private String codigoAcao;

	private AcaoUsuario(String codigoAcao){
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
