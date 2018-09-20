package br.gov.ans.integracao.sei.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import br.gov.ans.integracao.sei.modelo.Contato;

public class ContatoDAO {
	@PersistenceContext(unitName = "sei_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	public Contato getContatoPeloId(String id){
		Query query = em.createNamedQuery("Contato.pesquisarPorID",Contato.class);
		
		query.setParameter("id", Integer.parseInt(id));
		
		return (Contato) query.getSingleResult();				
	}
	
	public Contato getContatoPelaSigla(String sigla){
		Query query = em.createNamedQuery("Contato.pesquisarPorSigla",Contato.class);
		
		query.setParameter("sigla", sigla);
		
		return (Contato) query.getSingleResult();				
	}
	
	public List<Contato> getContatosNaoTemporariosPelaSigla(String sigla){
		Query query = em.createNamedQuery("Contato.pesquisarPorSigla",Contato.class);
		
		query.setParameter("sigla", sigla);
		
		return query.getResultList();
	}
}
