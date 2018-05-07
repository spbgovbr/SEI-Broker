package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
