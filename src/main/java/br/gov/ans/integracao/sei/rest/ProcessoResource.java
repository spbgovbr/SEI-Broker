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
import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sei.helper.ProcessoHelper;
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
    private ProcessoHelper processoHelper;
    
    @Inject
    private Logger logger;
    
	@Context
	private UriInfo uriInfo;

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
			logger.error(ex);
			logger.debug(ex, ex);
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

	@GET
	@Path("{unidade}/processos/tipos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public TipoProcedimento[] listarTiposProcesso(@PathParam("unidade") String unidade, @QueryParam("serie") String serie) throws Exception{
		return seiNativeService.listarTiposProcedimento(Constantes.SEI_BROKER, Operacao.LISTAR_TIPOS_PROCESSO, unidadeResource.consultarCodigo(unidade), serie);		
	}
	
	@POST
	@Path("{unidade}/processos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response abrirProcesso(@PathParam("unidade") String unidade, @QueryParam("auto-formatacao") String autoFormatar, NovoProcesso processo) throws RemoteException, Exception{
		processoHelper.validarNovoProcesso(processo);

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
	
	@GET
	@Path("/interessados/{interessado}/processos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Wrapped(element = "processos")
	public Response consultarProcessosPorInteressado(@PathParam("interessado") String interessado, @QueryParam("unidade") String unidade, @QueryParam("tipo") String tipoProcesso, 
			@QueryParam("crescente") boolean crescente, @QueryParam("pagina") String pagina, @QueryParam("qtdRegistros") String qtdRegistros) throws Exception{
		
		return consultarProcessos(interessado, unidade, tipoProcesso, crescente, pagina, qtdRegistros);
	}

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

	@POST
	@Path("/{unidade}/processos/{processo:\\d+}/andamentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response lancarAndamento(@PathParam("unidade") String unidade, @PathParam("processo") String processo, NovoAndamento andamento) throws RemoteException, Exception{
		
		Andamento andamentoLancado = seiNativeService.lancarAndamento(Constantes.SEI_BROKER, Operacao.LANCAR_ANDAMENTO, unidadeResource.consultarCodigo(unidade), formatarNumeroProcesso(processo)
				, andamento.getTarefa(), andamento.getTarefaModulo(), buildAtributosAndamento(andamento.getAtributos()));		
		
		return Response.status(Status.CREATED).entity(andamentoLancado).build();
	}	

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

	@GET
	@Path("/processos/{processo:\\d+}/documentos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response listarDocumentosPorProcesso(@PathParam("processo") String processo, @QueryParam("tipo")String tipo,
			@QueryParam("origem") String origem, @QueryParam("somenteAssinados") boolean somenteAssinados, @QueryParam("numeroInformado") String numeroInformado,
			@QueryParam("pagina") String pagina, @QueryParam("qtdRegistros") String qtdRegistros)throws RemoteException, Exception{
		
		Integer tamanhoPagina = (qtdRegistros == null ? null : parseInt(qtdRegistros));
		
		String idProcedimento = consultarIdProcedimento(processo);
			
		Long totalDocumentosProcesso = documentoDAO.countDocumentosProcesso(idProcedimento, tipo, origem, somenteAssinados, numeroInformado);

		if(totalDocumentosProcesso < 1L){
			throw new ResourceNotFoundException(messages.getMessage("erro.processo.sem.documentos", formatarNumeroProcesso(processo)));
		}
		
		List<DocumentoResumido> documentosProcesso = documentoDAO.getDocumentosProcesso(idProcedimento, tipo, origem, somenteAssinados, numeroInformado,
				pagina == null ? null : parseInt(pagina), tamanhoPagina);

		return Response.status(getStatus(totalDocumentosProcesso.intValue(), tamanhoPagina)).header("total_registros", totalDocumentosProcesso)
				.entity(new GenericEntity<List<DocumentoResumido>>(documentosProcesso){}).build();
	}

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
