package br.gov.ans.integracao.sei.modelo.enums;

public enum Sexo {
	FEMININO("F"),MASCULINO("M");
	
	private String codigo;
	
	private Sexo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	public static Sexo getByCodigo(String codigo){
		if("F".equals(codigo)){
			return Sexo.FEMININO;			
		}
		
		return Sexo.MASCULINO;
	}
}
