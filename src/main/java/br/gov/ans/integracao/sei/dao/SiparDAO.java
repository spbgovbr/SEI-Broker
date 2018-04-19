package br.gov.ans.integracao.sei.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import br.gov.ans.dao.DAO;
import br.gov.ans.integracao.sipar.dao.ControleMigracao;
import br.gov.ans.integracao.sipar.dao.ControleMigracaoId;
import br.gov.ans.integracao.sipar.dao.DocumentoSipar;

@Stateful
public class SiparDAO {

	@PersistenceContext(unitName = "sei_broker_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
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
	
	public boolean isProcessoEmTramitacao(String numero, String ano){
		
		String sql = "SELECT count(*) FROM DBPSIPAR.TB_TRAMITACAO TR "
				+ "WHERE "   
		   + "TR.DT_RECEBIMENTO IS NULL "
		   + "AND TR.CO_DOCUMENTO = :numero " 
		   + "AND TR.NU_ANO_DOCUMENTO = :ano "
		   + "AND TR.DT_ENVIO = ("
		   + "	SELECT MAX(TR2.DT_ENVIO) FROM DBPSIPAR.TB_TRAMITACAO TR2 WHERE TR2.CO_DOCUMENTO = :numero AND TR2.NU_ANO_DOCUMENTO = :ano"
		   + ")"; 
		
		Query query = em.createNativeQuery(sql);
		query.setParameter("numero", numero);
		query.setParameter("ano", ano);
		
		Long count = ((BigDecimal) query.getSingleResult()).longValue();
		
		if(count > 0L){
			return true;
		}		
		return false;
	}
	
	public void merge(ControleMigracao controleMigracao){
		daoMigracao.merge(controleMigracao);
	}

}
