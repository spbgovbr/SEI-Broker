package br.gov.ans.integracao.sei.modelo;

import java.util.Date;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import br.gov.ans.integracao.sei.client.Unidade;

@Entity
public class UnidadeTarefa extends Unidade{
	
	@JsonIgnore
	private int tarefa;
	
	@JsonIgnore
	private Date data;

	@JsonIgnore
	public int getTarefa() {
		return tarefa;
	}

	@JsonIgnore
	public void setTarefa(int tarefa) {
		this.tarefa = tarefa;
	}

	@JsonIgnore
	public Date getData() {
		return data;
	}

	@JsonIgnore
	public void setData(Date data) {
		this.data = data;
	}
}