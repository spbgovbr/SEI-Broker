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
import br.gov.ans.utils.MessageUtils;

@Provider
public class LogRequestFilter implements ContainerRequestFilter{

	@Inject
	private Logger logger;
	
    @Inject
    private LogIntegracaoUtil audit;
    
    @Inject
    private MessageUtils messages;
    
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
			return messages.getMessage("sem.informacoes.usuario");
		}
	}	

}
