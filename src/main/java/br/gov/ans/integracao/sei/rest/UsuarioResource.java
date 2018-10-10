package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.formatarNumeroProcesso;
import static br.gov.ans.integracao.sei.utils.Util.getSOuN;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.AtribuicaoProcesso;
import br.gov.ans.integracao.sei.modelo.Usuario;
import br.gov.ans.integracao.sei.modelo.enums.Acao;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.integracao.sip.client.SIPSoapClient;
import br.gov.ans.utils.MessageUtils;

@Path("/")
public class UsuarioResource {

	@Inject
	private SIPSoapClient sipClient;
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
    
    @Inject
    private MessageUtils messages;
   	
	@GET
	@Path("/{unidade}/usuarios")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public br.gov.ans.integracao.sei.client.Usuario[] listarUsuarios(@PathParam("unidade") String unidade, @QueryParam("usuario") String usuario) throws RemoteException, Exception{
		return seiNativeService.listarUsuarios(Constantes.SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, unidadeResource.consultarCodigo(unidade), usuario);
	}

	@GET	
	@Path("/{unidade}/usuarios/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public br.gov.ans.integracao.sei.client.Usuario buscarUsuario(@PathParam("unidade") String unidade, @PathParam("usuario") String usuario) throws Exception{		
		return getUsuario(usuario, unidade);
	}

	@POST
	@Path("/{unidade}/usuarios/{usuario}/processos")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String atribuirProcesso(@PathParam("unidade") String unidade, 
			@PathParam("usuario") String usuario, AtribuicaoProcesso processo) throws Exception{
		String resultado = seiNativeService.atribuirProcesso(Constantes.SEI_BROKER, Constantes.CHAVE_IDENTIFICACAO, unidadeResource.consultarCodigo(unidade), 
				formatarNumeroProcesso(processo.getProcesso()), getUsuario(usuario, unidade).getIdUsuario(), getSOuN(processo.isReabrir()));
	
		return trueOrFalse(resultado) + "";
	}

	@POST	
	@Path("/usuarios")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean incluirUsuario(Usuario usuario) throws Exception{
		return manterUsuario(Acao.ALTERAR_INCLUIR, usuario);
	}

	@DELETE
	@Path("/usuarios/{login}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean excluirUsuario(@PathParam("login") String login,Usuario usuario) throws Exception{
		return manterUsuario(Acao.EXCLUIR, usuario);
	}

	@DELETE
	@Path("/usuarios/ativos/{login}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean desativarUsuario(@PathParam("login") String login,Usuario usuario) throws Exception{
		return manterUsuario(Acao.DESATIVAR, usuario);
	}

	@POST
	@Path("/usuarios/ativos")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean ativarUsuario(Usuario usuario) throws Exception{
		return manterUsuario(Acao.REATIVAR, usuario);
	}
		
	public Boolean manterUsuario(Acao acao, Usuario usuario) throws Exception{
		return sipClient.replicarUsuario(acao.getCodigoAcao(), usuario.getCodigo(), Constantes.CODIGO_ORGAO_ANS, usuario.getLogin(), usuario.getNome());		
	}
		
	public br.gov.ans.integracao.sei.client.Usuario getUsuario(String loginUsuario, String unidade) throws NotFoundException,Exception{
		br.gov.ans.integracao.sei.client.Usuario usuario = new br.gov.ans.integracao.sei.client.Usuario();
		usuario.setSigla(loginUsuario);
		
		List<br.gov.ans.integracao.sei.client.Usuario> usuarios = Arrays.asList(listarUsuarios(unidade, null));
		
		int index = usuarios.indexOf(usuario);
		
		if(index < 0){
			throw new NotFoundException(messages.getMessage("erro.usuario.nao.encontrado", loginUsuario));
		}
		
		return usuarios.get(index);
	}
	
}
