package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.parseInt;
import static br.gov.ans.integracao.sei.utils.Util.setPaginacaoQuery;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.logging.Logger;

import br.gov.ans.dao.DAO;
import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.factories.qualifiers.PropertiesInfo;
import br.gov.ans.modelo.LogIntegracaoSistemica;
import br.gov.ans.utils.MessageUtils;


@Named
@Path("/info")
@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class InfoResource {

	@Inject
	private Logger logger;
	
    @Inject
    private MessageUtils messages;
	
	@Inject
	@PropertiesInfo(file="config.properties", key="versao.sistema")
	public String versao;
	
	@PersistenceContext(unitName = "sei_pu", type = PersistenceContextType.EXTENDED)	
	private EntityManager emMySQL;
	
	@PersistenceContext(unitName = "sei_broker_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager emOracle;
	
	@Inject
	private DAO<LogIntegracaoSistemica> dao;
	
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
	@Produces(MediaType.APPLICATION_JSON)
	public String getNumeroVersao(){
		return versao;
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
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Banco testMySQLConnection() throws Exception{
		Query query = emMySQL.createNativeQuery("SELECT version()");
		Banco banco = new Banco();
		
		try{
			banco.setVersao((String) query.getSingleResult());					
			return banco; 
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.testar.mysql"));
			throw new Exception(messages.getMessage("erro.testar.mysql"));
		}
	}
	
	/**
	 * @api {get} /info/conexoes/oracle Testar conexão MySQL
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
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Banco testOracleConnection() throws Exception{
		Query query = emOracle.createNativeQuery("SELECT * FROM V$VERSION WHERE ROWNUM = 1");
		Banco banco = new Banco();
		
		try{
			banco.setVersao((String) query.getSingleResult());					
			return banco; 
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.testar.oracle"),ex);
			throw new Exception(messages.getMessage("erro.testar.oracle"));
		}
	}
	
	/**
	 * @api {get} /info Informações
	 * @apiName getInfo
	 * @apiGroup Info
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Retorna informações da aplicação
	 * 	
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://<host>/sei-broker/service/info
	 *
	 * @apiSuccess {Info} info Informações da aplicação.
	 * @apiSuccess {Banco} info.oracle Informações da conexão Oracle.
	 * @apiSuccess {String} info.oracle.versao Versão do banco.
	 * @apiSuccess {Banco} info.mysql Informações da conexão MySQL.
	 * @apiSuccess {String} info.mysql.versao Versão do banco.
	 * @apiSuccess {String} info.versao Versão do SEI-Broker.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Info getInfo() throws Exception{
		Info info = new Info();
		
		info.setVersao(getNumeroVersao());
		info.setMySql(testMySQLConnection());
		info.setOracle(testOracleConnection());
		
		return info;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/requests")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<LogIntegracaoSistemica> getUltimosRequests(@QueryParam("pag") String pagina, @QueryParam("itens") String qtdRegistros) throws BusinessException{
		Query query = dao.createNamedQuery("LogIntegracaoSistemica.ultimosRequests", null);
		
		setPaginacaoQuery(query, pagina == null? null:parseInt(pagina), qtdRegistros == null? null : parseInt(qtdRegistros));
				
		return query.getResultList();
	}
		
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getInfoHTML() throws Exception{
		StringBuilder html = new StringBuilder();
		Info informacoes = getInfo();
		List<LogIntegracaoSistemica> requests = getUltimosRequests("1", "10");
		
		html.append("<html>");
		html.append("<head><title>SEI-Broker - informações</title></head>");
		html.append("<body>");
		html.append("<h3>Informações</h3>");
		html.append("<table border='1'>");
		html.append("<tr><td>SEI-Broker</td><td>"+informacoes.getVersao()+"</td></tr>");
		html.append("<tr><td>Oracle</td><td>"+informacoes.getOracle().getVersao()+"</td></tr>");
		html.append("<tr><td>MySQL</td><td>"+informacoes.getMySql().getVersao()+"</td></tr>");
		html.append("</table>");
		html.append("</br>");
		html.append("<h3>Últimos requests</h3>");
		html.append("<table border='1'>");
		
		html.append("<tr><th>DATA</th><th>ORIGEM</th></th><th>DESTINO</th></tr>");
		for(LogIntegracaoSistemica l : requests){
			html.append("<tr><td>"+l.getData()+"</td><td>"+l.getOrigem()+"</td></td><td>"+l.getDestino()+"</td></tr>");
		}
		
		html.append("</table>");
		html.append("</body>");
		html.append("</html>");
		
		return html.toString();
	}
	
	
	@XmlRootElement
	public static class Banco {
		private String versao;
		
		public String getVersao() {
			return versao;
		}

		public void setVersao(String versao) {
			this.versao = versao;
		}
	}
	
	@XmlRootElement
	public static class Info {
		private String versao;
		private Banco oracle;
		private Banco mySql;
				
		public String getVersao() {
			return versao;
		}

		public void setVersao(String versao) {
			this.versao = versao;
		}

		public Banco getOracle() {
			return oracle;
		}

		public void setOracle(Banco oracle) {
			this.oracle = oracle;
		}

		public Banco getMySql() {
			return mySql;
		}

		public void setMySql(Banco mySql) {
			this.mySql = mySql;
		}
	}
}
