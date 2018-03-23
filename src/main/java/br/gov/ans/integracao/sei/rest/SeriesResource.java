package br.gov.ans.integracao.sei.rest;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.client.Serie;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.modelo.TipoDocumento;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;

@Path("/")
public class SeriesResource {

    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
    
    @Inject
    private MessageUtils messages;
	  
	/**
	 * @api {get} /:unidade/series Listar séries
	 * @apiName listarSeries
	 * @apiGroup Serie
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Este método realiza uma consulta às séries.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * 
	 * @apiParam (Query Parameters) {String} [filtro] Para filtrar por series que contenham o trecho no nome.
	 * @apiParam (Query Parameters) {String} [tipo-processo=null] Para filtrar por determinado tipo de processo.
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/COSAP/series
	 *
	 * @apiSuccess (Sucesso - 200) {Serie[]} series Lista de séries.
	 * @apiSuccess (Sucesso - 200) {String} series.idSerie Identificador do tipo de documento
	 * @apiSuccess (Sucesso - 200) {String} series.nome Nome do tipo de documento
	 * @apiSuccess (Sucesso - 200) {String} series.aplicabilidade T = Documentos internos e externos, I = documentos internos, E = documentos externos e F = formulários
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/series")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Serie[] listarSeries(@PathParam("unidade") String unidade, @QueryParam("tipo-processo") String tipoProcesso, @QueryParam("filtro") String filtro) throws Exception{
		Serie[] series = seiNativeService.listarSeries(Constantes.SEI_BROKER, Operacao.LISTAR_SERIES, isBlank(unidade)? null : unidadeResource.consultarCodigo(unidade), tipoProcesso);	
		
		if(StringUtils.isNotBlank(filtro)){
			List<Serie> list = new ArrayList<Serie>(Arrays.asList(series));
			
			list.removeIf(serie -> !serie.getNome().toLowerCase().contains(filtro.toLowerCase()));
			
			if(list.isEmpty()){
				throw new NotFoundException(messages.getMessage("erro.series.nao.encontradas"));
			}
			
			return list.toArray(new Serie[list.size()]);
		}
		
		return series;
	}
	
	/**
	 * @api {get} /:unidade/tipos-documentos Listar tipos documentos
	 * @apiName listarTiposDocumentos
	 * @apiGroup Documento
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Lista os tipos de documentos do SEI.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * 
	 * @apiParam (Query Parameters) {String} [filtro] Para filtrar por documentos que contenham o trecho no nome.
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/COSAP/tipos-documentos
	 *
	 * @apiSuccess (Sucesso - 200) {List} tipos Lista com os tipos de documentos
	 * @apiSuccess (Sucesso - 200) {String} tipos.identificador Identificador do tipo de documento
	 * @apiSuccess (Sucesso - 200) {String} tipos.nome Nome do tipo de documento
	 * @apiSuccess (Sucesso - 200) {String} series.aplicabilidade T = Documentos internos e externos, I = documentos internos, E = documentos externos e F = formulários
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/tipos-documentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response listarTiposDocumentos(@PathParam("unidade") String unidade, @QueryParam("filtro") String filtro) throws Exception{
		Serie[] series = listarSeries(unidade, null, filtro);
		
		if(series == null || series.length < 1){
			throw new NotFoundException(messages.getMessage("erro.tipo.documento.nao.encontrado"));
		}
		
		return Response.ok(new GenericEntity<List<TipoDocumento>>(getTiposDocumentos(series)){}).build();
	}
	
	public List<TipoDocumento> getTiposDocumentos(Serie[] series){		
		List<TipoDocumento> tipos = new ArrayList<TipoDocumento>(); 
		
		for(Serie s : series){
			tipos.add(new TipoDocumento(s.getIdSerie(), s.getNome(), s.getAplicabilidade()));
		}
		
		return tipos;
	}

}
