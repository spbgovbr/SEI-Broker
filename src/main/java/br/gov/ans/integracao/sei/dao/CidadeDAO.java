package br.gov.ans.integracao.sei.dao;

import static br.gov.ans.integracao.sei.utils.Util.setQueryParameters;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.integracao.sei.modelo.Cidade;

public class CidadeDAO {
	@PersistenceContext(unitName = "sei_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	public Cidade getCidadePeloId(String id){
		Query query = em.createNamedQuery("Cidade.pesquisarPorID",Cidade.class);
		
		query.setParameter("id", Integer.parseInt(id));
		
		return (Cidade) query.getSingleResult();				
	}
	
	public Cidade getCidadePeloIbge(String codigoIbge){
		Query query = em.createNamedQuery("Cidade.pesquisarPorIbge",Cidade.class);
		
		query.setParameter("codigoIbge", codigoIbge);
		
		return (Cidade) query.getSingleResult();		
	}
	
	@SuppressWarnings("unchecked")
	public List<Cidade> getCidades(String uf, String filtro){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuilder sql = new StringBuilder("select c from Cidade c where c.estado.sigla = :uf ");
		parametros.put("uf", uf);
				
		if(StringUtils.isNotBlank(filtro)){
			sql.append("and upper(c.nome) like upper(:filtro) ");
			parametros.put("filtro", "%"+filtro+"%");
		}

		sql.append("order by nome asc");
		
		Query query = em.createQuery(sql.toString());
		
		setQueryParameters(query,parametros);
		
		return query.getResultList();
	}
}
