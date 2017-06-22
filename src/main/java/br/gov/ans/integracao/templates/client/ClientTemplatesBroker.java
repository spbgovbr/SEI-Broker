package br.gov.ans.integracao.templates.client;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ErrorMessage;
import br.gov.ans.factories.qualifiers.Autenticado;
import br.gov.ans.factories.qualifiers.SeiQualifiers.TemplatesBrokerParameter;

public class ClientTemplatesBroker {
	
	@Inject
	@TemplatesBrokerParameter
	private String uri;
	
	@Inject
	@Autenticado("sei.broker")
	private Client cliente;
	
	public String getTemplate(String identificador) throws BusinessException{
		Response response = cliente.target(uri + "templates")
			.path(identificador)
			.path("corpo")
			.request().get();
		
		if(response.getStatus() == Status.OK.getStatusCode()){
			return response.readEntity(String.class);
		}else{
			ErrorMessage erroMessage = response.readEntity(ErrorMessage.class);
			
			throw new BusinessException(erroMessage.getError());
		}
	}
}
