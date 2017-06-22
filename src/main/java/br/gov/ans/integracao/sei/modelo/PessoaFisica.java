package br.gov.ans.integracao.sei.modelo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import br.gov.ans.integracao.sei.modelo.enums.Sexo;

@XmlRootElement
public class PessoaFisica extends Pessoa {
	private Sexo sexo;
	private String cpf;
	private String rg;
	private String orgaoEmissor;
	private Date dataNascimento;
	private String matricula;
	private String matriculaOab;

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getMatriculaOab() {
		return matriculaOab;
	}

	public void setMatriculaOab(String oab) {
		this.matriculaOab = oab;
	}
}
