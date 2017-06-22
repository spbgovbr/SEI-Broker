package br.gov.ans.integracao.sei.modelo.enums;

public enum TipoPessoa {
	FISICA("F"),JURIDICA("J");
	
	private String codigo;
	
	private TipoPessoa(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	public static TipoPessoa getByCodigo(String codigo){
		if("F".equals(codigo)){
			return TipoPessoa.FISICA;
		}
		
		return TipoPessoa.JURIDICA;
	}
}
