package br.gov.ans.factories;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.logging.Logger;

public class LoggerFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Produces
	public Logger produceLogger(InjectionPoint injectionPoint){		
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}
