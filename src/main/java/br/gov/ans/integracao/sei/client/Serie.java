/**
 * Serie.java
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
public class Serie implements java.io.Serializable {
	private java.lang.String idSerie;

	private java.lang.String nome;

	private java.lang.String aplicabilidade;

	public Serie() {
	}

	public Serie(java.lang.String idSerie, java.lang.String nome, java.lang.String aplicabilidade) {
		this.idSerie = idSerie;
		this.nome = nome;
		this.aplicabilidade = aplicabilidade;
	}

	public java.lang.String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(java.lang.String idSerie) {
		this.idSerie = idSerie;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getAplicabilidade() {
		return aplicabilidade;
	}

	public void setAplicabilidade(java.lang.String aplicabilidade) {
		this.aplicabilidade = aplicabilidade;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Serie))
			return false;
		Serie other = (Serie) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.idSerie == null && other.getIdSerie() == null)
						|| (this.idSerie != null && this.idSerie.equals(other.getIdSerie())))
				&& ((this.nome == null && other.getNome() == null)
						|| (this.nome != null && this.nome.equals(other.getNome())))
				&& ((this.aplicabilidade == null && other.getAplicabilidade() == null)
						|| (this.aplicabilidade != null && this.aplicabilidade.equals(other.getAplicabilidade())));
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
		if (getIdSerie() != null) {
			_hashCode += getIdSerie().hashCode();
		}
		if (getNome() != null) {
			_hashCode += getNome().hashCode();
		}
		if (getAplicabilidade() != null) {
			_hashCode += getAplicabilidade().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Serie.class,
			true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Serie"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idSerie");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdSerie"));
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
		elemField.setFieldName("aplicabilidade");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Aplicabilidade"));
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
