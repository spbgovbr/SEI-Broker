/**
 * PublicacaoImprensaNacional.java
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
public class PublicacaoImprensaNacional implements java.io.Serializable {
	private java.lang.String siglaVeiculo;

	private java.lang.String descricaoVeiculo;

	private java.lang.String pagina;

	private java.lang.String secao;

	private java.lang.String data;

	public PublicacaoImprensaNacional() {
	}

	public PublicacaoImprensaNacional(java.lang.String siglaVeiculo, java.lang.String descricaoVeiculo,
			java.lang.String pagina, java.lang.String secao, java.lang.String data) {
		this.siglaVeiculo = siglaVeiculo;
		this.descricaoVeiculo = descricaoVeiculo;
		this.pagina = pagina;
		this.secao = secao;
		this.data = data;
	}

	public java.lang.String getSiglaVeiculo() {
		return siglaVeiculo;
	}

	public void setSiglaVeiculo(java.lang.String siglaVeiculo) {
		this.siglaVeiculo = siglaVeiculo;
	}

	public java.lang.String getDescricaoVeiculo() {
		return descricaoVeiculo;
	}

	public void setDescricaoVeiculo(java.lang.String descricaoVeiculo) {
		this.descricaoVeiculo = descricaoVeiculo;
	}

	public java.lang.String getPagina() {
		return pagina;
	}

	public void setPagina(java.lang.String pagina) {
		this.pagina = pagina;
	}

	public java.lang.String getSecao() {
		return secao;
	}

	public void setSecao(java.lang.String secao) {
		this.secao = secao;
	}

	public java.lang.String getData() {
		return data;
	}

	public void setData(java.lang.String data) {
		this.data = data;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof PublicacaoImprensaNacional))
			return false;
		PublicacaoImprensaNacional other = (PublicacaoImprensaNacional) obj;
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
				&& ((this.siglaVeiculo == null && other.getSiglaVeiculo() == null)
						|| (this.siglaVeiculo != null && this.siglaVeiculo.equals(other.getSiglaVeiculo())))
				&& ((this.descricaoVeiculo == null && other.getDescricaoVeiculo() == null)
						|| (this.descricaoVeiculo != null && this.descricaoVeiculo.equals(other.getDescricaoVeiculo())))
				&& ((this.pagina == null && other.getPagina() == null)
						|| (this.pagina != null && this.pagina.equals(other.getPagina())))
				&& ((this.secao == null && other.getSecao() == null)
						|| (this.secao != null && this.secao.equals(other.getSecao())))
				&& ((this.data == null && other.getData() == null)
						|| (this.data != null && this.data.equals(other.getData())));
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
		if (getSiglaVeiculo() != null) {
			_hashCode += getSiglaVeiculo().hashCode();
		}
		if (getDescricaoVeiculo() != null) {
			_hashCode += getDescricaoVeiculo().hashCode();
		}
		if (getPagina() != null) {
			_hashCode += getPagina().hashCode();
		}
		if (getSecao() != null) {
			_hashCode += getSecao().hashCode();
		}
		if (getData() != null) {
			_hashCode += getData().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			PublicacaoImprensaNacional.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "PublicacaoImprensaNacional"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("siglaVeiculo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SiglaVeiculo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("descricaoVeiculo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DescricaoVeiculo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("pagina");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Pagina"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("secao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Secao"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("data");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Data"));
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
