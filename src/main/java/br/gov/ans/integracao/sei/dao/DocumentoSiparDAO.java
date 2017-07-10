package br.gov.ans.integracao.sei.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.gov.ans.dao.DAO;
import br.gov.ans.integracao.sei.modelo.DocumentoSipar;

public class DocumentoSiparDAO {

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	private DAO<DocumentoSipar> dao;
	
	public DocumentoSipar getDocumento(String numeroDocumento, String anoDocumento, String digitoDocumento){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("numeroDocumento", numeroDocumento);
		params.put("anoDocumento", anoDocumento);
		params.put("digitoDocumento", digitoDocumento);
		
		List<DocumentoSipar> resultado = dao.executeNamedQuery("documentoPorNumeroAnoDigito", params);
		
		if(resultado.isEmpty()){
			return null;
		}
		
		return resultado.get(0);
	}
}
