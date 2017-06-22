package br.gov.ans.factories;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.gov.ans.dao.DAO;

public class DAOFactory implements Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "sei_broker_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Produces
	public DAO createDAO(InjectionPoint injectionPoint) {
		ParameterizedType type = (ParameterizedType) injectionPoint.getType();
		Class classe = (Class) type.getActualTypeArguments()[0];

		return new DAO(classe, em);
	}
	
}
