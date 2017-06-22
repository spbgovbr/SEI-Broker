package br.gov.ans.integracao.sei.rest;

import java.net.URI;
import java.rmi.RemoteException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.integracao.sei.client.Documento;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.Arquivo;
import br.gov.ans.integracao.sei.modelo.ArquivoCriado;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.modelo.ParteArquivo;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/")
@Stateless
public class ArquivoResource {
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
    
    @Inject
    private UnidadeResource unidadeResource;
	
	@Context
	private UriInfo uriInfo;
    
	/** 
	 * @api {post} /:unidade/arquivos Adicionar arquivo
	 * @apiName adicionarArquivo
	 * @apiGroup Arquivo
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription O serviço criará um arquivo no repositório de documentos e retornará seu identificador. O envio do arquivo poderá ser particionado com chamadas 
	 * posteriores ao serviço de Adicionar Conteúdo Arquivo. Após todo o conteúdo ser transferido o arquivo será ativado e poderá ser associado com um documento externo 
	 * no serviço de inclusão de documento. Serão excluídos em 24 horas os arquivos não completados e não associados a um documento.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 *  
	 * @apiParam (Request Body) {Arquivo} arquivo Objeto representando um arquivo.
	 * @apiParam (Request Body) {String} arquivo.nome Nome do arquivo.
	 * @apiParam (Request Body) {String} arquivo.tamanho Tamanho total do arquivo em bytes.
	 * @apiParam (Request Body) {String} arquivo.hash MD5 do conteúdo total do arquivo.
	 * @apiParam (Request Body) {String} arquivo.conteudo Conteúdo total ou parcial codificado em Base64.
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: https://<host>/sei-broker/service/COSAP/arquivos
	 *
	 *	body:
	 *	{
	 *		"nome":"documentos-sei-broker.pdf",
	 *		"tamanho":"2048",
	 *		"hash":"45F1DEFFB45A5F6C2380A4CEE9B3E452",
	 *		"conteudo":"Conteúdo total ou parcial do arquivo"
	 *	}
	 *
	 * @apiSuccess (Sucesso - 201) {ArquivoCriado} arquivo Objeto de retorno da criação do arquivo
	 * @apiSuccess (Sucesso - 201) {String} arquivo.identificador Identificador do arquivo criado.
	 * 
	 * @apiSuccess (Sucesso Response Header - 201) {header} Location URL de acesso ao recurso criado.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 201 Created
	 *  {
	 *  	"identificador":"123456"
	 *  }
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Path("{unidade}/arquivos")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response adicionarArquivo(@PathParam("unidade") String unidade, Arquivo arquivo) throws RemoteException, Exception{
		String identificador = seiNativeService.adicionarArquivo(Constantes.SEI_BROKER, Operacao.ADICIONAR_ARQUIVO, unidadeResource.consultarCodigo(unidade), arquivo.getNome(), arquivo.getTamanho(), 
				arquivo.getHash(), arquivo.getConteudo());
		
		return Response.created(getResourcePath(identificador)).entity(new ArquivoCriado(identificador)).build();
	}

	/** 
	 * @api {put} /:unidade/arquivos/:arquivo Adicionar conteúdo arquivo
	 * @apiName adicionarConteudoArquivo
	 * @apiGroup Arquivo
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription Adiciona conteúdo a um arquivo criado, o sistema identificará automaticamente quando o conteúdo foi completado validando o tamanho em bytes
	 * e o hash do conteúdo. Quando as condições forem satisfeitas o arquivo será ativado e poderá ser utilizado nas chamadas de inclusão de documento.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} arquivo Identificador do arquivo que receberá o conteúdo.
	 *  
	 * @apiParam (Request Body) {ParteArquivo} parte Objeto representando uma parte do arquivo.
	 * @apiParam (Request Body) {String} parte.arquivo Identificador do arquivo que receberá o conteúdo.
	 * @apiParam (Request Body) {String} parte.conteudo Conteúdo parcial codificado em Base64.
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: https://<host>/sei-broker/service/COSAP/arquivos/123456
	 *
	 *	body:
	 *	{
	 *		"arquivo":"123456",
	 *		"conteudo":"Conteúdo parcial do arquivo"
	 *	}
	 *
	 * @apiSuccess (Sucesso - 200) {ArquivoCriado} arquivo Objeto de retorno da criação do arquivo
	 * @apiSuccess (Sucesso - 200) {String} arquivo.identificador Identificador do arquivo criado.
	 * 
	 * @apiSuccess (Sucesso Response Header - 200) {header} Location URL de acesso ao recurso alterado.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 Created
	 *  {
	 *  	"identificador":"123456"
	 *  }
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@PUT
	@Path("{unidade}/arquivos/{arquivo}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response adicionarConteudoArquivo(@PathParam("unidade") String unidade, @PathParam("arquivo") String arquivo, ParteArquivo parte) throws RemoteException, Exception{
		String indice = seiNativeService.adicionarConteudoArquivo(Constantes.SEI_BROKER, Operacao.ADICIONAR_CONTEUDO_ARQUIVO, unidadeResource.consultarCodigo(unidade), parte.getArquivo(), 
				parte.getConteudo());
		
		return Response.ok(getResourcePath(indice)).entity(new ArquivoCriado(indice)).build();
	}
	
	public URI getResourcePath(String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}

}
