package br.gov.ans.integracao.sei.dao;

import static br.gov.ans.integracao.sei.utils.Util.setPaginacaoQuery;
import static br.gov.ans.integracao.sei.utils.Util.setQueryParameters;
import static br.gov.ans.integracao.sei.utils.Util.andOrWhere;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.modelo.LogIntegracaoSistemica;

public class LogIntegracaoSistemicaDAO {
	
	@PersistenceContext(unitName = "sei_broker_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<LogIntegracaoSistemica> getLogs(String operacao, String origem, Integer pagina, Integer qtdRegistros){
		HashMap<String,Object> parametros = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder("SELECT l FROM LogIntegracaoSistemica l ");
		
		if(StringUtils.isNotBlank(operacao)){
			sql.append(andOrWhere(sql));
			sql.append("l.operacao = :operacao ");
			parametros.put("operacao", operacao);
		}
		
		if(StringUtils.isNotBlank(origem)){
			sql.append(andOrWhere(sql));
			sql.append("l.origem = :origem ");
			parametros.put("origem", origem);
		}
				
		sql.append("order by l.data desc ");
		
		Query query = em.createQuery(sql.toString());
		
		setPaginacaoQuery(query, pagina, qtdRegistros);
		setQueryParameters(query, parametros);
				
		return query.getResultList();
	}
}
