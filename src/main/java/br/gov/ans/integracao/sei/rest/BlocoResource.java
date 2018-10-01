package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.formatarNumeroProcesso;
import static br.gov.ans.integracao.sei.utils.Util.getSOuN;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.integracao.sei.client.RetornoConsultaBloco;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.InclusaoDocumentoBloco;
import br.gov.ans.integracao.sei.modelo.InclusaoProcessoBloco;
import br.gov.ans.integracao.sei.modelo.NovoBloco;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;

@Path("")
public class BlocoResource {
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
    
	@Inject
	private MessageUtils messages;
    
	@Context
	private UriInfo uriInfo;
    
	@GET
	@Path("{unidade}/blocos/{bloco}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public RetornoConsultaBloco consultarBloco(@PathParam("unidade") String unidade, @PathParam("bloco") String bloco, 
			@QueryParam("protocolos") String exibirProtocolos) throws Exception{
		return seiNativeService.consultarBloco(Constantes.SEI_BROKER, Operacao.CONSULTAR_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, getSOuN(exibirProtocolos));
	}
	
	@POST
	@Path("{unidade}/blocos/disponibilizados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String disponibilizarBloco(@PathParam("unidade") String unidade, String bloco) throws Exception{
		String resultado = seiNativeService.disponibilizarBloco(Constantes.SEI_BROKER, Operacao.DISPONIBILIZAR_BLOCO, unidadeResource.consultarCodigo(unidade), bloco);
		
		return trueOrFalse(resultado) + "";
	}
	
	@DELETE
	@Path("{unidade}/blocos/disponibilizados/{bloco}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String cancelarDisponibilizacaoBloco(@PathParam("unidade") String unidade, @PathParam("bloco") String bloco) throws Exception{
		String resultado = seiNativeService.cancelarDisponibilizacaoBloco(Constantes.SEI_BROKER, Operacao.CANCELAR_DISPONIBILIZACAO_BLOCO, 
				unidadeResource.consultarCodigo(unidade), bloco);
				
		return trueOrFalse(resultado) + "";
	}
	
	@DELETE
	@Path("{unidade}/blocos/{bloco}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String excluirBloco(@PathParam("unidade") String unidade, @PathParam("bloco") String bloco) throws Exception{
		String resultado = seiNativeService.excluirBloco(Constantes.SEI_BROKER, Operacao.EXCLUIR_BLOCO, unidadeResource.consultarCodigo(unidade), bloco);
				
		return trueOrFalse(resultado) + "";
	}

	@Path("{unidade}/blocos")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response gerarBloco(@PathParam("unidade") String unidade, NovoBloco bloco) throws Exception{		
		String retorno = seiNativeService.gerarBloco(Constantes.SEI_BROKER, Operacao.GERAR_BLOCO, unidadeResource.consultarCodigo(unidade), bloco.getTipo().getCodigo(), 
				bloco.getDescricao(), unidadeResource.buscarCodigoUnidades(bloco.getUnidades()), bloco.getDocumentos(), getSOuN(bloco.isDisponibilizar()));	
		
		return Response.created(getResourcePath(retorno)).entity(retorno).build();
	}

	@POST
	@Path("{unidade}/blocos/{bloco}/documentos")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String incluirDocumentoNoBloco(@PathParam("unidade") String unidade, 
			@PathParam("bloco") String bloco, String documento) throws Exception{
		String resultado = seiNativeService.incluirDocumentoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_DOCUMENTO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, documento,
				null);

		return trueOrFalse(resultado) + "";
	}

	@POST
	@Path("{unidade}/blocos/{bloco}/documentos")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response incluirDocumentoComAnotacaoNoBloco(@PathParam("unidade") String unidade, 
			@PathParam("bloco") String bloco, InclusaoDocumentoBloco inclusao) throws Exception{
		String resultado = seiNativeService.incluirDocumentoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_DOCUMENTO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, 
				inclusao.getDocumento(), inclusao.getAnotacao());

		if(trueOrFalse(resultado)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.documento.incluir.bloco"));
		}		
	}

	@DELETE
	@Path("{unidade}/blocos/{bloco}/documentos/{documento}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String retirarDocumentoDoBloco(@PathParam("unidade") String unidade, @PathParam("bloco") String bloco, @PathParam("documento") String documento) throws Exception{
		String resultado = seiNativeService.retirarDocumentoBloco(Constantes.SEI_BROKER, Operacao.RETIRAR_DOCUMENTO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, documento);
						
		return trueOrFalse(resultado) + "";
	}

	@POST
	@Path("{unidade}/blocos/{bloco}/processos")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String incluirProcessoNoBloco(@PathParam("unidade") String unidade, 
			@PathParam("bloco") String bloco, @QueryParam("auto-formatacao") String autoFormatar, String processo) throws Exception{
		if(isAutoFormatar(autoFormatar)){			
			processo = formatarNumeroProcesso(processo);
		}
		
		String resultado =  seiNativeService.incluirProcessoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_PROCESSO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, 
				processo, null);
		
		return trueOrFalse(resultado) + "";
	}

	@POST
	@Path("{unidade}/blocos/{bloco}/processos")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response incluirProcessoComAnotacaoNoBloco(@PathParam("unidade") String unidade, @QueryParam("auto-formatacao") String autoFormatar, @PathParam("bloco") String bloco, 
			InclusaoProcessoBloco inclusao) throws Exception{		
		if(StringUtils.isNotBlank(inclusao.getProcesso()) && isAutoFormatar(autoFormatar)){
			String numeroFormatado = formatarNumeroProcesso(inclusao.getProcesso());
			inclusao.setProcesso(numeroFormatado);
		}
		
		String resultado =  seiNativeService.incluirProcessoBloco(Constantes.SEI_BROKER, Operacao.INCLUIR_PROCESSO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, 
				inclusao.getProcesso(), inclusao.getAnotacao());
		
		if(trueOrFalse(resultado)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.processo.incluir.bloco"));
		}
	}

	@DELETE
	@Path("{unidade}/blocos/{bloco}/processos/{processo}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String retirarProcessoDoBloco(@PathParam("unidade") String unidade, @PathParam("processo") String processo, @QueryParam("auto-formatacao") String autoFormatar, 
			@PathParam("bloco") String bloco)  throws Exception{
		if(isAutoFormatar(autoFormatar)){			
			processo = formatarNumeroProcesso(processo);
		}
		
		String resultado = seiNativeService.retirarProcessoBloco(Constantes.SEI_BROKER, Operacao.RETIRAR_PROCESSO_BLOCO, unidadeResource.consultarCodigo(unidade), bloco, processo);
				
		return trueOrFalse(resultado) + "";
	}
	
	public URI getResourcePath(String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}
	
	public boolean isAutoFormatar(String valor){
		return !("N".equals(valor) || "n".equals(valor));
	}
}
