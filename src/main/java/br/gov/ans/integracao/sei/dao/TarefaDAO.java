package br.gov.ans.integracao.sei.dao;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.integracao.sei.modelo.Tarefa;

import static br.gov.ans.integracao.sei.utils.Util.setQueryParameters;

public class TarefaDAO {
	    
	@PersistenceContext(unitName="sei_pu")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Tarefa> getTarefas(String filtroNome){

		HashMap<String,Object> parametros = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder("select t from Tarefa t ");
		
		if(StringUtils.isNoneBlank(filtroNome)){
			sql.append("where t.nome like :filtroNome ");
			parametros.put("filtroNome", filtroNome);
		}
		
		sql.append("order by t.identificador asc");
		
		Query query = em.createQuery(sql.toString());
		setQueryParameters(query, parametros);
		
		return query.getResultList();
	}
}
