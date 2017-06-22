package br.gov.ans.integracao.sei.rest;

import static org.apache.commons.lang3.StringUtils.isBlank;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.client.Serie;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/")
@Stateless
public class SeriesResource {

    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
	  
	/**
	 * @api {get} /:unidade/series Listar séries
	 * @apiName listarSeries
	 * @apiGroup Serie
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza uma consulta às séries.
	 * 
	 * @apiParam {String} [tipo-processo=null] Para filtrar por determinado tipo de processo.
	 * @apiParam {String} [unidade=null] Para filtrar por séries de determinada unidade.
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://anshmjboss01/sei-broker/service/COSAP/series
	 *
	 * @apiSuccess {Serie[]} series Lista de séries.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/series")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Serie[] listarSeries(@PathParam("unidade") String unidade, @QueryParam("tipo-processo") String tipoProcesso) throws Exception{
		return seiNativeService.listarSeries(Constantes.SEI_BROKER, Operacao.LISTAR_SERIES, isBlank(unidade)? null : unidadeResource.consultarCodigo(unidade), tipoProcesso);				
	}
}
