package br.gov.ans.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
public class SessionInvalidateFilter implements ContainerResponseFilter{

	@Context
	private HttpServletRequest request;
	
	@Inject
	private Logger logger;
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		try{			
			request.getSession().invalidate();			
		}catch(Exception ex){
			logger.error(ex);
		}
	}	
}
