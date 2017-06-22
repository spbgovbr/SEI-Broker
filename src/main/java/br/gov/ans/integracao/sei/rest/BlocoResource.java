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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.integracao.sei.client.RetornoConsultaBloco;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.InclusaoDocumentoBloco;
import br.gov.ans.integracao.sei.modelo.InclusaoProcessoBloco;
import br.gov.ans.integracao.sei.modelo.NovoBloco;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;

@Path("")
@Stateless
public class BlocoResource {
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
    
	@Inject
	private MessageUtils messages;
    
	@Context
	private UriInfo uriInfo;
    
	/**
	 * @api {get} /:unidade/blocos/:bloco Consultar bloco
	 * @apiName consultarBloco
	 * @apiGroup Bloco
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Recupera as informações do bloco informado.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} bloco Numero do bloco que deseja consultar
	 * 
	 * @apiParam (Query Parameters) {String} [protocolos=N] S ou N para exibir os protocolos do bloco
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://<host>/sei-broker/service/COSAP/blocos/12
	 *
	 * @apiSuccess (Sucesso - 200) {RetornoConsultaBloco} bloco Objeto representando o bloco encontrado
	 * @apiSuccess (Sucesso - 200) {String} bloco.idBloco Número do bloco
	 * @apiSuccess (Sucesso - 200) {Unidade} bloco.unidade Dados das unidade que gerou o bloco
	 * @apiSuccess (Sucesso - 200) {String} bloco.unidade.idUnidade Identificador da Unidade
	 * @apiSuccess (Sucesso - 200) {String} bloco.unidade.sigla Sigla da unidade
	 * @apiSuccess (Sucesso - 200) {String} bloco.unidade.descricao Descrição do unidade
	 * @apiSuccess (Sucesso - 200) {Usuario} bloco.usuario Dados das unidade que gerou o bloco
	 * @apiSuccess (Sucesso - 200) {String} bloco.usuario.idUsuario Identificador do suário
	 * @apiSuccess (Sucesso - 200) {String} bloco.usuario.sigla Sigla do usuário
	 * @apiSuccess (Sucesso - 200) {String} bloco.usuario.nome Nome do usuário
	 * @apiSuccess (Sucesso - 200) {String} bloco.descricao Descrição do bloco
	 * @apiSuccess (Sucesso - 200) {String} bloco.tipo Tipo do bloco (A=Assinatura, R=Reunião ou I=Interno)
	 * @apiSuccess (Sucesso - 200) {String} bloco.estado Estado do bloco (A=Aberto, D=Disponibilizado, R=Retornado ou C=Concluído)
	 * @apiSuccess (Sucesso - 200) {Unidade[]} bloco.unidadesDisponibilizacao Dados das unidades configuradas para disponibilização (ver estrutura Unidade)
	 * @apiSuccess (Sucesso - 200) {ProtocoloBloco[]} bloco.protocolos Processos ou documentos do bloco
	 * @apiSuccess (Sucesso - 200) {String} bloco.protocolos.protocoloFormatado.identificacao Tipo do processo ou documento
	 * @apiSuccess (Sucesso - 200) {Assinatura[]} bloco.protocolos.assinaturas Conjunto de assinaturas dos documentos. Será um conjunto vazio caso não existam informações ou se o protocolo representa um processo
	 * @apiSuccess (Sucesso - 200) {String} bloco.protocolos.assinaturas.nome Nome do assinante
	 * @apiSuccess (Sucesso - 200) {String} bloco.protocolos.assinaturas.cargoFuncao Cargo ou função utilizado no momento da assinatura
	 * @apiSuccess (Sucesso - 200) {String} bloco.protocolos.assinaturas.dataHora Data/hora em que ocorreu a assinatura
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
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Disponibiliza um determinado bloco.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiParam (Request Body) {String} bloco Numero do bloco que deseja disponibilizar
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://<host>/sei-broker/service/COSAP/blocos/disponibilizados
	 *
	 *	body:
	 *	12
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
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
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Cancela a disponibilização de blocos.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} bloco Numero do bloco que deseja indisponibilizar
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://<host>/sei-broker/service/COSAP/blocos/disponibilizados/12
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
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
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Exclui um bloco criado.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} bloco Numero do bloco que deseja excluir
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://<host>/sei-broker/service/COSAP/blocos/12
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
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
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Gera um novo bloco.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI 
	 * 
	 * @apiParam (Request Body) {NovoBloco} novoBloco Objeto de criação de bloco
	 * @apiParam (Request Body) {String="ASSINATURA","INTERNO","REUNIAO"} novoBloco.tipo Tipo do bloco a ser criado
	 * @apiParam (Request Body) {String} novoBloco.descricao Descrição do bloco
	 * @apiParam (Request Body) {String[]} novoBloco.unidades Códigos das unidades onde o bloco deve ser disponibilizado, ou vazio para não disponibilizar
	 * @apiParam (Request Body) {String[]} novoBloco.documentos Código dos documentos que serão incluídos no bloco
	 * @apiParam (Request Body) {Boolean} novoBloco.disponibilizar=false Informa se o bloco criado deve ser disponibilizado automaticamente
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://<host>/sei-broker/service/COSAP/blocos
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
	 * @apiSuccess (Sucesso Response Body - 201) {String} resultado Código do bloco criado
	 * 
	 * @apiSuccess (Sucesso Response Header - 201) {header} Location URL de acesso ao recurso criado
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
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Inclui um documento no bloco.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} bloco Número do bloco onde o documento será incluído
	 * 
	 * @apiParam (Request Body) {String} documento Número do documento que será incluído do bloco
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://<host>/sei-broker/service/COSAP/blocos/12/documentos
	 *
	 *	body:
	 *	0000050
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
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
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String incluirDocumentoNoBloco(@PathParam("unidade") String unidade, 
			@PathParam("bloco") String bloco, String documento) throws Exception{
		String resultado = seiNativeService.incluirDocumentoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_DOCUMENTO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, documento,
				null);

		return trueOrFalse(resultado) + "";
	}
	
	/**
	 * @api {POST} /:unidade/blocos/:bloco/documentos Incluir documento anotado
	 * @apiName incluirDocumentoComAnotacaoNoBloco
	 * @apiGroup Bloco
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Inclui um documento no bloco.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} bloco Número do bloco onde o documento será incluído
	 * 
	 * @apiParam (Request Body) {InclusaoDocumentoBloco} inclusao Objeto com os dados do documento a ser incluído
	 * @apiParam (Request Body) {String} bloco Número do bloco onde o documento será inserido
	 * @apiParam (Request Body) {String} documento Número do documento
	 * @apiParam (Request Body) {String} [anotacao] Texto de anotação associado com o documento no bloco
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://<host>/sei-broker/service/COSAP/blocos/12/documentos
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
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response incluirDocumentoComAnotacaoNoBloco(@PathParam("unidade") String unidade, 
			@PathParam("bloco") String bloco, InclusaoDocumentoBloco inclusao) throws Exception{
		String resultado = seiNativeService.incluirDocumentoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_DOCUMENTO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, 
				inclusao.getDocumento(), inclusao.getAnotacao());

		if(trueOrFalse(resultado)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.documento.incluir.bloco"));
		}		
	}
	
	/**
	 * @api {delete} /:unidade/:bloco/documentos/:documento Remover documento
	 * @apiName retirarDocumentoDoBloco
	 * @apiGroup Bloco
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Remove o documento do bloco.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI	
	 * @apiParam (Path Parameters) {String} bloco Numero do bloco de onde o documento será retirado
	 * @apiParam (Path Parameters) {String} documento Numero do documento que será retirado do bloco
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://<host>/sei-broker/service/COSAP/blocos/12/documentos/0000050
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
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
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Inclui um processo no bloco.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} bloco Numero do bloco onde o processo será incluído
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [auto-formatacao=S] O broker utilizará a mascara padrão para formatar o número do processo
	 * 
	 * @apiParam (Request Body) {String} processo Numero do processo que será incluído no bloco
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X PUT http://<host>/sei-broker/service/COSAP/blocos/12/processos
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
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
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String incluirProcessoNoBloco(@PathParam("unidade") String unidade, 
			@PathParam("bloco") String bloco, @QueryParam("auto-formatacao") String autoFormatar, String processo) throws Exception{
		if(isAutoFormatar(autoFormatar)){			
			processo = formatarNumeroProcesso(processo);
		}
		
		String resultado =  seiNativeService.incluirProcessoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_PROCESSO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, 
				processo, null);
		
		return trueOrFalse(resultado) + "";
	}
	
	/**
	 * @api {post} /:unidade/blocos/:bloco/processos Incluir processo anotado
	 * @apiName incluirProcessoComAnotacaoNoBloco
	 * @apiGroup Bloco
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Inclui um processo no bloco, junto com uma anotação.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} bloco Numero do bloco onde o processo será incluído
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [auto-formatacao=S] O broker utilizará a mascara padrão para formatar o número do processo
	 * 
	 * @apiParam (Request Body) {InclusaoProcessoBloco} inclusao Objeto com os dados do processo a ser incluído
	 * @apiParam (Request Body) {String} bloco Número do bloco onde o processo será inserido
	 * @apiParam (Request Body) {String} processo Número do processo
	 * @apiParam (Request Body) {String} [anotacao] Texto de anotação associado com o processo no bloco
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X PUT http://<host>/sei-broker/service/COSAP/blocos/12/processos
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
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response incluirProcessoComAnotacaoNoBloco(@PathParam("unidade") String unidade, @QueryParam("auto-formatacao") String autoFormatar, @PathParam("bloco") String bloco, 
			InclusaoProcessoBloco inclusao) throws Exception{		
		if(StringUtils.isNotBlank(inclusao.getProcesso()) && isAutoFormatar(autoFormatar)){
			String numeroFormatado = formatarNumeroProcesso(inclusao.getProcesso());
			inclusao.setProcesso(numeroFormatado);
		}
		
		String resultado =  seiNativeService.incluirProcessoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_PROCESSO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, 
				inclusao.getProcesso(), inclusao.getAnotacao());
		
		if(trueOrFalse(resultado)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.processo.incluir.bloco"));
		}
	}
	
	/**
	 * @api {delete} /:unidade/blocos/:bloco/processos/:processo Remover processo
	 * @apiName retirarProcessoDoBloco
	 * @apiGroup Bloco
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Este método remove o processo do bloco.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} bloco Numero do bloco de onde o processo será retirado
	 * @apiParam (Path Parameters) {String} processo Numero do processo que será retirado do bloco
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [auto-formatacao=S] O broker utilizará a mascara padrão para formatar o número do processo
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://<host>/sei-broker/service/COSAP/blocos/12/processos/33910000029201653
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
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
	public String retirarProcessoDoBloco(@PathParam("unidade") String unidade, @PathParam("processo") String processo, @QueryParam("auto-formatacao") String autoFormatar, 
			@PathParam("bloco") String bloco)  throws Exception{
		if(isAutoFormatar(autoFormatar)){			
			processo = formatarNumeroProcesso(processo);
		}
		
		String resultado = seiNativeService.retirarProcessoBloco(Constantes.SEI_BROKER, Operacao.RETIRAR_PROCESSO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, processo);
				
		return trueOrFalse(resultado) + "";
	}
	
	public URI getResourcePath(String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}
	
	public boolean isAutoFormatar(String valor){
		return !("N".equals(valor) || "n".equals(valor));
	}
}
