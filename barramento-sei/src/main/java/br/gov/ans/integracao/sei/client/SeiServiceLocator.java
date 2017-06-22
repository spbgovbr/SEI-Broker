/**
 * SeiServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.ans.integracao.sei.client;

import java.net.MalformedURLException;

import javax.inject.Inject;

import br.gov.ans.factories.qualifiers.PropertiesInfo;
import br.gov.ans.factories.qualifiers.Server;

public class SeiServiceLocator extends org.apache.axis.client.Service implements br.gov.ans.integracao.sei.client.SeiService {

	private static final long serialVersionUID = 1L;

    @Inject
    @PropertiesInfo(file="services.properties", key="sei.ws.uri")
    @Server
    private String SeiPortService_address;
	
	public SeiServiceLocator() {
    }

    public SeiServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SeiServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    public java.lang.String getSeiPortServiceAddress() {
        return SeiPortService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SeiPortServiceWSDDServiceName = "SeiPortService";

    public java.lang.String getSeiPortServiceWSDDServiceName() {
        return SeiPortServiceWSDDServiceName;
    }

    public void setSeiPortServiceWSDDServiceName(java.lang.String name) {
        SeiPortServiceWSDDServiceName = name;
    }
        
    public br.gov.ans.integracao.sei.client.SeiPortType getSeiPortService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SeiPortService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSeiPortService(endpoint);
    }
    
    public br.gov.ans.integracao.sei.client.SeiPortType getSeiPortService(String endpoint) throws javax.xml.rpc.ServiceException, MalformedURLException {
    	return getSeiPortService(new java.net.URL(endpoint));    
    }
    
    public br.gov.ans.integracao.sei.client.SeiPortType getSeiPortService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            br.gov.ans.integracao.sei.client.SeiBindingStub _stub = new br.gov.ans.integracao.sei.client.SeiBindingStub(portAddress, this);
            _stub.setPortName(getSeiPortServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSeiPortServiceEndpointAddress(java.lang.String address) {
        SeiPortService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (br.gov.ans.integracao.sei.client.SeiPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                br.gov.ans.integracao.sei.client.SeiBindingStub _stub = new br.gov.ans.integracao.sei.client.SeiBindingStub(new java.net.URL(SeiPortService_address), this);
                _stub.setPortName(getSeiPortServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SeiPortService".equals(inputPortName)) {
            return getSeiPortService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("Sei", "SeiService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("Sei", "SeiPortService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SeiPortService".equals(portName)) {
            setSeiPortServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
