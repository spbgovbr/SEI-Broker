package br.gov.ans.integracao.sei.utils;

import static br.gov.ans.integracao.sei.utils.Util.getSOuN;

import java.rmi.RemoteException;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.integracao.sei.client.Contato;
import br.gov.ans.integracao.sei.dao.CidadeDAO;
import br.gov.ans.integracao.sei.dao.ContatoDAO;
import br.gov.ans.integracao.sei.modelo.Cidade;
import br.gov.ans.integracao.sei.modelo.Endereco;
import br.gov.ans.integracao.sei.modelo.Pessoa;
import br.gov.ans.integracao.sei.modelo.PessoaFisica;
import br.gov.ans.integracao.sei.modelo.PessoaJuridica;
import br.gov.ans.integracao.sei.modelo.enums.TipoPessoa;

public class ContatoHelper {
	
	private static final FastDateFormat formatter = FastDateFormat.getInstance(Constantes.DATE_PATTERN);
	
	@Inject
	private ContatoDAO contatoDAO;
	
	@Inject
	private CidadeDAO cidadeDAO;
	
	public Contato buildContato(Pessoa pessoa) throws RemoteException, BusinessException, Exception{
		if(pessoa instanceof PessoaFisica){
			return buildContatoPessoaFisica((PessoaFisica) pessoa);
		}else if(pessoa instanceof PessoaJuridica){
			return buildContatoPessoaJuridica((PessoaJuridica) pessoa);
		}
		
		throw new BusinessException("Tipo inválido");
	}
	
	private Contato buildContatoPessoaFisica(PessoaFisica pessoa) throws RemoteException, BusinessException, Exception{
		Contato contato = buildContatoPessoa(pessoa);
		
		contato.setStaNatureza(TipoPessoa.FISICA.getCodigo());
		
		if(pessoa.getSexo() != null){
			contato.setStaGenero(pessoa.getSexo().getCodigo());			
		}
		
		contato.setCpf(pessoa.getCpf());
		contato.setRg(pessoa.getRg());
		contato.setOrgaoExpedidor(pessoa.getOrgaoEmissor());
		
		if(pessoa.getDataNascimento() !=  null){
			contato.setDataNascimento(formatter.format(pessoa.getDataNascimento()));			
		}
		
		contato.setMatricula(pessoa.getMatricula());
		contato.setMatriculaOab(pessoa.getMatriculaOab());		
		
		return contato;
	}
	
	private Contato buildContatoPessoaJuridica(PessoaJuridica pessoa) throws RemoteException, BusinessException, Exception{
		Contato contato = buildContatoPessoa(pessoa);
		
		contato.setStaNatureza(TipoPessoa.JURIDICA.getCodigo());
		contato.setCnpj(pessoa.getCnpj());
		contato.setSitioInternet(pessoa.getWebsite());
		
		return contato;
	}
	
	private Contato buildContatoPessoa(Pessoa pessoa) throws RemoteException, BusinessException, Exception{
		Contato contato = new Contato();
				
		contato.setSigla(pessoa.getSigla());
		contato.setNome(pessoa.getNome());
		contato.setTelefoneFixo(pessoa.getTelefone());
		contato.setTelefoneCelular(pessoa.getCelular());
		contato.setEmail(pessoa.getEmail());
		contato.setObservacao(pessoa.getObservacao());
		contato.setSinAtivo(getSOuN(pessoa.isAtivo()));
		
		preencherDadosAssociado(pessoa, contato);
		
		if(pessoa.getEndereco() != null){
			preencherEndereco(pessoa.getEndereco(), contato);
		}
		
		return contato;
	}
	
    private void preencherEndereco(Endereco endereco, Contato contato) throws RemoteException, BusinessException, Exception{
		contato.setEndereco(concatenarNumero(endereco.getLogradouro(), endereco.getNumero()));
		contato.setComplemento(endereco.getComplemento());
		contato.setBairro(endereco.getBairro());
		contato.setCep(endereco.getCEP());
    	
		if(StringUtils.isNotBlank(endereco.getCidade())){
			Cidade cidade = getCidade(endereco.getCidade());	
			
			contato.setIdCidade(cidade.getId().toString());
			contato.setIdEstado(cidade.getEstado().getId().toString());  
			contato.setIdPais(cidade.getEstado().getIdPais());			
		}
    }
    
    private void preencherDadosAssociado(Pessoa pessoa, Contato contato) throws RemoteException, BusinessException, Exception{
		if(pessoa.getAssociado() == null){
			contato.setSinEnderecoAssociado(Constantes.NAO);
		}else{
			contato.setSinEnderecoAssociado(Constantes.SIM);
			
			contato.setIdContatoAssociado(getIdContato(pessoa.getAssociado().getSigla())+"");
			contato.setNomeContatoAssociado(pessoa.getAssociado().getNome());
			
			if(pessoa.getAssociado().getEndereco() != null){
				preencherEndereco(pessoa.getAssociado().getEndereco(), contato);				
			}
		}
    }
	
    public void preencherIdContato(Contato contato, String sigla){
    	contato.setIdContato(getIdContato(sigla)+"");
    }
    
	private Integer getIdContato(String sigla){
		return contatoDAO.getContatoPelaSigla(sigla).getId();
	}
	
	private Cidade getCidade(String ibge){
		return cidadeDAO.getCidadePeloIbge(ibge);
	}
    
	private String concatenarNumero(String logradouro, String numero){
		if(StringUtils.isNotBlank(numero)){
			return logradouro.trim() + ", " + numero;
		}
		
		return logradouro;
	}
}
