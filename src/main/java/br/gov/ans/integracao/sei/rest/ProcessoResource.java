package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.formatarData;
import static br.gov.ans.integracao.sei.utils.Util.formatarNumeroProcesso;
import static br.gov.ans.integracao.sei.utils.Util.getSOuN;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;

import java.rmi.RemoteException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import br.gov.ans.integracao.sei.client.RetornoConsultaProcedimento;
import br.gov.ans.integracao.sei.client.RetornoGeracaoProcedimento;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.client.TipoProcedimento;
import br.gov.ans.integracao.sei.dao.DocumentoSiparDAO;
import br.gov.ans.integracao.sei.modelo.DocumentoSipar;
import br.gov.ans.integracao.sei.modelo.EnvioDeProcesso;
import br.gov.ans.integracao.sei.modelo.NovoProcesso;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.modelo.ResultadoConsultaProcesso;
import br.gov.ans.integracao.sei.utils.Constantes;


@Path("{unidade}/processos")
@Stateless
public class ProcessoResource {
	
    @Inject
    private DocumentoSiparDAO documentoSiparDAO;
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
      
    @Inject
    private Logger logger;
    
	/**
	 * @api {get} /:unidade/processos/:processo Consultar processo
	 * @apiName consultarProcesso
	 * @apiGroup Processo
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza uma consulta a processos no SEI e no SIPAR.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} processo Número do processo que deseja consultar
	 * @apiParam {String} [assuntos=N] S ou N para exibir assuntos do processo
	 * @apiParam {String} [interessados=N] S ou N para exibir interessados no processo
	 * @apiParam {String} [observacoes=N] S ou N para exibir observações feitas no processo
	 * @apiParam {String} [andamento=N] S ou N para exibir andamento do processo
	 * @apiParam {String} [andamento-conclusao=N] S ou N para exibir o andamento da conclusão do processo
	 * @apiParam {String} [ultimo-andamento=N] S ou N para exibir o último andamento dado ao processo
	 * @apiParam {String} [unidades=N] S ou N para exibir unidades onde o processo está aberto
	 * @apiParam {String} [relacionados=N] S ou N para exibir processos relacionados
	 * @apiParam {String} [anexados=N] S ou N para exibir processos anexados
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i http://anshmjboss01/sei-broker/service/COSAP/processos/33910000029201653
	 *
	 * @apiSuccess {ResultadoConsultaProcesso} resultadoConsultaProcesso Objeto de retorno da consulta aos processos, pode um conter processo do SEI ou do SIPAR
	 * @apiSuccess {RetornoConsultaProcedimento} resultadoConsultaProcesso.sei Resultado de processo do SEI
	 * @apiSuccess {Andamento} resultadoConsultaProcesso.sei.andamentoGeracao Andamento da geração do processo (opcional)
	 * @apiSuccess {Data} resultadoConsultaProcesso.sei.andamentoGeracao.dataHora Data e hora do registro de andamento
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.andamentoGeracao.descricao Descrição do andamento
	 * @apiSuccess {Unidade} resultadoConsultaProcesso.sei.andamentoGeracao.unidade Unidade responsável pelo andamento
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.andamentoGeracao.unidade.descricao Nome da unidade
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.andamentoGeracao.unidade.idUnidade Código da unidade
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.andamentoGeracao.unidade.sigla Sigla da unidade
	 * @apiSuccess {Usuario} resultadoConsultaProcesso.sei.andamentoGeracao.usuario Usuário responsável pela ação
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.andamentoGeracao.usuario.idUsuario Código do usuário
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.andamentoGeracao.usuario.nome Nome do usuário
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.andamentoGeracao.usuario.sigla Login do usuário
	 * @apiSuccess {Data} resultadoConsultaProcesso.sei.dataAutuacao Data de autuação do processo
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.especificacao Especificação do processo
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.idProcedimento Id interno do processo no SEI
	 * @apiSuccess {Interessado} resultadoConsultaProcesso.sei.interessados Lista de interessados no processo (opcional)
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.interessados.nome Nome do interessado
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.interessados.sigla Login do interessado
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.idProcedimento Id interno do processo no SEI
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.linkAcesso Link para acesso ao processo
	 * @apiSuccess {Observacao} resultadoConsultaProcesso.sei.observacoes Observações feitas sobre o processo (opcional)
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.observacoes.descricao Descrição da obsevação
	 * @apiSuccess {Unidade} resultadoConsultaProcesso.sei.observacoes.unidade Unidade responsável pela observação
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.observacoes.unidade.descricao Nome da unidade
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.observacoes.unidade.idUnidade Código da unidade
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.observacoes.unidade.sigla Sigla da unidade
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.procedimentoFormatado Número do processo visível para o usuário
	 * @apiSuccess {TipoProcedimento} resultadoConsultaProcesso.sei.tipoProcedimento Tipo de procedimento
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.tipoProcedimento.idTipoProcedimento Identificador do tipo de procedimento
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.tipoProcedimento.nome Nome do tipo de procedimento
	 * @apiSuccess {Andamento} resultadoConsultaProcesso.sei.ultimoAndamento Ultimo andamento do processo (opcional)
	 * @apiSuccess {Data} resultadoConsultaProcesso.sei.ultimoAndamento.dataHora Data e hora do registro de andamento
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.ultimoAndamento.descricao Descrição do andamento
	 * @apiSuccess {Unidade} resultadoConsultaProcesso.sei.ultimoAndamento.unidade Unidade responsável pelo andamento
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.ultimoAndamento.unidade.descricao Nome da unidade
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.ultimoAndamento.unidade.idUnidade Código da unidade
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.ultimoAndamento.unidade.sigla Sigla da unidade
	 * @apiSuccess {Usuario} resultadoConsultaProcesso.sei.ultimoAndamento.usuario Usuário responsável pela ação
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.ultimoAndamento.usuario.idUsuario Código do usuário
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.ultimoAndamento.usuario.nome Nome do usuário
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.ultimoAndamento.usuario.sigla Login do usuário
	 * @apiSuccess {UnidadeProcedimentoAberto} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto Unidades onde o processo está aberto (opcional)
	 * @apiSuccess {Unidade} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade Unidade onde o processo está aberto
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.descricao Nome da unidade
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.idUnidade Código da unidade
	 * @apiSuccess {String} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.sigla Sigla da unidade
	 * @apiSuccess {DocumentoSIPAR} resultadoConsultaProcesso.sipar Resultado de processo do SIPAR
	 * @apiSuccess {String} resultadoConsultaProcesso.sipar.digito Digito do processo
	 * @apiSuccess {String} resultadoConsultaProcesso.sipar.operadora Operadora relacionada ao processo
	 * @apiSuccess {Data} resultadoConsultaProcesso.sipar.emissao Data de emissão
	 * @apiSuccess {Data} resultadoConsultaProcesso.sipar.registro Data de registro
	 * @apiSuccess {String} resultadoConsultaProcesso.sipar.tipo Tipo do processo
	 * @apiSuccess {String} resultadoConsultaProcesso.sipar.resumo Resumo sobre o processo
	 * @apiSuccess {Long} resultadoConsultaProcesso.sipar.orgaoPosse Código do orgão que tem a posse do processo
	 * @apiSuccess {Long} resultadoConsultaProcesso.sipar.orgaoOrigem Código do orgão de origem do processo
	 * @apiSuccess {Long} resultadoConsultaProcesso.sipar.orgaoRegistro Código do orgão de registro do processo
	 * @apiSuccess {Long} resultadoConsultaProcesso.sipar.assunto Código do assunto
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/{processo}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResultadoConsultaProcesso consultarProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo,
			@QueryParam("assuntos") String exibirAssuntos, @QueryParam("interessados") String exibirInteressados, @QueryParam("observacoes") String exibirObservacoes,
			@QueryParam("andamento") String exibirAndamento, @QueryParam("andamento-conclusao") String exibirAndamentoConclusao, @QueryParam("ultimo-andamento") String exibirUltimoAndamento,
			@QueryParam("unidades") String exibirUnidadesAberto, @QueryParam("relacionados") String exibirProcessosRelacionados, @QueryParam("anexados") String exibirProcessosAnexados) throws Exception{
		
		ResultadoConsultaProcesso resultado = null;
				
		RetornoConsultaProcedimento processoSEI = consultarProcessoSEI(unidade, processo, exibirAssuntos, exibirInteressados, exibirObservacoes, exibirAndamento, exibirAndamentoConclusao, 
			exibirUltimoAndamento, exibirUnidadesAberto, exibirProcessosRelacionados, exibirProcessosAnexados);
				
		DocumentoSipar processoSIPAR = consultarProcessoSIPAR(processo);
		
		if(processoSEI != null || processoSIPAR != null){
			resultado = new ResultadoConsultaProcesso();
			resultado.setSei(processoSEI);
			resultado.setSipar(processoSIPAR);
		}
		
		return resultado;
	}
	
	public RetornoConsultaProcedimento consultarProcessoSEI(String unidade, String processo, String exibirAssuntos, String exibirInteressados, 
			String exibirObservacoes, String exibirAndamento, String exibirAndamentoConclusao, String exibirUltimoAndamento, String exibirUnidadesAberto, 
			String exibirProcessosRelacionados, String exibirProcessosAnexados) throws RemoteException, Exception{
		
		try{
			return seiNativeService.consultarProcedimento(Constantes.SEI_BROKER, Operacao.CONSULTAR_PROCESSO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo), getSOuN(exibirAssuntos), 
					getSOuN(exibirInteressados), getSOuN(exibirObservacoes), getSOuN(exibirAndamento), getSOuN(exibirAndamentoConclusao), getSOuN(exibirUltimoAndamento), getSOuN(exibirUnidadesAberto), 
					getSOuN(exibirProcessosRelacionados), getSOuN(exibirProcessosAnexados));
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}
	
	public DocumentoSipar consultarProcessoSIPAR(String processo){
		String documento, ano, digito;

		try{
			documento = processo.substring(0,(processo.length() - 6));
			ano = processo.substring((processo.length() - 6), (processo.length() - 2));
			digito = processo.substring((processo.length() - 2), processo.length());
		}catch(Exception e){
			logger.error("Número de processo não corresponde ao padrão do SIPAR.");
			return null;
		}
		
		return documentoSiparDAO.getDocumento(documento, ano, digito);
	}
		
	/**
	 * @api {post} /:unidade/processos/concluidos Concluir processo
	 * @apiName concluirProcesso
	 * @apiGroup Processo
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza a conclusão de um processo.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} processo Numero do processo a ser concluído
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/processos/concluidos
	 *
	 *	body:
	 *	33910000029201653
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Path("/concluidos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String concluirProcesso(@PathParam("unidade") String unidade, String processo) throws Exception{
		String resultado = seiNativeService.concluirProcesso(Constantes.SEI_BROKER, Operacao.CONCLUIR_PROCESSO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo));

		return trueOrFalse(resultado) + "";
	}
	
	
	/**
	 * @api {post} /:unidade/processos/enviados Enviar processo
	 * @apiName enviarProcesso
	 * @apiGroup Processo
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método envia processos a outras unidades.
	 * 
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} processo Numero do processo a ser enviado 
	 * @apiParam {String[]} unidadesDestino Códigos das unidades para onde o bloco será enviado
	 * @apiParam {Boolean} manterAbertoOrigem=false Informa se o processo deve continuar aberto na unidade de origem 
	 * @apiParam {Boolean} removerAnotacoes=false Informa se as anotações do processo devem ser removidas
	 * @apiParam {Boolean} enviarEmailNotificacao=false Informa se deve ser enviado um e-mail de notificação
	 * @apiParam {Date} dataRetornoProgramado=null Data para retorno programado do processo a unidade (padrão ISO-8601)
	 * @apiParam {Integer} qtdDiasAteRetorno=null Quantidade de dias até o retorno do processo
	 * @apiParam {Boolean} somenteDiasUteis=false Informa se só serão contabilizados dias úteis
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/processos/enviados
	 *
	 *	body:
	 *	{
	 *		"numeroDoProcesso":"1600000000098",
	 *		"unidadesDestino":["110000934","110000934"],
	 *		"manterAbertoOrigem":false,
	 *		"removerAnotacoes":false,
	 *		"enviarEmailNotificacao":true,
	 *		"dataRetornoProgramado":2016-04-14T19:39:22.292+0000,
	 *		"qtdDiasAteRetorno":5,
	 *		"somenteDiasUteis":true}
	 *	}
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST	
	@Path("/enviados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String enviarProcesso(@PathParam("unidade") String unidade, EnvioDeProcesso dadosEnvio) throws Exception{
		String resultado = seiNativeService.enviarProcesso(Constantes.SEI_BROKER, Operacao.ENVIAR_PROCESSO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(dadosEnvio.getNumeroDoProcesso()), 
					dadosEnvio.getUnidadesDestino(), getSOuN(dadosEnvio.getManterAbertoOrigem()), getSOuN(dadosEnvio.getRemoverAnotacoes()), getSOuN(dadosEnvio.getEnviarEmailNotificacao()), 
					formatarData(dadosEnvio.getDataRetornoProgramado()), (dadosEnvio.getQtdDiasAteRetorno() != null ? dadosEnvio.getQtdDiasAteRetorno().toString() : null), getSOuN(dadosEnvio.getSomenteDiasUteis()));
		
		return trueOrFalse(resultado) + "";
	}
	
	
	/**
	 * @api {delete} /:unidade/processos/concluidos/:processo Reabrir processo
	 * @apiName reabrirProcesso
	 * @apiGroup Processo
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método reabre um processo.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam {String} processo Numero do processo a ser reaberto
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://anshmjboss01/sei-broker/service/COSAP/processos/concluidos/33910000029201653
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@DELETE
	@Path("/concluidos/{processo}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String reabrirProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo) throws Exception{
		String resultado = seiNativeService.reabrirProcesso(Constantes.SEI_BROKER, Operacao.REABRIR_PROCESSO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo));

		return trueOrFalse(resultado) + "";
	}
		
	/**
	 * @api {get} /:unidade/processos/tipos Tipos de processo
	 * @apiName listarTiposProcesso
	 * @apiGroup Processo
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método consulta os tipos de processo.
	 * 
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://anshmjboss01/sei-broker/service/COSAP/processos/tipos
	 *
	 * @apiSuccess {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/tipos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public TipoProcedimento[] listarTiposProcesso(@PathParam("unidade") String unidade, @QueryParam("serie") String serie) throws Exception{
		return seiNativeService.listarTiposProcedimento(Constantes.SEI_BROKER, Operacao.LISTAR_TIPOS_PROCESSO, unidadeResource.consultarCodigo(unidade), serie);		
	}
	
	/**
	 * @api {post} /:unidade/processos Abrir processo
	 * @apiName abrirProcesso
	 * @apiGroup Processo
	 * @apiVersion 0.0.1
	 *
	 * @apiDescription Este método realiza a abertura de um processo.
	 *
	 * @apiParam {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam {String} idTipoProcedimento Identificador do tipo de processo no SEI (Consultar tipos de processo).
	 * @apiParam {String} especificacao Especificação do processo.
	 * @apiParam {Assunto[]} assuntos Assuntos do processo, os assuntos informados serão adicionados aos assuntos 
									sugeridos para o tipo de processo. Passar um array vazio caso nenhum outro assunto seja necessário 
									(caso apenas os sugeridos para o tipo bastem para classificação).
     * @apiParam {String} assuntos.codigoEstruturado Código do assunto
	 * @apiParam {String} assuntos.descricao Descrição do assunto								
	 * @apiParam {Interessado[]} interessados Informar um conjunto com os dados de interessados. Se não existirem interessados 
								deve ser informado um conjunto vazio.
	 * @apiParam {String} interessados.nome Nome do interessado
	 * @apiParam {String} interessados.sigla Login do interessado								
	 * @apiParam {String} observacao Texto da observação, passar null se não existir.
	 * @apiParam {String = "0 (público)","1 (restrito)", "2 (sigiloso)", "null (herda do processo)"} nivelAcesso Nível de acesso do processo. 
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://anshmjboss01/sei-broker/service/COSAP/processos
	 *
	 *	body:
	 *	{
	 *		"dadosProcesso":{
	 *				"idTipoProcedimento":"100000375",
	 *				"especificacao":"Documentação REST",
	 *				"assuntos":[{"codigoEstruturado":"00.01.01.01","descricao":"Assunto teste"}],
	 *				"interessados":[{"sigla":"andre.guimaraes","nome":"André Luís Fernandes Guimarães"}],
	 *				"observacao":"Exemplo de requisição",
	 *				"nivelAcesso":0
	 *			},
	 *			"documentos":[],
	 *			"processosRelacionados":[],
	 *			"unidadesDestino":["110000935","110000934"],
	 *			"manterAbertoOrigem":true,
	 *			"enviarEmailNotificacao":true,
	 *			"qtdDiasAteRetorno":null,
	 *			"somenteDiasUteis":false
	 *	}
	 * 
	 * @apiSuccess {String} idProcedimento Número do processo gerado
	 * @apiSuccess {String} procedimentoFormatado Número formatado do processo gerado
	 * @apiSuccess {String} linkAcesso Link de acesso ao processo
	 * @apiSuccess {RetornoInclusaoDocumento} retornoInclusaoDocumentos Retorno dos documentos inseridos no processo (opcional)
	 * @apiSuccess {String} retornoInclusaoDocumentos.idDocumento Número interno do documento
	 * @apiSuccess {String} retornoInclusaoDocumentos.documentoFormatado Número do documento visível para o usuário
	 * @apiSuccess {String} retornoInclusaoDocumentos.linkAcesso Link para acesso ao documento
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "idProcedimento":"33910000056201626",
	 *       "procedimentoFormatado":"33910.000056/2016-26",
	 *       "linkAcesso":"https://sei-hm.ans.gov.br/controlador.php?acao=arvore_visualizar&acao_origem=procedimento_visualizar&id_procedimento=267&infra_sistema=100000100&infra_unidade_atual=110000934&infra_hash=7a6a75f6b8ec6b43aaffc6616159a85e35e444b9b32da54108e467bc9f3bdfab",
	 *       "retornoInclusaoDocumentos":[]
	 *     }
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public RetornoGeracaoProcedimento abrirProcesso(@PathParam("unidade") String unidade, NovoProcesso processo) throws RemoteException, Exception{
		return seiNativeService.gerarProcedimento(Constantes.SEI_BROKER, Operacao.ABRIR_PROCESSO, unidadeResource.consultarCodigo(unidade), processo.getDadosProcesso(), processo.getDocumentos(), 
				 formatarNumeroProcesso(processo.getProcessosRelacionados()), processo.getUnidadesDestino(), getSOuN(processo.isManterAbertoOrigem()), getSOuN(processo.isEnviarEmailNotificacao()), 
				 formatarData(processo.getDataRetornoProgramado()), (processo.getQtdDiasAteRetorno() != null ? processo.getQtdDiasAteRetorno().toString() : null), getSOuN(processo.isSomenteDiasUteis()));
	}
	
}
