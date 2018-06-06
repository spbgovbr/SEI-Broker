/**
 * Documento.java
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
public class Documento implements java.io.Serializable {
	private java.lang.String tipo;

	private java.lang.String idProcedimento;

	private java.lang.String protocoloProcedimento;

	private java.lang.String idSerie;

	private java.lang.String numero;

	private java.lang.String data;

	private java.lang.String descricao;

	private java.lang.String idTipoConferencia;

	private br.gov.ans.integracao.sei.client.Remetente remetente;

	private br.gov.ans.integracao.sei.client.Interessado[] interessados;

	private br.gov.ans.integracao.sei.client.Destinatario[] destinatarios;

	private java.lang.String observacao;

	private java.lang.String nomeArquivo;

	private java.lang.String nivelAcesso;

	private java.lang.String idHipoteseLegal;

	private java.lang.String conteudo;

	private java.lang.String idArquivo;

	private br.gov.ans.integracao.sei.client.Campo[] campos;

	private java.lang.String sinBloqueado;

	public Documento() {
	}

	public Documento(java.lang.String tipo, java.lang.String idProcedimento, java.lang.String protocoloProcedimento,
			java.lang.String idSerie, java.lang.String numero, java.lang.String data, java.lang.String descricao,
			java.lang.String idTipoConferencia, br.gov.ans.integracao.sei.client.Remetente remetente,
			br.gov.ans.integracao.sei.client.Interessado[] interessados,
			br.gov.ans.integracao.sei.client.Destinatario[] destinatarios, java.lang.String observacao,
			java.lang.String nomeArquivo, java.lang.String nivelAcesso, java.lang.String idHipoteseLegal,
			java.lang.String conteudo, byte[] conteudoMTOM, java.lang.String idArquivo,
			br.gov.ans.integracao.sei.client.Campo[] campos, java.lang.String sinBloqueado) {
		this.tipo = tipo;
		this.idProcedimento = idProcedimento;
		this.protocoloProcedimento = protocoloProcedimento;
		this.idSerie = idSerie;
		this.numero = numero;
		this.data = data;
		this.descricao = descricao;
		this.idTipoConferencia = idTipoConferencia;
		this.remetente = remetente;
		this.interessados = interessados;
		this.destinatarios = destinatarios;
		this.observacao = observacao;
		this.nomeArquivo = nomeArquivo;
		this.nivelAcesso = nivelAcesso;
		this.idHipoteseLegal = idHipoteseLegal;
		this.conteudo = conteudo;
		this.idArquivo = idArquivo;
		this.campos = campos;
		this.sinBloqueado = sinBloqueado;
	}

	public java.lang.String getTipo() {
		return tipo;
	}

	public void setTipo(java.lang.String tipo) {
		this.tipo = tipo;
	}

	public java.lang.String getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(java.lang.String idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public java.lang.String getProtocoloProcedimento() {
		return protocoloProcedimento;
	}

	public void setProtocoloProcedimento(java.lang.String protocoloProcedimento) {
		this.protocoloProcedimento = protocoloProcedimento;
	}

	public java.lang.String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(java.lang.String idSerie) {
		this.idSerie = idSerie;
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

	public java.lang.String getDescricao() {
		return descricao;
	}

	public void setDescricao(java.lang.String descricao) {
		this.descricao = descricao;
	}

	public java.lang.String getIdTipoConferencia() {
		return idTipoConferencia;
	}

	public void setIdTipoConferencia(java.lang.String idTipoConferencia) {
		this.idTipoConferencia = idTipoConferencia;
	}

	public br.gov.ans.integracao.sei.client.Remetente getRemetente() {
		return remetente;
	}

	public void setRemetente(br.gov.ans.integracao.sei.client.Remetente remetente) {
		this.remetente = remetente;
	}

	public br.gov.ans.integracao.sei.client.Interessado[] getInteressados() {
		return interessados;
	}

	public void setInteressados(br.gov.ans.integracao.sei.client.Interessado[] interessados) {
		this.interessados = interessados;
	}

	public br.gov.ans.integracao.sei.client.Destinatario[] getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(br.gov.ans.integracao.sei.client.Destinatario[] destinatarios) {
		this.destinatarios = destinatarios;
	}

	public java.lang.String getObservacao() {
		return observacao;
	}

	public void setObservacao(java.lang.String observacao) {
		this.observacao = observacao;
	}

	public java.lang.String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(java.lang.String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public java.lang.String getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(java.lang.String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public java.lang.String getIdHipoteseLegal() {
		return idHipoteseLegal;
	}

	public void setIdHipoteseLegal(java.lang.String idHipoteseLegal) {
		this.idHipoteseLegal = idHipoteseLegal;
	}

	public java.lang.String getConteudo() {
		return conteudo;
	}

	public void setConteudo(java.lang.String conteudo) {
		this.conteudo = conteudo;
	}

	public java.lang.String getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(java.lang.String idArquivo) {
		this.idArquivo = idArquivo;
	}

	public br.gov.ans.integracao.sei.client.Campo[] getCampos() {
		return campos;
	}

	public void setCampos(br.gov.ans.integracao.sei.client.Campo[] campos) {
		this.campos = campos;
	}

	public java.lang.String getSinBloqueado() {
		return sinBloqueado;
	}

	public void setSinBloqueado(java.lang.String sinBloqueado) {
		this.sinBloqueado = sinBloqueado;
	}

	private java.lang.Object __equalsCalc = null;

	public boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Documento))
			return false;
		Documento other = (Documento) obj;
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
				&& ((this.tipo == null && other.getTipo() == null)
						|| (this.tipo != null && this.tipo.equals(other.getTipo())))
				&& ((this.idProcedimento == null && other.getIdProcedimento() == null)
						|| (this.idProcedimento != null && this.idProcedimento.equals(other.getIdProcedimento())))
				&& ((this.protocoloProcedimento == null && other.getProtocoloProcedimento() == null)
						|| (this.protocoloProcedimento != null
								&& this.protocoloProcedimento.equals(other.getProtocoloProcedimento())))
				&& ((this.idSerie == null && other.getIdSerie() == null)
						|| (this.idSerie != null && this.idSerie.equals(other.getIdSerie())))
				&& ((this.numero == null && other.getNumero() == null)
						|| (this.numero != null && this.numero.equals(other.getNumero())))
				&& ((this.data == null && other.getData() == null)
						|| (this.data != null && this.data.equals(other.getData())))
				&& ((this.descricao == null && other.getDescricao() == null)
						|| (this.descricao != null && this.descricao.equals(other.getDescricao())))
				&& ((this.idTipoConferencia == null && other.getIdTipoConferencia() == null)
						|| (this.idTipoConferencia != null
								&& this.idTipoConferencia.equals(other.getIdTipoConferencia())))
				&& ((this.remetente == null && other.getRemetente() == null)
						|| (this.remetente != null && this.remetente.equals(other.getRemetente())))
				&& ((this.interessados == null && other.getInteressados() == null) || (this.interessados != null
						&& java.util.Arrays.equals(this.interessados, other.getInteressados())))
				&& ((this.destinatarios == null && other.getDestinatarios() == null) || (this.destinatarios != null
						&& java.util.Arrays.equals(this.destinatarios, other.getDestinatarios())))
				&& ((this.observacao == null && other.getObservacao() == null)
						|| (this.observacao != null && this.observacao.equals(other.getObservacao())))
				&& ((this.nomeArquivo == null && other.getNomeArquivo() == null)
						|| (this.nomeArquivo != null && this.nomeArquivo.equals(other.getNomeArquivo())))
				&& ((this.nivelAcesso == null && other.getNivelAcesso() == null)
						|| (this.nivelAcesso != null && this.nivelAcesso.equals(other.getNivelAcesso())))
				&& ((this.idHipoteseLegal == null && other.getIdHipoteseLegal() == null)
						|| (this.idHipoteseLegal != null && this.idHipoteseLegal.equals(other.getIdHipoteseLegal())))
				&& ((this.conteudo == null && other.getConteudo() == null)
						|| (this.conteudo != null && this.conteudo.equals(other.getConteudo())))
				&& ((this.idArquivo == null && other.getIdArquivo() == null)
						|| (this.idArquivo != null && this.idArquivo.equals(other.getIdArquivo())))
				&& ((this.campos == null && other.getCampos() == null)
						|| (this.campos != null && java.util.Arrays.equals(this.campos, other.getCampos())))
				&& ((this.sinBloqueado == null && other.getSinBloqueado() == null)
						|| (this.sinBloqueado != null && this.sinBloqueado.equals(other.getSinBloqueado())));
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
		if (getTipo() != null) {
			_hashCode += getTipo().hashCode();
		}
		if (getIdProcedimento() != null) {
			_hashCode += getIdProcedimento().hashCode();
		}
		if (getProtocoloProcedimento() != null) {
			_hashCode += getProtocoloProcedimento().hashCode();
		}
		if (getIdSerie() != null) {
			_hashCode += getIdSerie().hashCode();
		}
		if (getNumero() != null) {
			_hashCode += getNumero().hashCode();
		}
		if (getData() != null) {
			_hashCode += getData().hashCode();
		}
		if (getDescricao() != null) {
			_hashCode += getDescricao().hashCode();
		}
		if (getIdTipoConferencia() != null) {
			_hashCode += getIdTipoConferencia().hashCode();
		}
		if (getRemetente() != null) {
			_hashCode += getRemetente().hashCode();
		}
		if (getInteressados() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getInteressados()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getInteressados(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getDestinatarios() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getDestinatarios()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getDestinatarios(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getObservacao() != null) {
			_hashCode += getObservacao().hashCode();
		}
		if (getNomeArquivo() != null) {
			_hashCode += getNomeArquivo().hashCode();
		}
		if (getNivelAcesso() != null) {
			_hashCode += getNivelAcesso().hashCode();
		}
		if (getIdHipoteseLegal() != null) {
			_hashCode += getIdHipoteseLegal().hashCode();
		}
		if (getConteudo() != null) {
			_hashCode += getConteudo().hashCode();
		}
		if (getIdArquivo() != null) {
			_hashCode += getIdArquivo().hashCode();
		}
		if (getCampos() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getCampos()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getCampos(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getSinBloqueado() != null) {
			_hashCode += getSinBloqueado().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			Documento.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Documento"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tipo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Tipo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idProcedimento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdProcedimento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("protocoloProcedimento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ProtocoloProcedimento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idSerie");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdSerie"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("numero");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Numero"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("data");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Data"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("descricao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Descricao"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idTipoConferencia");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdTipoConferencia"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("remetente");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Remetente"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Remetente"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("interessados");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Interessados"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Interessado"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("destinatarios");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Destinatarios"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Destinatario"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("observacao");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Observacao"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("nomeArquivo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NomeArquivo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("nivelAcesso");
		elemField.setXmlName(new javax.xml.namespace.QName("", "NivelAcesso"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idHipoteseLegal");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdHipoteseLegal"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("conteudo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Conteudo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("conteudoMTOM");
		elemField.setXmlName(new javax.xml.namespace.QName("", "ConteudoMTOM"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idArquivo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "IdArquivo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("campos");
		elemField.setXmlName(new javax.xml.namespace.QName("", "Campos"));
		elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Campo"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("sinBloqueado");
		elemField.setXmlName(new javax.xml.namespace.QName("", "SinBloqueado"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(true);
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
