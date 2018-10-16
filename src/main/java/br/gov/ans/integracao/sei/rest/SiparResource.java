package br.gov.ans.integracao.sei.rest;

import java.net.URI;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.exceptions.ResourceConflictException;
import br.gov.ans.integracao.sei.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sipar.dao.SiparDAO;
import br.gov.ans.integracao.sipar.modelo.ControleMigracao;
import br.gov.ans.integracao.sipar.modelo.ControleMigracaoId;
import br.gov.ans.integracao.sipar.modelo.DocumentoSipar;
import br.gov.ans.utils.MessageUtils;

@Path("/sipar")
public class SiparResource {
	
	@Inject
	private SiparDAO dao;
	
    @Inject
    private MessageUtils messages;
    
    @Inject
    private Logger logger;
    
	@Context
	private UriInfo uriInfo;
	
	private static int TRUE = 1;
	private static int FALSE = 0;

	@POST
	@Path("/importados")
	@Consumes({MediaType.TEXT_PLAIN})
	public Response importarProcesso(String processo) throws Exception{ 
		String numero, ano, digito; 
		
		try{
			numero = extraiNumero(processo);
			ano = extraiAno(processo);
			digito = extraiDigitoVerificador(processo);			
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.numero.sipar"), ex);
			throw new BusinessException(messages.getMessage("erro.numero.sipar",processo));
		}
		
		if(!isProcessoSipar(numero, ano, digito)){
			throw new ResourceNotFoundException(messages.getMessage("erro.processo.nao.pertence.sipar", processo));			
		}
		
		if(isProcessoEmTramitacao(numero, ano)){
			throw new BusinessException(messages.getMessage("erro.processo.em.tramitacao.sipar", processo));
		}
		
		if(isProcessoImportado(numero, ano)){
			throw new ResourceConflictException(messages.getMessage("erro.processo.sipar.importado", processo));			
		}
		
		importarProcesso(numero, ano);
		
		return Response.created(getResourcePath(processo)).build();
		
	}

	@DELETE
	@Path("/importados/{processo:\\d+}")
	public Response cancelarImportacaoProcesso(@PathParam("processo") String processo) throws Exception{
		String numero, ano, digito; 
		
		try{
			numero = extraiNumero(processo);
			ano = extraiAno(processo);
			digito = extraiDigitoVerificador(processo);			
		}catch(Exception ex){
			logger.error(messages.getMessage("erro.numero.sipar"), ex);
			throw new BusinessException(messages.getMessage("erro.numero.sipar",processo));
		}
		
		if(!isProcessoImportado(numero, ano)){
			throw new ResourceNotFoundException(messages.getMessage("erro.processo.nao.importado",processo));
		}
		
		cancelarImportacaoProcesso(numero, ano);
		
		return Response.noContent().build();
		
	}
	
	@GET
	@Path("{processo:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoSipar consultarProcessoSIPAR(@PathParam("processo") String processo) throws BusinessException, ResourceNotFoundException{
		DocumentoSipar documento = consultarProcesso(processo);
		
		if(documento == null){
			throw new ResourceNotFoundException(messages.getMessage("erro.processo.nao.encontrado", processo));
		}
		
		return documento;
	}
	
	public DocumentoSipar consultarProcesso(String processo) throws BusinessException{
		String numero, ano, digito;

		try{
			numero = extraiNumero(processo);
			ano = extraiAno(processo);
			digito = extraiDigitoVerificador(processo);
		}catch(Exception ex){
			logger.error(ex);
			
			throw new BusinessException(messages.getMessage("erro.numero.sipar",processo));			
		}
		
		return dao.getDocumento(numero, ano, digito);
	}
	
	private void importarProcesso(String numeroDocumento, String anoDocumento){
		ControleMigracao controle = new ControleMigracao();
		controle.setId(getControleMigracaoID(numeroDocumento, anoDocumento));
		controle.setMigrado(TRUE);
		controle.setDataUltimaAlteracao(new Date());
		
		dao.merge(controle);
	}
	
	private void cancelarImportacaoProcesso(String numeroDocumento, String anoDocumento){
		ControleMigracao controle = new ControleMigracao();
		controle.setId(getControleMigracaoID(numeroDocumento, anoDocumento));
		controle.setMigrado(FALSE);
		controle.setDataUltimaAlteracao(new Date());
		
		dao.merge(controle);
	}
	
	private ControleMigracaoId getControleMigracaoID(String numeroDocumento, String anoDocumento){
		return new ControleMigracaoId(numeroDocumento, anoDocumento);
	}
	
	private String extraiNumero(String processo) {
		return processo.substring(0,(processo.length() - 6));
	}
	
	private String extraiAno(String processo) {
		return processo.substring((processo.length() - 6), (processo.length() - 2));
	}
	
	private String extraiDigitoVerificador(String processo) {
		return processo.substring((processo.length() - 2), processo.length());
	}
	
	public boolean isProcessoImportado(String numeroDocumento, String anoDocumento){
		ControleMigracao processoMigrado = dao.buscaProcessoImportado(getControleMigracaoID(numeroDocumento, anoDocumento));
		
		if(processoMigrado != null && processoMigrado.isProcessoMigrado()){
			return true;
		}
		
		return false;
	}
	
	public boolean isProcessoSipar(String numeroDocumento, String anoDocumento, String digitoDocumento) throws ResourceNotFoundException{
		if(dao.getDocumento(numeroDocumento, anoDocumento, digitoDocumento) == null){
			return false;
		}
		
		return true;
	}
	
	public boolean isProcessoEmTramitacao(String numero, String ano){
		return dao.isProcessoEmTramitacao(numero, ano);
	}
	
	public URI getResourcePath(String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}
}
