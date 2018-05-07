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

import br.gov.ans.integracao.sei.client.Documento;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@Entity
@Table(schema="ANS_GERAL",name="TB_INCLUSAO_DOCUMENTO_BROKER")
public class InclusaoDocumento {

	@XmlTransient
	@JsonIgnore
	@Id 
	@Column(name="ID_INCLUSAO_DOCUMENTO_BROKER")
	@GeneratedValue(generator = "SQ_INCLUSAO_DOCUMENTO_BROKER", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SQ_INCLUSAO_DOCUMENTO_BROKER", sequenceName = "ANS_GERAL.SQ_INCLUSAO_DOCUMENTO_BROKER", allocationSize=1)
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INCLUSAO_DOCUMENTO")
	private Date data;
	
	@Column(name="NM_DOCUMENTO")
	private String nome;
	
	@Column(name="NR_DOCUMENTO_SEI")
	private String numero;
	
	@Column(name="DE_HASH_DOCUMENTO")
	private String hash;
	
	@Column(name="NM_SISTEMA_ANS")
	private String sistema;
	
	@Column(name="NM_UNIDADE_SEI")
	private String unidade;
	
	@Column(name="NR_PROCESSO_SEI")
	private String processo;
	
	@Column(name="CD_DOCUMENTO_USUARIO")
	private String numeroInformado;
	
	@Column(name="DE_LINK_ACESSO")
	private String link;	
	
	public InclusaoDocumento(){
		
	}
	
	public InclusaoDocumento(Documento documento, String unidade, String sistema, String hash){
		this.unidade = unidade;
		this.sistema = sistema;
		this.hash = hash;
		this.numeroInformado = documento.getNumero();
		
		this.nome = documento.getNomeArquivo();
		this.processo = documento.getIdProcedimento();
		
		this.data = new Date();
	}

	@JsonIgnore
	public long getId() {
		return id;
	}

	@JsonIgnore
	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getNumeroInformado() {
		return numeroInformado;
	}

	public void setNumeroInformado(String numeroInformado) {
		this.numeroInformado = numeroInformado;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
