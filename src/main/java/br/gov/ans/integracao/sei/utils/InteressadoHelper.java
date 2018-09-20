package br.gov.ans.integracao.sei.utils;

import javax.inject.Inject;

import br.gov.ans.integracao.sei.client.Interessado;
import br.gov.ans.integracao.sei.modelo.Contato;

public class InteressadoHelper {
	@Inject
	private ContatoHelper contatoHelper;
	
	public void tratarInteressados(Interessado[] interessados){
		for(int i=0; i < interessados.length; i++){
			interessados[i] = tratarInteressado(interessados[i]);
		}
	}
	
	public Interessado tratarInteressado(Interessado interessado){		
		if(isInteressadoCadastradoComoNaoTemporario(interessado.getSigla())){
			return getRegistroInteressadoNaoTemporarioMaisAntigo(interessado.getSigla());		
		}
		
		return interessado;
	}
	
	private boolean isInteressadoCadastradoComoNaoTemporario(String sigla){
		return contatoHelper.isContatoCadastradoComoNaoTemporario(sigla); 
	}
	
	public Interessado getRegistroInteressadoNaoTemporarioMaisAntigo(String sigla){
		Contato contatoNaoTemporarioMaisAntigo = contatoHelper.getContatoNaoTemporarioMaisAntigo(sigla);
		
		return convertContatoParaInteressado(contatoNaoTemporarioMaisAntigo);
	}

	private Interessado convertContatoParaInteressado(Contato contato){
		Interessado interessado = new Interessado();
		
		interessado.setNome(contato.getNome());
		interessado.setSigla(contato.getSigla());
		
		return interessado;		
	}
	
}
