package br.gov.ans.integracao.sei.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.ans.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sei.dao.TarefaDAO;
import br.gov.ans.integracao.sei.modelo.Tarefa;
import br.gov.ans.utils.MessageUtils;

@Path("/tarefas")
public class TarefaResource {

	@Inject
	private TarefaDAO dao;
	
    @Inject
    private MessageUtils messages;
	
    
	/**
	 * @api {get} /tarefas Listar tarefas
	 * @apiName listarTarefas
	 * @apiGroup Tarefa
	 * @apiVersion 2.0.0
	 * 
	 * @apiPermission RO_SEI_BROKER ou RO_SEI_BROKER_CONSULTA
	 * 
	 * @apiDescription Lista os tipos de tarefas existentes no SEI. 
	 *
	 * @apiParam (Query Parameters) {String} [nome] Filtro para o nome da tarefa.
	 *
	 * @apiExample {curl} Exemplo de requisição:
	 * 	curl -i https://<host>/sei-broker/service/tarefas
	 * 
	 * @apiSuccess (Sucesso - 200) {List} tarefas Lista com as tarefas
	 * @apiSuccess (Sucesso - 200) {String} tarefas.identificados Identificador da tarefa.
	 * @apiSuccess (Sucesso - 200) {String} tarefas.nome Nome da tarefa.
	 * @apiSuccess (Sucesso - 200) {String} tarefas.historicoResumido S/N - Sinalizador indica se a tarefa aparecerá no histórico resumido.
	 * @apiSuccess (Sucesso - 200) {String} tarefas.historicoCompleto S/N - Sinalizador indica se a tarefa aparecerá no histórico completo.
	 * @apiSuccess (Sucesso - 200) {String} tarefas.fecharAndamentosAbertos S/N - Sinalizador indica se a tarefa fecha andamentos abertos.
	 * @apiSuccess (Sucesso - 200) {String} tarefas.lancarAndamentoFechado S/N - Sinalizador indica que a tarefa encerra o andamento.
	 * @apiSuccess (Sucesso - 200) {String} tarefas.permiteProcessoFechado S/N - Sinalizador indica se é permitida essa tarefa em processo fechado.
	 * @apiSuccess (Sucesso - 200) {String} tarefas.identicadorTarefaModulo Identificador de tarefa módulo.
	 *
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Tarefa> listarTarefas(@QueryParam("nome") String filtroNome) throws ResourceNotFoundException{
		
		List<Tarefa> tarefas = dao.getTarefas(filtroNome);
		
		if(tarefas.isEmpty()){
			throw new ResourceNotFoundException(messages.getMessage("erro.tarefas.nao.encontradas"));
		}
		
		return tarefas;
	}
}
