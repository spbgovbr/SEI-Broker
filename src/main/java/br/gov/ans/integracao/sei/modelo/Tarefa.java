package br.gov.ans.integracao.sei.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@Entity
@Table(name="tarefa")
@NamedQueries({@NamedQuery(name="Tarefa.pesquisarPorID", query="select t from Tarefa t where t.id = :id"),
	@NamedQuery(name="Tarefa.pesquisarPorNome", query="select t from Tarefa t where t.nome like :nome")})
public class Tarefa {
	
	@Id
	@Column(name="id_tarefa")
	private Long identificador;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="id_tarefa_modulo")
	private Long identicadorTarefaModulo;
	
	@Column(name="sin_historico_resumido")
	private String historicoResumido;
	
	@Column(name="sin_historico_completo")
	private String historicoCompleto;
	
	@Column(name="sin_fechar_andamentos_abertos")
	private String fecharAndamentosAbertos;
	
	@Column(name="sin_lancar_andamento_fechado")
	private String lancarAndamentoFechado;
	
	@Column(name="sin_permite_processo_fechado")
	private String permiteProcessoFechado;

	public Long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdenticadorTarefaModulo() {
		return identicadorTarefaModulo;
	}

	public void setIdenticadorTarefaModulo(Long identicadorTarefaModulo) {
		this.identicadorTarefaModulo = identicadorTarefaModulo;
	}

	public String getHistoricoResumido() {
		return historicoResumido;
	}

	public void setHistoricoResumido(String historicoResumido) {
		this.historicoResumido = historicoResumido;
	}

	public String getHistoricoCompleto() {
		return historicoCompleto;
	}

	public void setHistoricoCompleto(String historicoCompleto) {
		this.historicoCompleto = historicoCompleto;
	}

	public String getFecharAndamentosAbertos() {
		return fecharAndamentosAbertos;
	}

	public void setFecharAndamentosAbertos(String fecharAndamentosAbertos) {
		this.fecharAndamentosAbertos = fecharAndamentosAbertos;
	}

	public String getLancarAndamentoFechado() {
		return lancarAndamentoFechado;
	}

	public void setLancarAndamentoFechado(String lancarAndamentoFechado) {
		this.lancarAndamentoFechado = lancarAndamentoFechado;
	}

	public String getPermiteProcessoFechado() {
		return permiteProcessoFechado;
	}

	public void setPermiteProcessoFechado(String permiteProcessoFechado) {
		this.permiteProcessoFechado = permiteProcessoFechado;
	}	
	
}
