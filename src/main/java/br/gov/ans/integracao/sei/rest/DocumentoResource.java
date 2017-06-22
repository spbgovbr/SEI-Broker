package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.getSOuN;
import static br.gov.ans.integracao.sei.utils.Util.parseInt;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;
import static br.gov.ans.utils.PDFUtil.getPDF;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.gov.ans.commons.security.crypt.HashUtils;
import br.gov.ans.dao.DAO;
import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.integracao.sei.client.Documento;
import br.gov.ans.integracao.sei.client.RetornoConsultaDocumento;
import br.gov.ans.integracao.sei.client.RetornoInclusaoDocumento;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.dao.DocumentoSiparDAO;
import br.gov.ans.integracao.sei.dao.InclusaoDocumentoDAO;
import br.gov.ans.integracao.sei.modelo.CancelamentoDocumento;
import br.gov.ans.integracao.sei.modelo.DocumentoSipar;
import br.gov.ans.integracao.sei.modelo.InclusaoDocumento;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;

@Path("/")
@Stateless
public class DocumentoResource {
	
    @Inject
    private DocumentoSiparDAO documentoSiparDAO;
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
    
    @Inject
    private UnidadeResource unidadeResource;
	
    @SuppressWarnings("cdi-ambiguous-dependency")
    @Inject
    private DAO<InclusaoDocumento> daoInclusaoDocumento;
    
    @Inject
    private InclusaoDocumentoDAO daoInclusaoDocumentImp;
        
	@Inject
	private Logger logger;

	@Context
	private SecurityContext securityContext;
	
	/** 
	 * @api {get} /:unidade/documentos/:documento Consultar documento
	 * @apiName consultarDocumento
	 * @apiGroup Documento
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza uma consulta aos documentos cadastrados no SEI.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} documento Id do documento que deseja recuperar as informações
	 * @apiParam {String = "S (sim), N (não)"} [andamento = N] exibir o andamento do processo
	 * @apiParam {String = "S (sim), N (não)"} [assinaturas = N] exibir as assinaturas presentes no documento
	 * @apiParam {String = "S (sim), N (não)"} [publicacao = N] exibir detalhes da publicação
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://anshmjboss01/sei-broker/service/COSAP/documentos/0000050?assinaturas=S&andamento=S
	 *
	 * @apiSuccess {RetornoConsultaDocumento} retornoConsultaDocumento Informações do documento encontrado no SEI
	 * @apiSuccess {String} retornoConsultaDocumento.idProcedimento Id interno do processo no SEI
	 * @apiSuccess {String} retornoConsultaDocumento.procedimentoFormatado Número do processo visível para o usuário
	 * @apiSuccess {String} retornoConsultaDocumento.idDocumento Id interno do documento no SEI
	 * @apiSuccess {String} retornoConsultaDocumento.documentoFormatado Número do documento visível para o usuário
	 * @apiSuccess {String} retornoConsultaDocumento.linkAcesso Link para acesso ao documento
	 * @apiSuccess {Serie} retornoConsultaDocumento.serie Dados do tipo do documento
	 * @apiSuccess {String} retornoConsultaDocumento.serie.idSerie Identificador do tipo de documento
	 * @apiSuccess {String} retornoConsultaDocumento.serie.nome Nome do tipo de documento
	 * @apiSuccess {String} retornoConsultaDocumento.numero Número do documento
	 * @apiSuccess {Data} retornoConsultaDocumento.data Data de geração para documentos internos e para documentos externos é a data informada na tela de cadastro
	 * @apiSuccess {Unidade} retornoConsultaDocumento.unidadeElaboradora Dados da unidade que gerou o documento
	 * @apiSuccess {String} retornoConsultaDocumento.unidadeElaboradora.descricao Nome da unidade
	 * @apiSuccess {String} retornoConsultaDocumento.unidadeElaboradora.idUnidade Código da unidade
	 * @apiSuccess {String} retornoConsultaDocumento.unidadeElaboradora.unidade.sigla Sigla da unidade
	 * @apiSuccess {Andamento} retornoConsultaDocumento.andamentoGeracao Informações do andamento de geração (opcional)
	 * @apiSuccess {Data} retornoConsultaDocumento.andamentoGeracao.dataHora Data e hora do registro de andamento
	 * @apiSuccess {String} retornoConsultaDocumento.andamentoGeracao.descricao Descrição do andamento
	 * @apiSuccess {Unidade} retornoConsultaDocumento.andamentoGeracao.unidade Unidade responsável pelo andamento
	 * @apiSuccess {String} retornoConsultaDocumento.andamentoGeracao.unidade.descricao Nome da unidade
	 * @apiSuccess {String} retornoConsultaDocumento.andamentoGeracao.unidade.idUnidade Código da unidade
	 * @apiSuccess {String} retornoConsultaDocumento.andamentoGeracao.unidade.sigla Sigla da unidade
	 * @apiSuccess {Usuario} retornoConsultaDocumento.andamentoGeracao.usuario Usuário responsável pela ação
	 * @apiSuccess {String} retornoConsultaDocumento.andamentoGeracao.usuario.idUsuario Código do usuário
	 * @apiSuccess {String} retornoConsultaDocumento.andamentoGeracao.usuario.nome Nome do usuário
	 * @apiSuccess {String} retornoConsultaDocumento.andamentoGeracao.usuario.sigla Login do usuário
	 * @apiSuccess {Assinatura} retornoConsultaDocumento.assinaturas Conjunto de assinaturas do documento
	 * @apiSuccess {String} retornoConsultaDocumento.assinaturas.cargoFuncao Cargo do responsável pela assinatura
	 * @apiSuccess {Data} retornoConsultaDocumento.assinaturas.dataHora Data e hora da assinatura
	 * @apiSuccess {String} retornoConsultaDocumento.assinaturas.nome Nome do assinante
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
	 *  	"publicacao":null  	
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
	@Path("{unidade}/documentos/{documento}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public RetornoConsultaDocumento consultarDocumento(@PathParam("unidade") String unidade, @PathParam("documento") String documento, 
			@QueryParam("andamento") String andamento, @QueryParam("assinaturas") String assinaturas, @QueryParam("publicacao") String publicacao) throws Exception{
		return seiNativeService.consultarDocumento(Constantes.SEI_BROKER, Operacao.CONSULTAR_DOCUMENTO, unidadeResource.consultarCodigo(unidade), documento, 
				getSOuN(andamento), getSOuN(assinaturas), getSOuN(publicacao));	
	}
	
	@Deprecated
	public RetornoConsultaDocumento consultarDocumentoSEI(String unidade, String documento, String andamento, String assinaturas, String publicacao) throws RemoteException{
		RetornoConsultaDocumento retornoConsultaDocumento = null;
		
		try{
			retornoConsultaDocumento = seiNativeService.consultarDocumento(Constantes.SEI_BROKER, Operacao.CONSULTAR_DOCUMENTO, unidadeResource.consultarCodigo(unidade), documento, 
					getSOuN(andamento), getSOuN(assinaturas), getSOuN(publicacao));
		}catch(Exception ex){
			logger.error(ex);
			ex.printStackTrace();
		}
		
		return retornoConsultaDocumento;				
	}
	
	@Deprecated
	public DocumentoSipar consultarDocumentoSIPAR(String documento, String ano, String digito){
		return documentoSiparDAO.getDocumento(documento, ano, digito);
	}
	
	
	/**
	 * @api {post} /:unidade/documentos Incluir documento
	 * @apiName incluirDocumento
	 * @apiGroup Documento
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza a inclusão de um documento.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String = "G (Gerado)","R (Recebido)"} tipo Tipo de documento
	 * @apiParam {String} idSerie Identificador do tipo de documento no SEI (Consultar serviço Listar Séries)
	 * @apiParam {String} idProcedimento Número do processo onde o documento será incluído
	 * @apiParam {String} [numero] Número do documento, passar null para documentos gerados com numeração controlada pelo SEI.Para documentos externos informar o número ou nome complementar a ser exibido na árvore de documentos do processo (o SEI não controla numeração de documentos externos). 
	 * @apiParam {String} [data] Data do documento, obrigatório para documentos externos. Passar null para documentos gerados
	 * @apiParam {String} [descricao] Descrição do documento para documentos gerados. Passar null para documentos externos
	 * @apiParam {Remetente} [remetente] Obrigatório para documentos externos, passar null para documentos gerados.
	 * @apiParam {String} [remetente.nome] Nome do remetente
	 * @apiParam {String} [remetente.sigla] Login do remetente
	 * @apiParam {Interessado[]} [interessados] Informar um conjunto com os dados de interessados. Se não existirem interessados deve ser informado um conjunto vazio
	 * @apiParam {String} [interessados.nome] Nome do interessado
	 * @apiParam {String} [interessados.sigla] Login do interessado
	 * @apiParam {Destinatario[]} [destinatarios] Informar um conjunto com os dados de destinatários. Se não existirem destinatários deve ser informado um conjunto vazio
	 * @apiParam {String} [destinatarios.nome] Nome do destinatário
	 * @apiParam {String} [destinatarios.sigla] Login do destinatário
	 * @apiParam {String} [observacao] Texto da observação da unidade, passar null se não existir
	 * @apiParam {String} [nomeArquivo] Nome do arquivo, obrigatório para documentos externos. Passar null para documentos gerados
	 * @apiParam {String} conteudo Conteúdo do arquivo codificado em Base64. Para documentos gerados será o conteúdo da seção principal do editor HTML e para documentos externos será o conteúdo do anexo.
	 * @apiParam {String = "0 (público)","1 (restrito)", "2 (sigiloso)", "null (herda do processo)"} [nivelAcesso] Nível de acesso do documento
	 *
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://anshmjboss01/sei-broker/service/COSAP/documentos
	 *
	 *	body:
	 *	{
	 *		"tipo":"G",
	 *		"idProcedimento":null,
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
	 * @apiSuccess {String} idDocumento número interno do documento
	 * @apiSuccess {String} documentoFormatado número do documento visível para o usuário
	 * @apiSuccess {String} linkAcesso link para acesso ao documento
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
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
	public RetornoInclusaoDocumento incluirDocumento(@PathParam("unidade") String unidade, Documento documento) throws RemoteException, Exception{
		RetornoInclusaoDocumento retorno = null;
					
		InclusaoDocumento inclusaoDocumento = registrarInclusao(documento, unidade);

		try{
			retorno = seiNativeService.incluirDocumento(Constantes.SEI_BROKER, Operacao.INCLUIR_DOCUMENTO,  unidadeResource.consultarCodigo(unidade), documento);			
		}catch(Exception ex){
			registrarProblemaInclusao(inclusaoDocumento);
			throw ex;
		}

		inclusaoDocumento.setNumero(retorno.getDocumentoFormatado());
		
		confirmarInclusao(inclusaoDocumento);

		return retorno;
	}
		
	/**
	 * @api {post} /:unidade/documentos/cancelados Cancelar documento
	 * @apiName cancelarDocumento
	 * @apiGroup Documento
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Cancela um documento.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI	
	 * @apiParam {String} documento Numero do documento que será cancelado
	 * @apiParam {String} motivo Motivo do cancelamento
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: http://anshmjboss01/sei-broker/service/COSAP/documentos/cancelados
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
		String resultado = seiNativeService.cancelarDocumento(Constantes.SEI_BROKER, Operacao.CANCELAR_DOCUMENTO, unidadeResource.consultarCodigo(unidade), 
				cancelamento.getDocumento(), cancelamento.getMotivo());
			
		return trueOrFalse(resultado) + "";
	}
	
	/**
	 * @api {get} /:unidade/documentos/ Listar documentos
	 * @apiName consultarDocumentosIncluidosBroker
	 * @apiGroup Documento
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Retorna informações dos documentos inclusos pelo SEI-Broker.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI	
	 * @apiParam {String} [pagina] Número da página
	 * @apiParam {String} [qtdRegistros = 50] Quantidade de registros que serão exibidos por página
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://anshmjboss01/sei-broker/service/COSAP/documentos?pagina=1&qtdRegsitros=30
	 *
	 * @apiSuccess {InclusaoDocumento} resultado Objeto com dados sobre o documento
	 * @apiSuccess {Date} resultado.data Data do envio (padrão ISO-8601)
	 * @apiSuccess {String} resultado.hash Hash SHA-256 gerado a partir do base64 enviado ao Broker
	 * @apiSuccess {Integer} resultado.id Identificação da inclusão de documento
	 * @apiSuccess {String} resultado.nome Nome do documento incluído
	 * @apiSuccess {String} resultado.numero Número retonado pelo SEI, NULL caso tenha ocorrido algum problema.
	 * @apiSuccess {String} resultado.processo Número do processo
	 * @apiSuccess {String} resultado.sistema Sistema responsável pela inclusão
	 * @apiSuccess {String} resultado.unidade Unidade onde foi incluído o documento
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
	 *       "processo": "33910000097201612"
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
	@Path("{unidade}/documentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public List<InclusaoDocumento> consultarDocumentosIncluidosBroker(@PathParam("unidade") String unidade, @QueryParam("pagina") String pagina, @QueryParam("qtdRegistros") String qtdRegistros) throws Exception{
		return daoInclusaoDocumentImp.getDocumentosInclusos(unidade, null, 
				pagina == null? null:parseInt(pagina), qtdRegistros == null? null : parseInt(qtdRegistros));		
	}

	/** 
	 * @api {get} /:unidade/documentos/:documento/pdf Exportar documento
	 * @apiName exportarDocumento
	 * @apiGroup Documento
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Exporta documentos do SEI em PDF.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} documento Id do documento que deseja recuperar as informações
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://anshmjboss01/sei-broker/service/COSIT/documentos/0003322/pdf
	 *
	 * @apiSuccess {PDF} binario Arquivo no formato PDF.
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/documentos/{documento}/pdf")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response exportarDocumento(@PathParam("unidade") String unidade, @PathParam("documento") String documento) throws Exception{
		RetornoConsultaDocumento retorno = seiNativeService.consultarDocumento(Constantes.SEI_BROKER, Operacao.CONSULTAR_DOCUMENTO, unidadeResource.consultarCodigo(unidade), documento, 
				"N", "N", "N");
				
		String linkAcesso = retorno.getLinkAcesso();
		
		URL url = new URL(linkAcesso);
		URLConnection con = url.openConnection();
		
		String contentType = con.getHeaderField("Content-Type");
		
		InputStream in = con.getInputStream();
		
		byte[] bytes = IOUtils.toByteArray(in);				
			
		if(isHTML(contentType)){			
			validarAssinaturaDocumento(bytes, documento);
			
			bytes = getPDF(bytes);
		}else if(isNotPDF(contentType)){
			throw new BusinessException("Documento "+documento+" não suportado. Content-Type "+contentType+" ainda não suportado.");
		}
		
	    return Response.ok(bytes).header("Content-Disposition", "attachment; filename=" +documento+ ".pdf").build();

	}
	
	public Long contarInclusoesDocumento(String unidade, String sistema){
		return daoInclusaoDocumentImp.countDocumentosInclusos(unidade, sistema);
	}
	
	public InclusaoDocumento registrarInclusao(Documento documento, String unidade) throws Exception{
		InclusaoDocumento registro = new InclusaoDocumento(documento, unidade, getSistemaResponsavel(),	calcularHashDocumento(documento.getConteudo()));

		try{			
			daoInclusaoDocumento.persistSemJTA(registro);
		}catch(Exception ex){
			logger.error("Ocorreu um erro ao persistir o registro de inclusão.",ex);
			throw new Exception("Ocorreu um erro no SEI-Broker, contacte a equipe responsável.");
		}
		
		return registro;
	}
	
	public void registrarProblemaInclusao(InclusaoDocumento inclusaoDocumento){
		inclusaoDocumento.setNumero("Ocorreu um erro no SEI.");
		
		daoInclusaoDocumento.merge(inclusaoDocumento);
	}
	
	public void confirmarInclusao(InclusaoDocumento inclusaoDocumento){
		daoInclusaoDocumento.merge(inclusaoDocumento);
	}
	
	public String calcularHashDocumento(String input){
		try {
			return HashUtils.toSHA256(input, null);
		} catch (Exception ex) {
			logger.error("Erro ao calcular hash.", ex);
			return "Erro ao calcular hash.";
		}		
	}
	
	public String getSistemaResponsavel(){
		try{
			String usuario = securityContext.getUserPrincipal().getName();
			return StringUtils.substringBefore(usuario, "@");
		}catch (Exception ex) {
			logger.error("Sem informações do sistema.", ex);
			return "Sem informações do sistema.";
		}
	}
	
	public void validarAssinaturaDocumento(byte[] bytes, String documento) throws BusinessException{
		String body = new String(bytes);

		String erroDocumentoNaoAssinado = "Documento "+documento+" ainda não foi assinado.";
		
		if(body.contains(erroDocumentoNaoAssinado)){
			throw new BusinessException(erroDocumentoNaoAssinado);
		}
	}
	
	public boolean isHTML(String contentType){
		return contentType.toLowerCase().equals("text/html; charset=iso-8859-1");
	}
	
	public boolean isNotPDF(String contentType){
		return !contentType.toLowerCase().equals("application/pdf");
	}
}
