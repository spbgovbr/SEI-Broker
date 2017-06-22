package br.gov.ans.integracao.sei.rest;

import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.client.HipoteseLegal;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.NivelAcesso;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/")
public class HipoteseLegalResource {

	@Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;

	/**
	 * @api {get} /:unidade/hipoteses-legais Listar hipóteses legais
	 * @apiName listarHipoteses
	 * @apiGroup Hipotese Legal
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Lista as hipóteses legais.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiParam (Query Parameters) {String} [nivelAcesso] Filtra hipóteses pelo nível de acesso associado (1 - restrito, 2 - sigiloso)
	 * 
	 * @apiExample Exemplo de requisição:
	 *	curl -i https://<host>/sei-broker/service/COSAP/hipoteses-legais
	 *
	 * @apiSuccess (Sucesso - 200) {HipoteseLegal[]} resultado Lista com as hipóteses legais encontrados
	 * @apiSuccess (Sucesso - 200) {String} resultado.idHipoteseLegal Identificador da hipótese legal no SEI
	 * @apiSuccess (Sucesso - 200) {String} resultado.nome Nome da hipótese legal
	 * @apiSuccess (Sucesso - 200) {String} resultado.baseLegal Descrição da base legal
	 * @apiSuccess (Sucesso - 200) {String} resultado.nivelAcesso Nivel de acesso associado a hipótese legal
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
    @GET
    @Path("{unidade}/hipoteses-legais")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public HipoteseLegal[] listarHipoteses(@PathParam("unidade") String unidade, @QueryParam("nivelAcesso") NivelAcesso nivelAcesso) throws RemoteException, Exception {
		return seiNativeService.listarHipotesesLegais(Constantes.SEI_BROKER, Operacao.LISTAR_HIPOTESES_LEGAIS, unidadeResource.consultarCodigo(unidade), 
				nivelAcesso != null? nivelAcesso.ordinal()+"" : null);
	}
    
}
