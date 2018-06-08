package br.gov.ans.integracao.sei.modelo;

import javax.persistence.Entity;

import br.gov.ans.integracao.sei.client.Unidade;

@Entity
public class UnidadeTarefa extends Unidade{
	
	private int tarefa;

	public int getTarefa() {
		return tarefa;
	}

	public void setTarefa(int tarefa) {
		this.tarefa = tarefa;
	}
}