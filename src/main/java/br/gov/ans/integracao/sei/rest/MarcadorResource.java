package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.formatarNumeroProcesso;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;

import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.gov.ans.integracao.sei.client.DefinicaoMarcador;
import br.gov.ans.integracao.sei.client.Marcador;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.MarcacaoProcesso;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;

@Path("/")
public class MarcadorResource {

	@Inject
	private SeiPortTypeProxy seiNativeService;

	@Inject
	private UnidadeResource unidadeResource;
	
	@Inject
	private MessageUtils messages;
	
	/**
	 * @api {get} /:unidade/marcadores Listar marcadores
	 * @apiName listarMarcadores
	 * @apiGroup Marcador
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Lista os marcadores de uma unidade.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/cosap/marcadores
	 * 
	 * @apiSuccess (Sucesso - 200) {Marcador[]} marcadores Lista de marcadores
	 * @apiSuccess (Sucesso - 200) {String} marcadores.id Identificador do marcador.
	 * @apiSuccess (Sucesso - 200) {String} marcadores.nome Nome do marcador.
	 * @apiSuccess (Sucesso - 200) {String} marcadores.icone Ícone do marcador em formato PNG codificado em Base64.
	 * @apiSuccess (Sucesso - 200) {String} marcadores.sinAtivo S/N - Sinalizador indica se o marcador está ativo.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/marcadores")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Marcador[] listarMarcadores(@PathParam("unidade") String unidade) throws RemoteException, Exception{
		return seiNativeService.listarMarcadoresUnidade(Constantes.SEI_BROKER, Operacao.LISTAR_MARCADORES_UNIDADE, unidadeResource.consultarCodigo(unidade));
	}
	
	/**
	 * @api {post} /:unidade/marcadores/:identificador/processos Adicionar Processo
	 * @apiName marcarProcesso
	 * @apiGroup Marcador
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Adiciona um processo ao marcador.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} identificador Identificador do marcador no SEI.
	 *
	 * @apiParam (Request Body) {MarcacaoProcesso} marcacaoProcesso Objeto de com as definições da marcação.
	 * @apiParam (Request Body) {String} marcacaoProcesso.processo Número do processo no SEI.
	 * @apiParam (Request Body) {String} marcacaoProcesso.texto Texto para associação.
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/cosap/marcadores/3/processos
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 201 Created
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@POST
	@Path("{unidade}/marcadores/{marcador}/processos")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response marcarProcesso(@PathParam("unidade") String unidade, @PathParam("marcador") String marcador, @QueryParam("auto-formatacao") String autoFormatar, 
			MarcacaoProcesso marcacao) throws RemoteException, Exception{
		if(isAutoFormatar(autoFormatar)){			
			marcacao.setProcesso(formatarNumeroProcesso(marcacao.getProcesso()));
		}
				
		DefinicaoMarcador definicaoMarcador = new DefinicaoMarcador();
		definicaoMarcador.setProtocoloProcedimento(marcacao.getProcesso());
		definicaoMarcador.setTexto(marcacao.getTexto());
		definicaoMarcador.setIdMarcador(marcador);
		
		String retorno = seiNativeService.definirMarcador(Constantes.SEI_BROKER, Operacao.DEFINIR_MARCADOR, unidadeResource.consultarCodigo(unidade), new DefinicaoMarcador[]{definicaoMarcador});
		
		if(trueOrFalse(retorno)){
			return Response.status(Status.CREATED).build();
		}else{
			throw new Exception(messages.getMessage("erro.marcar.processo"));
		}
	}
	
	public boolean isAutoFormatar(String valor){
		return !("N".equals(valor) || "n".equals(valor));
	}
}
