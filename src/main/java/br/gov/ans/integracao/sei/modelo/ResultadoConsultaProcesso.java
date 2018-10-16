package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import br.gov.ans.integracao.sei.client.RetornoConsultaProcedimento;
import br.gov.ans.integracao.sipar.modelo.DocumentoSipar;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class ResultadoConsultaProcesso {
	
	private DocumentoSipar sipar;
	private RetornoConsultaProcedimento sei;
	
	public DocumentoSipar getSipar() {
		return sipar;
	}
	
	public void setSipar(DocumentoSipar sipar) {
		this.sipar = sipar;
	}
	
	public RetornoConsultaProcedimento getSei() {
		return sei;
	}
	
	public void setSei(RetornoConsultaProcedimento sei) {
		this.sei = sei;
	}	
	
}
