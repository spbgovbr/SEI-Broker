package br.gov.ans.integracao.sei.exceptions.handlers;

import static br.gov.ans.utils.HttpHeadersUtil.getAcceptType;

import java.net.UnknownHostException;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.gov.ans.integracao.sei.exceptions.ErrorMessage;

@Provider
public class UnknownHostExceptionHandler implements ExceptionMapper<UnknownHostException>{
	
	@Inject
	private Logger logger;

	@Context
	private HttpHeaders headers;
	
	public Response toResponse(UnknownHostException ex) {
		logger.error(ex);
		
		logger.debug(ex, ex);		
		 		
		return Response.status(Status.BAD_GATEWAY)
				.entity(new ErrorMessage("Não foi possível se conectar ao SEI.",String.valueOf(Status.BAD_GATEWAY.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}
}
