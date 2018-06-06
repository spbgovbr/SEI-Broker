package br.gov.ans.integracao.sei.client;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import br.gov.ans.factories.qualifiers.SeiQualifiers.SeiParameter;

public class SeiPortTypeProxy implements br.gov.ans.integracao.sei.client.SeiPortType {

	private br.gov.ans.integracao.sei.client.SeiPortType seiPortType = null;

	@ApplicationScoped
	@Inject
	@SeiParameter
	private String _endpoint;

	@Inject
	private Logger logger;

	public SeiPortTypeProxy() {
	}

	public SeiPortTypeProxy(String endpoint) {
		_endpoint = endpoint;
		_initSeiPortTypeProxy();
	}

	private void _initSeiPortTypeProxy() {
		try {
			seiPortType = (new br.gov.ans.integracao.sei.client.SeiServiceLocator()).getSeiPortService(_endpoint);
			if (seiPortType != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) seiPortType)._setProperty("javax.xml.rpc.service.endpoint.address",
							_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) seiPortType)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (Exception serviceException) {
			logger.error(serviceException);
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (seiPortType != null)
			((javax.xml.rpc.Stub) seiPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public br.gov.ans.integracao.sei.client.SeiPortType getSeiPortType() {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType;
	}

	public br.gov.ans.integracao.sei.client.RetornoGeracaoProcedimento gerarProcedimento(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade,
			br.gov.ans.integracao.sei.client.Procedimento procedimento,
			br.gov.ans.integracao.sei.client.Documento[] documentos, java.lang.String[] procedimentosRelacionados,
			java.lang.String[] unidadesEnvio, java.lang.String sinManterAbertoUnidade,
			java.lang.String sinEnviarEmailNotificacao, java.lang.String dataRetornoProgramado,
			java.lang.String diasRetornoProgramado, java.lang.String sinDiasUteisRetornoProgramado,
			java.lang.String idMarcador, java.lang.String textoMarcador) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.gerarProcedimento(siglaSistema, identificacaoServico, idUnidade, procedimento, documentos,
				procedimentosRelacionados, unidadesEnvio, sinManterAbertoUnidade, sinEnviarEmailNotificacao,
				dataRetornoProgramado, diasRetornoProgramado, sinDiasUteisRetornoProgramado, idMarcador, textoMarcador);
	}

	public br.gov.ans.integracao.sei.client.RetornoInclusaoDocumento incluirDocumento(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade,
			br.gov.ans.integracao.sei.client.Documento documento) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.incluirDocumento(siglaSistema, identificacaoServico, idUnidade, documento);
	}

	public br.gov.ans.integracao.sei.client.Unidade[] listarUnidades(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idTipoProcedimento, java.lang.String idSerie)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarUnidades(siglaSistema, identificacaoServico, idTipoProcedimento, idSerie);
	}

	public br.gov.ans.integracao.sei.client.TipoProcedimento[] listarTiposProcedimento(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idSerie)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarTiposProcedimento(siglaSistema, identificacaoServico, idUnidade, idSerie);
	}

	public br.gov.ans.integracao.sei.client.Serie[] listarSeries(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idTipoProcedimento)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarSeries(siglaSistema, identificacaoServico, idUnidade, idTipoProcedimento);
	}

	public br.gov.ans.integracao.sei.client.Contato[] listarContatos(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idTipoContato,
			java.lang.String paginaRegistros, java.lang.String paginaAtual, java.lang.String sigla,
			java.lang.String nome, java.lang.String cpf, java.lang.String cnpj, java.lang.String matricula,
			java.lang.String[] idContatos) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarContatos(siglaSistema, identificacaoServico, idUnidade, idTipoContato, paginaRegistros,
				paginaAtual, sigla, nome, cpf, cnpj, matricula, idContatos);
	}

	public java.lang.String atualizarContatos(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, br.gov.ans.integracao.sei.client.Contato[] contatos)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.atualizarContatos(siglaSistema, identificacaoServico, idUnidade, contatos);
	}

	public br.gov.ans.integracao.sei.client.RetornoConsultaProcedimento consultarProcedimento(
			java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade,
			java.lang.String protocoloProcedimento, java.lang.String sinRetornarAssuntos,
			java.lang.String sinRetornarInteressados, java.lang.String sinRetornarObservacoes,
			java.lang.String sinRetornarAndamentoGeracao, java.lang.String sinRetornarAndamentoConclusao,
			java.lang.String sinRetornarUltimoAndamento, java.lang.String sinRetornarUnidadesProcedimentoAberto,
			java.lang.String sinRetornarProcedimentosRelacionados, java.lang.String sinRetornarProcedimentosAnexados)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.consultarProcedimento(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento,
				sinRetornarAssuntos, sinRetornarInteressados, sinRetornarObservacoes, sinRetornarAndamentoGeracao,
				sinRetornarAndamentoConclusao, sinRetornarUltimoAndamento, sinRetornarUnidadesProcedimentoAberto,
				sinRetornarProcedimentosRelacionados, sinRetornarProcedimentosAnexados);
	}

	public ProcedimentoResumido consultarProcedimentoIndividual(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idOrgaoProcedimento,
			java.lang.String idTipoProcedimento, java.lang.String idOrgaoUsuario, java.lang.String siglaUsuario)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.consultarProcedimentoIndividual(siglaSistema, identificacaoServico, idUnidade,
				idOrgaoProcedimento, idTipoProcedimento, idOrgaoUsuario, siglaUsuario);
	}

	public RetornoConsultaDocumento consultarDocumento(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloDocumento,
			java.lang.String sinRetornarAndamentoGeracao, java.lang.String sinRetornarAssinaturas,
			java.lang.String sinRetornarPublicacao, java.lang.String sinRetornarCampos)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.consultarDocumento(siglaSistema, identificacaoServico, idUnidade, protocoloDocumento,
				sinRetornarAndamentoGeracao, sinRetornarAssinaturas, sinRetornarPublicacao, sinRetornarCampos);
	}

	public java.lang.String cancelarDocumento(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloDocumento, java.lang.String motivo)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.cancelarDocumento(siglaSistema, identificacaoServico, idUnidade, protocoloDocumento, motivo);
	}

	public java.lang.String gerarBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String tipo, java.lang.String descricao,
			java.lang.String[] unidadesDisponibilizacao, java.lang.String[] documentos,
			java.lang.String sinDisponibilizar) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.gerarBloco(siglaSistema, identificacaoServico, idUnidade, tipo, descricao,
				unidadesDisponibilizacao, documentos, sinDisponibilizar);
	}

	public br.gov.ans.integracao.sei.client.RetornoConsultaBloco consultarBloco(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco,
			java.lang.String sinRetornarProtocolos) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.consultarBloco(siglaSistema, identificacaoServico, idUnidade, idBloco,
				sinRetornarProtocolos);
	}

	public java.lang.String excluirBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String idBloco) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.excluirBloco(siglaSistema, identificacaoServico, idUnidade, idBloco);
	}

	public java.lang.String disponibilizarBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String idBloco) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.disponibilizarBloco(siglaSistema, identificacaoServico, idUnidade, idBloco);
	}

	public java.lang.String cancelarDisponibilizacaoBloco(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idBloco)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.cancelarDisponibilizacaoBloco(siglaSistema, identificacaoServico, idUnidade, idBloco);
	}

	public java.lang.String incluirDocumentoBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String idBloco, java.lang.String protocoloDocumento,
			java.lang.String anotacao) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.incluirDocumentoBloco(siglaSistema, identificacaoServico, idUnidade, idBloco,
				protocoloDocumento, anotacao);
	}

	public java.lang.String retirarDocumentoBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String idBloco, java.lang.String protocoloDocumento)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.retirarDocumentoBloco(siglaSistema, identificacaoServico, idUnidade, idBloco,
				protocoloDocumento);
	}

	public java.lang.String incluirProcessoBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String idBloco, java.lang.String protocoloProcedimento,
			java.lang.String anotacao) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.incluirProcessoBloco(siglaSistema, identificacaoServico, idUnidade, idBloco,
				protocoloProcedimento, anotacao);
	}

	public java.lang.String retirarProcessoBloco(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String idBloco, java.lang.String protocoloProcedimento)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.retirarProcessoBloco(siglaSistema, identificacaoServico, idUnidade, idBloco,
				protocoloProcedimento);
	}

	public java.lang.String reabrirProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimento) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.reabrirProcesso(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento);
	}

	public java.lang.String concluirProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimento) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.concluirProcesso(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento);
	}

	public br.gov.ans.integracao.sei.client.ArquivoExtensao[] listarExtensoesPermitidas(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idArquivoExtensao)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarExtensoesPermitidas(siglaSistema, identificacaoServico, idUnidade, idArquivoExtensao);
	}

	public java.lang.String enviarProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimento, java.lang.String[] unidadesDestino,
			java.lang.String sinManterAbertoUnidade, java.lang.String sinRemoverAnotacao,
			java.lang.String sinEnviarEmailNotificacao, java.lang.String dataRetornoProgramado,
			java.lang.String diasRetornoProgramado, java.lang.String sinDiasUteisRetornoProgramado,
			java.lang.String sinReabrir) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.enviarProcesso(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento,
				unidadesDestino, sinManterAbertoUnidade, sinRemoverAnotacao, sinEnviarEmailNotificacao,
				dataRetornoProgramado, diasRetornoProgramado, sinDiasUteisRetornoProgramado, sinReabrir);
	}

	public br.gov.ans.integracao.sei.client.Usuario[] listarUsuarios(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idUsuario)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarUsuarios(siglaSistema, identificacaoServico, idUnidade, idUsuario);
	}

	public java.lang.String atribuirProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimento, java.lang.String idUsuario,
			java.lang.String sinReabrir) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.atribuirProcesso(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento,
				idUsuario, sinReabrir);
	}

	public br.gov.ans.integracao.sei.client.HipoteseLegal[] listarHipotesesLegais(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String nivelAcesso)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarHipotesesLegais(siglaSistema, identificacaoServico, idUnidade, nivelAcesso);
	}

	public br.gov.ans.integracao.sei.client.TipoConferencia[] listarTiposConferencia(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarTiposConferencia(siglaSistema, identificacaoServico, idUnidade);
	}

	public br.gov.ans.integracao.sei.client.Pais[] listarPaises(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarPaises(siglaSistema, identificacaoServico, idUnidade);
	}

	public br.gov.ans.integracao.sei.client.Estado[] listarEstados(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idPais)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarEstados(siglaSistema, identificacaoServico, idUnidade, idPais);
	}

	public br.gov.ans.integracao.sei.client.Cidade[] listarCidades(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idPais,
			java.lang.String idEstado) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarCidades(siglaSistema, identificacaoServico, idUnidade, idPais, idEstado);
	}

	public br.gov.ans.integracao.sei.client.Cargo[] listarCargos(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idCargo)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarCargos(siglaSistema, identificacaoServico, idUnidade, idCargo);
	}

	public java.lang.String adicionarArquivo(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String nome, java.lang.String tamanho, java.lang.String hash,
			java.lang.String conteudo) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.adicionarArquivo(siglaSistema, identificacaoServico, idUnidade, nome, tamanho, hash,
				conteudo);
	}

	public java.lang.String adicionarConteudoArquivo(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String idArquivo,
			java.lang.String conteudo) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.adicionarConteudoArquivo(siglaSistema, identificacaoServico, idUnidade, idArquivo, conteudo);
	}

	public br.gov.ans.integracao.sei.client.Andamento lancarAndamento(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloProcedimento,
			java.lang.String idTarefa, java.lang.String idTarefaModulo,
			br.gov.ans.integracao.sei.client.AtributoAndamento[] atributos) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.lancarAndamento(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento,
				idTarefa, idTarefaModulo, atributos);
	}

	public br.gov.ans.integracao.sei.client.Andamento[] listarAndamentos(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloProcedimento,
			java.lang.String sinRetornarAtributos, java.lang.String[] andamentos, java.lang.String[] tarefas,
			java.lang.String[] tarefasModulos) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarAndamentos(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento,
				sinRetornarAtributos, andamentos, tarefas, tarefasModulos);
	}

	public java.lang.String bloquearProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimento) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.bloquearProcesso(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento);
	}

	public java.lang.String desbloquearProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimento) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.desbloquearProcesso(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento);
	}

	public java.lang.String relacionarProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimento1,
			java.lang.String protocoloProcedimento2) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.relacionarProcesso(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento1,
				protocoloProcedimento2);
	}

	public java.lang.String removerRelacionamentoProcesso(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloProcedimento1,
			java.lang.String protocoloProcedimento2) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.removerRelacionamentoProcesso(siglaSistema, identificacaoServico, idUnidade,
				protocoloProcedimento1, protocoloProcedimento2);
	}

	public java.lang.String sobrestarProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimento,
			java.lang.String protocoloProcedimentoVinculado, java.lang.String motivo) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.sobrestarProcesso(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimento,
				protocoloProcedimentoVinculado, motivo);
	}

	public java.lang.String removerSobrestamentoProcesso(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade, java.lang.String protocoloProcedimento)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.removerSobrestamentoProcesso(siglaSistema, identificacaoServico, idUnidade,
				protocoloProcedimento);
	}

	public java.lang.String anexarProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimentoPrincipal,
			java.lang.String protocoloProcedimentoAnexado) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.anexarProcesso(siglaSistema, identificacaoServico, idUnidade, protocoloProcedimentoPrincipal,
				protocoloProcedimentoAnexado);
	}

	public java.lang.String desanexarProcesso(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, java.lang.String protocoloProcedimentoPrincipal,
			java.lang.String protocoloProcedimentoAnexado, java.lang.String motivo) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.desanexarProcesso(siglaSistema, identificacaoServico, idUnidade,
				protocoloProcedimentoPrincipal, protocoloProcedimentoAnexado, motivo);
	}

	public br.gov.ans.integracao.sei.client.Marcador[] listarMarcadoresUnidade(java.lang.String siglaSistema,
			java.lang.String identificacaoServico, java.lang.String idUnidade) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarMarcadoresUnidade(siglaSistema, identificacaoServico, idUnidade);
	}

	public java.lang.String definirMarcador(java.lang.String siglaSistema, java.lang.String identificacaoServico,
			java.lang.String idUnidade, br.gov.ans.integracao.sei.client.DefinicaoMarcador[] definicoes)
			throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.definirMarcador(siglaSistema, identificacaoServico, idUnidade, definicoes);
	}

	public br.gov.ans.integracao.sei.client.AndamentoMarcador[] listarAndamentosMarcadores(
			java.lang.String siglaSistema, java.lang.String identificacaoServico, java.lang.String idUnidade,
			java.lang.String protocoloProcedimento, java.lang.String[] marcadores) throws java.rmi.RemoteException {
		if (seiPortType == null)
			_initSeiPortTypeProxy();
		return seiPortType.listarAndamentosMarcadores(siglaSistema, identificacaoServico, idUnidade,
				protocoloProcedimento, marcadores);
	}

}