package br.gov.ans.integracao.sei.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@Entity
@Table(schema = "ANS_GERAL", name = "TB_EXCLUSAO_DOCUMENTO_BROKER")
public class ExclusaoDocumento {

	@XmlTransient
	@JsonIgnore
	@Id
	@Column(name = "ID_EXCLUSAO_DOCUMENTO_BROKER")
	@GeneratedValue(generator = "SQ_EXCLUSAO_DOCUMENTO_BROKER", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SQ_EXCLUSAO_DOCUMENTO_BROKER", sequenceName = "ANS_GERAL.SQ_EXCLUSAO_DOCUMENTO_BROKER", allocationSize = 1)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_EXCLUSAO_DOCUMENTO")
	private Date data;

	@Column(name = "NR_DOCUMENTO_SEI")
	private String numero;

	@Column(name = "NM_SISTEMA_ANS")
	private String sistema;

	@Column(name = "NM_UNIDADE_SEI")
	private String unidade;

	@Column(name = "TX_MOTIVO_EXCLUSAO_DOCUMENTO")
	private String motivo;

	
	public ExclusaoDocumento(){		
	}

	public ExclusaoDocumento(String numero, String sistema, String unidade, String motivo){
		this.numero = numero;
		this.sistema = sistema;
		this.unidade = unidade;
		this.motivo = motivo;
		this.data = new Date();		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
