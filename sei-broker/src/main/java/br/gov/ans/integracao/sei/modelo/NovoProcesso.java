package br.gov.ans.integracao.sei.modelo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import br.gov.ans.integracao.sei.client.Documento;
import br.gov.ans.integracao.sei.client.Procedimento;

@XmlRootElement
public class NovoProcesso {	
	private Procedimento dadosProcesso;
	private Documento[] documentos;
	private String[] processosRelacionados;
	private String[] unidadesDestino;
	private boolean manterAbertoOrigem;
	private boolean enviarEmailNotificacao;
	private Date dataRetornoProgramado;
	private Integer qtdDiasAteRetorno;
	private boolean somenteDiasUteis;
	private String idMarcadador;
	private String textoMarcador;
	
	public Procedimento getDadosProcesso() {
		return dadosProcesso;
	}
	
	public void setDadosProcesso(Procedimento dadosProcesso) {
		this.dadosProcesso = dadosProcesso;
	}
	
	public Documento[] getDocumentos() {
		return documentos;
	}
	
	public void setDocumentos(Documento[] documentos) {
		this.documentos = documentos;
	}
	
	public String[] getProcessosRelacionados() {
		return processosRelacionados;
	}
	
	public void setProcessosRelacionados(String[] processosRelacionados) {
		this.processosRelacionados = processosRelacionados;
	}
	
	public String[] getUnidadesDestino() {
		return unidadesDestino;
	}
	
	public void setUnidadesDestino(String[] unidadesDestino) {
		this.unidadesDestino = unidadesDestino;
	}
	
	public boolean isManterAbertoOrigem() {
		return manterAbertoOrigem;
	}
	
	public void setManterAbertoOrigem(boolean manterAbertoOrigem) {
		this.manterAbertoOrigem = manterAbertoOrigem;
	}
	
	public boolean isEnviarEmailNotificacao() {
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
	
	public boolean isSomenteDiasUteis() {
		return somenteDiasUteis;
	}
	
	public void setSomenteDiasUteis(boolean somenteDiasUteis) {
		this.somenteDiasUteis = somenteDiasUteis;
	}

	public String getIdMarcadador() {
		return idMarcadador;
	}

	public void setIdMarcadador(String idMarcadador) {
		this.idMarcadador = idMarcadador;
	}

	public String getTextoMarcador() {
		return textoMarcador;
	}

	public void setTextoMarcador(String textoMarcador) {
		this.textoMarcador = textoMarcador;
	}
	
}
