/**
 * SeiPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.gov.ans.integracao.sei.client;

public interface SeiPortType extends java.rmi.Remote {

    /**
     * Geracao de processos
     */
    public br.gov.ans.integracao.sei.client.RetornoGeracaoProcedimento gerarProcedimento(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, br.gov.ans.integracao.sei.client.Procedimento procedimento, br.gov.ans.integracao.sei.client.Documento[] documentos, java.lang.String[] procedimentosRelacionados, java.lang.String[] unidadesEnvio, java.lang.String sinManterAbertoUnidade, java.lang.String sinEnviarEmailNotificacao, java.lang.String dataRetornoProgramado, java.lang.String diasRetornoProgramado, java.lang.String sinDiasUteisRetornoProgramado) throws java.rmi.RemoteException;

    /**
     * Geracao de documentos
     */
    public br.gov.ans.integracao.sei.client.RetornoInclusaoDocumento incluirDocumento(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, br.gov.ans.integracao.sei.client.Documento documento) throws java.rmi.RemoteException;

    /**
     * Lista de unidades disponiveis
     */
    public br.gov.ans.integracao.sei.client.Unidade[] listarUnidades(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idTipoProcedimento, java.lang.String idSerie) throws java.rmi.RemoteException;

    /**
     * Lista de tipos de processo disponiveis
     */
    public br.gov.ans.integracao.sei.client.TipoProcedimento[] listarTiposProcedimento(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idSerie) throws java.rmi.RemoteException;

    /**
     * Lista de series disponiveis
     */
    public br.gov.ans.integracao.sei.client.Serie[] listarSeries(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idTipoProcedimento) throws java.rmi.RemoteException;

    /**
     * Consulta de processos
     */
    public br.gov.ans.integracao.sei.client.RetornoConsultaProcedimento consultarProcedimento(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloProcedimento, java.lang.String sinRetornarAssuntos, java.lang.String sinRetornarInteressados, java.lang.String sinRetornarObservacoes, java.lang.String sinRetornarAndamentoGeracao, java.lang.String sinRetornarAndamentoConclusao, java.lang.String sinRetornarUltimoAndamento, java.lang.String sinRetornarUnidadesProcedimentoAberto, java.lang.String sinRetornarProcedimentosRelacionados, java.lang.String sinRetornarProcedimentosAnexados) throws java.rmi.RemoteException;

    /**
     * Consulta de documentos
     */
    public br.gov.ans.integracao.sei.client.RetornoConsultaDocumento consultarDocumento(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloDocumento, java.lang.String sinRetornarAndamentoGeracao, java.lang.String sinRetornarAssinaturas, java.lang.String sinRetornarPublicacao) throws java.rmi.RemoteException;

    /**
     * Cancelamento de documentos
     */
    public java.lang.String cancelarDocumento(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloDocumento, java.lang.String motivo) throws java.rmi.RemoteException;

    /**
     * Geracao de bloco
     */
    public java.lang.String gerarBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String tipo, java.lang.String descricao, java.lang.String[] unidadesDisponibilizacao, java.lang.String[] documentos, java.lang.String sinDisponibilizar) throws java.rmi.RemoteException;

    /**
     * Consulta de bloco
     */
    public br.gov.ans.integracao.sei.client.RetornoConsultaBloco consultarBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco, java.lang.String sinRetornarProtocolos) throws java.rmi.RemoteException;

    /**
     * Exclusao de bloco
     */
    public java.lang.String excluirBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco) throws java.rmi.RemoteException;

    /**
     * Disponibilizacao de bloco
     */
    public java.lang.String disponibilizarBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco) throws java.rmi.RemoteException;

    /**
     * Cancelamento de disponibilizacao de bloco
     */
    public java.lang.String cancelarDisponibilizacaoBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco) throws java.rmi.RemoteException;

    /**
     * Inclusao de documento em bloco
     */
    public java.lang.String incluirDocumentoBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco, java.lang.String protocoloDocumento) throws java.rmi.RemoteException;

    /**
     * Remocao de documento de bloco
     */
    public java.lang.String retirarDocumentoBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco, java.lang.String protocoloDocumento) throws java.rmi.RemoteException;

    /**
     * Inclusao de processo em bloco
     */
    public java.lang.String incluirProcessoBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco, java.lang.String protocoloProcedimento) throws java.rmi.RemoteException;

    /**
     * Remocao de processo de bloco
     */
    public java.lang.String retirarProcessoBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco, java.lang.String protocoloProcedimento) throws java.rmi.RemoteException;

    /**
     * Reabertura de processo
     */
    public java.lang.String reabrirProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloProcedimento) throws java.rmi.RemoteException;

    /**
     * Conclusao de processo
     */
    public java.lang.String concluirProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloProcedimento) throws java.rmi.RemoteException;

    /**
     * Lista de extensoes de arquivos permitidas
     */
    public br.gov.ans.integracao.sei.client.ArquivoExtensao[] listarExtensoesPermitidas(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idArquivoExtensao) throws java.rmi.RemoteException;

    /**
     * Movimentacao de processo entre unidades
     */
    public java.lang.String enviarProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloProcedimento, java.lang.String[] unidadesDestino, java.lang.String sinManterAbertoUnidade, java.lang.String sinRemoverAnotacao, java.lang.String sinEnviarEmailNotificacao, java.lang.String dataRetornoProgramado, java.lang.String diasRetornoProgramado, java.lang.String sinDiasUteisRetornoProgramado) throws java.rmi.RemoteException;

    /**
     * Lista de usuarios com permissao na unidade
     */
    public br.gov.ans.integracao.sei.client.Usuario[] listarUsuarios(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idUsuario) throws java.rmi.RemoteException;

    /**
     * Atribuicao de processo para usuario na unidade
     */
    public java.lang.String atribuirProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloProcedimento, java.lang.String idUsuario, java.lang.String sinReabrir) throws java.rmi.RemoteException;
}
