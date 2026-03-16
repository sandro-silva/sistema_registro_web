package model;

public class Usuario {
	private Long id;
	private String nome;
	private String cpf;
	private boolean participou;
	private String observacao;
	private Long usuarioSistemaId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public boolean isParticipou() {
		return participou;
	}
	public void setParticipou(boolean participou) {
		this.participou = participou;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Long getUsuarioSistemaId() {
		return usuarioSistemaId;
	}
	public void setUsuarioSistemaId(Long usuarioSistemaId) {
		this.usuarioSistemaId = usuarioSistemaId;
	}

}
