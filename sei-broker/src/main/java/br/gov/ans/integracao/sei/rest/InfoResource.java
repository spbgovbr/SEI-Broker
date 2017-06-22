package br.gov.ans.integracao.sei.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.gov.ans.factories.qualifiers.PropertiesInfo;


@Path("/info")
@Stateless
public class InfoResource {
	
	@Inject
	@PropertiesInfo(file="config.properties", key="versao.sistema")
	public String versao;
	
	
	/**
	 * @api {get} /info/versao Consultar versão
	 * @apiName getNumeroVersao
	 * @apiGroup Info
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza uma consulta para saber a versão do sei-broker que está disponível.
	 * 	
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://anshmjboss01/sei-broker/service/info/versao
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
	
}
