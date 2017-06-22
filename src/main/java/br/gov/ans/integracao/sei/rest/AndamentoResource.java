package br.gov.ans.integracao.sei.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.gov.ans.integracao.sei.client.SeiPortTypeProxy;
import br.gov.ans.integracao.sei.modelo.Operacao;
import br.gov.ans.integracao.sei.utils.Constantes;

public class AndamentoResource {

    @Inject
	private SeiPortTypeProxy seiNativeService;
    
    @Inject
    private UnidadeResource unidadeResource;
	
    
}
