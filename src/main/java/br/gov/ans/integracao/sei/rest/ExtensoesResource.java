package br.gov.ans.integracao.sei.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.client.ArquivoExtensao;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/")
public class ExtensoesResource {

    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;

    @Path("{unidade}/extensoes")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public ArquivoExtensao[] listarExtensoesPermitidas(@PathParam("unidade") String unidade, @QueryParam("extensao") String extensao) throws Exception{
		return seiNativeService.listarExtensoesPermitidas(Constantes.SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, unidadeResource.consultarCodigo(unidade), extensao);		
	}
}
