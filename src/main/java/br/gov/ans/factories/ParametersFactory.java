package br.gov.ans.factories;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.gov.ans.factories.qualifiers.PropertiesInfo;
import br.gov.ans.factories.qualifiers.SeiQualifiers.SeiParameter;
import br.gov.ans.factories.qualifiers.SeiQualifiers.SipParameter;
import br.gov.ans.factories.qualifiers.SeiQualifiers.TemplatesBrokerParameter;
import br.gov.ans.factories.qualifiers.SeiQualifiers.UsersProperties;
import br.gov.ans.factories.qualifiers.Server;

@ApplicationScoped
public class ParametersFactory {

	@Inject
	@PropertiesInfo(file="ws-users.properties")
	@Server
	private Properties properties;
	
    @Inject
    @PropertiesInfo(file="services.properties", key="sei.ws.uri")
    @Server
    private String seiUri;
    
    @Inject
    @PropertiesInfo(file="services.properties", key="sip.ws.uri")
    @Server
    private String sipUri;
    
    @Inject
    @PropertiesInfo(file="services.properties", key="templates.broker.uri")
    @Server
    private String templatesBrokerUri;
    
    @Produces
    @SeiParameter
    public String getSeiUri(){
    	return seiUri;
    }

    @Produces
    @SipParameter
    public String getSipUri(){
    	return sipUri;
    }
    
    @Produces
    @TemplatesBrokerParameter
    public String getTemplatesBrokerUri(){
    	return templatesBrokerUri;
    }
    
    @Produces
    @UsersProperties
    public Properties getUsers(){
    	return properties;
    }
}
