package br.gov.ans.integracao.sei.dao;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import br.gov.ans.integracao.sei.client.Unidade;
import br.gov.ans.integracao.sei.modelo.UnidadeTarefa;

public class UnidadeDAO {
	@PersistenceContext(unitName = "sei_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	private static final int ABERTURA_PROCESSO = 1;
	private static final int CONCLUSAO_PROCESSO = 28;
	private static final int REABERTURA_PROCESSO = 29;
	private static final int ENVIO_PROCESSO = 32;
	private static final int CONCLUSAO_AUTOMATICA = 41;
	private static final int RECEBIMENTO_PROCESSO = 48;

	public List<Unidade> listarUnidadesProcesso(String idProcedimento){
		StringBuilder sql = new StringBuilder("SELECT u.id_unidade idUnidade, u.sigla, u.descricao, u.sin_protocolo sinProtocolo, ");
		sql.append("u.sin_arquivamento sinArquivamento, u.sin_ouvidoria sinOuvidoria, t.id_tarefa tarefa, a.dth_abertura data ");
		sql.append("FROM protocolo p ");
		sql.append("JOIN atividade a ON p.id_protocolo = a.id_protocolo ");
		sql.append("JOIN unidade u ON a.id_unidade = u.id_unidade ");
		sql.append("JOIN tarefa t ON a.id_tarefa = t.id_tarefa ");
		sql.append("WHERE p.id_protocolo = :protocolo ");
		sql.append("and t.id_tarefa in (");
		sql.append(ABERTURA_PROCESSO + ",");
		sql.append(REABERTURA_PROCESSO + ",");
		sql.append(CONCLUSAO_PROCESSO + ",");
		sql.append(ENVIO_PROCESSO + ",");
		sql.append(CONCLUSAO_AUTOMATICA + ",");
		sql.append(RECEBIMENTO_PROCESSO);
		sql.append(") ");
		sql.append("order by a.dth_abertura desc ");
		
		Query query = em.createNativeQuery(sql.toString(), UnidadeTarefa.class);
		query.setParameter("protocolo", idProcedimento);
		
		List<UnidadeTarefa> resultado = query.getResultList();

		resultado = removeUnidadesProcessoConcluido(resultado);
		
		resultado = removeRegistrosDeTarefasDeConclusao(resultado);
		
		return convertParaListUnidadeRemovendoDuplicidades(resultado);
	}
	
	private List<UnidadeTarefa> removeUnidadesProcessoConcluido(List<UnidadeTarefa> unidades){
		unidades.removeIf(u -> unidades.stream()
				.filter(c -> (c.getTarefa() == CONCLUSAO_PROCESSO || c.getTarefa() == CONCLUSAO_AUTOMATICA))
				.anyMatch(r -> 
					(
						r.getIdUnidade() == u.getIdUnidade() && 
						r.getData().after(u.getData())
					)
			));
		
		return unidades;
	}

	private List<UnidadeTarefa> removeRegistrosDeTarefasDeConclusao(List<UnidadeTarefa> unidades){
		return unidades.stream()
					.filter(u -> u.getTarefa() != CONCLUSAO_PROCESSO)
					.filter(u -> u.getTarefa() != CONCLUSAO_AUTOMATICA)
				.collect(Collectors.toList());
	}
	
	private List<Unidade> convertParaListUnidadeRemovendoDuplicidades(List<UnidadeTarefa> unidades){
		return unidades.stream()
				.map(u -> (Unidade) u)
				.distinct()
				.collect(Collectors.toList());
	}
}
