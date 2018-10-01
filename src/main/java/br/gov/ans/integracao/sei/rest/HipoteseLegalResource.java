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

    @GET
    @Path("{unidade}/hipoteses-legais")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public HipoteseLegal[] listarHipoteses(@PathParam("unidade") String unidade, @QueryParam("nivelAcesso") NivelAcesso nivelAcesso) throws RemoteException, Exception {
		return seiNativeService.listarHipotesesLegais(Constantes.SEI_BROKER, Operacao.LISTAR_HIPOTESES_LEGAIS, unidadeResource.consultarCodigo(unidade), 
				nivelAcesso != null? nivelAcesso.ordinal()+"" : null);
	}
    
}
