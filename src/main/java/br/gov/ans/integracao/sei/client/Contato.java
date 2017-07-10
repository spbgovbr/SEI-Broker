/**
 * Contato.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.ans.integracao.sei.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contato  implements java.io.Serializable {
    private java.lang.String staOperacao;

    private java.lang.String idContato;

    private java.lang.String idTipoContato;

    private java.lang.String nomeTipoContato;

    private java.lang.String sigla;

    private java.lang.String nome;

    private java.lang.String staNatureza;

    private java.lang.String idContatoAssociado;

    private java.lang.String nomeContatoAssociado;

    private java.lang.String sinEnderecoAssociado;

    private java.lang.String enderecoAssociado;

    private java.lang.String complementoAssociado;

    private java.lang.String bairroAssociado;

    private java.lang.String idCidadeAssociado;

    private java.lang.String nomeCidadeAssociado;

    private java.lang.String idEstadoAssociado;

    private java.lang.String siglaEstadoAssociado;

    private java.lang.String idPaisAssociado;

    private java.lang.String nomePaisAssociado;

    private java.lang.String cepAssociado;

    private java.lang.String endereco;

    private java.lang.String complemento;

    private java.lang.String bairro;

    private java.lang.String idCidade;

    private java.lang.String nomeCidade;

    private java.lang.String idEstado;

    private java.lang.String siglaEstado;

    private java.lang.String idPais;

    private java.lang.String nomePais;

    private java.lang.String cep;

    private java.lang.String staGenero;

    private java.lang.String idCargo;

    private java.lang.String expressaoCargo;

    private java.lang.String expressaoTratamento;

    private java.lang.String expressaoVocativo;

    private java.lang.String cpf;

    private java.lang.String cnpj;

    private java.lang.String rg;

    private java.lang.String orgaoExpedidor;

    private java.lang.String matricula;

    private java.lang.String matriculaOab;

    private java.lang.String telefoneFixo;

    private java.lang.String telefoneCelular;

    private java.lang.String dataNascimento;

    private java.lang.String email;

    private java.lang.String sitioInternet;

    private java.lang.String observacao;

    private java.lang.String sinAtivo;

    public Contato() {
    }

    public Contato(
           java.lang.String staOperacao,
           java.lang.String idContato,
           java.lang.String idTipoContato,
           java.lang.String nomeTipoContato,
           java.lang.String sigla,
           java.lang.String nome,
           java.lang.String staNatureza,
           java.lang.String idContatoAssociado,
           java.lang.String nomeContatoAssociado,
           java.lang.String sinEnderecoAssociado,
           java.lang.String enderecoAssociado,
           java.lang.String complementoAssociado,
           java.lang.String bairroAssociado,
           java.lang.String idCidadeAssociado,
           java.lang.String nomeCidadeAssociado,
           java.lang.String idEstadoAssociado,
           java.lang.String siglaEstadoAssociado,
           java.lang.String idPaisAssociado,
           java.lang.String nomePaisAssociado,
           java.lang.String cepAssociado,
           java.lang.String endereco,
           java.lang.String complemento,
           java.lang.String bairro,
           java.lang.String idCidade,
           java.lang.String nomeCidade,
           java.lang.String idEstado,
           java.lang.String siglaEstado,
           java.lang.String idPais,
           java.lang.String nomePais,
           java.lang.String cep,
           java.lang.String staGenero,
           java.lang.String idCargo,
           java.lang.String expressaoCargo,
           java.lang.String expressaoTratamento,
           java.lang.String expressaoVocativo,
           java.lang.String cpf,
           java.lang.String cnpj,
           java.lang.String rg,
           java.lang.String orgaoExpedidor,
           java.lang.String matricula,
           java.lang.String matriculaOab,
           java.lang.String telefoneFixo,
           java.lang.String telefoneCelular,
           java.lang.String dataNascimento,
           java.lang.String email,
           java.lang.String sitioInternet,
           java.lang.String observacao,
           java.lang.String sinAtivo) {
           this.staOperacao = staOperacao;
           this.idContato = idContato;
           this.idTipoContato = idTipoContato;
           this.nomeTipoContato = nomeTipoContato;
           this.sigla = sigla;
           this.nome = nome;
           this.staNatureza = staNatureza;
           this.idContatoAssociado = idContatoAssociado;
           this.nomeContatoAssociado = nomeContatoAssociado;
           this.sinEnderecoAssociado = sinEnderecoAssociado;
           this.enderecoAssociado = enderecoAssociado;
           this.complementoAssociado = complementoAssociado;
           this.bairroAssociado = bairroAssociado;
           this.idCidadeAssociado = idCidadeAssociado;
           this.nomeCidadeAssociado = nomeCidadeAssociado;
           this.idEstadoAssociado = idEstadoAssociado;
           this.siglaEstadoAssociado = siglaEstadoAssociado;
           this.idPaisAssociado = idPaisAssociado;
           this.nomePaisAssociado = nomePaisAssociado;
           this.cepAssociado = cepAssociado;
           this.endereco = endereco;
           this.complemento = complemento;
           this.bairro = bairro;
           this.idCidade = idCidade;
           this.nomeCidade = nomeCidade;
           this.idEstado = idEstado;
           this.siglaEstado = siglaEstado;
           this.idPais = idPais;
           this.nomePais = nomePais;
           this.cep = cep;
           this.staGenero = staGenero;
           this.idCargo = idCargo;
           this.expressaoCargo = expressaoCargo;
           this.expressaoTratamento = expressaoTratamento;
           this.expressaoVocativo = expressaoVocativo;
           this.cpf = cpf;
           this.cnpj = cnpj;
           this.rg = rg;
           this.orgaoExpedidor = orgaoExpedidor;
           this.matricula = matricula;
           this.matriculaOab = matriculaOab;
           this.telefoneFixo = telefoneFixo;
           this.telefoneCelular = telefoneCelular;
           this.dataNascimento = dataNascimento;
           this.email = email;
           this.sitioInternet = sitioInternet;
           this.observacao = observacao;
           this.sinAtivo = sinAtivo;
    }


    /**
     * Gets the staOperacao value for this Contato.
     * 
     * @return staOperacao
     */
    public java.lang.String getStaOperacao() {
        return staOperacao;
    }


    /**
     * Sets the staOperacao value for this Contato.
     * 
     * @param staOperacao
     */
    public void setStaOperacao(java.lang.String staOperacao) {
        this.staOperacao = staOperacao;
    }


    /**
     * Gets the idContato value for this Contato.
     * 
     * @return idContato
     */
    public java.lang.String getIdContato() {
        return idContato;
    }


    /**
     * Sets the idContato value for this Contato.
     * 
     * @param idContato
     */
    public void setIdContato(java.lang.String idContato) {
        this.idContato = idContato;
    }


    /**
     * Gets the idTipoContato value for this Contato.
     * 
     * @return idTipoContato
     */
    public java.lang.String getIdTipoContato() {
        return idTipoContato;
    }


    /**
     * Sets the idTipoContato value for this Contato.
     * 
     * @param idTipoContato
     */
    public void setIdTipoContato(java.lang.String idTipoContato) {
        this.idTipoContato = idTipoContato;
    }


    /**
     * Gets the nomeTipoContato value for this Contato.
     * 
     * @return nomeTipoContato
     */
    public java.lang.String getNomeTipoContato() {
        return nomeTipoContato;
    }


    /**
     * Sets the nomeTipoContato value for this Contato.
     * 
     * @param nomeTipoContato
     */
    public void setNomeTipoContato(java.lang.String nomeTipoContato) {
        this.nomeTipoContato = nomeTipoContato;
    }


    /**
     * Gets the sigla value for this Contato.
     * 
     * @return sigla
     */
    public java.lang.String getSigla() {
        return sigla;
    }


    /**
     * Sets the sigla value for this Contato.
     * 
     * @param sigla
     */
    public void setSigla(java.lang.String sigla) {
        this.sigla = sigla;
    }


    /**
     * Gets the nome value for this Contato.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this Contato.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the staNatureza value for this Contato.
     * 
     * @return staNatureza
     */
    public java.lang.String getStaNatureza() {
        return staNatureza;
    }


    /**
     * Sets the staNatureza value for this Contato.
     * 
     * @param staNatureza
     */
    public void setStaNatureza(java.lang.String staNatureza) {
        this.staNatureza = staNatureza;
    }


    /**
     * Gets the idContatoAssociado value for this Contato.
     * 
     * @return idContatoAssociado
     */
    public java.lang.String getIdContatoAssociado() {
        return idContatoAssociado;
    }


    /**
     * Sets the idContatoAssociado value for this Contato.
     * 
     * @param idContatoAssociado
     */
    public void setIdContatoAssociado(java.lang.String idContatoAssociado) {
        this.idContatoAssociado = idContatoAssociado;
    }


    /**
     * Gets the nomeContatoAssociado value for this Contato.
     * 
     * @return nomeContatoAssociado
     */
    public java.lang.String getNomeContatoAssociado() {
        return nomeContatoAssociado;
    }


    /**
     * Sets the nomeContatoAssociado value for this Contato.
     * 
     * @param nomeContatoAssociado
     */
    public void setNomeContatoAssociado(java.lang.String nomeContatoAssociado) {
        this.nomeContatoAssociado = nomeContatoAssociado;
    }


    /**
     * Gets the sinEnderecoAssociado value for this Contato.
     * 
     * @return sinEnderecoAssociado
     */
    public java.lang.String getSinEnderecoAssociado() {
        return sinEnderecoAssociado;
    }


    /**
     * Sets the sinEnderecoAssociado value for this Contato.
     * 
     * @param sinEnderecoAssociado
     */
    public void setSinEnderecoAssociado(java.lang.String sinEnderecoAssociado) {
        this.sinEnderecoAssociado = sinEnderecoAssociado;
    }


    /**
     * Gets the enderecoAssociado value for this Contato.
     * 
     * @return enderecoAssociado
     */
    public java.lang.String getEnderecoAssociado() {
        return enderecoAssociado;
    }


    /**
     * Sets the enderecoAssociado value for this Contato.
     * 
     * @param enderecoAssociado
     */
    public void setEnderecoAssociado(java.lang.String enderecoAssociado) {
        this.enderecoAssociado = enderecoAssociado;
    }


    /**
     * Gets the complementoAssociado value for this Contato.
     * 
     * @return complementoAssociado
     */
    public java.lang.String getComplementoAssociado() {
        return complementoAssociado;
    }


    /**
     * Sets the complementoAssociado value for this Contato.
     * 
     * @param complementoAssociado
     */
    public void setComplementoAssociado(java.lang.String complementoAssociado) {
        this.complementoAssociado = complementoAssociado;
    }


    /**
     * Gets the bairroAssociado value for this Contato.
     * 
     * @return bairroAssociado
     */
    public java.lang.String getBairroAssociado() {
        return bairroAssociado;
    }


    /**
     * Sets the bairroAssociado value for this Contato.
     * 
     * @param bairroAssociado
     */
    public void setBairroAssociado(java.lang.String bairroAssociado) {
        this.bairroAssociado = bairroAssociado;
    }


    /**
     * Gets the idCidadeAssociado value for this Contato.
     * 
     * @return idCidadeAssociado
     */
    public java.lang.String getIdCidadeAssociado() {
        return idCidadeAssociado;
    }


    /**
     * Sets the idCidadeAssociado value for this Contato.
     * 
     * @param idCidadeAssociado
     */
    public void setIdCidadeAssociado(java.lang.String idCidadeAssociado) {
        this.idCidadeAssociado = idCidadeAssociado;
    }


    /**
     * Gets the nomeCidadeAssociado value for this Contato.
     * 
     * @return nomeCidadeAssociado
     */
    public java.lang.String getNomeCidadeAssociado() {
        return nomeCidadeAssociado;
    }


    /**
     * Sets the nomeCidadeAssociado value for this Contato.
     * 
     * @param nomeCidadeAssociado
     */
    public void setNomeCidadeAssociado(java.lang.String nomeCidadeAssociado) {
        this.nomeCidadeAssociado = nomeCidadeAssociado;
    }


    /**
     * Gets the idEstadoAssociado value for this Contato.
     * 
     * @return idEstadoAssociado
     */
    public java.lang.String getIdEstadoAssociado() {
        return idEstadoAssociado;
    }


    /**
     * Sets the idEstadoAssociado value for this Contato.
     * 
     * @param idEstadoAssociado
     */
    public void setIdEstadoAssociado(java.lang.String idEstadoAssociado) {
        this.idEstadoAssociado = idEstadoAssociado;
    }


    /**
     * Gets the siglaEstadoAssociado value for this Contato.
     * 
     * @return siglaEstadoAssociado
     */
    public java.lang.String getSiglaEstadoAssociado() {
        return siglaEstadoAssociado;
    }


    /**
     * Sets the siglaEstadoAssociado value for this Contato.
     * 
     * @param siglaEstadoAssociado
     */
    public void setSiglaEstadoAssociado(java.lang.String siglaEstadoAssociado) {
        this.siglaEstadoAssociado = siglaEstadoAssociado;
    }


    /**
     * Gets the idPaisAssociado value for this Contato.
     * 
     * @return idPaisAssociado
     */
    public java.lang.String getIdPaisAssociado() {
        return idPaisAssociado;
    }


    /**
     * Sets the idPaisAssociado value for this Contato.
     * 
     * @param idPaisAssociado
     */
    public void setIdPaisAssociado(java.lang.String idPaisAssociado) {
        this.idPaisAssociado = idPaisAssociado;
    }


    /**
     * Gets the nomePaisAssociado value for this Contato.
     * 
     * @return nomePaisAssociado
     */
    public java.lang.String getNomePaisAssociado() {
        return nomePaisAssociado;
    }


    /**
     * Sets the nomePaisAssociado value for this Contato.
     * 
     * @param nomePaisAssociado
     */
    public void setNomePaisAssociado(java.lang.String nomePaisAssociado) {
        this.nomePaisAssociado = nomePaisAssociado;
    }


    /**
     * Gets the cepAssociado value for this Contato.
     * 
     * @return cepAssociado
     */
    public java.lang.String getCepAssociado() {
        return cepAssociado;
    }


    /**
     * Sets the cepAssociado value for this Contato.
     * 
     * @param cepAssociado
     */
    public void setCepAssociado(java.lang.String cepAssociado) {
        this.cepAssociado = cepAssociado;
    }


    /**
     * Gets the endereco value for this Contato.
     * 
     * @return endereco
     */
    public java.lang.String getEndereco() {
        return endereco;
    }


    /**
     * Sets the endereco value for this Contato.
     * 
     * @param endereco
     */
    public void setEndereco(java.lang.String endereco) {
        this.endereco = endereco;
    }


    /**
     * Gets the complemento value for this Contato.
     * 
     * @return complemento
     */
    public java.lang.String getComplemento() {
        return complemento;
    }


    /**
     * Sets the complemento value for this Contato.
     * 
     * @param complemento
     */
    public void setComplemento(java.lang.String complemento) {
        this.complemento = complemento;
    }


    /**
     * Gets the bairro value for this Contato.
     * 
     * @return bairro
     */
    public java.lang.String getBairro() {
        return bairro;
    }


    /**
     * Sets the bairro value for this Contato.
     * 
     * @param bairro
     */
    public void setBairro(java.lang.String bairro) {
        this.bairro = bairro;
    }


    /**
     * Gets the idCidade value for this Contato.
     * 
     * @return idCidade
     */
    public java.lang.String getIdCidade() {
        return idCidade;
    }


    /**
     * Sets the idCidade value for this Contato.
     * 
     * @param idCidade
     */
    public void setIdCidade(java.lang.String idCidade) {
        this.idCidade = idCidade;
    }


    /**
     * Gets the nomeCidade value for this Contato.
     * 
     * @return nomeCidade
     */
    public java.lang.String getNomeCidade() {
        return nomeCidade;
    }


    /**
     * Sets the nomeCidade value for this Contato.
     * 
     * @param nomeCidade
     */
    public void setNomeCidade(java.lang.String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }


    /**
     * Gets the idEstado value for this Contato.
     * 
     * @return idEstado
     */
    public java.lang.String getIdEstado() {
        return idEstado;
    }


    /**
     * Sets the idEstado value for this Contato.
     * 
     * @param idEstado
     */
    public void setIdEstado(java.lang.String idEstado) {
        this.idEstado = idEstado;
    }


    /**
     * Gets the siglaEstado value for this Contato.
     * 
     * @return siglaEstado
     */
    public java.lang.String getSiglaEstado() {
        return siglaEstado;
    }


    /**
     * Sets the siglaEstado value for this Contato.
     * 
     * @param siglaEstado
     */
    public void setSiglaEstado(java.lang.String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }


    /**
     * Gets the idPais value for this Contato.
     * 
     * @return idPais
     */
    public java.lang.String getIdPais() {
        return idPais;
    }


    /**
     * Sets the idPais value for this Contato.
     * 
     * @param idPais
     */
    public void setIdPais(java.lang.String idPais) {
        this.idPais = idPais;
    }


    /**
     * Gets the nomePais value for this Contato.
     * 
     * @return nomePais
     */
    public java.lang.String getNomePais() {
        return nomePais;
    }


    /**
     * Sets the nomePais value for this Contato.
     * 
     * @param nomePais
     */
    public void setNomePais(java.lang.String nomePais) {
        this.nomePais = nomePais;
    }


    /**
     * Gets the cep value for this Contato.
     * 
     * @return cep
     */
    public java.lang.String getCep() {
        return cep;
    }


    /**
     * Sets the cep value for this Contato.
     * 
     * @param cep
     */
    public void setCep(java.lang.String cep) {
        this.cep = cep;
    }


    /**
     * Gets the staGenero value for this Contato.
     * 
     * @return staGenero
     */
    public java.lang.String getStaGenero() {
        return staGenero;
    }


    /**
     * Sets the staGenero value for this Contato.
     * 
     * @param staGenero
     */
    public void setStaGenero(java.lang.String staGenero) {
        this.staGenero = staGenero;
    }


    /**
     * Gets the idCargo value for this Contato.
     * 
     * @return idCargo
     */
    public java.lang.String getIdCargo() {
        return idCargo;
    }


    /**
     * Sets the idCargo value for this Contato.
     * 
     * @param idCargo
     */
    public void setIdCargo(java.lang.String idCargo) {
        this.idCargo = idCargo;
    }


    /**
     * Gets the expressaoCargo value for this Contato.
     * 
     * @return expressaoCargo
     */
    public java.lang.String getExpressaoCargo() {
        return expressaoCargo;
    }


    /**
     * Sets the expressaoCargo value for this Contato.
     * 
     * @param expressaoCargo
     */
    public void setExpressaoCargo(java.lang.String expressaoCargo) {
        this.expressaoCargo = expressaoCargo;
    }


    /**
     * Gets the expressaoTratamento value for this Contato.
     * 
     * @return expressaoTratamento
     */
    public java.lang.String getExpressaoTratamento() {
        return expressaoTratamento;
    }


    /**
     * Sets the expressaoTratamento value for this Contato.
     * 
     * @param expressaoTratamento
     */
    public void setExpressaoTratamento(java.lang.String expressaoTratamento) {
        this.expressaoTratamento = expressaoTratamento;
    }


    /**
     * Gets the expressaoVocativo value for this Contato.
     * 
     * @return expressaoVocativo
     */
    public java.lang.String getExpressaoVocativo() {
        return expressaoVocativo;
    }


    /**
     * Sets the expressaoVocativo value for this Contato.
     * 
     * @param expressaoVocativo
     */
    public void setExpressaoVocativo(java.lang.String expressaoVocativo) {
        this.expressaoVocativo = expressaoVocativo;
    }


    /**
     * Gets the cpf value for this Contato.
     * 
     * @return cpf
     */
    public java.lang.String getCpf() {
        return cpf;
    }


    /**
     * Sets the cpf value for this Contato.
     * 
     * @param cpf
     */
    public void setCpf(java.lang.String cpf) {
        this.cpf = cpf;
    }


    /**
     * Gets the cnpj value for this Contato.
     * 
     * @return cnpj
     */
    public java.lang.String getCnpj() {
        return cnpj;
    }


    /**
     * Sets the cnpj value for this Contato.
     * 
     * @param cnpj
     */
    public void setCnpj(java.lang.String cnpj) {
        this.cnpj = cnpj;
    }


    /**
     * Gets the rg value for this Contato.
     * 
     * @return rg
     */
    public java.lang.String getRg() {
        return rg;
    }


    /**
     * Sets the rg value for this Contato.
     * 
     * @param rg
     */
    public void setRg(java.lang.String rg) {
        this.rg = rg;
    }


    /**
     * Gets the orgaoExpedidor value for this Contato.
     * 
     * @return orgaoExpedidor
     */
    public java.lang.String getOrgaoExpedidor() {
        return orgaoExpedidor;
    }


    /**
     * Sets the orgaoExpedidor value for this Contato.
     * 
     * @param orgaoExpedidor
     */
    public void setOrgaoExpedidor(java.lang.String orgaoExpedidor) {
        this.orgaoExpedidor = orgaoExpedidor;
    }


    /**
     * Gets the matricula value for this Contato.
     * 
     * @return matricula
     */
    public java.lang.String getMatricula() {
        return matricula;
    }


    /**
     * Sets the matricula value for this Contato.
     * 
     * @param matricula
     */
    public void setMatricula(java.lang.String matricula) {
        this.matricula = matricula;
    }


    /**
     * Gets the matriculaOab value for this Contato.
     * 
     * @return matriculaOab
     */
    public java.lang.String getMatriculaOab() {
        return matriculaOab;
    }


    /**
     * Sets the matriculaOab value for this Contato.
     * 
     * @param matriculaOab
     */
    public void setMatriculaOab(java.lang.String matriculaOab) {
        this.matriculaOab = matriculaOab;
    }


    /**
     * Gets the telefoneFixo value for this Contato.
     * 
     * @return telefoneFixo
     */
    public java.lang.String getTelefoneFixo() {
        return telefoneFixo;
    }


    /**
     * Sets the telefoneFixo value for this Contato.
     * 
     * @param telefoneFixo
     */
    public void setTelefoneFixo(java.lang.String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }


    /**
     * Gets the telefoneCelular value for this Contato.
     * 
     * @return telefoneCelular
     */
    public java.lang.String getTelefoneCelular() {
        return telefoneCelular;
    }


    /**
     * Sets the telefoneCelular value for this Contato.
     * 
     * @param telefoneCelular
     */
    public void setTelefoneCelular(java.lang.String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }


    /**
     * Gets the dataNascimento value for this Contato.
     * 
     * @return dataNascimento
     */
    public java.lang.String getDataNascimento() {
        return dataNascimento;
    }


    /**
     * Sets the dataNascimento value for this Contato.
     * 
     * @param dataNascimento
     */
    public void setDataNascimento(java.lang.String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


    /**
     * Gets the email value for this Contato.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this Contato.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the sitioInternet value for this Contato.
     * 
     * @return sitioInternet
     */
    public java.lang.String getSitioInternet() {
        return sitioInternet;
    }


    /**
     * Sets the sitioInternet value for this Contato.
     * 
     * @param sitioInternet
     */
    public void setSitioInternet(java.lang.String sitioInternet) {
        this.sitioInternet = sitioInternet;
    }


    /**
     * Gets the observacao value for this Contato.
     * 
     * @return observacao
     */
    public java.lang.String getObservacao() {
        return observacao;
    }


    /**
     * Sets the observacao value for this Contato.
     * 
     * @param observacao
     */
    public void setObservacao(java.lang.String observacao) {
        this.observacao = observacao;
    }


    /**
     * Gets the sinAtivo value for this Contato.
     * 
     * @return sinAtivo
     */
    public java.lang.String getSinAtivo() {
        return sinAtivo;
    }


    /**
     * Sets the sinAtivo value for this Contato.
     * 
     * @param sinAtivo
     */
    public void setSinAtivo(java.lang.String sinAtivo) {
        this.sinAtivo = sinAtivo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Contato)) return false;
        Contato other = (Contato) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.staOperacao==null && other.getStaOperacao()==null) || 
             (this.staOperacao!=null &&
              this.staOperacao.equals(other.getStaOperacao()))) &&
            ((this.idContato==null && other.getIdContato()==null) || 
             (this.idContato!=null &&
              this.idContato.equals(other.getIdContato()))) &&
            ((this.idTipoContato==null && other.getIdTipoContato()==null) || 
             (this.idTipoContato!=null &&
              this.idTipoContato.equals(other.getIdTipoContato()))) &&
            ((this.nomeTipoContato==null && other.getNomeTipoContato()==null) || 
             (this.nomeTipoContato!=null &&
              this.nomeTipoContato.equals(other.getNomeTipoContato()))) &&
            ((this.sigla==null && other.getSigla()==null) || 
             (this.sigla!=null &&
              this.sigla.equals(other.getSigla()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.staNatureza==null && other.getStaNatureza()==null) || 
             (this.staNatureza!=null &&
              this.staNatureza.equals(other.getStaNatureza()))) &&
            ((this.idContatoAssociado==null && other.getIdContatoAssociado()==null) || 
             (this.idContatoAssociado!=null &&
              this.idContatoAssociado.equals(other.getIdContatoAssociado()))) &&
            ((this.nomeContatoAssociado==null && other.getNomeContatoAssociado()==null) || 
             (this.nomeContatoAssociado!=null &&
              this.nomeContatoAssociado.equals(other.getNomeContatoAssociado()))) &&
            ((this.sinEnderecoAssociado==null && other.getSinEnderecoAssociado()==null) || 
             (this.sinEnderecoAssociado!=null &&
              this.sinEnderecoAssociado.equals(other.getSinEnderecoAssociado()))) &&
            ((this.enderecoAssociado==null && other.getEnderecoAssociado()==null) || 
             (this.enderecoAssociado!=null &&
              this.enderecoAssociado.equals(other.getEnderecoAssociado()))) &&
            ((this.complementoAssociado==null && other.getComplementoAssociado()==null) || 
             (this.complementoAssociado!=null &&
              this.complementoAssociado.equals(other.getComplementoAssociado()))) &&
            ((this.bairroAssociado==null && other.getBairroAssociado()==null) || 
             (this.bairroAssociado!=null &&
              this.bairroAssociado.equals(other.getBairroAssociado()))) &&
            ((this.idCidadeAssociado==null && other.getIdCidadeAssociado()==null) || 
             (this.idCidadeAssociado!=null &&
              this.idCidadeAssociado.equals(other.getIdCidadeAssociado()))) &&
            ((this.nomeCidadeAssociado==null && other.getNomeCidadeAssociado()==null) || 
             (this.nomeCidadeAssociado!=null &&
              this.nomeCidadeAssociado.equals(other.getNomeCidadeAssociado()))) &&
            ((this.idEstadoAssociado==null && other.getIdEstadoAssociado()==null) || 
             (this.idEstadoAssociado!=null &&
              this.idEstadoAssociado.equals(other.getIdEstadoAssociado()))) &&
            ((this.siglaEstadoAssociado==null && other.getSiglaEstadoAssociado()==null) || 
             (this.siglaEstadoAssociado!=null &&
              this.siglaEstadoAssociado.equals(other.getSiglaEstadoAssociado()))) &&
            ((this.idPaisAssociado==null && other.getIdPaisAssociado()==null) || 
             (this.idPaisAssociado!=null &&
              this.idPaisAssociado.equals(other.getIdPaisAssociado()))) &&
            ((this.nomePaisAssociado==null && other.getNomePaisAssociado()==null) || 
             (this.nomePaisAssociado!=null &&
              this.nomePaisAssociado.equals(other.getNomePaisAssociado()))) &&
            ((this.cepAssociado==null && other.getCepAssociado()==null) || 
             (this.cepAssociado!=null &&
              this.cepAssociado.equals(other.getCepAssociado()))) &&
            ((this.endereco==null && other.getEndereco()==null) || 
             (this.endereco!=null &&
              this.endereco.equals(other.getEndereco()))) &&
            ((this.complemento==null && other.getComplemento()==null) || 
             (this.complemento!=null &&
              this.complemento.equals(other.getComplemento()))) &&
            ((this.bairro==null && other.getBairro()==null) || 
             (this.bairro!=null &&
              this.bairro.equals(other.getBairro()))) &&
            ((this.idCidade==null && other.getIdCidade()==null) || 
             (this.idCidade!=null &&
              this.idCidade.equals(other.getIdCidade()))) &&
            ((this.nomeCidade==null && other.getNomeCidade()==null) || 
             (this.nomeCidade!=null &&
              this.nomeCidade.equals(other.getNomeCidade()))) &&
            ((this.idEstado==null && other.getIdEstado()==null) || 
             (this.idEstado!=null &&
              this.idEstado.equals(other.getIdEstado()))) &&
            ((this.siglaEstado==null && other.getSiglaEstado()==null) || 
             (this.siglaEstado!=null &&
              this.siglaEstado.equals(other.getSiglaEstado()))) &&
            ((this.idPais==null && other.getIdPais()==null) || 
             (this.idPais!=null &&
              this.idPais.equals(other.getIdPais()))) &&
            ((this.nomePais==null && other.getNomePais()==null) || 
             (this.nomePais!=null &&
              this.nomePais.equals(other.getNomePais()))) &&
            ((this.cep==null && other.getCep()==null) || 
             (this.cep!=null &&
              this.cep.equals(other.getCep()))) &&
            ((this.staGenero==null && other.getStaGenero()==null) || 
             (this.staGenero!=null &&
              this.staGenero.equals(other.getStaGenero()))) &&
            ((this.idCargo==null && other.getIdCargo()==null) || 
             (this.idCargo!=null &&
              this.idCargo.equals(other.getIdCargo()))) &&
            ((this.expressaoCargo==null && other.getExpressaoCargo()==null) || 
             (this.expressaoCargo!=null &&
              this.expressaoCargo.equals(other.getExpressaoCargo()))) &&
            ((this.expressaoTratamento==null && other.getExpressaoTratamento()==null) || 
             (this.expressaoTratamento!=null &&
              this.expressaoTratamento.equals(other.getExpressaoTratamento()))) &&
            ((this.expressaoVocativo==null && other.getExpressaoVocativo()==null) || 
             (this.expressaoVocativo!=null &&
              this.expressaoVocativo.equals(other.getExpressaoVocativo()))) &&
            ((this.cpf==null && other.getCpf()==null) || 
             (this.cpf!=null &&
              this.cpf.equals(other.getCpf()))) &&
            ((this.cnpj==null && other.getCnpj()==null) || 
             (this.cnpj!=null &&
              this.cnpj.equals(other.getCnpj()))) &&
            ((this.rg==null && other.getRg()==null) || 
             (this.rg!=null &&
              this.rg.equals(other.getRg()))) &&
            ((this.orgaoExpedidor==null && other.getOrgaoExpedidor()==null) || 
             (this.orgaoExpedidor!=null &&
              this.orgaoExpedidor.equals(other.getOrgaoExpedidor()))) &&
            ((this.matricula==null && other.getMatricula()==null) || 
             (this.matricula!=null &&
              this.matricula.equals(other.getMatricula()))) &&
            ((this.matriculaOab==null && other.getMatriculaOab()==null) || 
             (this.matriculaOab!=null &&
              this.matriculaOab.equals(other.getMatriculaOab()))) &&
            ((this.telefoneFixo==null && other.getTelefoneFixo()==null) || 
             (this.telefoneFixo!=null &&
              this.telefoneFixo.equals(other.getTelefoneFixo()))) &&
            ((this.telefoneCelular==null && other.getTelefoneCelular()==null) || 
             (this.telefoneCelular!=null &&
              this.telefoneCelular.equals(other.getTelefoneCelular()))) &&
            ((this.dataNascimento==null && other.getDataNascimento()==null) || 
             (this.dataNascimento!=null &&
              this.dataNascimento.equals(other.getDataNascimento()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.sitioInternet==null && other.getSitioInternet()==null) || 
             (this.sitioInternet!=null &&
              this.sitioInternet.equals(other.getSitioInternet()))) &&
            ((this.observacao==null && other.getObservacao()==null) || 
             (this.observacao!=null &&
              this.observacao.equals(other.getObservacao()))) &&
            ((this.sinAtivo==null && other.getSinAtivo()==null) || 
             (this.sinAtivo!=null &&
              this.sinAtivo.equals(other.getSinAtivo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getStaOperacao() != null) {
            _hashCode += getStaOperacao().hashCode();
        }
        if (getIdContato() != null) {
            _hashCode += getIdContato().hashCode();
        }
        if (getIdTipoContato() != null) {
            _hashCode += getIdTipoContato().hashCode();
        }
        if (getNomeTipoContato() != null) {
            _hashCode += getNomeTipoContato().hashCode();
        }
        if (getSigla() != null) {
            _hashCode += getSigla().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getStaNatureza() != null) {
            _hashCode += getStaNatureza().hashCode();
        }
        if (getIdContatoAssociado() != null) {
            _hashCode += getIdContatoAssociado().hashCode();
        }
        if (getNomeContatoAssociado() != null) {
            _hashCode += getNomeContatoAssociado().hashCode();
        }
        if (getSinEnderecoAssociado() != null) {
            _hashCode += getSinEnderecoAssociado().hashCode();
        }
        if (getEnderecoAssociado() != null) {
            _hashCode += getEnderecoAssociado().hashCode();
        }
        if (getComplementoAssociado() != null) {
            _hashCode += getComplementoAssociado().hashCode();
        }
        if (getBairroAssociado() != null) {
            _hashCode += getBairroAssociado().hashCode();
        }
        if (getIdCidadeAssociado() != null) {
            _hashCode += getIdCidadeAssociado().hashCode();
        }
        if (getNomeCidadeAssociado() != null) {
            _hashCode += getNomeCidadeAssociado().hashCode();
        }
        if (getIdEstadoAssociado() != null) {
            _hashCode += getIdEstadoAssociado().hashCode();
        }
        if (getSiglaEstadoAssociado() != null) {
            _hashCode += getSiglaEstadoAssociado().hashCode();
        }
        if (getIdPaisAssociado() != null) {
            _hashCode += getIdPaisAssociado().hashCode();
        }
        if (getNomePaisAssociado() != null) {
            _hashCode += getNomePaisAssociado().hashCode();
        }
        if (getCepAssociado() != null) {
            _hashCode += getCepAssociado().hashCode();
        }
        if (getEndereco() != null) {
            _hashCode += getEndereco().hashCode();
        }
        if (getComplemento() != null) {
            _hashCode += getComplemento().hashCode();
        }
        if (getBairro() != null) {
            _hashCode += getBairro().hashCode();
        }
        if (getIdCidade() != null) {
            _hashCode += getIdCidade().hashCode();
        }
        if (getNomeCidade() != null) {
            _hashCode += getNomeCidade().hashCode();
        }
        if (getIdEstado() != null) {
            _hashCode += getIdEstado().hashCode();
        }
        if (getSiglaEstado() != null) {
            _hashCode += getSiglaEstado().hashCode();
        }
        if (getIdPais() != null) {
            _hashCode += getIdPais().hashCode();
        }
        if (getNomePais() != null) {
            _hashCode += getNomePais().hashCode();
        }
        if (getCep() != null) {
            _hashCode += getCep().hashCode();
        }
        if (getStaGenero() != null) {
            _hashCode += getStaGenero().hashCode();
        }
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
        if (getCpf() != null) {
            _hashCode += getCpf().hashCode();
        }
        if (getCnpj() != null) {
            _hashCode += getCnpj().hashCode();
        }
        if (getRg() != null) {
            _hashCode += getRg().hashCode();
        }
        if (getOrgaoExpedidor() != null) {
            _hashCode += getOrgaoExpedidor().hashCode();
        }
        if (getMatricula() != null) {
            _hashCode += getMatricula().hashCode();
        }
        if (getMatriculaOab() != null) {
            _hashCode += getMatriculaOab().hashCode();
        }
        if (getTelefoneFixo() != null) {
            _hashCode += getTelefoneFixo().hashCode();
        }
        if (getTelefoneCelular() != null) {
            _hashCode += getTelefoneCelular().hashCode();
        }
        if (getDataNascimento() != null) {
            _hashCode += getDataNascimento().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getSitioInternet() != null) {
            _hashCode += getSitioInternet().hashCode();
        }
        if (getObservacao() != null) {
            _hashCode += getObservacao().hashCode();
        }
        if (getSinAtivo() != null) {
            _hashCode += getSinAtivo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Contato.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("Sei", "Contato"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("staOperacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "StaOperacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idContato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdContato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoContato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdTipoContato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeTipoContato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NomeTipoContato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sigla");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Sigla"));
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
        elemField.setFieldName("staNatureza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "StaNatureza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idContatoAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdContatoAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeContatoAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NomeContatoAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sinEnderecoAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SinEnderecoAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enderecoAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EnderecoAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complementoAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ComplementoAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bairroAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BairroAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCidadeAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdCidadeAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCidadeAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NomeCidadeAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEstadoAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdEstadoAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siglaEstadoAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SiglaEstadoAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPaisAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdPaisAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomePaisAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NomePaisAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cepAssociado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CepAssociado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endereco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Endereco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complemento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Complemento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bairro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Bairro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCidade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdCidade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCidade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NomeCidade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siglaEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SiglaEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPais");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdPais"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomePais");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NomePais"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cep");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Cep"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("staGenero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "StaGenero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCargo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IdCargo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expressaoCargo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ExpressaoCargo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expressaoTratamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ExpressaoTratamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expressaoVocativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ExpressaoVocativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cpf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Cpf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cnpj");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Cnpj"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Rg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgaoExpedidor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OrgaoExpedidor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matricula");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Matricula"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matriculaOab");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MatriculaOab"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telefoneFixo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TelefoneFixo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telefoneCelular");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TelefoneCelular"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataNascimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DataNascimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sitioInternet");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SitioInternet"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("observacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Observacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sinAtivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SinAtivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
           @SuppressWarnings("rawtypes") java.lang.Class _javaType,  
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
           @SuppressWarnings("rawtypes") java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
