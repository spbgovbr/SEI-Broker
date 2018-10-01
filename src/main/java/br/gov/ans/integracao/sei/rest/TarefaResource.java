package br.gov.ans.integracao.sei.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.dao.TarefaDAO;
import br.gov.ans.integracao.sei.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sei.modelo.Tarefa;
import br.gov.ans.utils.MessageUtils;

@Path("/tarefas")
public class TarefaResource {

	@Inject
	private TarefaDAO dao;
	
    @Inject
    private MessageUtils messages;
	
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
