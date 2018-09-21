package br.gov.ans.integracao.sei.helper;

import static br.gov.ans.integracao.sei.utils.Util.decodeConteudoMustache;
import static br.gov.ans.integracao.sei.utils.Util.encodeBase64;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.hibernate.engine.transaction.jta.platform.internal.TransactionManagerBasedSynchronizationStrategy;
import org.jboss.logging.Logger;

import com.github.mustachejava.Mustache;

import br.gov.ans.integracao.sei.client.Documento;
import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;
import br.gov.ans.utils.MustacheUtils;

public class DocumentoHelper {
	
    @Inject
    private MessageUtils messages;
	
	@Inject 
	private InteressadoHelper interessadoHelper;
	
	@Inject
	private Logger logger;
	
	@Inject
	private MustacheUtils mustacheUtils;
	
	public void validarDocumento(Documento documento, String template) throws BusinessException, RemoteException, Exception{
		if(documento == null){
			throw new BusinessException(messages.getMessage("erro.dados.documento.nao.informados"));
		}
		
		validarNumeroProcesso(documento);
		validarNomeArquivo(documento);
		validarNumeroInformado(documento);
		validarTamanhoConteudo(documento);
		validarInteressados(documento);
		
		if(StringUtils.isNotEmpty(template)){
			processarPreenchimentoTemplate(documento, template);
		}
	}
	
	private void processarPreenchimentoTemplate(Documento documento, String template) throws RemoteException, Exception{
		validarInclusaoComTemplate(documento);
		
		transformarConteudoDocumentoInterno(documento, template);
	}
	
	private void validarNumeroProcesso(Documento documento) throws BusinessException{
		if(StringUtils.isBlank(documento.getIdProcedimento())){
			throw new BusinessException(messages.getMessage("erro.documento.sem.processo"));
		}
	}
	
	private void validarNomeArquivo(Documento documento) throws BusinessException{
		if(StringUtils.length(documento.getNomeArquivo()) > 200){
			throw new BusinessException(messages.getMessage("erro.tamanho.nome.documento"));
		}
	}
	
	private void validarNumeroInformado(Documento documento) throws BusinessException{
		if(StringUtils.length(documento.getNumero()) > 50){
			throw new BusinessException(messages.getMessage("erro.tamanho.numero.informado"));
		}
	}
	
	private void validarInteressados(Documento documento) throws BusinessException{
		if(isSemInteressados(documento)){
			throw new BusinessException(messages.getMessage("erro.interessado.nao.informado"));
		}else{
			interessadoHelper.tratarInteressados(documento.getInteressados());			
		}
	}
	
	private void validarInclusaoComTemplate(Documento documento) throws BusinessException{
		if(documento.getTipo().equals(Constantes.DOCUMENTO_RECEBIDO)){
			throw new BusinessException(messages.getMessage("erro.template.documento.recebido"));
		}
	}
	
	private void validarTamanhoConteudo(Documento documento) throws BusinessException{
		if(documento.getConteudo() == null){
			return;
		}
			
		if(calcularBytes(documento.getConteudo().length()) > Constantes.TAMANHO_MAXIMO_DOCUMENTO){
			throw new BusinessException(messages.getMessage("erro.tamanho.documento"));
		}
	}
	
	private boolean isSemInteressados(Documento documento){
		return ArrayUtils.isEmpty(documento.getInteressados());
	}
	
	private double calcularBytes(int sizeBase64){		
		return sizeBase64 * 3.0 / 4;
	}
	
	private void transformarConteudoDocumentoInterno(Documento documento, String template) throws RemoteException, Exception{
		StringWriter writer = new StringWriter();

		try{
			Mustache mustache = mustacheUtils.compile(removeExtensaoLegado(template));	
			
			Map<String, Object> model = decodeConteudoMustache(documento.getConteudo());
			
			mustache.execute(writer, model);
			String html = writer.toString();
			
			documento.setConteudo(encodeBase64(html));
		}catch(JsonParseException ex){
			logger.debug(documento.getConteudo());
			throw new BusinessException(messages.getMessage("erro.processar.conteudo.json"));
		}finally{
			writer.close();
		}
	}
	
	private String removeExtensaoLegado(String template){
		if(template.contains(".mustache")){
			return StringUtils.remove(template, ".mustache");
		}
		
		return template;
	}
	
}
