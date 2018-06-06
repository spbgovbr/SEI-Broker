package br.gov.ans.integracao.sei.rest;

import static br.gov.ans.integracao.sei.utils.Util.formatarData;
import static br.gov.ans.integracao.sei.utils.Util.formatarNumeroProcesso;
import static br.gov.ans.integracao.sei.utils.Util.getOnlyNumbers;
import static br.gov.ans.integracao.sei.utils.Util.getSOuN;
import static br.gov.ans.integracao.sei.utils.Util.parseInt;
import static br.gov.ans.integracao.sei.utils.Util.trueOrFalse;

import java.math.BigInteger;
import java.net.URI;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.axis.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sei.client.Andamento;
import br.gov.ans.integracao.sei.client.AtributoAndamento;
import br.gov.ans.integracao.sei.client.RetornoConsultaProcedimento;
import br.gov.ans.integracao.sei.client.RetornoGeracaoProcedimento;
import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.client.TipoProcedimento;
import br.gov.ans.integracao.sei.client.Unidade;
import br.gov.ans.integracao.sei.dao.DocumentoDAO;
import br.gov.ans.integracao.sei.dao.ProcessoDAO;
import br.gov.ans.integracao.sei.dao.SiparDAO;
import br.gov.ans.integracao.sei.dao.UnidadeDAO;
import br.gov.ans.integracao.sei.modelo.DocumentoResumido;
import br.gov.ans.integracao.sei.modelo.EnvioDeProcesso;
import br.gov.ans.integracao.sei.modelo.Motivo;
import br.gov.ans.integracao.sei.modelo.NovoAndamento;
import br.gov.ans.integracao.sei.modelo.NovoProcesso;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.modelo.ProcessoAnexado;
import br.gov.ans.integracao.sei.modelo.ProcessoBloqueado;
import br.gov.ans.integracao.sei.modelo.ProcessoRelacionado;
import br.gov.ans.integracao.sei.modelo.ProcessoResumido;
import br.gov.ans.integracao.sei.modelo.ResultadoConsultaProcesso;
import br.gov.ans.integracao.sei.modelo.SobrestamentoProcesso;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.integracao.sipar.dao.DocumentoSipar;
import br.gov.ans.utils.MessageUtils;


@Path("")
public class ProcessoResource {
	
    @Inject
    private SiparDAO documentoSiparDAO;
    
    @Inject
    private ProcessoDAO processoDAO;
    
    @Inject
    private DocumentoDAO documentoDAO;
    
    @Inject
    private UnidadeDAO unidadeDAO;
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
	
    @Inject
    private UnidadeResource unidadeResource;
      
    @Inject
    private MessageUtils messages;
    
    @Inject
    private Logger logger;
    
	@Context
	private UriInfo uriInfo;
    
	/**
	 * @api {get} /:unidade/processos/:processo Consultar processo
	 * @apiName consultarProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 *
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Este método realiza uma consulta a processos no SEI e no SIPAR.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} processo Número do processo que deseja consultar
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [assuntos=N] Exibir assuntos do processo
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [interessados=N] Exibir interessados no processo
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [observacoes=N] Exibir observações feitas no processo
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [andamento=N] Exibir andamento do processo
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [andamento-conclusao=N] Exibir o andamento da conclusão do processo
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [ultimo-andamento=N] Exibir o último andamento dado ao processo
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [unidades=N] Exibir unidades onde o processo está aberto
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [relacionados=N] Exibir processos relacionados
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [anexados=N] Exibir processos anexados
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [auto-formatacao=S] O broker utilizará a mascara padrão para formatar o número do processo
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/COSAP/processos/33910000029201653
	 *
	 * @apiSuccess (Sucesso - 200) {ResultadoConsultaProcesso} resultadoConsultaProcesso Objeto de retorno da consulta aos processos, pode um conter processo do SEI ou do SIPAR
	 * @apiSuccess (Sucesso - 200) {RetornoConsultaProcedimento} resultadoConsultaProcesso.sei Resultado de processo do SEI
	 * @apiSuccess (Sucesso - 200) {Andamento} resultadoConsultaProcesso.sei.andamentoConclusao Andamento da conclusão do processo (opcional)
	 * @apiSuccess (Sucesso - 200) {Data} resultadoConsultaProcesso.sei.andamentoConclusao.dataHora Data e hora do registro de andamento
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoConclusao.descricao Descrição do andamento
	 * @apiSuccess (Sucesso - 200) {Unidade} resultadoConsultaProcesso.sei.andamentoConclusao.unidade Unidade responsável pelo andamento
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoConclusao.unidade.descricao Nome da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoConclusao.unidade.idUnidade Código da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoConclusao.unidade.sigla Sigla da unidade
	 * @apiSuccess (Sucesso - 200) {Usuario} resultadoConsultaProcesso.sei.andamentoConclusao.usuario Usuário responsável pela ação
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoConclusao.usuario.idUsuario Código do usuário
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoConclusao.usuario.nome Nome do usuário
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoConclusao.usuario.sigla Login do usuário
	 * @apiSuccess (Sucesso - 200) {Andamento} resultadoConsultaProcesso.sei.andamentoGeracao Andamento da geração do processo (opcional)
	 * @apiSuccess (Sucesso - 200) {Data} resultadoConsultaProcesso.sei.andamentoGeracao.dataHora Data e hora do registro de andamento
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoGeracao.descricao Descrição do andamento
	 * @apiSuccess (Sucesso - 200) {Unidade} resultadoConsultaProcesso.sei.andamentoGeracao.unidade Unidade responsável pelo andamento
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoGeracao.unidade.descricao Nome da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoGeracao.unidade.idUnidade Código da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoGeracao.unidade.sigla Sigla da unidade
	 * @apiSuccess (Sucesso - 200) {Usuario} resultadoConsultaProcesso.sei.andamentoGeracao.usuario Usuário responsável pela ação
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoGeracao.usuario.idUsuario Código do usuário
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoGeracao.usuario.nome Nome do usuário
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.andamentoGeracao.usuario.sigla Login do usuário
	 * @apiSuccess (Sucesso - 200) {Assunto} resultadoConsultaProcesso.sei.assuntos Lista de assuntos
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.assuntos.codigoEstruturado Código do assunto
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.descricao Descrição do assunto
	 * @apiSuccess (Sucesso - 200) {Data} resultadoConsultaProcesso.sei.dataAutuacao Data de autuação do processo
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.especificacao Especificação do processo
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.idProcedimento Id interno do processo no SEI
	 * @apiSuccess (Sucesso - 200) {Interessado} resultadoConsultaProcesso.sei.interessados Lista de interessados no processo (opcional)
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.interessados.nome Nome do interessado
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.interessados.sigla Login do interessado
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.idProcedimento Id interno do processo no SEI
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.linkAcesso Link para acesso ao processo
	 * @apiSuccess (Sucesso - 200) {Observacao} resultadoConsultaProcesso.sei.observacoes Observações feitas sobre o processo (opcional)
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.observacoes.descricao Descrição da obsevação
	 * @apiSuccess (Sucesso - 200) {Unidade} resultadoConsultaProcesso.sei.observacoes.unidade Unidade responsável pela observação
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.observacoes.unidade.descricao Nome da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.observacoes.unidade.idUnidade Código da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.observacoes.unidade.sigla Sigla da unidade 
	 * @apiSuccess (Sucesso - 200) {ProcedimentoResumido} resultadoConsultaProcesso.sei.procedimentosAnexados Lista com os processos anexados
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.procedimentosAnexados.idProcedimento Identificador do processo
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.procedimentosAnexados.procedimentoFormatado Número do processo visível para o usuário
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.procedimentosAnexados.tipoProcedimento Tipo do processo 
	 * @apiSuccess (Sucesso - 200) {ProcedimentoResumido} resultadoConsultaProcesso.sei.procedimentosRelacionados Lista com os processos relacionados
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.procedimentosRelacionados.idProcedimento Identificador do processo
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.procedimentosRelacionados.procedimentoFormatado Número do processo visível para o usuário
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.procedimentosRelacionados.tipoProcedimento Tipo do processo
	 * 
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.procedimentoFormatado Número do processo visível para o usuário
	 * @apiSuccess (Sucesso - 200) {TipoProcedimento} resultadoConsultaProcesso.sei.tipoProcedimento Tipo de procedimento
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.tipoProcedimento.idTipoProcedimento Identificador do tipo de procedimento
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.tipoProcedimento.nome Nome do tipo de procedimento
	 * @apiSuccess (Sucesso - 200) {Andamento} resultadoConsultaProcesso.sei.ultimoAndamento Ultimo andamento do processo (opcional)
	 * @apiSuccess (Sucesso - 200) {Data} resultadoConsultaProcesso.sei.ultimoAndamento.dataHora Data e hora do registro de andamento
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.ultimoAndamento.descricao Descrição do andamento
	 * @apiSuccess (Sucesso - 200) {Unidade} resultadoConsultaProcesso.sei.ultimoAndamento.unidade Unidade responsável pelo andamento
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.ultimoAndamento.unidade.descricao Nome da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.ultimoAndamento.unidade.idUnidade Código da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.ultimoAndamento.unidade.sigla Sigla da unidade
	 * @apiSuccess (Sucesso - 200) {Usuario} resultadoConsultaProcesso.sei.ultimoAndamento.usuario Usuário responsável pela ação
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.ultimoAndamento.usuario.idUsuario Código do usuário
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.ultimoAndamento.usuario.nome Nome do usuário
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.ultimoAndamento.usuario.sigla Login do usuário
	 * @apiSuccess (Sucesso - 200) {UnidadeProcedimentoAberto} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto Unidades onde o processo está aberto (opcional)
	 * @apiSuccess (Sucesso - 200) {Unidade} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade Unidade onde o processo está aberto
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.descricao Nome da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.idUnidade Código da unidade
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sei.unidadesProcedimentoAberto.unidade.sigla Sigla da unidade
	 * 
	 * @apiSuccess (Sucesso - 200) {DocumentoSIPAR} resultadoConsultaProcesso.sipar Resultado de processo do SIPAR
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sipar.digito Digito do processo
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sipar.operadora Operadora relacionada ao processo
	 * @apiSuccess (Sucesso - 200) {Data} resultadoConsultaProcesso.sipar.emissao Data de emissão
	 * @apiSuccess (Sucesso - 200) {Data} resultadoConsultaProcesso.sipar.registro Data de registro
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sipar.tipo Tipo do processo
	 * @apiSuccess (Sucesso - 200) {String} resultadoConsultaProcesso.sipar.resumo Resumo sobre o processo
	 * @apiSuccess (Sucesso - 200) {Long} resultadoConsultaProcesso.sipar.orgaoPosse Código do orgão que tem a posse do processo
	 * @apiSuccess (Sucesso - 200) {Long} resultadoConsultaProcesso.sipar.orgaoOrigem Código do orgão de origem do processo
	 * @apiSuccess (Sucesso - 200) {Long} resultadoConsultaProcesso.sipar.orgaoRegistro Código do orgão de registro do processo
	 * @apiSuccess (Sucesso - 200) {Long} resultadoConsultaProcesso.sipar.assunto Código do assunto
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/processos/{processo:\\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResultadoConsultaProcesso consultarProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo,
			@QueryParam("assuntos") String exibirAssuntos, @QueryParam("interessados") String exibirInteressados, @QueryParam("observacoes") String exibirObservacoes,
			@QueryParam("andamento") String exibirAndamento, @QueryParam("andamento-conclusao") String exibirAndamentoConclusao, @QueryParam("ultimo-andamento") String exibirUltimoAndamento,
			@QueryParam("unidades") String exibirUnidadesAberto, @QueryParam("relacionados") String exibirProcessosRelacionados, @QueryParam("anexados") String exibirProcessosAnexados,
			@QueryParam("auto-formatacao") String autoFormatar) throws Exception{
		
		ResultadoConsultaProcesso resultado = null;
				
		RetornoConsultaProcedimento processoSEI = consultarProcessoSEI(unidade, processo, exibirAssuntos, exibirInteressados, exibirObservacoes, exibirAndamento, exibirAndamentoConclusao, 
			exibirUltimoAndamento, exibirUnidadesAberto, exibirProcessosRelacionados, exibirProcessosAnexados, autoFormatar);
				
		DocumentoSipar processoSIPAR = consultarProcessoSIPAR(processo);
		
		if(processoSEI != null || processoSIPAR != null){
			resultado = new ResultadoConsultaProcesso();
			resultado.setSei(processoSEI);
			resultado.setSipar(processoSIPAR);
		}else{
			throw new ResourceNotFoundException(messages.getMessage("erro.processo.nao.encontrado", processo));
		}
		
		return resultado;
	}
	
	public RetornoConsultaProcedimento consultarProcessoSEI(String unidade, String processo, String exibirAssuntos, String exibirInteressados, 
			String exibirObservacoes, String exibirAndamento, String exibirAndamentoConclusao, String exibirUltimoAndamento, String exibirUnidadesAberto, 
			String exibirProcessosRelacionados, String exibirProcessosAnexados, String autoFormatar) throws RemoteException, Exception{
		if(isAutoFormatar(autoFormatar)){			
			processo = formatarNumeroProcesso(processo);
		}
		
		try{
			return seiNativeService.consultarProcedimento(Constantes.SEI_BROKER, Operacao.CONSULTAR_PROCESSO, unidadeResource.consultarCodigo(unidade), processo, getSOuN(exibirAssuntos), 
					getSOuN(exibirInteressados), getSOuN(exibirObservacoes), getSOuN(exibirAndamento), getSOuN(exibirAndamentoConclusao), getSOuN(exibirUltimoAndamento), getSOuN(exibirUnidadesAberto), 
					getSOuN(exibirProcessosRelacionados), getSOuN(exibirProcessosAnexados));
		}catch(AxisFault ex){
			logger.error(ex, ex);
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
			logger.error(messages.getMessage("erro.numero.sipar"));
			return null;
		}
		
		return documentoSiparDAO.getDocumento(documento, ano, digito);
	}
		
	/**
	 * @api {post} /:unidade/processos/concluidos Concluir processo
	 * @apiName concluirProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Conclui o processo informado.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [auto-formatacao=S] O broker utilizará a mascara padrão para formatar o número do processo
	 * 
	 * @apiParam (Request Body) {String} processo Numero do processo a ser concluído
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/concluidos
	 *
	 *	body:
	 *	33910000029201653
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST
	@Path("{unidade}/processos/concluidos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String concluirProcesso(@PathParam("unidade") String unidade, @QueryParam("auto-formatacao") String autoFormatar, String processo) throws Exception{
		if(isAutoFormatar(autoFormatar)){			
			processo = formatarNumeroProcesso(processo);
		}
		
		String resultado = seiNativeService.concluirProcesso(Constantes.SEI_BROKER, Operacao.CONCLUIR_PROCESSO, unidadeResource.consultarCodigo(unidade), processo);

		return trueOrFalse(resultado) + "";
	}
	
	
	/**
	 * @api {post} /:unidade/processos/enviados Enviar processo
	 * @apiName enviarProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 *
	 * @apiDescription Envia processos a outras unidades.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI. Representa a unidade de localização atual do processo.
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [reabir=N] Reabrir automaticamente caso esteja concluído na unidade
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [auto-formatacao=S] O broker utilizará a mascara padrão para formatar o número do processo
	 * 
	 * @apiParam (Request Body) {String} processo Numero do processo a ser enviado. Em caso de processo apensado, o processo a ser enviado deve ser o processo PAI. Não é possível tramitar através do processo FILHO.
	 * @apiParam (Request Body) {String[]} unidadesDestino Lista com os identificadores das unidades de destino do processo, código ou nome da unidade.
	 * @apiParam (Request Body) {Boolean} manterAbertoOrigem=false Informa se o processo deve continuar aberto na unidade de origem .
	 * @apiParam (Request Body) {Boolean} removerAnotacoes=false Informa se as anotações do processo devem ser removidas.
	 * @apiParam (Request Body) {Boolean} enviarEmailNotificacao=false Informa se deve ser enviado um e-mail de notificação.
	 * @apiParam (Request Body) {Date} dataRetornoProgramado=null Data para retorno programado do processo a unidade (padrão ISO-8601).
	 * @apiParam (Request Body) {Integer} qtdDiasAteRetorno=null Quantidade de dias até o retorno do processo.
	 * @apiParam (Request Body) {Boolean} somenteDiasUteis=false Informa se só serão contabilizados dias úteis.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/enviados
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
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@POST	
	@Path("{unidade}/processos/enviados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String enviarProcesso(@PathParam("unidade") String unidade, @QueryParam("reabir") String reabrir, @QueryParam("auto-formatacao") String autoFormatar, EnvioDeProcesso dadosEnvio) throws Exception{
		String processo;
		if(isAutoFormatar(autoFormatar)){			
			processo = formatarNumeroProcesso(dadosEnvio.getNumeroDoProcesso());
		}else{
			processo = dadosEnvio.getNumeroDoProcesso();
		}
		
		String resultado = seiNativeService.enviarProcesso(Constantes.SEI_BROKER, Operacao.ENVIAR_PROCESSO, unidadeResource.consultarCodigo(unidade), processo,
					unidadeResource.buscarCodigoUnidades(dadosEnvio.getUnidadesDestino()), getSOuN(dadosEnvio.getManterAbertoOrigem()), getSOuN(dadosEnvio.getRemoverAnotacoes()), 
					getSOuN(dadosEnvio.getEnviarEmailNotificacao()), formatarData(dadosEnvio.getDataRetornoProgramado()), 
					(dadosEnvio.getQtdDiasAteRetorno() != null ? dadosEnvio.getQtdDiasAteRetorno().toString() : null), getSOuN(dadosEnvio.getSomenteDiasUteis()),
					getSOuN(reabrir));
		
		return trueOrFalse(resultado) + "";
	}
	
	
	/**
	 * @api {delete} /:unidade/processos/concluidos/:processo Reabrir processo
	 * @apiName reabrirProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Reabre um processo.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * @apiParam (Path Parameters) {String} processo Numero do processo a ser reaberto
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [auto-formatacao=S] O broker utilizará a mascara padrão para formatar o número do processo
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE https://<host>/sei-broker/service/COSAP/processos/concluidos/33910000029201653
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@DELETE
	@Path("{unidade}/processos/concluidos/{processo:\\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String reabrirProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo, @QueryParam("auto-formatacao") String autoFormatar) throws Exception{
		if(isAutoFormatar(autoFormatar)){			
			processo = formatarNumeroProcesso(processo);
		}
		
		String resultado = seiNativeService.reabrirProcesso(Constantes.SEI_BROKER, Operacao.REABRIR_PROCESSO, unidadeResource.consultarCodigo(unidade), processo);

		return trueOrFalse(resultado) + "";
	}
		
	/**
	 * @api {get} /:unidade/processos/tipos Tipos de processo
	 * @apiName listarTiposProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta os tipos de processo.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI
	 * 
	 * @apiParam (Query Parameters) {String} [serie] Tipo do documento cadastrado no serviço
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i https://<host>/sei-broker/service/COSAP/processos/tipos
	 *
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado Booleano informando sucesso da requisição
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{unidade}/processos/tipos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public TipoProcedimento[] listarTiposProcesso(@PathParam("unidade") String unidade, @QueryParam("serie") String serie) throws Exception{
		return seiNativeService.listarTiposProcedimento(Constantes.SEI_BROKER, Operacao.LISTAR_TIPOS_PROCESSO, unidadeResource.consultarCodigo(unidade), serie);		
	}
	
	/**
	 * @api {post} /:unidade/processos Abrir processo
	 * @apiName abrirProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Abre um processo.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [auto-formatacao=S] O broker utilizará a mascara padrão para formatar o número do processo
	 * 
	 * @apiParam (Request Body) {NovoProcesso} novoProcesso Objeto de representação de novo processo.
	 * @apiParam (Request Body) {Procedimento} novoProcesso.dadosProcesso Dados do processo.
	 * @apiParam (Request Body) {String} novoProcesso.dadosProcesso.idTipoProcedimento Identificador do tipo de processo no SEI (Consultar tipos de processo).
	 * @apiParam (Request Body) {String} [novoProcesso.dadosProcesso.numeroProtocolo] Número do processo, se não for informado o sistema irá gerar um novo número automaticamente.
	 * @apiParam (Request Body) {String} [novoProcesso.dadosProcesso.dataAutuacao] Data de autuação do processo, se não for informada o sistema utilizará a data atual.
	 * @apiParam (Request Body) {String} novoProcesso.dadosProcesso.especificacao Especificação do processo.
	 * @apiParam (Request Body) {Assunto[]} [novoProcesso.dadosProcesso.assuntos] Assuntos do processo, os assuntos informados serão adicionados aos assuntos sugeridos para o tipo de processo. Passar um array vazio caso nenhum outro assunto seja necessário(caso apenas os sugeridos para o tipo bastem para classificação).
	 * @apiParam (Request Body) {String} novoProcesso.dadosProcesso.assuntos.codigoEstruturado Código do assunto
	 * @apiParam (Request Body) {String} novoProcesso.dadosProcesso.assuntos.descricao Descrição do assunto								
	 * @apiParam (Request Body) {Interessado[]} [novoProcesso.dadosProcesso.interessados] Informar um conjunto com os dados de interessados. Se não existirem interessados deve ser informado um conjunto vazio.
	 * @apiParam (Request Body) {String} novoProcesso.dadosProcesso.interessados.nome Nome do interessado
	 * @apiParam (Request Body) {String} novoProcesso.dadosProcesso.interessados.sigla Login do interessado								
	 * @apiParam (Request Body) {String} [novoProcesso.dadosProcesso.observacao] Texto da observação, passar null se não existir.
	 * @apiParam (Request Body) {String = "0 (público)","1 (restrito)", "2 (sigiloso)", "null (herda do tipo do processo)"} [novoProcesso.dadosProcesso.nivelAcesso] Nível de acesso do processo.
	 * @apiParam (Request Body) {String} [novoProcesso.dadosProcesso.idHipoteseLegal] Identificador da hipótese legal associada.	 * 
	 * @apiParam (Request Body) {Documento[]} [novoProcesso.documentos] Informar os documentos que devem ser gerados em conjunto com o processo (ver serviço de incluir documento para instruções de preenchimento). Se nenhum documento for gerado informar um conjunto vazio. 
	 * @apiParam (Request Body) {String[]} [novoProcesso.processosRelacionados] Lista com os identificadores dos processos(idProcedimento) que devem ser relacionados automaticamente com o novo processo, atenção, não passar o número do processo formatado.
	 * @apiParam (Request Body) {String[]} [novoProcesso.unidadesDestino] Lista com os identificadores das unidades de destino do processo, código ou nome da unidade.
	 * @apiParam (Request Body) {Boolean} [novoProcesso.manterAbertoOrigem=false] Indica se o processo deve ser mantido aberto na unidade de origem.
	 * @apiParam (Request Body) {Boolean} [novoProcesso.enviarEmailNotificacao=false] Indica se deve ser enviado email de aviso para as unidades destinatárias. 
	 * @apiParam (Request Body) {Date} [novoProcesso.dataRetornoProgramado] Data para definição de Retorno Programado.
	 * @apiParam (Request Body) {Integer} [novoProcesso.qtdDiasAteRetorno] Número de dias para o Retorno Programado.
	 * @apiParam (Request Body) {Boolean} [novoProcesso.somenteDiasUteis=false] Indica se o valor passado no parâmetro qtdDiasAteRetorno corresponde a dias úteis ou não.
	 * @apiParam (Request Body) {String} [novoProcesso.idMarcador] Identificador de um marcador da unidade para associação.
	 * @apiParam (Request Body) {String} [novoProcesso.textoMarcador] Texto do marcador. 
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos
	 *
	 *	body:
	 *	{
	 *		"dadosProcesso":{
	 *				"idTipoProcedimento":"100000375",
	 *				"especificacao":"Documentação REST",
	 *				"assuntos":[],
	 *				"interessados":[{"sigla":"andre.guimaraes","nome":"André Luís Fernandes Guimarães"}],
	 *				"observacao":"Exemplo de requisição",
	 *				"nivelAcesso":0
	 *			},
	 *			"documentos":[],
	 *			"processosRelacionados":["186649"],
	 *			"unidadesDestino":["COTEC","110000935","COSAP"],
	 *			"manterAbertoOrigem":true,
	 *			"enviarEmailNotificacao":true,
	 *			"qtdDiasAteRetorno":null,
	 *			"somenteDiasUteis":false
	 *	}
	 * 
	 * @apiSuccess (Sucesso Response Body - 201) {String} idProcedimento Número do processo gerado
	 * @apiSuccess (Sucesso Response Body - 201) {String} procedimentoFormatado Número formatado do processo gerado
	 * @apiSuccess (Sucesso Response Body - 201) {String} linkAcesso Link de acesso ao processo
	 * @apiSuccess (Sucesso Response Body - 201) {RetornoInclusaoDocumento} retornoInclusaoDocumentos Retorno dos documentos inseridos no processo (opcional)
	 * @apiSuccess (Sucesso Response Body - 201) {String} retornoInclusaoDocumentos.idDocumento Número interno do documento
	 * @apiSuccess (Sucesso Response Body - 201) {String} retornoInclusaoDocumentos.documentoFormatado Número do documento visível para o usuário
	 * @apiSuccess (Sucesso Response Body - 201) {String} retornoInclusaoDocumentos.linkAcesso Link para acesso ao documento
	 * 
	 * @apiSuccess (Sucesso Response Header - 201) {header} Location URL de acesso ao recurso criado.
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
	@Path("{unidade}/processos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response abrirProcesso(@PathParam("unidade") String unidade, @QueryParam("auto-formatacao") String autoFormatar, NovoProcesso processo) throws RemoteException, Exception{
		if(StringUtils.isNotBlank(processo.getDadosProcesso().getNumeroProtocolo()) && isAutoFormatar(autoFormatar)){
			String numeroFormatado = formatarNumeroProcesso(processo.getDadosProcesso().getNumeroProtocolo());
			processo.getDadosProcesso().setNumeroProtocolo(numeroFormatado);
		}
		
		RetornoGeracaoProcedimento retorno = seiNativeService.gerarProcedimento(Constantes.SEI_BROKER, Operacao.ABRIR_PROCESSO, unidadeResource.consultarCodigo(unidade), processo.getDadosProcesso(), processo.getDocumentos(), 
				 processo.getProcessosRelacionados(), unidadeResource.buscarCodigoUnidades(processo.getUnidadesDestino()), getSOuN(processo.isManterAbertoOrigem()), 
				 getSOuN(processo.isEnviarEmailNotificacao()), formatarData(processo.getDataRetornoProgramado()), (processo.getQtdDiasAteRetorno() != null ? processo.getQtdDiasAteRetorno().toString() : null), getSOuN(processo.isSomenteDiasUteis()),
				 processo.getIdMarcadador(), processo.getTextoMarcador());
	
		return Response.created(getResourcePath(getOnlyNumbers(retorno.getProcedimentoFormatado()))).entity(retorno).build();
	}
	
	/**
	 * @api {get} /processos Listar processos
	 * @apiName consultarProcessos
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Lista os processos conforme os filtros informados.
	 * 
	 * @apiParam (Query Parameters) {Boolean} [crescente=false] Ordenar em ordem crescente, processos mais antigos primeiro
	 * @apiParam (Query Parameters) {String} [interessado] Identificador do interessado
	 * @apiParam (Query Parameters) {String} [unidade] Unidade da qual deseja filtrar os processos
	 * @apiParam (Query Parameters) {String} [pagina=1] Número da página
	 * @apiParam (Query Parameters) {String} [qtdRegistros=50] Quantidade de registros retornados por página
	 * @apiParam (Query Parameters) {String} [tipo] Identificador do tipo de processo que deseja filtrar
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i https://<host>/sei-broker/service/processos
	 *
	 * @apiSuccess (Sucesso Response Body - 200) {List} processos Lista com os processos encontrados
	 * @apiSuccess (Sucesso Response Body - 200) {ProcessoResumido} processos.processoResumido Resumo do processo encontrado no SEI
	 * @apiSuccess (Sucesso Response Body - 200) {String} processos.processoResumido.numero Número do processo
	 * @apiSuccess (Sucesso Response Body - 200) {String} processos.processoResumido.numeroFormatado Número do processo formatado
	 * @apiSuccess (Sucesso Response Body - 200) {String} processos.processoResumido.descricao Descrição do processo
	 * @apiSuccess (Sucesso Response Body - 200) {String} processos.processoResumido.unidade Unidade responsável pelo processo
	 * @apiSuccess (Sucesso Response Body - 200) {Data} processos.processoResumido.dataGeracao Data de geração do processo
	 * @apiSuccess (Sucesso Response Body - 200) {Tipo} processos.processoResumido.tipo Objeto com os dados do tipo de processo
	 * @apiSuccess (Sucesso Response Body - 200) {String} processos.processoResumido.tipo.codigo Código do tipo
	 * @apiSuccess (Sucesso Response Body - 200) {String} processos.processoResumido.tipo.nome Nome do tipo
	 * 
	 * @apiSuccess (Sucesso Response Header - 200) {header} total_registros quantidade de registros que existem para essa consulta.
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "numero": "33910007118201710",
	 *       "numeroFormatado": "33910.007118/2017-10",
	 *       "descricao": "D:2237021 - SUL AMÉRICA SEGURO SAÚDE S/A",
	 *       "unidade": "NÚCLEO-RJ",
	 *       "dataGeracao": "2017-10-09T03:00:00.000+0000",
	 *       "tipo": {
	 *       	"codigo": "100000882",
	 *       	"nome": "Fiscalização: Sancionador"
	 *       }
	 *     }
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/processos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Wrapped(element = "processos")
	public Response consultarProcessos(@QueryParam("interessado") String interessado, @QueryParam("unidade") String unidade, @QueryParam("tipo") String tipoProcesso, 
			@QueryParam("crescente") boolean crescente, @QueryParam("pagina") String pagina, @QueryParam("qtdRegistros") String qtdRegistros) throws Exception{
		
		if(StringUtils.isNotBlank(unidade)){
			unidade = unidadeResource.consultarCodigo(unidade);
		}
		
		List<ProcessoResumido> processos = processoDAO.getProcessos(interessado, unidade, tipoProcesso, 
				pagina == null? null:parseInt(pagina), qtdRegistros == null? null : parseInt(qtdRegistros), crescente);
		
		if(processos.isEmpty()){
			throw new ResourceNotFoundException(messages.getMessage("erro.nenhum.processo.encontrado.filtros"));
		}
		
		GenericEntity<List<ProcessoResumido>> entity = new GenericEntity<List<ProcessoResumido>>(processos){};
		
		Long totalRegistros = processoDAO.countProcessos(interessado, unidade, tipoProcesso);
		
		return Response.ok().entity(entity)
		.header("total_registros", totalRegistros).build();			
	}
	
	/**
	 * @api {get} /:unidade/processos/:processo/andamentos Listar andamentos
	 * @apiName listarAndamentos
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Lista as andamentos do processo.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 * 
	 * @apiParam (Query Parameters) {String = "S (sim), N (não)"} [exibir-atributos="N"] Sinalizador para retorno dos atributos associados.
	 * @apiParam (Query Parameters) {String[]} [andamento] Filtra andamentos pelos identificadores informados.
	 * @apiParam (Query Parameters) {String[]} [tarefa="1,48,65"] Filtra andamentos pelos identificadores de tarefas informados (consultar lista de tarefas).
	 * @apiParam (Query Parameters) {String[]} [tarefa-modulo] Filtra andamentos pelos identificadores de tarefas de módulo informados.
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/cosap/processos/33910003114201754/andamentos
	 * 
	 * @apiSuccess (Sucesso - 200) {Andamento[]} andamentos Lista dos andamentos do processo.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.idAndamento Identificador do andamento.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.idTarefa Identificador da tarefa.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.descricao Descrição do andamento.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.dataHora Data e hora do andamento.
	 * @apiSuccess (Sucesso - 200) {Unidade} andamentos.unidade Unidade onde o andamento ocorreu.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.unidade.idUnidade Identificador da unidade.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.unidade.sigla Sigla da unidade.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.unidade.descricao Descrição da unidade.
	 * @apiSuccess (Sucesso - 200) {Usuario} andamentos.usuario Usuário responsável pelo andamento.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.usuario.idUsuario Identificador do usuário.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.usuario.sigla Login do usuário.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.usuario.nome Nome do usuário.
	 * @apiSuccess (Sucesso - 200) {AtributoAndamento[]} andamentos.atributos Lista com os atributos relacionados ao andamento.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.atributos.nome Nome do atributo.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.atributos.valor Valor do atributo.
	 * @apiSuccess (Sucesso - 200) {String} andamentos.atributos.idOrigem Identificador de origem do atributo.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@GET
	@Path("/{unidade}/processos/{processo:\\d+}/andamentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Andamento[] listarAndamentos(@PathParam("unidade") String unidade, @PathParam("processo") String processo, @QueryParam("exibir-atributos") String retornarAtributos,
			@QueryParam("andamento") String[] andamentos, @QueryParam("tarefa") String[] tarefas, @QueryParam("tarefa-modulo") String[] tarefasModulos) throws RemoteException, Exception{

		if(tarefas.length < 1){
			tarefas = new String[]{"1","48","65"};
		}
		
		return seiNativeService.listarAndamentos(Constantes.SEI_BROKER, Operacao.LISTAR_ANDAMENTOS, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo), 
				getSOuN(retornarAtributos), andamentos, tarefas, tarefasModulos);		
	}
	
	/**
	 * @api {post} /:unidade/processos/:processo/andamentos Lançar andamento
	 * @apiName lancarAndamento
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Lança um andamento ao processo.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 *
	 * @apiParam (Request Body) {NovoAndamento} novoAndamento Objeto representando o novo andamento. 
	 * @apiParam (Request Body) {String} novoAndamento.tarefa Identificador da tarefa a qual o andamento se refere (consultar lista de tarefas).
	 * @apiParam (Request Body) {String} [novoAndamento.tarefaModulo] Identificadoe da tarefa módulo a qual o andamento se refere.
	 * @apiParam (Request Body) {HashMap} novoAndamento.atributos Mapa chave-valor, identificando como serão preenchidos os atributos da tarefa.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/33910003114201754/andamentos
	 *
	 *	body:
	 *	{
	 *		"tarefa":"65",
	 *		"atributos":{"DESCRICAO":"Novo andamento adicionado pelo SEI-Broker"}
	 *	}
	 * 
	 * @apiSuccess (Sucesso - 201) {Andamento} andamento Andamento criado.
	 * @apiSuccess (Sucesso - 201) {String} andamento.idAndamento Identificador do andamento.
	 * @apiSuccess (Sucesso - 201) {String} andamento.idTarefa Identificador da tarefa.
	 * @apiSuccess (Sucesso - 201) {String} andamento.descricao Descrição do andamento.
	 * @apiSuccess (Sucesso - 201) {String} andamento.dataHora Data e hora do andamento.
	 * @apiSuccess (Sucesso - 201) {Unidade} andamento.unidade Unidade onde o andamento ocorreu.
	 * @apiSuccess (Sucesso - 201) {String} andamento.unidade.idUnidade Identificador da unidade.
	 * @apiSuccess (Sucesso - 201) {String} andamento.unidade.sigla Sigla da unidade.
	 * @apiSuccess (Sucesso - 201) {String} andamento.unidade.descricao Descrição da unidade.
	 * @apiSuccess (Sucesso - 201) {Usuario} andamento.usuario Usuário responsável pelo andamento.
	 * @apiSuccess (Sucesso - 201) {String} andamento.usuario.idUsuario Identificador do usuário.
	 * @apiSuccess (Sucesso - 201) {String} andamento.usuario.sigla Login do usuário.
	 * @apiSuccess (Sucesso - 201) {String} andamento.usuario.nome Nome do usuário.
	 * @apiSuccess (Sucesso - 201) {AtributoAndamento[]} andamento.atributos Lista com os atributos relacionados ao andamento.
	 * @apiSuccess (Sucesso - 201) {String} andamento.atributos.nome Nome do atributo.
	 * @apiSuccess (Sucesso - 201) {String} andamento.atributos.valor Valor do atributo.
	 * @apiSuccess (Sucesso - 201) {String} andamento.atributos.idOrigem Identificador de origem do atributo.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@POST
	@Path("/{unidade}/processos/{processo:\\d+}/andamentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response lancarAndamento(@PathParam("unidade") String unidade, @PathParam("processo") String processo, NovoAndamento andamento) throws RemoteException, Exception{
		
		Andamento andamentoLancado = seiNativeService.lancarAndamento(Constantes.SEI_BROKER, Operacao.LANCAR_ANDAMENTO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo)
				, andamento.getTarefa(), andamento.getTarefaModulo(), buildAtributosAndamento(andamento.getAtributos()));		
		
		return Response.status(Status.CREATED).entity(andamentoLancado).build();
	}	
	
	/**
	 * @api {post} /:unidade/processos/:processo/anexados Anexar processo
	 * @apiName anexarProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Anexar um processo.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 *
	 * @apiParam (Request Body) {ProcessoAnexado} processoAnexado Objeto representando o processo a ser anexado. 
	 * @apiParam (Request Body) {String} processoAnexado.numero Número do processo a ser anexado.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/33910003114201754/anexados
	 *
	 *	body:
	 *	{
	 *		"numero":"33910003093201777"
	 *	}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@POST
	@Path("/{unidade}/processos/{processo:\\d+}/anexados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response anexarProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo, ProcessoAnexado processoAnexado) throws RemoteException, Exception{
		if(processoAnexado == null){
			throw new BusinessException(messages.getMessage("erro.processo.anexado.nao.infomado"));
		}
		
		String retorno = seiNativeService.anexarProcesso(Constantes.SEI_BROKER, Operacao.ANEXAR_PROCESSO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo), 
				formatarNumeroProcesso(processoAnexado.getNumero()));
		
		if(trueOrFalse(retorno)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.anexar.processo"));
		}
	}
	
	/**
	 * @api {delete} /:unidade/processos/:processo/anexados/:processoAnexado Desanexar processo
	 * @apiName desanexarProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Remove um processo anexado.
	 * 
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 * @apiParam (Path Parameters) {String} processo Número do processo anexado.
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE https://<host>/sei-broker/service/COSAP/processos/33910003114201754/anexados/33910003093201777
	 *
	 * @apiParam (Request Body) {Motivo} motivo Objeto com o motivo.
	 * @apiParam (Request Body) {String} motivo.motivo Descrição do motivo para remoção do processo em anexo.
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@DELETE
	@Path("/{unidade}/processos/{processo:\\d+}/anexados/{processoAnexado:\\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response desanexarProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo, @PathParam("processoAnexado") String processoAnexado,
			Motivo motivo) throws RemoteException, Exception{
		if(motivo == null){
			throw new BusinessException(messages.getMessage("erro.motivo.nao.infomado"));
		}		
		
		String retorno = seiNativeService.desanexarProcesso(Constantes.SEI_BROKER, Operacao.DESANEXAR_PROCESSO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo), 
				formatarNumeroProcesso(processoAnexado), motivo.getMotivo());
		
		if(trueOrFalse(retorno)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.desanexar.processo"));
		}
	}
	
	/**
	 * @api {post} /:unidade/processos/bloqueados Bloquear processo
	 * @apiName bloquearProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Bloquear um processo.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 *
	 * @apiParam (Request Body) {ProcessoBloqueado} processoBloqueado Objeto com o número do processo a ser bloqueado. 
	 * @apiParam (Request Body) {String} processoBloqueado.numero Número do processo a ser bloqueado.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/bloqueados
	 *
	 *	body:
	 *	{
	 *		"numero":"33910003093201777"
	 *	}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@POST
	@Path("/{unidade}/processos/bloqueados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response bloquearProcesso(@PathParam("unidade") String unidade, ProcessoBloqueado processo) throws RemoteException, Exception{

		if(processo == null || StringUtils.isBlank(processo.getNumero())){
			throw new BusinessException(messages.getMessage("erro.informe.processo"));
		}
		
		String retorno = seiNativeService.bloquearProcesso(Constantes.SEI_BROKER, Operacao.BLOQUEAR_PROCESSO, unidadeResource.consultarCodigo(unidade), 
				formatarNumeroProcesso(processo.getNumero()));
		
		if(trueOrFalse(retorno)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.bloquear.processo"));
		}
	}
	
	/**
	 * @api {delete} /:unidade/processos/bloqueados/:processo Desbloquear processo
	 * @apiName desbloquearProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Desbloquear um processo.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [DELETE] https://<host>/sei-broker/service/COSAP/processos/bloqueados/33910003093201777
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@DELETE
	@Path("/{unidade}/processos/bloqueados/{processo:\\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response desbloquearProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo) throws RemoteException, Exception{

		String retorno = seiNativeService.desbloquearProcesso(Constantes.SEI_BROKER, Operacao.DESBLOQUEAR_PROCESSO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo));
		
		if(trueOrFalse(retorno)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.desbloquear.processo"));
		}
	}
	
	/**
	 * @api {post} /:unidade/processos/:processo/relacionados Relacionar processo
	 * @apiName relacionarProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Relacionar processos.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 *
	 * @apiParam (Request Body) {ProcessoRelacionado} processoRelacionado Objeto com o número do processo a ser relacionado. 
	 * @apiParam (Request Body) {String} processoRelacionado.numero Número do processo a ser relacionado.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/33910003093201777/relacionados
	 *
	 *	body:
	 *	{
	 *		"numero":"33910000086201632"
	 *	}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@POST
	@Path("/{unidade}/processos/{processo:\\d+}/relacionados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response relacionarProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo, ProcessoRelacionado processoRelacionado) throws RemoteException, Exception{
		if(processoRelacionado == null || StringUtils.isBlank(processoRelacionado.getNumero())){
			throw new BusinessException(messages.getMessage("erro.processo.relacionado.nao.infomado"));
		}
		
		String retorno = seiNativeService.relacionarProcesso(Constantes.SEI_BROKER, Operacao.RELACIONAR_PROCESSO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo), 
				formatarNumeroProcesso(processoRelacionado.getNumero()));
		
		if(trueOrFalse(retorno)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.relacionar.processo"));
		}
	}
	
	/**
	 * @api {delete} /:unidade/processos/:processo/relacionados/:processoRelacionado Desrelacionar processo
	 * @apiName desrelacionarProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Desrelacionar processos.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 * @apiParam (Path Parameters) {String} processoRelacionado Número do processo relacionado.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [DELETE] https://<host>/sei-broker/service/COSAP/processos/33910000086201632/relacionados/33910003107201752
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@DELETE
	@Path("/{unidade}/processos/{processo:\\d+}/relacionados/{processoRelacionado:\\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response desrelacionarProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo, @PathParam("processoRelacionado") String processoRelacionado) 
			throws RemoteException, Exception{

		String retorno = seiNativeService.removerRelacionamentoProcesso(Constantes.SEI_BROKER, Operacao.DESRELACIONAR_PROCESSO, unidadeResource.consultarCodigo(unidade), 
				formatarNumeroProcesso(processo), formatarNumeroProcesso(processoRelacionado));
		
		if(trueOrFalse(retorno)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.desrelacionar.processo"));
		}
	}
	
	/**
	 * @api {post} /:unidade/processos/sobrestados Sobrestar processo
	 * @apiName sobrestarProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Sobrestar processo.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 *
	 * @apiParam (Request Body) {SobrestamentoProcesso} sobrestamento Objeto com o motivo do sobrestamento.
	 * @apiParam (Request Body) {String} sobrestamento.processo Número do processo a ser sobrestado. 
	 * @apiParam (Request Body) {String} sobrestamento.motivo Motivo do sobrestamento.
	 * @apiParam (Request Body) {String} [sobrestamento.processoVinculado] Número do processo vinculado.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] https://<host>/sei-broker/service/COSAP/processos/sobrestados
	 *
	 *	body:
	 *	{
	 *		"processo":"33910003093201777",
	 *		"motivo":"Sobrestando através da camada de serviços."
	 *	}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@POST
	@Path("/{unidade}/processos/sobrestados")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response sobrestarProcesso(@PathParam("unidade") String unidade, SobrestamentoProcesso sobrestamento) throws RemoteException, Exception{

		if(sobrestamento == null || (StringUtils.isBlank(sobrestamento.getProcesso()) && StringUtils.isBlank(sobrestamento.getMotivo()))){
			throw new BusinessException(messages.getMessage("erro.campos.obrigatorios.sobrestamento.processo"));
		}
		
		String retorno = seiNativeService.sobrestarProcesso(Constantes.SEI_BROKER, Operacao.SOBRESTAR_PROCESSO, unidadeResource.consultarCodigo(unidade), 
				formatarNumeroProcesso(sobrestamento.getProcesso()), formatarNumeroProcesso(sobrestamento.getProcessoVinculado()), sobrestamento.getMotivo());
		
		if(trueOrFalse(retorno)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.sobrestar.processo"));
		}
	}
	
	/**
	 * @api {delete} /:unidade/processos/sobrestados/:processo Remover sobrestamento
	 * @apiName removerSobrestamentoProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER
	 * 
	 * @apiDescription Remover sobrestamento de processo.
	 *
	 * @apiParam (Path Parameters) {String} unidade Sigla da Unidade cadastrada no SEI.
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [DELETE] https://<host>/sei-broker/service/COSAP/processos/sobrestados/33910003093201777
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */	
	@DELETE
	@Path("/{unidade}/processos/sobrestados/{processo:\\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response removerSobrestamentoProcesso(@PathParam("unidade") String unidade, @PathParam("processo") String processo) throws RemoteException, Exception{
				
		String retorno = seiNativeService.removerSobrestamentoProcesso(Constantes.SEI_BROKER, Operacao.REMOVER_SOBRESTAMENTO_PROCESSO, unidadeResource.consultarCodigo(unidade), 
				formatarNumeroProcesso(processo));
		
		if(trueOrFalse(retorno)){
			return Response.ok().build();
		}else{
			throw new Exception(messages.getMessage("erro.remover.sobrestamento.processo"));
		}
	}
	
	/**
	 * @api {get} /processos/:processo/documentos Listar documentos
	 * @apiName listarDocumentosPorProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Retorna os documentos de um determinado processo.
	 * 
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 * 
	 * @apiParam (Query Parameters) {String} [tipo=null] Identificador do tipo do documento, caso seja necessário filtrar pelo tipo
	 * @apiParam (Query Parameters) {String = "G (gerado/interno), R (recebido/externo)"} [origem=null] Filtra os documentos por gerados ou recebidos
	 * @apiParam (Query Parameters) {boolean} [somenteAssinados=false] Exibir somente documentos assinados
	 * @apiParam (Query Parameters) {String} [pagina=1] Número da página
	 * @apiParam (Query Parameters) {String} [qtdRegistros = 50] Quantidade de registros que serão exibidos por página
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i https://<host>/sei-broker/service/processos/33910003149201793/documentos
	 *
	 * @apiSuccess (Sucesso Response Body - 200) {List} documentos Lista com os documentos encontrados.
	 * @apiSuccess (Sucesso Response Body - 200) {DocumentoResumido} documentos.documentoResumido Resumo do documento encontrado no SEI.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.numero Número do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.numeroInformado Número informado na inclusão do documento, também conhecido como número de árvore.
	 * @apiSuccess (Sucesso Response Body - 200) {String="GERADO","RECEBIDO"} documentos.documentoResumido.origem Origem do documento, se o mesmo é um documento "GERADO" internamente ou "RECEBIDO" de uma fonte externa.
	 * @apiSuccess (Sucesso Response Body - 200) {Data} documentos.documentoResumido.dataGeracao Data de geração do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {Tipo} documentos.documentoResumido.tipo Objeto representando o tipo do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.tipo.codigo Identificados do tipo do documento, também conhecido como série.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.tipo.nome Nome do tipo do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentos.documentoResumido.tipoConferencia Tipo de conferência do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {boolean} documentos.documentoResumido.assinado Boolean indicando se o documento foi assinado.
	 * 
	 * @apiSuccess (Sucesso Response Header- 200) {header} total_registros Quantidade de registros que existem para essa consulta
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "numero": "0670949",
	 *       "numeroInformado": "594",
	 *       "origem": "RECEBIDO",
	 *       "dataGeracao": "2015-08-10T00:00:00-03:00",
	 *       "tipo": {
	 *       	"codigo": "629",
	 *       	"nome": "Relatório de Arquivamento-SIF"
	 *       }
	 *       "tipoConferencia": "4",
	 *       "assinado": true
	 *     }
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/processos/{processo:\\d+}/documentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response listarDocumentosPorProcesso(@PathParam("processo") String processo, @QueryParam("tipo")String tipo,
			@QueryParam("origem") String origem, @QueryParam("somenteAssinados") boolean somenteAssinados, @QueryParam("pagina") String pagina, 
			@QueryParam("qtdRegistros") String qtdRegistros)throws RemoteException, Exception{
		
		Integer tamanhoPagina = (qtdRegistros == null ? null : parseInt(qtdRegistros));
		
		String idProcedimento = consultarIdProcedimento(processo);
			
		Long totalDocumentosProcesso = documentoDAO.countDocumentosProcesso(idProcedimento, tipo, origem, somenteAssinados);

		if(totalDocumentosProcesso < 1L){
			throw new ResourceNotFoundException(messages.getMessage("erro.processo.sem.documentos", formatarNumeroProcesso(processo)));
		}
		
		List<DocumentoResumido> documentosProcesso = documentoDAO.getDocumentosProcesso(idProcedimento, tipo, origem, somenteAssinados, 
				pagina == null ? null : parseInt(pagina), tamanhoPagina);

		return Response.status(getStatus(totalDocumentosProcesso.intValue(), tamanhoPagina)).header("total_registros", totalDocumentosProcesso)
				.entity(new GenericEntity<List<DocumentoResumido>>(documentosProcesso){}).build();
	}
	
	/**
	 * @api {get} /processos/:processo/documentos/:documento Consultar documento
	 * @apiName consultarDocumentoDoProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta um documento de determinado processo.
	 * 
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 * @apiParam (Path Parameters) {String} documento Número do documento.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i https://<host>/sei-broker/service/processos/33910002924201874/documentos/55737058
	 *
	 * @apiSuccess (Sucesso Response Body - 200) {DocumentoResumido} documentoResumido Resumo do documento encontrado no SEI.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentoResumido.numero Número do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentoResumido.numeroInformado Número informado na inclusão do documento, também conhecido como número de árvore.
	 * @apiSuccess (Sucesso Response Body - 200) {String="GERADO","RECEBIDO"} documentoResumido.origem Origem do documento, se o mesmo é um documento "GERADO" internamente ou "RECEBIDO" de uma fonte externa.
	 * @apiSuccess (Sucesso Response Body - 200) {Data} documentoResumido.dataGeracao Data de geração do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {Tipo} documentoResumido.tipo Objeto representando o tipo do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentoResumido.tipo.codigo Identificados do tipo do documento, também conhecido como série.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentoResumido.tipo.nome Nome do tipo do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {String} documentoResumido.tipoConferencia Tipo de conferência do documento.
	 * @apiSuccess (Sucesso Response Body - 200) {boolean} documentoResumido.assinado Boolean indicando se o documento foi assinado.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "numero": "0670949",
	 *       "numeroInformado": "594",
	 *       "origem": "RECEBIDO",
	 *       "dataGeracao": "2015-08-10T00:00:00-03:00",
	 *       "tipo": {
	 *       	"codigo": "629",
	 *       	"nome": "Relatório de Arquivamento-SIF"
	 *       }
	 *       "tipoConferencia": "4",
	 *       "assinado": true
	 *     }
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/processos/{processo:\\d+}/documentos/{documento:\\d+}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public DocumentoResumido consultarDocumentoDoProcesso(@PathParam("processo") String processo, @PathParam("documento") String documento)throws RemoteException, Exception{
		String idProcedimento = consultarIdProcedimento(processo);
		try {
			DocumentoResumido documentoProcesso = documentoDAO.getDocumentoProcesso(idProcedimento, documento);
			
			return documentoProcesso;
		} catch (Exception e) {
			throw new ResourceNotFoundException(messages.getMessage("erro.documento.nao.encontrado", documento, formatarNumeroProcesso(processo)));
		}		
	}
	
	/**
	 * @api {get} /processos/:processo/unidades Consultar unidades
	 * @apiName consultarUnidadesProcesso
	 * @apiGroup Processo
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Consulta as unidades onde o processo está aberto.
	 * 
	 * @apiParam (Path Parameters) {String} processo Número do processo.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i https://<host>/sei-broker/service/processos/33910002924201874/unidades
	 *
	 * @apiSuccess (Sucesso Response Body - 200) {List} unidades Lista da unidades onde o processo está aberto.
	 * @apiSuccess (Sucesso Response Body - 200) {Unidade} unidades.unidade Objeto Unidade.
	 * @apiSuccess (Sucesso Response Body - 200) {String} unidades.unidade.idUnidade Identificador da unidade.
	 * @apiSuccess (Sucesso Response Body - 200) {String} unidades.unidade.sigla Sigla de unidade.
	 * @apiSuccess (Sucesso Response Body - 200) {String} unidades.unidade.descricao Descrição da unidade.
	 * @apiSuccess (Sucesso Response Body - 200) {String} unidades.unidade.sinProtocolo Descrição pendente da área de negócio.
	 * @apiSuccess (Sucesso Response Body - 200) {String} unidades.unidade.sinArquivamento Descrição pendente da área de negócio.
	 * @apiSuccess (Sucesso Response Body - 200) {String} unidades.unidade.sinOuvidoria Descrição pendente da área de negócio.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     [
	 *     	{
	 *      	 "idUnidade": "110000934",
	 *      	 "sigla": "COSAP",
	 *      	 "descricao": "Coordenadoria de Sistemas e Aplicativos",
	 *      	 "sinProtocolo": "S",
	 *      	 "sinArquivamento": "N",
	 *      	 "sinOuvidoria": "N"
	 *     	}
	 *     ]
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("/processos/{processo:\\d+}/unidades")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarUnidadesProcesso(@PathParam("processo") String processo) throws Exception{
		String idProcedimento = consultarIdProcedimento(processo);
		
		List<Unidade> unidades = unidadeDAO.listarUnidadesProcesso(idProcedimento);
		
		if(unidades.isEmpty()){
			throw new ResourceNotFoundException(messages.getMessage("erro.nao.unidades.processo.aberto"));
		}
		
		return Response.ok(unidades).build();
	}
	
	public URI getResourcePath(String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}
	
	public boolean isAutoFormatar(String valor){
		return !("N".equals(valor) || "n".equals(valor));
	}
	
	public AtributoAndamento[] buildAtributosAndamento(HashMap<String, String> map){
		if(map.isEmpty()){
			return null;
		}
				
		List<AtributoAndamento> atributos = new ArrayList<AtributoAndamento>();
		
		map.forEach((k, v) -> atributos.add(new AtributoAndamento(k, v)));
	
		return atributos.toArray(new AtributoAndamento[atributos.size()]);
	}
	
	public String consultarIdProcedimento(String processo) throws Exception{
		try{
			return ((BigInteger) processoDAO.getIdProcedimento(formatarNumeroProcesso(processo))).toString();			
		}catch(NoResultException ex){
			throw new BusinessException(messages.getMessage("erro.processo.nao.pertence.sei", formatarNumeroProcesso(processo)));
		}
	}
	
	public Status getStatus(Integer quantidadeItens, Integer tamanhoPagina){
		if(quantidadeItens > (tamanhoPagina == null ? Constantes.TAMANHO_PAGINA_PADRAO : tamanhoPagina )){
			return Status.PARTIAL_CONTENT;
		}
		
		return Status.OK;
	}
}
