package br.gov.ans.integracao.sei.utils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.jboss.logging.Logger;

import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ResourceNotFoundException;
import br.gov.ans.integracao.sei.client.Contato;
import br.gov.ans.integracao.sei.dao.CidadeDAO;
import br.gov.ans.integracao.sei.dao.ContatoDAO;
import br.gov.ans.integracao.sei.modelo.Endereco;
import br.gov.ans.integracao.sei.modelo.Pessoa;
import br.gov.ans.integracao.sei.modelo.PessoaFisica;
import br.gov.ans.integracao.sei.modelo.PessoaJuridica;
import br.gov.ans.integracao.sei.modelo.enums.Sexo;
import br.gov.ans.integracao.sei.modelo.enums.TipoPessoa;
import br.gov.ans.utils.MessageUtils;

public class PessoaHelper {
	
	@Inject
	private ContatoDAO contatoDAO;
	
	@Inject
	private CidadeDAO cidadeDAO;
	
	@Inject
	private Logger logger;
	
	@Inject
	private MessageUtils messages;
	
	private static final FastDateFormat formatter = FastDateFormat.getInstance(Constantes.DATE_PATTERN);
	
	public List<Pessoa> buildPessoa(Contato[] contatos) throws ParseException, BusinessException, IllegalAccessException, ResourceNotFoundException{
		if(ArrayUtils.isNotEmpty(contatos)){
			ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
			
			for(Contato c : contatos){
				pessoas.add(buildPessoa(c));
			}
			
			return pessoas;
		}
		
		throw new ResourceNotFoundException(messages.getMessage("erro.nenhum.contato.encontrado"));
	}
	
	public Pessoa buildPessoa(Contato contato) throws ParseException, BusinessException, IllegalAccessException{
		TipoPessoa tipo = TipoPessoa.getByCodigo(contato.getStaNatureza());
		
		if(tipo.equals(TipoPessoa.FISICA)){
			return buildPessoaFisica(contato);
		}
		
		if(tipo.equals(TipoPessoa.JURIDICA)){
			return buildPessoaJuridica(contato);
		}
		
		throw new BusinessException(messages.getMessage("erro.tipo.pessoa.invalido"));
	}
	
	public PessoaFisica buildPessoaFisica(Contato contato) throws ParseException, IllegalAccessException{
		PessoaFisica pessoa = new PessoaFisica();
		
		pessoa.setSigla(contato.getSigla());
		pessoa.setNome(contato.getNome());
		
		if(StringUtils.isNotBlank(contato.getStaGenero())){
			pessoa.setSexo(Sexo.getByCodigo(contato.getStaGenero()));			
		}
		
		if(StringUtils.isNotBlank(contato.getDataNascimento())){
			pessoa.setDataNascimento(formatter.parse(contato.getDataNascimento()));			
		}
		
		pessoa.setCpf(contato.getCpf());
		pessoa.setRg(contato.getRg());
		pessoa.setOrgaoEmissor(contato.getOrgaoExpedidor());
		pessoa.setMatricula(contato.getMatricula());
		pessoa.setMatriculaOab(contato.getMatriculaOab());
		pessoa.setCelular(contato.getTelefoneCelular());
		pessoa.setTelefone(contato.getTelefoneFixo());
		pessoa.setEmail(contato.getEmail());
		pessoa.setObservacao(contato.getObservacao());
		pessoa.setAtivo("S".equals(contato.getSinAtivo()));
		
		pessoa.setEndereco(buildEndereco(contato));
		
		if("S".equals(contato.getSinEnderecoAssociado())){
			pessoa.setAssociado(buildAssociado(contato));
		}
		
		return pessoa;
	}
	
	public PessoaJuridica buildPessoaJuridica(Contato contato) throws ParseException, IllegalAccessException{
		PessoaJuridica pessoa = new PessoaJuridica();
		
		pessoa.setSigla(contato.getSigla());
		pessoa.setNome(contato.getNome());
		pessoa.setCnpj(contato.getCnpj());
		pessoa.setCelular(contato.getTelefoneCelular());
		pessoa.setTelefone(contato.getTelefoneFixo());
		pessoa.setEmail(contato.getEmail());
		pessoa.setWebsite(contato.getSitioInternet());
		pessoa.setObservacao(contato.getObservacao());
		pessoa.setAtivo("S".equals(contato.getSinAtivo()));
		
		pessoa.setEndereco(buildEndereco(contato));
		
		if("S".equals(contato.getSinEnderecoAssociado())){
			pessoa.setAssociado(buildAssociado(contato));			
		}
		
		return pessoa;
	}
	
	private Endereco buildEndereco(Contato contato){
		Endereco endereco = new Endereco();
		
		endereco.setLogradouro(contato.getEndereco());
		endereco.setComplemento(contato.getComplemento());
		endereco.setBairro(contato.getBairro());
		endereco.setCEP(contato.getCep());
		
		if(StringUtils.isNotBlank(contato.getIdCidade())){
			endereco.setCidade(getCodigoIbge(contato.getIdCidade()));			
		}
		endereco.setUf(contato.getSiglaEstado());
		endereco.setPais(contato.getNomePais());		
		
		return endereco;
	}
	
	private Endereco buildEnderecoAssociado(Contato contato){
		Endereco endereco = new Endereco();
		
		endereco.setLogradouro(contato.getEndereco());
		endereco.setComplemento(contato.getComplemento());
		endereco.setBairro(contato.getBairro());
		endereco.setCEP(contato.getCep());
		
		if(StringUtils.isNotBlank(contato.getIdCidade())){
			endereco.setCidade(getCodigoIbge(contato.getIdCidade()));			
		}
		endereco.setUf(contato.getSiglaEstado());
		endereco.setPais(contato.getNomePais());		
		
		return endereco;
	}
	
	private PessoaJuridica buildAssociado(Contato contato){
		PessoaJuridica pessoa = new PessoaJuridica();
		
		pessoa.setSigla(getSiglaContato(contato.getIdContatoAssociado()));		
		pessoa.setNome(contato.getNomeContatoAssociado());
		pessoa.setCnpj(contato.getCnpjAssociado());
		
		pessoa.setEndereco(buildEnderecoAssociado(contato));
		
		return pessoa;
	}
	
	private String getSiglaContato(String id){
		return contatoDAO.getContatoPeloId(id).getSigla();
	}
	
	public boolean hasAssociado(Contato contato) throws IllegalAccessException{
		List<Field> fieldsList = FieldUtils.getAllFieldsList(Contato.class);
		
		for(Field f : fieldsList){
			if(f.getName().contains("Associado")){
				String field = (String) FieldUtils.readDeclaredField(contato, f.getName(), true);
				
				logger.warn(field);
				
				if(StringUtils.isNotBlank(field)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public String getCodigoIbge(String idCidade){
		return cidadeDAO.getCidadePeloId(idCidade).getCodigoIbge();
	}
}
