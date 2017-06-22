package br.gov.ans.utils;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import br.gov.ans.dao.DAO;
import br.gov.ans.modelo.LogIntegracaoSistemica;

@Stateless
public class LogIntegracaoUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(LogIntegracaoUtil.class);
	
	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	private DAO<LogIntegracaoSistemica> dao;
	
	public void registrarLog(String user, String resource, String methodName){		
		LogIntegracaoSistemica log = new LogIntegracaoSistemica();
		
		log.setOrigem(user);
		log.setDestino(resource);
		log.setOperacao(methodName);		
		log.setData(new Date());
		
		logger.debug("O usuário " + user + " requisitou o " + methodName);
		
		dao.persist(log);
	}
}
