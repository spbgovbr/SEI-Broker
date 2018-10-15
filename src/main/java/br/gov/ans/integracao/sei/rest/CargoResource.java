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
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/")
public class CargoResource {

	@Inject
	private SeiPortTypeProxy seiNativeService;

	@Inject
	private UnidadeResource unidadeResource;

    @GET
    @Path("{unidade}/cargos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Cargo[] listarCargos(@PathParam("unidade") String unidade, @QueryParam("idCargo") String idCargo) throws RemoteException, Exception {
		return seiNativeService.listarCargos(Constantes.SIGLA_SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, unidadeResource.consultarCodigo(unidade), idCargo);
	}
}
