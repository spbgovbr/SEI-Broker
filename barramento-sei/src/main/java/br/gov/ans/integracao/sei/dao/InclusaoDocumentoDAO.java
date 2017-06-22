package br.gov.ans.integracao.sei.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.integracao.sei.modelo.InclusaoDocumento;
import br.gov.ans.integracao.sei.utils.Constantes;

public class InclusaoDocumentoDAO {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<InclusaoDocumento> getDocumentosInclusos(String unidade, String sistema, Integer pagina, Integer qtdRegistros){
		HashMap<String,Object> parametros = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder("select d from InclusaoDocumento d where unidade = :unidade ");
		parametros.put("unidade", unidade);
		
		if(StringUtils.isNotBlank(sistema)){
			parametros.put("sistema", sistema);
			sql.append("and sistema = :sistema ");			
		}
		
		sql.append("order by data desc");
		
		Query query = em.createQuery(sql.toString());
		setParametros(query, parametros);
		
		setPaginacao(query, pagina, qtdRegistros);
		
		return query.getResultList();
	}
	
	public Long countDocumentosInclusos(String unidade, String sistema){
		HashMap<String,Object> parametros = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder("select count(d) from InclusaoDocumento d where unidade = :unidade ");
		parametros.put("unidade", unidade);
		
		if(StringUtils.isNotBlank(sistema)){
			parametros.put("sistema", sistema);
			sql.append("and sistema = :sistema ");			
		}
		
		Query query = em.createQuery(sql.toString());
		setParametros(query, parametros);
		
		return (Long) query.getSingleResult();
	}
	
	@SuppressWarnings("rawtypes")
	private void setParametros(Query query, HashMap<String, Object> parametros){
		if ((parametros != null) && (!parametros.isEmpty())) {
			for (Map.Entry entry : parametros.entrySet()) {
				query.setParameter((String) entry.getKey(),entry.getValue());
			}
		}
	}
	
	public void setPaginacao(Query query, Integer pagina, Integer qtdRegistros){
		if(qtdRegistros == null){
			qtdRegistros = Constantes.TAMANHO_PAGINA_PADRAO;
		}
		
		if(pagina != null){
			int firstResult = ((pagina - 1)* qtdRegistros);		
			query.setFirstResult(firstResult);
			query.setMaxResults(qtdRegistros);			
		}
	}
}
