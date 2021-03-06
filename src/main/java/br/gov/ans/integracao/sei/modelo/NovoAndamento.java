package br.gov.ans.integracao.sei.modelo;

import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NovoAndamento {

	private String tarefa;
	private String tarefaModulo;
	private HashMap<String, String> atributos;

	public String getTarefa() {
		return tarefa;
	}

	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}

	public String getTarefaModulo() {
		return tarefaModulo;
	}

	public void setTarefaModulo(String tarefaModulo) {
		this.tarefaModulo = tarefaModulo;
	}

	public HashMap<String, String> getAtributos() {
		return atributos;
	}

	public void setAtributos(HashMap<String, String> atributos) {
		this.atributos = atributos;
	}

}
