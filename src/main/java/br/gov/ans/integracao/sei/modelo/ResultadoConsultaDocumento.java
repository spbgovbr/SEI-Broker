package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import br.gov.ans.integracao.sei.client.RetornoConsultaDocumento;
import br.gov.ans.integracao.sipar.dao.DocumentoSipar;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class ResultadoConsultaDocumento {
	
	private DocumentoSipar documentoSIPAR;
	private RetornoConsultaDocumento documentoSEI;
	
	public DocumentoSipar getDocumentoSIPAR() {
		return documentoSIPAR;
	}
	
	public void setDocumentoSIPAR(DocumentoSipar documentoSIPAR) {
		this.documentoSIPAR = documentoSIPAR;
	}
	
	public RetornoConsultaDocumento getDocumentoSEI() {
		return documentoSEI;
	}
	
	public void setDocumentoSEI(RetornoConsultaDocumento documentoSEI) {
		this.documentoSEI = documentoSEI;
	}

}
