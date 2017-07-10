package br.gov.ans.integracao.sei.modelo;

import javax.xml.bind.annotation.XmlRootElement;

import br.gov.ans.integracao.sei.client.RetornoConsultaDocumento;

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
