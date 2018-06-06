/**
 * Estado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.ans.integracao.sei.client;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Estado implements java.io.Serializable {
	private java.lang.String idEstado;

	private java.lang.String idPais;

	private java.lang.String sigla;

	private java.lang.String nome;

	private java.lang.String codigoIbge;

	public Estado() {
	}

	public Estado(String sigla) {
		this.sigla = sigla;
	}

	public Estado(java.lang.String idEstado, java.lang.String idPais, java.lang.String sigla, java.lang.String nome,
			java.lang.String codigoIbge) {
		this.idEstado = idEstado;
		this.idPais = idPais;
		this.sigla = sigla;
		this.nome = nome;
		this.codigoIbge = codigoIbge;
	}

	public java.lang.String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(java.lang.String idEstado) {
		this.idEstado = idEstado;
	}

	public java.lang.String getIdPais() {
		return idPais;
	}

	public void setIdPais(java.lang.String idPais) {
		this.idPais = idPais;
	}

	public java.lang.String getSigla() {
		return sigla;
	}

	public void setSigla(java.lang.String sigla) {
		this.sigla = sigla;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(java.lang.String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Estado))
			return false;
		Estado other = (Estado) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.sigla == null && other.getSigla() == null)
				|| (this.sigla != null && this.sigla.equals(other.getSigla())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getIdEstado() != null) {
			_hashCode += getIdEstado().hashCode();
		}
		if (getIdPais() != null) {
			_hashCode += getIdPais().hashCode();
		}
		if (getSigla() != null) {
			_hashCode += getSigla().hashCode();
		}
		if (getNome() != null) {
			_hashCode += getNome().hashCode();
		}
		if (getCodigoIbge() != null) {
			_hashCode += getCodigoIbge().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			Estado.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Estado"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idEstado");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdEstado"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idPais");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdPais"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("sigla");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Sigla"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("nome");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Nome"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("codigoIbge");
		elemField.setXmlName(new javax.xml.namespace.QName("", "CodigoIbge"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}

	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
