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

import br.gov.ans.integracao.sei.client.Estado;
import br.gov.ans.integracao.sei.client.Pais;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.dao.CidadeDAO;
import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.exceptions.ResourceNotFoundException;
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

    @GET
    @Path("{unidade}/paises")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Pais[] getPaises(@PathParam("unidade") String unidade) throws RemoteException, Exception{
    	return seiNativeService.listarPaises(Constantes.SIGLA_SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, unidadeResource.consultarCodigo(unidade));
    }

    @GET
	@Path("{unidade}/estados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Estado[] getEstados(@PathParam("unidade") String unidade, @QueryParam("pais") String pais) throws RemoteException, Exception{
		if(StringUtils.isBlank(pais)){
			pais = Constantes.CODIGO_BRASIL;
		}
		
		return seiNativeService.listarEstados(Constantes.SIGLA_SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, unidadeResource.consultarCodigo(unidade), pais);
	}

    @GET
	@Path("{unidade}/estados/{uf}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Estado getEstado(@PathParam("unidade") String unidade, @PathParam("uf") String uf, @QueryParam("pais") String pais) throws RemoteException, Exception{
		if(StringUtils.isBlank(pais)){
			pais = Constantes.CODIGO_BRASIL;
		}
		
		Estado[] estados = seiNativeService.listarEstados(Constantes.SIGLA_SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, unidadeResource.consultarCodigo(unidade), pais);
		int indexOf = ArrayUtils.indexOf(estados, new Estado(uf.toUpperCase()));
				
		if(indexOf < 0){
			throw new BusinessException(messages.getMessage("erro.estado.nao.encontrado", uf));
		}
		
		return estados[indexOf];
	}	

	@GET
	@Path("{unidade}/estados/{uf}/cidades")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<br.gov.ans.integracao.sei.modelo.Cidade> getCidades(@PathParam("unidade") String unidade,@PathParam("uf") String uf, @QueryParam("filtro") String filtro) throws RemoteException,BusinessException,Exception{
		return cidadeDAO.getCidades(uf, filtro);
	}

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

