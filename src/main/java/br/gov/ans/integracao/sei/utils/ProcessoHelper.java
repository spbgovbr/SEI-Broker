package br.gov.ans.integracao.sei.utils;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;

import br.gov.ans.integracao.sei.exceptions.BusinessException;
import br.gov.ans.integracao.sei.modelo.NovoProcesso;
import br.gov.ans.utils.MessageUtils;

public class ProcessoHelper {
	
    @Inject
    private MessageUtils messages;

    @Inject
    private InteressadoHelper interessadoHelper;
    
	public void validarNovoProcesso(NovoProcesso novoProcesso) throws BusinessException{
		if(novoProcesso == null){
			throw new BusinessException(messages.getMessage("erro.novo.processo.vazio"));
		}
		
		if(novoProcesso.getDadosProcesso() == null){
			throw new BusinessException(messages.getMessage("erro.dados.processo.nao.informados"));
		}
		
		if(isSemInteressados(novoProcesso)){
			throw new BusinessException(messages.getMessage("erro.interessado.nao.informado"));
		}else{
			interessadoHelper.tratarInteressados(novoProcesso.getDadosProcesso().getInteressados());
		}
	}
	
	public boolean isSemInteressados(NovoProcesso processo){
		return ArrayUtils.isEmpty(processo.getDadosProcesso().getInteressados());
	}
}
