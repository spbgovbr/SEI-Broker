/**
 * Marcador.java
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
public class Marcador implements java.io.Serializable {
	private java.lang.String idMarcador;

	private java.lang.String nome;

	private java.lang.String icone;

	private java.lang.String sinAtivo;

	public Marcador() {
	}

	public Marcador(java.lang.String idMarcador, java.lang.String nome, java.lang.String icone,
			java.lang.String sinAtivo) {
		this.idMarcador = idMarcador;
		this.nome = nome;
		this.icone = icone;
		this.sinAtivo = sinAtivo;
	}

	public java.lang.String getIdMarcador() {
		return idMarcador;
	}

	public void setIdMarcador(java.lang.String idMarcador) {
		this.idMarcador = idMarcador;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getIcone() {
		return icone;
	}

	public void setIcone(java.lang.String icone) {
		this.icone = icone;
	}

	public java.lang.String getSinAtivo() {
		return sinAtivo;
	}

	public void setSinAtivo(java.lang.String sinAtivo) {
		this.sinAtivo = sinAtivo;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Marcador))
			return false;
		Marcador other = (Marcador) obj;
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
				&& ((this.idMarcador == null && other.getIdMarcador() == null)
						|| (this.idMarcador != null && this.idMarcador.equals(other.getIdMarcador())))
				&& ((this.nome == null && other.getNome() == null)
						|| (this.nome != null && this.nome.equals(other.getNome())))
				&& ((this.icone == null && other.getIcone() == null)
						|| (this.icone != null && this.icone.equals(other.getIcone())))
				&& ((this.sinAtivo == null && other.getSinAtivo() == null)
						|| (this.sinAtivo != null && this.sinAtivo.equals(other.getSinAtivo())));
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
		if (getIdMarcador() != null) {
			_hashCode += getIdMarcador().hashCode();
		}
		if (getNome() != null) {
			_hashCode += getNome().hashCode();
		}
		if (getIcone() != null) {
			_hashCode += getIcone().hashCode();
		}
		if (getSinAtivo() != null) {
			_hashCode += getSinAtivo().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			Marcador.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Marcador"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idMarcador");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdMarcador"));
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
		elemField.setFieldName("icone");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Icone"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("sinAtivo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SinAtivo"));
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
