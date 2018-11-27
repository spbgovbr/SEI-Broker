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

	@GET
	@Path("{unidade}/series")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Serie[] listarSeries(@PathParam("unidade") String unidade, @QueryParam("tipo-processo") String tipoProcesso, @QueryParam("filtro") String filtro, 
			@QueryParam("nome") String nome) throws Exception{
		Serie[] series = seiNativeService.listarSeries(Constantes.SIGLA_SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, isBlank(unidade)? null : unidadeResource.consultarCodigo(unidade), tipoProcesso);	
		
		if(StringUtils.isNotBlank(filtro) || StringUtils.isNotBlank(nome)){
			List<Serie> list = new ArrayList<Serie>(Arrays.asList(series));
			
			if(StringUtils.isNotBlank(nome)){
				list.removeIf(serie -> !serie.getNome().toLowerCase().equals(nome.toLowerCase()));				
			}else{
				list.removeIf(serie -> !serie.getNome().toLowerCase().contains(filtro.toLowerCase()));				
			}		
			
			if(list.isEmpty()){
				throw new NotFoundException(messages.getMessage("erro.series.nao.encontradas"));
			}
			
			return list.toArray(new Serie[list.size()]);
		}
		
		return series;
	}

	@GET
	@Path("{unidade}/tipos-documentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response listarTiposDocumentos(@PathParam("unidade") String unidade, @QueryParam("filtro") String filtro, 
			@QueryParam("nome") String nome) throws Exception{
		Serie[] series = listarSeries(unidade, null, filtro, nome);
		
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
