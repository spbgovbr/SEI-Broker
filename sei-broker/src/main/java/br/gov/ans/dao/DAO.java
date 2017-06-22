package br.gov.ans.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Transaction;

public class DAO<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private EntityManager em;

	
	protected final Class<T> classe;
		
	public DAO(Class<T> classe, EntityManager em){
		this.classe = classe;
		this.em = em;
	}
	
	@Transactional
	public void persist(T t){		
		em.persist(t);
		em.flush();
	}
	
	public void persistSemJTA(T t){
		org.hibernate.Session session = (org.hibernate.Session) em.getDelegate();
		
		Transaction transaction = session.getTransaction();
		
		transaction.begin();
		
		session.persist(t);
		
		transaction.commit();
	}
	
	@Transactional
	public void merge(T t){
		em.merge(t);
	}
	
	@Transactional
    public void remove(T t) {
        em.remove(em.merge(t));
    }
	
	@Transactional
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Long count() {
        CriteriaQuery criteria = em.getCriteriaBuilder().createQuery();
        Root<T> root = criteria.from(classe);
        
        criteria.select(em.getCriteriaBuilder().count(root));
        javax.persistence.Query q = em.createQuery(criteria);
        
        return (Long) q.getSingleResult();
    } 
    
	@Transactional
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> executeNamedQuery(String queryName, Map<String, Object> params){
		Query query = em.createNamedQuery(queryName, classe);
		
		if ((params != null) && (!params.isEmpty())) {
			for (Map.Entry entry : params.entrySet()) {
				query.setParameter((String) entry.getKey(),entry.getValue());
			}
		}
		
		return query.getResultList();
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> getPaginatedResultList(int first, int pageSize) {
		Query query = em.createQuery("from " + classe.getName());
		
        if (pageSize >= 0){
        	query.setMaxResults(pageSize);
        }
        if (first >= 0){
        	query.setFirstResult(first);
        }
        
        return query.getResultList();
	}
		
}
