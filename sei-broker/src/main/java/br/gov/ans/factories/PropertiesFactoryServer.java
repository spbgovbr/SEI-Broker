package br.gov.ans.factories;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.ans.factories.qualifiers.Server;



/**
 * Obtém propriedades externas do jboss. 
 *
 */

@ApplicationScoped
public class PropertiesFactoryServer extends PropertiesFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String SERVER_PATH = System.getProperty("jboss.home.dir") + "/ans/properties/";

	@Produces
	@Server
	public Properties produceProperties(InjectionPoint injectionPoint) throws IOException{
		return super.produceProperties(injectionPoint);
	}

	@Produces
	@Server
	public String produceProperty(InjectionPoint injectionPoint) throws IOException{
		return super.produceProperty(injectionPoint);
	}
	
	@Override
	protected Properties getProperties(String file) throws IOException{
		Properties props = new Properties();
		
		FileInputStream f = new FileInputStream(SERVER_PATH + file);
	    props.load(f);
		
		return props;
	}

}