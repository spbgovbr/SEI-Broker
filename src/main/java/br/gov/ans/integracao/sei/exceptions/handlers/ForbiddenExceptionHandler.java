package br.gov.ans.integracao.sei.exceptions.handlers;

import static br.gov.ans.utils.HttpHeadersUtil.getAcceptType;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.gov.ans.integracao.sei.exceptions.ErrorMessage;

@Provider
public class ForbiddenExceptionHandler implements ExceptionMapper<ForbiddenException>{

	@Inject
	private Logger logger;
	
	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(ForbiddenException ex) {
		logger.error(ex);
		
		logger.debug(ex, ex);		
		 		
		return Response.status(Status.FORBIDDEN)
				.entity(new ErrorMessage(ex.getMessage(),String.valueOf(Status.FORBIDDEN.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}
}
