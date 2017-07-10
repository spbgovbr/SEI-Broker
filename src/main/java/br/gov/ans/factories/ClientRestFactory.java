package br.gov.ans.factories;

import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import br.gov.ans.factories.qualifiers.Autenticado;
import br.gov.ans.factories.qualifiers.SeiQualifiers.UsersProperties;
import br.gov.ans.utils.Authenticator;


public class ClientRestFactory {

	@Inject
	@UsersProperties
	private Properties properties;
	
	
	/**
	 * Produtor de cliente Rest
	 * Qualifier @Auntenticado: utilizá-lo para autenticação 
	 * 
	 * 
	 * @param injectionPoint
	 * @return
	 * @throws PolicyContextException 
	 */
	
	@Produces
	public Client produceClient(InjectionPoint injectionPoint) throws PolicyContextException{
		Annotated annotated = injectionPoint.getAnnotated();
		Autenticado a = annotated.getAnnotation(Autenticado.class);
		
		if(a != null){
			String user = properties.getProperty(a.value()+".user");
			String password = properties.getProperty(a.value()+".password");
			
			HttpServletRequest request = (HttpServletRequest) PolicyContext.getContext(HttpServletRequest.class.getName());
			if(request.getUserPrincipal() != null){
				user += "@"+request.getUserPrincipal().getName();
			}

			return ClientBuilder.newClient().register(new Authenticator(user, password));
		} else{
			return ClientBuilder.newClient();
		}
	}

}
