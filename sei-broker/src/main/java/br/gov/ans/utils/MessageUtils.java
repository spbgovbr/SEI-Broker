package br.gov.ans.utils;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.gov.ans.factories.qualifiers.PropertiesInfo;

@ApplicationScoped
public class MessageUtils implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApplicationScoped
	@Inject
	@PropertiesInfo(file="messages.properties")	
	private Properties properties;
    
	public MessageUtils(){		
	}
	
	public MessageUtils(Properties properties){
		this.properties = properties;
	}
	
	public static MessageUtils build() throws IOException{
		Properties properties = new Properties();
		properties.load(MessageUtils.class.getClassLoader().getResourceAsStream("messages.properties"));
		
		return new MessageUtils(properties);
	}
	
    public String getMessage(String key){
        return (String) properties.get(key);
    }
    
    public String getMessage(String key, Object... args){
        return MessageFormat.format(getMessage(key), args);
    }
}
