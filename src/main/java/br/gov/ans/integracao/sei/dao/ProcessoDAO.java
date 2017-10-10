package br.gov.ans.integracao.sei.dao;

import static br.gov.ans.integracao.sei.utils.Util.setPaginacaoQuery;
import static br.gov.ans.integracao.sei.utils.Util.setQueryParameters;

import java.math.BigInteger;
import java.util.ArrayList;
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
	public List<ProcessoResumido> getProcessos(String interessado, String unidade, String tipoProcesso, Integer pagina, Integer qtdRegistros, boolean crescente){
		HashMap<String, Object> parametros = new HashMap<String, Object>();

		StringBuilder builder = new StringBuilder("SELECT pr.protocolo_formatado_pesquisa numero, pr.protocolo_formatado numeroFormatado, pr.descricao, proc.id_tipo_procedimento tipoCodigo, ");
		builder.append("tp.nome tipoNome, u.sigla as unidade, pr.dta_geracao dataGeracao "); 
		builder.append("FROM protocolo pr, tipo_procedimento tp, participante p ");
		
		if(StringUtils.isNotBlank(interessado)){
			builder.append("JOIN contato c ON c.id_contato = p.id_contato ");			
		}
		
		builder.append("JOIN unidade u ON u.id_unidade = p.id_unidade ");
		builder.append("JOIN procedimento proc ON proc.id_procedimento = p.id_protocolo ");
		builder.append("WHERE pr.sta_protocolo = 'P' AND p.id_protocolo = pr.id_protocolo ");
		
		if(StringUtils.isNotBlank(interessado)){
			builder.append("AND c.sigla = :interessado ");
			parametros.put("interessado", interessado);
		}
		
		if(StringUtils.isNoneBlank(tipoProcesso)){
			builder.append("AND proc.id_tipo_procedimento = :tipoProcesso ");
			parametros.put("tipoProcesso", tipoProcesso);
		}

		builder.append("AND tp.id_tipo_procedimento = proc.id_tipo_procedimento ");
		
		if(StringUtils.isNoneBlank(unidade)){
			builder.append("AND p.id_unidade = :unidade ");
			parametros.put("unidade", unidade);
		}
		
		builder.append("group by pr.protocolo_formatado_pesquisa ");
		
		if(crescente){
			builder.append("order by pr.dta_geracao asc");
		}else{
			builder.append("order by pr.dta_geracao desc");			
		}

		Query query = em.createNativeQuery(builder.toString(), "ProcessoResumidoMapping");
		
		setQueryParameters(query, parametros);
		
		setPaginacaoQuery(query, pagina, qtdRegistros);
				
		List<Object[]> results = query.getResultList();
		
		List<ProcessoResumido> processos = new ArrayList<ProcessoResumido>();
		
		results.stream().forEach((record) -> {
		    ProcessoResumido processo = (ProcessoResumido) record[0];
		    processos.add(processo);
		});
		
		return processos;
	}

	public Long countProcessos(String interessado, String unidade, String tipoProcesso){
		HashMap<String, Object> parametros = new HashMap<String, Object>();

		StringBuilder builder = new StringBuilder("SELECT count(DISTINCT pr.protocolo_formatado_pesquisa) "); 
		builder.append("FROM protocolo pr, tipo_procedimento tp, participante p ");
		
		if(StringUtils.isNotBlank(interessado)){
			builder.append("JOIN contato c ON c.id_contato = p.id_contato ");			
		}
		
		builder.append("JOIN unidade u ON u.id_unidade = p.id_unidade ");
		builder.append("JOIN procedimento proc ON proc.id_procedimento = p.id_protocolo ");
		builder.append("WHERE pr.sta_protocolo = 'P' AND p.id_protocolo = pr.id_protocolo ");
		
		if(StringUtils.isNotBlank(interessado)){
			builder.append("AND c.sigla = :interessado ");
			parametros.put("interessado", interessado);
		}
		
		if(StringUtils.isNoneBlank(tipoProcesso)){
			builder.append("AND proc.id_tipo_procedimento = :tipoProcesso ");
			parametros.put("tipoProcesso", tipoProcesso);
		}
		
		builder.append("AND tp.id_tipo_procedimento = proc.id_tipo_procedimento ");
		
		if(StringUtils.isNoneBlank(unidade)){
			builder.append("AND p.id_unidade = :unidade ");
			parametros.put("unidade", unidade);
		}
		
		Query query = em.createNativeQuery(builder.toString());
		
		setQueryParameters(query, parametros);
		
		return Long.valueOf(query.getSingleResult().toString());
	}
	
	
	public BigInteger getIdProcedimento(String processo){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		String sql = "SELECT id_protocolo FROM protocolo WHERE protocolo_formatado = :processo";
		
		parametros.put("processo", processo);
		
		Query query = em.createNativeQuery(sql);
		
		setQueryParameters(query, parametros);
		
		return (BigInteger) query.getSingleResult();		
	}
}
