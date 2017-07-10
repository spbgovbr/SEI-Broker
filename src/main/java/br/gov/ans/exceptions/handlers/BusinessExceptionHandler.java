package br.gov.ans.exceptions.handlers;

import static br.gov.ans.utils.HttpHeadersUtil.getAcceptType;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ErrorMessage;

@Provider
public class BusinessExceptionHandler implements ExceptionMapper<BusinessException>{

	@Inject
	private Logger logger;

	@Context
	private HttpHeaders headers;
	
	public Response toResponse(BusinessException ex) {
		logger.error(ex, ex);		
		 		
		return Response.status(Status.BAD_REQUEST)
				.entity(new ErrorMessage(ex.getMessage(),String.valueOf(Status.BAD_REQUEST.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}
}
