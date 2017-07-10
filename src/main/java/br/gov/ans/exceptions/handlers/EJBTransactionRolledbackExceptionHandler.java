package br.gov.ans.exceptions.handlers;

import static br.gov.ans.utils.HttpHeadersUtil.getAcceptType;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.gov.ans.exceptions.ErrorMessage;
import br.gov.ans.utils.MessageUtils;

@Provider
public class EJBTransactionRolledbackExceptionHandler implements ExceptionMapper<EJBTransactionRolledbackException>{

	@Inject
	private Logger logger;
	
	@Context
	private HttpHeaders headers;
	
    @Inject
    private MessageUtils messages;
	
	@Override
	public Response toResponse(EJBTransactionRolledbackException ex) {
		logger.error(ex, ex);
		
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new ErrorMessage(messages.getMessage("erro.inesperado"),String.valueOf(Status.INTERNAL_SERVER_ERROR.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}

}
