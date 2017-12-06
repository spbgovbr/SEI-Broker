package br.gov.ans.integracao.sei.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.gov.ans.dao.DAO;
import br.gov.ans.integracao.sipar.dao.ControleMigracao;
import br.gov.ans.integracao.sipar.dao.ControleMigracaoId;
import br.gov.ans.integracao.sipar.dao.DocumentoSipar;

@Stateless
public class SiparDAO {

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	private DAO<DocumentoSipar> daoDocumento;
	
	@Inject
	private DAO<ControleMigracao> daoMigracao;
	
	public DocumentoSipar getDocumento(String numeroDocumento, String anoDocumento, String digitoDocumento){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("numeroDocumento", numeroDocumento);
		params.put("anoDocumento", anoDocumento);
		params.put("digitoDocumento", digitoDocumento);
		
		List<DocumentoSipar> resultado = daoDocumento.executeNamedQuery("documentoPorNumeroAnoDigito", params);
		
		if(resultado.isEmpty()){
			return null;
		}
		
		return resultado.get(0);
	}
	
	public ControleMigracao buscaProcessoImportado(ControleMigracaoId id){
		return daoMigracao.findById(id);
	}
	
	public void merge(ControleMigracao controleMigracao){
		daoMigracao.merge(controleMigracao);
	}

}
