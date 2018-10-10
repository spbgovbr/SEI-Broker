package br.gov.ans.integracao.sei.rest;

import java.net.URI;
import java.rmi.RemoteException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.modelo.Arquivo;
import br.gov.ans.integracao.sei.modelo.ArquivoCriado;
import br.gov.ans.integracao.sei.modelo.ParteArquivo;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;

@Path("/")
public class ArquivoResource {
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
    
    @Inject
    private MessageUtils messages;
    
    @Inject
    private UnidadeResource unidadeResource;
	
	@Context
	private UriInfo uriInfo;

	@POST
	@Path("{unidade}/arquivos")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response adicionarArquivo(@PathParam("unidade") String unidade, Arquivo arquivo) throws RemoteException, Exception{
		validarTamanhoArquivo(arquivo);
		
		String identificador = seiNativeService.adicionarArquivo(Constantes.SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, 
				unidadeResource.consultarCodigo(unidade), arquivo.getNome(), arquivo.getTamanho(), arquivo.getHash(), arquivo.getConteudo());
		
		return Response.created(getResourcePath(identificador)).entity(new ArquivoCriado(identificador)).build();
	}

	@PUT
	@Path("{unidade}/arquivos/{arquivo}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response adicionarConteudoArquivo(@PathParam("unidade") String unidade, @PathParam("arquivo") String arquivo, ParteArquivo parte) throws RemoteException, Exception{
		validarTamanhoParteArquivo(parte);
		
		String indice = seiNativeService.adicionarConteudoArquivo(Constantes.SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, unidadeResource.consultarCodigo(unidade), parte.getArquivo(), 
				parte.getConteudo());
		
		return Response.ok(getResourcePath(indice)).entity(new ArquivoCriado(indice)).build();
	}
	
	public URI getResourcePath(String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}

	public void validarTamanhoArquivo(Arquivo arquivo) throws BusinessException{
		if(arquivo == null || arquivo.getConteudo() == null){
			return;
		}
			
		if(calcularBytes(arquivo.getConteudo().length()) > Constantes.TAMANHO_MAXIMO_ARQUIVO){
			throw new BusinessException(messages.getMessage("erro.tamanho.arquivo"));
		}
	}
	
	public void validarTamanhoParteArquivo(ParteArquivo arquivo) throws BusinessException{
		if(arquivo == null || arquivo.getConteudo() == null){
			return;
		}
			
		if(calcularBytes(arquivo.getConteudo().length()) > Constantes.TAMANHO_MAXIMO_ARQUIVO){
			throw new BusinessException(messages.getMessage("erro.tamanho.parte.arquivo"));
		}
	}
	
	private double calcularBytes(int sizeBase64){		
		return sizeBase64 * 3.0 / 4;
	}
}
