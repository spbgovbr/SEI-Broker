package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArquivoCriado {
	private String identificador;

	public ArquivoCriado(){
		
	}
	
	public ArquivoCriado(String identificador){
		this.identificador = identificador;
	}
	
	public String getIdentificador() {
		return this.identificador;
	}
}
