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

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.dao.LogIntegracaoSistemicaDAO;
import br.gov.ans.integracao.sei.modelo.Operacao;
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
	
	/**
	 * @api {get} /info/versao Consultar versão
	 * @apiName getNumeroVersao
	 * @apiGroup Info
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Este método realiza uma consulta para saber a versão do sei-broker que está disponível.
	 * 	
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://<host>/sei-broker/service/info/versao
	 *
	 * @apiSuccess {String} versao Número da versão.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/versao")
	@Produces(MediaType.TEXT_PLAIN)
	public String getNumeroVersao(){
		return messages.getMessage("sei.broker.versao");
	}	
	
	/**
	 * @api {get} /info/conexoes/mysql Testar conexão MySQL
	 * @apiName testMySQLConnection
	 * @apiGroup Info
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Testa a conexão com o MySQL e retorna o número de versão do banco.
	 * 	
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://<host>/sei-broker/service/info/conexoes/mysql
	 *
	 * @apiSuccess {String} versao Número de versão do MySQL.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
	
	/**
	 * @api {get} /info/conexoes/oracle Testar conexão Oracle
	 * @apiName testOracleConnection
	 * @apiGroup Info
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Testa a conexão com o Oracle e retorna o número de versão do banco.
	 * 	
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://<host>/sei-broker/service/info/conexoes/oracle
	 *
	 * @apiSuccess {String} versao Número de versão do Oracle.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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

	/**
	 * @api {get} /info/conexoes/sei Testar conexão SEI
	 * @apiName testSEIConnection
	 * @apiGroup Info
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Testa a conexão com o SEI fazendo uma consulta ao serviço listar unidades.
	 * 	
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://<host>/sei-broker/service/info/conexoes/sei
	 *
	 * @apiSuccess {String} mensagem Mensagem de sucesso.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/conexoes/sei")
	@Produces(MediaType.TEXT_PLAIN)
	public String testSEIConnection() throws Exception{
		try{
			seiNativeService.listarUnidades(Constantes.SEI_BROKER, Operacao.LISTAR_UNIDADES, null, null);
			
			return Constantes.SEI_RESPONDEU_COM_SUCESSO;
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.testar.sei"),ex);
			throw new Exception(messages.getMessage("erro.testar.sei"));
		}
	}
	
	/**
	 * @api {get} /info/requests Listar Requests
	 * @apiName getUltimosRequests
	 * @apiGroup Info
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Lista os requests recebidos pelo broker.
	 * 
	 * 
	 * @apiParam (Query Parameters) {String} [operacao] nome do método acessado
	 * @apiParam (Query Parameters) {String} [origem] usuário que originou a requisição
	 * @apiParam (Query Parameters) {int} [pag=1] número da página
	 * @apiParam (Query Parameters) {int} [itens=50] quantidade de itens listados por página
	 * 
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://<host>/sei-broker/service/info/requests
	 *
	 * @apiSuccess {String} mensagem Mensagem de sucesso.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/requests")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<LogIntegracaoSistemica> getUltimosRequests(@QueryParam("pag") String pagina, @QueryParam("itens") String qtdRegistros, 
			@QueryParam("operacao") String operacao, @QueryParam("origem") String origem) throws BusinessException{
				
		return dao.getLogs(operacao, origem, pagina == null? null:parseInt(pagina), qtdRegistros == null? null : parseInt(qtdRegistros));
	}

}
