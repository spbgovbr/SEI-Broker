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
import org.xml.sax.SAXException;

import br.gov.ans.exceptions.ErrorMessage;

@Provider
public class SAXExceptionHandler implements ExceptionMapper<SAXException>{
	
	@Inject
	private Logger logger;
	
	@Context
	private HttpHeaders headers;
	
	@Override
	public Response toResponse(SAXException ex) {
		logger.error(ex, ex);
		
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new ErrorMessage("Não foi possível se conectar ao SEI.",String.valueOf(Status.INTERNAL_SERVER_ERROR.getStatusCode())))
				.type(getAcceptType(headers))
				.build();
	}
}
