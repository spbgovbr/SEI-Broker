package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.formatarNumeroProcesso;
import static br.gov.ans.integracao.sei.utils.Util.getSOuN;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;

import java.net.URI;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.gov.ans.integracao.sei.client.RetornoConsultaBloco;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.NovoBloco;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("")
@Stateless
public class BlocoResource {
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
    
	@Context
	private UriInfo uriInfo;
    
	/**
	 * @api {get} /:unidade/blocos/:bloco Consultar bloco
	 * @apiName consultarBloco
	 * @apiGroup Bloco
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método recupera as informações do bloco informado.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} bloco Numero do bloco que deseja consultar
	 * @apiParam {String} [protocolos=N] S ou N para exibir os protocolos do bloco
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://anshmjboss01/sei-broker/service/COSAP/blocos/12
	 *
	 * @apiSuccess {RetornoConsultaBloco} Retorno json ou xml com informações do bloco
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */    
	@GET
	@Path("{unidade}/blocos/{bloco}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public RetornoConsultaBloco consultarBloco(@PathParam("unidade") String unidade, @PathParam("bloco") String bloco, 
			@QueryParam("protocolos") String exibirProtocolos) throws Exception{
		return seiNativeService.consultarBloco(Constantes.SEI_BROKER, Operacao.CONSULTAR_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, getSOuN(exibirProtocolos));
	}
	
	
	/**
	 * @api {post} /:unidade/blocos/disponibilizados Disponibilizar bloco
	 * @apiName disponibilizarBloco
	 * @apiGroup Bloco
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método disponibiliza blocos.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} bloco Numero do bloco que deseja disponibilizar
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/blocos/disponibilizados
	 *
	 *	body:
	 *	12
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Path("{unidade}/blocos/disponibilizados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String disponibilizarBloco(@PathParam("unidade") String unidade, String bloco) throws Exception{
		String resultado = seiNativeService.disponibilizarBloco(Constantes.SEI_BROKER, Operacao.DISPONIBILIZAR_BLOCO, unidadeResource.consultarCodigo(unidade), bloco);
		
		return trueOrFalse(resultado) + "";
	}
	
	
	/**
	 * @api {delete} /:unidade/blocos/disponibilizados/12 Indisponibilizar bloco
	 * @apiName cancelarDisponibilizacaoBloco
	 * @apiGroup Bloco
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método cancela a disponibilização de blocos.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} bloco Numero do bloco que deseja indisponibilizar
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/blocos/disponibilizados/12
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@DELETE
	@Path("{unidade}/blocos/disponibilizados/{bloco}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String cancelarDisponibilizacaoBloco(@PathParam("unidade") String unidade, @PathParam("bloco") String bloco) throws Exception{
		String resultado = seiNativeService.cancelarDisponibilizacaoBloco(Constantes.SEI_BROKER, Operacao.CANCELAR_DISPONIBILIZACAO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco);
				
		return trueOrFalse(resultado) + "";
	}
	
	
	/**
	 * @api {delete} /:unidade/blocos/:bloco Excluir bloco
	 * @apiName excluirBloco
	 * @apiGroup Bloco
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método exclui um bloco criado.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} bloco Numero do bloco que deseja excluir
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/blocos/12
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@DELETE
	@Path("{unidade}/blocos/{bloco}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String excluirBloco(@PathParam("unidade") String unidade, @PathParam("bloco") String bloco) throws Exception{
		String resultado = seiNativeService.excluirBloco(Constantes.SEI_BROKER, Operacao.EXCLUIR_BLOCO, unidadeResource.consultarCodigo(unidade), bloco);
				
		return trueOrFalse(resultado) + "";
	}
	
	/**
	 * @api {post} /:unidade/blocos Gerar bloco
	 * @apiName gerarBloco
	 * @apiGroup Bloco
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método gera um novo bloco.
	 * 
	 * @apiParam {String="ASSINATURA","INTERNO","REUNIAO"} tipo Tipo do bloco a ser criado
	 * @apiParam {String} descricao Descrição do bloco
	 * @apiParam {String[]} unidades Códigos das unidades onde o bloco deve ser disponibilizado, ou vazio para não disponibilizar
	 * @apiParam {String[]} documentos Código dos documentos que serão incluídos no bloco
	 * @apiParam {Boolean} disponibilizar=false Informa se o bloco criado deve ser disponibilizado
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://anshmjboss01/sei-broker/service/COSAP/blocos
	 *
	 *	body:
	 *	{
	 *		"tipo":"ASSINATURA",
	 *		"descricao":"Bloco demonstrativo.",
	 *		"unidades":["110000935"],
	 *		"documentos":["0000131"],
	 *		"disponibilizar":true
	 *	}
	 *
	 * @apiSuccess {String} resultado Código do bloco criado
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@Path("{unidade}/blocos")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response gerarBloco(@PathParam("unidade") String unidade, NovoBloco bloco) throws Exception{
		String retorno = seiNativeService.gerarBloco(Constantes.SEI_BROKER, Operacao.GERAR_BLOCO, unidadeResource.consultarCodigo(unidade), bloco.getTipo().getCodigo(), bloco.getDescricao(), bloco.getUnidades(), 
					bloco.getDocumentos(), getSOuN(bloco.isDisponibilizar()));	
		
		return Response.created(getResourcePath(retorno)).entity(retorno).build();
	}
	
	/**
	 * @api {POST} /:unidade/blocos/:bloco/documentos Incluir documento
	 * @apiName incluirDocumentoNoBloco
	 * @apiGroup Bloco
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método inclui um documento no bloco.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} documento Numero do documento que será incluído do bloco
	 * @apiParam {String} bloco Numero do bloco onde o documento será incluído
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/blocos/12/documentos
	 *
	 *	body:
	 *	0000050
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Path("{unidade}/blocos/{bloco}/documentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String incluirDocumentoNoBloco(@PathParam("unidade") String unidade, 
			@PathParam("bloco") String bloco, String documento) throws Exception{
		String resultado = seiNativeService.incluirDocumentoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_DOCUMENTO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, documento);

		return trueOrFalse(resultado) + "";
	}
	
	/**
	 * @api {delete} /:unidade/:bloco/documentos/:documento Remover documento
	 * @apiName retirarDocumentoDoBloco
	 * @apiGroup Bloco
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método remove o documento do bloco.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI	
	 * @apiParam {String} documento Numero do documento que será retirado do bloco
	 * @apiParam {String} bloco Numero do bloco de onde o documento será retirado
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/blocos/12/documentos/0000050
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@DELETE
	@Path("{unidade}/blocos/{bloco}/documentos/{documento}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String retirarDocumentoDoBloco(@PathParam("unidade") String unidade, @PathParam("bloco") String bloco, @PathParam("documento") String documento) throws Exception{
		String resultado = seiNativeService.retirarDocumentoBloco(Constantes.SEI_BROKER, Operacao.RETIRAR_DOCUMENTO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, documento);
						
		return trueOrFalse(resultado) + "";
	}
	
	/**
	 * @api {post} /:unidade/blocos/:bloco/processos Incluir processo
	 * @apiName incluirProcessoNoBloco
	 * @apiGroup Bloco
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método inclui um processo no bloco.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} processo Numero do processo que será incluído no bloco
	 * @apiParam {String} bloco Numero do bloco onde o processo será incluído
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X PUT http://anshmjboss01/sei-broker/service/COSAP/blocos/12/processos
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Path("{unidade}/blocos/{bloco}/processos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String incluirProcessoNoBloco(@PathParam("unidade") String unidade, 
			@PathParam("bloco") String bloco, String processo) throws Exception{
		String resultado =  seiNativeService.incluirProcessoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_PROCESSO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, formatarNumeroProcesso(processo));
		
		return trueOrFalse(resultado) + "";
	}
	
	/**
	 * @api {delete} /:unidade/blocos/:bloco/processos/:processo Remover processo
	 * @apiName retirarProcessoDoBloco
	 * @apiGroup Bloco
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método remove o processo do bloco.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} processo Numero do processo que será retirado do bloco
	 * @apiParam {String} bloco Numero do bloco de onde o processo será retirado
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/blocos/12/processos/33910000029201653
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@DELETE
	@Path("{unidade}/blocos/{bloco}/processos/{processo}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String retirarProcessoDoBloco(@PathParam("unidade") String unidade, @PathParam("processo") String processo, 
			@PathParam("bloco") String bloco)  throws Exception{
		String resultado = seiNativeService.retirarProcessoBloco(Constantes.SEI_BROKER, Operacao.RETIRAR_PROCESSO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, formatarNumeroProcesso(processo));
				
		return trueOrFalse(resultado) + "";
	}
	
	public URI getResourcePath(String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}
}
