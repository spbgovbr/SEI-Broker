package br.gov.ans.integracao.sei.dao;

import static br.gov.ans.integracao.sei.utils.Util.setPaginacaoQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.integracao.sei.modelo.InclusaoDocumento;

public class InclusaoDocumentoDAO {

	@PersistenceContext(unitName = "sei_broker_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<InclusaoDocumento> getDocumentosInclusos(String unidade, String processo, String hash, String numeroInformado, Integer pagina, Integer qtdRegistros){
		HashMap<String,Object> parametros = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder("select d from InclusaoDocumento d where unidade = :unidade ");
		parametros.put("unidade", unidade);
		
		if(StringUtils.isNotBlank(processo)){
			parametros.put("processo", processo);
			sql.append("and processo = :processo ");			
		}

		if(StringUtils.isNotBlank(numeroInformado)){
			parametros.put("numeroInformado", numeroInformado);
			sql.append("and numeroInformado = :numeroInformado ");			
		}

		if(StringUtils.isNotBlank(hash)){
			parametros.put("hash", hash);
			sql.append("and hash = :hash ");			
		}
		
		sql.append("order by data desc");
		
		Query query = em.createQuery(sql.toString());
		setParametros(query, parametros);
		
		setPaginacaoQuery(query, pagina, qtdRegistros);
		
		return query.getResultList();
	}
	
	public Long countDocumentosInclusos(String unidade, String processo, String hash, String numeroInformado){
		HashMap<String,Object> parametros = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder("select count(d) from InclusaoDocumento d where unidade = :unidade ");
		parametros.put("unidade", unidade);
		
		if(StringUtils.isNotBlank(processo)){
			parametros.put("processo", processo);
			sql.append("and processo = :processo ");			
		}

		if(StringUtils.isNotBlank(numeroInformado)){
			parametros.put("numeroInformado", numeroInformado);
			sql.append("and numeroInformado = :numeroInformado ");			
		}

		if(StringUtils.isNotBlank(hash)){
			parametros.put("hash", hash);
			sql.append("and hash = :hash ");			
		}		
				
		Query query = em.createQuery(sql.toString());
		setParametros(query, parametros);
		
		return Long.valueOf(query.getSingleResult().toString());
	}
	
	@SuppressWarnings("rawtypes")
	private void setParametros(Query query, HashMap<String, Object> parametros){
		if ((parametros != null) && (!parametros.isEmpty())) {
			for (Map.Entry entry : parametros.entrySet()) {
				query.setParameter((String) entry.getKey(),entry.getValue());
			}
		}
	}
}
