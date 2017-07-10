package br.gov.ans.integracao.sei.dao;

import static br.gov.ans.integracao.sei.utils.Util.setPaginacaoQuery;
import static br.gov.ans.integracao.sei.utils.Util.setQueryParameters;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.integracao.sei.modelo.ProcessoResumido;

public class ProcessoDAO {

	@PersistenceContext(unitName = "sei_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<ProcessoResumido> getProcessos(String interessado, String unidade, Integer pagina, Integer qtdRegistros){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuilder builder = new StringBuilder("SELECT ");
		builder.append("pr.descricao, u.sigla as unidade, pr.protocolo_formatado_pesquisa protocolo, pr.protocolo_formatado, pr.dta_geracao data_geracao ");
		builder.append("FROM participante p, protocolo pr, contato c, unidade u ");
		builder.append("WHERE " );
		builder.append("p.id_contato = c.id_contato AND p.id_protocolo = pr.id_protocolo AND p.id_unidade = u.id_unidade AND pr.sta_protocolo = 'P' ");
		
		if(StringUtils.isNotBlank(interessado)){
			builder.append("AND c.sigla = :interessado ");
			parametros.put("interessado", interessado);
		}
		
		if(StringUtils.isNotBlank(unidade)){
			builder.append("AND u.sigla = :unidade ");
			parametros.put("unidade", unidade);
		}
		
		builder.append("order by pr.dta_geracao asc");

		Query query = em.createNativeQuery(builder.toString(), "ProcessoResumidoMapping");
		
		setQueryParameters(query, parametros);
		
		setPaginacaoQuery(query, pagina, qtdRegistros);
				
		return query.getResultList();
	}

	public Long countProcessos(String interessado, String unidade){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuilder builder = new StringBuilder("SELECT ");
		builder.append("COUNT(*) ");
		builder.append("FROM participante p, protocolo pr, contato c, unidade u ");
		builder.append("WHERE " );
		builder.append("p.id_contato = c.id_contato AND p.id_protocolo = pr.id_protocolo AND p.id_unidade = u.id_unidade AND pr.sta_protocolo = 'P' ");
		
		if(StringUtils.isNotBlank(interessado)){
			builder.append("AND c.sigla = :interessado ");
			parametros.put("interessado", interessado);
		}
		
		if(StringUtils.isNotBlank(unidade)){
			builder.append("AND u.sigla = :unidade ");
			parametros.put("unidade", unidade);
		}
		
		Query query = em.createNativeQuery(builder.toString());
		
		setQueryParameters(query, parametros);
		
		return Long.valueOf(query.getSingleResult().toString());
	}
}
