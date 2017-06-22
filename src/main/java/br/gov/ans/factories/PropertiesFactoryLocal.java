package br.gov.ans.factories;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.gov.ans.factories.qualifiers.Local;



/**
 * Obtém propriedades locais. 
 *
 */
@ApplicationScoped
public class PropertiesFactoryLocal extends PropertiesFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Produces
	@Local @Default
	@Override
	public Properties produceProperties(InjectionPoint injectionPoint) throws IOException{
		return super.produceProperties(injectionPoint);
	}

	@Produces
	@Local @Default
	@Override
	public String produceProperty(InjectionPoint injectionPoint) throws IOException{
		return super.produceProperty(injectionPoint);
	}
	
	@Override
	protected Properties getProperties(String file) throws IOException{		
		Properties props = new Properties();
		
		props.load(PropertiesFactoryLocal.class.getClassLoader().getResourceAsStream(file));

		return props;
	}

}