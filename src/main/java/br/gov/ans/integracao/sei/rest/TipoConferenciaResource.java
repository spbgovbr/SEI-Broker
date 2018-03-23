package br.gov.ans.integracao.sei.rest;

import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.client.TipoConferencia;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/")
public class TipoConferenciaResource {

	@Inject
	private SeiPortTypeProxy seiNativeService;
	
	@Inject
	private UnidadeResource unidadeResource;

	/**
	 * @api {get} /:unidade/tipos-conferencia Listar tipos de conferência
	 * @apiName listarTiposConferencia
	 * @apiGroup Tipos Conferência
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Lista os tipos de conferência.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/cosap/tipos-conferencia
	 * 
	 * @apiSuccess (Sucesso - 200) {TipoConferencia[]} tipos Lista de tipos de conferência.
	 * @apiSuccess (Sucesso - 200) {String} tipos.idTipoConferencia Identificador do tipo de conferência.
	 * @apiSuccess (Sucesso - 200) {String} tipos.descricao Descrição do tipo de conferência.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
    @GET
    @Path("{unidade}/tipos-conferencia")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public TipoConferencia[] listarTiposConferencia(@PathParam("unidade") String unidade) throws RemoteException, Exception{
		return seiNativeService.listarTiposConferencia(Constantes.SEI_BROKER, Operacao.LISTAR_MARCADORES_UNIDADE, 
				unidadeResource.consultarCodigo(unidade));
	}
}
