/**
 * RetornoConsultaBloco.java
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
public class RetornoConsultaBloco  implements java.io.Serializable {
    private java.lang.String idBloco;

    private br.gov.ans.integracao.sei.client.Unidade unidade;

    private br.gov.ans.integracao.sei.client.Usuario usuario;

    private java.lang.String descricao;

    private java.lang.String tipo;

    private java.lang.String estado;

    private br.gov.ans.integracao.sei.client.Unidade[] unidadesDisponibilizacao;

    private br.gov.ans.integracao.sei.client.ProtocoloBloco[] protocolos;

    public RetornoConsultaBloco() {
    }

    public RetornoConsultaBloco(
           java.lang.String idBloco,
           br.gov.ans.integracao.sei.client.Unidade unidade,
           br.gov.ans.integracao.sei.client.Usuario usuario,
           java.lang.String descricao,
           java.lang.String tipo,
           java.lang.String estado,
           br.gov.ans.integracao.sei.client.Unidade[] unidadesDisponibilizacao,
           br.gov.ans.integracao.sei.client.ProtocoloBloco[] protocolos) {
           this.idBloco = idBloco;
           this.unidade = unidade;
           this.usuario = usuario;
           this.descricao = descricao;
           this.tipo = tipo;
           this.estado = estado;
           this.unidadesDisponibilizacao = unidadesDisponibilizacao;
           this.protocolos = protocolos;
    }


    /**
     * Gets the idBloco value for this RetornoConsultaBloco.
     * 
     * @return idBloco
     */
    public java.lang.String getIdBloco() {
        return idBloco;
    }


    /**
     * Sets the idBloco value for this RetornoConsultaBloco.
     * 
     * @param idBloco
     */
    public void setIdBloco(java.lang.String idBloco) {
        this.idBloco = idBloco;
    }


    /**
     * Gets the unidade value for this RetornoConsultaBloco.
     * 
     * @return unidade
     */
    public br.gov.ans.integracao.sei.client.Unidade getUnidade() {
        return unidade;
    }


    /**
     * Sets the unidade value for this RetornoConsultaBloco.
     * 
     * @param unidade
     */
    public void setUnidade(br.gov.ans.integracao.sei.client.Unidade unidade) {
        this.unidade = unidade;
    }


    /**
     * Gets the usuario value for this RetornoConsultaBloco.
     * 
     * @return usuario
     */
    public br.gov.ans.integracao.sei.client.Usuario getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this RetornoConsultaBloco.
     * 
     * @param usuario
     */
    public void setUsuario(br.gov.ans.integracao.sei.client.Usuario usuario) {
        this.usuario = usuario;
    }


    /**
     * Gets the descricao value for this RetornoConsultaBloco.
     * 
     * @return descricao
     */
    public java.lang.String getDescricao() {
        return descricao;
    }


    /**
     * Sets the descricao value for this RetornoConsultaBloco.
     * 
     * @param descricao
     */
    public void setDescricao(java.lang.String descricao) {
        this.descricao = descricao;
    }


    /**
     * Gets the tipo value for this RetornoConsultaBloco.
     * 
     * @return tipo
     */
    public java.lang.String getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this RetornoConsultaBloco.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }


    /**
     * Gets the estado value for this RetornoConsultaBloco.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this RetornoConsultaBloco.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the unidadesDisponibilizacao value for this RetornoConsultaBloco.
     * 
     * @return unidadesDisponibilizacao
     */
    public br.gov.ans.integracao.sei.client.Unidade[] getUnidadesDisponibilizacao() {
        return unidadesDisponibilizacao;
    }


    /**
     * Sets the unidadesDisponibilizacao value for this RetornoConsultaBloco.
     * 
     * @param unidadesDisponibilizacao
     */
    public void setUnidadesDisponibilizacao(br.gov.ans.integracao.sei.client.Unidade[] unidadesDisponibilizacao) {
        this.unidadesDisponibilizacao = unidadesDisponibilizacao;
    }


    /**
     * Gets the protocolos value for this RetornoConsultaBloco.
     * 
     * @return protocolos
     */
    public br.gov.ans.integracao.sei.client.ProtocoloBloco[] getProtocolos() {
        return protocolos;
    }


    /**
     * Sets the protocolos value for this RetornoConsultaBloco.
     * 
     * @param protocolos
     */
    public void setProtocolos(br.gov.ans.integracao.sei.client.ProtocoloBloco[] protocolos) {
        this.protocolos = protocolos;
    }

    private java.lang.Object __equalsCalc = null;
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetornoConsultaBloco)) return false;
        RetornoConsultaBloco other = (RetornoConsultaBloco) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idBloco==null && other.getIdBloco()==null) || 
             (this.idBloco!=null &&
              this.idBloco.equals(other.getIdBloco()))) &&
            ((this.unidade==null && other.getUnidade()==null) || 
             (this.unidade!=null &&
              this.unidade.equals(other.getUnidade()))) &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario()))) &&
            ((this.descricao==null && other.getDescricao()==null) || 
             (this.descricao!=null &&
              this.descricao.equals(other.getDescricao()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.unidadesDisponibilizacao==null && other.getUnidadesDisponibilizacao()==null) || 
             (this.unidadesDisponibilizacao!=null &&
              java.util.Arrays.equals(this.unidadesDisponibilizacao, other.getUnidadesDisponibilizacao()))) &&
            ((this.protocolos==null && other.getProtocolos()==null) || 
             (this.protocolos!=null &&
              java.util.Arrays.equals(this.protocolos, other.getProtocolos())));
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
        if (getIdBloco() != null) {
            _hashCode += getIdBloco().hashCode();
        }
        if (getUnidade() != null) {
            _hashCode += getUnidade().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        if (getDescricao() != null) {
            _hashCode += getDescricao().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getUnidadesDisponibilizacao() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUnidadesDisponibilizacao());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUnidadesDisponibilizacao(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProtocolos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProtocolos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProtocolos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetornoConsultaBloco.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "RetornoConsultaBloco"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idBloco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdBloco"));
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
        elemField.setFieldName("descricao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Descricao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidadesDisponibilizacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UnidadesDisponibilizacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("Sei", "Unidade"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocolos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Protocolos"));
        elemField.setXmlType(new javax.xml.namespace.QName("Sei", "ProtocoloBloco"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
