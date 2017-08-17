package br.gov.ans.integracao.sei.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.client.ArquivoExtensao;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/")
public class ExtensoesResource {

    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
    
	/**
	 * @api {get} /:unidade/extensoes Listar extensões
	 * @apiName listarExtensoesPermitidas
	 * @apiGroup Extensao
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Este método realiza uma busca pelas extensões de arquivos permitidas.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * 
	 * @apiParam (Query Parameters) {String} [extensao=null] Para filtrar por uma determinada extensão.
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://<host>/sei-broker/service/COSAP/extensoes/
	 *
	 * @apiSuccess (Sucesso - 200) {ArquivoExtensao[]} extensoes Lista de extensões permitidas.
	 * @apiSuccess (Sucesso - 200) {String} extensoes.idArquivoExtensao Identificador interno do SEI relativo a extensão
	 * @apiSuccess (Sucesso - 200) {String} extensoes.extensao Texto da extensão (ex.: pdf, ods, doc, ppt,...)
	 * @apiSuccess (Sucesso - 200) {String} extensoes.descricao Descrição da extensão
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
    @Path("{unidade}/extensoes")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public ArquivoExtensao[] listarExtensoesPermitidas(@PathParam("unidade") String unidade, @QueryParam("extensao") String extensao) throws Exception{
		return seiNativeService.listarExtensoesPermitidas(Constantes.SEI_BROKER, Operacao.LISTAR_EXTENSOES, unidadeResource.consultarCodigo(unidade), extensao);		
	}
}
