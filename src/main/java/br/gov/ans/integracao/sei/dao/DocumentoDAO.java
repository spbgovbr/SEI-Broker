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
	
	public List<DocumentoResumido> getDocumentos(String interessado, String codigoTipo, Integer pagina, Integer qtdRegistros, boolean somenteAssinados, 
			boolean ordemCrescente,	boolean orderByProcesso){
		HashMap<String, Object> parametros = new HashMap<String, Object>();

		StringBuilder builder = new StringBuilder("SELECT pr.protocolo_formatado_pesquisa numero, s.nome tipoNome, s.id_serie tipoCodigo, d.numero numeroInformado, ");
		builder.append("null as nome, ");
		builder.append("CASE pr.sta_protocolo WHEN 'G' THEN 'GERADO' ELSE 'RECEBIDO' END origem, d.id_tipo_conferencia tipoConferencia, ");
		builder.append("pr.dta_geracao dataGeracao, ");
		builder.append("(select pr2.protocolo_formatado from protocolo pr2 where pr2.id_protocolo = d.id_procedimento) as processo, ");
		builder.append("u.sigla as unidade, ");
		builder.append("CASE WHEN a.id_assinatura is null THEN false ELSE true END assinado ");
		builder.append("FROM documento AS d ");

		
		if(somenteAssinados){
			builder.append("RIGHT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}else{
			builder.append("LEFT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}

		builder.append("JOIN participante As p ON p.id_protocolo = d.id_documento ");
		builder.append("JOIN protocolo AS pr ON pr.id_protocolo = d.id_documento "); 
		builder.append("JOIN serie AS s ON d.id_serie = s.id_serie ");
		builder.append("JOIN unidade AS u ON u.id_unidade = d.id_unidade_responsavel ");
		builder.append("WHERE p.id_contato in (select c.id_contato from contato c where c.sigla = :interessado) ");
		parametros.put("interessado", interessado);
		
		if(StringUtils.isNotBlank(codigoTipo)){
			builder.append("AND s.id_serie in (:codigoTipo)");
			parametros.put("codigoTipo", codigoTipo);
		}
				
		builder.append("GROUP BY pr.protocolo_formatado_pesquisa "); 

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
		
		@SuppressWarnings("unchecked")
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
	public List<DocumentoResumido> getDocumentosProcesso(String idProcedimento, String codigoTipo, String origem, boolean somenteAssinados, 
			String numeroInformado, Integer pagina, Integer qtdRegistros){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuilder builder = new StringBuilder("SELECT pr.protocolo_formatado_pesquisa numero, s.nome tipoNome, s.id_serie tipoCodigo, ");
		builder.append("d.numero numeroInformado, an.nome nome, ");
		builder.append("CASE pr.sta_protocolo WHEN 'G' THEN 'GERADO' ELSE 'RECEBIDO' END origem, d.id_tipo_conferencia tipoConferencia, ");
		builder.append("pr.dta_geracao dataGeracao, null as processo, u.sigla as unidade, ");
		builder.append("CASE WHEN a.id_assinatura is null THEN false ELSE true END assinado ");
		builder.append("FROM documento AS d ");		

		if(somenteAssinados){
			builder.append("RIGHT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}else{
			builder.append("LEFT JOIN assinatura AS a ON d.id_documento = a.id_documento ");			
		}
		
		builder.append("JOIN protocolo AS pr ON pr.id_protocolo = d.id_documento "); 
		builder.append("JOIN serie AS s ON d.id_serie = s.id_serie ");
		builder.append("JOIN unidade AS u ON u.id_unidade = d.id_unidade_responsavel ");
		builder.append("LEFT JOIN anexo AS an ON d.id_documento = an.id_protocolo ");
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
		
		if(StringUtils.isNotBlank(numeroInformado)){
			builder.append("AND d.numero like :numeroInformado ");
			parametros.put("numeroInformado", "%"+numeroInformado+"%");
		}
		
		builder.append("GROUP BY pr.protocolo_formatado_pesquisa ORDER BY pr.dta_geracao, pr.protocolo_formatado_pesquisa ASC");
				
		Query query = em.createNativeQuery(builder.toString(), "DocumentoResumidoMapping");
		
		setQueryParameters(query, parametros);
		setPaginacaoQuery(query, pagina, qtdRegistros);
		
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
	
	public Long countDocumentosProcesso(String idProcedimento, String codigoTipo, String origem, boolean somenteAssinados, String numeroInformado){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuilder builder = new StringBuilder("SELECT count(pr.protocolo_formatado_pesquisa) ");
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
		
		if(StringUtils.isNotBlank(numeroInformado)){
			builder.append("AND d.numero like :numeroInformado ");
			parametros.put("numeroInformado", "%"+numeroInformado+"%");
		}
				
		Query query = em.createNativeQuery(builder.toString());
		
		setQueryParameters(query, parametros);
		
		try{
			return Long.valueOf(query.getSingleResult().toString());
		}catch(NoResultException ex){
			return 0L;
		}
	}
	
	public DocumentoResumido getDocumentoProcesso(String idProcedimento, String documento){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		StringBuilder builder = new StringBuilder("SELECT pr.protocolo_formatado_pesquisa numero, s.nome tipoNome, s.id_serie tipoCodigo, d.numero numeroInformado, ");
		builder.append("CASE pr.sta_protocolo WHEN 'G' THEN 'GERADO' ELSE 'RECEBIDO' END origem, d.id_tipo_conferencia tipoConferencia, ");
		builder.append("pr.dta_geracao dataGeracao, null as processo, null as unidade, ");
		builder.append("CASE WHEN a.id_assinatura is null THEN false ELSE true END assinado ");
		builder.append("FROM documento AS d ");
		builder.append("LEFT JOIN assinatura AS a ON d.id_documento = a.id_documento ");
		builder.append("JOIN protocolo AS pr ON pr.id_protocolo = d.id_documento "); 
		builder.append("JOIN serie AS s ON d.id_serie = s.id_serie ");
		builder.append("WHERE d.id_procedimento = :idProcedimento ");
		builder.append("AND pr.protocolo_formatado_pesquisa = :documento ");
		
		parametros.put("idProcedimento", idProcedimento);
		parametros.put("documento", documento);
		
		Query query = em.createNativeQuery(builder.toString(), "DocumentoResumidoMapping");
		
		setQueryParameters(query, parametros);
		
		Object result = query.getSingleResult();
						
		return (DocumentoResumido) ((Object[]) result)[0];
	}
}
