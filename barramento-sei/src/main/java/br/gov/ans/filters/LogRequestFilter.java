package br.gov.ans.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.core.ResourceMethodInvoker;

import br.gov.ans.utils.LogIntegracaoUtil;

@Provider
public class LogRequestFilter implements ContainerRequestFilter{

	@Inject
	private Logger logger;
	
    @Inject
    private LogIntegracaoUtil audit;
    
	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpServletRequest request;
	
	@Context
	private SecurityContext securityContext;

	@Override
	public void filter(ContainerRequestContext context) throws IOException{
		request.setCharacterEncoding("UTF-8");
		
		audit.registrarLog(getUserName(),uriInfo.getAbsolutePath().toString(), getMethodName(context));		
	}
	
	public String getMethodName(ContainerRequestContext context){
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) context.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
               
		return methodInvoker.getMethod().getName(); 
	}
	
	public String getUserName(){
		try{
			return securityContext.getUserPrincipal().getName();
		}catch (Exception e) {
			return "Sem informações do usuário.";
		}
	}
	
	
//	private void printRequest(HttpServletRequest httpRequest) {
//		logger.info(" \n\n Headers");
//
//	    Enumeration headerNames = httpRequest.getHeaderNames();
//	    while(headerNames.hasMoreElements()) {
//	        String headerName = (String)headerNames.nextElement();
//	        logger.info(headerName + " = " + httpRequest.getHeader(headerName));
//	    }
//
//	    logger.info("\n\nParameters");
//
//	    Enumeration params = httpRequest.getParameterNames();
//	    while(params.hasMoreElements()){
//	        String paramName = (String)params.nextElement();
//	        logger.info(paramName + " = " + httpRequest.getParameter(paramName));
//	    }
//
//	    logger.info("\n\n Row data");
//	    logger.info(extractPostRequestBody(httpRequest));
//	}
//
//	static String extractPostRequestBody(HttpServletRequest request) {
//	    if ("POST".equalsIgnoreCase(request.getMethod())) {
//	        Scanner s = null;
//	        try {
//	            s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	        return s.hasNext() ? s.next() : "";
//	    }
//	    return "";
//	}
}
