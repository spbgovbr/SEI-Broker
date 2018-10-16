package br.gov.ans.integracao.sipar.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ControleMigracaoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CD_SEQ_DOCUMENTO")
	private String numero;

	@Column(name = "NR_ANO_DOCUMENTO")
	private String ano;
	
	public ControleMigracaoId() {
	}

	public ControleMigracaoId(String numero, String ano) {
		this.numero = numero;
		this.ano = ano;
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ControleMigracaoId) {
			ControleMigracaoId controleMigracao = (ControleMigracaoId) obj;
			return (this.numero != null && this.numero.equals(controleMigracao.getNumero()) && 
					this.ano != null && this.ano.equals(controleMigracao.getAno()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int numeroPrimo = 31;
		int resultado = 1;
		resultado = (numeroPrimo * resultado) + ((this.numero == null) ? 0 : this.numero.hashCode());
		resultado = (numeroPrimo * resultado) + ((this.ano == null) ? 0 : this.ano.hashCode());
		return resultado;
	}
}
