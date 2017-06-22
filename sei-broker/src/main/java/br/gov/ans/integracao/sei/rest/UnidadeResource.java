package br.gov.ans.integracao.sei.rest;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.ArrayUtils;
import org.jboss.logging.Logger;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;

@Path("/unidades")
@Stateless
public class UnidadeResource {

	@Inject
	private Logger logger;
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
    
    @Inject
    private MessageUtils messages;
	
	private static HashMap<String,String> unidades;
	
	/**
	 * @api {get} /unidades Listar unidades
	 * @apiName listarUnidades
	 * @apiGroup Unidade
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Retorna as Unidades cadastradas no SEI.
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/unidades/
	 * 
	 * @apiSuccess (Sucesso - 200) {Unidade[]} unidades Lista de unidades
	 * @apiSuccess (Sucesso - 200) {String} unidades.idUnidade Identificador da unidade
	 * @apiSuccess (Sucesso - 200) {String} unidades.sigla Sigla da unidade
	 * @apiSuccess (Sucesso - 200) {String} unidades.descricao Descrição da unidade
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
			logger.info(messages.getMessage("carregando.unidades"));

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
	 * @apiVersion 2.0.0
	 *
	 * @apiDescription Retorna o código da Unidade pesquisada.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade que deseja consultar o código
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/unidades/COSAP/codigo
	 *
	 * @apiSuccess (Sucesso - 200) {String} codigo Código da unidade.
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
		logger.debug(messages.getMessage("consultando.unidade",chave));

		if(!getUnidades().containsKey(chave.toUpperCase())){
			throw new BusinessException(messages.getMessage("erro.unidade.nao.encontrada", chave));
		}
		
		return getUnidades().get(chave.toUpperCase());
	}
	
	public String[] buscarCodigoUnidades(String[] unidades) throws Exception{
		if(ArrayUtils.isNotEmpty(unidades)){
			ArrayList<String> codigos = new ArrayList<String>();
			
			for(String unidade: unidades){
				if(isInteger(unidade)){
					codigos.add(unidade);
				}else{
					codigos.add(consultarCodigo(unidade));					
				}
			}
			
			return codigos.toArray(new String[codigos.size()]);
		}
		
		return unidades;
	}
	
	public boolean isInteger(String valor){		
		try{
			Integer.parseInt(valor);
			return true;
		}catch(Exception e){
			return false;			
		}	
	}
}
