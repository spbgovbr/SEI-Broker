package br.gov.ans.integracao.sei.rest;

import java.rmi.RemoteException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sei.client.Estado;
import br.gov.ans.integracao.sei.client.Pais;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.dao.CidadeDAO;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;

@Path("/")
public class InfraResource {
	
	@Inject
	private SeiPortTypeProxy seiNativeService;
	
	@Inject
	private UnidadeResource unidadeResource;
	
    @Inject
    private MessageUtils messages;
    
    @Inject
    private CidadeDAO cidadeDAO;
	
	/**
	 * @api {get} /:unidade/paises Listar países
	 * @apiName getPaises
	 * @apiGroup Endereco
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta países cadastrados.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/sei-broker/service/cosap/paises
	 *
	 * @apiSuccess (Sucesso - 200) {List} resultado Lista com os países.
	 * @apiSuccess (Sucesso - 200) {Pais} resultado.pais Objeto representando o país.
	 * @apiSuccess (Sucesso - 200) {String} resultado.pais.idPais Identificador do país no SEI.
	 * @apiSuccess (Sucesso - 200) {String} resultado.pais.nome Nome do país.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "idPais": "4",
	 *       "nome": "Afeganistão"
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
    @GET
    @Path("{unidade}/paises")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Pais[] getPaises(@PathParam("unidade") String unidade) throws RemoteException, Exception{
    	return seiNativeService.listarPaises(Constantes.SEI_BROKER, Operacao.LISTAR_ESTADOS, unidadeResource.consultarCodigo(unidade));
    }

	/**
	 * @api {get} /:unidade/estados Listar estados
	 * @apiName getEstados
	 * @apiGroup Endereco
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta estados cadastrados.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/sei-broker/service/cosap/estados
	 *
	 * @apiSuccess (Sucesso - 200) {List} resultado Lista com os estados.
	 * @apiSuccess (Sucesso - 200) {Estado} resultado.estado Objeto representando um estado.
	 * @apiSuccess (Sucesso - 200) {String} resultado.estado.idEstado Identificador do estado no SEI.
	 * @apiSuccess (Sucesso - 200) {String} resultado.estado.idPais Identificador do país no SEI.
	 * @apiSuccess (Sucesso - 200) {String} resultado.estado.sigla Sigla do estado.
	 * @apiSuccess (Sucesso - 200) {String} resultado.estado.nome Nome do estado.
	 * @apiSuccess (Sucesso - 200) {String} resultado.estado.codigoIbge Código do IBGE.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *     	 "idEstado": "2",
	 *       "idPais": "76",
	 *       "sigla": "AC",
	 *       "nome": "Acre",
	 *       "codigoIbge": "12"
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
    @GET
	@Path("{unidade}/estados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Estado[] getEstados(@PathParam("unidade") String unidade, @QueryParam("pais") String pais) throws RemoteException, Exception{
		if(StringUtils.isBlank(pais)){
			pais = Constantes.CODIGO_BRASIL;
		}
		
		return seiNativeService.listarEstados(Constantes.SEI_BROKER, Operacao.LISTAR_ESTADOS, unidadeResource.consultarCodigo(unidade), pais);
	}
    
	/**
	 * @api {get} /:unidade/estados Consultar estado
	 * @apiName getEstado
	 * @apiGroup Endereco
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta o estado pela sigla.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} sigla Sigla do estado
	 *
	 * @apiParam (Query Parameters) {String} [pais="76 (Brasil)"] Identificador do pais que deseja listar os estados.
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/sei-broker/service/cosap/estados/AC
	 * 
	 * @apiSuccess (Sucesso - 200) {Estado} estado Objeto representando um estado.
	 * @apiSuccess (Sucesso - 200) {String} estado.idEstado Identificador do estado no SEI.
	 * @apiSuccess (Sucesso - 200) {String} estado.idPais Identificador do país no SEI.
	 * @apiSuccess (Sucesso - 200) {String} estado.sigla Sigla do estado.
	 * @apiSuccess (Sucesso - 200) {String} estado.nome Nome do estado.
	 * @apiSuccess (Sucesso - 200) {String} estado.codigoIbge Código do IBGE.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *     	 "idEstado": "2",
	 *       "idPais": "76",
	 *       "sigla": "AC",
	 *       "nome": "Acre",
	 *       "codigoIbge": "12"
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
    @GET
	@Path("{unidade}/estados/{uf}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Estado getEstado(@PathParam("unidade") String unidade, @PathParam("uf") String uf, @QueryParam("pais") String pais) throws RemoteException, Exception{
		if(StringUtils.isBlank(pais)){
			pais = Constantes.CODIGO_BRASIL;
		}
		
		Estado[] estados = seiNativeService.listarEstados(Constantes.SEI_BROKER, Operacao.LISTAR_ESTADOS, unidadeResource.consultarCodigo(unidade), pais);
		int indexOf = ArrayUtils.indexOf(estados, new Estado(uf.toUpperCase()));
				
		if(indexOf < 0){
			throw new BusinessException(messages.getMessage("erro.estado.nao.encontrado", uf));
		}
		
		return estados[indexOf];
	}	
	
	/**
	 * @api {get} /:unidade/estados/:estado/cidades Listar cidades
	 * @apiName getCidades
	 * @apiGroup Endereco
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta as cidades de um determinado estado.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} estado Sigla do estado.
	 *
	 * @apiParam (Query Parameters) {String} [filtro] String utilizada para filtrar as cidades.
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/sei-broker/service/cosap/estados/rj/cidades?filtro=paraiba
	 * 
	 * @apiSuccess (Sucesso - 200) {List} resultado Lista com os cidades encontradas.
	 * @apiSuccess (Sucesso - 200) {Cidade} resultado.cidade Objeto representando uma cidade.
	 * @apiSuccess (Sucesso - 200) {String} resultado.cidade.nome Nome da cidade.
	 * @apiSuccess (Sucesso - 200) {String} resultado.cidade.codigoIbge Código do IBGE.
	 * @apiSuccess (Sucesso - 200) {Estado} resultado.cidade.estado Objeto representando um estado.
	 * @apiSuccess (Sucesso - 200) {String} resultado.cidade.estado.nome Nome do estado.
	 * @apiSuccess (Sucesso - 200) {String} resultado.cidade.estado.sigla Sigla do estado.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *     	 "estado": {
	 *       	"sigla": "RJ",
	 *       	"nome": "Rio de Janeiro"
	 *       },
	 *       "nome": "Paraíba do Sul",
	 *       "codigoIbge": "3303708"
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/estados/{uf}/cidades")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<br.gov.ans.integracao.sei.modelo.Cidade> getCidades(@PathParam("unidade") String unidade,@PathParam("uf") String uf, @QueryParam("filtro") String filtro) throws RemoteException,BusinessException,Exception{
		return cidadeDAO.getCidades(uf, filtro);
	}
	
	/**
	 * @api {get} /:unidade/estados/:estado/cidades/:cidade Consultar cidade
	 * @apiName getCidade
	 * @apiGroup Endereco
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta a cidade pelo código do IBGE.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} estado Sigla do estado.
	 * @apiParam (Path Parameters) {String} cidade Código do IBGE.
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/sei-broker/service/cosap/estados/rj/cidades/3303708
	 * 
	 * @apiSuccess (Sucesso - 200) {Cidade} cidade Objeto representando uma cidade.
	 * @apiSuccess (Sucesso - 200) {String} cidade.nome Nome da cidade.
	 * @apiSuccess (Sucesso - 200) {String} cidade.codigoIbge Código do IBGE.
	 * @apiSuccess (Sucesso - 200) {Estado} cidade.estado Objeto representando um estado.
	 * @apiSuccess (Sucesso - 200) {String} cidade.estado.nome Nome do estado.
	 * @apiSuccess (Sucesso - 200) {String} cidade.estado.sigla Sigla do estado.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *     	 "estado": {
	 *       	"sigla": "RJ",
	 *       	"nome": "Rio de Janeiro"
	 *       },
	 *       "nome": "Paraíba do Sul",
	 *       "codigoIbge": "3303708"
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/estados/{uf}/cidades/{ibge}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public br.gov.ans.integracao.sei.modelo.Cidade getCidade(@PathParam("unidade") String unidade, @PathParam("uf") String uf, @PathParam("ibge") String ibge) throws RemoteException,
			BusinessException, Exception {		
		
		try{
			return cidadeDAO.getCidadePeloIbge(ibge);
		}catch(NoResultException ex){
			throw new ResourceNotFoundException(messages.getMessage("erro.cidade.nao.encontrada.ibge"));
		}
	}
	
	public String getIdEstado(String unidade, String uf, String pais) throws RemoteException, BusinessException, Exception{
		if(pais.equals(Constantes.CODIGO_BRASIL)){
			Estado estado = getEstado(unidade, uf, pais);
			
			return estado.getIdEstado();
		}
		
		return uf;
	}
	
}

