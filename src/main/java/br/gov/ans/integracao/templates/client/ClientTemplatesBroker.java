package br.gov.ans.integracao.templates.client;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.jboss.logging.Logger;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ErrorMessage;
import br.gov.ans.factories.qualifiers.Autenticado;
import br.gov.ans.factories.qualifiers.SeiQualifiers.TemplatesBrokerParameter;
import br.gov.ans.integracao.sei.utils.Constantes;

public class ClientTemplatesBroker {
	
	@Inject
	@TemplatesBrokerParameter
	private String uri;
	
	@Inject
	@Autenticado("sei.broker")
	private Client cliente;
	
	@Inject
	private Logger logger;
	
	public String getTemplate(String identificador) throws Exception{
		try{			
			Response response = cliente.target(uri + "templates")
				.path(identificador)
				.path("corpo")
				.request().get();
			
			if(response.getStatusInfo().getFamily() != Family.SUCCESSFUL){
				errorHandling(response);
			}
			
			String corpoTemplate = response.readEntity(String.class);
			
			response.close();
			
			return corpoTemplate;
		}catch(ProcessingException ex){
			logger.error(ex);
			logger.debug(ex, ex);
			throw new Exception("Erro ao carregar o template, contacte a equipe responsável.");
		}
	}
	
	public void errorHandling(Response response) throws Exception{
		if(!response.getHeaderString(Constantes.CONTENT_TYPE_HEADER_KEY).equals(MediaType.APPLICATION_JSON)){
			logger.error(response.readEntity(String.class));
			
			response.close();
			throw new Exception("Erro ao carregar o template, contacte a equipe responsável.");
		}
		
		ErrorMessage erroMessage = response.readEntity(ErrorMessage.class);
		
		response.close();
		
		throw new BusinessException(erroMessage.getError());
	}
		
}
