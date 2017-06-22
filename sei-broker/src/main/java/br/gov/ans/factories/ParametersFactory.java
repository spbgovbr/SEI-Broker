package br.gov.ans.factories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.gov.ans.factories.qualifiers.PropertiesInfo;
import br.gov.ans.factories.qualifiers.SeiQualifiers.SeiParameter;
import br.gov.ans.factories.qualifiers.SeiQualifiers.SipParameter;
import br.gov.ans.factories.qualifiers.Server;

@ApplicationScoped
public class ParametersFactory {

    @Inject
    @PropertiesInfo(file="services.properties", key="sei.ws.uri")
    @Server
    private String seiUri;
    
    @Inject
    @PropertiesInfo(file="services.properties", key="sip.ws.uri")
    @Server
    private String sipUri;
    
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
}
