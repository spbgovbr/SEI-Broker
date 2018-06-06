/**
 * UnidadeProcedimentoAberto.java
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
public class UnidadeProcedimentoAberto implements java.io.Serializable {
	private br.gov.ans.integracao.sei.client.Unidade unidade;

	private br.gov.ans.integracao.sei.client.Usuario usuarioAtribuicao;

	public UnidadeProcedimentoAberto() {
	}

	public UnidadeProcedimentoAberto(br.gov.ans.integracao.sei.client.Unidade unidade,
			br.gov.ans.integracao.sei.client.Usuario usuarioAtribuicao) {
		this.unidade = unidade;
		this.usuarioAtribuicao = usuarioAtribuicao;
	}

	public br.gov.ans.integracao.sei.client.Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(br.gov.ans.integracao.sei.client.Unidade unidade) {
		this.unidade = unidade;
	}

	public br.gov.ans.integracao.sei.client.Usuario getUsuarioAtribuicao() {
		return usuarioAtribuicao;
	}

	public void setUsuarioAtribuicao(br.gov.ans.integracao.sei.client.Usuario usuarioAtribuicao) {
		this.usuarioAtribuicao = usuarioAtribuicao;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof UnidadeProcedimentoAberto))
			return false;
		UnidadeProcedimentoAberto other = (UnidadeProcedimentoAberto) obj;
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
				&& ((this.unidade == null && other.getUnidade() == null)
						|| (this.unidade != null && this.unidade.equals(other.getUnidade())))
				&& ((this.usuarioAtribuicao == null && other.getUsuarioAtribuicao() == null)
						|| (this.usuarioAtribuicao != null
								&& this.usuarioAtribuicao.equals(other.getUsuarioAtribuicao())));
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
		if (getUnidade() != null) {
			_hashCode += getUnidade().hashCode();
		}
		if (getUsuarioAtribuicao() != null) {
			_hashCode += getUsuarioAtribuicao().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			UnidadeProcedimentoAberto.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "UnidadeProcedimentoAberto"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("unidade");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Unidade"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Unidade"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("usuarioAtribuicao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UsuarioAtribuicao"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Usuario"));
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
