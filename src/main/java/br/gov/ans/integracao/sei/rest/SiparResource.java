package br.gov.ans.integracao.sei.rest;

import java.net.URI;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ResourceConflictException;
import br.gov.ans.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sei.dao.SiparDAO;
import br.gov.ans.integracao.sipar.dao.ControleMigracao;
import br.gov.ans.integracao.sipar.dao.ControleMigracaoId;
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
    
	/**
     * @api {post} /sipar/importados Importar Processo
     * @apiName importarProcesso
     * @apiGroup SIPAR
     * @apiVersion 2.0.0
     *
     * @apiDescription Marca um processo físico (SIPAR) como importado para um processo eletrônico (SEI).
     *    
	 * @apiParam (Header Parameters) {String} content-type Informar text/plain
	 * @apiParam (Request Body) {String} processo Número do processo a ser importado
     * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://<host>/sei-broker/service/sipar/importados
	 *
	 *	body:
	 *	33902112492200241
     * 
     * @apiSuccessExample {json} Success-Response:
     *  HTTP/1.1 201 Created
     *
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 500 Internal Server Error
     * {
     * 		"error":"Mensagem de erro."
     *		"code":"código do erro"
     * }
     * 
     *
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 400 Bad Request
     * {
     * 		"error":"Mensagem de erro."
     *		"code":"código do erro"
     * }
     * 
     *
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 409 Conflict
     * {
     * 		"error":"Mensagem de erro."
     *		"code":"código do erro"
     * }
     */
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
	
	/**
     * @api {delete} /sipar/importados/:processo Cancelar Importação Processo
     * @apiName cancelarImportacaoProcesso
     * @apiGroup SIPAR
     * @apiVersion 2.0.0
     *
     * @apiDescription Desmarca um processo físico (SIPAR) como importado para um processo eletrônico (SEI).
     *        
     * @apiParam (Path Parameter) {String} processo Número do processo físico existente no SIPAR contendo 17 dígitos e iniciado com 33902. Ex. 33902111111111111
     * 
     * @apiExample {curl} Exemplo de requisição:
     *        curl -X DELETE http://<host>/sei-broker/service/sipar/importados/33902112492200241
     * 
     * @apiSuccessExample {json} Success-Response:
     *  HTTP/1.1 204 No Content
     *
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 500 Internal Server Error
     * {
     * 		"error":"Mensagem de erro."
     *		"code":"código do erro"
     * }
     * 
     *
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 400 Bad Request
     * {
     * 		"error":"Mensagem de erro."
     *		"code":"código do erro"
     * }
     * 
     *
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     * 		"error":"Mensagem de erro."
     *		"code":"código do erro"
     * }
     */
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
