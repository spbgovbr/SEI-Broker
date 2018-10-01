package br.gov.ans.integracao.sei.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jboss.logging.Logger;

import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.client.Unidade;
import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;
import br.gov.ans.utils.MessageUtils;

@Path("/unidades")
public class UnidadeResource {

	@Inject
	private Logger logger;
	
    @Inject
	private SeiPortTypeProxy seiNativeService;
    
    @Inject
    private MessageUtils messages;
	
	private static HashMap<String,String> unidades;
	
	private static Date dataCarregamentoUnidades = new Date();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public br.gov.ans.integracao.sei.client.Unidade[] listarUnidades() throws Exception{
		Unidade[] lista = seiNativeService.listarUnidades(Constantes.SEI_BROKER, Operacao.LISTAR_UNIDADES, null, null);

		if(isMapUnidadesExpirado()){
			carregarMapUnidades(lista);
		}
		
		return lista;
	}

	@GET
	@Path("/{chave}/codigo")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String consultarCodigo(@PathParam("chave") String chave) throws Exception{
		logger.debug(messages.getMessage("consultando.unidade",chave));

		if(isInteger(chave)){
			return chave;
		}
		
		if(!getUnidades().containsKey(chave.toUpperCase())){
			throw new BusinessException(messages.getMessage("erro.unidade.nao.encontrada", chave));
		}
		
		return getUnidades().get(chave.toUpperCase());
	}
	
	public String[] buscarCodigoUnidades(String[] unidades) throws Exception{
		if(ArrayUtils.isNotEmpty(unidades)){
			ArrayList<String> codigos = new ArrayList<String>();
			
			for(String unidade: unidades){
				if(isInteger(unidade)){
					codigos.add(unidade);
				}else{
					codigos.add(consultarCodigo(unidade));					
				}
			}
			
			return codigos.toArray(new String[codigos.size()]);
		}
		
		return unidades;
	}
	
	public HashMap<String,String> getUnidades() throws Exception {
		if(unidades == null || isMapUnidadesExpirado()){
			carregarMapUnidades(listarUnidades());
		}
		
		return unidades;
	}
	
	public void carregarMapUnidades(Unidade[] lista) throws Exception{
		logger.info(messages.getMessage("carregando.unidades"));

		unidades = new HashMap<String,String>();
		
		for(br.gov.ans.integracao.sei.client.Unidade u : lista){
			unidades.put(u.getSigla(), u.getIdUnidade());
		}
		
		dataCarregamentoUnidades = new Date();
	}
	
	public boolean isMapUnidadesExpirado(){
		Date dataExpiracao = DateUtils.addDays(dataCarregamentoUnidades, 1);
		
		if(dataExpiracao.before(new Date())){
			return true;
		}
		
		return false;
	}	
	
	public boolean isInteger(String valor){		
		try{
			Integer.parseInt(valor);
			return true;
		}catch(Exception e){
			return false;			
		}	
	}
}
