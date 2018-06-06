/**
 * RetornoConsultaDocumento.java
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
public class RetornoConsultaDocumento implements java.io.Serializable {
	private java.lang.String idProcedimento;

	private java.lang.String procedimentoFormatado;

	private java.lang.String idDocumento;

	private java.lang.String documentoFormatado;

	private java.lang.String linkAcesso;

	private br.gov.ans.integracao.sei.client.Serie serie;

	private java.lang.String numero;

	private java.lang.String data;

	private br.gov.ans.integracao.sei.client.Unidade unidadeElaboradora;

	private br.gov.ans.integracao.sei.client.Andamento andamentoGeracao;

	private br.gov.ans.integracao.sei.client.Assinatura[] assinaturas;

	private br.gov.ans.integracao.sei.client.Publicacao publicacao;

	private br.gov.ans.integracao.sei.client.Campo[] campos;

	public RetornoConsultaDocumento() {
	}

	public RetornoConsultaDocumento(java.lang.String idProcedimento, java.lang.String procedimentoFormatado,
			java.lang.String idDocumento, java.lang.String documentoFormatado, java.lang.String linkAcesso,
			br.gov.ans.integracao.sei.client.Serie serie, java.lang.String numero, java.lang.String data,
			br.gov.ans.integracao.sei.client.Unidade unidadeElaboradora,
			br.gov.ans.integracao.sei.client.Andamento andamentoGeracao,
			br.gov.ans.integracao.sei.client.Assinatura[] assinaturas,
			br.gov.ans.integracao.sei.client.Publicacao publicacao, br.gov.ans.integracao.sei.client.Campo[] campos) {
		this.idProcedimento = idProcedimento;
		this.procedimentoFormatado = procedimentoFormatado;
		this.idDocumento = idDocumento;
		this.documentoFormatado = documentoFormatado;
		this.linkAcesso = linkAcesso;
		this.serie = serie;
		this.numero = numero;
		this.data = data;
		this.unidadeElaboradora = unidadeElaboradora;
		this.andamentoGeracao = andamentoGeracao;
		this.assinaturas = assinaturas;
		this.publicacao = publicacao;
		this.campos = campos;
	}

	public java.lang.String getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(java.lang.String idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public java.lang.String getProcedimentoFormatado() {
		return procedimentoFormatado;
	}

	public void setProcedimentoFormatado(java.lang.String procedimentoFormatado) {
		this.procedimentoFormatado = procedimentoFormatado;
	}

	public java.lang.String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(java.lang.String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public java.lang.String getDocumentoFormatado() {
		return documentoFormatado;
	}

	public void setDocumentoFormatado(java.lang.String documentoFormatado) {
		this.documentoFormatado = documentoFormatado;
	}

	public java.lang.String getLinkAcesso() {
		return linkAcesso;
	}

	public void setLinkAcesso(java.lang.String linkAcesso) {
		this.linkAcesso = linkAcesso;
	}

	public br.gov.ans.integracao.sei.client.Serie getSerie() {
		return serie;
	}

	public void setSerie(br.gov.ans.integracao.sei.client.Serie serie) {
		this.serie = serie;
	}

	public java.lang.String getNumero() {
		return numero;
	}

	public void setNumero(java.lang.String numero) {
		this.numero = numero;
	}

	public java.lang.String getData() {
		return data;
	}

	public void setData(java.lang.String data) {
		this.data = data;
	}

	public br.gov.ans.integracao.sei.client.Unidade getUnidadeElaboradora() {
		return unidadeElaboradora;
	}

	public void setUnidadeElaboradora(br.gov.ans.integracao.sei.client.Unidade unidadeElaboradora) {
		this.unidadeElaboradora = unidadeElaboradora;
	}

	public br.gov.ans.integracao.sei.client.Andamento getAndamentoGeracao() {
		return andamentoGeracao;
	}

	public void setAndamentoGeracao(br.gov.ans.integracao.sei.client.Andamento andamentoGeracao) {
		this.andamentoGeracao = andamentoGeracao;
	}

	public br.gov.ans.integracao.sei.client.Assinatura[] getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(br.gov.ans.integracao.sei.client.Assinatura[] assinaturas) {
		this.assinaturas = assinaturas;
	}

	public br.gov.ans.integracao.sei.client.Publicacao getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(br.gov.ans.integracao.sei.client.Publicacao publicacao) {
		this.publicacao = publicacao;
	}

	public br.gov.ans.integracao.sei.client.Campo[] getCampos() {
		return campos;
	}

	public void setCampos(br.gov.ans.integracao.sei.client.Campo[] campos) {
		this.campos = campos;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RetornoConsultaDocumento))
			return false;
		RetornoConsultaDocumento other = (RetornoConsultaDocumento) obj;
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
				&& ((this.idProcedimento == null && other.getIdProcedimento() == null)
						|| (this.idProcedimento != null && this.idProcedimento.equals(other.getIdProcedimento())))
				&& ((this.procedimentoFormatado == null && other.getProcedimentoFormatado() == null)
						|| (this.procedimentoFormatado != null
								&& this.procedimentoFormatado.equals(other.getProcedimentoFormatado())))
				&& ((this.idDocumento == null && other.getIdDocumento() == null)
						|| (this.idDocumento != null && this.idDocumento.equals(other.getIdDocumento())))
				&& ((this.documentoFormatado == null && other.getDocumentoFormatado() == null)
						|| (this.documentoFormatado != null
								&& this.documentoFormatado.equals(other.getDocumentoFormatado())))
				&& ((this.linkAcesso == null && other.getLinkAcesso() == null)
						|| (this.linkAcesso != null && this.linkAcesso.equals(other.getLinkAcesso())))
				&& ((this.serie == null && other.getSerie() == null)
						|| (this.serie != null && this.serie.equals(other.getSerie())))
				&& ((this.numero == null && other.getNumero() == null)
						|| (this.numero != null && this.numero.equals(other.getNumero())))
				&& ((this.data == null && other.getData() == null)
						|| (this.data != null && this.data.equals(other.getData())))
				&& ((this.unidadeElaboradora == null && other.getUnidadeElaboradora() == null)
						|| (this.unidadeElaboradora != null
								&& this.unidadeElaboradora.equals(other.getUnidadeElaboradora())))
				&& ((this.andamentoGeracao == null && other.getAndamentoGeracao() == null)
						|| (this.andamentoGeracao != null && this.andamentoGeracao.equals(other.getAndamentoGeracao())))
				&& ((this.assinaturas == null && other.getAssinaturas() == null) || (this.assinaturas != null
						&& java.util.Arrays.equals(this.assinaturas, other.getAssinaturas())))
				&& ((this.publicacao == null && other.getPublicacao() == null)
						|| (this.publicacao != null && this.publicacao.equals(other.getPublicacao())))
				&& ((this.campos == null && other.getCampos() == null)
						|| (this.campos != null && java.util.Arrays.equals(this.campos, other.getCampos())));
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
		if (getIdProcedimento() != null) {
			_hashCode += getIdProcedimento().hashCode();
		}
		if (getProcedimentoFormatado() != null) {
			_hashCode += getProcedimentoFormatado().hashCode();
		}
		if (getIdDocumento() != null) {
			_hashCode += getIdDocumento().hashCode();
		}
		if (getDocumentoFormatado() != null) {
			_hashCode += getDocumentoFormatado().hashCode();
		}
		if (getLinkAcesso() != null) {
			_hashCode += getLinkAcesso().hashCode();
		}
		if (getSerie() != null) {
			_hashCode += getSerie().hashCode();
		}
		if (getNumero() != null) {
			_hashCode += getNumero().hashCode();
		}
		if (getData() != null) {
			_hashCode += getData().hashCode();
		}
		if (getUnidadeElaboradora() != null) {
			_hashCode += getUnidadeElaboradora().hashCode();
		}
		if (getAndamentoGeracao() != null) {
			_hashCode += getAndamentoGeracao().hashCode();
		}
		if (getAssinaturas() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getAssinaturas()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getAssinaturas(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getPublicacao() != null) {
			_hashCode += getPublicacao().hashCode();
		}
		if (getCampos() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getCampos()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getCampos(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			RetornoConsultaDocumento.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "RetornoConsultaDocumento"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idProcedimento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdProcedimento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("procedimentoFormatado");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ProcedimentoFormatado"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idDocumento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("documentoFormatado");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DocumentoFormatado"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("linkAcesso");
		elemField.setXmlName(new javax.xml.namespace.QName("", "LinkAcesso"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("serie");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Serie"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Serie"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("numero");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Numero"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("data");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Data"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("unidadeElaboradora");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UnidadeElaboradora"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Unidade"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("andamentoGeracao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AndamentoGeracao"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Andamento"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("assinaturas");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Assinaturas"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Assinatura"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("publicacao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Publicacao"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Publicacao"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("campos");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Campos"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Campo"));
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
