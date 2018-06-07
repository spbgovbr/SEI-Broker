package br.gov.ans.integracao.sei.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import br.gov.ans.integracao.sei.client.Unidade;

public class UnidadeDAO {
	@PersistenceContext(unitName = "sei_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	public List<Unidade> listarUnidadesProcesso(String idProcedimento){
		StringBuilder sql = new StringBuilder("SELECT u.id_unidade idUnidade, u.sigla, u.descricao, u.sin_protocolo sinProtocolo, ");
		sql.append("u.sin_arquivamento sinArquivamento, u.sin_ouvidoria sinOuvidoria ");
		sql.append("FROM protocolo p ");
		sql.append("JOIN atividade a ON p.id_protocolo = a.id_protocolo ");
		sql.append("JOIN unidade u ON a.id_unidade = u.id_unidade ");
		sql.append("JOIN tarefa t ON a.id_tarefa = t.id_tarefa ");
		sql.append("WHERE p.id_protocolo = :protocolo ");
		sql.append("and  t.id_tarefa in (32, 29, 1) ");
		sql.append("group by u.id_unidade order by u.idx_unidade; ");
		
		Query query = em.createNativeQuery(sql.toString(), Unidade.class);
		
		query.setParameter("protocolo", idProcedimento);
		
		return query.getResultList();
	}
}
