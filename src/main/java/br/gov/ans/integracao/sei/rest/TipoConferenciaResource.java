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

    @GET
    @Path("{unidade}/tipos-conferencia")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public TipoConferencia[] listarTiposConferencia(@PathParam("unidade") String unidade) throws RemoteException, Exception{
		return seiNativeService.listarTiposConferencia(Constantes.SEI_BROKER, Operacao.LISTAR_MARCADORES_UNIDADE, 
				unidadeResource.consultarCodigo(unidade));
	}
}
