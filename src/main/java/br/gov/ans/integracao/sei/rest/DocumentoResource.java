package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.decodeConteudoMustache;
import static br.gov.ans.integracao.sei.utils.Util.encodeBase64;
import static br.gov.ans.integracao.sei.utils.Util.getSOuN;
import static br.gov.ans.integracao.sei.utils.Util.parseInt;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;
import static br.gov.ans.utils.PDFUtil.getPDF;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.jboss.logging.Logger;

import com.github.mustachejava.Mustache;

import br.gov.ans.commons.security.crypt.HashUtils;
import br.gov.ans.dao.DAO;
import br.gov.ans.integracao.sei.client.Documento;
import br.gov.ans.integracao.sei.client.RetornoConsultaDocumento;
import br.gov.ans.integracao.sei.client.RetornoInclusaoDocumento;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.dao.DocumentoDAO;
import br.gov.ans.integracao.sei.dao.InclusaoDocumentoDAO;
import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sei.exceptions.WrappedException;
import br.gov.ans.integracao.sei.helper.DocumentoHelper;
import br.gov.ans.integracao.sei.modelo.CancelamentoDocumento;
import br.gov.ans.integracao.sei.modelo.DocumentoResumido;
import br.gov.ans.integracao.sei.modelo.ExclusaoDocumento;
import br.gov.ans.integracao.sei.modelo.InclusaoDocumento;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.integracao.sei.utils.MessagesKeys;
import br.gov.ans.utils.MessageUtils;
import br.gov.ans.utils.MustacheUtils;

@Path("/")
public class DocumentoResource {
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
    
    @Inject
    private UnidadeResource unidadeResource;
	
    @Inject 
    private DAO<InclusaoDocumento> daoInclusaoDocumento;
    
    @Inject 
    private DAO<ExclusaoDocumento> daoExclusaoDocumento;
    
    @Inject
    private InclusaoDocumentoDAO daoInclusaoDocumentImp;
    
    @Inject
    private DocumentoDAO daoDocumento;
        
	@Inject
	private Logger logger;
	
    @Inject
    private MessageUtils messages;
    
    @Inject
    private DocumentoHelper documentoHelper;
    
	@Context
	private SecurityContext securityContext;
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private UserTransaction userTransaction;
			
	/** 
	 * @api {get} /:unidade/documentos/:documento Consultar documento
	 * @apiName consultarDocumento
	 * @apiGroup Documento
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta documento cadastrado no SEI.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} documento Id do documento que deseja recuperar as informações
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [andamento = N] exibir o andamento do processo
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [assinaturas = N] exibir as assinaturas presentes no documento
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [publicacao = N] exibir detalhes da publicação
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [campos = N] exibir campos do formulário
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/COSAP/documentos/0000050?assinaturas=S&andamento=S
	 *
	 * @apiSuccess (Sucesso - 200) {RetornoConsultaDocumento} retornoConsultaDocumento Informações do documento encontrado no SEI
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.idProcedimento Id interno do processo no SEI
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.procedimentoFormatado Número do processo visível para o usuário
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.idDocumento Id interno do documento no SEI
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.documentoFormatado Número do documento visível para o usuário
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.linkAcesso Link para acesso ao documento
	 * @apiSuccess (Sucesso - 200) {Serie} retornoConsultaDocumento.serie Dados do tipo do documento
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.serie.idSerie Identificador do tipo de documento
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.serie.nome Nome do tipo de documento
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.serie.aplicabilidade :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.numero Número do documento
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.data Data de geração para documentos internos e para documentos externos é a data informada na tela de cadastro
	 * @apiSuccess (Sucesso - 200) {Unidade} retornoConsultaDocumento.unidadeElaboradora Dados da unidade que gerou o documento
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.unidadeElaboradora.descricao Nome da unidade
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.unidadeElaboradora.idUnidade Código da unidade
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.unidadeElaboradora.sigla Sigla da unidade
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.unidadeElaboradora.sinProtocolo :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.unidadeElaboradora.sinArquivamento :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.unidadeElaboradora.sinOuvidoria :TODO pendente
	 * @apiSuccess (Sucesso - 200) {Andamento} retornoConsultaDocumento.andamentoGeracao Informações do andamento de geração (opcional)
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.idAndamento Identificador do andamento
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.idTarefa :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.idTarefaModulo :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.descricao Descrição do andamento
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.dataHora Data e hora do registro de andamento
	 * @apiSuccess (Sucesso - 200) {Unidade} retornoConsultaDocumento.andamentoGeracao.unidade Unidade responsável pelo andamento (ver estrutura Unidade)
	 * @apiSuccess (Sucesso - 200) {Usuario} retornoConsultaDocumento.andamentoGeracao.usuario Usuário responsável pela ação
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.usuario.idUsuario Código do usuário
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.usuario.nome Nome do usuário
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.usuario.sigla Login do usuário
	 * @apiSuccess (Sucesso - 200) {AtributoAndamento[]} retornoConsultaDocumento.andamentoGeracao.atributos Lista com os atributos relacionados ao andamento
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.atributos.nome Nome do atributo
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.atributos.valor Valor do atributo
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.andamentoGeracao.atributos.idOrigem :TODO pendente 
	 * @apiSuccess (Sucesso - 200) {Assinatura[]} retornoConsultaDocumento.assinaturas Conjunto de assinaturas do documento
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.assinaturas.nome Nome do assinante
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.assinaturas.cargoFuncao Cargo ou função utilizado no momento da assinatura
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.assinaturas.dataHora Data/hora em que ocorreu a assinatura
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.assinaturas.idUsuario Identificador do usuário
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.assinaturas.idOrigem :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.assinaturas.idOrgao :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.assinaturas.sigla Login do usuário responsável pela assinatura
	 * @apiSuccess (Sucesso - 200) {Publicacao} retornoConsultaDocumento.publicacao :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.nomeVeiculo :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.numero :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.dataDisponibilizacao :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.dataPublicacao :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.estado :TODO pendente
	 * @apiSuccess (Sucesso - 200) {PublicacaoImprensaNacional} retornoConsultaDocumento.publicacao.imprensaNacional :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.imprensaNacional.siglaVeiculo :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.imprensaNacional.descricaoVeiculo :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.imprensaNacional.pagina :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.imprensaNacional.secao :TODO pendente
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.publicacao.imprensaNacional.data :TODO pendente
	 * @apiSuccess (Sucesso - 200) {Campo[]} retornoConsultaDocumento.campos Conjunto de campos do formulário
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.campos.nome Nome do campo
	 * @apiSuccess (Sucesso - 200) {String} retornoConsultaDocumento.campos.valor Valor do campo
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {
	 *  	"idProcedimento":"52",
	 *  	"procedimentoFormatado":"16.0.000000005-5",
	 *  	"idDocumento":"152",
	 *  	"documentoFormatado":"0000123",
	 *  	"linkAcesso":"https://sei-hm.ans.gov.br/controlador.php?acao=procedimento_trabalhar&id_procedimento=52&id_documento=152",
	 *  	"serie":{
	 *  		"idSerie":"12",
	 *  		"nome":"Memorando"
	 *  	},
	 *  	"numero":"6",
	 *  	"data":"05/04/2016",
	 *  	"unidadeElaboradora":{
	 *  		"idUnidade":"110000934",
	 *  		"sigla":"COSAP",
	 *  		"descricao":"Coordenadoria de Sistemas e Aplicativos"
	 *  	},
	 *  	"andamentoGeracao":null,
	 *  	"assinaturas":[],
	 *  	"publicacao":null,
	 *  	"campos":[] 	
	 *  }
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/documentos/{documento:\\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public RetornoConsultaDocumento consultarDocumento(@PathParam("unidade") String unidade, @PathParam("documento") String documento, @QueryParam("andamento") String andamento,
			@QueryParam("assinaturas") String assinaturas, @QueryParam("publicacao") String publicacao, @QueryParam("campos") String campos) throws Exception{
		return seiNativeService.consultarDocumento(Constantes.SEI_BROKER, Operacao.CONSULTAR_DOCUMENTO, unidadeResource.consultarCodigo(unidade), documento, 
				getSOuN(andamento), getSOuN(assinaturas), getSOuN(publicacao), getSOuN(campos));	
	}
	
	/**
	 * @api {post} /:unidade/documentos Incluir documento
	 * @apiName incluirDocumento
	 * @apiGroup Documento
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Inclui um documento no SEI, podem ser incluídos documentos internos e externos, para documentos externo o tamanho máximo é 20MB.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiParam (Query Parameters) {String} [template] Identificador do template que será utilizado na transformação do conteudo para HTML, o template precisa ser previamente cadastrado no templates-broker. (Este atributo exige que o atributo conteudo seja enviado em formato JSon e codificado em Base64)
	 * 
	 * @apiParam (Request Body) {String = "G (Gerado)","R (Recebido)"} tipo Tipo de documento
	 * @apiParam (Request Body) {String} idSerie Identificador do tipo de documento no SEI (Consultar serviço Listar Séries)
	 * @apiParam (Request Body) {String} idProcedimento Identificador do processo onde o documento deve ser inserido. Opcional se protocoloProcedimento informado
	 * @apiParam (Request Body) {String} [protocoloProcedimento] Número do processo onde o documento deve ser inserido, visível para o usuário. Opcional se IdProcedimento informado.
	 * @apiParam (Request Body) {String} [idTipoConferencia] Identificador do tipo de conferência associada com o documento externo
	 * @apiParam (Request Body) {String{50}} [numero] Número do documento, passar null para documentos gerados com numeração controlada pelo SEI.Para documentos externos informar o número ou nome complementar a ser exibido na árvore de documentos do processo (o SEI não controla numeração de documentos externos). 
	 * @apiParam (Request Body) {String} [data] Data do documento (dd/MM/yyyy), obrigatório para documentos externos. Passar null para documentos gerados.
	 * @apiParam (Request Body) {String} [descricao] Descrição do documento para documentos gerados. Passar null para documentos externos
	 * @apiParam (Request Body) {Remetente} [remetente] Obrigatório para documentos externos, passar null para documentos gerados.
	 * @apiParam (Request Body) {String} [remetente.nome] Nome do remetente
	 * @apiParam (Request Body) {String} [remetente.sigla] Login do remetente
	 * @apiParam (Request Body) {Interessado[]} [interessados] Informar um conjunto com os dados de interessados. Se não existirem interessados deve ser informado um conjunto vazio
	 * @apiParam (Request Body) {String} [interessados.nome] Nome do interessado
	 * @apiParam (Request Body) {String} [interessados.sigla] Login do interessado
	 * @apiParam (Request Body) {Destinatario[]} [destinatarios] Informar um conjunto com os dados de destinatários. Se não existirem destinatários deve ser informado um conjunto vazio
	 * @apiParam (Request Body) {String} [destinatarios.nome] Nome do destinatário
	 * @apiParam (Request Body) {String} [destinatarios.sigla] Login do destinatário
	 * @apiParam (Request Body) {String} [observacao] Texto da observação da unidade, passar null se não existir
	 * @apiParam (Request Body) {String{200}} [nomeArquivo] Nome do arquivo, obrigatório para documentos externos. Passar null para documentos gerados.
	 * @apiParam (Request Body) {String} conteudo Conteúdo do arquivo codificado em Base64. Para documentos gerados será o conteúdo da seção principal do editor HTML e para documentos externos será o conteúdo do anexo. Para documentos com template cadastrado, enviar Base64 do JSon referente ao conteúdo a ser preenchido no template.
	 * @apiParam (Request Body) {String = "0 (público)","1 (restrito)", "2 (sigiloso)", "null (herda do tipo de processo)"} [nivelAcesso] Nível de acesso do documento
	 * @apiParam (Request Body) {String} [idHipoteseLegal] Identificador da hipótese legal associada
	 * @apiParam (Request Body) {String} [idArquivo] Identificador do arquivo enviado pelo serviço de Incluir Arquivo
	 * @apiParam (Request Body) {String = "S (Sim)","N (Não)"} [sinBloqueado] Bloquear o documento, não permite excluí-lo ou alterar seu conteúdo
	 * @apiParam (Request Body) {Campo} [campos] Conjunto de campos associados com o formulário
	 * @apiParam (Request Body) {String} [campos.nome] Nome do campo
	 * @apiParam (Request Body) {String} [campos.valor] Valor do campo
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: https://<host>/sei-broker/service/COSAP/documentos
	 *
	 *	body:
	 *	{
	 *		"tipo":"G",
	 *		"idProcedimento":"33910000173201771",
	 *		"protocoloProcedimento":null,
	 *		"idSerie":null,
	 *		"numero":null,
	 *		"data":null,
	 *		"descricao":"Documento demonstrativo",
	 *		"remetente":null,
	 *		"interessados":[{"sigla":"andre.guimaraes","nome":"André Luís Fernandes Guimarães"}],
	 *		"destinatarios":[],
	 *		"observacao":null,
	 *		"nomeArquivo":null,
	 *		"conteudo":"Conteúdo Base64",
	 *		"nivelAcesso":"0"
	 *	}
	 *
	 * @apiSuccess (Sucesso Response Body - 201) {String} idDocumento número interno do documento
	 * @apiSuccess (Sucesso Response Body - 201) {String} documentoFormatado número do documento visível para o usuário
	 * @apiSuccess (Sucesso Response Body - 201) {String} linkAcesso link para acesso ao documento
	 * 
	 * @apiSuccess (Sucesso Response Header - 201) {header} Location URL de acesso ao recurso criado.
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 201 Created
	 *     {
	 *       "idDocumento":"1140000000872",
	 *       "documentoFormatado":"0003934",
	 *       "linkAcesso":"https://sei-hm.ans.gov.br/controlador.php?acao=arvore_visualizar&acao_origem=procedimento_visualizar&id_procedimento=267&id_documento=1017&sta_editor=I&infra_sistema=100000100&infra_unidade_atual=110000934&infra_hash=3d798777382d6ac455317f3a87ad9bd1f9650315e019ef922f388b829902a95b"
	 *     }
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Path("{unidade}/documentos")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response incluirDocumento(@PathParam("unidade") String unidade, @QueryParam("template") String template, Documento documento) throws RemoteException, Exception{
		RetornoInclusaoDocumento retorno = null;
		
		documentoHelper.validarDocumento(documento, template);

		InclusaoDocumento inclusaoDocumento = registrarInclusao(documento, unidade);
		
		try{
			logger.debug(messages.getMessage(MessagesKeys.DEBUG_NOVO_DOCUMENTO_ENVIADO));
			
			retorno = seiNativeService.incluirDocumento(Constantes.SEI_BROKER, Operacao.INCLUIR_DOCUMENTO,  unidadeResource.consultarCodigo(unidade), documento);
			
			logger.debug(messages.getMessage(MessagesKeys.DEBUG_NOVO_DOCUMENTO_PROCESSADO));
		}catch(Exception ex){
			registrarProblemaInclusao(inclusaoDocumento);
			
			throw new WrappedException(ex);
		}

		inclusaoDocumento.setNumero(retorno.getDocumentoFormatado());
		inclusaoDocumento.setLink(retorno.getLinkAcesso());
		
		confirmarInclusao(inclusaoDocumento);

		return Response.created(getResourcePath(retorno.getDocumentoFormatado())).entity(retorno).build();
	}
		
	/**
	 * @api {post} /:unidade/documentos/cancelados Cancelar documento
	 * @apiName cancelarDocumento
	 * @apiGroup Documento
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Cancela um documento.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiParam (Request Body) {String} documento Numero do documento que será cancelado
	 * @apiParam (Request Body) {String} motivo Motivo do cancelamento
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: https://<host>/sei-broker/service/COSAP/documentos/cancelados
	 *
	 *	body:
	 *	{
	 *		"documento":"0000050",
	 *		"motivo":"Motivo do cancelamento."
	 *	}
	 *	
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
	@Path("{unidade}/documentos/cancelados")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String cancelarDocumento(@PathParam("unidade") String unidade, CancelamentoDocumento cancelamento) throws Exception{
		validarMotivoCancelamento(cancelamento.getMotivo());
		
		String resultado = seiNativeService.cancelarDocumento(Constantes.SEI_BROKER, Operacao.CANCELAR_DOCUMENTO, unidadeResource.consultarCodigo(unidade), 
				cancelamento.getDocumento(), cancelamento.getMotivo());
		
		if(trueOrFalse(resultado)){
			registrarExclusao(cancelamento.getDocumento(), unidade, cancelamento.getMotivo());
		}
		
		return trueOrFalse(resultado) + "";
	}
	
	/**
	 * @api {get} /:unidade/documentos/enviados-broker Listar documentos enviados
	 * @apiName consultarDocumentosIncluidosBroker
	 * @apiGroup Documento
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta documentos inclusos pelo SEI-Broker.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiParam (Query Parameters) {String} [hash] Hash SHA-256 gerado a partir do conteúdo enviado ao Broker
	 * @apiParam (Query Parameters) {String} [processo] Número do processo onde o documento foi inserido
	 * @apiParam (Query Parameters) {String} [numeroInformado] Número informado na inclusão do documento, exibido na árvore do processo.
	 * @apiParam (Query Parameters) {String} [pagina=1] Número da página
	 * @apiParam (Query Parameters) {String} [qtdRegistros = 50] Quantidade de registros que serão exibidos por página
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i https://<host>/sei-broker/service/COSAP/documentos/enviados-broker?pagina=1&qtdRegsitros=30
	 *
	 * @apiSuccess (Sucesso Response Body - 200) {InclusaoDocumento} resultado Objeto com dados sobre o documento
	 * @apiSuccess (Sucesso Response Body - 200) {Date} resultado.data Data do envio (padrão ISO-8601)
	 * @apiSuccess (Sucesso Response Body - 200) {String} resultado.hash Hash SHA-256 gerado a partir do conteúdo enviado ao Broker
	 * @apiSuccess (Sucesso Response Body - 200) {Integer} resultado.id Identificação da inclusão de documento
	 * @apiSuccess (Sucesso Response Body - 200) {String} resultado.nome Nome do documento incluído
	 * @apiSuccess (Sucesso Response Body - 200) {String} resultado.numero Número retonado pelo SEI, NULL caso tenha ocorrido algum problema.
	 * @apiSuccess (Sucesso Response Body - 200) {String} resultado.processo Número do processo
	 * @apiSuccess (Sucesso Response Body - 200) {String} resultado.sistema Sistema responsável pela inclusão
	 * @apiSuccess (Sucesso Response Body - 200) {String} resultado.unidade Unidade onde foi incluído o documento
	 * @apiSuccess (Sucesso Response Body - 200) {String} resultado.numeroInformado Valor opcional informado na inclusão do documento
	 * 
	 * @apiSuccess (Sucesso Response Header- 200) {header} total_registros Quantidade de registros que existem para essa consulta
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "id": 1717,
	 *       "data": "2016-10-31T11:59:56.016+0000",
	 *       "nome": "Doc Homologação.pdf",
	 *       "numero": "0003312",
	 *       "hash": "ca7ebe0c37419db14ffd4f09485a1ebed8e8deeed594e15720da185ee32e9d19",
	 *       "sistema": "desenv_integracao_sei",
	 *       "unidade": "COAI",
	 *       "processo": "33910000097201612",
	 *       "numeroInformado":"2016ans45875"
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
	@Path("{unidade}/documentos/enviados-broker")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public Response consultarDocumentosIncluidosBroker(@PathParam("unidade") String unidade, @QueryParam("hash") String hash, @QueryParam("processo") String processo, 
			@QueryParam("numeroInformado") String numeroInformado, @QueryParam("pagina") String pagina, @QueryParam("qtdRegistros") String qtdRegistros) throws Exception{

		List<InclusaoDocumento> documentosInclusos = daoInclusaoDocumentImp.getDocumentosInclusos(unidade.toUpperCase(), processo, hash, numeroInformado,
				pagina == null? null:parseInt(pagina), qtdRegistros == null? null : parseInt(qtdRegistros));

		GenericEntity<List<InclusaoDocumento>> entity = new GenericEntity<List<InclusaoDocumento>>(documentosInclusos){};

		Long totalRegistros = contarInclusoesDocumento(unidade.toUpperCase(), processo, hash, numeroInformado);

		return Response.ok().entity(entity)
		.header("total_registros", totalRegistros).build();
	}

	/** 
	 * @api {get} /:unidade/documentos/:documento Exportar documento
	 * @apiName exportarDocumento
	 * @apiGroup Documento
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Exporta documentos do SEI em PDF.
	 *
	 * @apiParam (Header Parameters) {String} Accept Utilize application/pdf
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} documento Id do documento que deseja recuperar as informações
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/COSIT/documentos/0003322
	 *
	 * @apiSuccess (Sucesso - 200) {PDF} binario Arquivo no formato PDF.
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/documentos/{documento}")
	@Produces("application/pdf")
	public Response exportarDocumentoV2(@PathParam("unidade") String unidade, @PathParam("documento") String documento) throws Exception{
		return exportarDocumento(unidade, documento);
	}
		
	@GET
	@Path("{unidade}/documentos/{documento}/pdf")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response exportarDocumento(@PathParam("unidade") String unidade, @PathParam("documento") String documento) throws Exception{
		RetornoConsultaDocumento retorno = seiNativeService.consultarDocumento(Constantes.SEI_BROKER, Operacao.CONSULTAR_DOCUMENTO, unidadeResource.consultarCodigo(unidade), documento, 
				Constantes.NAO, Constantes.NAO, Constantes.NAO, Constantes.NAO);
				
		String linkAcesso = retorno.getLinkAcesso();
		
		URL url = new URL(linkAcesso);
		URLConnection con = url.openConnection();
		
		String contentType = con.getHeaderField(Constantes.CONTENT_TYPE_HEADER_KEY);
		
		InputStream in = con.getInputStream();
		
		byte[] bytes = IOUtils.toByteArray(in);				
			
		if(isHTML(contentType)){			
			validarAssinaturaDocumento(bytes, documento);
			
			bytes = getPDF(bytes);
		}else if(isNotPDF(contentType)){
			throw new BusinessException(messages.getMessage("documento.nao.suportado", documento, contentType));
		}
		
	    return Response.ok(bytes).header("Content-Disposition", "attachment; filename=" +documento+ ".pdf").build();

	}
	
	/**
	 * @api {get} /interessados/:interessado/documentos Consultar por interessado
	 * @apiName consultarDocumentoInteressado
	 * @apiGroup Documento
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Retorna os documentos de um determinado interessado.
	 * 
	 * @apiParam (Path Parameters) {String} interessado Identificador do interessado
	 * 
	 * @apiParam (Query Parameters) {String} [tipo] Tipo/Série do documento
	 * @apiParam (Query Parameters) {boolean} [somenteAssinados=false] Exibir somente documentos assinados
	 * @apiParam (Query Parameters) {boolean} [orderByProcesso=false] Ordenar pelo número do processo, por padrão o retorno é ordenado pela dataGeracao
	 * @apiParam (Query Parameters) {boolean} [crescente=false] Ordenar em ordem crescente
	 * @apiParam (Query Parameters) {String} [pagina=1] Número da página
	 * @apiParam (Query Parameters) {String} [qtdRegistros=50] Quantidade de registros retornados por página
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i https://<host>/sei-broker/service/interessados/005711/documentos
	 *
	 * @apiSuccess (Sucesso Response Body - 200) {List} documentos Lista com os documentos encontrados.
	 * @apiSuccess (Sucesso Response Body - 200) {DocumentoResumido} documentos.documentoResumido Resumo do documento encontrado no SEI.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.numero Número do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.numeroInformado Número informado na inclusão do documento, também conhecido como número de árvore.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.unidade Unidade responsável pelo documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String="GERADO","RECEBIDO"} documentos.documentoResumido.origem Origem do documento, se o mesmo é um documento "GERADO" internamente ou "RECEBIDO" de uma fonte externa.
	 * @apiSuccess (Sucesso Response Body - 200) {Data} documentos.documentoResumido.dataGeracao Data de geração do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.processo Processo onde o documento está incluído.
	 * @apiSuccess (Sucesso Response Body - 200) {Tipo} documentos.documentoResumido.tipo Objeto representando o tipo do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.tipo.codigo Identificados do tipo do documento, também conhecido como série.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.tipo.nome Nome do tipo do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.tipoConferencia Tipo de conferência do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {boolean} documentos.documentoResumido.assinado Boolean indicando se o documento foi assinado.
	 * 
	 * @apiSuccess (Sucesso Response Header - 200) {header} total_registros Quantidade de registros que existem para essa consulta
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "numero": "0670949",
	 *       "numeroInformado": "594",
	 *       "unidade": "COSAP",
	 *       "origem": "RECEBIDO",
	 *       "dataGeracao": "2015-08-10T00:00:00-03:00",
	 *       "processo": "33910.000002/2017-41",
	 *       "tipo": {
	 *       	"codigo": "629",
	 *       	"nome": "Relatório de Arquivamento-SIF"
	 *       }
	 *       "tipoConferencia": "4",
	 *       "assinado": true
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
	@Path("interessados/{interessado}/documentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public Response consultarDocumentos(@PathParam("interessado") String interessado, @QueryParam("tipo") String tipo, @QueryParam("pagina") String pagina, 
			@QueryParam("qtdRegistros") String qtdRegistros, @QueryParam("somenteAssinados") boolean somenteAssinados, @QueryParam("crescente") boolean ordemCrescente, 
			@QueryParam("orderByProcesso") boolean orderByProcesso) throws BusinessException, ResourceNotFoundException{
		
		List<DocumentoResumido> documentos = daoDocumento.getDocumentos(interessado, tipo, pagina == null? null:parseInt(pagina), qtdRegistros == null? null : parseInt(qtdRegistros),
				somenteAssinados, ordemCrescente, orderByProcesso);
		
		if(documentos.isEmpty()){
			throw new ResourceNotFoundException(messages.getMessage("erro.nenhum.documento.encontrado.interessado", interessado));
		}
		
		GenericEntity<List<DocumentoResumido>> entity = new GenericEntity<List<DocumentoResumido>>(documentos){};
		
		Long totalRegistros = daoDocumento.countDocumentos(interessado, tipo, somenteAssinados);
		
		return Response.ok().entity(entity)
		.header("total_registros", totalRegistros).build();
	}
	
	public Long contarInclusoesDocumento(String unidade, String processo, String hash, String numeroInformado){
		return daoInclusaoDocumentImp.countDocumentosInclusos(unidade, processo, hash, numeroInformado);
	}
	
	public InclusaoDocumento registrarInclusao(Documento documento, String unidade) throws Exception{
		InclusaoDocumento registro = new InclusaoDocumento(documento, unidade, getSistemaResponsavel(),	calcularHashDocumento(documento.getConteudo()));

		try{
			userTransaction.begin();
			
			daoInclusaoDocumento.persistSemJTA(registro);
			
			userTransaction.commit();
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.registro.inclusao"), ex);
			throw new Exception(messages.getMessage("erro.inesperado"));
		}
		
		return registro;
	}

	public void registrarProblemaInclusao(InclusaoDocumento inclusaoDocumento) throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
		inclusaoDocumento.setNumero(messages.getMessage("erro.sei"));
		
		userTransaction.begin();
		
		daoInclusaoDocumento.merge(inclusaoDocumento);
		
		userTransaction.commit();
	}

	public void confirmarInclusao(InclusaoDocumento inclusaoDocumento){
		try{
			userTransaction.begin();
			
			daoInclusaoDocumento.merge(inclusaoDocumento);
			
			userTransaction.commit();
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.persistir.confirmacao.inclusao.documento",inclusaoDocumento.getNumero()),ex);
		}
	}
	
	public String calcularHashDocumento(String input){
		try {
			logger.debug(messages.getMessage("hash.em.geracao"));
			return HashUtils.toSHA256(input, null);
		} catch (Exception ex) {
			logger.error(messages.getMessage("erro.calculo.hash"));
			logger.debug(ex, ex);
			return messages.getMessage("erro.calculo.hash");
		}finally{
			logger.debug(messages.getMessage("hash.gerado"));
		}
	}
	
	public String getSistemaResponsavel(){
		try{
			return securityContext.getUserPrincipal().getName();
		}catch (Exception ex) {
			logger.error(messages.getMessage("sem.informacoes.sistema"), ex);
			return messages.getMessage("sem.informacoes.sistema");
		}
	}
	
	public void validarAssinaturaDocumento(byte[] bytes, String documento) throws BusinessException{
		String body = new String(bytes);

		String documentoNaoAssinado = messages.getMessage("documento.nao.assinado", documento);
		String documentoSemAssinatura = messages.getMessage("documento.sem.assinatura");
		
		if(body.contains(documentoNaoAssinado) || body.contains(documentoSemAssinatura)){
			throw new BusinessException(documentoNaoAssinado);
		}
	}
	
	public boolean isHTML(String contentType){
		return contentType.toLowerCase().equals("text/html; charset=iso-8859-1");
	}
	
	public boolean isNotPDF(String contentType){
		contentType = StringUtils.remove(contentType, ";");		
		return !contentType.toLowerCase().equals("application/pdf");
	}

	public URI getResourcePath(String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}
	
	public void registrarExclusao(String numero, String unidade, String motivo) throws BusinessException, NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
		try{
			userTransaction.begin();
			
			daoExclusaoDocumento.persist(new ExclusaoDocumento(numero, getSistemaResponsavel(), unidade, motivo));
			
			userTransaction.commit();
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.registrar.exclusao.documento", numero), ex);
		}
	}
	
	public void validarMotivoCancelamento(String motivo) throws BusinessException{
		if(StringUtils.isBlank(motivo)){
			throw new BusinessException(messages.getMessage("erro.motivo.cancelamento.obrigatorio"));
		}
		if(motivo.length() > 500){
			throw new BusinessException(messages.getMessage("erro.tamanho.motivo.cancelamento"));
		}
	}

}
