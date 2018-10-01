package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.getSOuN;
import static br.gov.ans.integracao.sei.utils.Util.parseInt;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;
import static br.gov.ans.utils.PDFUtil.getPDF;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.List;

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
import org.jboss.logging.Logger;

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

	@GET
	@Path("{unidade}/documentos/{documento:\\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public RetornoConsultaDocumento consultarDocumento(@PathParam("unidade") String unidade, @PathParam("documento") String documento, @QueryParam("andamento") String andamento,
			@QueryParam("assinaturas") String assinaturas, @QueryParam("publicacao") String publicacao, @QueryParam("campos") String campos) throws Exception{
		return seiNativeService.consultarDocumento(Constantes.SEI_BROKER, Operacao.CONSULTAR_DOCUMENTO, unidadeResource.consultarCodigo(unidade), documento, 
				getSOuN(andamento), getSOuN(assinaturas), getSOuN(publicacao), getSOuN(campos));	
	}

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

	@GET
	@Path("interessados/{interessado}/documentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})	
	public Response consultarDocumentosDoInteressado(@PathParam("interessado") String interessado, @QueryParam("tipo") String tipo, @QueryParam("pagina") String pagina, 
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
