package br.gov.ans.integracao.sei.modelo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EnvioDeProcesso {
	private String numeroDoProcesso;	
	private String[] unidadesDestino;
	private boolean manterAbertoOrigem;
	private boolean removerAnotacoes;
	private boolean enviarEmailNotificacao;
	private Date dataRetornoProgramado;
	private Integer qtdDiasAteRetorno;
	private boolean somenteDiasUteis;

	public String getNumeroDoProcesso() {
		return numeroDoProcesso;
	}

	public void setNumeroDoProcesso(String numeroDoProcesso) {
		this.numeroDoProcesso = numeroDoProcesso;
	}

	public String[] getUnidadesDestino() {
		return unidadesDestino;
	}

	public void setUnidadesDestino(String[] unidadesDestino) {
		this.unidadesDestino = unidadesDestino;
	}

	public boolean getManterAbertoOrigem() {
		return manterAbertoOrigem;
	}

	public void setManterAbertoOrigem(boolean manterAbertoOrigem) {
		this.manterAbertoOrigem = manterAbertoOrigem;
	}

	public boolean getRemoverAnotacoes() {
		return removerAnotacoes;
	}

	public void setRemoverAnotacoes(boolean removerAnotacoes) {
		this.removerAnotacoes = removerAnotacoes;
	}

	public boolean getEnviarEmailNotificacao() {
		return enviarEmailNotificacao;
	}

	public void setEnviarEmailNotificacao(boolean enviarEmailNotificacao) {
		this.enviarEmailNotificacao = enviarEmailNotificacao;
	}

	public Date getDataRetornoProgramado() {
		return dataRetornoProgramado;
	}

	public void setDataRetornoProgramado(Date dataRetornoProgramado) {
		this.dataRetornoProgramado = dataRetornoProgramado;
	}

	public Integer getQtdDiasAteRetorno() {
		return qtdDiasAteRetorno;
	}

	public void setQtdDiasAteRetorno(Integer qtdDiasAteRetorno) {
		this.qtdDiasAteRetorno = qtdDiasAteRetorno;
	}

	public boolean getSomenteDiasUteis() {
		return somenteDiasUteis;
	}

	public void setSomenteDiasUteis(boolean somenteDiasUteis) {
		this.somenteDiasUteis = somenteDiasUteis;
	}

}
