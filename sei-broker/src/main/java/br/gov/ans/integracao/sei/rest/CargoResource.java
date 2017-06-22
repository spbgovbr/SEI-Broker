package br.gov.ans.integracao.sei.rest;

import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.client.Cargo;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/")
public class CargoResource {

	@Inject
	private SeiPortTypeProxy seiNativeService;

	@Inject
	private UnidadeResource unidadeResource;

	/**
	 * @api {get} /:unidade/cargos Listar cargos
	 * @apiName listarCargos
	 * @apiGroup Cargo
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Lista os cargos.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiParam (Query Parameters) {String} [idCargo] Identificador do cargo
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/sei-broker/service/COSAP/cargos
	 *
	 * @apiSuccess (Sucesso - 200) {Cargo[]} resultado Lista com os cargos encontrados
	 * @apiSuccess (Sucesso - 200) {String} resultado.idCargo Identificador do cargo no SEI
	 * @apiSuccess (Sucesso - 200) {String} resultado.expressaoCargo Descrição do cargo (Ex.: Governador)
	 * @apiSuccess (Sucesso - 200) {String} resultado.expressaoTratamento Tratamento para o cargo (Ex.: A Sua Excelência o Senhor)
	 * @apiSuccess (Sucesso - 200) {String} resultado.expressaoVocativo Vocativo para o cargo (Ex.: Senhor Governador)
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
    @GET
    @Path("{unidade}/cargos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Cargo[] listarCargos(@PathParam("unidade") String unidade, @QueryParam("idCargo") String idCargo) throws RemoteException, Exception {
		return seiNativeService.listarCargos(Constantes.SEI_BROKER, Operacao.LISTAR_HIPOTESES_LEGAIS, unidadeResource.consultarCodigo(unidade), idCargo);
	}
}
