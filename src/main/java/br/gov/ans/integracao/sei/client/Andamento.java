/**
 * Andamento.java
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
public class Andamento implements java.io.Serializable {
	private java.lang.String idAndamento;

	private java.lang.String idTarefa;

	private java.lang.String idTarefaModulo;

	private java.lang.String descricao;

	private java.lang.String dataHora;

	private br.gov.ans.integracao.sei.client.Unidade unidade;

	private br.gov.ans.integracao.sei.client.Usuario usuario;

	private br.gov.ans.integracao.sei.client.AtributoAndamento[] atributos;

	public Andamento() {
	}

	public Andamento(java.lang.String idAndamento, java.lang.String idTarefa, java.lang.String idTarefaModulo,
			java.lang.String descricao, java.lang.String dataHora, br.gov.ans.integracao.sei.client.Unidade unidade,
			br.gov.ans.integracao.sei.client.Usuario usuario,
			br.gov.ans.integracao.sei.client.AtributoAndamento[] atributos) {
		this.idAndamento = idAndamento;
		this.idTarefa = idTarefa;
		this.idTarefaModulo = idTarefaModulo;
		this.descricao = descricao;
		this.dataHora = dataHora;
		this.unidade = unidade;
		this.usuario = usuario;
		this.atributos = atributos;
	}

	public java.lang.String getIdAndamento() {
		return idAndamento;
	}

	public void setIdAndamento(java.lang.String idAndamento) {
		this.idAndamento = idAndamento;
	}

	public java.lang.String getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(java.lang.String idTarefa) {
		this.idTarefa = idTarefa;
	}

	public java.lang.String getIdTarefaModulo() {
		return idTarefaModulo;
	}

	public void setIdTarefaModulo(java.lang.String idTarefaModulo) {
		this.idTarefaModulo = idTarefaModulo;
	}

	public java.lang.String getDescricao() {
		return descricao;
	}

	public void setDescricao(java.lang.String descricao) {
		this.descricao = descricao;
	}

	public java.lang.String getDataHora() {
		return dataHora;
	}

	public void setDataHora(java.lang.String dataHora) {
		this.dataHora = dataHora;
	}

	public br.gov.ans.integracao.sei.client.Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(br.gov.ans.integracao.sei.client.Unidade unidade) {
		this.unidade = unidade;
	}

	public br.gov.ans.integracao.sei.client.Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(br.gov.ans.integracao.sei.client.Usuario usuario) {
		this.usuario = usuario;
	}

	public br.gov.ans.integracao.sei.client.AtributoAndamento[] getAtributos() {
		return atributos;
	}

	public void setAtributos(br.gov.ans.integracao.sei.client.AtributoAndamento[] atributos) {
		this.atributos = atributos;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Andamento))
			return false;
		Andamento other = (Andamento) obj;
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
				&& ((this.idAndamento == null && other.getIdAndamento() == null)
						|| (this.idAndamento != null && this.idAndamento.equals(other.getIdAndamento())))
				&& ((this.idTarefa == null && other.getIdTarefa() == null)
						|| (this.idTarefa != null && this.idTarefa.equals(other.getIdTarefa())))
				&& ((this.idTarefaModulo == null && other.getIdTarefaModulo() == null)
						|| (this.idTarefaModulo != null && this.idTarefaModulo.equals(other.getIdTarefaModulo())))
				&& ((this.descricao == null && other.getDescricao() == null)
						|| (this.descricao != null && this.descricao.equals(other.getDescricao())))
				&& ((this.dataHora == null && other.getDataHora() == null)
						|| (this.dataHora != null && this.dataHora.equals(other.getDataHora())))
				&& ((this.unidade == null && other.getUnidade() == null)
						|| (this.unidade != null && this.unidade.equals(other.getUnidade())))
				&& ((this.usuario == null && other.getUsuario() == null)
						|| (this.usuario != null && this.usuario.equals(other.getUsuario())))
				&& ((this.atributos == null && other.getAtributos() == null)
						|| (this.atributos != null && java.util.Arrays.equals(this.atributos, other.getAtributos())));
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
		if (getIdAndamento() != null) {
			_hashCode += getIdAndamento().hashCode();
		}
		if (getIdTarefa() != null) {
			_hashCode += getIdTarefa().hashCode();
		}
		if (getIdTarefaModulo() != null) {
			_hashCode += getIdTarefaModulo().hashCode();
		}
		if (getDescricao() != null) {
			_hashCode += getDescricao().hashCode();
		}
		if (getDataHora() != null) {
			_hashCode += getDataHora().hashCode();
		}
		if (getUnidade() != null) {
			_hashCode += getUnidade().hashCode();
		}
		if (getUsuario() != null) {
			_hashCode += getUsuario().hashCode();
		}
		if (getAtributos() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getAtributos()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getAtributos(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			Andamento.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Andamento"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idAndamento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdAndamento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idTarefa");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdTarefa"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idTarefaModulo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdTarefaModulo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("descricao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Descricao"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dataHora");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DataHora"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("unidade");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Unidade"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Unidade"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("usuario");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Usuario"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Usuario"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("atributos");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Atributos"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "AtributoAndamento"));
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
