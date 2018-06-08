package br.gov.ans.integracao.sei.modelo;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import br.gov.ans.integracao.sei.client.Unidade;

@Entity
public class UnidadeTarefa extends Unidade{
	
	@JsonIgnore
	private int tarefa;

	@JsonIgnore
	public int getTarefa() {
		return tarefa;
	}

	@JsonIgnore
	public void setTarefa(int tarefa) {
		this.tarefa = tarefa;
	}
}