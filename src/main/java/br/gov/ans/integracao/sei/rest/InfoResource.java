package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.parseInt;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.dao.LogIntegracaoSistemicaDAO;
import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.modelo.LogIntegracaoSistemica;
import br.gov.ans.utils.LogIgnore;
import br.gov.ans.utils.MessageUtils;

@LogIgnore
@Path("/info")
public class InfoResource {

	@Inject
	private Logger logger;
	
    @Inject
    private MessageUtils messages;
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
	
	@PersistenceContext(unitName = "sei_pu", type = PersistenceContextType.EXTENDED)	
	private EntityManager emMySQL;
	
	@PersistenceContext(unitName = "sei_broker_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager emOracle;
	
	@Inject	
	private LogIntegracaoSistemicaDAO dao;

	@GET
	@Path("/versao")
	@Produces(MediaType.TEXT_PLAIN)
	public String getNumeroVersao(){
		return messages.getMessage("sei.broker.versao");
	}	

	@GET
	@Path("/conexoes/mysql")
	@Produces(MediaType.TEXT_PLAIN)
	public String testMySQLConnection() throws Exception{
		Query query = emMySQL.createNativeQuery(Constantes.MYSQL_SQL_TEST_CONECTION);
		
		try{
			return((String) query.getSingleResult());
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.testar.mysql"));
			throw new Exception(messages.getMessage("erro.testar.mysql"));
		}
	}

	@GET
	@Path("/conexoes/oracle")
	@Produces(MediaType.TEXT_PLAIN)
	public String testOracleConnection() throws Exception{
		Query query = emOracle.createNativeQuery(Constantes.ORACLE_SQL_TEST_CONECTION);
		
		try{
			return ((String) query.getSingleResult());
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.testar.oracle"),ex);
			throw new Exception(messages.getMessage("erro.testar.oracle"));
		}
	}

	@GET
	@Path("/conexoes/sei")
	@Produces(MediaType.TEXT_PLAIN)
	public String testSEIConnection() throws Exception{
		try{
			seiNativeService.listarUnidades(Constantes.SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, null, null);
			
			return Constantes.SEI_RESPONDEU_COM_SUCESSO;
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.testar.sei"),ex);
			throw new Exception(messages.getMessage("erro.testar.sei"));
		}
	}

	@GET
	@Path("/requests")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<LogIntegracaoSistemica> getUltimosRequests(@QueryParam("pag") String pagina, @QueryParam("itens") String qtdRegistros, 
			@QueryParam("operacao") String operacao, @QueryParam("origem") String origem) throws BusinessException{
				
		return dao.getLogs(operacao, origem, pagina == null? null:parseInt(pagina), qtdRegistros == null? null : parseInt(qtdRegistros));
	}

}
