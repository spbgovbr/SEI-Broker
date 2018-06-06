/**
 * RetornoConsultaProcedimento.java
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
public class RetornoConsultaProcedimento implements java.io.Serializable {
	private java.lang.String idProcedimento;

	private java.lang.String procedimentoFormatado;

	private java.lang.String especificacao;

	private java.lang.String dataAutuacao;

	private java.lang.String linkAcesso;

	private br.gov.ans.integracao.sei.client.TipoProcedimento tipoProcedimento;

	private br.gov.ans.integracao.sei.client.Andamento andamentoGeracao;

	private br.gov.ans.integracao.sei.client.Andamento andamentoConclusao;

	private br.gov.ans.integracao.sei.client.Andamento ultimoAndamento;

	private br.gov.ans.integracao.sei.client.UnidadeProcedimentoAberto[] unidadesProcedimentoAberto;

	private br.gov.ans.integracao.sei.client.Assunto[] assuntos;

	private br.gov.ans.integracao.sei.client.Interessado[] interessados;

	private br.gov.ans.integracao.sei.client.Observacao[] observacoes;

	private br.gov.ans.integracao.sei.client.ProcedimentoResumido[] procedimentosRelacionados;

	private br.gov.ans.integracao.sei.client.ProcedimentoResumido[] procedimentosAnexados;

	public RetornoConsultaProcedimento() {
	}

	public RetornoConsultaProcedimento(java.lang.String idProcedimento, java.lang.String procedimentoFormatado,
			java.lang.String especificacao, java.lang.String dataAutuacao, java.lang.String linkAcesso,
			br.gov.ans.integracao.sei.client.TipoProcedimento tipoProcedimento,
			br.gov.ans.integracao.sei.client.Andamento andamentoGeracao,
			br.gov.ans.integracao.sei.client.Andamento andamentoConclusao,
			br.gov.ans.integracao.sei.client.Andamento ultimoAndamento,
			br.gov.ans.integracao.sei.client.UnidadeProcedimentoAberto[] unidadesProcedimentoAberto,
			br.gov.ans.integracao.sei.client.Assunto[] assuntos,
			br.gov.ans.integracao.sei.client.Interessado[] interessados,
			br.gov.ans.integracao.sei.client.Observacao[] observacoes,
			br.gov.ans.integracao.sei.client.ProcedimentoResumido[] procedimentosRelacionados,
			br.gov.ans.integracao.sei.client.ProcedimentoResumido[] procedimentosAnexados) {
		this.idProcedimento = idProcedimento;
		this.procedimentoFormatado = procedimentoFormatado;
		this.especificacao = especificacao;
		this.dataAutuacao = dataAutuacao;
		this.linkAcesso = linkAcesso;
		this.tipoProcedimento = tipoProcedimento;
		this.andamentoGeracao = andamentoGeracao;
		this.andamentoConclusao = andamentoConclusao;
		this.ultimoAndamento = ultimoAndamento;
		this.unidadesProcedimentoAberto = unidadesProcedimentoAberto;
		this.assuntos = assuntos;
		this.interessados = interessados;
		this.observacoes = observacoes;
		this.procedimentosRelacionados = procedimentosRelacionados;
		this.procedimentosAnexados = procedimentosAnexados;
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

	public java.lang.String getEspecificacao() {
		return especificacao;
	}

	public void setEspecificacao(java.lang.String especificacao) {
		this.especificacao = especificacao;
	}

	public java.lang.String getDataAutuacao() {
		return dataAutuacao;
	}

	public void setDataAutuacao(java.lang.String dataAutuacao) {
		this.dataAutuacao = dataAutuacao;
	}

	public java.lang.String getLinkAcesso() {
		return linkAcesso;
	}

	public void setLinkAcesso(java.lang.String linkAcesso) {
		this.linkAcesso = linkAcesso;
	}

	public br.gov.ans.integracao.sei.client.TipoProcedimento getTipoProcedimento() {
		return tipoProcedimento;
	}

	public void setTipoProcedimento(br.gov.ans.integracao.sei.client.TipoProcedimento tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	public br.gov.ans.integracao.sei.client.Andamento getAndamentoGeracao() {
		return andamentoGeracao;
	}

	public void setAndamentoGeracao(br.gov.ans.integracao.sei.client.Andamento andamentoGeracao) {
		this.andamentoGeracao = andamentoGeracao;
	}

	public br.gov.ans.integracao.sei.client.Andamento getAndamentoConclusao() {
		return andamentoConclusao;
	}

	public void setAndamentoConclusao(br.gov.ans.integracao.sei.client.Andamento andamentoConclusao) {
		this.andamentoConclusao = andamentoConclusao;
	}

	public br.gov.ans.integracao.sei.client.Andamento getUltimoAndamento() {
		return ultimoAndamento;
	}

	public void setUltimoAndamento(br.gov.ans.integracao.sei.client.Andamento ultimoAndamento) {
		this.ultimoAndamento = ultimoAndamento;
	}

	public br.gov.ans.integracao.sei.client.UnidadeProcedimentoAberto[] getUnidadesProcedimentoAberto() {
		return unidadesProcedimentoAberto;
	}

	public void setUnidadesProcedimentoAberto(
			br.gov.ans.integracao.sei.client.UnidadeProcedimentoAberto[] unidadesProcedimentoAberto) {
		this.unidadesProcedimentoAberto = unidadesProcedimentoAberto;
	}

	public br.gov.ans.integracao.sei.client.Assunto[] getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(br.gov.ans.integracao.sei.client.Assunto[] assuntos) {
		this.assuntos = assuntos;
	}

	public br.gov.ans.integracao.sei.client.Interessado[] getInteressados() {
		return interessados;
	}

	public void setInteressados(br.gov.ans.integracao.sei.client.Interessado[] interessados) {
		this.interessados = interessados;
	}

	public br.gov.ans.integracao.sei.client.Observacao[] getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(br.gov.ans.integracao.sei.client.Observacao[] observacoes) {
		this.observacoes = observacoes;
	}

	public br.gov.ans.integracao.sei.client.ProcedimentoResumido[] getProcedimentosRelacionados() {
		return procedimentosRelacionados;
	}

	public void setProcedimentosRelacionados(
			br.gov.ans.integracao.sei.client.ProcedimentoResumido[] procedimentosRelacionados) {
		this.procedimentosRelacionados = procedimentosRelacionados;
	}

	public br.gov.ans.integracao.sei.client.ProcedimentoResumido[] getProcedimentosAnexados() {
		return procedimentosAnexados;
	}

	public void setProcedimentosAnexados(
			br.gov.ans.integracao.sei.client.ProcedimentoResumido[] procedimentosAnexados) {
		this.procedimentosAnexados = procedimentosAnexados;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RetornoConsultaProcedimento))
			return false;
		RetornoConsultaProcedimento other = (RetornoConsultaProcedimento) obj;
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
				&& ((this.especificacao == null && other.getEspecificacao() == null)
						|| (this.especificacao != null && this.especificacao.equals(other.getEspecificacao())))
				&& ((this.dataAutuacao == null && other.getDataAutuacao() == null)
						|| (this.dataAutuacao != null && this.dataAutuacao.equals(other.getDataAutuacao())))
				&& ((this.linkAcesso == null && other.getLinkAcesso() == null)
						|| (this.linkAcesso != null && this.linkAcesso.equals(other.getLinkAcesso())))
				&& ((this.tipoProcedimento == null && other.getTipoProcedimento() == null)
						|| (this.tipoProcedimento != null && this.tipoProcedimento.equals(other.getTipoProcedimento())))
				&& ((this.andamentoGeracao == null && other.getAndamentoGeracao() == null)
						|| (this.andamentoGeracao != null && this.andamentoGeracao.equals(other.getAndamentoGeracao())))
				&& ((this.andamentoConclusao == null && other.getAndamentoConclusao() == null)
						|| (this.andamentoConclusao != null
								&& this.andamentoConclusao.equals(other.getAndamentoConclusao())))
				&& ((this.ultimoAndamento == null && other.getUltimoAndamento() == null)
						|| (this.ultimoAndamento != null && this.ultimoAndamento.equals(other.getUltimoAndamento())))
				&& ((this.unidadesProcedimentoAberto == null && other.getUnidadesProcedimentoAberto() == null)
						|| (this.unidadesProcedimentoAberto != null && java.util.Arrays
								.equals(this.unidadesProcedimentoAberto, other.getUnidadesProcedimentoAberto())))
				&& ((this.assuntos == null && other.getAssuntos() == null)
						|| (this.assuntos != null && java.util.Arrays.equals(this.assuntos, other.getAssuntos())))
				&& ((this.interessados == null && other.getInteressados() == null) || (this.interessados != null
						&& java.util.Arrays.equals(this.interessados, other.getInteressados())))
				&& ((this.observacoes == null && other.getObservacoes() == null) || (this.observacoes != null
						&& java.util.Arrays.equals(this.observacoes, other.getObservacoes())))
				&& ((this.procedimentosRelacionados == null && other.getProcedimentosRelacionados() == null)
						|| (this.procedimentosRelacionados != null && java.util.Arrays
								.equals(this.procedimentosRelacionados, other.getProcedimentosRelacionados())))
				&& ((this.procedimentosAnexados == null && other.getProcedimentosAnexados() == null)
						|| (this.procedimentosAnexados != null && java.util.Arrays.equals(this.procedimentosAnexados,
								other.getProcedimentosAnexados())));
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
		if (getEspecificacao() != null) {
			_hashCode += getEspecificacao().hashCode();
		}
		if (getDataAutuacao() != null) {
			_hashCode += getDataAutuacao().hashCode();
		}
		if (getLinkAcesso() != null) {
			_hashCode += getLinkAcesso().hashCode();
		}
		if (getTipoProcedimento() != null) {
			_hashCode += getTipoProcedimento().hashCode();
		}
		if (getAndamentoGeracao() != null) {
			_hashCode += getAndamentoGeracao().hashCode();
		}
		if (getAndamentoConclusao() != null) {
			_hashCode += getAndamentoConclusao().hashCode();
		}
		if (getUltimoAndamento() != null) {
			_hashCode += getUltimoAndamento().hashCode();
		}
		if (getUnidadesProcedimentoAberto() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getUnidadesProcedimentoAberto()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getUnidadesProcedimentoAberto(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getAssuntos() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getAssuntos()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getAssuntos(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getInteressados() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getInteressados()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getInteressados(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getObservacoes() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getObservacoes()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getObservacoes(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getProcedimentosRelacionados() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getProcedimentosRelacionados()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getProcedimentosRelacionados(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getProcedimentosAnexados() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getProcedimentosAnexados()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getProcedimentosAnexados(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			RetornoConsultaProcedimento.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "RetornoConsultaProcedimento"));
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
		elemField.setFieldName("especificacao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Especificacao"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dataAutuacao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "DataAutuacao"));
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
		elemField.setFieldName("tipoProcedimento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "TipoProcedimento"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "TipoProcedimento"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("andamentoGeracao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AndamentoGeracao"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Andamento"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("andamentoConclusao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "AndamentoConclusao"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Andamento"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ultimoAndamento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UltimoAndamento"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Andamento"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("unidadesProcedimentoAberto");
		elemField.setXmlName(new javax.xml.namespace.QName("", "UnidadesProcedimentoAberto"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "UnidadeProcedimentoAberto"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("assuntos");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Assuntos"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Assunto"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("interessados");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Interessados"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Interessado"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("observacoes");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Observacoes"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Observacao"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("procedimentosRelacionados");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ProcedimentosRelacionados"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "ProcedimentoResumido"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("procedimentosAnexados");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ProcedimentosAnexados"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "ProcedimentoResumido"));
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
