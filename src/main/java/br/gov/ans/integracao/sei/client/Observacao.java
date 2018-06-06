/**
 * Observacao.java
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
public class Observacao implements java.io.Serializable {
	private java.lang.String descricao;

	private br.gov.ans.integracao.sei.client.Unidade unidade;

	public Observacao() {
	}

	public Observacao(java.lang.String descricao, br.gov.ans.integracao.sei.client.Unidade unidade) {
		this.descricao = descricao;
		this.unidade = unidade;
	}

	public java.lang.String getDescricao() {
		return descricao;
	}

	public void setDescricao(java.lang.String descricao) {
		this.descricao = descricao;
	}

	public br.gov.ans.integracao.sei.client.Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(br.gov.ans.integracao.sei.client.Unidade unidade) {
		this.unidade = unidade;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Observacao))
			return false;
		Observacao other = (Observacao) obj;
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
				&& ((this.descricao == null && other.getDescricao() == null)
						|| (this.descricao != null && this.descricao.equals(other.getDescricao())))
				&& ((this.unidade == null && other.getUnidade() == null)
						|| (this.unidade != null && this.unidade.equals(other.getUnidade())));
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
		if (getDescricao() != null) {
			_hashCode += getDescricao().hashCode();
		}
		if (getUnidade() != null) {
			_hashCode += getUnidade().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			Observacao.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Observacao"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("descricao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Descricao"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("unidade");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Unidade"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Unidade"));
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
