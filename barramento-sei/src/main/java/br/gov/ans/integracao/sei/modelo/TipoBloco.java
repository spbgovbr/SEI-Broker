package br.gov.ans.integracao.sei.modelo;

public enum TipoBloco{
	ASSINATURA("A"), REUNIAO("R"), INTERNO("I");
	
	private String codigo;
	
	private TipoBloco(String codigo){
		this.codigo = codigo;
	}
	
	public String getCodigo(){
		return this.codigo;
	}
}
