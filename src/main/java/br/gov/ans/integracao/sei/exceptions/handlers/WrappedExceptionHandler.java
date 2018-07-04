package br.gov.ans.integracao.sei.exceptions.handlers;

import static br.gov.ans.utils.HttpHeadersUtil.getAcceptType;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.exceptions.ErrorMessage;
import br.gov.ans.integracao.sei.exceptions.WrappedException;

@Provider
public class WrappedExceptionHandler implements ExceptionMapper<WrappedException>{

	@Inject
	private Logger logger;
	
	@Context
	private HttpHeaders headers;
	
	@Override
	public Response toResponse(WrappedException ex) {
		logger.error(ex.getEx());
		
		logger.debug(ex.getEx(), ex.getEx());
		
		Status status = Status.INTERNAL_SERVER_ERROR;
		
		if(ex.getEx() instanceof BusinessException){
			status = Status.BAD_REQUEST;
		}
		
		return Response.status(status)
				.entity(new ErrorMessage(ex.getEx().getMessage(),String.valueOf(status.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}

}
