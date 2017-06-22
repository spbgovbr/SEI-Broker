package br.gov.ans.exceptions.handlers;

import static br.gov.ans.utils.HttpHeadersUtil.getAcceptType;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import br.gov.ans.exceptions.ErrorMessage;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException>{

	@Inject
	private Logger logger;
	
	@Context
	private HttpHeaders headers;
	
	@Override
	public Response toResponse(NotFoundException ex) {
		logger.error(ex, ex);

		return Response.status(Status.NOT_FOUND)
				.entity(new ErrorMessage(ex.getMessage(),String.valueOf(Status.NOT_FOUND.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}
}
