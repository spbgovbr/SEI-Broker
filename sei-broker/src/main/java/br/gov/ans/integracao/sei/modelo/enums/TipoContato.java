package br.gov.ans.integracao.sei.modelo.enums;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TipoContato {
	usuarios_abc("1"),
	sistemas("2"),
	usuarios_externos("3"),
	temporarios("4"),
	usuarios_externos_ans("6"),
	operadoras("7"),
	unidades_abc("16"),
	usuarios_ans("17"),
	unidades_ans("18"),
	orgaos("19");
	
	String codigo;
	
	private TipoContato(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigo(){
		return codigo;
	}
}
