package br.gov.ans.factories;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.factories.qualifiers.PropertiesInfo;


public abstract class PropertiesFactory {

	protected Properties produceProperties(InjectionPoint injectionPoint) throws IOException{
		Annotated annotated = injectionPoint.getAnnotated();
		PropertiesInfo info = annotated.getAnnotation(PropertiesInfo.class);
		
		return getProperties(info.file());
	}

	protected String produceProperty(InjectionPoint injectionPoint) throws IOException{
		Annotated annotated = injectionPoint.getAnnotated();
		PropertiesInfo info = annotated.getAnnotation(PropertiesInfo.class);
		
		if(StringUtils.isBlank(info.key())){
			return null;
		}
		
		Properties props = getProperties(info.file());

		return props.getProperty(info.key());
	}
	
	abstract Properties getProperties(String file) throws IOException;

}