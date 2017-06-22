package br.gov.ans.integracao.sei.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DIDES.TB_USUARIO_PROJETO")
@SequenceGenerator(name = "sq_usuario_projeto", sequenceName = "DIDES.NOME_DA_SEQUENCE")
@NamedQueries({ @NamedQuery(name = "Usuario.findAllUsuarioServico", query = "SELECT u FROM UsuarioProjeto u "
		+ "WHERE  u.idPessoa is null AND u.senha is not null AND u.usuarioProjeto = 1") })
public class UsuarioProjeto {

	@Id
	@GeneratedValue(generator = "sq_usuario_projeto", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_USUARIO")
	private Long id;

	@Column(name = "ID_PESSOA_USUARIO")
	private Long idPessoa;

	@Column(name = "DE_LOGIN_USUARIO")
	private String loginUsuario;

	@Column(name = "DE_SENHA")
	private String senha;

	@Column(name = "NM_USUARIO")
	private String nomeUsuario;

	@Column(name = "LG_ATIVO")
	private Long status;

	@Column(name = "LG_USUARIO_PROJECT")
	private Long usuarioProjeto;

	@Column(name = "LG_BLOQUEADO")
	private Long usuarioBloqueado;

	@Column(name = "DE_OBSERVACAO_USUARIO")
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getUsuarioBloqueado() {
		return usuarioBloqueado;
	}

	public void setUsuarioBloqueado(Long usuarioBloqueado) {
		this.usuarioBloqueado = usuarioBloqueado;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
