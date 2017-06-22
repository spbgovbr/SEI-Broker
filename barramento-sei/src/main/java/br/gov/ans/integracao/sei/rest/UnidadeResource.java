package br.gov.ans.integracao.sei.rest;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/unidades")
@Stateless
public class UnidadeResource {

	@Inject
	private Logger logger;
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
	
	private static HashMap<String,String> unidades;
	
	/**
	 * @api {get} /unidades Listar unidades
	 * @apiName listarUnidades
	 * @apiGroup Unidade
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza uma consulta aos usuários cadastrados que possuem o perfil "Básico".
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://anshmjboss01/sei-broker/service/unidades/
	 * @apiSuccess {Unidade[]} unidades Lista de unidades
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public br.gov.ans.integracao.sei.client.Unidade[] listarUnidades() throws RemoteException{
		return seiNativeService.listarUnidades(Constantes.SEI_BROKER, Operacao.LISTAR_UNIDADES, null, null);
	}
		
	public HashMap<String,String> getUnidades() throws RemoteException {
		if(unidades == null){
			logger.debug("Carregando unidades cadastradas no SEI.");

			br.gov.ans.integracao.sei.client.Unidade[] lista = listarUnidades();
			
			unidades = new HashMap<String,String>();
						
			for(br.gov.ans.integracao.sei.client.Unidade u : lista){
				unidades.put(u.getSigla(), u.getIdUnidade());
			}
		}
		
		return unidades;
	}
	
	/**
	 * @api {get} /unidades/{unidade}/codigo Consultar código
	 * @apiName consultarCodigo
	 * @apiGroup Unidade
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método retorna o código da Unidade pesquisada.
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://anshmjboss01/sei-broker/service/unidades/COSAP/codigo
	 *
	 * @apiSuccess {String} codigo Código da unidade.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/{chave}/codigo")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String consultarCodigo(@PathParam("chave") String chave) throws Exception{
		logger.debug("Consultando código da unidade " + chave + " no SEI.");

		if(!getUnidades().containsKey(chave.toUpperCase())){
			throw new BusinessException("Unidade " + chave + " não encontrada.");
		}
		
		return getUnidades().get(chave.toUpperCase());
	}
	
}
