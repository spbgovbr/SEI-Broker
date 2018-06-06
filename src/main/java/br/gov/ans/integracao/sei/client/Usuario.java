/**
 * Usuario.java
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
public class Usuario implements java.io.Serializable {
	private java.lang.String idUsuario;

	private java.lang.String sigla;

	private java.lang.String nome;

	public Usuario() {
	}

	public Usuario(java.lang.String idUsuario, java.lang.String sigla, java.lang.String nome) {
		this.idUsuario = idUsuario;
		this.sigla = sigla;
		this.nome = nome;
	}

	public java.lang.String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(java.lang.String idUsuario) {
		this.idUsuario = idUsuario;
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

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Usuario)) {
			return false;
		}

		Usuario other = (Usuario) obj;

		if (this.getSigla().equals(other.getSigla())) {
			return true;
		}

		return false;
	}

	private boolean __hashCodeCalc = false;

	public int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getIdUsuario() != null) {
			_hashCode += getIdUsuario().hashCode();
		}
		if (getSigla() != null) {
			_hashCode += getSigla().hashCode();
		}
		if (getNome() != null) {
			_hashCode += getNome().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			Usuario.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Usuario"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idUsuario");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdUsuario"));
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
