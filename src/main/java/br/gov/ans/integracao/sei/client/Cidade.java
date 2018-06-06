/**
 * Cidade.java
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
public class Cidade implements java.io.Serializable {
	private java.lang.String idCidade;

	private java.lang.String idEstado;

	private java.lang.String idPais;

	private java.lang.String nome;

	private java.lang.String codigoIbge;

	private java.lang.String sinCapital;

	private java.lang.String latitude;

	private java.lang.String longitude;

	public Cidade() {
	}

	public Cidade(String ibge) {
		this.codigoIbge = ibge;
	}

	public Cidade(java.lang.String idCidade, java.lang.String idEstado, java.lang.String idPais, java.lang.String nome,
			java.lang.String codigoIbge, java.lang.String sinCapital, java.lang.String latitude,
			java.lang.String longitude) {
		this.idCidade = idCidade;
		this.idEstado = idEstado;
		this.idPais = idPais;
		this.nome = nome;
		this.codigoIbge = codigoIbge;
		this.sinCapital = sinCapital;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public java.lang.String getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(java.lang.String idCidade) {
		this.idCidade = idCidade;
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

	public java.lang.String getSinCapital() {
		return sinCapital;
	}

	public void setSinCapital(java.lang.String sinCapital) {
		this.sinCapital = sinCapital;
	}

	public java.lang.String getLatitude() {
		return latitude;
	}

	public void setLatitude(java.lang.String latitude) {
		this.latitude = latitude;
	}

	public java.lang.String getLongitude() {
		return longitude;
	}

	public void setLongitude(java.lang.String longitude) {
		this.longitude = longitude;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Cidade))
			return false;
		Cidade other = (Cidade) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.codigoIbge == null && other.getCodigoIbge() == null)
				|| (this.codigoIbge != null && this.codigoIbge.equals(other.getCodigoIbge())));
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
		if (getIdCidade() != null) {
			_hashCode += getIdCidade().hashCode();
		}
		if (getIdEstado() != null) {
			_hashCode += getIdEstado().hashCode();
		}
		if (getIdPais() != null) {
			_hashCode += getIdPais().hashCode();
		}
		if (getNome() != null) {
			_hashCode += getNome().hashCode();
		}
		if (getCodigoIbge() != null) {
			_hashCode += getCodigoIbge().hashCode();
		}
		if (getSinCapital() != null) {
			_hashCode += getSinCapital().hashCode();
		}
		if (getLatitude() != null) {
			_hashCode += getLatitude().hashCode();
		}
		if (getLongitude() != null) {
			_hashCode += getLongitude().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			Cidade.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Cidade"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idCidade");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdCidade"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
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
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("sinCapital");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SinCapital"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("latitude");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Latitude"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("longitude");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Longitude"));
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
