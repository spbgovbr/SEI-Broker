package br.gov.ans.integracao.sei.dao;

import static br.gov.ans.integracao.sei.utils.Util.setPaginacaoQuery;
import static br.gov.ans.integracao.sei.utils.Util.setQueryParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.integracao.sei.modelo.DocumentoResumido;

public class DocumentoDAO {
	@PersistenceContext(unitName = "sei_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<DocumentoResumido> getDocumentosV1(String interessado, String unidade, Integer pagina, Integer qtdRegistros){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuilder builder = new StringBuilder("SELECT ");
		builder.append("pr.protocolo_formatado_pesquisa numero, s.nome tipo, d.numero numeroInformado, ");
		builder.append("(select protocolo_formatado from protocolo where id_protocolo = d.id_procedimento) processo, ");
		builder.append("u.sigla unidade, CASE pr.sta_protocolo WHEN 'G' THEN 'GERADO' ELSE 'RECEBIDO' END origem, pr.dta_geracao dataGeracao ");
		builder.append("FROM participante p, protocolo pr, documento d, contato c, serie s, unidade u ");
		builder.append("WHERE p.id_contato = c.id_contato AND p.id_protocolo = pr.id_protocolo AND p.id_protocolo = d.id_documento ");
		builder.append("AND p.id_unidade = u.id_unidade AND d.id_serie = s.id_serie ");
		
		if(StringUtils.isNotBlank(interessado)){
			builder.append("AND c.sigla = :interessado ");
			parametros.put("interessado", interessado);
		}
		
		if(StringUtils.isNotBlank(unidade)){
			builder.append("AND u.sigla = :unidade ");
			parametros.put("unidade", unidade);
		}
		
		builder.append("order by pr.dta_geracao asc");

		Query query = em.createNativeQuery(builder.toString(), "DocumentoResumidoMapping");
		
		setQueryParameters(query, parametros);
		
		setPaginacaoQuery(query, pagina, qtdRegistros);
		
		return query.getResultList();
	}
	
	public List<DocumentoResumido> getDocumentos(String interessado, String codigoTipo, Integer pagina, Integer qtdRegistros, boolean somenteAssinados, boolean ordemCrescente, 
			boolean orderByProcesso){
		HashMap<String, Object> parametros = new HashMap<String, Object>();

		StringBuilder builder = new StringBuilder("SELECT pr.protocolo_formatado_pesquisa numero, s.nome tipoNome, s.id_serie tipoCodigo, d.numero numeroInformado, ");
		builder.append("CASE pr.sta_protocolo WHEN 'G' THEN 'GERADO' ELSE 'RECEBIDO' END origem, d.id_tipo_conferencia tipoConferencia, ");
		builder.append("pr.dta_geracao dataGeracao, pr2.protocolo_formatado as processo, u.sigla as unidade, ");
		builder.append("CASE WHEN a.id_assinatura is null THEN false ELSE true END assinado ");
		builder.append("FROM documento AS d ");

		
		if(somenteAssinados){
			builder.append("RIGHT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}else{
			builder.append("LEFT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}

		builder.append("JOIN participante p	ON p.id_protocolo = d.id_documento ");
		builder.append("JOIN protocolo AS pr ON pr.id_protocolo = d.id_documento "); 
		builder.append("JOIN protocolo AS pr2 ON pr2.id_protocolo = d.id_procedimento "); 
		builder.append("JOIN serie AS s ON d.id_serie = s.id_serie ");
		builder.append("JOIN unidade AS u ON u.id_unidade = d.id_unidade_responsavel ");
		builder.append("WHERE p.id_contato in (select c.id_contato from contato c where c.sigla = :interessado) ");
		parametros.put("interessado", interessado);
		
		if(StringUtils.isNotBlank(codigoTipo)){
			builder.append("AND s.id_serie in (:codigoTipo)");
			parametros.put("codigoTipo", codigoTipo);
		}
				
		builder.append("GROUP BY numero "); 

		if(orderByProcesso){
			builder.append("ORDER BY processo ");			
		}else{
			builder.append("ORDER BY pr.dta_geracao ");			
		}

		if(!ordemCrescente){
			builder.append("DESC");			
		}
		
		Query query = em.createNativeQuery(builder.toString(), "DocumentoResumidoMapping");
		
		setQueryParameters(query, parametros);
		
		setPaginacaoQuery(query, pagina, qtdRegistros);
		
		List<Object[]> results = query.getResultList();
		
		List<DocumentoResumido> documentos = new ArrayList<DocumentoResumido>();
		
		results.stream().forEach((record) -> {
			DocumentoResumido documento = (DocumentoResumido) record[0];
			documentos.add(documento);
		});
		
		return documentos;
	}
	
	public Long countDocumentos(String interessado, String codigoTipo, boolean somenteAssinados){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuilder builder = new StringBuilder("SELECT count(distinct pr.protocolo_formatado_pesquisa) ");
		builder.append("FROM documento AS d ");

		
		if(somenteAssinados){
			builder.append("RIGHT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}else{
			builder.append("LEFT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}

		builder.append("JOIN participante p	ON p.id_protocolo = d.id_documento ");
		builder.append("JOIN protocolo AS pr ON pr.id_protocolo = d.id_documento "); 
		builder.append("JOIN protocolo AS pr2 ON pr2.id_protocolo = d.id_procedimento "); 
		builder.append("JOIN serie AS s ON d.id_serie = s.id_serie ");
		builder.append("JOIN unidade AS u ON u.id_unidade = d.id_unidade_responsavel ");
		builder.append("WHERE p.id_contato in (select c.id_contato from contato c where c.sigla = :interessado) ");
		parametros.put("interessado", interessado);
		
		if(StringUtils.isNotBlank(codigoTipo)){
			builder.append("AND s.id_serie in (:codigoTipo)");
			parametros.put("codigoTipo", codigoTipo);
		}
		
		Query query = em.createNativeQuery(builder.toString());
		
		setQueryParameters(query, parametros);
		
		try{
			return Long.valueOf(query.getSingleResult().toString());
		}catch(NoResultException ex){
			return 0L;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoResumido> getDocumentosProcesso(String idProcedimento, String codigoTipo, String origem, boolean somenteAssinados){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuilder builder = new StringBuilder("SELECT pr.protocolo_formatado_pesquisa numero, s.nome tipoNome, s.id_serie tipoCodigo, d.numero numeroInformado, ");
		builder.append("CASE pr.sta_protocolo WHEN 'G' THEN 'GERADO' ELSE 'RECEBIDO' END origem, d.id_tipo_conferencia tipoConferencia, ");
		builder.append("pr.dta_geracao dataGeracao, null as processo, null as unidade, ");
		builder.append("CASE WHEN a.id_assinatura is null THEN false ELSE true END assinado ");
		builder.append("FROM documento AS d ");		

		if(somenteAssinados){
			builder.append("RIGHT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}else{
			builder.append("LEFT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}
		
		builder.append("JOIN protocolo AS pr ON pr.id_protocolo = d.id_documento "); 
		builder.append("JOIN serie AS s ON d.id_serie = s.id_serie ");
		builder.append("WHERE d.id_procedimento = :idProcedimento ");

		parametros.put("idProcedimento", idProcedimento);
		
		if(StringUtils.isNotBlank(codigoTipo)){
			builder.append("AND s.id_serie in (:codigoTipo) ");
			parametros.put("codigoTipo", codigoTipo);
		}
		
		if(StringUtils.isNotBlank(origem)){
			builder.append("AND pr.sta_protocolo = :origem ");
			parametros.put("origem", origem);
		}
		
		builder.append("GROUP BY numero ORDER BY pr.dta_geracao ASC");
				
		Query query = em.createNativeQuery(builder.toString(), "DocumentoResumidoMapping");
		
		setQueryParameters(query, parametros);
		
		List<Object[]> results = null;
		List<DocumentoResumido> documentos = new ArrayList<DocumentoResumido>();
		
		try{
			results = query.getResultList();			
				
			results.stream().forEach((record) -> {
				DocumentoResumido documento = (DocumentoResumido) record[0];
				documentos.add(documento);
			});			
		}catch(NoResultException ex){
			return documentos;
		}
		
		return documentos;
	}

}
