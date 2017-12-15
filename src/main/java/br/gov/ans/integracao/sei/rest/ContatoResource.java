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

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ResourceNotFoundException;
import br.gov.ans.exceptions.WrappedException;
import br.gov.ans.integracao.sei.client.Contato;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.modelo.Pessoa;
import br.gov.ans.integracao.sei.modelo.enums.Acao;
import br.gov.ans.integracao.sei.modelo.enums.TipoContato;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.integracao.sei.utils.ContatoHelper;
import br.gov.ans.integracao.sei.utils.PessoaHelper;
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
	
	/**
	 * @api {get} /:unidade/contatos/tipos Tipos de contato
	 * @apiName listarTipos
	 * @apiGroup Contato
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Consulta os tipos de contato.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/sei-broker/service/cosap/contatos/tipos
	 *
	 * @apiSuccess (Sucesso - 200) {TipoContato[]} resultado Lista com os tipos de contato, representados por uma Enum TipoContato.
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/contatos/tipos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public TipoContato[] listarTipos(@PathParam("unidade") String unidade){
		return TipoContato.values();		 
	}
	
	/**
	 * @api {get} /:unidade/contatos/:tipo Listar contatos
	 * @apiName listarContatos
	 * @apiGroup Contato
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Consulta os contatos de determinado tipo, o retorno pode ser PessoaFisica ou PessoaJuridica é recomendado utilizar a ans-commons-sei.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} tipo Tipo do contato
	 * 
	 * @apiParam (Query Parameters) {String} [nome] Nome do contato
	 * @apiParam (Query Parameters) {String} [cpf] CPF do contato
	 * @apiParam (Query Parameters) {String} [cnpj] CNPJ do contato
	 * @apiParam (Query Parameters) {String} [sigla] Sigla(login) do contato
	 * @apiParam (Query Parameters) {String} [matricula] Matricula do contato
	 * @apiParam (Query Parameters) {String} [qtdRegistros = 1] Quantidade de contatos que serão exibidos
	 * @apiParam (Query Parameters) {String} [pagina = 1] Número da página
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/sei-broker/service/cosap/contatos/operadoras?qtdRegistros=20
	 *
	 * @apiSuccess (Sucesso - 200) {List} resultado Lista com os contatos encontrados.
	 * @apiSuccess (Sucesso - 200) {Pessoa} resultado.pessoa Pessoa que representa o contato.
	 * @apiSuccess (Sucesso - 200) {String="Fisica","Juridica"} resultado.pessoa.type Tipo de pessoa.
	 * @apiSuccess (Sucesso - 200) {String} resultado.pessoa.nome Nome do contato.
	 * @apiSuccess (Sucesso - 200) {String} resultado.pessoa.sigla Sigla(login) do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.email] Email do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.telefone] Telefone fixo do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.celular] Celular do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.observacao] Observações sobre o contato.
	 * @apiSuccess (Sucesso - 200) {Boolean} [resultado.pessoa.ativo = false] Situação do contato.
	 * @apiSuccess (Sucesso - 200) {Endereco} [resultado.pessoa.endereco] Endereço do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.logradouro] Logradouro do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.complemento] Complento do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.numero] Número do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.cep] CEP do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.bairro] Bairro do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.cidade] Código do IBGE da cidade.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.uf] Sigla do estado.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.pais] País do endereço.
	 * @apiSuccess (Sucesso - 200) {PessoaJuridica} [resultado.pessoa.associado] Pessoa Jurídica a qual o contato está associado.
	 * @apiSuccess (Sucesso - 200) {String="Juridica"} resultado.pessoa.associado.type Tipo de pessoa, neste caso o tipo deve ser Juridica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.associado.sigla] Sigla(login) do contato associado.
	 * @apiSuccess (Sucesso - 200) {String="MASCULINO","FEMININO"} [resultado.pessoa.sexo] Sexo do contato, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.cpf] CPF do contato, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.rg] RG do contato, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.orgaoEmissor] Orgão emissor do RG, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.dataNascimento] Data de nascimento, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.matricula] Matricula do contato, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.matriculaOab] Número de registro do OAB, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.cnpj] CNPJ do contato, presente em contatos do tipo PessoaJuridica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.website] Site do contato, presente em contatos do tipo PessoaJuridica.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "@type": "Juridica",
	 *       "nome": "18 DE JULHO ADMINISTRADORA DE BENEFÍCIOS LTDA",
	 *       "endereco": {
	 *       	"logradouro": "RUA CAPITÃO MEDEIROS DE REZENDE 274",
	 *       	"complemento": "Teste de complemento",
	 *       	"numero": null,
	 *       	"uf": "MG",
	 *       	"pais": "Brasil",
	 *       	"cidade": "3101508",
	 *       	"bairro": "PRAÇA DA BANDEIRA",
	 *       	"cep": "36660000"
	 *       },
	 *       "email": "teste@email.com",
	 *       "celular": "(32)982538993",
	 *       "telefone": "(32)34624649",
	 *       "observacao": "Observado via SEI-Broker",
	 *       "ativo": true,
	 *       "associado": null,
	 *       "cnpj": "19541931000125",
	 *       "website": "sitiodopicapauamarelo.com.br"
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
    
	/**
	 * @api {get} /:unidade/contatos/:tipo/:sigla Consultar contato
	 * @apiName getContato
	 * @apiGroup Contato
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Consulta contato pela sigla(login)
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} tipo Tipo do contato
	 * @apiParam (Path Parameters) {String} sigla Sigla(login) do contato
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/sei-broker/service/cosap/contatos/operadoras/419761
	 *
	 * @apiSuccess (Sucesso - 200) {Pessoa} resultado.pessoa Pessoa que representa o contato.
	 * @apiSuccess (Sucesso - 200) {String="Fisica","Juridica"} resultado.pessoa.type Tipo de pessoa.
	 * @apiSuccess (Sucesso - 200) {String} resultado.pessoa.nome Nome do contato.
	 * @apiSuccess (Sucesso - 200) {String} resultado.pessoa.sigla Sigla(login) do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.email] Email do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.telefone] Telefone fixo do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.celular] Celular do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.observacao] Observações sobre o contato.
	 * @apiSuccess (Sucesso - 200) {Boolean} [resultado.pessoa.ativo = false] Situação do contato.
	 * @apiSuccess (Sucesso - 200) {Endereco} [resultado.pessoa.endereco] Endereço do contato.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.logradouro] Logradouro do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.complemento] Complento do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.numero] Número do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.cep] CEP do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.bairro] Bairro do endereço.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.cidade] Código do IBGE da cidade.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.uf] Sigla do estado.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.endereco.pais] País do endereço.
	 * @apiSuccess (Sucesso - 200) {PessoaJuridica} [resultado.pessoa.associado] Pessoa Jurídica a qual o contato está associado.
	 * @apiSuccess (Sucesso - 200) {String="Juridica"} resultado.pessoa.associado.type Tipo de pessoa, neste caso o tipo deve ser Juridica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.associado.sigla] Sigla(login) do contato associado.
	 * @apiSuccess (Sucesso - 200) {String="MASCULINO","FEMININO"} [resultado.pessoa.sexo] Sexo do contato, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.cpf] CPF do contato, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.rg] RG do contato, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.orgaoEmissor] Orgão emissor do RG, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.dataNascimento] Data de nascimento, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.matricula] Matricula do contato, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.matriculaOab] Número de registro do OAB, presente em contatos do tipo PessoaFisica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.cnpj] CNPJ do contato, presente em contatos do tipo PessoaJuridica.
	 * @apiSuccess (Sucesso - 200) {String} [resultado.pessoa.website] Site do contato, presente em contatos do tipo PessoaJuridica.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "@type": "Juridica",
	 *       "nome": "18 DE JULHO ADMINISTRADORA DE BENEFÍCIOS LTDA",
	 *       "sigla": "419761",
	 *       "endereco": {
	 *       	"logradouro": "RUA CAPITÃO MEDEIROS DE REZENDE 274",
	 *       	"complemento": "Teste de complemento",
	 *       	"numero": null,
	 *       	"uf": "MG",
	 *       	"pais": "Brasil",
	 *       	"cidade": "3101508",
	 *       	"bairro": "PRAÇA DA BANDEIRA",
	 *       	"cep": "36660000"
	 *       },
	 *       "email": "teste@email.com",
	 *       "celular": "(32)982538993",
	 *       "telefone": "(32)34624649",
	 *       "observacao": "Observado via SEI-Broker",
	 *       "ativo": true,
	 *       "associado": null,
	 *       "cnpj": "19541931000125",
	 *       "website": "sitiodopicapauamarelo.com.br"
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
    @GET
    @Path("{unidade}/contatos/{tipo}/{sigla}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Pessoa getContato(@PathParam("unidade") String unidade, @PathParam("tipo") TipoContato tipo, @PathParam("sigla") String sigla) throws RemoteException, Exception{
    	Contato[] contatos = seiNativeService.listarContatos(Constantes.SEI_BROKER, Operacao.LISTAR_CONTATOS, unidadeResource.consultarCodigo(unidade), tipo.getCodigo(), 
				null, null, sigla, null, null, null, null, null);
    	
    	if(ArrayUtils.isEmpty(contatos)){
    		throw new ResourceNotFoundException(messages.getMessage("erro.contato.nao.encontrado",sigla));
    	}
    	
    	return pessoaHelper.buildPessoa(contatos[0]);
    }
    
	/**
	 * @api {post} /:unidade/contatos/:tipo Incluir contato
	 * @apiName criarContato
	 * @apiGroup Contato
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Incluir contato
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} tipo Tipo do contato
	 * 
	 * @apiParam (Request Body) {Pessoa} resultado.pessoa Pessoa que representa o contato.
	 * @apiParam (Request Body) {String="Fisica","Juridica"} resultado.pessoa.type Tipo de pessoa.
	 * @apiParam (Request Body) {String} resultado.pessoa.nome Nome do contato.
	 * @apiParam (Request Body) {String} resultado.pessoa.sigla Sigla(login) do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.email] Email do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.telefone] Telefone fixo do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.celular] Celular do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.observacao] Observações sobre o contato.
	 * @apiParam (Request Body) {Boolean} [resultado.pessoa.ativo = false] Situação do contato.
	 * @apiParam (Request Body) {Endereco} [resultado.pessoa.endereco] Endereço do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.logradouro] Logradouro do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.complemento] Complento do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.numero] Número do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.cep] CEP do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.bairro] Bairro do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.cidade] Código do IBGE da cidade.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.uf] Sigla do estado.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.pais] País do endereço.
	 * @apiParam (Request Body) {PessoaJuridica} [resultado.pessoa.associado] Pessoa Jurídica a qual o contato está associado.
	 * @apiParam (Request Body) {String="Juridica"} resultado.pessoa.associado.type Tipo de pessoa, neste caso o tipo deve ser Juridica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.associado.sigla] Sigla(login) do contato associado.
	 * @apiParam (Request Body) {String="MASCULINO","FEMININO"} [resultado.pessoa.sexo] Sexo do contato, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.cpf] CPF do contato, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.rg] RG do contato, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.orgaoEmissor] Orgão emissor do RG, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.dataNascimento] Data de nascimento, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.matricula] Matricula do contato, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.matriculaOab] Número de registro do OAB, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.cnpj] CNPJ do contato, presente em contatos do tipo PessoaJuridica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.website] Site do contato, presente em contatos do tipo PessoaJuridica.
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://<host>/sei-broker/service/cosap/contatos/operadoras
	 *
	 *	body:
	 *     {
	 *       "@type": "Juridica",
	 *       "nome": "18 DE JULHO ADMINISTRADORA DE BENEFÍCIOS LTDA",
	 *       "sigla": "419761",
	 *       "endereco": {
	 *       	"logradouro": "RUA CAPITÃO MEDEIROS DE REZENDE 274",
	 *       	"complemento": "Teste de complemento",
	 *       	"numero": null,
	 *       	"uf": "MG",
	 *       	"pais": "Brasil",
	 *       	"cidade": "3101508",
	 *       	"bairro": "PRAÇA DA BANDEIRA",
	 *       	"cep": "36660000"
	 *       },
	 *       "email": "teste@email.com",
	 *       "celular": "(32)982538993",
	 *       "telefone": "(32)34624649",
	 *       "observacao": "Observado via SEI-Broker",
	 *       "ativo": true,
	 *       "associado": null,
	 *       "cnpj": "19541931000125",
	 *       "website": "sitiodopicapauamarelo.com.br"
	 *     }
	 *
	 * @apiSuccess (Criado - 201) {header} Location URL de acesso ao recurso criado.
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
     
	/**
	 * @api {put} /:unidade/contatos/:tipo/:sigla Atualizar contato
	 * @apiName atualizarContato
	 * @apiGroup Contato
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Atualizar contato
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} tipo Tipo do contato
	 * @apiParam (Path Parameters) {String} sigla Sigla(login) do contato
	 * 
	 * @apiParam (Request Body) {Pessoa} resultado.pessoa Pessoa que representa o contato.
	 * @apiParam (Request Body) {String="Fisica","Juridica"} resultado.pessoa.type Tipo de pessoa.
	 * @apiParam (Request Body) {String} resultado.pessoa.nome Nome do contato.
	 * @apiParam (Request Body) {String} resultado.pessoa.sigla Sigla(login) do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.email] Email do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.telefone] Telefone fixo do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.celular] Celular do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.observacao] Observações sobre o contato.
	 * @apiParam (Request Body) {Boolean} [resultado.pessoa.ativo = false] Situação do contato.
	 * @apiParam (Request Body) {Endereco} [resultado.pessoa.endereco] Endereço do contato.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.logradouro] Logradouro do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.complemento] Complento do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.numero] Número do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.cep] CEP do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.bairro] Bairro do endereço.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.cidade] Código do IBGE da cidade.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.uf] Sigla do estado.
	 * @apiParam (Request Body) {String} [resultado.pessoa.endereco.pais] País do endereço.
	 * @apiParam (Request Body) {PessoaJuridica} [resultado.pessoa.associado] Pessoa Jurídica a qual o contato está associado.
	 * @apiParam (Request Body) {String="Juridica"} resultado.pessoa.associado.type Tipo de pessoa, neste caso o tipo deve ser Juridica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.associado.sigla] Sigla(login) do contato associado.
	 * @apiParam (Request Body) {String="MASCULINO","FEMININO"} [resultado.pessoa.sexo] Sexo do contato, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.cpf] CPF do contato, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.rg] RG do contato, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.orgaoEmissor] Orgão emissor do RG, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.dataNascimento] Data de nascimento, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.matricula] Matricula do contato, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.matriculaOab] Número de registro do OAB, presente em contatos do tipo PessoaFisica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.cnpj] CNPJ do contato, presente em contatos do tipo PessoaJuridica.
	 * @apiParam (Request Body) {String} [resultado.pessoa.website] Site do contato, presente em contatos do tipo PessoaJuridica.
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://<host>/sei-broker/service/cosap/contatos/operadoras
	 *
	 *	body:
	 *     {
	 *       "@type": "Juridica",
	 *       "nome": "18 DE JULHO ADMINISTRADORA DE BENEFÍCIOS LTDA",
	 *       "sigla": "419761",
	 *       "endereco": {
	 *       	"logradouro": "RUA CAPITÃO MEDEIROS DE REZENDE 274",
	 *       	"complemento": "Teste de complemento",
	 *       	"numero": null,
	 *       	"uf": "MG",
	 *       	"pais": "Brasil",
	 *       	"cidade": "3101508",
	 *       	"bairro": "PRAÇA DA BANDEIRA",
	 *       	"cep": "36660000"
	 *       },
	 *       "email": "teste@email.com",
	 *       "celular": "(32)982538993",
	 *       "telefone": "(32)34624649",
	 *       "observacao": "Observado via SEI-Broker",
	 *       "ativo": true,
	 *       "associado": null,
	 *       "cnpj": "19541931000125",
	 *       "website": "sitiodopicapauamarelo.com.br"
	 *     }
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK	
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */    
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
