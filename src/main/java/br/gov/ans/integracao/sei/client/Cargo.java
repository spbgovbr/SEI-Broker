/**
 * Cargo.java
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
public class Cargo implements java.io.Serializable {
	private java.lang.String idCargo;

	private java.lang.String expressaoCargo;

	private java.lang.String expressaoTratamento;

	private java.lang.String expressaoVocativo;

	public Cargo() {
	}

	public Cargo(java.lang.String idCargo, java.lang.String expressaoCargo, java.lang.String expressaoTratamento,
			java.lang.String expressaoVocativo) {
		this.idCargo = idCargo;
		this.expressaoCargo = expressaoCargo;
		this.expressaoTratamento = expressaoTratamento;
		this.expressaoVocativo = expressaoVocativo;
	}

	public java.lang.String getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(java.lang.String idCargo) {
		this.idCargo = idCargo;
	}

	public java.lang.String getExpressaoCargo() {
		return expressaoCargo;
	}

	public void setExpressaoCargo(java.lang.String expressaoCargo) {
		this.expressaoCargo = expressaoCargo;
	}

	public java.lang.String getExpressaoTratamento() {
		return expressaoTratamento;
	}

	public void setExpressaoTratamento(java.lang.String expressaoTratamento) {
		this.expressaoTratamento = expressaoTratamento;
	}

	public java.lang.String getExpressaoVocativo() {
		return expressaoVocativo;
	}

	public void setExpressaoVocativo(java.lang.String expressaoVocativo) {
		this.expressaoVocativo = expressaoVocativo;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Cargo))
			return false;
		Cargo other = (Cargo) obj;
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
				&& ((this.idCargo == null && other.getIdCargo() == null)
						|| (this.idCargo != null && this.idCargo.equals(other.getIdCargo())))
				&& ((this.expressaoCargo == null && other.getExpressaoCargo() == null)
						|| (this.expressaoCargo != null && this.expressaoCargo.equals(other.getExpressaoCargo())))
				&& ((this.expressaoTratamento == null && other.getExpressaoTratamento() == null)
						|| (this.expressaoTratamento != null
								&& this.expressaoTratamento.equals(other.getExpressaoTratamento())))
				&& ((this.expressaoVocativo == null && other.getExpressaoVocativo() == null)
						|| (this.expressaoVocativo != null
								&& this.expressaoVocativo.equals(other.getExpressaoVocativo())));
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
		if (getIdCargo() != null) {
			_hashCode += getIdCargo().hashCode();
		}
		if (getExpressaoCargo() != null) {
			_hashCode += getExpressaoCargo().hashCode();
		}
		if (getExpressaoTratamento() != null) {
			_hashCode += getExpressaoTratamento().hashCode();
		}
		if (getExpressaoVocativo() != null) {
			_hashCode += getExpressaoVocativo().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Cargo.class,
			true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Cargo"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idCargo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdCargo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("expressaoCargo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ExpressaoCargo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("expressaoTratamento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ExpressaoTratamento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("expressaoVocativo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ExpressaoVocativo"));
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
