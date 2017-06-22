package br.gov.ans.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(schema="ANS_GERAL",name="TB_LOG_INTEGRACAO_SISTEMICA")
@NamedQueries({@NamedQuery(name = "LogIntegracaoSistemica.ultimosRequests", query = "SELECT l FROM LogIntegracaoSistemica l order by l.data desc")})
public class LogIntegracaoSistemica {
	
	@Id
	@Column(name="ID_LOG_INTEGRACAO_SISTEMICA")
	@GeneratedValue(generator = "SQ_INTEGRACAO_SISTEMICA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SQ_INTEGRACAO_SISTEMICA", sequenceName = "ANS_GERAL.SQ_ID_INTEGRACAO_SISTEMICA", allocationSize=1)
	private Long id;
	
	@Column(name="DE_LOG_ORIGEM_REQUISICAO")
	private String origem;
	
	@Column(name="DE_LOG_DESTINO_REQUISICAO")
	private String destino;
	
	@Column(name="DE_OPERACAO")
	private String operacao;
	
	@Column(name="DT_REQUISICAO_INTEGRACAO_SIST")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	public String getOrigem() {
		return origem;
	}
	
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	public String getDestino() {
		return destino;
	}
	
	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	public String getOperacao() {
		return operacao;
	}
	
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
}
