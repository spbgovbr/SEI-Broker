package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.formatarNumeroProcesso;
import static br.gov.ans.integracao.sei.utils.Util.getSOuN;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.AcaoUsuario;
import br.gov.ans.integracao.sei.modelo.AtribuicaoProcesso;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.modelo.Usuario;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.integracao.sip.client.SIPSoapClient;

@Path("/")
@Stateless
public class UsuarioResource {

	@Inject
	private SIPSoapClient sipClient;
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
   	
	/**
	 * @api {get} /:unidade/usuarios Listar usuários
	 * @apiName listarUsuarios
	 * @apiGroup Usuario
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza uma consulta aos usuários cadastrados que possuem o perfil "Básico".
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} [usuario=null] Id do usuário que deseja recuperar as informações
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://anshmjboss01/sei-broker/service/usuarios/COSAP
	 *
	 * @apiSuccess {Usuario[]} usuarios Lista de usuários
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/{unidade}/usuarios")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public br.gov.ans.integracao.sei.client.Usuario[] listarUsuarios(@PathParam("unidade") String unidade, @QueryParam("usuario") String usuario) throws Exception{
		return seiNativeService.listarUsuarios(Constantes.SEI_BROKER, Operacao.LISTAR_USUARIOS, unidadeResource.consultarCodigo(unidade), usuario);
	}
	
	
	/**
	 * @api {get} /usuarios/:usuario Buscar usuário
	 * @apiName buscarUsuario
	 * @apiGroup Usuario
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza a uma busca pelo login do usuário.
	 * 
	 * @apiParam {String} usuario Login do usuário
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://anshmjboss01/sei-broker/service/usuarios/andre.guimaraes
	 *
	 * @apiSuccess {Usuario} usuario Informações do usuário encontrado.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET	
	@Path("/{unidade}/usuarios/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public br.gov.ans.integracao.sei.client.Usuario buscarUsuario(@PathParam("unidade") String unidade, @PathParam("usuario") String usuario) throws Exception{		
		return getUsuario(usuario, unidade);
	}
	
	/**
	 * @api {post} /:unidade/:usuario/processos Atribuir processo
	 * @apiName atribuirProcesso
	 * @apiGroup Usuario
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método atribui o processo a um usuário.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} processo Numero do processo a ser atribuído
	 * @apiParam {String} usuario Login do usuário a quem deseja atribuir o processo
	 * @apiParam {String} [reabrir-processo=N] S ou N para reabrir o processo
	 *
 	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/usuarios/andre.guimaraes/processos
	 *
	 *	body:
	 *	{
	 *		"processo":"33910000029201653",
	 *		"reabrir":false
	 *	}
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Path("/{unidade}/usuarios/{usuario}/processos")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String atribuirProcesso(@PathParam("unidade") String unidade, 
			@PathParam("usuario") String usuario, AtribuicaoProcesso processo) throws Exception{
		String resultado = seiNativeService.atribuirProcesso(Constantes.SEI_BROKER, Operacao.ATRIBUIR_PROCESSO, unidadeResource.consultarCodigo(unidade), 
				formatarNumeroProcesso(processo.getProcesso()), getUsuario(usuario, unidade).getIdUsuario(), getSOuN(processo.isReabrir()));
	
		return trueOrFalse(resultado) + "";
	}
	
	/**
	 * @api {post} /usuarios/incluir-alterar Incluir ou alterar usuário
	 * @apiName incluirUsuario
	 * @apiGroup Usuario
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza a inclusão de novos usuários ou alterarações nos usuários existentes.
	 *
	 * @apiParam {String} codigo Código que deseja atribuir ao usuário
	 * @apiParam {String} nome Nome do usuário
	 * @apiParam {String} login Login que será atribuído ao usuário
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://anshmjboss01/sei-broker/service/usuarios/incluir-alterar
	 *
	 *	body:
	 *	{
	 *		"codigo":"1234",
	 *		"nome":"André Luís Fernandes Guimarães",
	 *		"login":"andre.guimaraes"
	 *	}
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST	
	@Path("/usuarios/incluir-alterar")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean incluirUsuario(Usuario usuario) throws Exception{
		return manterUsuario(AcaoUsuario.ALTERAR_INCLUIR, usuario);
	}

		
	/**
	 * @api {post} /usuarios/excluir Excluir usuário
	 * @apiName excluirUsuario
	 * @apiGroup Usuario
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza a exclusão de usuários.
	 * 
	 * @apiParam {String} codigo Código que deseja atribuir ao usuário
	 * @apiParam {String} nome Nome do usuário
	 * @apiParam {String} login Login que será atribuído ao usuário
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://anshmjboss01/sei-broker/service/usuarios/excluir
	 *
	 *	body:
	 *	{
	 *		"codigo":"1234",
	 *		"nome":"André Luís Fernandes Guimarães",
	 *		"login":"andre.guimaraes"
	 *	}
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Path("/usuarios/excluir")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean excluirUsuario(Usuario usuario) throws Exception{
		return manterUsuario(AcaoUsuario.EXCLUIR, usuario);
	}
	
	
	/**
	 * @api {put} /usuarios/desativar Desativar usuário
	 * @apiName desativarUsuario
	 * @apiGroup Usuario
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método desativa usuários.
	 *
	 * @apiParam {String} codigo Código que deseja atribuir ao usuário
	 * @apiParam {String} nome Nome do usuário
	 * @apiParam {String} login Login que será atribuído ao usuário
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://anshmjboss01/sei-broker/service/usuarios/desativar
	 *
	 *	body:
	 *	{
	 *		"codigo":"1234",
	 *		"nome":"André Luís Fernandes Guimarães",
	 *		"login":"andre.guimaraes"
	 *	}
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@PUT
	@Path("/usuarios/desativar")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean desativarUsuario(Usuario usuario) throws Exception{
		return manterUsuario(AcaoUsuario.DESATIVAR, usuario);
	}
	
	
	/**
	 * @api {put} /usuarios/ativar Ativar usuário
	 * @apiName ativarUsuario
	 * @apiGroup Usuario
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método reativa usuários.
	 *
	 * @apiParam {String} codigo Código que deseja atribuir ao usuário
	 * @apiParam {String} nome Nome do usuário
	 * @apiParam {String} login Login que será atribuído ao usuário
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://anshmjboss01/sei-broker/service/usuarios/ativar
	 *
	 *	body:
	 *	{
	 *		"codigo":"1234",
	 *		"nome":"André Luís Fernandes Guimarães",
	 *		"login":"andre.guimaraes"
	 *	}
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@PUT
	@Path("/usuarios/ativar")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean ativarUsuario(Usuario usuario) throws Exception{
		return manterUsuario(AcaoUsuario.REATIVAR, usuario);
	}
	
	
	public Boolean manterUsuario(AcaoUsuario acao, Usuario usuario) throws Exception{
		return sipClient.replicarUsuario(acao.getCodigoAcao(), usuario.getCodigo(), Constantes.CODIGO_ORGAO_ANS, usuario.getLogin(), usuario.getNome());		
	}
		
	public br.gov.ans.integracao.sei.client.Usuario getUsuario(String loginUsuario, String unidade) throws Exception{
		br.gov.ans.integracao.sei.client.Usuario usuario = new br.gov.ans.integracao.sei.client.Usuario();
		usuario.setSigla(loginUsuario);
		
		List<br.gov.ans.integracao.sei.client.Usuario> usuarios = Arrays.asList(listarUsuarios(unidade, null));
		
		int index = usuarios.indexOf(usuario);
		
		if(index < 0){
			throw new Exception("Usuário "+ loginUsuario +" não encontrado.");
		}
		
		return usuarios.get(index);
	}
	
}
