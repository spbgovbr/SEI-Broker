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

import org.apache.axis.AxisFault;
import org.jboss.logging.Logger;
import org.xml.sax.SAXException;

import br.gov.ans.integracao.sei.exceptions.ErrorMessage;

@Provider
public class AxisFaultHandler implements ExceptionMapper<AxisFault>{
	
	@Inject
	private Logger logger;
	
	@Context
	private HttpHeaders headers;
	
	@Inject
	private UnknownHostExceptionHandler unknownHostExceptionHandler;
	
	@Inject
	private SAXExceptionHandler sAXExceptionHandler;
	
	@Override
	public Response toResponse(AxisFault ex) {
		logger.error(ex);
		
		logger.debug(ex, ex);
		
		Throwable cause = ex.getCause();
		
		if(cause instanceof UnknownHostException){
			return unknownHostExceptionHandler.toResponse((UnknownHostException) cause);
		}
		
		if(cause instanceof SAXException){
			return sAXExceptionHandler.toResponse((SAXException) cause);
		}
				
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new ErrorMessage(ex.getMessage(),String.valueOf(Status.INTERNAL_SERVER_ERROR.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}
}
