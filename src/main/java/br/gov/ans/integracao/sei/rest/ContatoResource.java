package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.parseInt;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;

import java.net.URI;
import java.rmi.RemoteException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.ArrayUtils;

import br.gov.ans.integracao.sei.client.Contato;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sei.exceptions.WrappedException;
import br.gov.ans.integracao.sei.helper.ContatoHelper;
import br.gov.ans.integracao.sei.helper.PessoaHelper;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.modelo.Pessoa;
import br.gov.ans.integracao.sei.modelo.enums.Acao;
import br.gov.ans.integracao.sei.modelo.enums.TipoContato;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.integracao.sei.utils.MessagesKeys;
import br.gov.ans.utils.MessageUtils;

@Path("/")
public class ContatoResource {
	@Inject
	private SeiPortTypeProxy seiNativeService;

	@Inject
	private UnidadeResource unidadeResource;
	
	@Inject
	private MessageUtils messages;
	
	@Inject
	private ContatoHelper contatoHelper;
	
	@Inject
	private PessoaHelper pessoaHelper;

	@Context
	private UriInfo uriInfo;
		
	@PersistenceContext(unitName = "sei_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@GET
	@Path("{unidade}/contatos/tipos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public TipoContato[] listarTipos(@PathParam("unidade") String unidade){
		return TipoContato.values();		 
	}

    @GET
    @Path("{unidade}/contatos/{tipo}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Pessoa> listarContatos(@PathParam("unidade") String unidade, @PathParam("tipo") TipoContato tipo, @QueryParam("nome") String nome, @QueryParam("cpf") String cpf, 
			@QueryParam("cnpj") String cnpj, @QueryParam("sigla") String sigla, @QueryParam("matricula") String matricula, @QueryParam("qtdRegistros") String qtdRegistros, 
			@QueryParam("pagina") String pagina) throws RemoteException, BusinessException, Exception{
		
    	Contato[] contatos = seiNativeService.listarContatos(Constantes.SEI_BROKER, Operacao.LISTAR_CONTATOS, unidadeResource.consultarCodigo(unidade), tipo.getCodigo(), 
				qtdRegistros == null? null : parseInt(qtdRegistros)+"", pagina == null? null:parseInt(pagina)+"", sigla, nome, cpf, cnpj, matricula, null);
    	
    	List<Pessoa> pessoas = pessoaHelper.buildPessoa(contatos);
    	
    	return pessoas;
	}

    @GET
    @Path("{unidade}/contatos/{tipo}/{sigla}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Pessoa getContato(@PathParam("unidade") String unidade, @PathParam("tipo") TipoContato tipo, @PathParam("sigla") String sigla) throws RemoteException, Exception{
    	Contato[] contatos = seiNativeService.listarContatos(Constantes.SEI_BROKER, Operacao.LISTAR_CONTATOS, unidadeResource.consultarCodigo(unidade), tipo.getCodigo(), 
				null, null, sigla, null, null, null, null, null);
    	
    	if(ArrayUtils.isEmpty(contatos)){
    		throw new ResourceNotFoundException(messages.getMessage(MessagesKeys.ERRO_CONTATO_NAO_ENCONTRADO, sigla));
    	}
    	
    	return pessoaHelper.buildPessoa(contatos[0]);
    }

    @POST
    @Path("{unidade}/contatos/{tipo}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response criarContato(@PathParam("unidade") String unidade, @PathParam("tipo") TipoContato tipo, Pessoa pessoa) throws RemoteException, Exception{
    	Contato contato = contatoHelper.buildContato(pessoa);
    	
    	boolean feito = modificarContatoSEI(unidade, contato, tipo, Acao.ALTERAR_INCLUIR);
    	    	
		if(feito){
			return Response.created(getResourcePath(contato.getSigla())).build();
		}else{
			return Response.status(Status.BAD_REQUEST).build();
		}		
    }
 
    @PUT
    @Path("{unidade}/contatos/{tipo}/{sigla}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response atualizarContato(@PathParam("unidade") String unidade, @PathParam("tipo") TipoContato tipo, @PathParam("sigla") String sigla, Pessoa pessoa) throws RemoteException, Exception{
    	Contato contato = contatoHelper.buildContato(pessoa);
    	
    	contatoHelper.preencherIdContato(contato, sigla);
    	
    	boolean feito = modificarContatoSEI(unidade, contato, tipo, Acao.ALTERAR_INCLUIR);
    	
    	if(feito){
    		return Response.ok().build();
    	}else{
    		return Response.status(Status.BAD_REQUEST).build();
    	}
    }
    
    private boolean modificarContatoSEI(String unidade, Contato contato, TipoContato tipo, Acao acao) throws RemoteException, Exception{
    	String resultado = "false";
    	
    	contato.setStaOperacao(acao.getCodigoAcao());
    	contato.setIdTipoContato(tipo.getCodigo());
    	    	
    	Contato[] contatos = {contato};
    	
    	try{
    		resultado = seiNativeService.atualizarContatos(Constantes.SEI_BROKER, Operacao.ATUALIZAR_CONTATOS, unidadeResource.consultarCodigo(unidade), contatos);    		
    	}catch(Exception ex){
    		throw new WrappedException(ex);
    	}
    	
    	return trueOrFalse(resultado);
    }
     
	public URI getResourcePath(String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}
}
